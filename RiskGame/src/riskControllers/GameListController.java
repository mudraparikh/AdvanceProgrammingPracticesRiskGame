package riskControllers;

import riskModels.GamePlayModel;
import riskView.GameView;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.IOException;

public class GameListController implements ListSelectionListener {

    private GameView gameView;
    private GamePlayModel gamePlayModel;

    public GameListController() throws IOException {
        this.gamePlayModel = new GamePlayModel();
        this.gameView = new GameView();
    }
    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        System.out.println("Value changed method called !");
        if (!listSelectionEvent.getValueIsAdjusting()) {
            if (gameView.getCountryAIndex() != -1) {
                gamePlayModel.setCountryASelection(gameView.getCountryA().replaceAll("[0-9]", "").replaceAll("\\-", ""));
            }
        }
    }
}
