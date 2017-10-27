package riskModels.gamedriver;

import riskModels.continent.Continent;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.Player;


import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    Player player;
    public int playerCount;

    // Game APIs

    public void createGameMapFromFile(File file){
        mapModel = new MapModel();
        gameMap = mapModel.readMapFile(file.getAbsolutePath());
        if (!gameMap.isCorrectMap) System.exit(1);
    }

    public void initData(File selectedFile, int playerCount) {
        if(selectedFile.getName().endsWith("map") && playerCount > 0){
            createGameMapFromFile(selectedFile);
            initializePlayerData(playerCount);
            this.playerCount = playerCount;
            setInitialArmies();
            allocateCountriesToPlayers();
            addInitialArmiesInRR();
            for (Country c : gameMap.getCountryAndNeighborsMap().keySet()){
                System.out.println(c.getCountryName() + " "+ c.getCurrentArmiesDeployed());            }
        }
        else {
            System.out.println("Something went wrong ! ");
            System.exit(1);
        }
    }

    private void addInitialArmiesInRR() {
        int j = 0;
        int playersLeftForAssign = playerCount;
        while (playersLeftForAssign > 0) {
            if (player.getPlayerList().get(j % playerCount).getTotalArmies() > 0) {
                List<Country> playerCountryList = player.getPlayerList().get(j % playerCount).getAssignedCountries();
                Country randomCountry = playerCountryList.get(new Random().nextInt(playerCountryList.size()));
                randomCountry.addArmy(1);
                player.getPlayerList().get(j % playerCount)
                        .setTotalArmies(player.getPlayerList().get(j % playerCount).getTotalArmies() - 1);
            } else {
                playersLeftForAssign--;
            }
            j++;
        }

    }

    private void allocateCountriesToPlayers() {
        int j = 0;
        for (Country country : gameMap.getCountryAndNeighborsMap().keySet()) {
            Player p = player.getPlayerList().get(j % playerCount);
            p.assignedCountries.add(country);
            country.setBelongsToPlayer(p);
            country.addArmy(1);
            p.subArmy(1);
            j++;
        }

    }

    private void setInitialArmies() {
        for (Player p : player.getPlayerList()) {
            p.setTotalArmies(getInitialArmyCount());
        }
    }

    private int getInitialArmyCount() {
        switch (playerCount) {
            case 3:
                return 35;
            case 4:
                return 30;
            case 5:
                return 25;
            case 6:
                return 20;
            default:
                return 10;
        }
    }

    public void initializePlayerData(int playerCount) {
        Color color[] = {Color.RED, Color.MAGENTA, Color.BLUE, Color.GREEN};
        List<Player> playerList = new ArrayList<>();
        int i = 0;
        while (i < playerCount) {
            playerList.add(new Player("player" + i, color[i]));
            i++;
        }
        player = new Player(playerList);
        player.setPlayerList(playerList);
    }
}
