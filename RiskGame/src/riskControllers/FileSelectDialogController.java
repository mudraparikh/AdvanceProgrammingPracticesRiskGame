package riskControllers;

import riskModels.GamePlayModel;
import riskView.FileSelectDialog;
import riskView.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FileSelectDialogController implements ActionListener {

    private FileSelectDialog fileSelectDialog;
    private File selectedFile;
    private int playerCount;
    private GamePlayModel model = new GamePlayModel();
    private GameView gameView;


    public FileSelectDialogController(FileSelectDialog fileSelectDialog, int playerCount) {
        this.fileSelectDialog = fileSelectDialog;
        this.playerCount = playerCount;
        actionPerformed(null);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int result = fileSelectDialog.showOpenDialog(fileSelectDialog);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileSelectDialog.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            model.initData(selectedFile, playerCount);
            try {
                gameView = new GameView();
                //gameView.addActionListeners(new GameListController());
            } catch (IOException e) {
                e.printStackTrace();
            }
            gameView.setVisible(true);
        }
    }


}
