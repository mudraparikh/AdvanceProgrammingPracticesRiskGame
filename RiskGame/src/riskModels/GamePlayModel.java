package riskModels;

import riskModels.continent.Continent;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.Player;
import riskModels.player.PlayerModel;
import riskView.GameView;

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

    private int playerIndex = 0;
    private MapModel mapModel;
    private GameMap gameMap;
    private List<Player> playerList;
    private Player player;
    private Player currentPlayer;
    private int currentPlayerReinforceArmies;
    private int playerCount;
    private ArrayList<String> list;
    private Country countryA;
    private Country countryB;

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
                        if (armies !=null){
                            currentPlayer.subArmy(armies);
                            countryA.addArmy(armies);
                            GameView.displayLog(currentPlayer.getName() + " has chosen to reinforce " + countryA.getCountryName() + " with " + armies + " armies.");
                        }

                    } else if (currentPlayer.getTotalArmies() == 0) {
                        canAttack = true;
                        canFortify = true;
                        GameView.displayLog("You do not have any armies to reinforce");
                    }

                } catch (NumberFormatException e) {
                    GameView.displayLog("System Error or Exception is thrown for reinforce method");
                }
            }
        }
    }
/*
    *//**
     * Handles the attack function.
     * Attacking allows the player to engage in battles, with outcomes decided by RNG, with
     * opposing players in order to lower the number of armies in a territory to 0 in order
     * to occupy it.
     *
     * @param countryA is a String of the point A country.
     * @param countryB is a String of the point B country.
     **//*
    protected void attack(String countryAName, String countryBName) {

        countryA = board.getCountryByName(countryAName);
        countryB = board.getCountryByName(countryBName);

        if (canAttack == true || isAI == true) {

            if (!currentPlayer.equals(countryB.getOccupant())) {
                // Check if countryB is occupied by an opponent
                if (board.checkAdjacency(countryA.getName(), countryB.getName()) == true) {
                    // Check if countryA is adjacent to countryB

                    dice = new Dice();

                    // Set default values
                    attackerLosses = 0;
                    defenderLosses = 0;
                    attackerDice = 1;
                    defenderDice = 1;
                    isInt = false;

                    if (isAI == true) {
                        rng = new Random();
                        if (countryA.getArmies() <= 3) {
                            attackerDice = 1;
                        } else {
                            attackerDice = rng.nextInt(2) + 1;
                        }
                    } else {
                        // If current player is Human
                        try {
                            // Attacker chooses how many dice to roll

                            attackerDice = Integer.parseInt(JOptionPane.showInputDialog(countryA.getOccupant().getName() + ", you are attacking " + countryAName + " from " + countryBName + "! How many dice will you roll?"));

                            if (attackerDice < 1 || attackerDice > 3 || attackerDice >= countryA.getArmies()) {
                                throw new IllegalArgumentException();
                            }
                            isInt = true;

                        } catch (NumberFormatException e) {
                            // Error: attacker inputs non-integer
                            System.out.println("Commander, please take this seriously. We are at war.");

                        } catch (IllegalArgumentException e) {
                            // Error: attacker inputs invalid number of dice
                            System.out.println("Roll 1,2 or 3 dice. You must have at least one more army in your country than the number of dice you roll.");
                        }
                    }
                    if (isInt == true || currentPlayer.getAI() == true) {
                        attackerRolls = dice.roll(attackerDice);
                        isInt = false;
                        if (countryB.getOccupant().getAI() == true) {
                            // If the current player is AI
                            rng = new Random();
                            if (countryB.getArmies() <= 1) {
                                defenderDice = 1;
                            } else {
                                defenderDice = rng.nextInt(1) + 1;
                            }
                        } else {
                            // If current player is Human
                            while (isInt == false) {
                                try {
                                    // Defender chooses how many dice to roll after attacker
                                    defenderDice = Integer.parseInt(JOptionPane.showInputDialog(countryB.getOccupant().getName() + ", you are defending " + countryBName + " from " + countryA.getOccupant().getName() + "! How many dice will you roll?"));

                                    if (defenderDice < 1 || defenderDice > 2 || defenderDice > countryA.getArmies()) {
                                        throw new IllegalArgumentException();
                                    }
                                    isInt = true;

                                } catch (NumberFormatException e) {
                                    // Error: defender inputs non-integer
                                    System.out.println("Commander, please take this seriously. We are at war.");

                                } catch (IllegalArgumentException e) {
                                    // Error: defender inputs invalid number of dice
                                    System.out.println("Roll either 1 or 2 dice. To roll 2 dice, you must have at least 2 armies on your country.");
                                }
                            }
                        }
                        if (isInt == true || countryB.getOccupant().getAI() == true) {
                            defenderRolls = dice.roll(defenderDice);
                            // Rolls arrays have been ordered in descending order. Index 0 = highest pair
                            if (attackerRolls[0] > defenderRolls[0]) {
                                defenderLosses++;
                            } else if (attackerRolls[0] < defenderRolls[0]) {
                                attackerLosses++;
                            }
                            // Index 1 = second highest pair
                            if (attackerDice > 1 && defenderDice > 1) {

                                if (attackerRolls[1] > defenderRolls[1]) {
                                    defenderLosses++;

                                } else if (attackerRolls[1] < defenderRolls[1]) {
                                    attackerLosses++;
                                }
                            }
                            // Calculate losses
                            System.out.println("<COMBAT REPORT>");
                            countryA.decrementArmies(attackerLosses);
                            countryB.decrementArmies(defenderLosses);

                            // If defending country loses all armies
                            if (countryB.getArmies() < 1) {

                                System.out.println("WORLD NEWS: " + countryA.getOccupant().getName() + " has defeated all of " + countryB.getOccupant().getName() + "'s armies in " + countryBName + " and has occupied the country!");

                                // Remove country from defender's list of occupied territories and adds to attacker's list
                                countryB.getOccupant().removeCountry(countryBName);
                                countryA.getOccupant().addCountry(countryB);

                                // Check if defender is eliminated from game
                                if (countryB.getOccupant().getOwnedCountries().size() == 0) {

                                    System.out.println("WORLD NEWS: " + countryB.getOccupant().getName() + " has surrendered to " + currentPlayer.getName() + " after his last military defeat at " + countryBName + ". " + currentPlayer.getName() + " has issued an execution, with " + countryB.getOccupant().getName() + " charged as a war criminal.");

                                    players.remove(countryB.getOccupant().getIndex());
                                }
                                // Set country occupant to attacker
                                countryB.setOccupant(countryA.getOccupant());
                                countryA.decrementArmies(1);
                                countryB.incrementArmies(1);

                                if (isAI == false) {
                                    setChanged();
                                    notifyObservers("countryA");
                                }
                            }
                            canReinforce = false;
                        }
                    }
                } else {
                    System.out.println("Commander, " + countryAName + " is not adjacent to " + countryBName + ".");
                }
            } else {
                System.out.println("Commander, you cannot attack your own territories.");
            }
        } else {
            System.out.println("Commander, our forces are not prepared to launch an attack right now.");
        }
    }

    */

    /**
     * Handles the fortify function.
     * Fortifying allows the player to move armies from one country to another occupied
     * country once per turn.
     *
     * @param countryA is a String of the point A country.
     * @param countryB is a String of the point B country.
     **//*
    protected void fortify(String countryAName, String countryBName) {

        countryA = board.getCountryByName(countryAName);
        countryB = board.getCountryByName(countryBName);

        if (canFortify == true || currentPlayer.getAI() == true) {

            if (currentPlayer.equals(countryA.getOccupant()) && currentPlayer.equals(countryB.getOccupant())) {
                // Check player owns countryA and countryB
                if (board.checkAdjacency(countryAName, countryBName) == true) {
                    // Check if countryA and countryB are adjacent
                    isInt = false;

                    if (isAI == true) {
                        // If current player is AI
                        rng = new Random();
                        System.out.println(countryA.getArmies());
                        armies = rng.nextInt(countryA.getArmies());
                        if (countryA.getArmies() > 0 && armies == 0) {
                            armies = 1;
                        }
                    } else {
                        // If current player is Human
                        try {
                            // Player inputs how many armies to move from country A to country B
                            armies = Integer.parseInt(JOptionPane.showInputDialog("Commander, how many armies from " + countryAName + " do you wish to send to fortify " + countryBName + "?"));
                            isInt = true;

                        } catch (NumberFormatException e) {
                            System.out.println("Commander, please take this seriously. We are at war.");
                        }
                    }
                    // Decrements armies in country A and increments armies in country B
                    if (isInt == true || currentPlayer.getAI() == true) {

                        if (countryA.getArmies() >= armies) {
                            System.out.println(currentPlayer.getName() + " has chosen to fortify " + countryBName + " with " + armies + " armies from " + countryAName + ".");

                            countryA.decrementArmies(armies);
                            countryB.incrementArmies(armies);

                            if (isAI == false) {
                                setChanged();
                                notifyObservers("countryA");
                            }
                            nextPlayer();

                        } else {
                            System.out.println("Commander, you do not have enough armies in " + countryAName + " to fortify " + countryBName + " with " + armies + " armies.\nNumber of armies in " + countryAName + ": " + countryA.getArmies());
                        }
                    }
                } else {
                    System.out.println("Commander, " + countryAName + " is not adjacent to " + countryBName + ".");
                }
            } else {
                System.out.println("Commander, you do not occupy both " + countryAName + " and " + countryBName + ".");
            }
        } else {
            System.out.println("Commander, we can't relocate troops right now.");
        }
    }*/
    public void nextPlayerTurn() {
        if (playerList.size() > 1) {
            //if at least one player remains
            canReinforce = true;
            canAttack = false;
            canFortify = false;
            currentPlayer = playerList.get(playerIndex % playerList.size());
            currentPlayerReinforceArmies = getReinforcementArmyForPlayer(currentPlayer);
            currentPlayer.addArmy(currentPlayerReinforceArmies);
            playerIndex++;
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

        for (Country c : gameMap.getCountryAndNeighborsMap().keySet()) {
           /* if (checkAdjacency(countryASelection, c.getCountryName())) {
                list.add(c.getCurrentArmiesDeployed() + "-" + c.getCountryName());
            }*/
        }
        return list;
    }

    public boolean checkAdjacency(String countryA, String countryB) {
        return GameMap.getInstance().getCountryAndNeighborsMap().get(new Country(countryA)).contains(new Country(countryB));

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

        List<Continent> ruledContinents = getContinentsCounqueredBy(p);
        for (Continent c : ruledContinents)
            countArmy += c.getControlValue();

        return countArmy;

    }

    /**
     * Geting the countries conquered by.
     *
     * @param p the p
     * @return the countries conquered by
     */
    public List<Country> getCountriesConqueredBy(Player p) {
        return p.getAssignedCountries();
    }

    /**
     * Getting the continents counquered by.
     *
     * @param p the p
     * @return the continents counquered by
     */
    public List<Continent> getContinentsCounqueredBy(Player p) {
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
