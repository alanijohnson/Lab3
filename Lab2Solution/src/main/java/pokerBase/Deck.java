package pokerBase;

import java.util.ArrayList;
import java.util.Collections;

import pokerEnums.eRank;
import pokerEnums.eSuit;
import pokerExceptions.DeckException;

public class Deck {

	private ArrayList<Card> deckCards = new ArrayList<Card>();

	public Deck() {
		int iCardNbr = 1;
		for (eSuit eSuit : eSuit.values()) {
			for (eRank eRank : eRank.values()) {
				// TODO Lab3 - Fix this
				if ((eSuit != eSuit.JOKER) && (eRank != eRank.JOKER)) {
					deckCards.add(new Card(eSuit, eRank, iCardNbr++));
				}
			}
		}
		Collections.shuffle(deckCards);
	}

	public Deck(int NbrOfJokers) {
		// TODO Lab3 - Implement joker constructor
		this();
		for (int i = 0; i < NbrOfJokers; i++) {
			deckCards.add(new Card(eSuit.JOKER, eRank.JOKER, 99));
		}
		Collections.shuffle(deckCards);
	}

	public Deck(int NbrOfJokers, ArrayList<Card> Wilds) {
		this(NbrOfJokers);
		// TODO Lab3 - Implement joker and wild constructor
		for (Card WildCard : Wilds) {
			for (Card DeckCard : deckCards) {
				if ((DeckCard.geteRank() == WildCard.geteRank()) && DeckCard.geteSuit() == WildCard.geteSuit()) {
					DeckCard.setbWild(true);
				}
			}
		}

	}

	public Card Draw() throws DeckException {
		// TODO Lab 3 - Implement exception handling for overdraw
		if (deckCards.size() < 1) {
			throw new DeckException();
		} else {
			return deckCards.remove(0);
		}
	}

	public ArrayList<Card> getCardsinDeck() {
		return deckCards;
	}

	
}
