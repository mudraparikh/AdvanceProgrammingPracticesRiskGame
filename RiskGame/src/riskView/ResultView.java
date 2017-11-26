package riskView;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * This class will load the Result of tournament mode
 * @author mudraparikh
 *
 */
public class ResultView extends JDialog {
	public JPanel tablePanel;
	public GridBagLayout tableLayout;
	public JTable resultTable;
	public GridBagConstraints c;
	public JScrollPane tablePane;
	public JButton okBtn;
	public Object rowData[][] ={ 
			{"Map1","Map2","Map3"},
	        {"Agressive","Cheater","Cheater"}, 
	        {"Random", "Draw","Agressive"},
	        {"Cheater","Cheater","Cheater"},
	        {"Cheater","Agressive","Random"}
	};
	public Object columnNames[]={ "", "Game 1", "Game 2","Game 3","Game 4" };
	
	/**
	 * Constructs the view for result of tournament mode
	 */
	public void ResultView() {
	setTitle("**Result - Tournament Mode**");
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    setResizable(false);
    setVisible(true);
    
	tableLayout = new GridBagLayout();
    setLayout(tableLayout);
    c = new GridBagConstraints();
    
    c.fill = GridBagConstraints.BOTH;
    c.anchor = GridBagConstraints.LINE_START;
    c.insets = new Insets(5, 5, 5, 5);
    c.weightx = 8;
    c.weighty = 0.5;
    c.gridx = 0;
    c.gridy = 0;
    add(tablePanel());
    setLocationRelativeTo(null);
    pack();
}
	/**
	 * This panel will display the result table
	 */
	private JPanel tablePanel() {
		tablePanel = new JPanel();
		tablePanel.setPreferredSize(new Dimension(250,250));
		tablePanel.setLayout(tableLayout);
		tablePanel.setVisible(true);
		
		    resultTable = new JTable(rowData, columnNames);
		    resultTable.setVisible(true);
		    tablePane = new JScrollPane(resultTable);
		    tablePane.setVisible(true);
		    okBtn = new JButton("OK");
		    okBtn.setVisible(true);
		    
		    c = new GridBagConstraints();
	          
	          c.fill = GridBagConstraints.BOTH;
	          c.insets = new Insets(5, 5, 5, 5);
	          c.weightx = 0.5;
	          c.weighty = 18;
	          c.gridx = 0;
	          c.gridy = 0;
	          tablePanel.add(tablePane, c);
	          
	          c.fill = GridBagConstraints.BOTH;
		      c.insets = new Insets(5, 5, 5, 5);
		      c.weightx = 0.5;
		      c.weighty = 0.5;
		      c.gridx = 0;
		      c.gridy = 1;
		      tablePanel.add(okBtn, c);
		
		return tablePanel;
	}
	/**
	 * Add action listeners
	 */
	public void addActionListeners() {
		ResultView();
		
	}
}
