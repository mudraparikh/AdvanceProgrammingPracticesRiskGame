package riskControllers;

import riskModels.map.MapModel;
import riskModels.player.Player;
import riskView.GameMenuView;
import riskView.GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenuViewController implements ActionListener {

    public GameMenuView view;
    public Player model;

    public GameMenuViewController(GameMenuView menuView, Player model) {
        // TODO Auto-generated constructor stub
        this.view = menuView;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String actionEvent = evt.getActionCommand();

        switch (actionEvent) {
            case "returnBtn":
                view.dispose();
                break;

            case "saveBtn":
                model.saveGame();
                break;

            case "quitBtn":
                System.exit(1);
                break;

            default:
                System.out.println("action Event not found: " + actionEvent);
                break;
        }
    }
}
