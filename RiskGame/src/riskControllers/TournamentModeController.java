package riskControllers;

import static util.RiskGameUtil.mapBtnName1;
import static util.RiskGameUtil.mapBtnName2;
import static util.RiskGameUtil.mapBtnName3;
import static util.RiskGameUtil.startGameBtnName;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskView.TournamentMode;
import riskView.TournamentView;
import tournamentMode.TournamentModel;
/**
 * This class maps the user's input to the data and methods.
 * @author mudraparikh
 *
 */
public class TournamentModeController implements ActionListener{
	private JFileChooser filechooser = new JFileChooser();
	private TournamentMode view;
	private TournamentView tournamentView;
    GameMap gameMap = GameMap.getInstance();
    GameMap gameMap1 = GameMap.getInstance();
    GameMap gameMap2 = GameMap.getInstance();
    MapModel mapModel = new MapModel();
    String fileName,fileName1,fileName2;
    List<String> selectedFiles=new ArrayList<String>();
    StringBuilder stringBuilder = new StringBuilder();
    TournamentView add;
    
    
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
    	      //  gameMap = mapmodel.readMapFile(selectedFile.getAbsolutePath());
    	        
    	      //Condition to check if the correct file is selected.
                if (gameMap.isCorrectMap) {
                	stringBuilder.append("Map File 1 : "+fileName+"\n");
                	selectedFiles.add(selectedFile.getAbsolutePath());
               // model.initData(selectedFile, playerCount);
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
    	        //gameMap1 = mapmodel.readMapFile(selectedFile.getAbsolutePath());
    	        
    	      //Condition to check if the correct file is selected.
                if (gameMap.isCorrectMap) {
                	stringBuilder.append("Map File 2 : "+fileName1+"\n");
                	selectedFiles.add(selectedFile.getAbsolutePath());
                	
               // model.initData(selectedFile, playerCount);
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
    	        //gameMap2 = mapmodel.readMapFile(selectedFile.getAbsolutePath());
    	        
    	      //Condition to check if the correct file is selected.
                if (gameMap.isCorrectMap) {
                	stringBuilder.append("Map File 3 : "+fileName2+"\n");
                	selectedFiles.add(selectedFile.getAbsolutePath());
               // model.initData(selectedFile, playerCount);
                }
                else
                {
                	JOptionPane.showMessageDialog(null,GameMap.getInstance().getErrorMessage().toString());
                }
    	    }
		}
		else if(event.equals(startGameBtnName)) {
			System.out.println("Map files selected are :\n" + fileName+"\n"+fileName1+"\n"+fileName2+"\n");
			view.numTurns = view.turns.getText();
			stringBuilder.append("Number of Turns :"+view.numTurns+"\n");
			System.out.println("Number of turns :"+ view.numTurns);
			view.numGames = (String) view.gamesList.getSelectedItem();
			stringBuilder.append("Number of Games :"+ view.numGames);
			int maxNumberOfIteration =Integer.valueOf(view.numTurns);
			int numberOfGames =Integer.valueOf(view.numGames);
			System.out.println("Number of Games :"+ view.numGames);
			TournamentMode.gameDetails.setText(stringBuilder.toString());
			TournamentModel tournamentModel = new TournamentModel(selectedFiles,numberOfGames,maxNumberOfIteration);
			try {
				add = new TournamentView();
				add.addActionListeners(new TournamentViewController(add,tournamentModel));
				add.setVisible(true);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		else{
			System.out.println("Error: " + actionEvent + " actionEvent not found!");
		}
}
}
