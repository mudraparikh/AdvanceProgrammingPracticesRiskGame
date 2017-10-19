package riskModels.gamedriver;

import org.junit.Before;
import org.junit.Test;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
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

    @Before
    public void init() {
        mapObj = new MapModel();
        gameMapObj = new GameMap();
        gamePlay = new GamePlayAPI();
        startupPhase = new StartupPhase();
        playerList = new ArrayList<>();
        filePath = "/home/akshay/AdvanceProgrammingPracticesRiskGame/RiskGame/res/";
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

}