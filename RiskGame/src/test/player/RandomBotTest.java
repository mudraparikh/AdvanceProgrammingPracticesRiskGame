package test.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import riskModels.cards.Card;
import riskModels.cards.Deck;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.CheaterBot;
import riskModels.player.Player;
import riskModels.player.RandomBot;
import riskView.GameView;

public class RandomBotTest extends Player {

    private GameMap gameMap;
    private GameView gameView;
    private Player model;

    private ArrayList<String> playerNames;
    private ArrayList<String> playerTypes;


    private String filePath;
    String location = PlayerTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    /**
     * This method setting up the context as many test cases share the same values
     * In this we are assigning the London.map file
     * @throws Exception it throws if there are any exceptions found
     */
    @Before
    public void setUp() throws Exception {
        mapModel = new MapModel();
        filePath = location.replaceAll("/bin", "/res");
        File f = new File("/home/akshay/AdvanceProgrammingPracticesRiskGame/London.map");
        gameMap = mapModel.readMapFile("/home/akshay/AdvanceProgrammingPracticesRiskGame/RiskGame/London.map");
        playerNames = new ArrayList<String>();
        playerTypes = new ArrayList<String>();

        playerNames.add("John");
        playerNames.add("Alexa");
        playerNames.add("Penny");
        playerNames.add("Sheldon");
        playerNames.add("Raj");
        playerNames.add("Leonard");
        playerNames.add("Howard");
        playerTypes.add("Randomize Bot");
        playerTypes.add("Randomize Bot");
        playerTypes.add("Randomize Bot");
        playerTypes.add("Randomize Bot");
        playerTypes.add("Randomize Bot");
        playerTypes.add("Randomize Bot");
        drawTurns = 1000;
        //createGameMapFromFile(f);
    }


    @Test
    public void reinforceRandomTest() throws Exception{
        initializePlayerData(6, playerNames, playerTypes);
        playerCount = 6;
        setInitialArmies();
        allocateCountriesToPlayers();
        addInitialArmiesInRR();
        gameView = new GameView();
        gameView.setVisible(false);
        currentPlayer = getPlayerList().get(0);
        currentPlayerReinforceArmies = getReinforcementArmyForPlayer(currentPlayer);
        currentPlayer.addArmy(currentPlayerReinforceArmies);
        setStrategy(new RandomBot());
        rng = new Random();
        Country randomCountry = currentPlayer.getAssignedCountries().get(rng.nextInt(currentPlayer.getAssignedCountries().size()));
        int oldArmy = randomCountry.getCurrentArmiesDeployed();
        executeReinforce(randomCountry.getCountryName(), gameView, this);
        assertTrue(oldArmy <= randomCountry.getCurrentArmiesDeployed());
    }

    @Test
    public void attackRandomWithOneRandomLoopTest() throws Exception{
        initializePlayerData(6, playerNames, playerTypes);
        playerCount = 6;
        setInitialArmies();
        allocateCountriesToPlayers();
        addInitialArmiesInRR();
        gameView = new GameView();
        gameView.setVisible(false);
        currentPlayer = getPlayerList().get(0);
        setStrategy(new RandomBot());
        Country attackingCountry = currentPlayer.getAssignedCountries().get(0);
        Country defendingCountry = MapModel.getCountryObj(attackingCountry.getNeighborNodes().get(0).getCountryName(),GameMap.getInstance());
        defendingCountry.setBelongsToPlayer(getPlayerList().get(1));
        defendingCountry.setCurrentArmiesDeployed(1);

        attackingCountry.setCurrentArmiesDeployed(20);

        executeAttack(attackingCountry.getCountryName(),defendingCountry.getCountryName(),gameView,this);

        assertTrue(hasCountryCaptured);
    }

    @Test
    public void attackRandomWithMoreThanRandomLoopTest() throws Exception{
        initializePlayerData(6, playerNames, playerTypes);
        playerCount = 6;
        setInitialArmies();
        allocateCountriesToPlayers();
        addInitialArmiesInRR();
        gameView = new GameView();
        gameView.setVisible(false);
        currentPlayer = getPlayerList().get(0);
        setStrategy(new RandomBot());
        Country attackingCountry = currentPlayer.getAssignedCountries().get(0);
        Country defendingCountry = MapModel.getCountryObj(attackingCountry.getNeighborNodes().get(0).getCountryName(),GameMap.getInstance());
        defendingCountry.setBelongsToPlayer(getPlayerList().get(1));
        defendingCountry.setCurrentArmiesDeployed(50);

        attackingCountry.setCurrentArmiesDeployed(5);

        executeAttack(attackingCountry.getCountryName(),defendingCountry.getCountryName(),gameView,this);

        assertTrue(!hasCountryCaptured);
    }

    @Test
    public void fortificationRandomTest() throws Exception{
        initializePlayerData(6, playerNames, playerTypes);
        playerCount = 6;
        setInitialArmies();
        allocateCountriesToPlayers();
        addInitialArmiesInRR();
        gameView = new GameView();
        gameView.setVisible(false);
        currentPlayer = getPlayerList().get(0);
        currentPlayerReinforceArmies = getReinforcementArmyForPlayer(currentPlayer);
        currentPlayer.addArmy(currentPlayerReinforceArmies);
        setStrategy(new RandomBot());
        rng = new Random();
        Country randomCountry = currentPlayer.getAssignedCountries().get(rng.nextInt(currentPlayer.getAssignedCountries().size()));
        randomCountry.setCurrentArmiesDeployed(10);
        Country neighbor = MapModel.getCountryObj(randomCountry.getNeighborNodes().get(0).getCountryName(),GameMap.getInstance());
        executeFortification(randomCountry.getCountryName(),neighbor.getCountryName(),gameView,this);
        assertTrue(randomCountry.getCurrentArmiesDeployed() <= neighbor.getCurrentArmiesDeployed());
    }
}
