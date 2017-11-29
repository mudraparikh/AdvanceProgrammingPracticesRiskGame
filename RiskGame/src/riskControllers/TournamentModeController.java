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
import riskModels.player.Player;
import riskView.GameView;
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
    public boolean allValidMaps;
    MapModel mapModel = new MapModel();
    String fileName,fileName1,fileName2;
    List<String> selectedFiles=new ArrayList<String>();
    StringBuilder stringBuilder = new StringBuilder();
    GameView gameView;
    
    /**
     * Constructor assigning view
     * @param view tournament mode view
     */
    public TournamentModeController(TournamentMode view) {
    	this.view = view;
	}

    /**
     * over rides action performed method
     */
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
			for (String mapFile : selectedFiles) {
				gameMap = mapModel.readMapFile(mapFile);
				if (!gameMap.isCorrectMap) {
					System.out.println(mapFile + " is not Valid");
					System.out.println(gameMap.getErrorMessage());
					allValidMaps = false;
					break;
				}
				allValidMaps = true;
			}
			if (allValidMaps) {
				StringBuilder finalResult =new StringBuilder();
				TournamentModel tournamentModel = new TournamentModel(selectedFiles,numberOfGames,maxNumberOfIteration);
                for (String mapFile : tournamentModel.getMapFiles()) {
                    Player model = new Player();
                    
                    int currentGame=1;
                    StringBuilder result = new StringBuilder();
                    result.append("------------------->Map ::"+ mapFile+"\n");
                    for (int i = 1; i <= tournamentModel.getNumberOfGames(); i++) {
                    	currentGame=i;
                        model.initData(new File(mapFile),4,tournamentModel.getPlayerNames(),tournamentModel.getPlayerTypes(),true);
                        model.setDrawTurns(tournamentModel.getNumberOfTurns());
                        try {
                            gameView = new GameView();
                        } catch (IOException e) {
                           // e.printStackTrace();
                        }
                        //result.append("-------------------------ResultForGame"+ currentGame+"-------------\n");
                       
                        gameView.addActionListeners(new GamePlayController(model, gameView, false));
                        result.append("Game"+ i+"\n");
                        result.append("Result ::"+model.winner+"\n");
                        gameView.setVisible(true);
                        gameView=null;
                        GameMap.setInstance(null);
                        Player.hasBotWon=false;
                    }
                    
                    
                    
                    finalResult.append(result);
                   
                }
                System.out.println(finalResult.toString());
			}
			else{
                System.out.println("Looks like you have selected an invalid map(s) file !");
            }

		}
		else{
			System.out.println("Error: " + actionEvent + " actionEvent not found!");
		}
}
}
