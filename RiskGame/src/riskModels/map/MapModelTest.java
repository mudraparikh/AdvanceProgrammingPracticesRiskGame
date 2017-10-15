package riskModels.map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapModelTest {
    private MapModel mapObj ;
    private GameMap gameMapObj;

    @Before
    public  void init(){
        mapObj = new MapModel();
        gameMapObj = new GameMap();
    }
    @Test
    public void testValidateMap() throws Exception {
        gameMapObj =  mapObj.readMapFile("/home/akshay/AdvanceProgrammingPracticesRiskGame/RiskGame/src/riskModels/map/demoblankfail.map");
        assertEquals(false,gameMapObj.isCorrectMap());
    }

    @Test
    public void testValidateFile() throws Exception {
    }

}