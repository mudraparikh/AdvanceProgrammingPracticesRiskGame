package riskModels.gamedriver;

import riskModels.continent.Continent;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.Player;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static riskModels.map.GameMap.*;

/**
 * This class contains the services for the Game Play.
 * All the Phases relevant actions and events are listed down.
 * @author Akshay
 */
public class GamePlayAPI {
    public static int turn = 0;
    private MapModel mapModel;
    private GameMap gameMap;

    // Game APIs
    public void createGameMapFromFile(File file){
        mapModel = new MapModel();
        gameMap = mapModel.readMapFile(file.getAbsolutePath());
        if(!gameMap.isCorrectMap)
        {
        	System.out.print("Invalid Map File Selected   ");System.out.println(gameMap.getErrorMessage());
        	System.exit(1);
        }
    }

    /**
     * This method will get the current player turn
     * @param playerList The list of Player objects
     * @return Player object whose turn is it
     */
    public Player getCurrentTurnPlayer(List<Player> playerList) {
        return playerList.get(turn);
    }

    /**
     *
     * @param numberOfPlayers total number of players playing the game
     * @param playerList List of player objects
     * @return Will call the <code>getCurrentTurnPlayer</code> which will return player object
     */
    public Player changeTurnToNextPlayer(int numberOfPlayers, List<Player> playerList) {
        turn = (turn+1) % numberOfPlayers;
        return getCurrentTurnPlayer(playerList);
    }
    /**
     * This method will return number of Reinforcement Army for the player
     * @param player Player object whose turn it is 
     * @param gameMap current map details 
     * @return return number of reinforcement army for the player
     */
    public int getReinforcementArmyForPlayer(Player player, GameMap gameMap) {
        int countriesConquered = player.assignedCountries.size();

        if(countriesConquered==0)
            return 3;

        int count = (countriesConquered / 3);
        List<Continent> ruledContinents = getContinentsConqueredBy(player, gameMap);
        for(Continent c : ruledContinents)
            count += c.getControlValue();

        return count<3?3:count;
    }
    /**
     * This method will subtract army from player and country
     * @param player player from whom we need to subtract army  
     * @param country country from where we need to subtract army 
     * @param subAmount amount of subtraction
     * @return  true if provided number of armies deleted else false
     */
    public boolean subArmies(Player player, Country country, int subAmount) {
        if((country.getCurrentArmiesDeployed()==0 || player.assignedCountries.contains(country)) && ((country.getCurrentArmiesDeployed()-subAmount)>=0)) {
            player.subArmy(subAmount);
            country.subtractArmy(subAmount);
            if(country.getCurrentArmiesDeployed()==0)
                country.setPlayer(null, 0);
            return true;
        }
        return false;
    }
    /**
     * This method will move army  for player from one country to another
     * @param player player who wants to move army
     * @param from country from where players wants to move army
     * @param to country where player wants to move army	
     * @param noOfArmy number of armies player wants to move
     * @return true if armies moved else false if army not moved.
     */
    public boolean moveArmy(Player player, Country from, Country to, int noOfArmy) {
        if(player.assignedCountries.contains(from) && (to.getCurrentArmiesDeployed()==0 || player.assignedCountries.contains(to)) && (from.getCurrentArmiesDeployed()-noOfArmy)>=1) {
            from.subtractArmy(noOfArmy);
            if(to.getCurrentArmiesDeployed()==0)
                to.setPlayer(player, noOfArmy);
            else
                to.addArmy(noOfArmy);
            return true;
        }
        return false;
    }
    /**
     * This method will return list of continents conquered by player.
     * @param player player for whom we need to send list of continents conqueredd by him
     * @param gameMap current map details
     * @return list of continents 
     */
    public List<Continent> getContinentsConqueredBy(Player player, GameMap gameMap) {
        List<Continent> lst = new ArrayList<>();

        for(Continent c : getContinents(gameMap)) {
            boolean isOccupiedByPlayer = true;
            for(Map.Entry<Country, List<Country>> e : gameMap.getCountryAndNeighborsMap().entrySet()) {
                if(!e.getKey().getBelongsToPlayer().equals(player)) {
                    isOccupiedByPlayer = false;
                    break;
                }
            }
            if(isOccupiedByPlayer)
                lst.add(c);
        }
        return lst;
    }

    public List<Continent> getContinents(GameMap gameMap) {
        return gameMap.getContinentList();
    }
    /**
	 * This method will return list of continent where player has at least one country
	 * @param player object of player
	 * @return list of continent objects
	 */
	public List<Continent> getContinentOfPlayer(Player player){
		
		List<Country> countriesOfPlayer = player.getAssignedCountries();
		List<Continent> continentList = new ArrayList<>(); //List of Continent for the player 
		if(countriesOfPlayer!=null && !countriesOfPlayer.isEmpty()) {
			for(Country country : countriesOfPlayer) {
				//we have country,now find the continent details  
			    int indexOfContinent = GameMap.getInstance().getContinentList().indexOf(new Continent(country.getBelongsToContinent()));
		        Continent continent =GameMap.getInstance().getContinentList().get(indexOfContinent); 
				continentList.add(continent);
			}
		}
		return continentList;
		
	}

}
