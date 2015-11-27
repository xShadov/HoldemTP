package com.tp.holdem;

public enum HandRankingEnum {

    ROYAL_FLUSH(10),
    STRAIGHT_FLUSH(9),
    FOUR_OF_A_KIND(8),
    FULL_HOUSE(7),
    FLUSH(6),
    STRAIGHT(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    PAIR(2),
    HIGH_CARD(1);
	
	private int value;
	
	HandRankingEnum(int value){
		this.value = value;
	}
	
	int getValue(){
		return value;
	}
}