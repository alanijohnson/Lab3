package pokerBase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import pokerEnums.eCardNo;
import pokerEnums.eHandExceptionType;
import pokerEnums.eHandStrength;
import pokerEnums.eRank;
import pokerEnums.eSuit;
import pokerExceptions.HandException;
import pokerExceptions.exHand;

public class Hand {

	private ArrayList<Card> CardsInHand = new ArrayList<Card>();
	private boolean bScored;
	private HandScore hs;

	public Hand() {

	}

	private ArrayList<Card> getCardsInHand() {
		return CardsInHand;
	}

	void AddToCardsInHand(Card c) {
		CardsInHand.add(c);
	}

	public boolean isbScored() {
		return bScored;
	}

	public HandScore getHs() {
		return hs;
	}

	public void EvaulateHand() {
		try {
			Hand h = EvaluateHand(this);
			h.hs = h.getHs();
			h.bScored = h.bScored;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Hand PickBestHand(ArrayList<Hand> Hands) throws exHand {
		Collections.sort(Hands, Hand.HandRank);
		if (Hands.size() > 1) {
			if (Hands.get(0).getHs().getHandStrength() == Hands.get(1).getHs().getHandStrength()) {
				if (Hands.get(0).getHs().getHiHand() == Hands.get(1).getHs().getHiHand()) {
					if (Hands.get(0).getHs().getLoHand() == Hands.get(1).getHs().getLoHand()) {
						if (Hands.get(0).getHs().getKickers().size() == 0) {
							throw new exHand();
						} else {
							Collections.sort(Hands.get(0).getHs().getKickers(), Card.CardRank);
							Collections.sort(Hands.get(1).getHs().getKickers(), Card.CardRank);

							for (int i = 0; i < Hands.get(0).getHs().getKickers().size(); i++) {
								if (Hands.get(0).getHs().getKickers().get(i).geteRank() != Hands.get(1).getHs()
										.getKickers().get(i).geteRank()) {
									return Hands.get(0);
								}
							}
							throw new exHand();
						}
					} else {
						return Hands.get(0);
					}
				} else {
					return Hands.get(0);
				}
			} else {
				return Hands.get(0);
			}
		} else {
			return Hands.get(0);
		}
	}

	/**
	 * <b>EvaluateHand</b> is a static method that will score a given Hand of
	 * cards
	 * 
	 * @param h
	 * @return
	 * @throws HandException
	 */
	static Hand EvaluateHand(Hand h) throws HandException {

		// Sort the colleciton (by hand rank)
		// Collections.sort(h.getCardsInHand());

		// TODO - Lab 3 Here's the code to throw the HandException
		// TODO - Implement HandException
		/*
		 * if (h.getCardsInHand().size() != 5) { throw new
		 * HandException(h,eHandExceptionType.ShortHand); }
		 */

		ArrayList<Hand> ExplodedHands = new ArrayList<Hand>();
		ExplodedHands.add(h);

		ExplodedHands = ExplodeHands(ExplodedHands);

		for (Hand hEval : ExplodedHands) {

			if (hEval.getCardsInHand().size() != 5) {
				throw new HandException(hEval);
			}
			HandScore hs = new HandScore();
			try {
				Class<?> c = Class.forName("pokerBase.Hand");

				for (eHandStrength hstr : eHandStrength.values()) {
					Class[] cArg = new Class[2];
					cArg[0] = pokerBase.Hand.class;
					cArg[1] = pokerBase.HandScore.class;

					Method meth = c.getMethod(hstr.getEvalMethod(), cArg);
					Object o = meth.invoke(null, new Object[] { hEval, hs });

					// If o = true, that means the hand evaluated- skip the rest
					// of
					// the evaluations
					if ((Boolean) o) {
						break;
					}
				}

				hEval.bScored = true;
				hEval.hs = hs;

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
				e.printStackTrace();
			}
		}

		// TODO - Lab 3. ExplodedHands has a bunch of hands.
		// TODO need comparator for sorting hands
		Collections.sort(ExplodedHands, Hand.HandRank);
		h.bScored = true;
		h.hs = ExplodedHands.get(0).getHs();
		// Either 1, 52, 2
		// return a
		return ExplodedHands.get(0);
	}

	/**
	 * 
	 * @param h
	 * @param hs
	 * @return
	 */
	private static ArrayList<Hand> ExplodeHands(ArrayList<Hand> Hands) {
		// TODO - Lab3 Implement this
		Hand OriginalHand = Hands.get(0);
		// Hand tempHand = OriginalHand;
		ArrayList<Hand> ExplodedHands = new ArrayList<Hand>();
		Deck StandardDeck = new Deck();

		ExplodedHands.add(Hands.get(0));

		for (int card = 0; card < 5; card++) {
			ExplodedHands = SubstituteCard(card, ExplodedHands);
		}
		
		return ExplodedHands;
	}

	private static ArrayList<Hand> SubstituteCard(int CardPosition, ArrayList<Hand> hands) {
		ArrayList<Hand> CreatedHands = new ArrayList<Hand>();
		Deck StandardDeck = new Deck();

		for (Hand h : hands) {
			if (h.getCardsInHand().get(CardPosition).geteRank() == pokerEnums.eRank.JOKER
					|| h.getCardsInHand().get(CardPosition).isbWild()) {
				for (Card StandardCard : StandardDeck.getCardsinDeck()) {
					Hand newHand = new Hand();
					for (int position = 0; position < 5; position++) {
						if (CardPosition == position) {
							newHand.AddToCardsInHand(StandardCard);
						} else {
							newHand.AddToCardsInHand(h.getCardsInHand().get(position));
						}
					}
					CreatedHands.add(newHand);
				}
			} else {
				CreatedHands.add(h);
			}
		}
		return CreatedHands;
	}

	/*
	 * for (Card card1 : StandardDeck.getCardsinDeck()) { // set value of card1
	 * if
	 * (OriginalHand.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).isbWild
	 * () || OriginalHand
	 * .getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() ==
	 * eRank.JOKER) {
	 * tempHand.getCardsInHand().set(eCardNo.FirstCard.getCardNo(), card1); }
	 * 
	 * for (Card card2 : StandardDeck.getCardsinDeck()) { // set value of card2
	 * if (OriginalHand.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).
	 * isbWild() ||
	 * OriginalHand.getCardsInHand().get(eCardNo.SecondCard.getCardNo())
	 * .geteRank()== eRank.JOKER) {
	 * tempHand.getCardsInHand().set(eCardNo.SecondCard.getCardNo(), card2); }
	 * 
	 * for (Card card3 : StandardDeck.getCardsinDeck()) { // set value of card3
	 * if
	 * (OriginalHand.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).isbWild
	 * () || OriginalHand.getCardsInHand().get(eCardNo.ThirdCard.getCardNo())
	 * .geteRank() == eRank.JOKER) {
	 * tempHand.getCardsInHand().set(eCardNo.ThirdCard.getCardNo(), card3); }
	 * 
	 * for (Card card4 : StandardDeck.getCardsinDeck()) { // set value of card4
	 * if (OriginalHand.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).
	 * isbWild() ||
	 * OriginalHand.getCardsInHand().get(eCardNo.FourthCard.getCardNo())
	 * .geteRank() == eRank.JOKER) {
	 * tempHand.getCardsInHand().set(eCardNo.FourthCard.getCardNo(), card4); }
	 * 
	 * for (Card card5 : StandardDeck.getCardsinDeck()) { // set value of card5
	 * if
	 * (OriginalHand.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).isbWild
	 * () ||
	 * OriginalHand.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank
	 * () == eRank.JOKER) {
	 * tempHand.getCardsInHand().set(eCardNo.FifthCard.getCardNo(), card5); }
	 * 
	 * ExplodedHands.add(tempHand);
	 * 
	 * } } } }
	 * 
	 * }
	 * 
	 * if (ExplodedHands.isEmpty()) { ExplodedHands.add(OriginalHand); }
	 * 
	 * return ExplodedHands; }
	 */

	public static boolean isHandFiveOfAKind(Hand h, HandScore hs) {
		boolean isFiveOfAKind = false;

		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			isFiveOfAKind = true;
			hs.setHandStrength(eHandStrength.FiveOfAKind.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);

		}
		return isFiveOfAKind;

	}

	public static boolean isHandRoyalFlush(Hand h, HandScore hs) {

		Card c = new Card();
		boolean isRoyalFlush = false;
		if ((isHandFlush(h.getCardsInHand())) && (isStraight(h.getCardsInHand(), c))) {
			if (c.geteRank() == eRank.ACE) {
				isRoyalFlush = true;
				hs.setHandStrength(eHandStrength.RoyalFlush.getHandStrength());
				hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
				hs.setLoHand(0);
			}
		}
		return isRoyalFlush;
	}

	/**
	 * isHandStraightFlush - Will return true if the hand is a straight flush
	 * 
	 * @param h
	 * @param hs
	 * @return
	 */
	public static boolean isHandStraightFlush(Hand h, HandScore hs) {
		Card c = new Card();
		boolean isRoyalFlush = false;
		if ((isHandFlush(h.getCardsInHand())) && (isStraight(h.getCardsInHand(), c))) {
			isRoyalFlush = true;
			hs.setHandStrength(eHandStrength.StraightFlush.getHandStrength());
			hs.setHiHand(c.geteRank().getiRankNbr());
			hs.setLoHand(0);
		}

		return isRoyalFlush;
	}

	/**
	 * isHandFourOfAKind - this method will determine if the hand is a four of a
	 * kind
	 * 
	 * @param h
	 * @param hs
	 * @return
	 */
	public static boolean isHandFourOfAKind(Hand h, HandScore hs) {

		boolean bHandCheck = false;

		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.FourOfAKind.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setKickers(kickers);

		} else if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			bHandCheck = true;
			hs.setHandStrength(eHandStrength.FourOfAKind.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			hs.setKickers(kickers);
		}

		return bHandCheck;
	}

	/**
	 * isHandFullHouse - This method will determine if the hand is a full house
	 * 
	 * @param h
	 * @param hs
	 * @return
	 */
	public static boolean isHandFullHouse(Hand h, HandScore hs) {

		boolean isFullHouse = false;
		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;
			hs.setHandStrength(eHandStrength.FullHouse.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr());
		} else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;
			hs.setHandStrength(eHandStrength.FullHouse.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
		}

		return isFullHouse;

	}

	public static boolean isHandFlush(Hand h, HandScore hs) {

		boolean bIsFlush = false;
		if (isHandFlush(h.getCardsInHand())) {
			hs.setHandStrength(eHandStrength.Flush.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setKickers(kickers);
			bIsFlush = true;
		}

		return bIsFlush;
	}

	public static boolean isHandFlush(ArrayList<Card> cards) {
		int cnt = 0;
		boolean bIsFlush = false;
		for (eSuit Suit : eSuit.values()) {
			cnt = 0;
			for (Card c : cards) {
				if (c.geteSuit() == Suit) {
					cnt++;
				}
			}
			if (cnt == 5)
				bIsFlush = true;

		}
		return bIsFlush;
	}

	public static boolean isStraight(ArrayList<Card> cards, Card highCard) {
		boolean bIsStraight = false;
		boolean bAce = false;

		int iStartCard = 0;
		highCard.seteRank(cards.get(eCardNo.FirstCard.getCardNo()).geteRank());
		highCard.seteSuit(cards.get(eCardNo.FirstCard.getCardNo()).geteSuit());

		if (cards.get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.ACE) {
			// First card is an 'ace', handle aces
			bAce = true;
			iStartCard++;
		}

		for (int a = iStartCard; a < cards.size() - 1; a++) {
			if ((cards.get(a).geteRank().getiRankNbr() - cards.get(a + 1).geteRank().getiRankNbr()) == 1) {
				bIsStraight = true;
			} else {
				bIsStraight = false;
				break;
			}
		}

		if ((bAce) && (bIsStraight)) {
			if (cards.get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.KING) {
				highCard.seteRank(cards.get(eCardNo.FirstCard.getCardNo()).geteRank());
				highCard.seteSuit(cards.get(eCardNo.FirstCard.getCardNo()).geteSuit());
			} else if (cards.get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.FIVE) {
				highCard.seteRank(cards.get(eCardNo.SecondCard.getCardNo()).geteRank());
				highCard.seteSuit(cards.get(eCardNo.SecondCard.getCardNo()).geteSuit());
			} else {
				bIsStraight = false;
			}
		}
		return bIsStraight;
	}

	public static boolean isHandStraight(Hand h, HandScore hs) {

		boolean bIsStraight = false;
		Card highCard = new Card();
		if (isStraight(h.getCardsInHand(), highCard)) {
			hs.setHandStrength(eHandStrength.Straight.getHandStrength());
			hs.setHiHand(highCard.geteRank().getiRankNbr());
			hs.setLoHand(0);
			bIsStraight = true;
		}
		return bIsStraight;
	}

	public static boolean isHandThreeOfAKind(Hand h, HandScore hs) {

		boolean isThreeOfAKind = false;
		ArrayList<Card> kickers = new ArrayList<Card>();
		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank()) {
			isThreeOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
		} else if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank()) {
			isThreeOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));

		} else if (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			isThreeOfAKind = true;
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr());
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));

		}

		if (isThreeOfAKind) {
			hs.setHandStrength(eHandStrength.ThreeOfAKind.getHandStrength());
			hs.setLoHand(0);
			hs.setKickers(kickers);
		}

		return isThreeOfAKind;
	}

	public static boolean isHandTwoPair(Hand h, HandScore hs) {

		boolean isTwoPair = false;
		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FourthCard.getCardNo()).geteRank())) {
			isTwoPair = true;
			hs.setHandStrength(eHandStrength.TwoPair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr());
			kickers.add(h.getCardsInHand().get((eCardNo.FifthCard.getCardNo())));
			hs.setKickers(kickers);
		} else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isTwoPair = true;
			hs.setHandStrength(eHandStrength.TwoPair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr());
			kickers.add(h.getCardsInHand().get((eCardNo.ThirdCard.getCardNo())));
			hs.setKickers(kickers);
		} else if ((h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isTwoPair = true;
			hs.setHandStrength(eHandStrength.TwoPair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr());
			kickers.add(h.getCardsInHand().get((eCardNo.FirstCard.getCardNo())));
			hs.setKickers(kickers);
		}
		return isTwoPair;
	}

	public static boolean isHandPair(Hand h, HandScore hs) {
		boolean isPair = false;
		ArrayList<Card> kickers = new ArrayList<Card>();
		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank()) {
			isPair = true;
			hs.setHandStrength(eHandStrength.Pair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			kickers.add(h.getCardsInHand().get((eCardNo.ThirdCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.FourthCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.FifthCard.getCardNo())));
			hs.setKickers(kickers);
		} else if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank()) {
			isPair = true;
			hs.setHandStrength(eHandStrength.Pair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			kickers.add(h.getCardsInHand().get((eCardNo.FirstCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.FourthCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.FifthCard.getCardNo())));
			hs.setKickers(kickers);
		} else if (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank()) {
			isPair = true;
			hs.setHandStrength(eHandStrength.Pair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			kickers.add(h.getCardsInHand().get((eCardNo.FirstCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.SecondCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.FifthCard.getCardNo())));
			hs.setKickers(kickers);
		} else if (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
			isPair = true;
			hs.setHandStrength(eHandStrength.Pair.getHandStrength());
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr());
			hs.setLoHand(0);
			kickers.add(h.getCardsInHand().get((eCardNo.FirstCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.SecondCard.getCardNo())));
			kickers.add(h.getCardsInHand().get((eCardNo.ThirdCard.getCardNo())));
			hs.setKickers(kickers);
		}
		return isPair;
	}

	public static boolean isHandHighCard(Hand h, HandScore hs) {
		hs.setHandStrength(eHandStrength.HighCard.getHandStrength());
		hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr());
		hs.setLoHand(0);
		ArrayList<Card> kickers = new ArrayList<Card>();
		kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
		kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
		kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
		kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
		hs.setKickers(kickers);
		return true;
	}

	public static Comparator<Hand> HandRank = new Comparator<Hand>() {

		public int compare(Hand h1, Hand h2) {

			int result = 0;

			result = h2.getHs().getHandStrength() - h1.getHs().getHandStrength();

			if (result != 0) {
				return result;
			}

			result = h2.getHs().getHiHand() - h1.getHs().getHiHand();
			if (result != 0) {
				return result;
			}

			result = h2.getHs().getLoHand() - h1.getHs().getLoHand();
			if (result != 0) {
				return result;
			}

			if (h2.getHs().getKickers().size() > 0) {
				if (h1.getHs().getKickers().size() > 0) {
					result = h2.getHs().getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr()
							- h1.getHs().getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr();
				}
				if (result != 0) {
					return result;
				}
			}

			if (h2.getHs().getKickers().size() > 1) {
				if (h1.getHs().getKickers().size() > 1) {
					result = h2.getHs().getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr()
							- h1.getHs().getKickers().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr();
				}
				if (result != 0) {
					return result;
				}
			}

			if (h2.getHs().getKickers().size() > 2) {
				if (h1.getHs().getKickers().size() > 2) {
					result = h2.getHs().getKickers().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr()
							- h1.getHs().getKickers().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr();
				}
				if (result != 0) {
					return result;
				}
			}

			if (h2.getHs().getKickers().size() > 3) {
				if (h1.getHs().getKickers().size() > 3) {
					result = h2.getHs().getKickers().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr()
							- h1.getHs().getKickers().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr();
				}
				if (result != 0) {
					return result;
				}
			}
			return 0;
		}
	};

}
