package riskView.map;

import riskModels.country.Country;
import riskModels.gamedriver.StartupPhase;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.continent.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Caret;
import javax.swing.text.Document;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * MapView class holds the property of generating map.
 *
 * @author Mudra
 */
@SuppressWarnings("serial")
public class MapView extends java.awt.Frame {

	private JFileChooser filechooser = new JFileChooser();
	private JDialog dialog = new JDialog();
	private JLabel label = new JLabel();
	private JFrame frame = new JFrame();
	private JLabel label1 = new JLabel();
	private JButton btn_continent = new JButton();
	private JButton btn_country = new JButton();
	private JButton btn_territory = new JButton();
	private JList continentList,countryList,territoryList;
	private DefaultListModel continentDisplay,countryDisplay,territoryDisplay;
	private JFrame frameContinent,frameCountry,frameTerritory;
	private JLabel labelContinent,labelCountry,labelTerritory;
	private JTextField textContinent,textCountry,textTerritory;
	private JButton button1,button2,button3;
	private int counter=15;
	 GameMap gameMap = new GameMap();
	
	public MapView(){
		initMapComponents();
	}
	
	//Function to Add or Remove Continents from a Map
	
	private void continent() {
	    continentDisplay = new DefaultListModel();
	    continentList = new JList(continentDisplay);
	    JScrollPane pane = new JScrollPane(continentList);
	    JButton addButton = new JButton("Add Continent");
	    JButton removeButton = new JButton("Remove Continent");
	    JButton saveButton = new JButton("Save Changes");
	    JLabel labelContinent = new JLabel("Enter Continent name :");
	    JFrame frameContinent = new JFrame();
	    JTextField textContinent= new JTextField();
	    JButton button1 = new JButton("OK");
	    
	    
	    
	    for (int i = 0; i <gameMap.getContinentList().size(); i++)
	      continentDisplay.addElement(gameMap.getContinentList().get(i).getContinentName());

	    addButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	    	frameContinent.setTitle("Add Continent");
	  	    frameContinent.setVisible(true);
	  	    frameContinent.setSize(250,150);
	  	    frameContinent.setLocationRelativeTo(null);
	  	    frameContinent.add(labelContinent, BorderLayout.PAGE_START);
	  	    frameContinent.add(textContinent, BorderLayout.CENTER);
	  	    frameContinent.add(button1,BorderLayout.PAGE_END );
	  	    button1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					continentDisplay.addElement(textContinent.getText());
				}
	  	    });
	      }
	    });
	    
	    
	    removeButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        if (continentDisplay.getSize() > 0)
	          continentDisplay.removeElement(0);
	      }
	    });
	    JFrame frame1 = new JFrame();
	    frame1.setTitle("Add or Remove Continent");
		frame1.setVisible(true);
		frame1.setSize(450,300);
		frame1.setLocationRelativeTo(null);
	    frame1.add(pane, BorderLayout.NORTH);
	    frame1.add(addButton, BorderLayout.WEST);
	    frame1.add(removeButton, BorderLayout.CENTER);
	    frame1.add(saveButton, BorderLayout.EAST);
	  }		
	
	//Function to Add or Remove Countries from a Map

	private void country() {
		countryDisplay = new DefaultListModel();
	    countryList = new JList(countryDisplay);
	    JScrollPane pane = new JScrollPane(countryList);
	    JButton addButton = new JButton("Add Country");
	    JButton removeButton = new JButton("Remove Country");
	    JButton saveButton = new JButton("Save Changes");
	    for(Map.Entry<Country, List<Country>> e: gameMap.getCountryAndNeighborsMap().entrySet()) {
	    	countryDisplay.addElement(e.getKey().getCountryName());
	    	}
	    
	     

	    addButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        countryDisplay.addElement("Country " + counter);
	        counter++;
	      }
	    });
	    
	    removeButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        if (countryDisplay.getSize() > 0)
	          countryDisplay.removeElementAt(0);
	      }
	    });
	    JFrame frame1 = new JFrame();
	    frame1.setTitle("Add or Remove Country");
		frame1.setVisible(true);
		frame1.setSize(450,300);
		frame1.setLocationRelativeTo(null);
	    frame1.add(pane, BorderLayout.NORTH);
	    frame1.add(addButton, BorderLayout.WEST);
	    frame1.add(removeButton, BorderLayout.CENTER);
	    frame1.add(saveButton, BorderLayout.EAST);
	}
	
	//Function to Add or Remove Territories from a Map
	
	private void territory() {
		territoryDisplay = new DefaultListModel();
	    territoryList = new JList(territoryDisplay);
	    JScrollPane pane = new JScrollPane(territoryList);
	    JButton addButton = new JButton("Add Territory");
	    JButton removeButton = new JButton("Remove Territory");
	    JButton saveButton = new JButton("Save Changes");
	    for (int i = 1; i <= 15; i++)
	      territoryDisplay.addElement("Territory " + i);

	    addButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        territoryDisplay.addElement("Territory " + counter);
	        counter++;
	      }
	    });
	    
	    removeButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        if (territoryDisplay.getSize() > 0)
	          territoryDisplay.removeElementAt(0);
	      }
	    });
	    JFrame frame1 = new JFrame();
	    frame1.setTitle("Add or Remove Territory");
		frame1.setVisible(true);
		frame1.setSize(450,300);
		frame1.setLocationRelativeTo(null);
	    frame1.add(pane, BorderLayout.NORTH);
	    frame1.add(addButton, BorderLayout.WEST);
	    frame1.add(removeButton, BorderLayout.CENTER);
	    frame1.add(saveButton, BorderLayout.EAST);
	}

	//Function to initalize the components used in MapView
	
	private void initMapComponents() {
		label.setText("Please Select a Correct File!");
		label1.setText("Click on the Button to Add/Remove Continent/Country/Territory from Map  ");
		label1.setVisible(true);
		btn_continent.setText("Continent");
		btn_continent.setName("button1");
		btn_continent.setVisible(true);
		btn_country.setText("Country");
		btn_country.setName("button2");
		btn_country.setVisible(true);
		btn_territory.setText("Territory");
		btn_territory.setName("button3");
		btn_territory.setVisible(true);
	
		filechooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		filechooser.setDialogTitle("Select a .map file");

		int result = filechooser.showOpenDialog(filechooser);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = filechooser.getSelectedFile();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			MapModel mapmodel = new MapModel();
			gameMap = mapmodel.readMapFile(selectedFile.getAbsolutePath()); 
			
			if (gameMap.isCorrectMap == true) {
			
			btn_continent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					button1ActionPerformed(evt);
				}
				private void button1ActionPerformed(ActionEvent evt) {
					continent();
				}
			});
			btn_country.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					button2ActionPerformed(evt);
				}
				private void button2ActionPerformed(ActionEvent evt) {
					country();	
				}
			});
			
			btn_territory.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					button3ActionPerformed(evt);
				}
				private void button3ActionPerformed(ActionEvent evt) {
					territory();
				}
			});
			frame.setTitle("Edit Map");
			frame.setVisible(true);
			frame.setSize(450,100);
			frame.setLocationRelativeTo(null);
			frame.add(label1,BorderLayout.PAGE_START);
			frame.add(btn_continent, BorderLayout.WEST);
			frame.add(btn_country, BorderLayout.CENTER);
			frame.add(btn_territory, BorderLayout.EAST);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
			else if (gameMap.isCorrectMap == false) {
				dialog.setVisible(true);
				dialog.setSize(275, 100);
				label.setVisible(true);
				label.setSize(275, 100);
				dialog.setTitle("ERROR");
				dialog.add(label);
			} 
		}
	}
}