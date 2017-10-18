
package riskModels.map;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class MapModelTest {
    private MapModel mapObj;
    private GameMap gameMapObj;
    private String filePath;
    @Before
    public void init() {
        mapObj = new MapModel();
        gameMapObj = new GameMap();
        filePath = "/home/akshay/AdvanceProgrammingPracticesRiskGame/RiskGame/res/";
    }

    @Test
    public void testValidateMap() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "validate.map");
        assertEquals(true, gameMapObj.isCorrectMap());
    }
    @Test
    public void testInValidateMap() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "no_headers.map");
        assertNotEquals(true,gameMapObj.isCorrectMap());
    }
    @Test
    public void testInvalidNeighboursMap() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "no_neighbours.map");
        assertFalse(gameMapObj.isCorrectMap());
    }
    @Test
    public void testBlankMap() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "blank.map");
        assertFalse(gameMapObj.isCorrectMap());
    }
    @Test
    public void testInvalidExtensionMap() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "invalid_extension.map");
        assertFalse(gameMapObj.isCorrectMap());
    }

    @Test
    public void testValidateFile() throws Exception {
    }
}