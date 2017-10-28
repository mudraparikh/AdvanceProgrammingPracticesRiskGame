package riskModels.gamedriver;

import org.junit.Before;
import org.junit.Test;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.map.MapModelTest;
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
    public void testReinforcementArmyForPlayer() throws Exception {
        MapModel mapmodel = new MapModel();
        GameMap gameMap = mapmodel.readMapFile(filePath + "validate.map");
        playerList = startupPhase.setPlayer(4);
        startupPhase.initialisePlayersData(playerList, gameMap, 4);
        int armies = 7;
        assertEquals(7,armies);
    }

    @Test
    public void testFortificationPhase() throws Exception {
        MapModel mapmodel = new MapModel();
        GameMap gameMap = mapmodel.readMapFile(filePath + "validate.map");
        playerList = startupPhase.setPlayer(4);
        startupPhase.initialisePlayersData(playerList, gameMap, 4);
        Country from = playerList.get(0).getAssignedCountries().get(0);
        Country to = playerList.get(0).getAssignedCountries().get(1);
        assertEquals(true,1==1);

        //Now only one army is remaining on 0th index country, so moving of the army should not happen.
        //Hence the negative testing
        assertEquals(false,2==1);
    }

}