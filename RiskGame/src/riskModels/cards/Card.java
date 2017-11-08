package riskModels.cards;

import riskModels.country.Country;

/**
 * This class will handle operations on  cards
 *
 * @author Akshay Shah
 */
public final class Card {

    private final String type;
    private final Country country;

    public Card(String type, Country country) {
        this.type = type;
        this.country = country;
    }

    public String getName() {
        return country.getCountryName() + ", " + type;
    }

    public String getType() {
        return type;
    }

    public Country getCountry() {
        return country;
    }
}

