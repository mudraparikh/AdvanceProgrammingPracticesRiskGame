package riskControllers;

import riskModels.player.Player;
import riskView.FileSelectDialog;
import riskView.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * This class maps the user's input to the data and methods in the model
 * @author hnath
 *
 */
public class FileSelectDialogController implements ActionListener {

    private FileSelectDialog fileSelectDialog;
    private File selectedFile;
    private int playerCount;
    private Player model = new Player();
    private GameView gameView;

/**
 * setter method assigns select file and the player count
 * @param fileSelectDialog map file selected
 * @param playerCount number of players selected
 */
    public FileSelectDialogController(FileSelectDialog fileSelectDialog, int playerCount) {
        this.fileSelectDialog = fileSelectDialog;
        this.playerCount = playerCount;
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
            model.initData(selectedFile, playerCount);
            try {
                gameView = new GameView();
                gameView.addActionListeners(new GamePlayController(model, gameView));
            } catch (IOException e) {
                e.printStackTrace();
            }
            gameView.setVisible(true);
        }
    }


}
