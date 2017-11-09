package riskModels.player;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import riskModels.cards.Deck;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskView.GameView;

public class PlayerTest extends Player {

    private GameMap gameMap;
    private MapModel mapModel;
    private GameView gameView;
    private Player model;

    private String filePath;
    String location = PlayerTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    @Before
    public void setUp() throws Exception {
        mapModel = new MapModel();
        filePath=location.replaceAll("/bin", "/res");
        File f = new File("C:\\Users\\Mudra-PC\\git\\APPRiskGame\\RiskGame\\London.map");
        gameMap = GameMap.getInstance();
        createGameMapFromFile(f);
    }

    @Test
    public void checkDeckCardsPopulated(){
        Deck deck = new Deck((ArrayList<Country>) gameMap.getCountries());
        assertEquals(gameMap.getCountries().size(), deck.deck.size());
    }

    @Test
    public void testInitialisePlayerData(){
        initializePlayerData(6);
        assertEquals("John", getPlayerList().get(0).getName());
        assertEquals("Alexa", getPlayerList().get(1).getName());
        assertEquals("Penny", getPlayerList().get(2).getName());
        assertEquals("Sheldon", getPlayerList().get(3).getName());
        assertEquals("Amy", getPlayerList().get(4).getName());
        assertEquals("Raj", getPlayerList().get(5).getName());
    }
    
    @Test
    public void testInitalArmy() {
    	playerCount = 3;
    	getInitialArmyCount();
    	assertEquals(35,playerList.get(0).totalArmies);
    }
}