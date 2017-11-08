package riskModels.player;

import riskModels.cards.Card;
import riskModels.cards.Deck;
import riskModels.cards.Hand;
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

/**
 * This is bean class to set and get properties of player.
 *
 * @author akshay shah
 */
public class Player extends Observable {

    public boolean canTurnInCards;
    public boolean canReinforce;
    public boolean canAttack;
    public boolean canFortify;
    public boolean canEndTurn;
    public boolean hasCountryCaptured;
    public boolean hasPlayerWon;

    public String name;

    public int playerIndex = 0;
    public int i;
    public int currentPlayerReinforceArmies;
    public int playerCount;
    public int attackerLosses;
    public int defenderLosses;
    public int attackerDice;
    public int defenderDice;
    public int totalArmies;
    public int reinforcementArmies;
    public int turnInCount;

    public double domination; // player's domination in game based on number of countries out of total countries player own

    public int[] attackerRolls;
    public int[] defenderRolls;

    public MapModel mapModel;
    public GameMap gameMap;

    public Dice dice;
    public Deck deck;

    public Player player;
    public Player currentPlayer;

    public Country countryA;
    public Country countryB;

    public ArrayList<String> list;

    public List<Country> assignedCountries;

    public List<Player> playerList;

    public Hand hand;

    public Player(String name) {
        super();
        this.name = name;
        assignedCountries = new ArrayList<>();
        hand = new Hand();
        turnInCount = 0;
    }

    /**
     * setter method assigns list of players
     *
     * @param playerList list of player object
     */
    public Player(List<Player> playerList) {
        this.playerList = playerList;
    }

    /**
     * default constructor
     */
    public Player() {
        // TODO Auto-generated constructor stub
    }

    /**
     * getter method to get the list of current players
     */
    public List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * setter method to assign list of players
     *
     * @param playerList list contains players name
     */
    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    /**
     * getter method gives value of reinforcement army
     *
     * @return value of reinforcement army
     */
    public int getReinforcementArmies() {
        return reinforcementArmies;
    }

    /**
     * setter method assigns reinforcement army value
     *
     * @param reinforcementArmies player object
     */
    public void setReinforcementArmies(int reinforcementArmies) {
        this.reinforcementArmies = reinforcementArmies;
    }

    /**
     * getter method gives the player name
     *
     * @return name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * setter method assigns name of the player
     *
     * @param name players name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter method gives the total number of armies assigned to the player
     *
     * @return number of armies assigned to the player
     */
    public int getTotalArmies() {
        return totalArmies;
    }

    /**
     * setter method to assign total number armies to the player
     *
     * @param totalArmies player object
     */
    public void setTotalArmies(int totalArmies) {
        this.totalArmies = totalArmies;
    }

    /**
     * getter method gives the total number of countries assigned to the player
     *
     * @return number of countries assigned
     */
    public List<Country> getAssignedCountries() {
        return assignedCountries;
    }

    /**
     * setter method to assign countries to the player
     *
     * @param assignedCountries number of countries assigned to the player
     */
    public void setAssignedCountries(List<Country> assignedCountries) {
        this.assignedCountries = assignedCountries;
    }

    /**
     * setter method is used to add the army
     *
     * @param n adds n number of army to the existing army
     */
    public void addArmy(int n) {
        this.totalArmies += n;
    }

    /**
     * setter method is used to reduce the army
     *
     * @param n reduces n number of army from total army
     */
    public void subArmy(int n) {
        this.totalArmies -= n;
    }

    /**
     * getter method to know which player domination
     *
     * @return players name who has the domination
     */
    public double getDomination() {
        return domination;
    }

    /**
     * setter method to assign domination of player
     *
     * @param domination player object
     */
    public void setDomination(double domination) {
        this.domination = domination;
    }

    /**
     * Adds a risk card to the players hand
     **/
    public void addRiskCard(Card riskCard) {

        hand.add(riskCard);
    }

    /**
     * Removed a set of risk cards from the players hand to reflect risk cards being turned in
     **/
    public void removeCards(int[] cardsTurnedInIndex) {

        hand.removeCardsFromHand(cardsTurnedInIndex[0], cardsTurnedInIndex[1], cardsTurnedInIndex[2]);
    }

    public int getTurnInCount() {

        turnInCount++;
        return turnInCount;
    }

    public ArrayList<Card> getHand() {

        return hand.getCards();
    }

    public Hand getHandObject() {

        return hand;
    }

    public boolean mustTurnInCards() {

        return hand.mustTurnInCards();
    }

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
            hasPlayerWon = false;

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

    public Integer showReinforceArmiesDialogBox(GameView gameView) {
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
                            attackerDice = showAttackerDiceDialogBox(gameView);
                        } catch (IllegalArgumentException e) {
                            // Error: attacker inputs invalid number of dice
                            GameView.displayLog("Roll 1,2 or 3 dice. You must have at least one more army in your country than the number of dice you roll.");
                        }

                        try {
                            // Defender chooses how many dice to roll after attacker
                            defenderDice = showDefenderDiceDialogBox(gameView);
                        } catch (IllegalArgumentException e) {
                            // Error: defender inputs invalid number of dice
                            GameView.displayLog("Roll either 1 or 2 dice. To roll 2 dice, you must have at least 2 armies on your country.");
                        }
                        attackerRolls = dice.rollDice(attackerDice).getDiceResult();
                        defenderRolls = dice.rollDice(defenderDice).getDiceResult();

                        for (int attackerRoll : attackerRolls) {
                            GameView.displayLog(" " + attackerRoll + " ");
                        }

                        for (int defenderRoll: defenderRolls){
                            GameView.displayLog(" "+defenderRoll+ " ");
                        }
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
                            int moveArmies = showMoveArmiesToCaptureCountryDialogBox(gameView);
                            if (moveArmies > 0) {
                                countryA.subtractArmy(moveArmies);
                                countryB.addArmy(moveArmies);
                            }
                            hasCountryCaptured = true;
                            if(currentPlayer.assignedCountries.size() == GameMap.getInstance().getCountries().size()){
                                hasPlayerWon = true;
                            }
                            PlayerView playerView = new PlayerView();
                            PlayerObserverModel playerModel = new PlayerObserverModel();
                            playerModel.addObserver(playerView);
                            playerModel.getPlayerWorldDomination(playerList);
                        }
                        if(hasPlayerWon){
                            GameView.displayLog(""+currentPlayer.getName()+" has won the game ! Congratulations ! ");
                            canAttack = false;
                            canReinforce = false;
                            canEndTurn = false;
                            canFortify = false;
                            canTurnInCards = false;
                        }
                        else{
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

    private int showDefenderDiceDialogBox(GameView gameView) {
        Integer[] selectOptions = new Integer[getMaxNumberOfDicesForDefender(countryB)];
        for (int i = 0; i < getMaxNumberOfDicesForDefender(countryB); i++) {
            selectOptions[i] = i + 1;
        }
        return (Integer) JOptionPane.showInputDialog(gameView,
                countryB.getBelongsToPlayer().getName() + ", you are defending " + countryB.getCountryName() + " from " + countryA.getBelongsToPlayer().getName() + "! How many dice will you roll?",
                "Input", JOptionPane.OK_OPTION, BasicIconFactory.getMenuArrowIcon(), selectOptions,
                selectOptions[0]);
    }

    private int showMoveArmiesToCaptureCountryDialogBox(GameView gameView) {

        ArrayList<Integer> selectOptions = new ArrayList<>();
        for (int i = attackerDice; i <= countryA.getCurrentArmiesDeployed() - 1; i++) {
            selectOptions.add(i + 1);
        }
        return (Integer) JOptionPane.showInputDialog(gameView,
                "How many armies do you wish to move?",
                "Input", JOptionPane.OK_OPTION, BasicIconFactory.getMenuArrowIcon(), selectOptions.toArray(),
                selectOptions.get(0));
    }

    private int showAttackerDiceDialogBox(GameView gameView) {
        Integer[] selectOptions = new Integer[getMaxNumberOfDices(countryA)];
        for (int i = 0; i < getMaxNumberOfDices(countryA); i++) {
            selectOptions[i] = i + 1;
        }
        return (Integer) JOptionPane.showInputDialog(gameView,
                countryA.getBelongsToPlayer().getName() + ", is attacking " + countryB.getCountryName() + " from " + countryA.getCountryName() + "! How many dice will you roll?",
                "Input", JOptionPane.OK_OPTION, BasicIconFactory.getMenuArrowIcon(), selectOptions,
                selectOptions[0]);
    }

    /**
     * Handles the fortify function.
     * Fortifying allows the player to move armies from one country to another occupied
     * country once per turn.
     *
     * @param country1 is a String of the point A country.
     * @param country2 is a String of the point B country.
     **/
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
            notifyObservers(Player.class);
        }
        hasCountryCaptured = false;
    }

    /**
     * This will end the player's turn and assign the card if player decided to skip Foritification phase.
     * @param model <code>Player.class</code>
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
     * @param model <code>Player.class</code> object to passed for Card View Observable
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
            updatePhaseDetails("\n\n===" + currentPlayer.getName() + " turn's start===");
            if (currentPlayer.mustTurnInCards()) {
                // While player has 5 or more cards
                GameView.displayLog("Your hand is full. Trade in cards for reinforcements to continue.");
                // Player model = new Player();
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
     * @param model
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
        updatePhaseDetails("Reinforcement Phase");
        
        nextPlayerTurn(model);
    }

    public static void updatePhaseDetails(String messageToUpdate) {
    	PlayerObserverModel obsModel = new PlayerObserverModel();
        PlayerView playerView = new PlayerView();
        obsModel.addObserver(playerView);
        obsModel.showPhaseDetails(messageToUpdate);
		
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

