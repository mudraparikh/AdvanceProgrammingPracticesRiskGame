package riskControllers;

import riskModels.map.GameMap;
import riskModels.player.Player;
import riskView.FileSelectDialog;
import riskView.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class maps the user's input to the data and methods in the model
 * @author hnath
 */
public class FileSelectDialogController implements ActionListener {

    private FileSelectDialog fileSelectDialog;
    private File selectedFile;
    private int playerCount;
    private Player model = new Player();
    private GameView gameView;

    private ArrayList<String> playerNames;
    private ArrayList<String> playerTypes;

/**
 * setter method assigns select file and the player count
 * @param fileSelectDialog map file selected
 * @param playerCount number of players selected
 * @param playerNames name of the players in the input box
 * @param playerTypes type of the player selected
 */
    public FileSelectDialogController(FileSelectDialog fileSelectDialog, int playerCount, ArrayList<String> playerNames, ArrayList<String> playerTypes) {
        this.fileSelectDialog = fileSelectDialog;
        this.playerCount = playerCount;
        this.playerNames = playerNames;
        this.playerTypes = playerTypes;
        actionPerformed(null);
    }

    /**
     * overrides action performed method taking fileSelectDialog and creates file object and sends to the model
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int result = fileSelectDialog.showOpenDialog(fileSelectDialog);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileSelectDialog.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            model.initData(selectedFile, playerCount, playerNames, playerTypes);
            try {
                gameView = new GameView();
                gameView.addActionListeners(new GamePlayController(model, gameView));
            } catch (IOException e) {
                e.printStackTrace();
            }
            gameView.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, GameMap.getInstance().getErrorMessage().toString());
        }
    }
}
