package com.tp.holdem.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.tp.holdem.logic.model.Player;
import com.tp.holdem.logic.model.PokerTable;
import com.tp.holdem.model.common.Phase;
import com.tp.holdem.model.message.Message;
import com.tp.holdem.model.message.MessageType;
import com.tp.holdem.model.message.PlayerActionMessage;
import com.tp.holdem.model.message.PlayerConnectMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
class HoldemServerListener extends Listener {
	private final MessageSender sender;
	private final GameParams params;
	private final GameHandler gameHandler;

	private ConnectedPlayers connectedPlayers = ConnectedPlayers.empty();
	private int expectActionFrom;

	private final ScheduledExecutorService delayer = Executors.newSingleThreadScheduledExecutor();

	@Override
	public synchronized void received(final Connection connection, final Object object) {
		if (object instanceof Message) {
			log.debug("Received message from " + connection.getID() + ": " + object);

			if (connection.getID() != expectActionFrom)
				throw new IllegalStateException("Unexpected player sent action");

			final Integer playerNumber = connectedPlayers.getConnected(connection.getID())
					.getOrElseThrow(() -> new IllegalStateException("Message from not-connected player"));

			final Message message = (Message) object;

			if (message.getMessageType() == MessageType.PLAYER_ACTION) {
				log.debug(String.format("Received action of type %s from player %d", message.getMessageType(), playerNumber));

				final PlayerActionMessage content = message.instance(PlayerActionMessage.class);

				final PokerTable response = gameHandler.handlePlayerMove(playerNumber, content);

				sender.sendStateUpdate(connectedPlayers, response);
				expectActionFrom = findExpectedResponder(response);

				if (response.getPhase() == Phase.OVER) {
					log.debug("Current round is over, sleeping for 5s and staring new round");

					delayer.schedule(this::startRound, 5, TimeUnit.SECONDS);
				}
			}
		}
	}

	@Override
	public synchronized void connected(final Connection con) {
		log.debug(String.format("Connection attempt: %d", con.getID()));

		final boolean connected = tryConnectingPlayer(con);

		if (!connected) {
			log.debug("Could not connect player");
			return;
		}

		if (enoughPlayers()) {
			log.debug("Enough players connected, starting game");
			startGame();
		}
	}

	@Override
	public synchronized void disconnected(final Connection con) {
		log.debug(String.format("Dis-connection attempt: %d", con.getID()));

		if (!connectedPlayers.isConnected(con.getID()))
			throw new IllegalStateException("Player is not connected");

		connectedPlayers.getConnected(con.getID())
				.map(gameHandler::disconnectPlayer)
				.forEach(table -> sender.sendStateUpdate(connectedPlayers, table));
	}

	private boolean tryConnectingPlayer(Connection con) {
		if (enoughPlayers()) {
			log.debug("Already enough players, could not connect");

			final PlayerConnectMessage response = PlayerConnectMessage.failure();
			sender.sendSingle(con.getID(), Message.from(MessageType.PLAYER_CONNECTION, response));
			return false;
		}

		if (connectedPlayers.isConnected(con.getID()))
			throw new IllegalStateException("Player already connected");

		final Player player = gameHandler.connectPlayer();
		connectedPlayers = connectedPlayers.connect(con.getID(), player.getNumber());

		log.debug(String.format("Connected player: %d", player.getNumber()));

		final PlayerConnectMessage connectionResponse = PlayerConnectMessage.success(player.toCurrentPlayerDTO());
		sender.sendSingle(con.getID(), Message.from(MessageType.PLAYER_CONNECTION, connectionResponse));
		return true;
	}

	private void startGame() {
		log.debug("Staring new game");

		final PokerTable response = gameHandler.startGame();
		sender.sendStateUpdate(connectedPlayers, response);
		expectActionFrom = findExpectedResponder(response);
	}

	private void startRound() {
		log.debug("Starting new round");

		final PokerTable response = gameHandler.startRound();
		sender.sendStateUpdate(connectedPlayers, response);
		expectActionFrom = findExpectedResponder(response);
	}

	private int findExpectedResponder(PokerTable response) {
		int value = -1;

		if (response.getPhase() != Phase.OVER) {
			value = connectedPlayers.getConnectionId(response.getBettingPlayer().map(Player::getNumber).getOrElseThrow(() -> new IllegalArgumentException("Player not found")))
					.getOrElseThrow(() -> new IllegalArgumentException("Expecting action from non-existing player"));
		}

		log.debug(String.format("Expecting next action from: %d", value));

		return value;
	}

	private boolean enoughPlayers() {
		return connectedPlayers.size() >= params.getPlayerCount();
	}
}
