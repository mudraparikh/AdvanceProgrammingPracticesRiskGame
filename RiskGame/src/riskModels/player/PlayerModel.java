package riskModels.player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import riskModels.continent.Continent;
import riskModels.country.Country;
import riskModels.map.GameMap;
/**
 * This class will handle operations on player 
 * @author prashantp95
 *
 */
public class PlayerModel extends Observable{
	Player player = new Player();
	String updateMessage="";
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	/**
	 * This method will return list of continent where player has at least one country
	 * @param player object of player
	 * @return list of continent objects
	 */
	public List<Continent> getContinentOfPlayer(Player player){
		
		List<Country> countriesOfPlayer = player.getAssignedCountries();
		List<Continent> continentList = new ArrayList<>(); //List of Continent for the player 
		if(countriesOfPlayer!=null && !countriesOfPlayer.isEmpty()) {
			for(Country country : countriesOfPlayer) {
				//we have country,now find the continent details  
			    int indexOfContinent = GameMap.getInstance().getContinentList().indexOf(new Continent(country.getBelongsToContinent()));
		        Continent continent =GameMap.getInstance().getContinentList().get(indexOfContinent); 
				continentList.add(continent);
			}
		}
		return continentList;
		
	}
	/**
	 * After Adding domination attribute for each player , this method will return list of player 
	 * @param playerList Current players list 
	 * @return List of players
	 */
	public Player getPlyaerWorldDomination(List<Player> playerList) {
		double totalNumberOfCountries=GameMap.getInstance().getCountryAndNeighborsMap().keySet().size();
		List<Player> updatedPlayerList = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("#.##");
		if(totalNumberOfCountries>0) {
			for(Player player : playerList) {
				double dominationOfPlayer = player.getAssignedCountries().size()/totalNumberOfCountries;
				dominationOfPlayer*=100;
				player.setDomination(Double.valueOf(df.format(dominationOfPlayer)));
				updatedPlayerList.add(player);
			}
		}
		Player player = new Player();
		player.setPlayerList(updatedPlayerList);
		this.player=player;
		setChanged();
		notifyObservers(Player.class);
		String lable="";
		
		for(Player play:player.getPlayerList()) {
			lable.concat(play.getName());
			lable.concat(String.valueOf(play.getDomination())+"\n");
		}
		return player;
		
	}
}
