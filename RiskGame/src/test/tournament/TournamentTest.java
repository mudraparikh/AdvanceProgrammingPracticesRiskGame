package test.tournament;

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

import riskControllers.GamePlayController;
import riskModels.cards.Card;
import riskModels.cards.Deck;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.CheaterBot;
import riskModels.player.Player;
import riskModels.player.RandomBot;
import riskView.GameView;
import riskView.TournamentView;
import test.player.PlayerTest;
/**
 * Test Cases for the tournament mode.
 * @author prashantp95
 *
 */
public class TournamentTest extends Player {


    private GameMap gameMap;
    private GameView gameView;
    private Player model;

    private ArrayList<String> playerNames;
    private ArrayList<String> playerTypes;

    private List<File> fileList;

    private String filePath;
    String location = PlayerTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    /**
     * This method setting up the context as many test cases share the same values
     * In this we are assigning the London.map file
     * @throws Exception it throws if there are any exceptions found
     */
    @Before
    public void setUp() throws Exception {
    	Player.isTournamentMode=true;
        mapModel = new MapModel();
        fileList = new ArrayList<>();
        filePath = location.replaceAll("/bin", "/res");
        File f = new File("C:\\Users\\prashantp95\\Dropbox\\APP\\TB\\Build3Grading.SOEN6441.2017.2\\World\\World.map");
       // File f2 = new File("/home/akshay/AdvanceProgrammingPracticesRiskGame/3d_cliff.map");
        playerNames = new ArrayList<String>();
        playerTypes = new ArrayList<String>();

        playerNames.add("Aggressive");
        playerNames.add("Benevolent");
        playerNames.add("Random");
        playerNames.add("Cheater");
        playerTypes.add("Aggressive Bot");
        playerTypes.add("Benevolent Bot");
        playerTypes.add("Randomize Bot");
        playerTypes.add("Cheater Bot");
        fileList.add(f);
     
    }
    /**
     * Testing if game result turns draw if there is low number of draw turn
     * If  drawturns number is less , there is high probability that game will end in draw.   
     */
    @Test
    public void tournamentGameWithDrawTurnsWithThreeGames(){
        for (File mapFile : fileList) {
            Player model = new Player();
            for (int i = 1; i <= 3; i++) {
                model.initData(mapFile,playerNames.size(),playerNames,playerTypes,true);
                model.setDrawTurns(3);
                try {
                    gameView = new GameView();
                    gameView.setVisible(false);
                } catch (IOException e) {
                    // e.printStackTrace();
                }
                gameView.addActionListeners(new GamePlayController(model, gameView, false));
                gameView.setVisible(false);
                gameView=null;
                GameMap.setInstance(null);
                Player.hasBotWon=false;
                assertTrue(("DrawGame").equalsIgnoreCase(winner.trim()));
                Player.winner="";
            }
        }
    }
    /**
     * If number of draw turn is max , Test if some one winning the game or not.
     */
    @Test
    public void tournamentGameWithWinner(){
        for (File mapFile : fileList) {
            Player model = new Player();
            for (int i = 1; i <= 3; i++) {
                model.initData(mapFile,playerNames.size(),playerNames,playerTypes,true);
                model.setDrawTurns(50);
                try {
                    gameView = new GameView();
                    gameView.setVisible(false);
                } catch (IOException e) {
                    // e.printStackTrace();
                }
                gameView.addActionListeners(new GamePlayController(model, gameView, false));
                gameView.setVisible(false);
                assertTrue(Player.hasBotWon);
                Player.winner="";
                gameView=null;
                GameMap.setInstance(null);
                Player.hasBotWon=false;
            }
        }
    }

    @Test
    public void tournamentModeWithHighestGamePossible(){

    }
    /**
     * Play tournament between Aggressive and Benevolent bot with less number of draw turns . 
     * if the game result is draw after certain turn , Aggressive should have more number of countries compare to benevolent. checking the same.
     * if result is not draw, then check if some one has won the game or not. 
     */
    @Test
    public void tournamentBetweenAgressiveBenevolent() {
    	 playerNames = new ArrayList<String>();
         playerTypes = new ArrayList<String>();
         playerNames.add("Aggressive");
         playerNames.add("Benevolent");
         playerTypes.add("Aggressive Bot");
         playerTypes.add("Benevolent Bot");
         int agressiveCountryCounter = 1,benevolentCountryCounter=1;
         for (File mapFile : fileList) {
             Player model = new Player();
             for (int i = 1; i < 3; i++) {
                 model.initData(mapFile,playerNames.size(),playerNames,playerTypes,true);
                 model.setDrawTurns(10);
                 try {
                     gameView = new GameView();
                     gameView.setVisible(false);
                 } catch (IOException e) {
                     // e.printStackTrace();
                 }
                 gameView.addActionListeners(new GamePlayController(model, gameView, false));
                 gameView.setVisible(false);
                 
                 for (Country country : GameMap.getInstance().getCountryAndNeighborsMap().keySet()) {
                	 	if(country.getBelongsToPlayer().getName().equalsIgnoreCase("Aggressive")) {
                	 		agressiveCountryCounter++;
                	 	}else if(country.getBelongsToPlayer().getName().equalsIgnoreCase("Benevolent")) {
                	 		benevolentCountryCounter++;
                	 	}
                     }
                 }
                 
             	if(Player.winner.equalsIgnoreCase("DrawGame")) {
             		assertTrue(agressiveCountryCounter>benevolentCountryCounter);	
             	}else {
             		assertTrue(Player.hasBotWon);
             	}
                 
                 Player.winner="";
                 gameView=null;
                 GameMap.setInstance(null);
                 Player.hasBotWon=false;
             }
         }
    
    /**
     * Play tournament between Cheater and Benevolent bot with less number of draw turns . 
     * if the game result is draw after certain turn , Cheater should have more number of countries compare to benevolent. checking the same.
     * if result is not draw, then check if some one has won the game or not.
     */
    @Test
    public void tournamentBetweenCheaterBenevolent() {

    	playerNames = new ArrayList<String>();
        playerTypes = new ArrayList<String>();
        playerNames.add("Cheater");
        playerNames.add("Benevolent");
        playerTypes.add("Aggressive Bot");
        playerTypes.add("Benevolent Bot");
        int cheaterCountryCounter = 1,benevolentCountryCounter=1;
        for (File mapFile : fileList) {
            Player model = new Player();
            
                model.initData(mapFile,playerNames.size(),playerNames,playerTypes,true);
                model.setDrawTurns(10);
                try {
                    gameView = new GameView();
                    gameView.setVisible(false);
                } catch (IOException e) {
                    // e.printStackTrace();
                }
                gameView.addActionListeners(new GamePlayController(model, gameView, false));
                gameView.setVisible(false);
                
                for (Country country : GameMap.getInstance().getCountryAndNeighborsMap().keySet()) {
               	 	if(country.getBelongsToPlayer().getName().equalsIgnoreCase("Cheater")) {
               	 		cheaterCountryCounter++;
               	 	}else if(country.getBelongsToPlayer().getName().equalsIgnoreCase("Benevolent")) {
               	 		benevolentCountryCounter++;
               	 	}
                    }
                }
                
            	if(Player.winner.equalsIgnoreCase("DrawGame")) {
            		assertTrue(cheaterCountryCounter>benevolentCountryCounter);	
            	}else {
            		assertTrue(Player.hasBotWon);
            	}
                
                Player.winner="";
                gameView=null;
                GameMap.setInstance(null);
                Player.hasBotWon=false;
            }

    }


