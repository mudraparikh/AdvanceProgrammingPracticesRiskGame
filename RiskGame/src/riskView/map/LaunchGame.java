package riskView.map;

import riskModels.country.Country;
import riskModels.gamedriver.GamePlayAPI;
import riskModels.gamedriver.StartupPhase;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
/**
 * This class will get map details from model and display the map with number of players
 * @author hnath
 * @author mudraparikh
 *
 */
@SuppressWarnings("serial")
public class LaunchGame extends JPanel {
    GamePlayAPI game;
    private JLabel label = new JLabel("Select number of Players :");
    private JLabel label1 = new JLabel(); 
    private JTextField textField = new JTextField(20);
    private JButton button = new JButton("OK");
    private JFileChooser fileChooser = new JFileChooser();
    private JDialog dialog = new JDialog();
    private JRadioButton option1 = new JRadioButton("2");
    private JRadioButton option2 = new JRadioButton("3");
    private JRadioButton option3 = new JRadioButton("4");

    private JLabel player1 = new JLabel("Player 1");
    private JLabel player2 = new JLabel("Player 2");
    private JLabel player3 = new JLabel("Player 3");
    private JLabel player4 = new JLabel("Player 4");

    private static String currentPhaseState = "RP";

    public List<Player> playerList;

    private int turn = 1;
    private int noOfPlayers = 4;

    /**
     * This LaunchGame method allows you to select no. of players and select the .map file from your local folder.
     */
    public LaunchGame() {
        JFrame frame = new JFrame();
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

        JButton reinforcement = new JButton("reinforcement");
        reinforcement.setSize(10,10);
        reinforcement.setBounds(100,100,50,25);
        reinforcement.setVisible(true);


        frame.add(label, BorderLayout.PAGE_START);
        frame.add(option1, BorderLayout.LINE_START);
        frame.add(option2, BorderLayout.CENTER);
        frame.add(option3, BorderLayout.LINE_END);
        frame.add(button, BorderLayout.PAGE_END);

        player1.setVisible(true);
        player1.setOpaque(true);
        player1.setBackground(Color.RED); // assigning colors to the players
        player2.setVisible(true);
        player2.setOpaque(true);
        player2.setBackground(Color.MAGENTA);
        player3.setVisible(true);
        player3.setOpaque(true);
        player3.setBackground(Color.BLUE);
        player4.setVisible(true);
        player4.setOpaque(true);
        player4.setBackground(Color.GREEN);
        
        //On click the button determines and storesthe value for the Number of Players.
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean isOptionSelected;
                int numberOfPlayers = 0;
                if (isOptionSelected = option1.isSelected()) {
                    System.out.println("No. of players is 2");
                    numberOfPlayers = 2;
                } else if (isOptionSelected = option2.isSelected()) {
                    System.out.println("No. of players is 3");
                    numberOfPlayers = 3;
                } else if (isOptionSelected = option3.isSelected()) {
                    System.out.println("No. of players is 4");
                    numberOfPlayers = 4;
                } else {
                    System.out.println("Select One Player At-least"); 
                }
                
                //Displays the dialog to select a file from the user's machine.
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                fileChooser.setDialogTitle("Select a .map file");

                int result = fileChooser.showOpenDialog(fileChooser);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    MapModel mapmodel = new MapModel();
                    GameMap gameMap = mapmodel.readMapFile(selectedFile.getAbsolutePath());
                    StartupPhase startupPhase = new StartupPhase();
                    playerList = startupPhase.setPlayer(numberOfPlayers);
                    /**
                     * This checks whether the map is correct then it loads the map from directory
                     * 
                     * @param playerList It holds list of players
                     * @param gameMap this holds the location of .map file in the directory
                     * @param numberOfPlayer this holds the value of no. of players selected
                     */

                    startupPhase.initialisePlayersData(playerList, gameMap, numberOfPlayers);
                    if (!gameMap.isCorrectMap) {
                    	label1.setText(gameMap.getErrorMessage());
                        label1.setVisible(true);
                        label1.setSize(400, 100);
                        dialog.setVisible(true);
                        dialog.setSize(400, 100);
                        dialog.setTitle("ERROR"); // error message when wrong file is selected
                        dialog.add(label1);
                    } else {
                    	startupPhase.initialisePlayersData(playerList, gameMap, numberOfPlayers);
                        String absolute = selectedFile.getParent() + "\\" + gameMap.getMapDetail().get("image");
                        System.out.println(absolute);
                        JFrame f = new JFrame();
                        BufferedImage image = null;
                        try {
                            image = ImageIO.read(new File(absolute));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            System.exit(1);
                        }
                        ImageIcon icon = new ImageIcon(image);
                        JLabel jIcon = new JLabel(icon) {
                            
                        	/**
                             * This paintComponent method helps to draw connects between the neighboring countries in the map
                             */
                        	
                        	@Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                HashMap<Country, List<Country>> map = gameMap.getCountryAndNeighborsMap();
                                Set<Country> countryList = map.keySet();
                                for (Entry<Country, List<Country>> entry : map.entrySet()) {
                                    for (Country c : entry.getValue()) {
                                        for (Country t : countryList) {
                                            if (c.getCountryName().equals(t.getCountryName())) {
                                                g.setColor(Color.darkGray);
                                                g.drawLine(t.getStartPixel() - 10, t.getEndPixel() - 40, entry.getKey().getStartPixel() - 10, entry.getKey().getEndPixel() - 40);
                                            }
                                        }
                                    }
                                }
                            }
                        };
                        jIcon.setMaximumSize(getMaximumSize());
                        f.setSize(icon.getIconWidth(), icon.getIconHeight());
                        f.setVisible(true);
                        if (numberOfPlayers == 2) {
                            f.add(player1, BorderLayout.PAGE_START); // alignment of players on the screen
                            f.add(player2, BorderLayout.PAGE_END);
                            f.add(jIcon, BorderLayout.CENTER);
                        } else if (numberOfPlayers == 3) {
                            f.add(player1, BorderLayout.PAGE_START);
                            f.add(player2, BorderLayout.BEFORE_LINE_BEGINS);
                            f.add(jIcon, BorderLayout.CENTER);
                            f.add(player3, BorderLayout.PAGE_END);
                        } else if (numberOfPlayers == 4) {
                            f.add(player1, BorderLayout.PAGE_START);
                            f.add(player2, BorderLayout.LINE_START);
                            f.add(jIcon, BorderLayout.CENTER);
                            f.add(player3, BorderLayout.LINE_END);
                            f.add(player4, BorderLayout.PAGE_END);
                        }
                        currentPhaseState = "SP";
                        JLabel[] l = new JLabel[gameMap.getCountryAndNeighborsMap().keySet().size()];
                        int i = 0;
                        for (Country c : gameMap.getCountryAndNeighborsMap().keySet()) {
                            l[i] = new JLabel("" + c.getCountryName() + ":" + c.getCurrentArmiesDeployed());
                            l[i].setBounds(c.getStartPixel() - 15, c.getEndPixel() - 90, 100, 100);
                            l[i].setVisible(true);
                            jIcon.add(l[i]);
                            l[i].setForeground(c.getBelongsToPlayer().getColors());
                            l[i].addMouseListener(new MouseAdapter() {
                                public void mouseClicked(MouseEvent e) {
                                    System.out.println(c.countryName + " was clicked !");
                                    if(turn>noOfPlayers) {
                                        currentPhaseState = "FORTIFICATION_PHASE";
                                        // go to next phase
                                        return;
                                    }
                                    if(currentPhaseState.equalsIgnoreCase("RP")) {
                                        if(c.getBelongsToPlayer().equals(playerList.get(turn-1))) {
                                            JLabel l = (JLabel) e.getSource();
                                            c.addArmy(7);
                                            l.setText("" + c.getCountryName() + ":" + c.getCurrentArmiesDeployed());
                                            turn++;
                                        }
                                        else
                                        {
                                            System.out.println("\n\n\t\t"
                                                    + "Its Player("+turn+") turn..."
                                                    + "\n\n");
                                        }
                                    }
                                }
                            });
                            i++;
                        }
                        currentPhaseState = "RP";
                        //startReinforcementPhase(playerList, gameMap, numberOfPlayers, l);
                        textField.addActionListener(event -> {
                            String text = textField.getText();
                            System.out.println(text);
                        });
                    }
                }
            }
        });
    }

    private void startReinforcementPhase(List<Player> playerList, GameMap gameMap, int numberOfPlayers, JLabel[] labels) {
        game = new GamePlayAPI();
        currentPhaseState = "RP";
        Player player = game.changeTurnToNextPlayer(numberOfPlayers, playerList);
        for (JLabel label:labels){
            if (player.assignedCountries.contains(label.getText()))
            System.out.println(label.getText());
        }

    }
}