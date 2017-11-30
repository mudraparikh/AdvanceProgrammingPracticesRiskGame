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
        mapModel = new MapModel();
        fileList = new ArrayList<>();
        filePath = location.replaceAll("/bin", "/res");
        File f = new File("/home/akshay/AdvanceProgrammingPracticesRiskGame/London.map");
        File f1 = new File("/home/akshay/AdvanceProgrammingPracticesRiskGame/World.map");
        File f2 = new File("/home/akshay/AdvanceProgrammingPracticesRiskGame/3d_cliff.map");
        //gameMap = mapModel.readMapFile("/home/akshay/AdvanceProgrammingPracticesRiskGame/RiskGame/London.map");
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
        fileList.add(f1);
        fileList.add(f2);
        //createGameMapFromFile(f);
    }

    @Test
    public void tournamentGameWithDrawTurnsWithThreeGames(){
        for (File mapFile : fileList) {
            Player model = new Player();
            for (int i = 1; i <= 3; i++) {
                model.initData(mapFile,playerNames.size(),playerNames,playerTypes,true);
                model.setDrawTurns(1);
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
                System.out.println(winner);
            }
        }
    }

    @Test
    public void tournamentGameWithWinner(){

    }

    @Test
    public void tournamentModeWithHighestGamePossible(){

    }

}
