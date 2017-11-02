package riskView;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import riskControllers.*;
import riskModels.country.*;
import riskModels.map.*;
import riskModels.gamedriver.*;


/**
 * This class will load the game board 
 * @author mudraparikh
 *
 */
public class GameView extends JDialog{
	private JPanel messagePanel;
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
	private JScrollPane phaseViewPane;
	private JScrollPane playerViewPane;
	private JScrollPane dominationViewPane;
	
	private JLabel selectedLabel;
	private JLabel targetLabel;
	private JLabel continentLabel;
	private JLabel dominationLabel;
	private JLabel phaseViewLabel;
	private JLabel playerViewLabel;
	
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
	private DefaultListModel<String> continentDisplay;
	private DefaultListModel<String> countryDisplay1;
	private DefaultListModel<String> countryDisplay2;

	private GameMap gameMap;
	private MapModel mapModel;
	
	private JList cardsList;
	private ListModel cardsListModel;
	
	private ImageIcon mapImageIcon;
	private File mapFile;
	private JTextArea printTextArea;
	private DefaultCaret caret;
	

	/**
	 * Constructs the Risk game board.
	 **/
	public GameView() {		
		
		setTitle("Risk Game");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		
		gameMap = GameMap.getInstance();
		mapModel = new MapModel();
		
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
	
	private JPanel actionPanel() {
		actionPanel = new JPanel();
		actionPanel.setPreferredSize(new Dimension(200,690));
		messageLayout = new GridBagLayout();
		actionPanel.setLayout(messageLayout);
		
		menuBtn = new JButton("Menu");
		turnInBtn = new JButton("Turn In Cards");
		
		dominationLabel = new JLabel("Player Domaination %:");
		//model.addObserver(dominationLabel);
		dominationViewPane = new JScrollPane(dominationLabel);
		actionPanel.add(dominationViewPane);
		
		phaseViewLabel = new JLabel("Phase View:");
		//model.addObserver(phaseViewLabel);
		phaseViewPane = new JScrollPane(phaseViewLabel);
		actionPanel.add(phaseViewPane);
		
		playerViewLabel = new JLabel("Player View:");
		//model.addObserver(playerViewLabel);
		playerViewPane = new JScrollPane(playerViewLabel);
		actionPanel.add(playerViewPane);
		
		c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 10;
		c.gridx = 0;
		c.gridy = 0;
		actionPanel.add(playerViewPane, c);
		
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
		c.weighty = 10;
		c.gridx = 0;
		c.gridy = 2;
		actionPanel.add(dominationViewPane, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 16;
		c.gridx = 0;
		c.gridy = 3;
		actionPanel.add(cardsList, c);

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 4;
		actionPanel.add(turnInBtn, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 5;
		actionPanel.add(menuBtn, c);
		
		return actionPanel;
	}

	/**
	 * The panel for the logger message display, card display and turn-in button.
	 **/
	private JPanel messagePanel() {
	
		messagePanel = new JPanel();
		messagePanel.setPreferredSize(new Dimension(200,690));
		messageLayout = new GridBagLayout();
		messagePanel.setLayout(messageLayout);
		
		printTextArea = new JTextArea();
		System.out.println(printTextArea);
		//System.setOut(new PrintStream(new TextAreaOutputStream(printTextArea)));
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
		
		cardsList = new JList();
		cardsList.setLayoutOrientation(JList.VERTICAL_WRAP);
		cardsList.setVisibleRowCount(6);
		
		selectedLabel = new JLabel("Selected Territory:");
		targetLabel = new JLabel("Adjacent Territory:");
		continentLabel = new JLabel("Continents:");
		
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
  /**
   * The panel for the map and load display as per users choice.
  **/
private JPanel mapPanel() {
	mapPanel = new JPanel();
	mapPanel.setLayout(new GridLayout(1, 1, 5, 5));
	mapImageIcon = new ImageIcon("Map.jpg");
	mapScrollPane = new JScrollPane(new JLabel(mapImageIcon));
	mapScrollPane.setPreferredSize(new Dimension(690, 690));
	mapPanel.add(mapScrollPane);
	return mapPanel;
}

/**
 * The panel to display the list of continents, countries and their adjacent territories.
**/
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
	countryList2 = new JList(countryDisplay1);
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
}