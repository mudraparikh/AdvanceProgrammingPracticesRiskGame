package riskModels.dice;

import java.util.Random;

import riskView.GameView;

public class DiceModel {
int numberofDice; //number of Dice user wants to roll 
int[] diceResult; //the dice result example : first dice roll result will be stored in diceResult[0]
boolean isDiceRolled; // check if dice rolled  
public boolean isDiceRolled() {
	return isDiceRolled;
}
public void setDiceRolled(boolean isDiceRolled) {
	this.isDiceRolled = isDiceRolled;
}
public int getNumberofDice() {
	return numberofDice;
}
public void setNumberofDice(int numberofDice) {
	this.numberofDice = numberofDice;
}
public int[] getDiceResult() {
	return diceResult;
}
public void setDiceResult(int[] diceResult) {
	this.diceResult = diceResult;
}
/**
 * This method will return the result of the dice roll 
 * @param numberOfDice number of Dice User wants to roll
 * @return return diceModel object that will have dice roll result
 */
public static DiceModel gettheDiceResult(int numberOfDice) {
	DiceModel dicemodel = new DiceModel();
	//check if we can roll the dice or not
	if(numberOfDice<=0) {
		dicemodel.setDiceRolled(false);
		return dicemodel;
	}
	GameView.displayLog("Dice Rolling");
	int[] diceResult = new int[numberOfDice];
	for(int counter=0;counter<numberOfDice;counter++) {
		Random random = new Random();
		int result = random.nextInt(5) + 1; //this function will give results between 1-6 including both
		diceResult[counter] = result;
	}
	dicemodel.setDiceRolled(true);
	dicemodel.setDiceResult(diceResult);
	dicemodel.setNumberofDice(numberOfDice);
	return dicemodel;
	
	
}
}
