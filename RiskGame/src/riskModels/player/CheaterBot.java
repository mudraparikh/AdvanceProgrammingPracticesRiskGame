package riskModels.player;

import riskModels.country.Country;
import riskModels.dice.Dice;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskView.GameView;

import java.util.Random;

public class CheaterBot implements PlayerStrategy {

    public Country countryA;
    public Country countryB;

    @Override
    public void attack(String country1, String country2, GameView gameView, Player model) {
        countryA = MapModel.getCountryObj(country1, GameMap.getInstance());
        countryB = MapModel.getCountryObj(country2, GameMap.getInstance());
        model.updatePhaseDetails("Repaint");
        model.updatePhaseDetails("==Attack Phase==");
        GameView.displayLog(countryA.getBelongsToPlayer().getName() + " has defeated all of " + countryB.getBelongsToPlayer().getName() + "'s armies in " + country2 + " and has occupied the country!");
        defendingPlayerLostCountry(countryA, countryB, model);

        //If player conquered all the country and have won the game
        if (model.currentPlayer.assignedCountries.size() == GameMap.getInstance().getCountries().size()) {
            model.hasBotWon = true;
            GameView.displayLog("" + model.currentPlayer.getName() + " has won the game ! Congratulations ! ");
            model.updatePhaseDetails(model.currentPlayer.getName() + "Won");
        }
        GameView.updateMapPanel();

    }

    @Override
    public void fortify(String country1, String country2, GameView gameView, Player model) {
        countryA = MapModel.getCountryObj(country1, GameMap.getInstance());
        if (countryA != null){
            int armies = countryA.getCurrentArmiesDeployed();
            countryA.addArmy(armies);
            GameView.displayLog("Cheater has doubled it's armies on " + country1);
        }
        GameView.updateMapPanel();
    }

    @Override
    public void reinforce(String country, GameView gameView, Player model) throws NullPointerException {
        countryA = MapModel.getCountryObj(country, GameMap.getInstance());
        if (countryA !=null){
            int armies = countryA.getCurrentArmiesDeployed();
            countryA.addArmy(armies);
            GameView.displayLog("Cheater has doubled it's armies on " + country);
        }
        GameView.updateMapPanel();
    }


    private void defendingPlayerLostCountry(Country countryA, Country countryB, Player model) {

        // Remove country from defender's list of occupied territories and adds to attacker's list
        countryB.getBelongsToPlayer().assignedCountries.remove(countryB);
        countryA.getBelongsToPlayer().assignedCountries.add(countryB);

        // Check if defender is eliminated from game
        if (countryB.getBelongsToPlayer().getAssignedCountries().size() == 0) {
            model.playerLostRule(countryA, countryB);
        }
        // Set country player to attacker
        countryB.setBelongsToPlayer(countryA.getBelongsToPlayer());
        model.updatePhaseDetails("\n"+countryB.getCountryName()+" has been captured ! ");

        //The attacking player must then place a number of armies
        //in the conquered country which is greater or equal than the number of dice that was used in the attack that
        //resulted in conquering the country
        int moveArmies = 1;

        countryA.subtractArmy(moveArmies);
        countryB.addArmy(moveArmies);
        model.hasCountryCaptured = true;
        model.updateDomination();
    }

}
