package riskView.map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import riskModels.country.Country;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import util.CreateMap;

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
	public static javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;

	public LaunchGame() {
		JFrame frame = new JFrame();
		jButton1.setEnabled(true);
		jButton2.setEnabled(true);
		jButton3.setEnabled(true);
		frame.setLayout(new BorderLayout());
		frame.setSize(400, 100);
		frame.setVisible(true);
		label.setVisible(true);
		textField.setVisible(true);
		button.setVisible(true);
		ButtonGroup group = new ButtonGroup();
		group.add(option1);
		group.add(option2);
		group.add(option3);

		frame.add(label, BorderLayout.PAGE_START);
		frame.add(option1, BorderLayout.LINE_START);
		frame.add(option2, BorderLayout.CENTER);
		frame.add(option3, BorderLayout.LINE_END);
		frame.add(button, BorderLayout.PAGE_END);
	
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				boolean isoptionSelected;
				if (isoptionSelected = option1.isSelected()) {
					System.out.println("No. of players is 2");
				} else if (isoptionSelected = option2.isSelected()) {
					System.out.println("No. of players is 3");
				} else if (isoptionSelected = option2.isSelected()) {
					System.out.println("No. of players is 4");
				} else {
					System.out.println("Select One Player Atleast");
				}
				filechooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				filechooser.setDialogTitle("Select a .map file");

				int result = filechooser.showOpenDialog(filechooser);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = filechooser.getSelectedFile();
					System.out.println("Selected file: " + selectedFile.getAbsolutePath());
					MapModel mapmodel = new MapModel();
					GameMap gameMap = mapmodel.readMapFile(selectedFile.getAbsolutePath());
					if (gameMap.isCorrectMap == false) {
						dialog.setVisible(true);
						dialog.setSize(275, 100);
						label1.setVisible(true);
						label1.setSize(275, 100);
						dialog.setTitle("ERROR");
						dialog.add(label1);
					} else if (gameMap.isCorrectMap == true) {
						String absolute = selectedFile.getParent() + "\\" + gameMap.getMapDetail().get("image");
						JFrame f = new JFrame();
						BufferedImage image = null;
						try {
							image = ImageIO.read(new File(absolute));
						} catch (Exception ex) {
							ex.printStackTrace();
							System.exit(1);
						}
						ImageIcon icon = new ImageIcon(image);
						JLabel jIcon = new JLabel(icon){
							@Override
							protected void paintComponent(Graphics g) {
						        super.paintComponent(g);
						        HashMap<Country,List<Country>> map =  gameMap.getCountryAndNeighborsMap();
						        Set<Country> countryList = map.keySet();
						        for(Entry<Country, List<Country>> entry :map.entrySet()){
						        	for(Country c:entry.getValue()){
						        		for(Country t:countryList){
						        			if(c.getCountryName().equals(t.getCountryName())){
						        				g.setColor(Color.RED);
						        				g.drawLine(t.getStartPixel()-10, t.getEndPixel()-40, entry.getKey().getStartPixel()-10, entry.getKey().getEndPixel()-40);
						        				
						        			}
						        		}
						        	}
						        }
						    }
						};
						f.add(jIcon);
						f.setSize(icon.getIconWidth(), icon.getIconHeight());
						f.setVisible(true);
						JLabel[] l = new JLabel[gameMap.getCountryAndNeighborsMap().keySet().size()];
						int i = 0;
						for(Country c:gameMap.getCountryAndNeighborsMap().keySet()){
							l[i] = new JLabel(c.getCountryName());
							l[i].setBounds(c.getStartPixel()-15, c.getEndPixel()-90, 100, 100);
							l[i].setVisible(true);
							jIcon.add(l[i]);
							l[i].setToolTipText("Solider:");
							i++;
						}
	/*					
						
						jButton1 = new javax.swing.JButton();
				        jButton3 = new javax.swing.JButton();
				        jButton2 = new javax.swing.JButton();
				        
				        jButton1.setText("Dice");
				        jButton1.setName("jButton1");
				        jButton1.addActionListener(new java.awt.event.ActionListener() {
				            public void actionPerformed(java.awt.event.ActionEvent evt) {
				                jButton1ActionPerformed(evt);
				            }
				        });

				        jButton2.setText("ATTACK");
				        jButton2.setName("jButton2");
				        jButton2.addActionListener(new java.awt.event.ActionListener() {
				            public void actionPerformed(java.awt.event.ActionEvent evt) {
				                   jButton2ActionPerformed(evt);
				            }
				        });

				        jButton3.setText("REINFORCEMENT");
				        jButton3.setName("jButton3");
				        jButton3.addActionListener(new java.awt.event.ActionListener() {
				            public void actionPerformed(java.awt.event.ActionEvent evt) {
				                jButton3ActionPerformed(evt);
				            }
				        });

				        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
				            LaunchGame add = new LaunchGame();
				            add.setVisible(true);
				            }

				        	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) 
				            {
				        	   CreateMap add = new CreateMap();
				        	   add.setVisible(true);
				            }
				            private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
				               MapView add = new MapView();
				               add.setVisible(true);
				            }
*/
						textField.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent event) {
								String text = textField.getText();
								System.out.println(text);

								// open a map
							}
						});


					}
				}
			}
		});
	}
	}