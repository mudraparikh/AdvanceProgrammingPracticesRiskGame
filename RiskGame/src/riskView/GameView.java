package riskView;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.text.DefaultCaret;

import riskModels.GameListModel;
import riskModels.GamePlayModel;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.Player;
import riskModels.player.PlayerModel;


/**
 * This class will load the game board 
 * @author mudraparikh
 *
 **/
public class GameView extends JDialog{
	public JPanel messagePanel;
	private JPanel mapPanel;
	private JPanel countryInfoPanel;
	private JPanel actionPanel;
	
	private GridBagLayout mainLayout;
	private GridBagConstraints c;
	private GridBagLayout messageLayout;
	private GridBagLayout actionLayout;
	
	private JScrollPane messageScrollPane;
	private JScrollPane mapScrollPane;
	private JScrollPane continentScrollPane;
	private JScrollPane countryScrollPane1;
	private JScrollPane countryScrollPane2;
	public static JScrollPane phaseViewPane;
	public static  JScrollPane dominationViewPane;
	
	private JLabel selectedLabel;
	private JLabel targetLabel;
	private JLabel continentLabel;
	
	private JButton menuBtn;
	private JButton turnInBtn;
	private JButton reinforceBtn;
	private JButton attackBtn;
	private JButton fortifyBtn;
	private JButton endTurnBtn;
	
	private String menuBtnName;
	private String turnInBtnName;
	private String reinforceBtnName;
	private String attackBtnName;
	private String fortifyBtnName;
	private String endTurnBtnName;
	
	private JList<String> continentList;
	private JList<String> countryList1;
	private JList countryList2;
	private JList cardsList;
	private DefaultListModel<String> continentDisplay;
	private DefaultListModel<String> countryDisplay1;
	private DefaultListModel<String> countryDisplay2;

	private GameMap gameMap;
	private MapModel mapModel;
	public  PlayerModel playerModel= new PlayerModel();
	private GamePlayModel serviceLayer;
	private GameListModel countryAListModel;
	private GameListModel countryBListModel;
	
	private static JTextArea printTextArea;
	public static JTextArea dominationTextArea;
	public static JTextArea phaseViewTextArea;

    private ImageIcon mapImageIcon;
    
	private DefaultCaret caret;
	

	/*
	 * Constructs the Risk game board.
	 */
	public GameView() throws IOException {
		
		setTitle("Risk Game");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);

        gameMap = GameMap.getInstance();
        mapModel = new MapModel();
        Player player = new Player();
		//  GridBagLayout allows a flexible sizing of components
		mainLayout = new GridBagLayout();
		setLayout(mainLayout);
		
		c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 8;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		add(mapPanel());
		
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		add(countryInfoPanel());
		
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		add(messagePanel());
		
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 3;
		c.gridy = 0;
		add(actionPanel());
		
		setLocationRelativeTo(null);
		
		pack();
	}
	public GameView(String update) {
		
	}
	/*
	 * The panel for the logger message display and game play buttons.
	 */
	private JPanel messagePanel() {
	
		messagePanel = new JPanel();
		messagePanel.setPreferredSize(new Dimension(200,690));
		messageLayout = new GridBagLayout();
		messagePanel.setLayout(messageLayout);
		
		printTextArea = new JTextArea();
		System.out.println(printTextArea);
		printTextArea.setFocusable(false);
		printTextArea.setLineWrap(true);
		printTextArea.setWrapStyleWord(true);
		caret = (DefaultCaret)printTextArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		messageScrollPane = new JScrollPane(printTextArea);
		
		reinforceBtn = new JButton("Reinforce Armies");
		attackBtn = new JButton("Attack!");
		fortifyBtn = new JButton("Fortify");
		endTurnBtn = new JButton("End Turn");
	
		reinforceBtn.setActionCommand(reinforceBtnName);
		attackBtn.setActionCommand(attackBtnName);
		fortifyBtn.setActionCommand(fortifyBtnName);
		endTurnBtn.setActionCommand(endTurnBtnName);

		c = new GridBagConstraints();
					
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		messagePanel.add(reinforceBtn, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		messagePanel.add(attackBtn, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		messagePanel.add(fortifyBtn, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 3;
		messagePanel.add(endTurnBtn, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 18 ;
		c.gridx = 0;
		c.gridy = 4;
		messagePanel.add(messageScrollPane, c);
		return messagePanel;
	}
	
	/*
	 * The panel for the logger message display and game play buttons.
	 */
	private JPanel actionPanel() {
		actionPanel = new JPanel();
		actionPanel.setPreferredSize(new Dimension(200,690));
		messageLayout = new GridBagLayout();
		actionPanel.setLayout(messageLayout);
		
		menuBtn = new JButton("Menu");
		turnInBtn = new JButton("Turn In Cards");
		
		cardsList = new JList();
		cardsList.setLayoutOrientation(JList.VERTICAL_WRAP);
		cardsList.setVisibleRowCount(6);
		
		selectedLabel = new JLabel("Selected Territory:");
		targetLabel = new JLabel("Adjacent Territory:");
		continentLabel = new JLabel("Continents:");
		
		dominationTextArea = new JTextArea();
		System.out.println(dominationTextArea);
		dominationTextArea.setFocusable(false);
		dominationTextArea.setLineWrap(true);
		dominationTextArea.setWrapStyleWord(true);
		dominationViewPane = new JScrollPane(dominationTextArea);
		actionPanel.add(dominationViewPane);
		PlayerView playerView= new PlayerView();
		playerModel.addObserver(playerView);
		playerModel.getPlyaerWorldDomination(GameMap.getInstance().getPlayerList());
		
		phaseViewTextArea = new JTextArea();
		System.out.println(phaseViewTextArea);
		phaseViewTextArea.setFocusable(false);
		phaseViewTextArea.setLineWrap(true);
		phaseViewTextArea.setWrapStyleWord(true);
		phaseViewPane = new JScrollPane(phaseViewTextArea);
		actionPanel.add(phaseViewPane);
		playerModel.getPhaseDetails();
		
		c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 10;
		c.gridx = 0;
		c.gridy = 0;
		actionPanel.add(dominationViewPane, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 10;
		c.gridx = 0;
		c.gridy = 1;
		actionPanel.add(phaseViewPane, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 25;
		c.gridx = 0;
		c.gridy = 2;
		actionPanel.add(cardsList, c);

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 3;
		actionPanel.add(turnInBtn, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 4;
		actionPanel.add(menuBtn, c);
		
		return actionPanel;
	}
  /*
   * The panel for the map and load display as per users choice.
  */
private JPanel mapPanel() throws IOException {
	mapPanel = new JPanel();
	mapPanel.setLayout(new GridLayout(1, 1, 5, 5));
	String imageFile = gameMap.getMapDetail().get("image");
    Image image = ImageIO.read(new File(imageFile));
    System.out.println(imageFile);
    mapImageIcon = new ImageIcon(image);
	mapScrollPane = new JScrollPane(new JLabel(mapImageIcon));
	mapScrollPane.setPreferredSize(new Dimension(675, 690));
	mapPanel.add(mapScrollPane);
	return mapPanel;
}

/*
 * The panel to display the list of continents, countries and their adjacent territories.
*/
private JPanel countryInfoPanel() {
	countryInfoPanel = new JPanel();
	countryInfoPanel.setPreferredSize(new Dimension(250,690));
	actionLayout = new GridBagLayout();
	countryInfoPanel.setLayout(actionLayout);
	
	selectedLabel = new JLabel("Selected Country:");
	targetLabel = new JLabel("Neighbouring Countries:");
	continentLabel = new JLabel("Continents:");
	


	
	countryDisplay1 = new DefaultListModel<>();
	countryList1 = new JList(countryDisplay1);
	countryList1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	countryList1.setLayoutOrientation(JList.VERTICAL_WRAP);
	countryList1.setVisibleRowCount(30);
	countryScrollPane1 = new JScrollPane(countryList1);
	countryList1.setVisible(true);
	for (Country c : gameMap.getCountryAndNeighborsMap().keySet())
		countryDisplay1.addElement(c.getCountryName());
	
	countryDisplay2 = new DefaultListModel<>();
	countryList2 = new JList(countryDisplay2);
	countryList2.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	countryList2.setLayoutOrientation(JList.VERTICAL_WRAP);
	countryList2.setVisibleRowCount(30);
	countryScrollPane2 = new JScrollPane(countryList2);
	countryList2.setVisible(true);
	for (Country c : gameMap.getCountryAndNeighborsMap().keySet()) {
		List<Country> neighborCountries = new ArrayList<>();
		neighborCountries = c.getNeighborNodes();
		for(Country nCountry : neighborCountries) {
			countryDisplay2.addElement(nCountry.getCountryName());
		}
	}
	
	continentDisplay = new DefaultListModel<>();
	continentList = new JList(continentDisplay);
	continentList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	continentList.setLayoutOrientation(JList.VERTICAL_WRAP);
	continentList.setVisibleRowCount(50);
	continentScrollPane = new JScrollPane(continentList);
	continentList.setVisible(true);
	for (int i = 0; i < gameMap.getContinentList().size(); i++)
     continentDisplay.addElement(gameMap.getContinentList().get(i).getContinentName());
	 continentList.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent evt) {
		 System.out.println("Selected continent"+continentList.getSelectedValue());
		 countryDisplay1.removeAllElements();
		 countryDisplay2.removeAllElements();
		 for (Country c : gameMap.getCountryAndNeighborsMap().keySet()) {
			 if(c.getBelongsToContinent().equalsIgnoreCase(continentList.getSelectedValue())) {
				 countryDisplay1.addElement(c.getCountryName());
			 }
			
		 }
				
		}
	});
	 countryList1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
			 System.out.println("Selected country"+countryList1.getSelectedValue());
			 countryDisplay2.removeAllElements();
			 List<Country> neighbours=GameMap.getInstance().getCountryAndNeighborsMap().get(new Country(countryList1.getSelectedValue()));
			 for(Country country :neighbours) {
				 countryDisplay2.addElement(country.getCountryName());
			 }		
			}
		});
	
	c = new GridBagConstraints();
	
	c.fill = GridBagConstraints.BOTH;
	c.insets = new Insets(5, 5, 5, 5);
	c.weightx = 0.5;
	c.weighty = 0.5;
	c.gridx = 0;
	c.gridy = 0;
	countryInfoPanel.add(continentLabel, c);
	
	c.fill = GridBagConstraints.BOTH;
	c.insets = new Insets(5, 5, 5, 5);
	c.weightx = 0.5;
	c.weighty = 14;
	c.gridx = 0;
	c.gridy = 1;
	countryInfoPanel.add(continentScrollPane, c);
	
	c.fill = GridBagConstraints.BOTH;
	c.insets = new Insets(5, 5, 5, 5);
	c.weightx = 0.5;
	c.weighty = 0.5;
	c.gridx = 0;
	c.gridy = 2;
	countryInfoPanel.add(selectedLabel, c);
	
	c.fill = GridBagConstraints.BOTH;
	c.insets = new Insets(5, 5, 5, 5);
	c.weightx = 0.5;
	c.weighty = 5;
	c.gridx = 0;
	c.gridy = 3;
	countryInfoPanel.add(countryScrollPane1, c);
	
	c.fill = GridBagConstraints.BOTH;
	c.insets = new Insets(5, 5, 5, 5);
	c.weightx = 0.5;
	c.weighty = 0.5;
	c.gridx = 0;
	c.gridy = 4;
	countryInfoPanel.add(targetLabel, c);
	
	c.fill = GridBagConstraints.BOTH;
	c.insets = new Insets(5, 5, 5, 5);
	c.weightx = 0.5;
	c.weighty = 5;
	c.gridx = 0;
	c.gridy = 5;
	countryInfoPanel.add(countryScrollPane2, c);

	return countryInfoPanel;
}
/**
 * This method will display updated logs in main window of the game 
 * @param logDetail log message that you want to add.
 */
public static void displayLog(String logDetail) {
	String existingDetails = printTextArea.getText();
	StringBuilder stringBuid = new StringBuilder(existingDetails);
	printTextArea.setText("\n"+stringBuid.append(logDetail)+"\n");
	
}
}