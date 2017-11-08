package riskView;

import riskModels.player.GamePlayModel;
import riskModels.player.Player;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class CardView extends DefaultListModel implements Observer {

    private Player model;

    public CardView(Player model, String type) {
        super();
        this.model = model;
    }

    @Override
    public void update(Observable observable, Object obj) {
        GameView.displayLog("Refreshing...");

        model = (Player) observable;
        String cardArray[] = new String[model.getCardsList().size()];
        int i;
        for (i = 0; i < model.getCardsList().size(); i++) {
            cardArray[i] = model.getCardsList().get(i);
        }
        GameView.updateCardView(cardArray);
    }
}
