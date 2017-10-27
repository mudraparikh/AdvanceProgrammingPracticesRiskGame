package riskControllers;

import riskModels.gamedriver.GamePlayAPI;
import riskView.FileSelectDialog;
import riskView.PlayerCount;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileSelectDialogController implements ActionListener {

    private FileSelectDialog fileSelectDialog;
    private File selectedFile;
    private int playerCount;


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
        }
    }
}
