package riskView.map;

import riskModels.map.GameMap;
import riskModels.map.MapModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


@SuppressWarnings("serial")
public class LaunchGame extends JPanel {
    private JLabel label = new JLabel("Enter No of Players (2-4): ");
    private JLabel label1 = new JLabel("Please Select a Correct File");
    private JLabel label2 = new JLabel("Correct File");
    private JTextField textField = new JTextField(20);
    private JButton button = new JButton("OK");
    private JFileChooser filechooser = new JFileChooser();
    private JDialog dialog = new JDialog();

    public LaunchGame() {
    	JFrame frame = new JFrame();
    	frame.setLayout(new BorderLayout());
    	frame.setSize(400,100);
    	frame.setVisible(true);
    	label.setVisible(true);
    	label1.setVisible(true);
    	label2.setVisible(true);
        textField.setVisible(true);
        button.setVisible(true);
        frame.add(label,BorderLayout.LINE_START);
        frame.add(textField, BorderLayout.LINE_END);
        frame.add(button, BorderLayout.PAGE_END);
        button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {		
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
            	dialog.setTitle("ERROR");
            	dialog.add(label1);
            	
            }
            else if(gameMap.isCorrectMap == true)
            {
            	
            textField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    dialog.setVisible(true);
                    dialog.add(label2);
                	String text = textField.getText();
                    System.out.println(text);
                }
            });
            }
        }	  }
  	  });
    }
}

	 