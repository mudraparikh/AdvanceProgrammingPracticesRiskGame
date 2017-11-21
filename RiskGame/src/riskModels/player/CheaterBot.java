package riskModels.player;

import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskView.GameView;

public class CheaterBot implements PlayerStrategy {

    public Country countryA;
    public Country countryB;

    @Override
    public void attack(String country1, String country2, GameView gameView, Player model) {
        countryA = MapModel.getCountryObj(country1, GameMap.getInstance());
        countryB = MapModel.getCountryObj(country2, GameMap.getInstance());
        model.defendingPlayerLostCountry(countryA, countryB, gameView);
        //If player conquered all the country and have won the game
        if(model.currentPlayer.assignedCountries.size() == GameMap.getInstance().getCountries().size()){
            model.hasPlayerWon = true;
            GameView.displayLog(""+model.currentPlayer.getName()+" has won the game ! Congratulations ! ");
            //Player.updatePhaseDetails(model.currentPlayer.getName()+"Won");
            model.canAttack = false;
            model.canReinforce = false;
            model.canEndTurn = false;
            model.canFortify = false;
            model.canTurnInCards = false;
        }
        else{
            model.canReinforce = false;
            model.canEndTurn = true;

            //Current Player cannot continue attack phase if none of his countries that have an adjacent country
            //controlled by another player is containing more than one army
            model.checkPlayerTurnCanContinue(model.currentPlayer, model);
        }
    }

    @Override
    public void fortify(String country1, String country2, GameView gameView, Player model) {

    }

    @Override
    public void reinforce(String country, GameView gameView, Player model) throws NullPointerException {
        countryA = MapModel.getCountryObj(country, GameMap.getInstance());
        if (model.canReinforce && countryA !=null){
            System.out.println("reinorce phase enteresd !~!");
            int armies = countryA.currentArmiesDeployed;
            countryA.addArmy(armies);
            GameView.displayLog("Cheater has doubled it's armies on " + country);
        }
        model.canFortify = true;
        model.canAttack = true;
        System.out.println("***turnAI-Game");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GameView.updateMapPanel();
        gameView.setVisible(true);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
