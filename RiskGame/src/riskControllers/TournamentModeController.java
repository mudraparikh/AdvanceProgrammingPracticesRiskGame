package riskControllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskView.TournamentMode;
import riskView.TournamentView;
import static util.RiskGameUtil.*;

public class TournamentModeController implements ActionListener{
	private JFileChooser filechooser = new JFileChooser();
	private TournamentMode view;
	private TournamentView tournamentView;
    GameMap gameMap = GameMap.getInstance();
    MapModel mapModel = new MapModel();
    String fileName,fileName1,fileName2;
    
    public TournamentModeController(TournamentMode view) {
    	this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		String event = actionEvent.getActionCommand();
		if (event.equals(mapBtnName1)) {
            System.out.println("Now Choose the first map file from the Dialog box");
        	//Displays the dialog to select a file from the user's machine.
    	    filechooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    	    filechooser.setDialogTitle("Select a .map file");
    	    int result = filechooser.showOpenDialog(filechooser);
    	    if (result == filechooser.APPROVE_OPTION) {
    	        File selectedFile = filechooser.getSelectedFile();
    	        fileName = selectedFile.getName();
    	        System.out.println("Selected file 1: " + selectedFile.getAbsolutePath());
    	        MapModel mapmodel = new MapModel();
    	        gameMap = mapmodel.readMapFile(selectedFile.getAbsolutePath());
    	        
    	      //Condition to check if the correct file is selected.
                if (gameMap.isCorrectMap) {
               // model.initData(selectedFile, playerCount);
                try {
                    tournamentView = new TournamentView();
                    //tournamentView.addActionListeners(new GamePlayController(model, gameView));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //tournamentView.setVisible(true);
            }
                else
                {
                	JOptionPane.showMessageDialog(null,GameMap.getInstance().getErrorMessage().toString());
                }
    	    }
        }
		else if(event.equals(mapBtnName2)) {
			System.out.println("Now Choose the first map file from the Dialog box");
        	//Displays the dialog to select a file from the user's machine.
    	    filechooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    	    filechooser.setDialogTitle("Select a .map file");
    	    int result = filechooser.showOpenDialog(filechooser);
    	    if (result == filechooser.APPROVE_OPTION) {
    	        File selectedFile = filechooser.getSelectedFile();
    	        fileName1 = selectedFile.getName();
    	        System.out.println("Selected file 2: " + selectedFile.getAbsolutePath());
    	        MapModel mapmodel = new MapModel();
    	        gameMap = mapmodel.readMapFile(selectedFile.getAbsolutePath());
    	        
    	      //Condition to check if the correct file is selected.
                if (gameMap.isCorrectMap) {
               // model.initData(selectedFile, playerCount);
                try {
                    tournamentView = new TournamentView();
                    //tournamentView.addActionListeners(new GamePlayController(model, gameView));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //tournamentView.setVisible(true);
            }
                else
                {
                	JOptionPane.showMessageDialog(null,GameMap.getInstance().getErrorMessage().toString());
                }
    	    }	
		}
		else if(event.equals(mapBtnName3)) {
			System.out.println("Now Choose the first map file from the Dialog box");
        	//Displays the dialog to select a file from the user's machine.
    	    filechooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    	    filechooser.setDialogTitle("Select a .map file");
    	    int result = filechooser.showOpenDialog(filechooser);
    	    if (result == filechooser.APPROVE_OPTION) {
    	        File selectedFile = filechooser.getSelectedFile();
    	        fileName2 = selectedFile.getName();
    	        System.out.println("Selected file 3: " + selectedFile.getAbsolutePath());
    	        MapModel mapmodel = new MapModel();
    	        gameMap = mapmodel.readMapFile(selectedFile.getAbsolutePath());
    	        
    	      //Condition to check if the correct file is selected.
                if (gameMap.isCorrectMap) {
               // model.initData(selectedFile, playerCount);
                try {
                    tournamentView = new TournamentView();
                    //tournamentView.addActionListeners(new GamePlayController(model, gameView));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //tournamentView.setVisible(true);
            }
                else
                {
                	JOptionPane.showMessageDialog(null,GameMap.getInstance().getErrorMessage().toString());
                }
    	    }
		}
		else if(event.equals(startGameBtnName)) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Map File 1 :"+fileName+"\n");
			stringBuilder.append("Map File 2 :"+fileName1+"\n");
			stringBuilder.append("Map File 3 :"+fileName2+"\n");
			System.out.println("Map files selected are :\n" + fileName+fileName1+fileName2);
			view.numTurns = view.turns.getText();
			stringBuilder.append("Numbber of Turns :"+view.numTurns);
			System.out.println("Number of turns :"+ view.numTurns);
			view.numGames = (String) view.gamesList.getSelectedItem();
			stringBuilder.append("Number of Games :"+ view.numGames);
			System.out.println("Number of Games :"+ view.numGames);
			view.gameDetails.setText(stringBuilder.toString());
			view.mapListPane.add(view.gameDetails);
		}
		else{
			System.out.println("Error: " + actionEvent + " actionEvent not found!");
		}
}
}
