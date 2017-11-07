package riskModels;

import riskModels.cards.Deck;
import riskModels.continent.Continent;
import riskModels.country.Country;
import riskModels.dice.Dice;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.Player;
import riskModels.player.PlayerModel;
import riskView.GameView;
import riskView.PlayerView;

import javax.swing.*;
import javax.swing.plaf.basic.BasicIconFactory;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * This class contains the services for the Game Play.
 * All the Phases relevant actions and events are listed down.
 *
 * @author Akshay
 */
public class GamePlayModel extends Observable {

    private boolean canTurnInCards;
    private boolean canReinforce;
    private boolean canAttack;
    private boolean canFortify;
    private boolean hasCountryCaptured;

    private int playerIndex = 0;
    private int i;
    private MapModel mapModel;
    private GameMap gameMap;
    private List<Player> playerList;
    private Player player;
    private Player currentPlayer;
    private int currentPlayerReinforceArmies;
    private int playerCount;
    private int attackerLosses;
    private int defenderLosses;
    private int attackerDice;
    private int defenderDice;
    private int[] attackerRolls;
    private int[] defenderRolls;
    private ArrayList<String> list;
    private Country countryA;
    private Country countryB;
    private Dice dice;
    private Deck deck;


    // Game APIs

    /**
     * This method will create populate GameMap instance while reading file
     *
     * @param file .map file
     */
    public void createGameMapFromFile(File file) {
        mapModel = new MapModel();
        gameMap = mapModel.readMapFile(file.getAbsolutePath());
        if (!gameMap.isCorrectMap) {
            System.out.print("Invalid Map File Selected   ");
            System.out.println(gameMap.getErrorMessage());
            System.exit(1);
        }

    }

    /**
     * This method will perform initialization for the game for example reading map,Assign country to players
     *
     * @param selectedFile .map file
     * @param playerCount  number of players for the game
     */
    public void initData(File selectedFile, int playerCount) {
        if (selectedFile.getName().endsWith("map") && playerCount > 0) {
            createGameMapFromFile(selectedFile);
            // Creates deck
            System.out.println("Populating deck...");
            deck = new Deck((ArrayList<Country>) gameMap.getCountries());
            initializePlayerData(playerCount);
            attachModelAndObservers();
            this.playerCount = playerCount;
            setInitialArmies();
            allocateCountriesToPlayers();
            addInitialArmiesInRR();
            PlayerModel playerModel = new PlayerModel();
            playerModel.getPlayerWorldDomination(player.getPlayerList());
            canTurnInCards = false;
            canReinforce = true;
            canAttack = false;
            canFortify = false;

        } else {
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
        Color color[] = {Color.RED, Color.MAGENTA, Color.BLUE, Color.GREEN, Color.YELLOW, Color.BLACK};
        String playersName[] = {"John", "Alexa", "Penny", "Sheldon", "Amy", "Raj"};
        playerList = new ArrayList<>();
        int i = 0;
        while (i < playerCount) {
            playerList.add(new Player(playersName[i], color[i]));
            i++;
        }
        player = new Player(playerList);
        player.setPlayerList(playerList);
        GameMap.getInstance().setPlayerList(playerList);
    }

    /**
     * Handles placing reinforcements.
     *
     * @param country is a String of the country in which the reinforcements will be placed
     **/
    public void reinforce(String country, GameView gameView) {

        countryA = MapModel.getCountryObj(country, GameMap.getInstance());
        if (canReinforce) {
            assert countryA != null;
            if (currentPlayer.equals(countryA.getBelongsToPlayer())) {
                GameView.displayLog(currentPlayer.name + " gets " + currentPlayerReinforceArmies + " armies");
                try {
                    Integer[] selectOptions = new Integer[currentPlayer.getTotalArmies()];
                    for (int i = 0; i < currentPlayer.getTotalArmies(); i++) {
                        selectOptions[i] = i + 1;
                    }
                    if (currentPlayer.getTotalArmies() > 0) {
                        // Player inputs how many armies to reinforce selected country
                        Integer armies = (Integer) JOptionPane.showInputDialog(gameView,
                                "How many armies do you wish to send to reinforce " + countryA.getCountryName() + "?",
                                "Input", JOptionPane.OK_OPTION, BasicIconFactory.getMenuArrowIcon(), selectOptions,
                                selectOptions[0]);
                        // Subtracts player armies and adds armies to country
                        if (armies != null) {
                            currentPlayer.subArmy(armies);
                            countryA.addArmy(armies);
                            GameView.displayLog(currentPlayer.getName() + " has chosen to reinforce " + countryA.getCountryName() + " with " + armies + " armies.");
                            if (currentPlayer.getTotalArmies() == 0) {
                                canAttack = true;
                                canFortify = true;
                                GameView.displayLog("You do not have any armies left to reinforce");
                            }
                        }
                        GameView.updateMapPanel();

                    } else if (currentPlayer.getTotalArmies() == 0) {
                        canAttack = true;
                        canFortify = true;
                        GameView.displayLog("You do not have any armies left to reinforce");
                    }

                } catch (NumberFormatException e) {
                    GameView.displayLog("System Error or Exception is thrown for reinforce method");
                }
            }
        }
    }


    /**
     * Handles the attack function.
     * Attacking allows the player to engage in battles, with outcomes decided by RNG, with
     * opposing players in order to lower the number of armies in a territory to 0 in order
     * to occupy it.
     *
     * @param country1 is a String of the point A country.
     * @param country2 is a String of the point B country.
     **/
    public void attack(String country1, String country2, GameView gameView) {
        countryA = MapModel.getCountryObj(country1, GameMap.getInstance());
        countryB = MapModel.getCountryObj(country2, GameMap.getInstance());
        if (canAttack) {

            assert countryB != null;
            if (countryA.getCurrentArmiesDeployed() > 1) {
                //Check if at-least 2 armies are there on the attacking country.
                if (!currentPlayer.equals(countryB.getBelongsToPlayer()) && currentPlayer.equals(countryA.getBelongsToPlayer())) {
                    // Check if another country is occupied by an opponent and not by the currentPlayer.
                    if (mapModel.isNeighbour(countryA, countryB)) {
                        // Check if countryA is adjacent to countryB

                        dice = new Dice();

                        // Set default values
                        attackerLosses = 0;
                        defenderLosses = 0;
                        attackerDice = 1;
                        defenderDice = 1;
                        try {
                            // Attacker chooses how many dice to roll
                            Integer[] selectOptions = new Integer[getMaxNumberOfDices(countryA)];
                            for (int i = 0; i < getMaxNumberOfDices(countryA); i++) {
                                selectOptions[i] = i + 1;
                            }

                            attackerDice = (Integer) JOptionPane.showInputDialog(gameView,
                                    countryA.getBelongsToPlayer().getName() + ", is attacking " + country1 + " from " + country2 + "! How many dice will you roll?",
                                    "Input", JOptionPane.OK_OPTION, BasicIconFactory.getMenuArrowIcon(), selectOptions,
                                    selectOptions[0]);
                        } catch (NumberFormatException e) {
                            // Error: attacker inputs non-integer
                            GameView.displayLog("Please pass integer. We are attacking !");

                        } catch (IllegalArgumentException e) {
                            // Error: attacker inputs invalid number of dice
                            GameView.displayLog("Roll 1,2 or 3 dice. You must have at least one more army in your country than the number of dice you roll.");
                        }

                        attackerRolls = dice.rollDice(attackerDice).getDiceResult();
                        try {
                            // Defender chooses how many dice to roll after attacker
                            Integer[] selectOptions = new Integer[getMaxNumberOfDicesForDefender(countryB)];
                            for (int i = 0; i < getMaxNumberOfDicesForDefender(countryB); i++) {
                                selectOptions[i] = i + 1;
                            }
                            defenderDice = (Integer) JOptionPane.showInputDialog(gameView,
                                    countryB.getBelongsToPlayer().getName() + ", you are defending " + country2 + " from " + countryA.getBelongsToPlayer().getName() + "! How many dice will you roll?",
                                    "Input", JOptionPane.OK_OPTION, BasicIconFactory.getMenuArrowIcon(), selectOptions,
                                    selectOptions[0]);
                        } catch (NumberFormatException e) {
                            // Error: defender inputs non-integer
                            GameView.displayLog("Please enter integer only. You are defending ! ");

                        } catch (IllegalArgumentException e) {
                            // Error: defender inputs invalid number of dice
                            GameView.displayLog("Roll either 1 or 2 dice. To roll 2 dice, you must have at least 2 armies on your country.");
                        }
                        defenderRolls = dice.rollDice(defenderDice).getDiceResult();
                        // Rolls arrays have been ordered in descending order. Index 0 = highest pair
                        if (attackerRolls[0] > defenderRolls[0]) {
                            defenderLosses++;
                        } else if (attackerRolls[0] < defenderRolls[0] || attackerRolls[0] == defenderRolls[0]) {
                            attackerLosses++;
                        }
                        // Index 1 = second highest pair
                        if (attackerDice > 1 && defenderDice > 1) {

                            if (attackerRolls[1] > defenderRolls[1]) {
                                defenderLosses++;

                            } else if (attackerRolls[1] < defenderRolls[1] || attackerRolls[1] == defenderRolls[1]) {
                                attackerLosses++;
                            }
                        }
                        // Calculate losses
                        GameView.displayLog("\n\n<COMBAT REPORT>");
                        countryA.subtractArmy(attackerLosses);
                        countryB.subtractArmy(defenderLosses);
                        GameView.displayLog("Attacker Losses : " + attackerLosses + " army.");
                        GameView.displayLog("Defender Losses : " + defenderLosses + " army.");
                        GameView.displayLog(countryA.getCountryName() + " has now " + countryA.getCurrentArmiesDeployed());
                        GameView.displayLog(countryB.getCountryName() + " has now " + countryB.getCurrentArmiesDeployed());
                        GameView.displayLog("\n\n");

                        // If defending country loses all armies
                        if (countryB.getCurrentArmiesDeployed() < 1) {

                            GameView.displayLog(countryA.getBelongsToPlayer().getName() + " has defeated all of " + countryB.getBelongsToPlayer().getName() + "'s armies in " + country2 + " and has occupied the country!");

                            // Remove country from defender's list of occupied territories and adds to attacker's list
                            countryB.getBelongsToPlayer().assignedCountries.remove(countryB);
                            countryA.getBelongsToPlayer().assignedCountries.add(countryB);

                            // Check if defender is eliminated from game
                            if (countryB.getBelongsToPlayer().getAssignedCountries().size() == 0) {
                                GameView.displayLog(countryB.getBelongsToPlayer().getName() + "has no countries left, player looses the game and is eliminated");
                                playerList.remove(countryB.getBelongsToPlayer());
                            }

                            // Set country player to attacker
                            countryB.setBelongsToPlayer(countryA.getBelongsToPlayer());

                            //The attacking player must then place a number of armies
                            //in the conquered country which is greater or equal than the number of dice that was used in the attack that
                            //resulted in conquering the country
                            ArrayList<Integer> selectOptions = new ArrayList<>();
                            for (int i = attackerDice; i <= countryA.getCurrentArmiesDeployed() - 1; i++) {
                                selectOptions.add(i + 1);
                            }
                            int moveArmies = (Integer) JOptionPane.showInputDialog(gameView,
                                    "How many armies do you wish to move?",
                                    "Input", JOptionPane.OK_OPTION, BasicIconFactory.getMenuArrowIcon(), selectOptions.toArray(),
                                    selectOptions.get(0));
                            if (moveArmies > 0) {
                                countryA.subtractArmy(moveArmies);
                                countryB.addArmy(moveArmies);
                            }
                            hasCountryCaptured = true;
                            PlayerView playerView = new PlayerView();
                            PlayerModel playerModel = new PlayerModel();
                            playerModel.addObserver(playerView);
                            playerModel.getPlayerWorldDomination(playerList);
                        }
                        canReinforce = false;
                        GameView.updateMapPanel();
                        //Current Player cannot continue attack phase if none of his countries that have an adjacent country
                        //controlled by another player is containing more than one army
                        //TODO: check this condition.
                        for (Country c : currentPlayer.getAssignedCountries()) {
                            canAttack = false;
                            if (c.getCurrentArmiesDeployed() > 1) {
                                canAttack = true;
                                break;
                            }
                        }
                    } else {
                        GameView.displayLog(country1 + " is not the neighbor of " + country2 + ".");
                    }
                } else {
                    GameView.displayLog("You cannot attack your own country.");
                }
            } else {
                GameView.displayLog("You must have more than 1 army on " + country1 + " if you wish to attack from it.");
            }
        } else {
            GameView.displayLog("Can not attack right now.");
        }
    }

    /**
     * Handles the fortify function.
     * Fortifying allows the player to move armies from one country to another occupied
     * country once per turn.
     *
     * @param country1 is a String of the point A country.
     * @param country2 is a String of the point B country.
     **/
    public void fortify(String country1, String country2, GameView gameView) {

        countryA = MapModel.getCountryObj(country1, GameMap.getInstance());
        countryB = MapModel.getCountryObj(country2, GameMap.getInstance());

        if (canFortify) {

            if (currentPlayer.equals(countryA.getBelongsToPlayer()) && currentPlayer.equals(countryB.getBelongsToPlayer())) {
                // Check player owns countryA and countryB
                if (mapModel.isConnected(countryA, countryB)) {
                    // Check if countryA and countryB are adjacent
                    // If current player is Human
                    try {
                        // Player inputs how many armies to move from country A to country B
                        Integer[] optionArmies = new Integer[countryA.getCurrentArmiesDeployed() - 1];

                        for (int i = 0; i < optionArmies.length; i++) {
                            optionArmies[i] = i + 1;
                        }
                        int armies = (Integer) JOptionPane.showInputDialog(gameView, "Number of Armies to Move", "Input",
                                JOptionPane.YES_OPTION, BasicIconFactory.getMenuArrowIcon(), optionArmies, 1);

                        moveArmyFromTo(countryA, countryB, armies);
                        checkForCaptureCountryCard();
                        nextPlayerTurn();

                    } catch (NumberFormatException e) {
                        GameView.displayLog("Something went wrong ! ");
                    }
                } else {
                    GameView.displayLog("You cannot relocate armies right now.");
                }
            } else{
                GameView.displayLog("You should be owner of both the country. Please select countries which you are an occupant and has more than 1 army.");
            }
        }
    }

    private void checkForCaptureCountryCard() {

    }

    private void moveArmyFromTo(Country countryA, Country countryB, int armies) {
        // Decrements armies in country A and increments armies in country B
        GameView.displayLog(currentPlayer.getName() + " has chosen to fortify " + countryB.getCountryName() + " with " + armies + " armies from " + countryA.getCountryName() + ".");
        countryA.subtractArmy(armies);
        countryB.addArmy(armies);
    }

    private int getMaxNumberOfDicesForDefender(Country country) {
        return country.getCurrentArmiesDeployed() >= 2 ? 2 : 1;
    }

    /**
     * Return the maximum number of dices which can be rolled during an attack phase for the country
     * selected.
     *
     * @param country Country object to calculate the number of dice according to the armies in it.
     * @return max number of dices which can be rolled.
     */
    private int getMaxNumberOfDices(Country country) {
        int dices = 1;
        if (country.getCurrentArmiesDeployed() > 3) {
            dices = 3;
        } else if (country.getCurrentArmiesDeployed() == 3) {
            dices = 2;
        }
        return dices;
    }


    public void nextPlayerTurn() {
        if (playerList.size() > 1) {
            //if at least one player remains
            canReinforce = true;
            canAttack = false;
            canFortify = false;
            hasCountryCaptured = false;
            currentPlayer = playerList.get(playerIndex % playerList.size());
            currentPlayerReinforceArmies = getReinforcementArmyForPlayer(currentPlayer);
            currentPlayer.addArmy(currentPlayerReinforceArmies);
            playerIndex++;
            GameView.displayLog("\n\n===" + currentPlayer.getName() + " turn's start===");
        }
    }

    public void startGame() {
        Collections.shuffle(playerList);
        player.setPlayerList(playerList);
        GameMap.getInstance().setPlayerList(playerList);
        GameView.displayLog("The order of turns:");
        for (Player p : playerList) {
            GameView.displayLog(p.getName());
        }
        GameView.displayLog("All the players have been given the countries randomly and have assigned 1 initial armies from the total initial armies player gets.\n");
        GameView.displayLog("To begin: Start reinforcement phase by placing army in your designated country\n");
        nextPlayerTurn();
    }

    /**
     * Creates and returns the information for the cardsList in the BoardView.
     * @return a list of Strings to be displayed in the cardsList.
     **/
    protected ArrayList<String> getCardsList() {

        list = new ArrayList<String>();

        for (i = 0; i < currentPlayer.getHand().size(); i++) {

            list.add(currentPlayer.getHand().get(i).getName());
        }
        return list;
    }


    public ArrayList<String> getSelectedCountryList() {
        list = new ArrayList<>();
        for (Country c : currentPlayer.getAssignedCountries()) {
            list.add(c.getCurrentArmiesDeployed() + " : " + c.getCountryName());
        }
        return list;
    }

    /**
     * Creates and returns the information for the countryBList in the GameView.
     *
     * @return a list of Strings to be displayed in the countryBList.
     **/
    protected ArrayList<String> getCountryBList() {

        list = new ArrayList<>();

       /* for (Country c : gameMap.getCountryAndNeighborsMap().keySet()) {
            if (checkAdjacency(countryASelection, c.getCountryName())) {
                list.add(c.getCurrentArmiesDeployed() + "-" + c.getCountryName());
            }
        }*/
        return list;
    }

    /**
     * Getting the reinforcement army for player.
     *
     * @param p the p
     * @return the reinforcement army for player
     */
    public int getReinforcementArmyForPlayer(Player p) {

        int countArmy = 0;
        int countriesConquered = getCountriesConqueredBy(p).size();
        if (countriesConquered <= 11 && countriesConquered > 0) {
            countArmy = 3;
        } else {
            countArmy = countriesConquered / 3;
        }

        List<Continent> ruledContinents = getContinentsConqueredBy(p);
        for (Continent c : ruledContinents)
            countArmy += c.getControlValue();

        return countArmy;

    }

    /**
     * Getting the countries conquered by the player.
     *
     * @param p the player obj
     * @return the countries conquered by the player.
     */
    public List<Country> getCountriesConqueredBy(Player p) {
        return p.assignedCountries;
    }

    /**
     * Getting the continents conquered by the player.
     *
     * @param p the player obj
     * @return the continents conquered by the player.
     */
    public List<Continent> getContinentsConqueredBy(Player p) {
        List<Continent> lst = new ArrayList<>();
        for (Continent c : GameMap.getInstance().getContinentList()) {
            boolean isRuler = true;
            for (Country country : GameMap.getInstance().getCountriesByContinent(c.getContinentName())) {
                if (!country.getBelongsToPlayer().equals(p)) {
                    isRuler = false;
                    break;
                }
            }
            if (isRuler)
                lst.add(c);
        }
        return lst;
    }

}
