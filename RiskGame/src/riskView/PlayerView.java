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


public class PlayerView implements Observer{

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

		PlayerModel p = (PlayerModel)arg0;
		String lable="";
		
		for(Player play:p.getPlayer().getPlayerList()) {
			lable+=play.getName()+" "+"\n";
			lable+=(String.valueOf(play.getDomination())+"%"+"\n");
		}
		//GameView.dominationLabel = new JLabel(lable+"\n");
		GameView.dominationLabel.setText(lable+"\n");
		GameView.dominationViewPane = new JScrollPane(GameView.dominationLabel);
		GameView.dominationViewPane.repaint();
		System.out.println("in Player View"+lable);
}
	
	
	
}

	

