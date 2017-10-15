package riskView.map;

import javax.swing.*;

import riskModels.map.GameMap;
import riskModels.map.MapModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


@SuppressWarnings("serial")
public class LaunchGame extends JPanel {
    private JLabel label = new JLabel("Select number of Players :");
    private JLabel label1 = new JLabel("Please Select a Correct File");
    private JTextField textField = new JTextField(20);
    private JButton button = new JButton("OK");
    private JFileChooser filechooser = new JFileChooser();
    private JDialog dialog = new JDialog();
    private JRadioButton option1 = new JRadioButton("2");
    private JRadioButton option2 = new JRadioButton("3");
    private JRadioButton option3 = new JRadioButton("4");
    private int players;
    public LaunchGame() {
    	JFrame frame = new JFrame();
    	frame.setLayout(new BorderLayout());
    	frame.setSize(400,100);
    	frame.setVisible(true);
    	label.setVisible(true);
        textField.setVisible(true);
        button.setVisible(true);
        ButtonGroup group = new ButtonGroup();
        group.add(option1);
        group.add(option2);
        group.add(option3);
        
        frame.add(label,BorderLayout.PAGE_START);
        frame.add(option1,BorderLayout.LINE_START);
        frame.add(option2,BorderLayout.CENTER);
        frame.add(option3,BorderLayout.LINE_END);
        frame.add(button,BorderLayout.PAGE_END);
             
        button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boolean isoptionSelected;
		        if(isoptionSelected = option1.isSelected()){
		        	players=2;
		        	System.out.println("No. of players is 2");
		        }
		        else if(isoptionSelected = option2.isSelected()){
		        	players=3;
		        	System.out.println("No. of players is 3");
		        }
		        else if(isoptionSelected = option3.isSelected()){
		        	players=4;
		        	System.out.println("No. of players is 4");
		        }
		        else {
		        	System.out.println("Select One Player Atleast");
		        }
		filechooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        filechooser.setDialogTitle("Select a .map file");
        
        int result = filechooser.showOpenDialog(filechooser);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = filechooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            MapModel mapmodel = new MapModel();
            GameMap gameMap =mapmodel.readMapFile(selectedFile.getAbsolutePath());
            if(gameMap.isCorrectMap == false)
            {
            	dialog.setVisible(true);
            	dialog.setSize(275, 100);
            	label1.setVisible(true);
            	label1.setSize(275,100);
            	dialog.setTitle("ERROR");
            	dialog.add(label1);
            }
            else if(gameMap.isCorrectMap == true)
            {
            	
            textField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                	String text = textField.getText();
                    System.out.println(text);
                    openMap(gameMap,players);                    {}
                }

				private void openMap(GameMap gameMap, int players) {
					//call the function that loads MAP
				}
            });
            }
        }	  }
  	  });
}
}
	 