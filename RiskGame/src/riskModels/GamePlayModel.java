package riskModels;

import java.awt.Color;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.Random;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.Player;
import riskModels.player.PlayerModel;

/**
 * This class contains the services for the Game Play.
 * All the Phases relevant actions and events are listed down.
 * @author Akshay
 */
public class GamePlayModel extends Observable{

    private boolean canTurnInCards;
    private boolean canReinforce;
    private boolean canAttack;
    private boolean canFortify;

    private int playerIndex;
    private int i;
    private String countryASelection;
    private MapModel mapModel;
    private GameMap gameMap;
    private List<Player> playerList;
    private Player player;
    private Player currentPlayer;
    private int playerCount;
    private ArrayList<String> list;

    // Game APIs
    /**
     * This method will create populate GameMap instance while reading file
     * @param file .map file
     */
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
     * This method will perform initialization for the game for example reading map,Assign country to players
     * @param selectedFile .map file 
     * @param playerCount number of players for the game
     */
    public void initData(File selectedFile, int playerCount) {
        if(selectedFile.getName().endsWith("map") && playerCount > 0){
            createGameMapFromFile(selectedFile);
            initializePlayerData(playerCount);
            attachModelAndObservers();
            this.playerCount = playerCount;
            setInitialArmies();
            allocateCountriesToPlayers();
            addInitialArmiesInRR();
            PlayerModel playerModel = new PlayerModel();
            playerModel.getPlyaerWorldDomination(player.getPlayerList()) ;

        }
        else {
            System.out.println("Something went wrong ! ");
            System.exit(1);
        }
    }

    private void attachModelAndObservers() {
		PlayerModel playermodel = new PlayerModel();
		
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
        playerList = new ArrayList<>();
        int i = 0;
        while (i < playerCount) {
            playerList.add(new Player("player" + i, color[i]));
            i++;
        }
        player = new Player(playerList);
        player.setPlayerList(playerList);
        GameMap.getInstance().setPlayerList(playerList);
    }

    public void nextPlayerTurn(){
        if (playerList.size()>1){
            //if at least one player remains
            canReinforce = false;
            canAttack = false;
            canFortify = false;
            playerIndex++;

            if (playerIndex >= playerList.size()){
                //Loop player index back to 0 when it exceeds the number of players
                playerIndex = 0;
            }
            currentPlayer = playerList.get(playerIndex);
        }
    }

    public void startGame(){
        Collections.shuffle(playerList);
        player.setPlayerList(playerList);
        System.out.println("The order of turns:");
        for (Player p: playerList){
            System.out.println(p.getName());
        }
        System.out.println("All the players have been given the countries randomly and have assigned 1 initial armies from the total initial armies player gets.\n");
        System.out.println("To begin: Start reinforcement phase by placing army in your designated country\n");
        nextPlayerTurn();
    }

    public ArrayList<String> getSelectedCountryList(){
        list = new ArrayList<>();
        for(Country c: currentPlayer.getAssignedCountries()){
            list.add(c.getCurrentArmiesDeployed() + " : "+c.getCountryName());
        }
        return list;
    }

    /**
     * Receives information on which country is selected in countryAList.
     * @param country String of the selected country
     **/
    public void setCountryASelection(String country) {
        countryASelection = country;
        System.out.println("186"+ countryASelection);
        setChanged();
        notifyObservers("countryB");
    }

    /**
     * Creates and returns the information for the countryBList in the GameView.
     * @return a list of Strings to be displayed in the countryBList.
     **/
    protected ArrayList<String> getCountryBList() {

        list = new ArrayList<>();

        for(Country c: gameMap.getCountryAndNeighborsMap().keySet()){
            if (checkAdjacency(countryASelection, c.getCountryName())) {
                list.add(c.getCurrentArmiesDeployed() + "-" + c.getCountryName());
            }
        }
        return list;
    }

    public boolean checkAdjacency(String countryA, String countryB) {
        return GameMap.getInstance().getCountryAndNeighborsMap().get(new Country(countryA)).contains(new Country(countryB));

    }
}
