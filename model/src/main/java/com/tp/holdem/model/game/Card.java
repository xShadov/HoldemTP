package com.tp.holdem.model.game;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Card {
	private Suit suit;
	private Honour honour;
	private int value;

	private Card(final Suit suit, final Honour honour) {
		this.suit = suit;
		this.honour = honour;
		this.value = this.honour.value();
	}

	public static Card from(Suit suit, Honour honour) {
		return new Card(suit, honour);
	}

	public String code() {
		return String.format("%s_%s", honour.name(), suit.name());
	}
}