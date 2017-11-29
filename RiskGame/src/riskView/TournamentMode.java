package riskView;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import riskControllers.TournamentModeController;
import riskModels.map.GameMap.*;
import riskModels.map.MapModel.*;
import static util.RiskGameUtil.*;
/**
 * This class will get the details required to start the game in tournament mode.
 * @author mudraparikh
 *
 */
public class TournamentMode extends JDialog{
	public static JPanel tournamentDetailsPanel;
	public static GridLayout tournamentLayout;
    public static JLabel mapSelectLabel;
    public static JButton mapBtn1;
    public static JButton mapBtn2;
    public static JButton mapBtn3;
    public static JButton startGame;
    public static JLabel numberOfTurnsLabel;
    public static JLabel numberOfGames;
    public static JTextField turns;
    public static JComboBox <String> gamesList;
    public static String numTurns;
	public static String numGames;
	public static String[] games;
	public static JTextArea gameDetails;
    
	/**
	 * this constructor helps to set title,window size,default close operation
	 */
	public TournamentMode()
	{
		setTitle("Tournament Mode");
        setPreferredSize(new Dimension(250, 250));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);
        add(tournamentDetailsPanel());
        setLocationRelativeTo(null);
        pack();
	}
	
	/**
	 * The panel to display all the elements for playing tournament mode
	 * @return JPanel message
	 */
	public JPanel tournamentDetailsPanel()
	{
		tournamentDetailsPanel = new JPanel();
		tournamentLayout = new GridLayout();

		mapSelectLabel = new JLabel("Select the Map Files :");
		mapBtn1 = new JButton("Map 1");
		mapBtn2 = new JButton("Map 2");
		mapBtn3 = new JButton("Map 3");
		startGame = new JButton("Start Game");
		gameDetails = new JTextArea();
		gameDetails.setVisible(true);
		
		mapBtn1.setActionCommand(mapBtnName1);
		mapBtn2.setActionCommand(mapBtnName2);
		mapBtn3.setActionCommand(mapBtnName3);
		startGame.setActionCommand(startGameBtnName);
		
		numberOfTurnsLabel = new JLabel("Number of turns :");
		turns = new JTextField(5);
		
		numberOfGames = new JLabel("Number of Games :");
		games = new String[] {"1","2","3","4"};
		gamesList = new JComboBox<>(games);
		
		tournamentDetailsPanel.add(mapSelectLabel,0);
		tournamentDetailsPanel.add(mapBtn1, 1);
		tournamentDetailsPanel.add(mapBtn2, 2);
		tournamentDetailsPanel.add(mapBtn3, 3);
		tournamentDetailsPanel.add(numberOfTurnsLabel, 4);
		tournamentDetailsPanel.add(turns, 5);
		tournamentDetailsPanel.add(numberOfGames, 6);
		tournamentDetailsPanel.add(gamesList, 7);
		tournamentDetailsPanel.add(startGame, 8);
		tournamentDetailsPanel.add(gameDetails, 9);
		return tournamentDetailsPanel;
	}
	
	 /**
     * Adds the action listeners;
     * @param event Event that should occur in tournamentModeController
     */
	public void addActionListeners(TournamentModeController event) {
		mapBtn1.addActionListener(event);
	    mapBtn2.addActionListener(event);
	    mapBtn3.addActionListener(event);
	    startGame.addActionListener(event);
	}
 }