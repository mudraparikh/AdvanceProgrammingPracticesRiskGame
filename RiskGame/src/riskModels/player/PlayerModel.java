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
public class PlayerModel extends Observable {
    Player player = new Player();
    String updateMessage = "";

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getUpdateMessage() {
        return updateMessage;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
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
        notifyObservers(Player.class);
        String lable = "";

        for (Player play : player.getPlayerList()) {
            lable.concat(play.getName());
            lable.concat(String.valueOf(play.getDomination()) + "\n");
        }

    }

    public void getPhaseDetails() {
        List<Player> playerList = GameMap.getInstance().getPlayerList();
        this.player.setPlayerList(playerList);
        this.updateMessage = "Phase";
        setChanged();
        notifyObservers(Player.class);

    }
}
