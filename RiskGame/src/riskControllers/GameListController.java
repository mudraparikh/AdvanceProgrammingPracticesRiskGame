package riskControllers;

import riskModels.GamePlayModel;
import riskView.GameView;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GameListController implements ListSelectionListener {

    private GameView gameView;
    private GamePlayModel gamePlayModel;

    public GameListController(GameView gameView, GamePlayModel gamePlayModel){
        this.gamePlayModel = gamePlayModel;
        this.gameView = gameView;
    }
    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        if (!listSelectionEvent.getValueIsAdjusting()) {
            if (gameView.getCountryAIndex() != -1) {
                gamePlayModel.setCountryASelection(gameView.getCountryA().replaceAll("[0-9]", "").replaceAll("\\-", ""));
            }
        }
    }
}
