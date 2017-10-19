
package riskModels.map;

import org.junit.Before;
import org.junit.Test;
import riskModels.country.Country;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MapModelTest {
    private MapModel mapObj;
    private GameMap gameMapObj;
    private String filePath;
    String location = MapModelTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    
    @Before
    public void init() {
        mapObj = new MapModel();
        gameMapObj = new GameMap();
        filePath=location.replaceAll("/bin", "/res");
        //filePath = "/home/akshay/AdvanceProgrammingPracticesRiskGame/RiskGame/res/";
    }

    @Test
    public void testValidateMap() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath+ "validate.map");
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
    public void testCountryObject() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath+ "validate.map");
        assertEquals("44",mapObj.getCountryObj("44",gameMapObj).getCountryName());
    }

    @Test
    public void testCountryObjectWithNull() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath+ "validate.map");
        assertEquals(null,mapObj.getCountryObj("APP",gameMapObj));
    }

    @Test
    public void removeCountry() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath+ "validate.map");
        Country countryToRemove = mapObj.getCountryObj("44",gameMapObj);
        mapObj.removeCountry(countryToRemove,gameMapObj);
        assertEquals(null,mapObj.getCountryObj("44",gameMapObj));
    }

    @Test
    public void addCountry() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath+ "validate.map");
        Country country = new Country("100", 23, 34, "Yukon");
        Country country1 = new Country("101", 43, 94, "Yukon");
        Country country2 = new Country("102", 63, 23, "Yukon");
        List<Country> neighboringCountries = new ArrayList<Country>();
        neighboringCountries.add(country1);
        neighboringCountries.add(country2);
        mapObj.addCountry(country,gameMapObj,neighboringCountries);
        assertEquals("100",mapObj.getCountryObj("100",gameMapObj).getCountryName());

    }

    @Test
    public void addNeighbor() throws Exception {
    }
}