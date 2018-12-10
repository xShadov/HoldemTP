package com.tp.holdem.client.architecture.model.action;

import com.tp.holdem.client.architecture.model.common.UpdateStateMessage;
import com.tp.holdem.client.architecture.model.common.PlayerConnectMessage;
import io.vavr.control.Option;

public enum ActionType {
	PLAYER_CONNECT(PlayerConnectMessage.class),
	UPDATE_STATE(UpdateStateMessage.class),
	CHECK,
	FOLD,
	ALLIN,
	CALL,
	RAISE(PlayerRaiseAction.class),
	BET(PlayerBetAction.class);

	private Class<? extends ActionRequest> clazz;

	ActionType() {

	}

	ActionType(Class<? extends ActionRequest> clazz) {
		this.clazz = clazz;
	}

	public Option<Class<? extends ActionRequest>> clazz() {
		return Option.of(clazz);
	}
}
