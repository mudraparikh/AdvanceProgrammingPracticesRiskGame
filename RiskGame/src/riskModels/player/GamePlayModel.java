package riskModels.player;

import riskModels.cards.Card;
import riskModels.cards.Deck;
import riskModels.continent.Continent;
import riskModels.country.Country;
import riskModels.dice.Dice;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskView.CardView;
import riskView.GameView;
import riskView.PlayerView;

import javax.swing.*;
import javax.swing.plaf.basic.BasicIconFactory;
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
    private boolean canEndTurn;
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
    private int turnInCount;
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
            this.playerCount = playerCount;
            setInitialArmies();
            allocateCountriesToPlayers();
            addInitialArmiesInRR();
            PlayerObserverModel playerModel = new PlayerObserverModel();
            playerModel.getPlayerWorldDomination(player.getPlayerList());
            canTurnInCards = false;
            canReinforce = true;
            canAttack = false;
            canFortify = false;
            canEndTurn = false;

        } else {
            System.out.println("Something went wrong ! ");
            System.exit(1);
        }
    }

    /**
     * This method will add initial armies to the country of the player in round robin fashion
     */
    public void addInitialArmiesInRR() {
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

    /**
     * This method will allocate the countries to players
     */
    public void allocateCountriesToPlayers() {
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

    /**
     * This will set the total number of armies for the player during the start-up phase
     */
    public void setInitialArmies() {
        for (Player p : player.getPlayerList()) {
            p.setTotalArmies(getInitialArmyCount());
        }
    }

    /**
     * This method will calculate the initial total armies based upon the number of players
     * @return number of armies based on number of players
     */
    public int getInitialArmyCount() {
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

    /**
     * Will create the players object based on the number of player selected from the player count dialog box
     * @param playerCount Number of players selected from the dialog box
     */
    public void initializePlayerData(int playerCount) {
        String playersName[] = {"John", "Alexa", "Penny", "Sheldon", "Amy", "Raj"};
        playerList = new ArrayList<>();
        int i = 0;
        while (i < playerCount) {
            playerList.add(new Player(playersName[i]));
            i++;
        }
        player = new Player(playerList);
        player.setPlayerList(playerList);
        GameMap.getInstance().setPlayerList(playerList);
    }

   /**
    * Handles placing reinforcements.
    * @param country is a String of the country in which the reinforcements will be placed
    * @param gameView has the details to load the game board
    */
    public void reinforce(String country, GameView gameView) {

        countryA = MapModel.getCountryObj(country, GameMap.getInstance());
        if (canReinforce) {
            assert countryA != null;
            if (currentPlayer.equals(countryA.getBelongsToPlayer())) {
                GameView.displayLog(currentPlayer.name + " gets " + currentPlayerReinforceArmies + " armies");
                try {
                    if (currentPlayer.getTotalArmies() > 0) {
                        // Player inputs how many armies to reinforce selected country
                        Integer armies = showReinforceArmiesDialogBox(gameView);

                        // Subtracts player armies and adds armies to country
                        if (armies != null) {
                            currentPlayer.subArmy(armies);
                            countryA.addArmy(armies);
                            GameView.displayLog(currentPlayer.getName() + " has chosen to reinforce " + countryA.getCountryName() + " with " + armies + " armies.");
                            if (currentPlayer.getTotalArmies() == 0) {
                                canAttack = true;
                                canFortify = true;
                                canEndTurn = true;
                                GameView.displayLog("You do not have any armies left to reinforce");
                            }
                        }
                        GameView.updateMapPanel();

                    } else if (currentPlayer.getTotalArmies() == 0) {
                        canAttack = true;
                        canFortify = true;
                        canEndTurn = true;
                        GameView.displayLog("You do not have any armies left to reinforce");
                    }

                } catch (NumberFormatException e) {
                    GameView.displayLog("System Error or Exception is thrown for reinforce method");
                }
            }
        }
    }

    private Integer showReinforceArmiesDialogBox(GameView gameView) {
        Integer[] selectOptions = new Integer[currentPlayer.getTotalArmies()];
        for (int i = 0; i < currentPlayer.getTotalArmies(); i++) {
            selectOptions[i] = i + 1;
        }
        return (Integer) JOptionPane.showInputDialog(gameView,
                "How many armies do you wish to send to reinforce " + countryA.getCountryName() + "?",
                "Input", JOptionPane.OK_OPTION, BasicIconFactory.getMenuArrowIcon(), selectOptions,
                selectOptions[0]);

    }


    /**
     * Handles the attack function.
     * Attacking allows the player to engage in battles, with outcomes decided by RNG, with
     * opposing players in order to lower the number of armies in a territory to 0 in order
     * to occupy it.
     *
     * @param country1 is a String of the point A country
     * @param country2 is a String of the point B country
     * @param gameView has the details to load the game board
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

                                //Attacker will get all the cards of the defender as defender has lost all of it's countries
                                List<Card> listOfDefenderCards = countryB.getBelongsToPlayer().getHand();
                                for (Card card : listOfDefenderCards)
                                    countryA.getBelongsToPlayer().addRiskCard(card);

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
                            PlayerObserverModel playerModel = new PlayerObserverModel();
                            playerModel.addObserver(playerView);
                            playerModel.getPlayerWorldDomination(playerList);
                        }
                        canReinforce = false;
                        canEndTurn = true;
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
     * @param country1 is a String of the point A country
     * @param country2 is a String of the point B country
     * @param gameView has the details to load game board
     * @param model its a player object
     */
    public void fortify(String country1, String country2, GameView gameView, Player model) {

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
                        checkHasCountryCaptured();
                        nextPlayerTurn(model);

                    } catch (NumberFormatException e) {
                        GameView.displayLog("Something went wrong ! ");
                    }
                } else {
                    GameView.displayLog("You cannot relocate armies right now.");
                }
            } else {
                GameView.displayLog("You should be owner of both the country. Please select countries which you are an occupant and has more than 1 army.");
            }
        }
    }

    /**
     * Handles turning in Risk cards.
     *
     * @param cardsToRemove is an integer array of the indexes of cards to be removed.
     **/
    public void turnInCards(int[] cardsToRemove) {

        if (canTurnInCards) {
            if (cardsToRemove.length == 3) {
                if (currentPlayer.getHandObject().canTurnInCards(cardsToRemove[0], cardsToRemove[1], cardsToRemove[2])) {
                    /* if (currentPlayer.getHand().get(cardsToRemove[0]).getCountry().getBelongsToPlayer().equals(currentPlayer) || currentPlayer.getHand().get(cardsToRemove[1]).getCountry().getBelongsToPlayer().equals(currentPlayer) || currentPlayer.getHand().get(cardsToRemove[2]).getCountry().getBelongsToPlayer().equals(currentPlayer)) {
                    // Checks if player owns a country on the cards to remove
                    currentPlayerReinforceArmies+=2;
                }*/
                    turnInCount = currentPlayer.getTurnInCount();
                    // Increments armies according to how many turn-ins have occurred
                    currentPlayerReinforceArmies += (5 * turnInCount);
                    currentPlayer.removeCards(cardsToRemove);
                    setChanged();
                    notifyObservers("cards");

                } else {
                    GameView.displayLog("You must trade in three cards of the same type or one of each three types.");
                }
            } else {
                System.out.println("You must trade in three cards of the same type or one of each three types.");
            }
        } else {
            System.out.println("You can't turn in cards right now.");
        }
    }

    /**
     * This will assign the Card if attacker has captured atleast one country in the
     * attack phase based upon the <code>hasCountryCaptured</code> variable boolean
     */
    public void checkHasCountryCaptured() {
        if (hasCountryCaptured) {
            currentPlayer.addRiskCard(deck.draw());
            setChanged();
            notifyObservers(GamePlayModel.class);
        }
        hasCountryCaptured = false;
    }

    /**
     * This will end the player's turn and assign the card if player decided to skip Foritification phase.
     * @param model <code>GamePlayModel.class</code>
     */
    public void endPlayerTurn(Player model) {
        if (canEndTurn) {
            checkHasCountryCaptured();
            nextPlayerTurn(model);
        } else {
            GameView.displayLog("You cannot end turn without playing reinforcement phase, atleast !");
        }
    }

    /**
     * Move the army from one country to another.
     * Usually is called when fortification phase
     * @param countryA Country from which army is to be moved
     * @param countryB Country to which army is to be added
     * @param armies the number of armies to allocate
     */
    public void moveArmyFromTo(Country countryA, Country countryB, int armies) {
        // Decrements armies in country A and increments armies in country B
        GameView.displayLog(currentPlayer.getName() + " has chosen to fortify " + countryB.getCountryName() + " with " + armies + " armies from " + countryA.getCountryName() + ".");
        countryA.subtractArmy(armies);
        countryB.addArmy(armies);
    }

    /**
     * gets the max number of dices for defender country based upon the no. of armies present in
     * the country at that time
     * @param country Defender's defending country
     * @return total number of dices to be used
     */
    public int getMaxNumberOfDicesForDefender(Country country) {
        return country.getCurrentArmiesDeployed() >= 2 ? 2 : 1;
    }

    /**
     * Return the maximum number of dices which can be rolled during an attack phase for the country
     * selected.
     *
     * @param country Country object to calculate the number of dice according to the armies in it.
     * @return max number of dices which can be rolled.
     */
    public int getMaxNumberOfDices(Country country) {
        int dices = 1;
        if (country.getCurrentArmiesDeployed() > 3) {
            dices = 3;
        } else if (country.getCurrentArmiesDeployed() == 3) {
            dices = 2;
        }
        return dices;
    }

    /**
     * Reset all the boolean values and set <code>currentPlayer</code> value to next player.
     * It will also calculate the total reinforcement armies needed.
     * @param model <code>GamePlayModel.class</code> object to passed for Card View Observable
     */
    public void nextPlayerTurn(Player model) {
        if (playerList.size() > 1) {
            //if at least one player remains
            canReinforce = false;
            canAttack = false;
            canFortify = false;
            canTurnInCards = false;
            canEndTurn = false;
            hasCountryCaptured = false;
            currentPlayer = playerList.get(playerIndex % playerList.size());
            currentPlayerReinforceArmies = getReinforcementArmyForPlayer(currentPlayer);
            currentPlayer.addArmy(currentPlayerReinforceArmies);
            playerIndex++;
            GameView.displayLog("\n\n===" + currentPlayer.getName() + " turn's start===");
            if (currentPlayer.mustTurnInCards()) {
                // While player has 5 or more cards
                GameView.displayLog("Your hand is full. Trade in cards for reinforcements to continue.");
                // GamePlayModel model = new GamePlayModel();
                CardView cardview = new CardView(model, "cards");
                model.addObserver(cardview);
                model.showCard();
                canTurnInCards = true;
                canReinforce = false;
            } else {
                canReinforce = true;
            }

        }
    }

    /**
     * WIll notify all the observers for any card exchanges
     */
    public void showCard() {
        setChanged();
        notifyObservers();
    }

   /**
    * Starts the Game.
    * Shuffles the players.
    * @param model its a player object
    */
    public void startGame(Player model) {
        Collections.shuffle(playerList);
        player.setPlayerList(playerList);
        GameMap.getInstance().setPlayerList(playerList);
        GameView.displayLog("The order of turns:");
        for (Player p : playerList) {
            GameView.displayLog(p.getName());
        }
        GameView.displayLog("All the players have been given the countries randomly and have assigned 1 initial armies from the total initial armies player gets.\n");
        GameView.displayLog("To begin: Start reinforcement phase by placing army in your designated country\n");
        nextPlayerTurn(model);
    }

    /**
     * Creates and returns the information for the cardsList in the BoardView.
     *
     * @return a list of Strings to be displayed in the cardsList.
     **/
    public ArrayList<String> getCardsList() {

        list = new ArrayList<String>();

        for (i = 0; i < currentPlayer.getHandObject().getCards().size(); i++) {

            list.add(currentPlayer.getHandObject().getCards().get(i).getName());
        }
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
