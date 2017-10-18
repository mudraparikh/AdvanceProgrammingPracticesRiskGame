package riskView.map;

import riskModels.country.Country;
import riskModels.gamedriver.StartupPhase;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

@SuppressWarnings("serial")
public class LaunchGame extends JPanel {
    private JLabel label = new JLabel("Select number of Players :");
    private JLabel label1 = new JLabel("Please Select a Correct File");
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

    public List<Player> playerList;

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

        frame.add(label, BorderLayout.PAGE_START);
        frame.add(option1, BorderLayout.LINE_START);
        frame.add(option2, BorderLayout.CENTER);
        frame.add(option3, BorderLayout.LINE_END);
        frame.add(button, BorderLayout.PAGE_END);

        player1.setVisible(true);
        player1.setOpaque(true);
        player1.setBackground(Color.RED);
        player2.setVisible(true);
        player2.setOpaque(true);
        player2.setBackground(Color.MAGENTA);
        player3.setVisible(true);
        player3.setOpaque(true);
        player3.setBackground(Color.BLUE);
        player4.setVisible(true);
        player4.setOpaque(true);
        player4.setBackground(Color.GREEN);
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
                    for (Player p : playerList){
                        System.out.println(p.getName());
                        System.out.println(p.getColors());
                    }
                    startupPhase.initialisePlayersData(playerList, gameMap, numberOfPlayers);
                    if (!gameMap.isCorrectMap) {
                        dialog.setVisible(true);
                        dialog.setSize(275, 100);
                        label1.setVisible(true);
                        label1.setSize(275, 100);
                        dialog.setTitle("ERROR");
                        dialog.add(label1);
                    } else {
                        String absolute = selectedFile.getParent() + "/" + gameMap.getMapDetail().get("image");
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
                            f.add(player1, BorderLayout.PAGE_START);
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
                        JLabel[] l = new JLabel[gameMap.getCountryAndNeighborsMap().keySet().size()];
                        int i = 0;
                        for (Country c : gameMap.getCountryAndNeighborsMap().keySet()) {
                            l[i] = new JLabel("" + c.getCountryName() + ":" + c.getCurrentArmiesDeployed());
                            l[i].setBounds(c.getStartPixel() - 15, c.getEndPixel() - 90, 100, 100);
                            l[i].setVisible(true);
                            jIcon.add(l[i]);
                            l[i].setForeground(c.getBelongsToPlayer().getColors());
                            /*l[i].addMouseListener(new MouseAdapter() {
                                public void mouseClicked(MouseEvent e) {
                                    System.out.println(c.countryName + " was clicked !");
                                }
                            });*/
                            i++;
                        }
                        textField.addActionListener(event -> {
                            String text = textField.getText();
                            System.out.println(text);
                        });
                    }
                }
            }
        });
    }
}