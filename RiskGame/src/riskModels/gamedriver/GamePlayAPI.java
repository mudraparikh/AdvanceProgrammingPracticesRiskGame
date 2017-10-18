package riskModels.gamedriver;

import riskModels.continent.Continent;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.player.Player;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GamePlayAPI {
    public static int turn = 0;

    // Game APIs

    public Player getCurrentTurnPlayer(List<Player> playerList) {
        return playerList.get(turn);
    }

    public Player changeTurnToNextPlayer(int numberOfPlayers, List<Player> playerList) {
        turn = (turn+1) % numberOfPlayers;
        return getCurrentTurnPlayer(playerList);
    }

    public int getReinforcementArmyForPlayer(Player player, GameMap gameMap) {
        int countriesConquered = player.assignedCountries.size();

        if(countriesConquered==0)
            return 3;

        int count = (countriesConquered / 3);
        List<Continent> ruledContinents = getContinentsCounqueredBy(player, gameMap);
        for(Continent c : ruledContinents)
            count += c.getControlValue();

        return count<3?3:count;
    }

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

    public boolean moveArmyFromTo(Player player, Country from, Country to, int noOfArmy) {
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

    public List<Continent> getContinentsCounqueredBy(Player p, GameMap gameMap) {
        List<Continent> lst = new ArrayList<>();

        for(Continent c : getContinents(gameMap)) {
            boolean isOccupiedByPlayer = true;
            for(Map.Entry<Country, List<Country>> e : gameMap.getCountryAndNeighborsMap().entrySet()) {
                if(!e.getKey().getBelongsToPlayer().equals(p)) {
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

}
