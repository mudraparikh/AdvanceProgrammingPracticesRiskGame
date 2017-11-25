package riskModels.player;

import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskView.GameView;

public class BenevolentBot implements PlayerStrategy {

    public Country countryA;
    public Country countryB;
    @Override
    public void attack(String country1, String country2, GameView gameView, Player model) {
        GameView.displayLog(model.currentPlayer+" is too afraid to attack ! Skipping the attack phase.");
        model.updatePhaseDetails("Repaint");
        model.updatePhaseDetails("==Attack Phase==");
        model.updatePhaseDetails("Skipping Attack Phase as player is benevolent.");
        model.updatePhaseDetails("====Attack Phase ended===");
    }

    @Override
    public void fortify(String country1, String country2, GameView gameView, Player model) {
        countryA = MapModel.getCountryObj(country1, GameMap.getInstance());
        countryB = MapModel.getCountryObj(country2, GameMap.getInstance());

        // Player inputs how many armies to move from country A to country B
        model.updatePhaseDetails("Repaint");
        model.updatePhaseDetails("===Fortification phase===");

        int armies = countryA.getCurrentArmiesDeployed() - 1;

        model.moveArmyFromTo(countryA, countryB, armies);
        GameView.updateMapPanel();
        model.updatePhaseDetails("You moved "+armies+" army from "+countryA.getCountryName()+" to " + countryB.getCountryName());
        model.checkHasCountryCaptured();
        model.updatePhaseDetails("===Fortification ends===");

    }

    @Override
    public void reinforce(String country, GameView gameView, Player model) {
        countryA = MapModel.getCountryObj(country, GameMap.getInstance());
        GameView.displayLog(model.currentPlayer.name + " gets " + model.currentPlayerReinforceArmies + " armies");
        try {
            if (model.currentPlayer.getTotalArmies() > 0) {
                int armies = model.currentPlayer.getTotalArmies();
                model.currentPlayer.subArmy(armies);
                countryA.addArmy(armies);
                GameView.displayLog(model.currentPlayer.getName() + " has chosen to reinforce " + countryA.getCountryName() + " with " + armies + " armies.");
                if (model.currentPlayer.getTotalArmies() == 0) {
                    GameView.displayLog("\nYou do not have any armies left to reinforce");
                    model.updatePhaseDetails("\nReinforcement Phase ends");
                }
                GameView.updateMapPanel();
            }

        } catch (Exception e) {
            GameView.displayLog("\nSystem Error or Exception is thrown for reinforce method");
        }
    }
}
