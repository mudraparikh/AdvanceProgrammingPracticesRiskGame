package riskView;

import riskModels.GamePlayModel;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class CardView extends DefaultListModel implements Observer {

    private GamePlayModel model;

    public CardView(GamePlayModel model, String type) {
        super();
        this.model = model;
    }

    @Override
    public void update(Observable observable, Object obj) {
        GameView.displayLog("Refreshing...");

        model = (GamePlayModel) observable;
        String cardArray[] = new String[model.getCardsList().size()];
        int i;
        for (i = 0; i < model.getCardsList().size(); i++) {
            cardArray[i] = model.getCardsList().get(i);
        }
        GameView.updateCardView(cardArray);
    }
}
