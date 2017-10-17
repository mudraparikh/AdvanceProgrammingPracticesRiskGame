package riskModels.country;

import riskModels.player.Player;

import java.util.List;

/**
 * Country Bean class to get and set properties related to country
 *
 * @author prashantp95
 */

public class Country {
    public String countryName;
    public int startPixel;
    public int endPixel;
    public String belongsToContinent;// to Represent county belongs to which continent
    public List<Country> neighborNodes; // to Represents adjacent country nodes
    public Player belongsToPlayer;
    public int currentArmiesDeployed;

    public Country(String countryName, int startPixel, int endPixel, String continentName) {
        this.countryName = countryName;
        this.startPixel = startPixel;
        this.endPixel = endPixel;
        this.belongsToContinent = continentName;
    }

    public Country(Player belongsToPlayer, int currentArmiesDeployed) {
        this.belongsToPlayer = belongsToPlayer;
        this.currentArmiesDeployed = currentArmiesDeployed;
    }

    public Country(String neighbourCountryName) {
        this.countryName = neighbourCountryName;
    }
    /**
     *  Overriding equals method.
     *  {@link https://www.ibm.com/developerworks/java/library/j-jtp05273/index.html}
     */
    @Override
    public boolean equals(Object o) {
        String countryName = ((Country) o).getCountryName();
        return countryName.equals(this.getCountryName());
    }
    /**
     * Overiding hashcode method.
     * {@link http://www.xyzws.com/javafaq/why-always-override-hashcode-if-overriding-equals/20}
     */
    @Override
    public int hashCode() {
        return this.countryName.hashCode();
    }

    public Player getBelongsToPlayer() {
        return belongsToPlayer;
    }

    public void setBelongsToPlayer(Player belongsToPlayer) {
        this.belongsToPlayer = belongsToPlayer;
    }

    public int getCurrentArmiesDeployed() {
        return currentArmiesDeployed;
    }

    public void setCurrentArmiesDeployed(int currentArmiesDeployed) {
        this.currentArmiesDeployed = currentArmiesDeployed;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getStartPixel() {
        return startPixel;
    }

    public void setStartPixel(int startPixel) {
        this.startPixel = startPixel;
    }

    public int getEndPixel() {
        return endPixel;
    }

    public void setEndPixel(int endPixel) {
        this.endPixel = endPixel;
    }

    public String getBelongsToContinent() {
        return belongsToContinent;
    }

    public void setBelongsToContinent(String belongsToContinent) {
        this.belongsToContinent = belongsToContinent;
    }

    public List<Country> getNeighborNodes() {
        return neighborNodes;
    }

    public void setNeighborNodes(List<Country> neighborNodes) {
        this.neighborNodes = neighborNodes;
    }
}
