package com.tp.holdem.client.model;

import com.tp.holdem.client.strategy.Strategy;
import lombok.Data;

@Data
public class Bot extends Player {
	private Strategy strategy;

	public Bot(final int number, final String name, final Strategy strategy) {
		super(number, name);
		this.strategy = strategy;
	}

}