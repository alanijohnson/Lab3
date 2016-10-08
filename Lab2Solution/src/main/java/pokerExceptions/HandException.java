package pokerExceptions;

import pokerBase.Hand;

public class HandException extends Exception{
	
	public HandException(Hand hand){
		super("Not enough cards in hand: "+ hand.hashCode());
	}
}
