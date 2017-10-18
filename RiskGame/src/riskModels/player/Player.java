package riskModels.player;

import riskModels.cards.Card;
import riskModels.country.Country;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {

    public String name;
    public String alias;
    public int totalArmies;
    public int reinforcementArmies;
    public List<Country> assignedCountries;
    public Card cards;
    public Color colors;

    public Player(String name) {
        super();
        this.name = name;
        assignedCountries = new ArrayList<>();
        cards = new Card();
    }

    public Player(String name, Color colors) {
        super();
        this.name = name;
        assignedCountries = new ArrayList<>();
        this.colors = colors;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public Card getCards() {
        return cards;
    }

    public void setCards(Card cards) {
        this.cards = cards;
    }

    public Color getColors() {
        return colors;
    }

    public void setColors(Color colors) {
        this.colors = colors;
    }

    public void addArmy(int n) {
        this.totalArmies+=n;
    }
    public void subArmy(int n) {
        this.totalArmies-=n;
    }

}

