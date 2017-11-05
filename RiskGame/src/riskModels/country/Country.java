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

    /**
     * This constructor will create country object based provided parameters
     * @param countryName name of the country
     * @param startPixel For UI purpose start pixel
     * @param endPixel For UI purpose end pixel
     * @param continentName name of the continent
     */
    public Country(String countryName, int startPixel, int endPixel, String continentName) {
        this.countryName = countryName;
        this.startPixel = startPixel;
        this.endPixel = endPixel;
        this.belongsToContinent = continentName;
    }
    /**
     * This constructor will create country object based on provided player object and current deployed armies
     * @param belongsToPlayer player object
     * @param currentArmiesDeployed number of armies deployed to the country
     */
    public Country(Player belongsToPlayer, int currentArmiesDeployed) {
        this.belongsToPlayer = belongsToPlayer;
        this.currentArmiesDeployed = currentArmiesDeployed;
    }
    /**
     * This constructor will create country object based on provided country name
     * @param countryName name of the country
     */
    public Country(String countryName) {
        this.countryName = countryName;
    }
    /**
     * This constructor will create country object based on country name and continent name , for non UI purpose
     * @param countryName  Name of the country
     * @param belongsToContinent Name of the continent where country belongs to .
     */
    public Country(String countryName, String belongsToContinent) {
   	 this.countryName = countryName;
   	 this.belongsToContinent=belongsToContinent;
	}

	/**
	 * Over ride equals method in order to compare compare objects based on country name not country objects
	 */
    @Override
    public boolean equals(Object o) {
        String countryName = ((Country) o).getCountryName();
        return countryName.equals(this.getCountryName());
    }

    /**
     * Overriding hashCode method.
     * 
     */
    @Override
    public int hashCode() {
        return this.countryName.hashCode();
    }
    
    /**
     * getter method for player who own the country
     * @return belongsToPlayer owner of the country (player object)
     */
    public Player getBelongsToPlayer() {
        return belongsToPlayer;
    }
    
    /**
     * setter method to assign country to player
     * @param belongsToPlayer player object
     */
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

    public void addArmy(int n) {
        currentArmiesDeployed+=n;
        // update UI
    }

    public void subtractArmy(int n) {
        currentArmiesDeployed-=n;
        // update UI
    }

    public void setPlayer(Player player, int noOfArmy) {
        this.belongsToPlayer = player;
        this.currentArmiesDeployed = noOfArmy;
        // update UI
    }

}
