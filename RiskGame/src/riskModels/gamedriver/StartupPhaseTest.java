package riskModels.gamedriver;

import org.junit.Before;
import org.junit.Test;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.map.MapModelTest;
import riskModels.player.Player;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StartupPhaseTest {
    private MapModel mapObj;
    private GameMap gameMapObj;
    private GamePlayAPI gamePlay;
    private StartupPhase startupPhase;
    private List<Player> playerList;
    private String filePath;
    String location = MapModelTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    @Before
    public void init() {
        mapObj = new MapModel();
        gameMapObj = GameMap.getInstance();
        gamePlay = new GamePlayAPI();
        startupPhase = new StartupPhase();
        playerList = new ArrayList<>();
        filePath=location.replaceAll("/bin", "/res");
        //filePath = "/home/akshay/AdvanceProgrammingPracticesRiskGame/RiskGame/res/";
    }
    @Test
    public void testAssignInitialArmiesToPlayers() throws Exception {

        playerList = startupPhase.setPlayer(2);
        startupPhase.assignInitialArmiesToPlayers(2, playerList);
        assertEquals(40,playerList.get(0).getTotalArmies());
        playerList.clear();

        playerList = startupPhase.setPlayer(3);
        startupPhase.assignInitialArmiesToPlayers(3, playerList);
        assertEquals(35,playerList.get(0).getTotalArmies());
        playerList.clear();

        playerList = startupPhase.setPlayer(4);
        startupPhase.assignInitialArmiesToPlayers(4, playerList);
        assertEquals(30,playerList.get(0).getTotalArmies());
    }
    @Test
    public void testPlayerCreation() throws Exception {

        playerList = startupPhase.setPlayer(2);
        assertEquals(2,playerList.size());
        playerList.clear();

        playerList = startupPhase.setPlayer(3);
        assertEquals(3,playerList.size());
        playerList.clear();

        playerList = startupPhase.setPlayer(4);
        assertEquals(4,playerList.size());
    }

}