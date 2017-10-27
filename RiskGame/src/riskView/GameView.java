package riskView;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import riskControllers.FileSelectDialogController;
import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;


/**
 * This class will load the game board 
 * @author mudraparikh
 *
 */
public class GameView extends JDialog{
	private JPanel messagePanel;
	private JPanel mapPanel;
	private JPanel actionPanel;
	private JPanel countryInfoPanel;
	
	private GridBagLayout mainLayout;
	private GridBagConstraints c;
	private GridBagLayout messageLayout;
	private GridBagLayout actionLayout;
	
	private JScrollPane messageScrollPane;
	private JScrollPane mapScrollPane;
	
	private JLabel selectedLabel;
	private JLabel targetLabel;
	
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
	private DefaultListModel<String> continentDisplay;
	private JList<String> countryList1;
	private DefaultListModel<String> countryDisplay1;
	private JList countryList2;
	private DefaultListModel<String> countryDisplay2;

	private GameMap gameMap;
	private MapModel mapModel;
	
	private ImageIcon mapImageIcon;
	private File mapFile;
	private JTextArea printTextArea;
	private DefaultCaret caret;
	private JScrollPane continentScrollPane;
	private JScrollPane countryScrollPane1;
	private JScrollPane countryScrollPane2;

	/**
	 * Constructs the Risk game board.
	 **/
	public GameView() {		
		
		setTitle("Java-Risk");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		
		//  GridBagLayout allows a flexible sizing of components
		mainLayout = new GridBagLayout();
		setLayout(mainLayout);
		
		c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		add(messagePanel());
		
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 8;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		add(mapPanel());
		
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		add(actionPanel());
		
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		//add(countryInfoPanel(model.getBoard()));
		
		setLocationRelativeTo(null);
		
		pack();
	}
	
	/**
	 * The panel for the logger message display, card display and turn-in button.
	 **/
	private JPanel messagePanel() {
	
		messagePanel = new JPanel();
		messagePanel.setPreferredSize(new Dimension(300,675));
		messageLayout = new GridBagLayout();
		messagePanel.setLayout(messageLayout);
		
		c = new GridBagConstraints();
		
		printTextArea = new JTextArea();
		System.out.println(printTextArea);
		//System.setOut(new PrintStream(new TextAreaOutputStream(printTextArea)));
		printTextArea.setFocusable(false);
		printTextArea.setLineWrap(true);
		printTextArea.setWrapStyleWord(true);
		caret = (DefaultCaret)printTextArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		messageScrollPane = new JScrollPane(printTextArea);
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.weightx = 0.5;
		c.weighty = 14 ;
		c.gridx = 0;
		c.gridy = 0;
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
	mapScrollPane.setPreferredSize(new Dimension(675, 675));
	mapPanel.add(mapScrollPane);
	return mapPanel;
}
/**
 * The panel to display the buttons for user to play the game.
**/
private JPanel actionPanel() {
	actionPanel = new JPanel();
	
	actionPanel.setPreferredSize(new Dimension(200, 675));
	
	actionLayout = new GridBagLayout();
	actionPanel.setLayout(actionLayout);
	
	selectedLabel = new JLabel("Selected Territory:");
	targetLabel = new JLabel("Adjacent Territory:");
	
	menuBtn = new JButton("Menu");
	turnInBtn = new JButton("Turn In Cards");
	reinforceBtn = new JButton("Place Reinforcements");
	attackBtn = new JButton("Attack!");
	fortifyBtn = new JButton("Fortify");
	endTurnBtn = new JButton("End Turn");
	
	menuBtn.setActionCommand(menuBtnName);
	turnInBtn.setActionCommand(turnInBtnName);
	reinforceBtn.setActionCommand(reinforceBtnName);
	attackBtn.setActionCommand(attackBtnName);
	fortifyBtn.setActionCommand(fortifyBtnName);
	endTurnBtn.setActionCommand(endTurnBtnName);
	
	gameMap = GameMap.getInstance();
	mapModel = new MapModel();
	
	continentDisplay = new DefaultListModel<>();
	continentList = new JList(continentDisplay);
	continentList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	continentList.setLayoutOrientation(JList.VERTICAL_WRAP);
	continentList.setVisibleRowCount(10);
	continentScrollPane = new JScrollPane(continentList);
	continentList.setVisible(true);
	for (int i = 0; i < gameMap.getContinentList().size(); i++)
      continentDisplay.addElement(gameMap.getContinentList().get(i).getContinentName());
	
	countryDisplay1 = new DefaultListModel<>();
	countryList1 = new JList(countryDisplay1);
	countryList1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	countryList1.setLayoutOrientation(JList.VERTICAL_WRAP);
	countryList1.setVisibleRowCount(10);
	countryScrollPane1 = new JScrollPane(countryList1);
	countryList1.setVisible(true);
	for (Map.Entry<Country, List<Country>> e : gameMap.getCountryAndNeighborsMap().entrySet())
		countryDisplay1.addElement(e.getKey().getCountryName());
	
	countryDisplay2 = new DefaultListModel<>();
	countryList2 = new JList(countryDisplay1);
	countryList2.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	countryList2.setLayoutOrientation(JList.VERTICAL_WRAP);
	countryList2.setVisibleRowCount(10);
	countryScrollPane2 = new JScrollPane(countryList2);
	countryList2.setVisible(true);
	for (Map.Entry<Country, List<Country>> e : gameMap.getCountryAndNeighborsMap().entrySet())
		countryDisplay2.addElement(e.getKey().getCountryName());
	
	c = new GridBagConstraints();
	
	c.fill = GridBagConstraints.BOTH;
	c.insets = new Insets(5, 5, 5, 5);
	c.weightx = 0.5;
	c.weighty = 0.5;
	c.gridx = 0;
	c.gridy = 0;
	actionPanel.add(continentScrollPane, c);
	
	c.fill = GridBagConstraints.BOTH;
	c.insets = new Insets(5, 5, 5, 5);
	c.weightx = 0.5;
	c.weighty = 0.5;
	c.gridx = 0;
	c.gridy = 1;
	actionPanel.add(countryScrollPane1, c);

	c.fill = GridBagConstraints.BOTH;
	c.insets = new Insets(5, 5, 5, 5);
	c.weightx = 0.5;
	c.weighty = 0.5;
	c.gridx = 0;
	c.gridy = 2;
	actionPanel.add(countryScrollPane2, c);
	
	/*c.fill = GridBagConstraints.BOTH;
	c.insets = new Insets(5, 5, 5, 5);
	c.weightx = 0.5;
	c.weighty = 0.5;
	c.gridx = 0;
	c.gridy = 4;
	actionPanel.add(selectedLabel, c);
	
	c.fill = GridBagConstraints.BOTH;
	c.insets = new Insets(5, 5, 5, 5);
	c.weightx = 0.5;
	c.weighty = 10;
	c.gridx = 0;
	c.gridy = 5;
	actionPanel.add(countryAScrollPane, c);
	
	c.fill = GridBagConstraints.BOTH;
	c.insets = new Insets(5, 5, 5, 5);
	c.weightx = 0.5;
	c.weighty = 0.5;
	c.gridx = 0;
	c.gridy = 6;
	actionPanel.add(reinforceBtn, c);
	
	c.fill = GridBagConstraints.BOTH;
	c.insets = new Insets(5, 5, 5, 5);
	c.weightx = 0.5;
	c.weighty = 0.5;
	c.gridx = 0;
	c.gridy = 7;
	actionPanel.add(targetLabel, c);
	
	c.fill = GridBagConstraints.BOTH;
	c.insets = new Insets(5, 5, 5, 5);
	c.weightx = 0.5;
	c.weighty = 10;
	c.gridx = 0;
	c.gridy = 8;
	actionPanel.add(countryBScrollPane, c);
	
	c.fill = GridBagConstraints.BOTH;
	c.insets = new Insets(5, 5, 5, 5);
	c.weightx = 0.5;
	c.weighty = 0.5;
	c.gridx = 0;
	c.gridy = 9;
	actionPanel.add(attackBtn, c);
	
	c.fill = GridBagConstraints.BOTH;
	c.insets = new Insets(5, 5, 5, 5);
	c.weightx = 0.5;
	c.weighty = 0.5;
	c.gridx = 0;
	c.gridy = 10;
	actionPanel.add(fortifyBtn, c);
	
	c.fill = GridBagConstraints.BOTH;
	c.insets = new Insets(5, 5, 5, 5);
	c.weightx = 0.5;
	c.weighty = 0.5;
	c.gridx = 0;
	c.gridy = 11;
	actionPanel.add(endTurnBtn, c);*/
	return actionPanel;
}
/**
 * The panel to display the list of continents, countries and their adjacent territories.
**/
private JPanel CountrynfoPanel() {
	return countryInfoPanel;
	
}
}
