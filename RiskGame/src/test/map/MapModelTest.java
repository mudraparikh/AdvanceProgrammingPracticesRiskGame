package test.map;

import org.junit.Before;
import org.junit.Test;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.Player;
import riskView.GameView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class performs the validations related to maps
 * @author hnath
 *
 */
public class MapModelTest extends Player {
    String location = MapModelTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    private MapModel mapObj;
    private GameMap gameMapObj;
    private String filePath;

    /**
     * This method setting up the context as many test cases share the same values
     */
    @Before
    public void init() {
        mapObj = new MapModel();
        gameMapObj = GameMap.getInstance();
        //filePath = location.replaceAll("/bin", "/res");
        filePath = "/home/akshay/AdvanceProgrammingPracticesRiskGame/RiskGame/res/";
    }

    /**
     * This method checks if the selected map is the correct map
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testValidateMap() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "validate.map");
        assertEquals(true, gameMapObj.isCorrectMap());
    }

    /**
     * This method checks whether the selected map is valid , if no header map is selected
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testInValidateMap() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "no_headers.map");
        assertNotEquals(true, gameMapObj.isCorrectMap());
    }

    /**
     * This method checks whether the selected map is valid , if no neighbours map is selected
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testInvalidNeighboursMap() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "no_neighbours.map");
        assertFalse(gameMapObj.isCorrectMap());
    }
    
    /**
     * This method checks whether the selected map is valid , if disconnected map is selected
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testDisconnectedMap() throws Exception{
    	gameMapObj = mapObj.readMapFile(filePath + "disconnectedTest.map");
    	assertFalse(gameMapObj.isCorrectMap());
    }

    /**
     * This method checks whether the selected map is valid , if blank map is selected
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testBlankMap() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "blank.map");
        assertFalse(gameMapObj.isCorrectMap());
    }

    /**
     * This method checks whether the selected map is valid , if invalid extension map is selected
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testInvalidExtensionMap() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "invalid_extension.map");
        assertFalse(gameMapObj.isCorrectMap());
    }

    /**
     * This method checks for map validation by testing country object
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testCountryObject() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "validate.map");
        assertEquals("c3", mapObj.getCountryObj("c3", gameMapObj).getCountryName());
    }

    /**
     * This method checks if the map is valid , the country object with null
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void testCountryObjectWithNull() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "validate.map");
        assertEquals(null, mapObj.getCountryObj("APP", gameMapObj));
    }

    /**
     * This method checks if the country is removed as expected to the map
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void removeCountry() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "validate.map");
        Country countryToRemove = mapObj.getCountryObj("c1", gameMapObj);
        mapObj.removeCountry(countryToRemove, gameMapObj);
        assertEquals(null, mapObj.getCountryObj("c1", gameMapObj));
    }

    /**
     * This method checks if the country is added as expected to the map
     * @throws Exception it throws if there are any exceptions found
     */
    @Test
    public void addCountry() throws Exception {
        gameMapObj = mapObj.readMapFile(filePath + "validate.map");
        Country country = new Country("100", 23, 34, "abc");
        Country country1 = new Country("101", 43, 94, "abc");
        Country country2 = new Country("102", 63, 23, "abc");
        List<Country> neighboringCountries = new ArrayList<Country>();
        neighboringCountries.add(country1);
        neighboringCountries.add(country2);
        mapObj.addCountry(country, gameMapObj, neighboringCountries);
        assertEquals("100", mapObj.getCountryObj("100", gameMapObj).getCountryName());

    }
    
    /**
     * This method is used to check where the game getting saved correctly
     * @throws IOException when the input or output has some exceptions
     */
    @Test
    public void saveGame() throws IOException{
    	 gameMapObj = mapObj.readMapFile(filePath + "validate.map");
    	// initializePlayerData(3, playerNames, playerTypes);
        playerCount = 3;
        setInitialArmies(); //Assigning armies
        allocateCountriesToPlayers();  //Allocating countries
        addInitialArmiesInRR();  //Assigning initial armies
        
        Player player = getPlayerList().get(0); //Performing attack and defending
        Country attackerCountry = player.assignedCountries.get(0);
        Country defendingCountry = attackerCountry.getNeighborNodes().get(0);
        
        //Performing fortification
        gameView = new GameView();    
        nextPlayerTurn(this);
        canFortify = true;
        int oldArmyInCountry1 = currentPlayer.assignedCountries.get(0).currentArmiesDeployed;
        int oldArmyInCountry2 = currentPlayer.assignedCountries.get(1).currentArmiesDeployed;
        System.out.println(oldArmyInCountry1);
        //System.out.println(oldArmyInCountry2);
        moveArmyFromTo(currentPlayer.assignedCountries.get(0),currentPlayer.assignedCountries.get(1),1);
        int newArmyInCountry1 = currentPlayer.assignedCountries.get(0).currentArmiesDeployed;
        System.out.println(newArmyInCountry1);
        int newArmyInCountry2 = currentPlayer.assignedCountries.get(1).currentArmiesDeployed;
        
        saveGame();
    //    filePath="C:\\Windows\\Temp\\SunNov26EST2017_1.ser\\";
    //    loadGame(filePath);
        
 //  	assertEquals(true,);
    }
    
    /**
     * This method test whether LoadGmae function is working as intended
     * @throws IOException when the input or output has some exceptions
     */
    @Test
    public void loadGame() throws IOException{
    	filePath="C:\\Windows\\Temp\\SunNov26EST2017_1.ser\\";
        gameMapObj = mapObj.readMapFile(filePath);
        loadGame(filePath);
        
    }

}