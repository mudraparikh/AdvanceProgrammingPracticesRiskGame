package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import riskModels.cards.Card;
import riskModels.cards.Deck;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.Player;
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
        File f = new File("/home/akshay/AdvanceProgrammingPracticesRiskGame/London.map");
        gameMap = mapModel.readMapFile("/home/akshay/AdvanceProgrammingPracticesRiskGame/RiskGame/London.map");
       //createGameMapFromFile(f);
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
    public void testGameWon() throws IOException {

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
            List<Country> neighbors = GameMap.getInstance().getCountryAndNeighborsMap().get(new Country(country.getCountryName()));
        	GameMap.getInstance().getCountryAndNeighborsMap().put(country, neighbors);
        }
        for(Country country :GameMap.getInstance().getCountryAndNeighborsMap().keySet() ) {
        	if(country.getCountryName().equalsIgnoreCase(defender.assignedCountries.get(0).getCountryName())) {
        		country.setBelongsToPlayer(defender);
        		country.setCurrentArmiesDeployed(1);
        		List<Country> neighbors = GameMap.getInstance().getCountryAndNeighborsMap().get(new Country(country.getCountryName()));
            	GameMap.getInstance().getCountryAndNeighborsMap().put(country, neighbors);
        	}
        }
       GameView gameView = new GameView();
       currentPlayer=attacker;
       canAttack=true;
       mapModel = new MapModel();
       attack(attacker.assignedCountries.get(0).getCountryName(),countryToAssignDefender.getCountryName(),gameView,attacker);
        System.out.println(hasPlayerWon);

       assertTrue(hasPlayerWon);
    }

    @Test
    public void testReinforcementPhase() throws IOException {
        initializePlayerData(6);
        playerCount = 6;
        setInitialArmies();
        allocateCountriesToPlayers();
        addInitialArmiesInRR();
        gameView = new GameView();
        nextPlayerTurn(this);
        Country country = currentPlayer.assignedCountries.get(0);
        int oldArmies = country.currentArmiesDeployed;
        System.out.println();
        reinforce(country.getCountryName(),gameView);
        assertTrue(country.currentArmiesDeployed - oldArmies == 1);
    }

    @Test
    public void testForReinforcementArmiesCalculation() throws IOException {
        initializePlayerData(6);
        playerCount = 6;
        setInitialArmies();
        allocateCountriesToPlayers();
        addInitialArmiesInRR();
        gameView = new GameView();
        nextPlayerTurn(this);
        deck = new Deck();


        Country c1 = GameMap.getInstance().getCountries().get(0);
        Country c2 = GameMap.getInstance().getCountries().get(1);
        Country c3 = GameMap.getInstance().getCountries().get(2);
        Country c4 = GameMap.getInstance().getCountries().get(3);
        Card card1 = new Card("Infantry",c1);
        Card card2 = new Card("Infantry",c2);
        Card card3 = new Card("Infantry",c3);
        Card card4 = new Card("Infantry",c4);
        deck.add(card1);
        deck.add(card2);
        deck.add(card3);
        deck.add(card4);
        assertEquals(3,currentPlayerReinforceArmies);
        currentPlayer.addRiskCard(card1);
        currentPlayer.addRiskCard(card2);
        currentPlayer.addRiskCard(card3);
        currentPlayer.addRiskCard(card4);
        canTurnInCards = true;
        int[] cardsToTurn = new int[3];
        cardsToTurn[0] = 0; cardsToTurn[1] = 1; cardsToTurn[2] = 2;
        turnInCards(cardsToTurn);
        assertEquals(8,currentPlayer.getTotalArmies());
    }

    @Test
    public void testFortificationPhase() throws IOException {
        initializePlayerData(6);
        playerCount = 6;
        setInitialArmies();
        allocateCountriesToPlayers();
        addInitialArmiesInRR();
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
        assertEquals(oldArmyInCountry1,newArmyInCountry1+1);
        assertEquals(oldArmyInCountry2, newArmyInCountry2 - 1);
    }

    @Override
    protected int showMoveArmiesToCaptureCountryDialogBox(GameView gameView) {
        return 1;
    }
    
    @Override
    protected int showAttackerDiceDialogBox(GameView gameView){
        return 1;
    }

    @Override
    protected int showDefenderDiceDialogBox(GameView gameView) {
        return 1;
    }

    @Override
    protected boolean isAttackValid(Player p, Country c, Country c1) {
    	return true;    	
    }

    @Override
    protected boolean isFortifyValid() {
        return true;
    }

    @Override
    protected Integer showReinforceArmiesDialogBox(GameView gameView) {
        return 1;
    }

    @Override
    protected int showFortificationArmyMoveDialog(GameView gameView){
        return  1;
    }

    @Override
    protected void updateArmiesBasedOnDiceResult(int attackerLosses,int defenderLosses){
        countryA.subtractArmy(0);
        countryB.subtractArmy(countryB.getCurrentArmiesDeployed());
    }
}