
package riskModels.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import riskModels.cards.Deck;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskView.GameView;

public class PlayerTest extends Player {

    private GameMap gameMap;
    private MapModel mapModel;
    private GameView gameView;
    private Player model;
    

    private String filePath;
    String location = PlayerTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    @Before
    public void setUp() throws Exception {
        mapModel = new MapModel();
        filePath = location.replaceAll("/bin", "/res");
        File f = new File(filePath+"London.map");
        gameMap = mapModel.readMapFile("C:\\Users\\prashantp95\\git\\AdvanceProgrammingPracticesRiskGame\\phaseView\\RiskGame\\res\\London.map");
        
    }

    @Test
    public void checkDeckCardsPopulated(){
        Deck deck = new Deck((ArrayList<Country>) gameMap.getCountries());
        assertEquals(gameMap.getCountries().size(), deck.deck.size());
    }

    @Test
    public void testInitialisePlayerData(){
        initializePlayerData(6);
        assertEquals("John", getPlayerList().get(0).getName());
        assertEquals("Alexa", getPlayerList().get(1).getName());
        assertEquals("Penny", getPlayerList().get(2).getName());
        assertEquals("Sheldon",getPlayerList().get(3).getName());
        assertEquals("Amy", getPlayerList().get(4).getName());
        assertEquals("Raj", getPlayerList().get(5).getName());
    }

    @Test
    public void testIsAttackPhase() throws IOException {
        initializePlayerData(6);
        playerCount = 6;
        setInitialArmies();
        allocateCountriesToPlayers();
        addInitialArmiesInRR();
        Player player = getPlayerList().get(0);
        Country attackerCountry = player.assignedCountries.get(0);
        Country defendingCountry = attackerCountry.getNeighborNodes().get(0);
        gameView = new GameView();
        assertTrue(isAttackValid(player, attackerCountry, defendingCountry));
    }

    @Test
    public void testInitialArmy() {
        initializePlayerData(6);
        playerCount = 6;
        setInitialArmies();
        playerList.get(0);
        assertEquals(20,playerList.get(0).totalArmies);
    }
    @Test
    public void testCompareDiceResultsAndCalculateLosses(){
        attackerDice = 3;
        defenderDice = 2;
        attackerRolls = new Integer[3];
        defenderRolls = new Integer[2];
        attackerRolls[0] = 5;
        attackerRolls[1] = 4;
        attackerRolls[2] = 3;
        defenderRolls[0] = 2;
        defenderRolls[1] = 1;
        compareDiceResultsAndCalculateLosses();
        assertEquals(0,attackerLosses);
        assertEquals(2,defenderLosses);

        attackerLosses = 0;
        defenderLosses = 0;
        attackerRolls[0] = 1;
        attackerRolls[1] = 2;
        attackerRolls[2] = 3;
        defenderRolls[0] = 4;
        defenderRolls[1] = 5;
        compareDiceResultsAndCalculateLosses();
        assertEquals(2,attackerLosses);
        assertEquals(0,defenderLosses);

        attackerLosses = 0;
        defenderLosses = 0;
        attackerRolls[0] = 5;
        attackerRolls[1] = 4;
        attackerRolls[2] = 3;
        defenderRolls[0] = 5;
        defenderRolls[1] = 3;
        compareDiceResultsAndCalculateLosses();
        assertEquals(1,attackerLosses);
        assertEquals(1,defenderLosses);
    }

    @Test
    public void testDefendingPlayerLostCountry() throws IOException {

        initializePlayerData(6);
        playerCount = 6;
        setInitialArmies();
        allocateCountriesToPlayers();
        addInitialArmiesInRR();
        gameView = new GameView();

        Player player = getPlayerList().get(0);
        Country attackerCountry = player.assignedCountries.get(0);
        Country defendingCountry = attackerCountry.getNeighborNodes().get(0);
        defendingCountry.setBelongsToPlayer(getPlayerList().get(1));
        defendingCountry.currentArmiesDeployed = 0;
        defendingPlayerLostCountry(attackerCountry, defendingCountry, gameView);
        assertTrue(defendingCountry.getBelongsToPlayer().equals(attackerCountry.getBelongsToPlayer()));
    }

    @Test
    public void testPlayerLostRule() throws IOException {

        initializePlayerData(3);
        playerCount = 3;
        setInitialArmies();
        allocateCountriesToPlayers();
        addInitialArmiesInRR();
       // gameView = new GameView();
        
        Player attacker = getPlayerList().get(0);
        attacker.assignedCountries.clear();
        for(Country c : GameMap.getInstance().getCountryAndNeighborsMap().keySet()) {
        	c.setBelongsToPlayer(attacker);
        	c.setCurrentArmiesDeployed(50);
        	attacker.assignedCountries.add(c);
        }
        Player defender =  getPlayerList().get(1);
        defender.assignedCountries.clear();
        Country countryToAssignDefender = GameMap.getInstance().getCountryAndNeighborsMap().get(attacker.assignedCountries.get(0)).get(0);
        
        attacker.assignedCountries.remove(countryToAssignDefender);
        countryToAssignDefender.setBelongsToPlayer(defender);
        defender.assignedCountries.add(countryToAssignDefender);
        
        for(Country country :attacker.assignedCountries ) {
        	System.out.println(country.getCountryName()+"----->"+country.getBelongsToPlayer().getName());
        	List<Country> neighbors = GameMap.getInstance().getCountryAndNeighborsMap().get(new Country(country.getCountryName()));
        	GameMap.getInstance().getCountryAndNeighborsMap().put(country, neighbors);
        }
        for(Country country :GameMap.getInstance().getCountryAndNeighborsMap().keySet() ) {
        	if(country.getCountryName().equalsIgnoreCase(defender.assignedCountries.get(0).getCountryName())) {
        		country.setBelongsToPlayer(defender);
        		country.setCurrentArmiesDeployed(2);
        		List<Country> neighbors = GameMap.getInstance().getCountryAndNeighborsMap().get(new Country(country.getCountryName()));
            	GameMap.getInstance().getCountryAndNeighborsMap().put(country, neighbors);
        	}
        	System.out.println(country.getCountryName()+"----->"+country.getBelongsToPlayer().getName());
        	
        }
       GameView gameView = new GameView();
       currentPlayer=attacker;
       canAttack=true;
       mapModel = new MapModel();
       attack(attacker.assignedCountries.get(0).getCountryName(),countryToAssignDefender.getCountryName(),gameView,attacker);
    }

    @Override
    protected int showMoveArmiesToCaptureCountryDialogBox(GameView gameView) {
        return 1;
    }
    
    
    @Override
    protected boolean isAttackValid(Player p, Country c, Country c1) {
    	return true;    	
    }



}