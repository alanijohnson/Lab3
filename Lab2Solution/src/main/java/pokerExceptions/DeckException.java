package pokerExceptions;

import pokerBase.Card;
import pokerBase.Deck.DeckException;

public class DeckException {
	class DeckException extends Exception {

		public Card Draw() throws DeckException {
			if (deckCards.size() < 1)
				throw new DeckException();
			return deckCards.remove(0);
		}
	}
}
