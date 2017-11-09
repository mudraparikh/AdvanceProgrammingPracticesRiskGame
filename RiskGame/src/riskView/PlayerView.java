package riskView;

import riskModels.player.Player;
import riskModels.player.PlayerObserverModel;

import java.util.Observable;
import java.util.Observer;


/**
 * This class will handle display of  players' details
 *
 * @author prashantp95
 */
public class PlayerView implements Observer {

	/**
	 * overriding update method for getting the current state of the objects
	 */
    @Override
    public void update(Observable arg0, Object arg1) {
        // TODO Auto-generated method stub

        PlayerObserverModel p = (PlayerObserverModel) arg0;
        if (p.getUpdateMessage().equalsIgnoreCase("Domination")) {
            StringBuilder dominationDetails = new StringBuilder();

            for (Player play : p.getPlayer().getPlayerList()) {
                dominationDetails.append(play.getName()).append(" ").append("\n");
                dominationDetails.append(String.valueOf(play.getDomination())).append("%").append("\n");
            }
            //GameView.dominationLabel = new JLabel(lable+"\n");
            GameView.showDomination(dominationDetails);
        }
        if (p.getUpdateMessage().equalsIgnoreCase("Phase")) {
           GameView.updatePanelOfPhaseDetails(p.getPhaseDetailMessage());
        }


    }
}

	

