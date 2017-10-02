package pkgCore;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

import pkgEnum.eCardNo;
import pkgEnum.eHandStrength;
import pkgEnum.eRank;
import pkgEnum.eSuit;

public class HandPoker extends Hand {

	public HandPoker() {
		this.setHS(new HandScorePoker());
	}

	protected void setCards(ArrayList<Card> cards)
	{
		super.setCards(cards);
		
	}
	
	protected HandScore getHandScore()
	{
		return (HandScore) super.getHS();
	}
	
	@Override
	public HandScore ScoreHand() {
		// TODO : Implement this method... call each of the 'is' methods (isRoyalFlush,
		// etc) until
		// one of the hands is true, then score the hand

		Collections.sort(super.getCards());
		HandScore HS = this.getHS();

		try {
			
			//	c = structure of class 'Hand'
			Class<?> c = Class.forName("pkgCore.HandPoker");
			
			//	Create an instance of Hand
			Object inst = c.newInstance();
			ArrayList<Card> cardsInHand = this.getCards();
			
			Method mSetHand = c.getDeclaredMethod("setCards", ArrayList.class);
			mSetHand.invoke(inst, cardsInHand);
			
			for (eHandStrength eHS : eHandStrength.values()) {
				
				Method mEval = c.getDeclaredMethod(eHS.getEvalMethod(), null);
				//	Make the private method accessible 
				mEval.setAccessible(true);
				if ((boolean) mEval.invoke(inst, null))
				{
					break;
				}
			}
			
			Method mGetHandScore = c.getDeclaredMethod("getHandScore", null);
			
			HS = (HandScore) mGetHandScore.invoke(inst, null);
			


		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		} catch (IllegalAccessException x) {
			x.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setHS((HandScorePoker) HS);
		return HS;
		
	}

	public boolean isRoyalFlush() {
		boolean bIsRoyalFlush = false;
		// TODO : Implement this method
		if (super.getCards().get(eCardNo.FIRST.getiCardNo()).geteRank()==eRank.TEN&&isStraightFlush())
		{bIsRoyalFlush = true;}
		return bIsRoyalFlush;
	}

	public boolean isStraightFlush() {
		boolean bisStraightFlush = false;
		if(isStraight()&&isFlush())
		{bisStraightFlush = true;}
		return bisStraightFlush;
	}

	public boolean isFourOfAKind() {
		boolean bisFourOfAKind = false;
		HandScorePoker HS = (HandScorePoker) super.getHS();

		if (super.getCards().get(eCardNo.FIRST.getiCardNo()).geteRank() == super.getCards()
				.get(eCardNo.FOURTH.getiCardNo()).geteRank()) {

		   HS.seteHandStrength(eHandStrength.FourOfAKind);
			HS.setHiCard(super.getCards().get(eCardNo.FIRST.getiCardNo()));
			HS.setLoCard(null);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(super.getCards().get(eCardNo.FIFTH.getiCardNo()));
			HS.setKickers(kickers);
			this.setHS(HS);
			
			bisFourOfAKind = true;

		} else if (super.getCards().get(eCardNo.SECOND.getiCardNo()).geteRank() == super.getCards()
				.get(eCardNo.FIFTH.getiCardNo()).geteRank()) {
			HS.seteHandStrength(eHandStrength.FourOfAKind);
			HS.setHiCard(super.getCards().get(eCardNo.SECOND.getiCardNo()));
			HS.setLoCard(null);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(super.getCards().get(eCardNo.FIRST.getiCardNo()));
			HS.setKickers(kickers);
			this.setHS(HS);
			bisFourOfAKind = true;
		}

		return bisFourOfAKind;
	}

	public boolean isFullHouse() {
		boolean bisFullHouse = false;
		// TODO : Implement this method
		if 		((super.getCards().get(eCardNo.FIRST.getiCardNo()).geteRank() == 
				super.getCards().get(eCardNo.SECOND.getiCardNo()).geteRank()&&
				super.getCards().get(eCardNo.THIRD.getiCardNo()).geteRank() == 
				super.getCards().get(eCardNo.FIFTH.getiCardNo()).geteRank())||
				(super.getCards().get(eCardNo.FOURTH.getiCardNo()).geteRank() == 
				super.getCards().get(eCardNo.FIFTH.getiCardNo()).geteRank()&&
				super.getCards().get(eCardNo.FIRST.getiCardNo()).geteRank() == 
				super.getCards().get(eCardNo.THIRD.getiCardNo()).geteRank())) {
			bisFullHouse = true;		
			
			
		}
		
		
		
		return bisFullHouse;
	}

	public boolean isFlush() {
		boolean bisFlush = false;

		int iCardCnt = super.getCards().size();
		int iSuitCnt = 0;

		for (eSuit eSuit : eSuit.values()) {
			for (Card c : super.getCards()) {
				if (eSuit == c.geteSuit()) {
					iSuitCnt++;
				}
			}
			if (iSuitCnt > 0)
				break;
		}

		if (iSuitCnt == iCardCnt)
			bisFlush = true;
		else
			bisFlush = false;

		return bisFlush;
	}

	public boolean isStraight() {
		boolean bisStraight = false;
		// TODO : Implement this method
		if (super.getCards().get(eCardNo.FIRST.getiCardNo()).geteRank().getiRankNbr()+5
		== super.getCards().get(eCardNo.FOURTH.getiCardNo()).geteRank().getiRankNbr()&&!isPair()) {
			
			bisStraight=true;
		}
		
		
		
		return bisStraight;
	}

	public boolean isThreeOfAKind() {
		boolean bisThreeOfAKind = false;
		// TODO : Implement this method
		return bisThreeOfAKind;
	}

	public boolean isTwoPair() {
		boolean bisTwoPair = false;
		// TODO : Implement this method
		if 		(((super.getCards().get(eCardNo.THIRD.getiCardNo()).geteRank() == 
				super.getCards().get(eCardNo.SECOND.getiCardNo()).geteRank())||
				(super.getCards().get(eCardNo.SECOND.getiCardNo()).geteRank() == 
				super.getCards().get(eCardNo.FIRST.getiCardNo()).geteRank()))&&
				((super.getCards().get(eCardNo.FOURTH.getiCardNo()).geteRank() == 
				super.getCards().get(eCardNo.THIRD.getiCardNo()).geteRank())||
				(super.getCards().get(eCardNo.FOURTH.getiCardNo()).geteRank() == 
				super.getCards().get(eCardNo.FIFTH.getiCardNo()).geteRank()))) {
			
			bisTwoPair=true;
		}
		return bisTwoPair;
	}

	public boolean isPair() {
		boolean bisPair = false;
		
		int iRankCnt = 0;

		for (eRank eRank : eRank.values()) {
			for (Card c : super.getCards()) {
				if (eRank == c.geteRank()) {
					iRankCnt++;
				}
			}
			if (iRankCnt == 2)
				bisPair = true;
		}
		
		
		// TODO : Implement this method
		return bisPair;
	}

	public boolean isHighCard() {
		boolean bisHighCard = false;
		// TODO : Implement this method
		return bisHighCard;
	}

}
