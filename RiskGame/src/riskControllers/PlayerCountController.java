package riskControllers;

import riskView.FileSelectDialog;
import riskView.PlayerCount;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static util.RiskGameUtil.*;

/**
 * This class maps the user's input to the data and methods in the model
 */
public class PlayerCountController implements ActionListener {
    private PlayerCount view;
    private FileSelectDialog fileSelectDialog;

    /**
     * setter method assigns view 
     * @param view player count object
     */
    public PlayerCountController(PlayerCount view) {
        this.fileSelectDialog = new FileSelectDialog();
        this.view = view;
    }

    /**
     * overriding action performed method passing number of players selected to the model
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String event = actionEvent.getActionCommand();
        if (event.equals(threePlayersBtnName)) {

            //Open the MapFileChooseDialog box over here.
            //Instantiate the class over here and pass the value
            System.out.println("Now Choose the map file from the Dialog box");
            fileSelectDialog.addActionListener(new FileSelectDialogController(fileSelectDialog, 3));
            fileSelectDialog.setVisible(true);

        } else if (event.equals(fourPlayersBtnName)) {

            //Open the MapFileChooseDialog box over here.
            //Instantiate the class over here and pass the value
            System.out.println("Now Choose the map file from the Dialog box");
            fileSelectDialog.addActionListener(new FileSelectDialogController(fileSelectDialog, 4));
            fileSelectDialog.setVisible(true);
        } else if (event.equals(fivePlayersBtnName)) {
            //Open the MapFileChooseDialog box over here.
            //Instantiate the class over here and pass the value
            System.out.println("Now Choose the map file from the Dialog box");
            fileSelectDialog.addActionListener(new FileSelectDialogController(fileSelectDialog, 5));
            fileSelectDialog.setVisible(true);
        } else if (event.equals(sixPlayersBtnName)) {
            //Open the MapFileChooseDialog box over here.
            //Instantiate the class over here and pass the value
            System.out.println("Now Choose the map file from the Dialog box");
            fileSelectDialog.addActionListener(new FileSelectDialogController(fileSelectDialog, 6));
            fileSelectDialog.setVisible(true);
        } else if (event.equals(backBtnName)) {
            view.dispose();
        } else {
            System.out.println("Error: " + actionEvent + " actionEvent not found!");
        }
    }
}
