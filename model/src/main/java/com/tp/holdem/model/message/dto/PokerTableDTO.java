package com.tp.holdem.model.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PokerTableDTO {
	private int potAmount;
	private int smallBlindAmount;
	private int bigBlindAmount;
	private PlayerDTO bettingPlayer;
	private PlayerDTO winnerPlayer;
	@Builder.Default
	private List<PlayerDTO> allPlayers = new ArrayList<>();
	@Builder.Default
	private List<CardDTO> cardsOnTable = new ArrayList<>();
}