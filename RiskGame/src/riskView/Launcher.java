package riskView;

import riskControllers.PlayerCountController;

/**
 * This class holds the method that gives the menu option to start game,create map and edit map.
 *
 * @author mudraparikh
 */
public class Launcher extends java.awt.Frame {
    
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    /**
     * This method calls the initMenuComponents method to initialize Launcher components.
     */
    public Launcher() {
        super("RISK Game");
        initMenuComponents();
        jButton1.setEnabled(true);
        setLocationRelativeTo(null);
    }
    /**
     * This main method creates an instance of the startGame.
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Launcher().setVisible(true);
            }
        });
    }

    private void initMenuComponents() {
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(1, 1, 1));
        addWindowListener(new java.awt.event.WindowAdapter() {
        	
        	//Method to terminate and close the program window.
            public void windowClosing(java.awt.event.WindowEvent evt) {
            	System.exit(0);
            }
        });

        jPanel1.setBackground(new java.awt.Color(1, 1, 1));
        jPanel1.setName("jPanel1");
        
        //On click the button creates an instance of the PlayerCount class.
        jButton1.setText("Start Game");
        jButton1.setName("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	PlayerCount add = new PlayerCount();
            	add.addActionListeners(new PlayerCountController(add));
                add.setVisible(true);
            }
        });
        
        //On click the button creates an instance of the CreateMap class.
        jButton2.setText("Create Map");
        jButton2.setName("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	util.CreateMap add = new util.CreateMap();
                add.main(null);
                System.exit(0);
            }
        });
        
       //On click the button creates an instance of the MapView class.
        jButton3.setText("Edit Map");
        jButton3.setName("jButton3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	MapView add = new MapView();
                add.setVisible(true);
            }
        });
        //On click the button terminates the game.
        jButton4.setText("Exit");
        jButton4.setName("jButton4");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	System.exit(0);
            }
        });


        jLabel1.setName("jLabel1");
        //set the layout of the panel with all the components added
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        //set the horizontal group for the panel layout
        jPanel1Layout.setHorizontalGroup
                (
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel1)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(91, 91, 91)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))
                                        .addGap(87, 87, 87))
                );
        //set the vertical group for the panel layout
        jPanel1Layout.setVerticalGroup
                (
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel1)
                                        .addGap(34, 34, 34)
                                        .addComponent(jButton1)
                                        .addGap(12, 12, 12)
                                        .addComponent(jButton2)
                                        .addGap(15, 15, 15)
                                        .addComponent(jButton3)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton4)
                                        .addGap(21, 21, 21))
                );
        add(jPanel1, java.awt.BorderLayout.CENTER);
        pack();
    }
}