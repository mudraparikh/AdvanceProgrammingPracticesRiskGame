package riskModels;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class GameListModel extends DefaultListModel implements Observer {

    private int i;
    private String type;
    private String display;
    private ArrayList<String> stringList;
    private GamePlayModel model;
    public GameListModel(GamePlayModel model, String type) {
        super();
        this.model = model;
        this.type = type;
    }

    @Override
    public void update(Observable observable, Object obj) {

        display = (String)obj;
        if (Objects.equals(type, "selectedCountry") && Objects.equals(type, display)){
            removeAllElements();
        }
    }
}
