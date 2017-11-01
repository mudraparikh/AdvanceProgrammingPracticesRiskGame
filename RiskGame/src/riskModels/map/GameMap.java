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
    private GameMap() {
		//To Prevent Other classes from creating object.
	}

    public boolean isCorrectMap() {
        return isCorrectMap;
    }

    public void setCorrectMap(boolean isCorrectMap) {
        this.isCorrectMap = isCorrectMap;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;

    }

    public HashMap<String, String> getMapDetail() {
        return mapDetail;
    }

    public void setMapDetail(HashMap<String, String> mapDetail) {
        this.mapDetail = mapDetail;

    }

    public HashMap<Country, List<Country>> getCountryAndNeighborsMap() {
        return countryAndNeighborsMap;
    }

    public void setCountryAndNeighborsMap(HashMap<Country, List<Country>> countryAndNeighborsMap) {
        this.countryAndNeighborsMap = countryAndNeighborsMap;
    }

    public List<Continent> getContinentList() {
        return continentList;
    }

    public void setContinentList(List<Continent> continentList) {
        this.continentList = continentList;
    }
	public HashMap<Continent, List<Country>> getContinentCountryMap() {
		return continentCountryMap;
	}
	public void setContinentCountryMap(HashMap<Continent, List<Country>> continentCountryMap) {
		this.continentCountryMap = continentCountryMap;
	}
	public List<Player> getPlayerList() {
		return playerList;
	}
	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}
}
