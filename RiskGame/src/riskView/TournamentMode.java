package riskView;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
	public static JScrollPane mapListPane;
	public static JTextArea gameDetails;
    
	/**
	 * this constructor helps to set title,window size,default close operation
	 */
	public TournamentMode()
	{
		setTitle("--Tournament Mode--");
        setPreferredSize(new Dimension(250, 310));
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);
        add(tournamentDetailsPanel());
        setLocationRelativeTo(null);
        pack();
	}
	
	public JPanel tournamentDetailsPanel()
	{
		tournamentDetailsPanel = new JPanel();
		tournamentLayout = new GridLayout();
		
		
		mapSelectLabel = new JLabel("---Select the Map Files---");
		mapBtn1 = new JButton("Map 1");
		mapBtn2 = new JButton("Map 2");
		mapBtn3 = new JButton("Map 3");
		startGame = new JButton("Start Game");
		gameDetails = new JTextArea();
		mapListPane = new JScrollPane();
		mapListPane.setPreferredSize(new Dimension(150,150));
		mapListPane.setVisible(true);
		
		mapBtn1.setActionCommand(mapBtnName1);
		mapBtn2.setActionCommand(mapBtnName2);
		mapBtn3.setActionCommand(mapBtnName3);
		startGame.setActionCommand(startGameBtnName);
		
		numberOfTurnsLabel = new JLabel("Number of turns :");
		turns = new JTextField(5);
		
		
		numberOfGames = new JLabel("Number of Games :");
		games = new String[] {"1","2","3","4"};
		gamesList = new JComboBox<>(games);
		
		
		tournamentDetailsPanel.add(mapBtn1);
		tournamentDetailsPanel.add(mapBtn2);
		tournamentDetailsPanel.add(mapBtn3);
		tournamentDetailsPanel.add(mapListPane);
		tournamentDetailsPanel.add(numberOfTurnsLabel);
		tournamentDetailsPanel.add(turns);
		tournamentDetailsPanel.add(numberOfGames);
		tournamentDetailsPanel.add(gamesList);
		tournamentDetailsPanel.add(startGame);
		tournamentDetailsPanel.add(mapListPane);
		return tournamentDetailsPanel;
	}
	 /**
     * Adds the action listeners;
     */
    protected void addActionListeners(ActionListener event) {
    mapBtn1.addActionListener(event);
    mapBtn2.addActionListener(event);
    mapBtn3.addActionListener(event);
    startGame.addActionListener(event);
    }
 }