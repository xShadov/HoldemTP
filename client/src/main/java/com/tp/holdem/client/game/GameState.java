package com.tp.holdem.client.game;

import com.google.common.base.Preconditions;
import com.tp.holdem.client.architecture.message.ServerObservable;
import com.tp.holdem.model.game.Card;
import com.tp.holdem.model.game.Player;
import com.tp.holdem.model.game.PokerTable;
import com.tp.holdem.model.message.Message;
import com.tp.holdem.model.message.MessageType;
import com.tp.holdem.model.message.PlayerConnectMessage;
import com.tp.holdem.model.message.UpdateStateMessage;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GameState implements ServerObservable {
	private PokerTable table;
	private Player currentPlayer;
	private Player bettingPlayer;
	private Player winnerPlayer;
	private List<Player> allPlayers;

	public List<Player> getOtherPlayers() {
		return getAllPlayers().stream()
				.skip(1)
				.collect(Collectors.toList());
	}

	public List<Player> playersExcept(Player... players) {
		final List<Integer> numbers = Arrays.stream(players).map(Player::getNumber).collect(Collectors.toList());
		return getAllPlayers().stream()
				.filter(player -> !numbers.contains(player.getNumber()))
				.collect(Collectors.toList());
	}

	public List<Player> getAllPlayers() {
		final List<Player> players = allPlayers;

		while (players.get(0).getNumber() != currentPlayer.getNumber())
			Collections.rotate(players, 1);

		return players;
	}

	public boolean isCurrentPlayerWinner() {
		return winnerPlayer.getNumber() == currentPlayer.getNumber();
	}

	public boolean isCurrentPlayerWaiting() {
		return isCurrentPlayerConnected() && !isGameStarted();
	}

	public boolean isCurrentPlayerConnected() {
		return currentPlayer != null;
	}

	public boolean isGameStarted() {
		return getTable() != null;
	}

	public int relativePlayerNumber(Player player) {
		return (player.getNumber() + getCurrentPlayer().getNumber()) % getAllPlayers().size();
	}

	public boolean hasWinner() {
		return getWinnerPlayer() != null;
	}

	public boolean isSomeoneBetting() {
		return getBettingPlayer() != null;
	}

	public List<Card> getCardsOnTable() {
		return table.getCardsOnTable();
	}

	public int getPotAmount() {
		return table.getPotAmount();
	}

	public int getSmallBlindAmount() {
		return table.getSmallBlindAmount();
	}

	public int getBigBlindAmount() {
		return table.getBigBlindAmount();
	}

	@Override
	public void accept(Message message) {
		log.debug(String.format("Received message: %s", message.getMessageType()));

		if (message.getMessageType() == MessageType.PLAYER_CONNECTION) {
			handlePlayerConnection(message);
		}

		if (message.getMessageType() == MessageType.UPDATE_STATE) {
			handleUpdateState(message);
		}
	}

	private void handlePlayerConnection(Message message) {
		log.debug("Handling player connection event");

		Preconditions.checkArgument(!isCurrentPlayerConnected(), "Player already connected");

		final PlayerConnectMessage response = message.instance(PlayerConnectMessage.class);

		currentPlayer = response.getPlayer();

		log.debug(String.format("Registered current player with number: %d", currentPlayer.getNumber()));
	}

	private void handleUpdateState(Message message) {
		log.debug("Handling update state event");

		final UpdateStateMessage response = message.instance(UpdateStateMessage.class);
		copyProperties(response);

		log.debug(String.format("Staring game with %d players", allPlayers.size()));
	}

	public void copyProperties(UpdateStateMessage response) {
		this.currentPlayer = response.getCurrentPlayer();
		this.allPlayers = response.getAllPlayers();
		this.bettingPlayer = response.getBettingPlayer();
		this.table = response.getTable();
		this.winnerPlayer = response.getWinnerPlayer();
	}
}
