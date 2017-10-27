package riskControllers;

import riskModels.gamedriver.GamePlayAPI;
import riskView.GamePlay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class maps the user's input to the data and methods in the model
 */
public class GamePlayController implements ActionListener {
    private GamePlayAPI serviceLayer;
    private GamePlay view;

    private GamePlayController(GamePlay view){
        this.view = view;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String event = actionEvent.getActionCommand();
    }
}
