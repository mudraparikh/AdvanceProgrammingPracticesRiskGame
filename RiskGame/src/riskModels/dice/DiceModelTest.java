package riskModels.dice;

import static org.junit.Assert.*;

import org.junit.Test;

public class DiceModelTest {

	@Test
	public void testDiceResult() {
		assertFalse(DiceModel.gettheDiceResult(-1).isDiceRolled);
		assertTrue(DiceModel.gettheDiceResult(3).isDiceRolled);
	}
	

}
