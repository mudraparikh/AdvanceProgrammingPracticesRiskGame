package test.cards;

import org.junit.Test;
import riskModels.dice.Dice;
import static org.junit.Assert.*;

/**
 * This class checks the functions related to the dices
 * @author hnath
 *
 */
public class DiceTest {

	/**
	 * This method checks dice result if 3 dices are rolled
	 * @throws Exception it throws if there are any exceptions found
	 */
    @Test
    public void testDiceResult() throws Exception {
    	assertTrue(Dice.rollDice(3).isDiceRolled);
    }

    /**
     * This method checks dice result is more than 3 if 6 dices are rolled 
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testDiceResultMoreThanThree() throws Exception{
        assertFalse(Dice.rollDice(6).isDiceRolled);
    }

    /**
     * This method checks the dice result is following the conditions
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testRollDice() throws Exception{
    	Integer[] i = Dice.rollDice(3).getDiceResult();
    	assertTrue(i[0] >= i[1]);
    	assertTrue(i[0] < 7);
    	assertTrue(i[0] > 0);
    }
}
