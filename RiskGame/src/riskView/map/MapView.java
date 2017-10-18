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
 * @author hanita
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
	private DefaultListModel continentModel,countryModel,territoryModel;
	private int counter=15;
	
	public MapView(){
		initMapComponents();
	}
	
	//Function to Add or Remove Continents from a Map
	
	private void continent() {
	    continentModel = new DefaultListModel();
	    continentList = new JList(continentModel);
	    JScrollPane pane = new JScrollPane(continentList);
	    JButton addButton = new JButton("Add Continent");
	    JButton removeButton = new JButton("Remove Continent");
	    JButton saveButton = new JButton("Save Changes");
	    for (int i = 1; i <= 15; i++)
	      continentModel.addElement("Continent " + i);

	    addButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        continentModel.addElement("Continent " + counter);
	        counter++;
	      }
	    });
	    
	    removeButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        if (continentModel.getSize() > 0)
	          continentModel.removeElementAt(0);
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
		countryModel = new DefaultListModel();
	    countryList = new JList(countryModel);
	    JScrollPane pane = new JScrollPane(countryList);
	    JButton addButton = new JButton("Add Country");
	    JButton removeButton = new JButton("Remove Country");
	    JButton saveButton = new JButton("Save Changes");
	    for (int i = 1; i <= 15; i++)
	      countryModel.addElement("Country " + i);

	    addButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        countryModel.addElement("Country " + counter);
	        counter++;
	      }
	    });
	    
	    removeButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        if (countryModel.getSize() > 0)
	          countryModel.removeElementAt(0);
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
		territoryModel = new DefaultListModel();
	    territoryList = new JList(territoryModel);
	    JScrollPane pane = new JScrollPane(territoryList);
	    JButton addButton = new JButton("Add Territory");
	    JButton removeButton = new JButton("Remove Territory");
	    JButton saveButton = new JButton("Save Changes");
	    for (int i = 1; i <= 15; i++)
	      territoryModel.addElement("Territory " + i);

	    addButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        territoryModel.addElement("Territory " + counter);
	        counter++;
	      }
	    });
	    
	    removeButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        if (territoryModel.getSize() > 0)
	          territoryModel.removeElementAt(0);
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
			GameMap gameMap = mapmodel.readMapFile(selectedFile.getAbsolutePath()); 
			
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