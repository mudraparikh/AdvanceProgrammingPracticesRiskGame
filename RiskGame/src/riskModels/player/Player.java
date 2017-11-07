package riskModels.player;

import riskModels.GamePlayModel;
import riskModels.cards.Card;
import riskModels.cards.Hand;
import riskModels.country.Country;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is bean class to set and get properties of player.
 * @author akshay shah
 *
 */
public class Player {

    public String name;
    public String alias;
    public int totalArmies;
    public int reinforcementArmies;
    public List<Country> assignedCountries;
    public Card cards;
    public Color colors;
    private int turnInCount;
    private Hand hand;
    public double domination; // player's domination in game based on number of countries out of total countries player own

    /**
     * getter method to get the list of current players
     */
    public List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * setter method to assign list of players
     * @param playerList list contains players name
     */
    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public List<Player> playerList;

    public Player(String name, Color colors) {
        super();
        this.name = name;
        assignedCountries = new ArrayList<>();
        this.colors = colors;
        hand = new Hand();

        turnInCount = 0;
    }

    /**
     * setter method assigns list of players
     * @param playerList player obect
     */
    public Player(List<Player> playerList){
        this.playerList = playerList;
    }

    /**
     * default constructor
     */
    public Player() {
		// TODO Auto-generated constructor stub
	}

    /**
     * getter method gives value of reinforcement army
     * @return value of reinforcement army
     */
	public int getReinforcementArmies() {
        return reinforcementArmies;
    }

	/**
	 * setter method assigns reinforcement army value
	 * @param reinforcementArmies player object
	 */
    public void setReinforcementArmies(int reinforcementArmies) {
        this.reinforcementArmies = reinforcementArmies;
    }

    /** 
     * getter method gives the player name
     * @return name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * setter method assigns name of the player
     * @param name players name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * getter method gives the total number of armies assigned to the player
     * @return number of armies assigned to the player
     */
    public int getTotalArmies() {
        return totalArmies;
    }

    /**
     * setter method to assign total number armies to the player
     * @param totalArmies player object
     */
    public void setTotalArmies(int totalArmies) {
        this.totalArmies = totalArmies;
    }

    /**
     * getter method gives the total number of countries assigned to the player
     * @return number of countries assigned
     */
    public List<Country> getAssignedCountries() {
        return assignedCountries;
    }

    /**
     * setter method to assign countries to the player
     * @param assignedCountries number of countries assigned to the player
     */
    public void setAssignedCountries(List<Country> assignedCountries) {
        this.assignedCountries = assignedCountries;
    }

    /**
     * getter method to know the card
     * @return cards for the players
     */
    public Card getCards() {
        return cards;
    }

    /**
     * setter method to assign cards to the player
     * @param cards players object
     */
    public void setCards(Card cards) {
        this.cards = cards;
    }

    /**
     * getter method to know which color is assigned
     * @return color of the player
     */
    public Color getColors() {
        return colors;
    }

    /**
     * setter method assigns the colors to the players
     * @param colors color of the player
     */
    public void setColors(Color colors) {
        this.colors = colors;
    }

    /**
     * setter method is used to add the army
     * @param n adds n number of army to the existing army
     */
    public void addArmy(int n) {
        this.totalArmies+=n;
    }

    /**
     * setter method is used to reduce the army
     * @param n reduces n number of army from total army
     */
    public void subArmy(int n) {
        this.totalArmies-=n;
    }

    /**
     * getter method to know which player domination
     * @return players name who has the domination
     */
	public double getDomination() {
		return domination;
	}

	/**
	 * setter method to assign domination of player
	 * @param domination player object
	 */
	public void setDomination(double domination) {
		this.domination = domination;
	}

    /**
     * Adds a risk card to the players hand
     **/
    public void addRiskCard(Card riskCard) {

        hand.add(riskCard);
    }

    /**
     * Removed a set of risk cards from the players hand to reflect risk cards being turned in
     **/
    public void removeCards(int[] cardsTurnedInIndex) {

        hand.removeCardsFromHand(cardsTurnedInIndex[0], cardsTurnedInIndex[1], cardsTurnedInIndex[2]);
    }

    public int getTurnInCount() {

        turnInCount++;
        return turnInCount;
    }

    public ArrayList<Card> getHand() {

        return hand.getCards();
    }

    public Hand getHandObject() {

        return hand;
    }

    public boolean mustTurnInCards() {

        return hand.mustTurnInCards();
    }
}

