package riskView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PlayerSettingsView extends JDialog {
    private JPanel playerNamesPanel;
    private JPanel playerTypesPanel;

    private GridLayout mainLayout;
    private GridLayout playerNamesLayout;
    private GridLayout playerTypesLayout;

    private JButton startBtn;
    private JButton backBtn;

    private String startBtnName = "startBtn";
    private String backBtnName = "backBtn";

    private JTextField player1TextField;
    private JTextField player2TextField;
    private JTextField player3TextField;
    private JTextField player4TextField;
    private JTextField player5TextField;
    private JTextField player6TextField;

    private JComboBox player1ComboBox;
    private JComboBox player2ComboBox;
    private JComboBox player3ComboBox;
    private JComboBox player4ComboBox;
    private JComboBox player5ComboBox;
    private JComboBox player6ComboBox;

    private int playerCount;

    private String[] types = { "Human", "Aggressive Bot", "Benevolent Bot", "Randomize Bot", "Cheater Bot" };

    public PlayerSettingsView(PlayerCount owner, boolean modality, int playerCount)
    {
        super(owner, modality);
        setTitle("Java-Risk");

        this.playerCount = playerCount;

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);

        mainLayout = new GridLayout(1, 2, 5, 5); //  Make second parameter '2' if including playerTypesPanel
        setLayout(mainLayout);

        add(playerNamesPanel());
        add(playerTypesPanel());

        setLocationRelativeTo(null);

        pack();
    }

    private JPanel playerNamesPanel() {

        playerNamesPanel = new JPanel();

        playerNamesPanel.setPreferredSize(new Dimension(200, playerCount * 40 + 40));

        playerNamesLayout = new GridLayout(playerCount + 1, 1, 5, 5);
        playerNamesPanel.setLayout(playerNamesLayout);

        player1TextField = new JTextField("Theodoric");
        player2TextField = new JTextField("Napoleon");
        player3TextField = new JTextField("Alexander");

        playerNamesPanel.add(player1TextField);
        playerNamesPanel.add(player2TextField);
        playerNamesPanel.add(player3TextField);

        if (playerCount > 3) {
            player4TextField = new JTextField("William");
            playerNamesPanel.add(player4TextField);
        }
        if (playerCount > 4) {
            player5TextField = new JTextField("Georgy");
            playerNamesPanel.add(player5TextField);
        }
        if (playerCount > 5) {
            player6TextField = new JTextField("Cyrus");
            playerNamesPanel.add(player6TextField);
        }

        backBtn = new JButton ("Back");
        backBtn.setActionCommand(backBtnName);
        playerNamesPanel.add(backBtn);

        return playerNamesPanel;
    }

    private JPanel playerTypesPanel() {

        playerTypesPanel = new JPanel();

        playerTypesPanel.setPreferredSize(new Dimension(200, playerCount * 40 + 40));

        playerTypesLayout = new GridLayout(playerCount + 1, 1, 5, 5);
        playerTypesPanel.setLayout(playerTypesLayout);

        player1ComboBox = new JComboBox(types);
        player2ComboBox = new JComboBox(types);
        player3ComboBox = new JComboBox(types);

        playerTypesPanel.add(player1ComboBox);
        playerTypesPanel.add(player2ComboBox);
        playerTypesPanel.add(player3ComboBox);

        if (playerCount > 3) {
            player4ComboBox = new JComboBox(types);
            playerTypesPanel.add(player4ComboBox);
        }
        if (playerCount > 4) {
            player5ComboBox = new JComboBox(types);
            playerTypesPanel.add(player5ComboBox);
        }
        if (playerCount > 5) {
            player6ComboBox = new JComboBox(types);
            playerTypesPanel.add(player6ComboBox);
        }

        startBtn = new JButton ("Start Game");

        startBtn.setActionCommand(startBtnName);

        playerTypesPanel.add(startBtn);

        return playerTypesPanel;
    }

    public void addActionListeners(ActionListener evt)
    {
        startBtn.addActionListener(evt);
        backBtn.addActionListener(evt);
    }

    // Get methods for text fields

    public String getPlayerTextField(int playerNum)
    {
        if (playerNum == 1)
        {
            return player1TextField.getText();
        }
        else if (playerNum == 2)
        {
            return player2TextField.getText();
        }
        else if (playerNum == 3)
        {
            return player3TextField.getText();
        }
        else if (playerNum == 4)
        {
            return player4TextField.getText();
        }
        else if (playerNum == 5)
        {
            return player5TextField.getText();
        }
        else
        {
            return player6TextField.getText();
        }
    }

    // Get methods for combo boxes

    public String getPlayerComboBox(int playerNum)
    {
        if (playerNum == 1)
        {
            return player1ComboBox.getSelectedItem().toString();
        }
        else if (playerNum == 2)
        {
            return player2ComboBox.getSelectedItem().toString();
        }
        else if (playerNum == 3)
        {
            return player3ComboBox.getSelectedItem().toString();
        }
        else if (playerNum == 4)
        {
            return player4ComboBox.getSelectedItem().toString();
        }
        else if (playerNum == 5)
        {
            return player5ComboBox.getSelectedItem().toString();
        }
        else
        {
            return player6ComboBox.getSelectedItem().toString();
        }
    }
}