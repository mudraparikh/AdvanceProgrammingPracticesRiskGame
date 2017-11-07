package riskView;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import riskModels.player.Player;
import riskModels.player.PlayerModel;


/**
 * This class will handle display of  players' details 
 * @author prashantp95
 *
 */
public class PlayerView implements Observer{
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

		PlayerModel p = (PlayerModel)arg0;
		if(p.getUpdateMessage().equalsIgnoreCase("Domination")) {
			StringBuilder dominationDetails= new StringBuilder();
			
			for(Player play:p.getPlayer().getPlayerList()) {
                dominationDetails.append(play.getName()).append(" ").append("\n");
                dominationDetails.append(String.valueOf(play.getDomination())).append("%").append("\n");
			}
			//GameView.dominationLabel = new JLabel(lable+"\n");
            GameView.showDomination(dominationDetails);
        }
		if(p.getUpdateMessage().equalsIgnoreCase("Phase")) {
			StringBuilder lable= new StringBuilder();
		 for(Player player:p.getPlayer().getPlayerList())
		 {
			 lable.append(player.getName()).append("\n");
			 lable.append("Total Armies: ").append(player.getTotalArmies()).append("\n");
			 lable.append("Reinforcement Armies").append(player.getReinforcementArmies()).append("\n");
			 lable.append("Countries Owned").append(player.getAssignedCountries().size()).append("\n");
			 lable.append("  ||  " + "\n");
			 
		 }
		   	GameView.phaseViewTextArea.setText(lable+"\n");
			GameView.phaseViewPane = new JScrollPane(GameView.phaseViewTextArea);
			GameView.phaseViewPane.repaint();
		}
		
		
	}
}

	

