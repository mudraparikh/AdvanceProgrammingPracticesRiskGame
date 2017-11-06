package riskModels.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import riskModels.continent.Continent;
import riskModels.country.Country;
import riskModels.player.Player;

/**
 * This class holds the properties to create graph from map file
 *
 * @author prashantp95
 */
public class GameMap {

    public HashMap<Country, List<Country>> countryAndNeighborsMap = new HashMap<>();
    public HashMap<Continent, List<Country>> continentCountryMap = new HashMap<>();
    public List<Continent> continentList = new ArrayList<>();
    public List<Player> playerList = new ArrayList<>();
    public boolean isCorrectMap = true;
    public String errorMessage;
    public HashMap<String, String> mapDetail = new HashMap<>();
    private static GameMap gameMap;
    /**
     * This method will will return singleton instance for the GameMap class
     * @return single GameMap instance
     */
    public static GameMap getInstance() {
    	if(null ==gameMap) {
    		gameMap = new GameMap();
    	}
    	return gameMap;
    }
    
    /**
     * Default Constructor
     */
    private GameMap() {
		//To Prevent Other classes from creating object.
	}

    /**
     * this method verify weather the selected map validate the rules of CorrectMap constraints
     * @return true if the map is correct; otherwise return false
     */
    public boolean isCorrectMap() {
        return isCorrectMap;
    }

    /** 
     * setter method checks is the map is correct and then assigns map 
     * @param isCorrectMap boolean value
     */
    public void setCorrectMap(boolean isCorrectMap) {
        this.isCorrectMap = isCorrectMap;
    }

    /**
     * getter method for players who selected the map to play on
     * @return errorMessage message display on the console
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * setter method to assign the message when we select a map which doesn't agree to the rules
     * @param errorMessage generates a error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;

    }

    /**
     * getter method to get the selected map details
     * @return mapDetails Complete details of selected map
     */
    public HashMap<String, String> getMapDetail() {
        return mapDetail;
    }

    /**
     * setter method to assign mapDetails
     * @param mapDetail map object
     */
    public void setMapDetail(HashMap<String, String> mapDetail) {
        this.mapDetail = mapDetail;

    }

    /**
     * getter method to get the informations of neighboring countries
     * @return countryAndNeighborsMap list of neighboring countries details
     */
    public HashMap<Country, List<Country>> getCountryAndNeighborsMap() {
        return countryAndNeighborsMap;
    }

    /**
     * setter method to assign countries and its neighbors in the map
     * @param countryAndNeighborsMap map object
     */
    public void setCountryAndNeighborsMap(HashMap<Country, List<Country>> countryAndNeighborsMap) {
        this.countryAndNeighborsMap = countryAndNeighborsMap;
    }

    /**
     * getter method to get list of continents in the selected map
     * @return continentList list of continents in the map
     */
    public List<Continent> getContinentList() {
        return continentList;
    }

    /**
     * setter method to assign continents to the map
     * @param continentList map object
     */
    public void setContinentList(List<Continent> continentList) {
        this.continentList = continentList;
    }
    
    /**
     * getter method to get list of countries belong to the continent 
     * @return continentCountryMap map object
     */
	public HashMap<Continent, List<Country>> getContinentCountryMap() {
		return continentCountryMap;
	}
	
	/**
	 * setter method to assign countries to the continent
	 * @param continentCountryMap map object
	 */
	public void setContinentCountryMap(HashMap<Continent, List<Country>> continentCountryMap) {
		this.continentCountryMap = continentCountryMap;
	}
	
	/**
	 * getter method to get the list of the players
	 * @return playerList player details
	 */
	public List<Player> getPlayerList() {
		return playerList;
	}
	
	/**
	 * setter method to assign players list
	 * @param playerList details of players
	 */
	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}
}
