package riskModels.gamedriver;

import org.junit.Before;
import org.junit.Test;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.Player;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GamePlayAPITest {

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
    public void testReinforcementArmyForPlayer() throws Exception {
        MapModel mapmodel = new MapModel();
        GameMap gameMap = mapmodel.readMapFile(filePath + "validate.map");
        playerList = startupPhase.setPlayer(4);
        startupPhase.initialisePlayersData(playerList, gameMap, 4);
        int armies = gamePlay.getReinforcementArmyForPlayer(playerList.get(0),gameMap);
        assertEquals(7,armies);
    }

}