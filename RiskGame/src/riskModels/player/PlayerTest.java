package riskModels.player;

import org.junit.Before;
import org.junit.Test;
import riskModels.cards.Deck;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskView.GameView;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {

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
        filePath = "/home/akshay/AdvanceProgrammingPracticesRiskGame/RiskGame/";
        mapModel.readMapFile(filePath+"London.map");
        gameMap = GameMap.getInstance();
        model = new Player();
    }

    @Test
    public void checkDeckCardsPopulated(){
        Deck deck = new Deck((ArrayList<Country>) gameMap.getCountries());
        assertEquals(gameMap.getCountries().size(), deck.deck.size());
    }

    @Test
    public void testInitialisePlayerData(){
        model.initializePlayerData(6);
        assertEquals("John", model.getPlayerList().get(0).getName());
        assertEquals("Alexa", model.getPlayerList().get(1).getName());
        assertEquals("Penny", model.getPlayerList().get(2).getName());
        assertEquals("Sheldon", model.getPlayerList().get(3).getName());
        assertEquals("Amy", model.getPlayerList().get(4).getName());
        assertEquals("Raj", model.getPlayerList().get(5).getName());
    }

}