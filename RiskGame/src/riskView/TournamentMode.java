package riskView;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import riskControllers.TournamentModeController;

import static util.RiskGameUtil.*;
/**
 * This class will get the details required to start the game in tournament mode.
 * @author mudraparikh
 *
 */
public class TournamentMode extends JDialog{

    public static JButton mapBtn1;
    public static JButton mapBtn2;
    public static JButton mapBtn3;
	public static JButton mapBtn4;
	public static JButton mapBtn5;
    public static JButton startGame;

    public static JLabel numberOfTurnsLabel;
    public static JLabel numberOfGames;
    public static JLabel mapSelectLabel;

    public static JTextField turns;

    public static JComboBox <String> gamesList;

    public static String numTurns;
	public static String numGames;

	public static String[] games;

	public static JTextArea gameDetails;

    private JPanel playerNamesPanel;
    private JPanel playerTypesPanel;
    public static JPanel tournamentDetailsPanel;

    private GridLayout playerNamesLayout;
    private GridLayout playerTypesLayout;
    public static GridLayout tournamentLayout;

    private JTextField player1TextField;
    private JTextField player2TextField;
    private JTextField player3TextField;
    private JTextField player4TextField;

    private JComboBox<String> player1ComboBox;
    private JComboBox<String> player2ComboBox;
    private JComboBox<String> player3ComboBox;
    private JComboBox<String> player4ComboBox;

    private String[] types = {"Aggressive Bot", "Benevolent Bot", "Randomize Bot", "Cheater Bot" };
    
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
		//add(playerNamesPanel());
		//add(playerTypesPanel());
        setLocationRelativeTo(null);
        pack();
	}

    private JPanel playerTypesPanel() {
        playerTypesPanel = new JPanel();

        playerTypesPanel.setPreferredSize(new Dimension(200, 4 * 40 + 40));

        //playerTypesLayout = new GridLayout(4 + 1, 1, 5, 5);
        //playerTypesPanel.setLayout(playerTypesLayout);

        player1ComboBox = new JComboBox<>(types);
        player2ComboBox = new JComboBox<>(types);
        player3ComboBox = new JComboBox<>(types);
        player4ComboBox = new JComboBox<>(types);

        playerTypesPanel.add(player1ComboBox);
        playerTypesPanel.add(player2ComboBox);
        playerTypesPanel.add(player3ComboBox);
        playerTypesPanel.add(player4ComboBox);

        return playerTypesPanel;
    }

    private JPanel playerNamesPanel() {
        playerNamesPanel = new JPanel();

        playerNamesPanel.setPreferredSize(new Dimension(200, 4 * 40 + 40));

        //playerNamesLayout = new GridLayout(4 + 1, 1, 5, 5);
       // playerNamesPanel.setLayout(playerNamesLayout);

        player1TextField = new JTextField("Aggressive");
        player2TextField = new JTextField("Benevolent");
        player3TextField = new JTextField("Random");
        player4TextField = new JTextField("Cheater");

        playerNamesPanel.add(player1TextField);
        playerNamesPanel.add(player2TextField);
        playerNamesPanel.add(player3TextField);
        playerNamesPanel.add(player4TextField);
        return playerNamesPanel;
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
        mapBtn4 = new JButton("Map 4");
        mapBtn5 = new JButton("Map 5");
		startGame = new JButton("Start Game");
		gameDetails = new JTextArea();
		gameDetails.setVisible(true);
		
		mapBtn1.setActionCommand(mapBtnName1);
		mapBtn2.setActionCommand(mapBtnName2);
		mapBtn3.setActionCommand(mapBtnName3);
        mapBtn4.setActionCommand(mapBtnName4);
        mapBtn5.setActionCommand(mapBtnName5);
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
        tournamentDetailsPanel.add(mapBtn4, 4);
        tournamentDetailsPanel.add(mapBtn5, 5);
		tournamentDetailsPanel.add(numberOfTurnsLabel, 6);
		tournamentDetailsPanel.add(turns, 7);
		tournamentDetailsPanel.add(numberOfGames, 8);
		tournamentDetailsPanel.add(gamesList, 9);
		tournamentDetailsPanel.add(startGame, 10);
		tournamentDetailsPanel.add(gameDetails, 11);
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
        mapBtn4.addActionListener(event);
        mapBtn5.addActionListener(event);
	    startGame.addActionListener(event);
	}

    /**
     * Getter methods for text field of players
     * @param playerNum maintains the player number
     * @return getText value from the respective text-field
     */

    public String getPlayerTextField(int playerNum)
    {
        if (playerNum == 1)
        {
            return player1TextField.getText();
        }
        else if (playerNum == 2)
        {
            return player2TextField.getText();
        }
        else if (playerNum == 3)
        {
            return player3TextField.getText();
        }
        else
        {
            return player4TextField.getText();
        }
    }

    // Get methods for combo boxes
    /**
     * Getter methods for the comboBox
     * @param playerNum maintains the player number
     * @return getSelectedItem the value of the selected item form the comboBox
     */

    public String getPlayerComboBox(int playerNum)
    {
        if (playerNum == 1)
        {
            return player1ComboBox.getSelectedItem().toString();
        }
        else if (playerNum == 2)
        {
            return player2ComboBox.getSelectedItem().toString();
        }
        else if (playerNum == 3)
        {
            return player3ComboBox.getSelectedItem().toString();
        }
        else
        {
            return player4ComboBox.getSelectedItem().toString();
        }
    }
 }