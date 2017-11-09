package riskControllers;

import riskModels.player.Player;
import riskView.GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePlayController implements ActionListener {

    private Player model;
    private GameView view;

    public GamePlayController(Player model, GameView gameView) {
        this.model = model;
        this.view = gameView;
        model.startGame(model);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String actionEvent = evt.getActionCommand();

        switch (actionEvent) {
            case "clearLogBtn":
            	view.clearLog();
                break;

            case "turnInBtn":
                model.turnInCards(view.getCardsToRemove());

                break;
            case "reinforceBtn":
                if (view.getCountryA() != null) {
                    model.reinforce(view.getCountryA().replaceAll("[0-9]", " ").replaceAll("\\-", " "), view);
                } else {
                    GameView.displayLog("Please make sure that country is selected from the list !");
                }

                break;
            case "attackBtn":
                if (view.getCountryA() != null && view.getCountryB() != null) {
                    model.attack(view.getCountryA().trim().replaceAll("[0-9]", " ").replaceAll("\\-", " "), view.getCountryB().trim().replaceAll("[0-9]", "").replaceAll("\\-", ""), view);
                } else {
                    GameView.displayLog("Please make sure that country is selected from both the list !");
                }

                break;
            case "fortifyBtn":
                if (view.getCountryA() != null && view.getCountryB() != null) {
                    model.fortify(view.getCountryA().replaceAll("[0-9]", " ").replaceAll("\\-", " "), view.getCountryB().replaceAll("[0-9]", " ").replaceAll("\\-", " "), view, model);
                } else {
                    GameView.displayLog("Please make sure that country is selected from both the list !");
                }

                break;
            case "endTurnBtn":
                model.endPlayerTurn(model);

                break;
            default:
                System.out.println("action Event not found: " + actionEvent);
                break;
        }
    }
}
