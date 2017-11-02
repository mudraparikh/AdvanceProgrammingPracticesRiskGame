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
        System.out.println(display);
        if (Objects.equals(type, "countryA") && Objects.equals(type, display)){
            removeAllElements();
            for (i = 0; i < model.getSelectedCountryList().size(); i++) {

                addElement(model.getSelectedCountryList().get(i));
            }
        }else if (Objects.equals(type, "countryB") && Objects.equals(type, display)) {

            // System.out.println("Notified country B list.");

            removeAllElements();

            for (i = 0; i < model.getCountryBList().size(); i++) {

                addElement(model.getCountryBList().get(i));
            }
        } else {

        }
    }
}
