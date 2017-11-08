package riskModels.dice;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Dice {
    int numberOfDice; //number of Dice user wants to roll
    int[] diceResult; //the dice result example : first dice roll result will be stored in diceResult[0]
    boolean isDiceRolled; // check if dice rolled

    /**
     * Returns result of Dice roll stored into object of values between 1 and 6 representing the
     * outcome of rolling the dice.  The number of values in the array should be
     * between 1 and 3, depending on the number of dice rolled by the player.  The
     * number of dice rolled by the player is specified by the argument numberOfDice
     *
     * @param numberOfDice number of Dice User wants to roll
     * @return return diceModel object that will have dice roll result
     */
    public Dice rollDice(int numberOfDice) {
        Dice dicemodel = new Dice();
        //check if we can roll the dice or not
        if (numberOfDice <= 0) {
            dicemodel.setDiceRolled(false);
            return dicemodel;
        }
        int[] diceResult = new int[numberOfDice];
        for (int counter = 0; counter < numberOfDice; counter++) {
            Random random = new Random();
            int result = random.nextInt(5) + 1; //this function will give results between 1-6 including both
            diceResult[counter] = result;
        }
        //So
        Arrays.sort(diceResult);
        Collections.reverse(Arrays.asList(diceResult));
        dicemodel.setDiceRolled(true);
        dicemodel.setDiceResult(diceResult);
        dicemodel.setNumberOfDice(numberOfDice);
        return dicemodel;

    }

    public boolean isDiceRolled() {
        return isDiceRolled;
    }

    public void setDiceRolled(boolean isDiceRolled) {
        this.isDiceRolled = isDiceRolled;
    }

    public int getNumberOfDice() {
        return numberOfDice;
    }

    public void setNumberOfDice(int numberOfDice) {
        this.numberOfDice = numberOfDice;
    }

    public int[] getDiceResult() {
        return diceResult;
    }

    public void setDiceResult(int[] diceResult) {
        this.diceResult = diceResult;
    }
}
