package riskModels.player;

import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskView.GameView;

public class BenevolentBot implements PlayerStrategy {

    public Country countryA;
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
