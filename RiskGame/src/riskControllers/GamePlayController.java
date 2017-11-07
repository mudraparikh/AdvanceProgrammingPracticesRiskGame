package riskControllers;

import riskModels.GamePlayModel;
import riskView.GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePlayController implements ActionListener {

    private GamePlayModel model;
    private GameView view;

    public GamePlayController(GamePlayModel model, GameView gameView) {
        this.model = model;
        this.view = gameView;
        model.startGame();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String actionEvent = evt.getActionCommand();

        switch (actionEvent) {
            case "menuBtn":
                System.out.println("User pressed menuButton.");
                /*menuDialog = new MenuDialog(view, true);
                menuDialog.addActionListeners(new MenuController(model, menuDialog));
                menuDialog.setVisible(true);*/

                break;
            case "turnInBtn":
                //System.out.println("User pressed turnInButton.");
                //model.turnInCards(view.getCardsToRemove());

                break;
            case "reinforceBtn":
                //System.out.println("User pressed reinforceButton.");view.getSelectedComboBox();
                model.reinforce(view.getCountryA().replaceAll("[0-9]", "").replaceAll("\\-", ""), view);

                break;
            case "attackBtn":
                //System.out.println("User pressed attackButton.");
                model.attack(view.getCountryA().replaceAll("[0-9]", "").replaceAll("\\-", ""), view.getCountryB().replaceAll("[0-9]", "").replaceAll("\\-", ""), view);

                break;
            case "fortifyBtn":
                //System.out.println("User pressed fortifyButton.");
               model.fortify(view.getCountryA().replaceAll("[0-9]", "").replaceAll("\\-", ""), view.getCountryB().replaceAll("[0-9]", "").replaceAll("\\-", ""), view);

                break;
            case "endTurnBtn":
                model.nextPlayerTurn();

                break;
            default:
                System.out.println("actionEvent not found: " + actionEvent);
                break;
        }
    }
}
