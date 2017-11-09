package riskModels.cards;

import org.junit.Before;
import org.junit.Test;
import riskModels.country.Country;

import static org.junit.Assert.*;

public class HandTest {
    private Hand hand;
    private Card infantryCard,infantryCard2,infantryCard3, cavalryCard, artilleryCard;
    private Country country1, country2, country3, country4, country5;

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

    @Test
    public void add() throws Exception {
        hand.add(infantryCard);
        assertEquals("Infantry",hand.hand.get(0).getType());
    }

    @Test
    public void removeCardsFromHandSameCards() throws Exception {
        hand.add(infantryCard);
        hand.add(infantryCard2);
        hand.add(infantryCard3);
        hand.removeCardsFromHand(0,1,2);
        assertTrue(hand.getCards().size() == 0);
    }

    @Test
    public void removeCardsFromHandDifferentCards() throws Exception {
        hand.add(infantryCard);
        hand.add(cavalryCard);
        hand.add(artilleryCard);
        hand.removeCardsFromHand(0,1,2);
        assertTrue(hand.getCards().size() == 0);
    }

    @Test
    public void removeCardsFromHandTwoSameCards() throws Exception {
        hand.add(infantryCard);
        hand.add(cavalryCard);
        hand.add(cavalryCard);
        hand.removeCardsFromHand(0,1,2);
        assertFalse(hand.getCards().size() == 0);
    }

    @Test
    public void canTurnInSameCards() throws Exception {
        hand.add(infantryCard);
        hand.add(infantryCard2);
        hand.add(infantryCard3);
        assertTrue(hand.canTurnInCards(0,1,2));
    }

    @Test
    public void canTurnInDifferentCards() throws Exception {
        hand.add(infantryCard);
        hand.add(cavalryCard);
        hand.add(artilleryCard);
        assertTrue(hand.canTurnInCards(0,1,2));
    }
}