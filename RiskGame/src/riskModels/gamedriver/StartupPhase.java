package riskModels.gamedriver;

import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.player.Player;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * This class initialises the Players data and assign countries with armies.
 * Basically, all the necessary setup/startup requirements are met before actually playing the game.
 * @author Akshay
 */
public class StartupPhase {

    public void initialisePlayersData(List<Player> playerList, GameMap gameMap, int numberOfPlayers) {

        // init players data
        assignInitialArmiesToPlayers(numberOfPlayers, playerList);

        // allocate countries to players
        allocateCountriesToPlayers(playerList, gameMap, numberOfPlayers);

        // add initial army using round-robin fashion
        addArmiesToCountries(playerList, numberOfPlayers);

    }

    public void allocateCountriesToPlayers(List<Player> playerList, GameMap gameMap, int numberOfPlayers) {
        int j = 0;
        for (Map.Entry<Country, List<Country>> e : gameMap.getCountryAndNeighborsMap().entrySet()) {
            playerList.get(j % numberOfPlayers).assignedCountries.add(e.getKey());
            e.getKey().setBelongsToPlayer(playerList.get(j % numberOfPlayers));
            j++;
        }
    }

    public void assignInitialArmiesToPlayers(int numberOfPlayers, List<Player> playerList) {
        for (Player p:playerList){
            p.setTotalArmies(getInitialArmy(numberOfPlayers));
        }
    }


    public List<Player> setPlayer(int numberOfPlayers) {
        Color color[] = {Color.RED, Color.MAGENTA, Color.BLUE, Color.GREEN};
        List<Player> playerList = new ArrayList<>();
        int i = 0;
        while (i < numberOfPlayers) {
            playerList.add(new Player("player" + i, color[i]));
            i++;
        }
        return playerList;
    }

    public void addArmiesToCountries(List<Player> playerList, int numberOfPlayers) {
        for (Player player : playerList) {
            List<Country> cList = player.assignedCountries;
            for (int i = 0; i < getInitialArmy(numberOfPlayers); i++) {
                int index = i % cList.size();
                Country putArmyAtCountry = cList.get(index);
                addArmies(player, putArmyAtCountry, 1);
            }
        }
    }
    public int getInitialArmy(int numberOfPlayers) {
        switch (numberOfPlayers) {
            case 2: return 40;
            case 3: return 35;
            case 4: return 30;
            default: return 10;
        }
    }

    public void addArmies(Player player, Country country, int addAmount) {
        if(country.currentArmiesDeployed==0 || player.getAssignedCountries().contains(country)) {
            player.addArmy(addAmount);
            country.addArmy(addAmount);
        }
    }
}

