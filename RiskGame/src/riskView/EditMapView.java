package riskView;

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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * EditMapView class holds the property of editing map.
 *
 * @author mudraparikh
 */
@SuppressWarnings("serial")
public class EditMapView extends java.awt.Frame {

	
	private JLabel label = new JLabel();
	private JLabel label1 = new JLabel();
	private JLabel label2 = new JLabel();
	private JLabel labelContinent = new JLabel();
	private JLabel labelCountry = new JLabel();
	
	private JButton btn_continent = new JButton();
	private JButton btn_country = new JButton();
	private JButton button1 = new JButton();
	private JButton button2 = new JButton();
	//private JButton button3 = new JButton();
	private JButton addButton = new JButton();
	private JButton removeButton = new JButton();
	
	private JList<String> continentList, continentList1, countryList, countryList2;
	
	private DefaultListModel<String> continentDisplay = new DefaultListModel<>();
	private DefaultListModel<String> continentDisplay1 = new DefaultListModel<>();
	private DefaultListModel<String> countryDisplay = new DefaultListModel<>();
	
	private JFrame frame = new JFrame();
	private JFrame frameContinent = new JFrame();
	private JFrame frameCountry = new JFrame();
	
	
	private JScrollPane pane = new JScrollPane();
	private JScrollPane pane2 = new JScrollPane();
	private JScrollPane pane3 = new JScrollPane();
	
	private JTextField textContinent = new JTextField();
	private JTextField textCountry = new JTextField();
	
	private JFileChooser filechooser = new JFileChooser();
	private JDialog dialog = new JDialog();
	
	String fileName;
	private int index;
	Object index1;
	GameMap gameMap = GameMap.getInstance();
	MapModel mapModel = new MapModel();

	public EditMapView() {
		initMapComponents();
	}
	/**
     * This continent method is used to edit map by "Add" or "Remove" functionality Continents from a Map
     */
	private void continent() {
		
		continentList = new JList(continentDisplay);
		pane.add(continentList);
		addButton.setText("Add Continent");
		removeButton.setText("Remove Continent");
		labelContinent.setText("Enter Continent name :");
		button1.setText("OK");

		for (int i = 0; i < gameMap.getContinentList().size(); i++)
			continentDisplay.addElement(gameMap.getContinentList().get(i).getContinentName());
		
		//This button performs the add continent operation on the map to be edited.
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameContinent.setTitle("Add Continent");
				frameContinent.setVisible(true);
				frameContinent.setSize(250, 150);
				frameContinent.setLocationRelativeTo(null);
				frameContinent.add(labelContinent, BorderLayout.PAGE_START);
				frameContinent.add(textContinent, BorderLayout.CENTER);
				frameContinent.add(button1, BorderLayout.PAGE_END);
				
		//Changes made on the GUI to edit map will be saved to the .map file	
				button1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frameContinent.setTitle("Add Continent");
						frameContinent.setVisible(true);
						frameContinent.setSize(250, 150);
						frameContinent.setLocationRelativeTo(null);
						frameContinent.add(labelContinent, BorderLayout.PAGE_START);
						frameContinent.add(textContinent, BorderLayout.CENTER);
						frameContinent.add(button1, BorderLayout.PAGE_END);
						continentDisplay.addElement(textContinent.getText());
						pane.repaint();
					}
				});
			}
		});
		
		//This button performs the remove continent operation on the map to be edited.
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (continentList.getSelectedIndex() > 0) {
					List<String> obj = continentList.getSelectedValuesList();
					for (String string : obj) {
						continentDisplay.removeElement(string);
						//if(mapModel.readContinents(string)!= null)
						//	mapModel.removeContinent(string);
					}
				}
				continentList.repaint();
				mapModel.writeMap(gameMap, fileName);
			}
		});

		JFrame frame1 = new JFrame();
		frame1.setTitle("Add or Remove Continent");
		frame1.setVisible(true);
		frame1.setSize(450, 300);
		frame1.setLocationRelativeTo(null);
		frame1.add(pane, BorderLayout.NORTH);
		frame1.add(addButton, BorderLayout.WEST);
		frame1.add(removeButton, BorderLayout.CENTER);
	}

	/**
     * This country method is used to edit map by "Add" or "Remove" functionality country from a Map
     */

	private void country() {
		countryList = new JList(countryDisplay);
		countryList2 = new JList(countryDisplay);
		pane.add(countryList);
		pane3.add(countryList2);

		for(int i=0; i<continentDisplay.size(); i++)
			continentDisplay1.addElement(continentDisplay.get(i));
				
		continentList1 = new JList(continentDisplay1);
		pane2.add(continentList1);
		label2.setText("Click on the respective button to perform tasks");
		addButton.setText("Add Country");
		removeButton.setText("Remove Country");
		labelCountry.setText("Enter Country name :");
		button2.setText("OK");
		
		/**
	     * The following loops display the list of continents and the countries
	     * by accessing the values from the gameMap object of the gameMap class
	     */
		for (int i = 0; i < gameMap.getContinentList().size(); i++)
			continentDisplay1.addElement(gameMap.getContinentList().get(i).getContinentName());

		for (Map.Entry<Country, List<Country>> e : gameMap.getCountryAndNeighborsMap().entrySet()) {
			countryDisplay.addElement(e.getKey().getCountryName());
			
			//This button performs the add country operation on the map to be edited.
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frameCountry.setTitle("Add Country");
					frameCountry.setVisible(true);
					frameCountry.setSize(550, 250);
					frameCountry.setLocationRelativeTo(null);
					frameCountry.add(labelCountry, BorderLayout.PAGE_START);
					frameCountry.add(pane2, BorderLayout.LINE_START);
					frameCountry.add(textCountry, BorderLayout.CENTER);
					frameCountry.add(pane3, BorderLayout.EAST);
					frameCountry.add(button2, BorderLayout.PAGE_END);
				}
			});
		}
		
		//Changes made on the GUI to edit map will be saved to the .map file
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				countryDisplay.addElement(textCountry.getText());
				List<String> continents = continentList1.getSelectedValuesList();
				if (continents.size() > 0) {
					Country country = new Country(textCountry.getText(), 0, 0, continents.get(0));
					List<Country> countries = new ArrayList<Country>();
					if (countryList2.getSelectedValuesList().size() > 0) {
						List<String> countryNames = countryList2.getSelectedValuesList();
						for (Country c : gameMap.getCountryAndNeighborsMap().keySet()) {
							for (String countryName : countryNames) {
								if (countryName.equals(c.getCountryName())) {
									countries.add(c);
									break;
								}
							}
						}
					}
					mapModel.addCountry(country, gameMap, countries);
					mapModel.writeMap(gameMap, fileName);
				}
			}
		});
		//This button performs the remove country operation on the map to be edited.
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (countryList.getSelectedIndex() >= 0) {
					List<String> obj = countryList.getSelectedValuesList();
					for (String string : obj) {
						countryDisplay.removeElement(string);
						if(mapModel.getCountryObj(string, gameMap) !=null){
						    mapModel.removeCountry(mapModel.getCountryObj(string, gameMap), gameMap);
                        };
					}
				}
				countryList.repaint();
				mapModel.writeMap(gameMap, fileName);
			}
		});
		JFrame frame1 = new JFrame();
		frame1.setTitle("Add or Remove Country");
		frame1.setVisible(true);
		frame1.setSize(450, 300);
		frame1.setLocationRelativeTo(null);
		frame1.add(pane, BorderLayout.PAGE_START);
		frame1.add(addButton, BorderLayout.CENTER);
		frame1.add(removeButton, BorderLayout.PAGE_END);

	}
	
	/**
     * This initMapComponents is used to initialize the components usedin EditMapView class
     */
	private void initMapComponents() {
		label1.setText("Click on the Button to Add/Remove Continent/Country/Territory from Map  ");
		label1.setVisible(true);
		btn_continent.setText("Continent");
		btn_continent.setName("button1");
		btn_continent.setVisible(true);
		btn_country.setText("Country");
		btn_country.setName("button2");
		btn_country.setVisible(true);
		
		//Displays the dialog to select a file from the user's machine.
		filechooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		filechooser.setDialogTitle("Select a .map file");

		int result = filechooser.showOpenDialog(filechooser);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = filechooser.getSelectedFile();
			fileName=selectedFile.getName();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			MapModel mapmodel = new MapModel();
			gameMap = mapmodel.readMapFile(selectedFile.getAbsolutePath());
			
			//Condition to check if the correct file is selected.
			if (gameMap.isCorrectMap) {
				
				//calls the continent method on click of button continent.
				btn_continent.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						continent();
					}
				});
				
				//calls the continent method on click of button continent.
				btn_country.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						country();
					}
				});

				frame.setTitle("Edit Map");
				frame.setVisible(true);
				frame.setSize(450, 100);
				frame.setLocationRelativeTo(null);
				frame.add(label1, BorderLayout.PAGE_START);
				frame.add(btn_continent, BorderLayout.WEST);
				frame.add(btn_country, BorderLayout.EAST);
				
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
			} else {
				dialog.setVisible(true);
				dialog.setSize(400, 100);
				label.setVisible(true);
				label.setSize(400, 100);
				label.setText(gameMap.getErrorMessage());
				dialog.setTitle("ERROR"); // error message when wrong file is selected
				dialog.add(label);
			}
		}
	}
}