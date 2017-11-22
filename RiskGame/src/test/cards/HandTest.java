package test.cards;

import org.junit.Before;
import org.junit.Test;
import riskModels.cards.Card;
import riskModels.cards.Hand;
import riskModels.country.Country;

import static org.junit.Assert.*;

/**
 * this class tests the functions related to cards in the hand
 * @author hnath
 *
 */
public class HandTest {
    private Hand hand;
    private Card infantryCard,infantryCard2,infantryCard3, cavalryCard, artilleryCard;
    private Country country1, country2, country3, country4, country5;

    /**
     * This method setting up the context as many test cases share the same values
     * In  this we provided 5 cards ,5 countries
     */
    @Before
    public void init(){
        hand = new Hand();

        country1 = new Country("xyz",1,1,"abc");
        country2 = new Country("qwe",2,1,"abc");
        country3 = new Country("yui",3,1,"abc");
        country4 = new Country("dfg",4,1,"abc");
        country5 = new Country("xyz",5,1,"abc");

        infantryCard = new Card("Infantry",country1);
        infantryCard2 = new Card("Infantry",country2);
        infantryCard3 = new Card("Infantry",country3);
        cavalryCard = new Card("Cavalry",country4);
        artilleryCard = new Card("Artillery",country5);
    }

    /**
     * This method checks if the card is added to the hand
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void add() throws Exception {
        hand.add(infantryCard);
        assertEquals("Infantry",hand.hand.get(0).getType());
    }

    /**
     * This method removes same cards from the hand
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void removeCardsFromHandSameCards() throws Exception {
        hand.add(infantryCard);
        hand.add(infantryCard2);
        hand.add(infantryCard3);
        hand.removeCardsFromHand(0,1,2);
        assertTrue(hand.getCards().size() == 0);
    }

    /**
     * This method removes different cards from the hand
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void removeCardsFromHandDifferentCards() throws Exception {
        hand.add(infantryCard);
        hand.add(cavalryCard);
        hand.add(artilleryCard);
        hand.removeCardsFromHand(0,1,2);
        assertTrue(hand.getCards().size() == 0);
    }

    /**
     * This method removes 2 same cards from the hand
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void removeCardsFromHandTwoSameCards() throws Exception {
        hand.add(infantryCard);
        hand.add(cavalryCard);
        hand.add(cavalryCard);
        hand.removeCardsFromHand(0,1,2);
        assertFalse(hand.getCards().size() == 0);
    }

    /**
     * This method checks can it turn in same cards
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void canTurnInSameCards() throws Exception {
        hand.add(infantryCard);
        hand.add(infantryCard2);
        hand.add(infantryCard3);
        assertTrue(hand.canTurnInCards(0,1,2));
    }

    /**
     * This method checks can it turn in different cards
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void canTurnInDifferentCards() throws Exception {
        hand.add(infantryCard);
        hand.add(cavalryCard);
        hand.add(artilleryCard);
        assertTrue(hand.canTurnInCards(0,1,2));
    }
}