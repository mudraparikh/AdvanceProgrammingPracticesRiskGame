package riskView.map;

import riskModels.country.Country;
import riskModels.gamedriver.StartupPhase;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.continent.*;

import javax.imageio.ImageIO;
import javax.swing.*;

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
	private JLabel label1 = new JLabel("Please Select a Correct File");
	BufferedImage image = null;
	ImageIcon icon = new ImageIcon(image);
	JLabel jIcon = new JLabel(icon);
	
	public MapView(){
		initMapComponents();
	}
	
	private void initMapComponents() {
		
		filechooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		filechooser.setDialogTitle("Select a .map file");

		int result = filechooser.showOpenDialog(filechooser);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = filechooser.getSelectedFile();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());
			MapModel mapmodel = new MapModel();
			StartupPhase startupPhase = new StartupPhase();
			GameMap gameMap = mapmodel.readMapFile(selectedFile.getAbsolutePath());
			startupPhase.assignCountriesToPlayer(numberOfPlayers,gameMap); 
			if (gameMap.isCorrectMap == false) {
				dialog.setVisible(true);
				dialog.setSize(275, 100);
				label1.setVisible(true);
				label1.setSize(275, 100);
				dialog.setTitle("ERROR");
				dialog.add(label1);
			} 
			else if (gameMap.isCorrectMap == true) {
				String absolute = selectedFile.getParent() + "\\" + gameMap.getMapDetail().get("image");
				JFrame f = new JFrame();
				BufferedImage image = null;
				try {
					image = ImageIO.read(new File(absolute));
				} 
				catch (Exception ex) {
					ex.printStackTrace();
					System.exit(1);
				}
			}
		}
	}
public static void main(String[] args) {
	java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new MapView().setVisible(true);
        }
    });
        MapModel mapmodel = new MapModel();
        GameMap gameMapDetails = mapmodel.readMapFile("C:\\CanadaMap.txt");
        for (Continent continent : gameMapDetails.getContinentList()) {
            System.out.println("Name" + continent.getContinentName() + "--" + continent.getNumberOfTerritories());
        }
        Iterator it = gameMapDetails.getCountryAndNeighborsMap().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Country country = (Country) pair.getKey();
            
            List<Country> neighbours = (List<Country>) pair.getValue();
            // System.out.println("------"+country.getCountryName()+"-----"+country.getBelongsToContinet());
            for (Country neighbour : neighbours) {
                //	System.out.println(neighbour.getCountryName()+" "+neighbour.getBelongsToContinet());
            }

        }
}
}