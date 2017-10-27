package riskControllers;

import riskModels.gamedriver.GamePlayAPI;
import riskView.FileSelectDialog;
import riskView.GameView;
import riskView.PlayerCount;

import static util.RiskGameUtil.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import java.awt.*;
/**
 * This class maps the user's input to the data and methods in the model
 */
public class PlayerCountController implements ActionListener {
    private GamePlayAPI serviceLayer;
    private PlayerCount view;
    private FileSelectDialog fileSelectDialog;
    private JButton openbtn;

    public PlayerCountController(PlayerCount view){
        this.view = view;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String event = actionEvent.getActionCommand();
        if (event.equals(threePlayersBtnName)) {

            //Open the MapFileChooseDialog box over here.
            //Instantiate the class over here and pass the value
            /*System.out.println("Now Choose the map file from the Dialog box");
            fileSelectDialog = new FileSelectDialog();
            fileSelectDialog.addActionListener(new FileSelectDialogController(fileSelectDialog, 3));
            fileSelectDialog.setVisible(true);
            */
        	GameView add = new GameView();
        	add.setVisible(true);
        }

        else if (event.equals(fourPlayersBtnName)) {

            //Open the MapFileChooseDialog box over here.
            //Instantiate the class over here and pass the value
            System.out.println("Now Choose the map file from the Dialog box");
            fileSelectDialog = new FileSelectDialog();
            fileSelectDialog.addActionListener(new FileSelectDialogController(fileSelectDialog, 4));
            fileSelectDialog.setVisible(true);
        }

        else if (event.equals(fivePlayersBtnName))
        {
            //Open the MapFileChooseDialog box over here.
            //Instantiate the class over here and pass the value
            System.out.println("Now Choose the map file from the Dialog box");
            fileSelectDialog = new FileSelectDialog();
            fileSelectDialog.addActionListener(new FileSelectDialogController(fileSelectDialog, 5));
            fileSelectDialog.setVisible(true);
        }

        else if (event.equals(sixPlayersBtnName))
        {
            //Open the MapFileChooseDialog box over here.
            //Instantiate the class over here and pass the value
            System.out.println("Now Choose the map file from the Dialog box");
            fileSelectDialog = new FileSelectDialog();
            fileSelectDialog.addActionListener(new FileSelectDialogController(fileSelectDialog, 6));
            fileSelectDialog.setVisible(true);
        }

        else if (event.equals(backBtnName))
        {
            view.dispose();
        }

        else
        {
            System.out.println("Error: " + actionEvent + " actionEvent not found!");
        }
    }
}