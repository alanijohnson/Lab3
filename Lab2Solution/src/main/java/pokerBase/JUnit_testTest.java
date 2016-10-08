package pokerBase;
 

import org.junit.Test;

import pokerEnums.eRank;
import pokerEnums.eSuit;
import pokerExceptions.DeckException;

import org.junit.Ignore;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

//Card Test
public class JUnit_testTest {
	
   Card card = new Card(eSuit.CLUBS,eRank.THREE);
   
   @Test
   public void test_Card() {

	assertEquals(card.geteRank(), eRank.THREE);
	assertEquals(card.geteSuit(), eSuit.CLUBS);
   }
   
// Deck Test
      
   @Test //test creation of deck and draw card
   public void test_Deck() throws DeckException{
	   Deck deck= new Deck();
	   assertEquals(deck.getCardsinDeck().size(),52);
	   deck.Draw();
	   assertEquals(deck.getCardsinDeck().size(), 51);
	   
   }  
   
   @Test(expected = pokerExceptions.DeckException.class) //test DeckException for drawing from empty deck
   public void test_DeckException() throws DeckException{
	   Deck deck= new Deck();
	   for (int i=0; i<52; i++){
		   deck.Draw();
	   }
	   deck.Draw();
   }
   
   @Test
   public void test_Deck_oneArg(){
	   Deck deck = new Deck(5);
	   assertEquals(deck.getCardsinDeck().size(),57);
	   int sum = 0;
	   for (Card card: deck.getCardsinDeck()){
		   if (card.geteRank() == pokerEnums.eRank.JOKER){
			   sum++;
		   }
	   }
	   assertEquals(5,sum);
   }
   
   @Test
   public void test_Deck_twoArg(){
	   ArrayList<Card> WildCards = new ArrayList<Card>();
	   Card card1 = new Card(pokerEnums.eSuit.DIAMONDS,pokerEnums.eRank.ACE);
	   Card card2 = new Card(pokerEnums.eSuit.SPADES,pokerEnums.eRank.EIGHT);
	   WildCards.add(card1);
	   WildCards.add(card2);
	   Deck deck = new Deck(5, WildCards);
	   assertEquals(deck.getCardsinDeck().size(),57);
	   int sum = 0;
	   for (Card card: deck.getCardsinDeck()){
		   if (card.geteRank() == pokerEnums.eRank.JOKER){
			   sum++;
		   }
	   }
	   int sum1 = 0;
	   for (Card card: deck.getCardsinDeck()){
		   if (card.isbWild() == true){
			   sum1++;
		   }
	   }
	   assertEquals(5,sum);
	   assertEquals(2,sum1);
   }
   
   //Handscore test
   @Test
   public void test_Hands(){
	   Hand hand1 = new Hand(); //high card
	   hand1.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand1.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.TWO));
	   hand1.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.THREE));
	   hand1.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.FIVE));
	   hand1.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.NINE));
	   
	   hand1.EvaulateHand();
	   
	   assertEquals(hand1.getHs().getHandStrength(),10);
	   
	   
	   Hand hand2 = new Hand(); //pair - first 2 cards
	   hand2.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand2.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.ACE));
	   hand2.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.TWO));
	   hand2.AddToCardsInHand(new Card(pokerEnums.eSuit.SPADES,pokerEnums.eRank.FIVE));
	   hand2.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.SEVEN));
	   
	   hand2.EvaulateHand();
	   assertEquals(hand2.getHs().getHandStrength(),20);
	   
	   Hand hand3 = new Hand(); //pair - second 2 cards
	   hand3.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand3.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.KING));
	   hand3.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.KING));
	   hand3.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.THREE));
	   hand3.AddToCardsInHand(new Card(pokerEnums.eSuit.SPADES,pokerEnums.eRank.TWO));
	   
	   hand3.EvaulateHand();
	   assertEquals(hand3.getHs().getHandStrength(),20);
	   
	   Hand hand4 = new Hand(); //pair - third 2 cards
	   hand4.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand4.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.KING));
	   hand4.AddToCardsInHand(new Card(pokerEnums.eSuit.SPADES,pokerEnums.eRank.SEVEN));
	   hand4.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.SEVEN));
	   hand4.AddToCardsInHand(new Card(pokerEnums.eSuit.DIAMONDS,pokerEnums.eRank.TWO));

	   hand4.EvaulateHand();
	   assertEquals(hand4.getHs().getHandStrength(),20);
	   
	   Hand hand5 = new Hand(); //pair - fourth 2 cards
	   hand5.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand5.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.NINE));
	   hand5.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.EIGHT));
	   hand5.AddToCardsInHand(new Card(pokerEnums.eSuit.DIAMONDS,pokerEnums.eRank.TWO));
	   hand5.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.TWO));

	   hand5.EvaulateHand();
	   assertEquals(hand5.getHs().getHandStrength(),20);
	   
	   Hand hand6 = new Hand(); //two pair - first two
	   hand6.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand6.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.ACE));
	   hand6.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.KING));
	   hand6.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.KING));
	   hand6.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.NINE));
	   
	   hand6.EvaulateHand();
	   assertEquals(hand6.getHs().getHandStrength(),30);
	   
	   Hand hand7 = new Hand(); //two pair - last 2
	   hand7.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand7.AddToCardsInHand(new Card(pokerEnums.eSuit.SPADES,pokerEnums.eRank.EIGHT));
	   hand7.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.EIGHT));
	   hand7.AddToCardsInHand(new Card(pokerEnums.eSuit.DIAMONDS,pokerEnums.eRank.TWO));
	   hand7.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.TWO));

	   hand7.EvaulateHand();
	   assertEquals(hand7.getHs().getHandStrength(),30);

	   Hand hand8 = new Hand(); //two pair - outer
	   hand8.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand8.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.ACE));
	   hand8.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.EIGHT));
	   hand8.AddToCardsInHand(new Card(pokerEnums.eSuit.DIAMONDS,pokerEnums.eRank.TWO));
	   hand8.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.TWO));

	   hand8.EvaulateHand();
	   assertEquals(hand8.getHs().getHandStrength(),30);
	   

	   Hand hand9 = new Hand(); //3 of a kind - first
	   hand9.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand9.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.ACE));
	   hand9.AddToCardsInHand(new Card(pokerEnums.eSuit.DIAMONDS,pokerEnums.eRank.ACE));
	   hand9.AddToCardsInHand(new Card(pokerEnums.eSuit.DIAMONDS,pokerEnums.eRank.THREE));
	   hand9.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.TWO));

	   hand9.EvaulateHand();
	   assertEquals(hand9.getHs().getHandStrength(),40);
	   

	   Hand hand10 = new Hand(); //3 of a kind - middle
	   hand10.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand10.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.NINE));
	   hand10.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.NINE));
	   hand10.AddToCardsInHand(new Card(pokerEnums.eSuit.DIAMONDS,pokerEnums.eRank.NINE));
	   hand10.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.TWO));

	   hand10.EvaulateHand();
	   assertEquals(hand10.getHs().getHandStrength(),40);
	   

	   Hand hand11 = new Hand(); //3 of a kind - last
	   hand11.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand11.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.NINE));
	   hand11.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.TWO));
	   hand11.AddToCardsInHand(new Card(pokerEnums.eSuit.DIAMONDS,pokerEnums.eRank.TWO));
	   hand11.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.TWO));

	   hand11.EvaulateHand();
	   assertEquals(hand11.getHs().getHandStrength(),40);
	   

	   Hand hand12 = new Hand(); //straight - Ace high
	   hand12.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand12.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.KING));
	   hand12.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.QUEEN));
	   hand12.AddToCardsInHand(new Card(pokerEnums.eSuit.DIAMONDS,pokerEnums.eRank.JACK));
	   hand12.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.TEN));

	   hand12.EvaulateHand();
	   assertEquals(hand12.getHs().getHandStrength(),50);
	   

	   Hand hand18 = new Hand(); //straight - ace low
	   hand18.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand18.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.FIVE));
	   hand18.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.FOUR));
	   hand18.AddToCardsInHand(new Card(pokerEnums.eSuit.DIAMONDS,pokerEnums.eRank.THREE));
	   hand18.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.TWO));

	   hand18.EvaulateHand();
	   assertEquals(hand18.getHs().getHandStrength(),50);

	   Hand hand13 = new Hand(); //flush
	   hand13.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand13.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.NINE));
	   hand13.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.EIGHT));
	   hand13.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.TWO));
	   hand13.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.THREE));

	   hand13.EvaulateHand();
	   assertEquals(hand13.getHs().getHandStrength(),60);
	   

	   Hand hand14 = new Hand(); //full house - set of 3 first
	   hand14.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand14.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand14.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand14.AddToCardsInHand(new Card(pokerEnums.eSuit.DIAMONDS,pokerEnums.eRank.TWO));
	   hand14.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.TWO));

	   hand14.EvaulateHand();
	   assertEquals(hand14.getHs().getHandStrength(),70);
	   

	   Hand hand15 = new Hand(); //full house - set of 2 first
	   hand15.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand15.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand15.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.TWO));
	   hand15.AddToCardsInHand(new Card(pokerEnums.eSuit.DIAMONDS,pokerEnums.eRank.TWO));
	   hand15.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.TWO));

	   hand15.EvaulateHand();
	   assertEquals(hand15.getHs().getHandStrength(),70);
	   

	   Hand hand16 = new Hand(); //Four of a Kind - first
	   hand16.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand16.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand16.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand16.AddToCardsInHand(new Card(pokerEnums.eSuit.DIAMONDS,pokerEnums.eRank.ACE));
	   hand16.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.TWO));

	   hand16.EvaulateHand();
	   assertEquals(hand16.getHs().getHandStrength(),80);
	   

	   Hand hand17 = new Hand(); //four of a kind - second
	   hand17.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand17.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.TWO));
	   hand17.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.TWO));
	   hand17.AddToCardsInHand(new Card(pokerEnums.eSuit.DIAMONDS,pokerEnums.eRank.TWO));
	   hand17.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.TWO));

	   hand17.EvaulateHand();
	   assertEquals(hand17.getHs().getHandStrength(),80);
	   

	   Hand hand19 = new Hand(); //Five of a kind
	   hand19.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.TWO));
	   hand19.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.TWO));
	   hand19.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.TWO));
	   hand19.AddToCardsInHand(new Card(pokerEnums.eSuit.DIAMONDS,pokerEnums.eRank.TWO));
	   hand19.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.TWO));

	   hand19.EvaulateHand();
	   assertEquals(hand19.getHs().getHandStrength(),85);
	   

	   Hand hand20 = new Hand(); //straight flush
	   hand20.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand20.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.FIVE));
	   hand20.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.FOUR));
	   hand20.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.THREE));
	   hand20.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.TWO));

	   hand20.EvaulateHand();
	   assertEquals(hand20.getHs().getHandStrength(),90);
	   

	   Hand hand21 = new Hand(); //pair - fourth 2 cards
	   hand21.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand21.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.KING));
	   hand21.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.QUEEN));
	   hand21.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.JACK));
	   hand21.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.TEN));

	   hand21.EvaulateHand();
	   assertEquals(hand21.getHs().getHandStrength(),100);
	   
	   
   }
   
   @Test
   public void test_Hand_JokersInvolved(){
	   Hand hand1 = new Hand(); //pair - fourth 2 cards
	   hand1.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.ACE));
	   hand1.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.NINE));
	   hand1.AddToCardsInHand(new Card(pokerEnums.eSuit.CLUBS,pokerEnums.eRank.EIGHT));
	   hand1.AddToCardsInHand(new Card(pokerEnums.eSuit.JOKER,pokerEnums.eRank.JOKER));
	   hand1.AddToCardsInHand(new Card(pokerEnums.eSuit.HEARTS,pokerEnums.eRank.TWO));

	   hand1.EvaulateHand();
	   assertEquals(hand1.isbScored(),true);
   }
   
		   
   }

