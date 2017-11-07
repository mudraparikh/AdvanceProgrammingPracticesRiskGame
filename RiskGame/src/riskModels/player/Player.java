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

    public List<Player> getPlayerList() {
        return playerList;
    }

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

    public Player(List<Player> playerList){
        this.playerList = playerList;
    }

    public Player() {
		// TODO Auto-generated constructor stub
	}

	public int getReinforcementArmies() {
        return reinforcementArmies;
    }

    public void setReinforcementArmies(int reinforcementArmies) {
        this.reinforcementArmies = reinforcementArmies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalArmies() {
        return totalArmies;
    }

    public void setTotalArmies(int totalArmies) {
        this.totalArmies = totalArmies;
    }

    public List<Country> getAssignedCountries() {
        return assignedCountries;
    }

    public void setAssignedCountries(List<Country> assignedCountries) {
        this.assignedCountries = assignedCountries;
    }

    public void addArmy(int n) {
        this.totalArmies+=n;
    }

    public void subArmy(int n) {
        this.totalArmies-=n;
    }

	public double getDomination() {
		return domination;
	}

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

