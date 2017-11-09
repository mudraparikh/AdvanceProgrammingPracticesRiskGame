package riskModels.player;

import riskModels.map.GameMap;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * This class will handle operations on player
 *
 * @author prashantp95
 */
public class PlayerObserverModel extends Observable {
    Player player = new Player();
    String updateMessage = "";
    String phaseDetailMessage="";

    /**
     * getter method give current player
     * @return name of current player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * setter method to assign the player
     * @param player name of the player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * getter method give the message to update
     * @return update message
     */
    public String getUpdateMessage() {
        return updateMessage;
    }

    /**
     * setter method assigns the update message
     * @param updateMessage string to update
     */
    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }
    
    /**
     * getter method gives details about phase
     * @return phase details
     */
    public String getPhaseDetailMessage() {
		return phaseDetailMessage;
	}

    /**
     * setter method to assign phase details
     * @param phaseDetailMessage details about phase
     */
	public void setPhaseDetailMessage(String phaseDetailMessage) {
		this.phaseDetailMessage = phaseDetailMessage;
	}

	/**
     * After Adding domination attribute for each player , this method will return list of player
     *
     * @param playerList Current players list
     */
    public void getPlayerWorldDomination(List<Player> playerList) {
        double totalNumberOfCountries = GameMap.getInstance().getCountryAndNeighborsMap().keySet().size();
        List<Player> updatedPlayerList = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.##");
        if (totalNumberOfCountries > 0) {
            for (Player player : playerList) {
                double dominationOfPlayer = player.assignedCountries.size() / totalNumberOfCountries;
                dominationOfPlayer *= 100;
                player.setDomination(Double.valueOf(df.format(dominationOfPlayer)));
                updatedPlayerList.add(player);
            }
        }
        Player player = new Player();
        player.setPlayerList(updatedPlayerList);
        this.player = player;
        this.updateMessage = "Domination";
        setChanged();
        notifyObservers();
    }

    /**
     * setter method to assign message for the phase
     * @param messageForPhase string message
     */
    public void showPhaseDetails(String messageForPhase) {
        
        this.updateMessage = "Phase";
        this.phaseDetailMessage = messageForPhase;
        setChanged();
        notifyObservers();
    }
}
