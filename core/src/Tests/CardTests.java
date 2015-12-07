package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.tp.holdem.BetComparator;
import com.tp.holdem.Card;
import com.tp.holdem.HandOperations;
import com.tp.holdem.HandRank;
import com.tp.holdem.HandRankComparator;
import com.tp.holdem.HandRankingEnum;
import com.tp.holdem.Player;

public class CardTests {

	@Test                                        
    public final void testCardsValue() {          
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card("Jack", "Spade"));
		cards.add(new Card("Jack", "Heart"));
		cards.add(new Card("Jack", "Diamond"));
		cards.add(new Card("Queen", "Spade"));
		cards.add(new Card("5", "Heart"));
		cards.add(new Card("Queen", "Diamond"));
		cards.add(new Card("4", "Spade"));
		cards.add(new Card("8", "Spade"));
		assertEquals(11, cards.get(0).getValue());
		assertEquals(11, cards.get(1).getValue());
		assertEquals(11, cards.get(2).getValue());
		assertEquals(12, cards.get(3).getValue());
		assertEquals(5, cards.get(4).getValue());
		assertEquals(12, cards.get(5).getValue());
		assertEquals(4, cards.get(6).getValue());
		assertEquals(8, cards.get(7).getValue());
    } 
	
	@Test                                        
    public final void testHighCard() {          
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card("4", "Heart"));
		cards.add(new Card("Jack", "Spade"));
		List<Card> tableCards = new ArrayList<Card>();
		tableCards.add(new Card("6", "Club"));
		tableCards.add(new Card("8", "Heart"));
		tableCards.add(new Card("9", "Spade"));
		tableCards.add(new Card("3", "Club"));
		tableCards.add(new Card("King", "Spade"));
		assertEquals(HandRankingEnum.HIGH_CARD, HandOperations.findHandRank(0, cards, tableCards).getHand());
		
		cards = new ArrayList<Card>();
		cards.add(new Card("4", "Heart"));
		cards.add(new Card("4", "Spade"));
		tableCards = new ArrayList<Card>();
		tableCards.add(new Card("8", "Club"));
		tableCards.add(new Card("8", "Heart"));
		tableCards.add(new Card("8", "Spade"));
		tableCards.add(new Card("3", "Club"));
		tableCards.add(new Card("King", "Spade"));
		assertEquals(HandRankingEnum.FULL_HOUSE, HandOperations.findHandRank(1, cards, tableCards).getHand());
    } 
	
	@Test                                        
    public final void testFullHouse() {          
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card("4", "Heart"));
		cards.add(new Card("4", "Spade"));
		List<Card> tableCards = new ArrayList<Card>();
		tableCards.add(new Card("8", "Club"));
		tableCards.add(new Card("8", "Heart"));
		tableCards.add(new Card("8", "Spade"));
		tableCards.add(new Card("3", "Club"));
		tableCards.add(new Card("King", "Spade"));
		assertEquals(HandRankingEnum.FULL_HOUSE, HandOperations.findHandRank(1, cards, tableCards).getHand());
    } 
	
	@Test                                        
    public final void testOnePair() {          
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card("4", "Heart"));
		cards.add(new Card("4", "Spade"));
		List<Card> tableCards = new ArrayList<Card>();
		tableCards.add(new Card("7", "Club"));
		tableCards.add(new Card("8", "Heart"));
		tableCards.add(new Card("10", "Spade"));
		tableCards.add(new Card("3", "Club"));
		tableCards.add(new Card("King", "Spade"));
		assertEquals(HandRankingEnum.PAIR, HandOperations.findHandRank(1, cards, tableCards).getHand());
    } 
	
	@Test                                        
    public final void testTwoPair() {          
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card("4", "Heart"));
		cards.add(new Card("4", "Spade"));
		List<Card> tableCards = new ArrayList<Card>();
		tableCards.add(new Card("8", "Club"));
		tableCards.add(new Card("8", "Heart"));
		tableCards.add(new Card("10", "Spade"));
		tableCards.add(new Card("3", "Club"));
		tableCards.add(new Card("King", "Spade"));
		assertEquals(HandRankingEnum.TWO_PAIR, HandOperations.findHandRank(1, cards, tableCards).getHand());
    } 
	
	@Test                                        
    public final void testThreeOfAKind() {          
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card("4", "Heart"));
		cards.add(new Card("4", "Spade"));
		List<Card> tableCards = new ArrayList<Card>();
		tableCards.add(new Card("4", "Club"));
		tableCards.add(new Card("8", "Heart"));
		tableCards.add(new Card("10", "Spade"));
		tableCards.add(new Card("3", "Club"));
		tableCards.add(new Card("King", "Spade"));
		assertEquals(HandRankingEnum.THREE_OF_A_KIND, HandOperations.findHandRank(1, cards, tableCards).getHand());
    } 
	
	@Test                                        
    public final void testFourOfAKind() {          
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card("4", "Heart"));
		cards.add(new Card("4", "Spade"));
		List<Card> tableCards = new ArrayList<Card>();
		tableCards.add(new Card("8", "Club"));
		tableCards.add(new Card("8", "Heart"));
		tableCards.add(new Card("8", "Spade"));
		tableCards.add(new Card("8", "Club"));
		tableCards.add(new Card("King", "Spade"));
		assertEquals(HandRankingEnum.FOUR_OF_A_KIND, HandOperations.findHandRank(1, cards, tableCards).getHand());
    } 
	
	@Test                                        
    public final void testFlush() {          
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card("4", "Heart"));
		cards.add(new Card("4", "Spade"));
		List<Card> tableCards = new ArrayList<Card>();
		tableCards.add(new Card("8", "Club"));
		tableCards.add(new Card("8", "Spade"));
		tableCards.add(new Card("10", "Spade"));
		tableCards.add(new Card("3", "Spade"));
		tableCards.add(new Card("King", "Spade"));
		assertEquals(HandRankingEnum.FLUSH, HandOperations.findHandRank(1, cards, tableCards).getHand());
    } 
	
	@Test                                        
    public final void testRoyalFlush() {          
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card("4", "Heart"));
		cards.add(new Card("Jack", "Club"));
		List<Card> tableCards = new ArrayList<Card>();
		tableCards.add(new Card("4", "Club"));
		tableCards.add(new Card("Queen", "Club"));
		tableCards.add(new Card("10", "Club"));
		tableCards.add(new Card("Ace", "Club"));
		tableCards.add(new Card("King", "Club"));
		assertEquals(HandRankingEnum.ROYAL_FLUSH, HandOperations.findHandRank(1, cards, tableCards).getHand());
    } 
	
	@Test                                        
    public final void testStraight() {          
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card("4", "Heart"));
		cards.add(new Card("Jack", "Club"));
		List<Card> tableCards = new ArrayList<Card>();
		tableCards.add(new Card("4", "Club"));
		tableCards.add(new Card("Queen", "Heart"));
		tableCards.add(new Card("10", "Club"));
		tableCards.add(new Card("Ace", "Spade"));
		tableCards.add(new Card("King", "Heart"));
		assertEquals(HandRankingEnum.STRAIGHT, HandOperations.findHandRank(1, cards, tableCards).getHand());
    } 
	
	@Test                                        
    public final void testStraightStartingWithAce() {          
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card("Ace", "Heart"));
		cards.add(new Card("2", "Club"));
		List<Card> tableCards = new ArrayList<Card>();
		tableCards.add(new Card("4", "Club"));
		tableCards.add(new Card("Queen", "Heart"));
		tableCards.add(new Card("10", "Club"));
		tableCards.add(new Card("3", "Spade"));
		tableCards.add(new Card("5", "Heart"));
		assertEquals(HandRankingEnum.STRAIGHT, HandOperations.findHandRank(1, cards, tableCards).getHand());
    } 
	
	@Test                                        
    public final void testStraightFlush() {          
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card("King", "Club"));
		cards.add(new Card("2", "Heart"));
		List<Card> tableCards = new ArrayList<Card>();
		tableCards.add(new Card("4", "Heart"));
		tableCards.add(new Card("6", "Heart"));
		tableCards.add(new Card("10", "Club"));
		tableCards.add(new Card("3", "Heart"));
		tableCards.add(new Card("5", "Heart"));
		assertEquals(HandRankingEnum.STRAIGHT_FLUSH, HandOperations.findHandRank(1, cards, tableCards).getHand());
    } 
	
	@Test
	public final void testCompareTwoHands(){
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card("4", "Heart"));
		cards.add(new Card("5", "Spade"));
		List<Card> tableCards = new ArrayList<Card>();
		tableCards.add(new Card("6", "Club"));
		tableCards.add(new Card("8", "Heart"));
		tableCards.add(new Card("9", "Spade"));
		tableCards.add(new Card("3", "Club"));
		tableCards.add(new Card("7", "Spade"));
		List<HandRank> hands = new ArrayList<HandRank>();
		hands.add(HandOperations.findHandRank(0, cards, tableCards));
		cards = new ArrayList<Card>();
		cards.add(new Card("Queen", "Heart"));
		cards.add(new Card("King", "Heart"));
		tableCards = new ArrayList<Card>();
		tableCards.add(new Card("Ace", "Heart"));
		tableCards.add(new Card("10", "Heart"));
		tableCards.add(new Card("Jack", "Heart"));
		tableCards.add(new Card("3", "Club"));
		tableCards.add(new Card("7", "Spade"));
		hands.add(HandOperations.findHandRank(1, cards, tableCards));
		HandRankComparator comp = new HandRankComparator();
		assertEquals(-1, comp.compare(hands.get(0), hands.get(1) ));
		assertEquals(1, comp.compare(hands.get(1), hands.get(0) ));
		assertEquals(0, comp.compare(hands.get(0), hands.get(0) ));
	}
	@Test                                        
    public final void testSortingByHandPower() {          
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card("4", "Heart"));
		cards.add(new Card("Jack", "Spade"));
		List<Card> tableCards = new ArrayList<Card>();
		tableCards.add(new Card("6", "Club"));
		tableCards.add(new Card("8", "Heart"));
		tableCards.add(new Card("9", "Spade"));
		tableCards.add(new Card("3", "Club"));
		tableCards.add(new Card("King", "Spade"));
		List<HandRank> hands = new ArrayList<HandRank>();
		
		hands.add(HandOperations.findHandRank(0, cards, tableCards));
		cards = new ArrayList<Card>();
		cards.add(new Card("10", "Heart"));
		cards.add(new Card("Jack", "Heart"));
		tableCards = new ArrayList<Card>();
		tableCards.add(new Card("2", "Heart"));
		tableCards.add(new Card("4", "Heart"));
		tableCards.add(new Card("Ace", "Heart"));
		tableCards.add(new Card("3", "Club"));
		tableCards.add(new Card("King", "Spade"));
		hands.add(HandOperations.findHandRank(1, cards, tableCards));

		cards = new ArrayList<Card>();
		cards.add(new Card("10", "Heart"));
		cards.add(new Card("Jack", "Heart"));
		tableCards = new ArrayList<Card>();
		tableCards.add(new Card("2", "Heart"));
		tableCards.add(new Card("4", "Heart"));
		tableCards.add(new Card("8", "Heart"));
		tableCards.add(new Card("3", "Club"));
		tableCards.add(new Card("9", "Spade"));
		hands.add(HandOperations.findHandRank(2, cards, tableCards));
		
		cards = new ArrayList<Card>();
		cards.add(new Card("10", "Heart"));
		cards.add(new Card("Jack", "Heart"));
		tableCards = new ArrayList<Card>();
		tableCards.add(new Card("2", "Heart"));
		tableCards.add(new Card("4", "Heart"));
		tableCards.add(new Card("8", "Heart"));
		tableCards.add(new Card("3", "Club"));
		tableCards.add(new Card("9", "Spade"));
		hands.add(HandOperations.findHandRank(3, cards, tableCards));
		Collections.sort(hands, new HandRankComparator());
		assertEquals(0, hands.get(0).getPlayerNumber());
		assertEquals(2, hands.get(1).getPlayerNumber());
		assertEquals(3, hands.get(2).getPlayerNumber());
		assertEquals(1, hands.get(3).getPlayerNumber());
    } 
	
	@Test                                        
    public final void testSortingByBet() {          
		List<HandRank> hands = new ArrayList<HandRank>();
		List<Player> players = new ArrayList<Player>();
		Player player0 = new Player(0);
		player0.setBetAmount(500);
		Player player1 = new Player(1);
		player1.setBetAmount(600);
		Player player2 = new Player(2);
		player2.setBetAmount(1000);
		Player player3 = new Player(3);
		player3.setBetAmount(600);
		players.add(player0); players.add(player1); players.add(player2);players.add(player3);
		hands.add(new HandRank(0, HandRankingEnum.ROYAL_FLUSH, new ArrayList<Card>()));
		hands.add(new HandRank(1, HandRankingEnum.FLUSH, new ArrayList<Card>()));
		hands.add(new HandRank(2, HandRankingEnum.STRAIGHT, new ArrayList<Card>()));
		hands.add(new HandRank(3, HandRankingEnum.FOUR_OF_A_KIND, new ArrayList<Card>()));
		Collections.sort(hands, new HandRankComparator());
		List<Player> winners = new ArrayList<Player>();
		for(int i=0; i<hands.size();i++){
			winners.add(players.get(hands.get(i).getPlayerNumber()));
		}
		Collections.sort(winners, new BetComparator());
		assertEquals(500, winners.get(0).getBetAmount());
		assertEquals(600, winners.get(1).getBetAmount());
		assertEquals(600, winners.get(2).getBetAmount());
		assertEquals(1000, winners.get(3).getBetAmount());
    } 
}
