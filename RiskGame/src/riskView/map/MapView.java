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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
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
	private javax.swing.JButton jButton1;
	private JFileChooser filechooser = new JFileChooser();
	private JDialog dialog = new JDialog();
	private JLabel label = new JLabel();
	private JFrame frame = new JFrame();
	
	public MapView(){
		initMapComponents();
	}
	
	private void initMapComponents() {
		label.setText("Please Select a Correct File!");
		frame.setLocationRelativeTo(null);
		frame.setTitle("Edit Map");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		filechooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		filechooser.setDialogTitle("Select a .map file");

		int result = filechooser.showOpenDialog(filechooser);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = filechooser.getSelectedFile();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			MapModel mapmodel = new MapModel();
			GameMap gameMap = mapmodel.readMapFile(selectedFile.getAbsolutePath()); 
			if (gameMap.isCorrectMap == true) {
			frame.setVisible(true);
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