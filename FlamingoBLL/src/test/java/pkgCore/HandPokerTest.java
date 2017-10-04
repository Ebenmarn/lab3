package pkgCore;

import static org.junit.Assert.*;

import org.junit.Test;

import pkgEnum.eRank;
import pkgEnum.eSuit;

public class HandPokerTest {

	@Test
	public void FOUROFAKIND1() {
		
		HandPoker hp = new HandPoker();
		hp.AddCard(new Card(eSuit.CLUBS,eRank.TWO));
		hp.AddCard(new Card(eSuit.CLUBS,eRank.TWO));
		hp.AddCard(new Card(eSuit.CLUBS,eRank.TWO));
		hp.AddCard(new Card(eSuit.CLUBS,eRank.TWO));
		hp.AddCard(new Card(eSuit.CLUBS,eRank.THREE));
		
		HandScorePoker HSP = (HandScorePoker) hp.ScoreHand();
		
		//System.out.println(HSP.geteHandStrength().toString());
		
		assertEquals("Four of a Kind",HSP.geteHandStrength().toString());
		assertEquals(eRank.TWO,HSP.getHiCard().geteRank());
		
		assertEquals(new Card(eSuit.CLUBS,eRank.THREE).geteRank(),HSP.getKickers().get(0).geteRank());
		
		
		
		
	}

	@Test
	public void PAIR1() {
		
		HandPoker hp = new HandPoker();
		hp.AddCard(new Card(eSuit.CLUBS,eRank.FOUR));
		hp.AddCard(new Card(eSuit.CLUBS,eRank.TWO));
		hp.AddCard(new Card(eSuit.SPADES,eRank.THREE));
		hp.AddCard(new Card(eSuit.CLUBS,eRank.FIVE));
		hp.AddCard(new Card(eSuit.HEARTS,eRank.TWO));
		
		System.out.println(hp.getCards().size());
		

		HandScorePoker HSP = (HandScorePoker) hp.ScoreHand();
		
		System.out.println(HSP.geteHandStrength().toString());
		assertEquals("One Pair",HSP.geteHandStrength().toString());
		
		System.out.println(HSP.getHiCard().geteRank());
		assertEquals(eRank.TWO,HSP.getHiCard().geteRank());
		
		System.out.println(HSP.getKickers().get(0).geteRank());
		assertEquals(new Card(eSuit.CLUBS,eRank.FIVE).geteRank(),HSP.getKickers().get(0).geteRank());
		assertEquals(new Card(eSuit.CLUBS,eRank.FOUR).geteRank(),HSP.getKickers().get(1).geteRank());
		assertEquals(new Card(eSuit.CLUBS,eRank.THREE).geteRank(),HSP.getKickers().get(2).geteRank());
		
		
		
		
	}
	
	@Test
	public void TWOPAIR1() {
		System.out.println("TEST 3");
		HandPoker hp = new HandPoker();
		hp.AddCard(new Card(eSuit.CLUBS,eRank.TWO));
		hp.AddCard(new Card(eSuit.CLUBS,eRank.THREE));
		hp.AddCard(new Card(eSuit.SPADES,eRank.TWO));
		hp.AddCard(new Card(eSuit.CLUBS,eRank.FIVE));
		hp.AddCard(new Card(eSuit.CLUBS,eRank.THREE));
		
		HandScorePoker HSP = (HandScorePoker) hp.ScoreHand();
		
		if(HSP.geteHandStrength() == null) {System.out.println("NULL");}
		
		if(hp.getCRC() != null) {System.out.println("NOT NULL");}
		
		System.out.println(HSP.geteHandStrength().toString());
		assertEquals("Two Pairs",HSP.geteHandStrength().toString());
		System.out.println(HSP.getHiCard().geteRank());
		assertEquals(eRank.THREE,HSP.getHiCard().geteRank());
		System.out.println(HSP.getLoCard().geteRank());
		assertEquals(eRank.TWO,HSP.getLoCard().geteRank());
		System.out.println(HSP.getKickers().get(0).geteRank());
		assertEquals(new Card(eSuit.CLUBS,eRank.FIVE).geteRank(),HSP.getKickers().get(0).geteRank());
		
		
		
		
	}
	
	@Test
	public void THREEOFAKIND1() {
		
		HandPoker hp = new HandPoker();
		hp.AddCard(new Card(eSuit.CLUBS,eRank.TWO));
		hp.AddCard(new Card(eSuit.CLUBS,eRank.TWO));
		hp.AddCard(new Card(eSuit.SPADES,eRank.FIVE));
		hp.AddCard(new Card(eSuit.CLUBS,eRank.TWO));
		hp.AddCard(new Card(eSuit.CLUBS,eRank.THREE));
		
		HandScorePoker HSP = (HandScorePoker) hp.ScoreHand();
		
		//System.out.println(HSP.geteHandStrength().toString());
		
		assertEquals("Three of a Kind",HSP.geteHandStrength().toString());
		assertEquals(eRank.TWO,HSP.getHiCard().geteRank());
		
		assertEquals(new Card(eSuit.CLUBS,eRank.FIVE).geteRank(),HSP.getKickers().get(0).geteRank());
		assertEquals(new Card(eSuit.CLUBS,eRank.THREE).geteRank(),HSP.getKickers().get(1).geteRank());
		
		
		
		
	}

	@Test
	public void FULLHOUSE() {
		System.out.println("TEST 4");
		HandPoker hp = new HandPoker();
		hp.AddCard(new Card(eSuit.CLUBS,eRank.TWO));
		hp.AddCard(new Card(eSuit.CLUBS,eRank.THREE));
		hp.AddCard(new Card(eSuit.SPADES,eRank.TWO));
		hp.AddCard(new Card(eSuit.CLUBS,eRank.TWO));
		hp.AddCard(new Card(eSuit.CLUBS,eRank.THREE));
		
		HandScorePoker HSP = (HandScorePoker) hp.ScoreHand();
		
		if(HSP.geteHandStrength() == null) {System.out.println("NULL");}
		//Frequency();
		if(hp.getCRC() != null) {System.out.println("NOT NULL");}
		
		System.out.println(HSP.geteHandStrength().toString());
		assertEquals("Full House",HSP.geteHandStrength().toString());
		System.out.println(HSP.getHiCard().geteRank());
		assertEquals(eRank.TWO,HSP.getHiCard().geteRank());
		System.out.println(HSP.getLoCard().geteRank());
		assertEquals(eRank.THREE,HSP.getLoCard().geteRank());
		
		
		
		
		
	}
	
	
	
}
