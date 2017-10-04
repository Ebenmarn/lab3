package pkgCore;

import static org.junit.Assert.*;

import org.junit.Test;

import pkgEnum.eRank;
import pkgEnum.eSuit;

public class HandPokerTest {

	@Test
	public void Hand1() {
		
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
	public void Hand2() {
		
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
	
	
	
}
