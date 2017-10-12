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

	private ArrayList<CardRankCount> CRC = null;

	public HandPoker() {
		this.setHS(new HandScorePoker());
	}

	protected ArrayList<CardRankCount> getCRC() {
		return CRC;
	}

	protected void setCards(ArrayList<Card> cards) {
		super.setCards(cards);

	}

	protected HandScore getHandScore() {
		return (HandScore) super.getHS();
	}

	@Override
	public HandScore ScoreHand() {
		// TODO : Implement this method... call each of the 'is' methods (isRoyalFlush,
		// etc) until
		// one of the hands is true, then score the hand

		Collections.sort(super.getCards());
		Frequency();
		HandScore HS = this.getHS();

		try {

			// c = structure of class 'Hand'
			Class<?> c = Class.forName("pkgCore.HandPoker");

			// Create an instance of Hand
			Object inst = c.newInstance();
			ArrayList<Card> cardsInHand = this.getCards();

			Method mSetHand = c.getDeclaredMethod("setCards", ArrayList.class);
			mSetHand.invoke(inst, cardsInHand);

			for (eHandStrength eHS : eHandStrength.values()) {

				Method mEval = c.getDeclaredMethod(eHS.getEvalMethod(), null);
				// Make the private method accessible
				mEval.setAccessible(true);
				if ((boolean) mEval.invoke(inst, null)) {
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

	private void Frequency() {

		CRC = new ArrayList<CardRankCount>();

		int iCnt = 0;
		int iPos = 0;

		for (eRank eRank : eRank.values()) {
			iCnt = (CountRank(eRank));
			if (iCnt > 0) {
				iPos = FindCardRank(eRank);
				CRC.add(new CardRankCount(eRank, iCnt, iPos));
			}
		}

		Collections.sort(CRC);

		for (CardRankCount crcount : CRC) {
			System.out.print(crcount.getiCnt());
			System.out.print(" ");
			System.out.print(crcount.geteRank());
			System.out.print(" ");
			System.out.println(crcount.getiCardPosition());
		}

	}

	private int CountRank(eRank eRank) {
		int iCnt = 0;
		for (Card c : super.getCards()) {
			if (c.geteRank() == eRank) {
				iCnt++;
			}
		}
		return iCnt;
	}

	private int FindCardRank(eRank eRank) {
		int iPos = 0;
		for (iPos = 0; iPos < super.getCards().size(); iPos++) {
			if (super.getCards().get(iPos).geteRank() == eRank) {
				break;
			}
		}
		return iPos;
	}

	public boolean isRoyalFlush() {
		boolean bIsRoyalFlush = false;
		if (super.getCards().get(eCardNo.FIRST.getiCardNo()).geteRank() == eRank.TEN && isStraightFlush()) {
			bIsRoyalFlush = true;
			HandScorePoker HSP = (HandScorePoker) this.getHS();
			HSP.seteHandStrength(eHandStrength.RoyalFlush);
		}
		return bIsRoyalFlush;
	}

	public boolean isStraightFlush() {
		boolean bisStraightFlush = false;
		if (isStraight() && isFlush()) {
			bisStraightFlush = true;
			HandScorePoker HSP = (HandScorePoker) this.getHS();
			HSP.seteHandStrength(eHandStrength.StraightFlush);
		}
		return bisStraightFlush;
	}
	
	public boolean isFourOfAKind() {
		System.out.println("Running 3");
		boolean bisFourOfAKind = false;
		//System.out.println("SIZE");
		// System.out.println(this.getCRC().size());
		if (this.getCRC() == null) {
			System.out.println("NULL");
		}
		Frequency();
		if (this.getCRC() == null) {
			System.out.println("STILL NULL");
		}
		if (this.getCRC().size() == 2) {
			if (this.getCRC().get(0).getiCnt() == 4) {
				bisFourOfAKind = true;
				HandScorePoker HSP = (HandScorePoker) this.getHS();
				HSP.seteHandStrength(eHandStrength.FourOfAKind);

				int iGetCard = this.getCRC().get(0).getiCardPosition();

				HSP.setHiCard(this.getCards().get(iGetCard));
				HSP.setLoCard(null);

				HSP.setKickers(FindTheKickers(this.getCRC()));

				this.setHS(HSP);

			}
		}
		return bisFourOfAKind;
	}
	
		
	

	public boolean isFullHouse() {
		System.out.println("Running Full");
		boolean bisFullHouse = false;
		Frequency();

		if ((this.getCRC().get(0).getiCnt() == 3) && (this.getCRC().get(1).getiCnt() == 2)) {

			HandScorePoker HSP = (HandScorePoker) this.getHS();
			HSP.seteHandStrength(eHandStrength.FullHouse);
			bisFullHouse = true;

			int iGetCard = this.getCRC().get(0).getiCardPosition();

			HSP.setHiCard(this.getCards().get(iGetCard));
			HSP.setLoCard(this.getCards().get(this.getCRC().get(1).getiCardPosition()));

			HSP.setKickers(FindTheKickers(this.getCRC()));

			this.setHS(HSP);
		}
		return bisFullHouse;
	}

 public boolean isFlush() {
		boolean bisFlush = false;
		
		int i = 0;
		
		for (;i<super.getCards().size()-1;i++) {
			if (super.getCards().get(i).geteSuit() 
				== super.getCards().get(i+1).geteSuit()) {
				bisFlush = true;
			}
			else {
				return bisFlush = false;
			}
			
		}
		
		HandScorePoker HSP = (HandScorePoker) this.getHS();
		HSP.seteHandStrength(eHandStrength.Flush);
	
	
		return bisFlush;
 }
 

	public boolean isStraight() {
		boolean bisStraight = false;

		int i = 0;
		if (this.getCards().get(0).geteRank()==eRank.ACE 
		&& (this.getCards().get(1).geteRank()==eRank.FIVE)) {
			i=1;
		
		}
		for (;i<this.getCards().size()-1;i++) {
			if (((this.getCards().get(i).geteRank().getiRankNbr()) 
				- (this.getCards().get(i+1).geteRank().getiRankNbr())) == -1) {
				bisStraight = true;
			}
			else {
				return bisStraight =false;
			}
			
		}
			HandScorePoker HSP = (HandScorePoker) this.getHS();
			HSP.seteHandStrength(eHandStrength.Straight);
			
		return bisStraight;
	}
	
	
	public boolean isThreeOfAKind() {
		System.out.println("Running 3");
		boolean bisThreeOfAKind = false;
		//System.out.println("SIZE");
		// System.out.println(this.getCRC().size());
		if (this.getCRC() == null) {
			System.out.println("NULL");
		}
		Frequency();
		if (this.getCRC() == null) {
			System.out.println("STILL NULL");
		}
		if (this.getCRC().size() == 3) {
			if (this.getCRC().get(0).getiCnt() == 3) {
				bisThreeOfAKind = true;
				HandScorePoker HSP = (HandScorePoker) this.getHS();
				HSP.seteHandStrength(eHandStrength.ThreeOfAKind);

				int iGetCard = this.getCRC().get(0).getiCardPosition();

				HSP.setHiCard(this.getCards().get(iGetCard));
				HSP.setLoCard(null);

				HSP.setKickers(FindTheKickers(this.getCRC()));

				this.setHS(HSP);

			}
		}
		return bisThreeOfAKind;
	}

	public boolean isTwoPair() {
		System.out.println("Running 2");
		boolean bisTwoPair = false;
		Frequency();
		System.out.println(this.getCRC().size());
		if (this.getCRC().size() == 3) {
			if ((this.getCRC().get(0).getiCnt() == 2) && (this.getCRC().get(1).getiCnt() == 2)) {

				HandScorePoker HSP = (HandScorePoker) this.getHS();
				HSP.seteHandStrength(eHandStrength.TwoPair);
				bisTwoPair = true;

				int iGetCard = this.getCRC().get(0).getiCardPosition();

				HSP.setHiCard(this.getCards().get(iGetCard));
				HSP.setLoCard(this.getCards().get(this.getCRC().get(1).getiCardPosition()));

				HSP.setKickers(FindTheKickers(this.getCRC()));

				this.setHS(HSP);

			}
		}

		return bisTwoPair;
	}

	public boolean isPair() {
		boolean bisPair = false;

		if (this.getCRC().size() == 4) {
			if (this.getCRC().get(0).getiCnt() == 2) {

				HandScorePoker HSP = (HandScorePoker) this.getHS();
				HSP.seteHandStrength(eHandStrength.Pair);
				bisPair = true;

				int iGetCard = this.getCRC().get(0).getiCardPosition();

				HSP.setHiCard(this.getCards().get(iGetCard));
				HSP.setLoCard(null);

				HSP.setKickers(FindTheKickers(this.getCRC()));

				this.setHS(HSP);

			}
		}

		// TODO : Implement this method
		return bisPair;
	}

	public boolean isHighCard() {
		boolean bisHighCard = true;
		// TODO : Implement this method
		HandScorePoker HSP = (HandScorePoker) this.getHS();
		HSP.seteHandStrength(eHandStrength.HighCard);
		return bisHighCard;

	}

	private ArrayList<Card> FindTheKickers(ArrayList<CardRankCount> CRC) {
		ArrayList<Card> kickers = new ArrayList<Card>();

		for (CardRankCount crcCheck : CRC) {
			if (crcCheck.getiCnt() == 1) {
				kickers.add(this.getCards().get(crcCheck.getiCardPosition()));
			}
		}

		return kickers;
	}

}
