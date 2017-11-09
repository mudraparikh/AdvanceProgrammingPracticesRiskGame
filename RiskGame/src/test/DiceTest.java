package test;

import org.junit.Test;
import riskModels.dice.Dice;
import static org.junit.Assert.*;

public class DiceTest {

    @Test
    public void testDiceResult() throws Exception {
    	assertTrue(Dice.rollDice(3).isDiceRolled);
    }

    @Test
    public void testDiceResultMoreThanThree() throws Exception{
        assertFalse(Dice.rollDice(6).isDiceRolled);
    }

    @Test
    public void testRollDice() throws Exception{
    	Integer[] i = Dice.rollDice(3).getDiceResult();
    	assertTrue(i[0] >= i[1]);
    	assertTrue(i[0] < 7);
    	assertTrue(i[0] > 0);
    }
}
