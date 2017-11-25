package tournamentMode;

import java.util.ArrayList;
import java.util.List;

public class TournamentModel {


    private ArrayList<String> playerNames;
    private ArrayList<String> playerTypes;

	List<String> mapFiles;
	int numberOfGames=1;
	int numberOfTurns=1;
	
	
	public TournamentModel(List<String> mapFiles, int numberOfGames, int numberOfTurns) {
		super();
		this.mapFiles = mapFiles;
		this.numberOfGames = numberOfGames;
		this.numberOfTurns = numberOfTurns;
		playerNames = new ArrayList<String>();
		playerTypes = new ArrayList<String>();

		playerNames.add("Aggressive");
		playerNames.add("Benevolent");
		playerNames.add("Random");
		playerNames.add("Cheater");
		playerTypes.add("Aggressive Bot");
		playerTypes.add("BenevolentBot Bot");
		playerTypes.add("Randomize Bot");
		playerTypes.add("Cheater Bot");

	}
	public List<String> getMapFiles() {
		return mapFiles;
	}
	public void setMapFiles(List<String> mapFiles) {
		this.mapFiles = mapFiles;
	}
	public int getNumberOfGames() {
		return numberOfGames;
	}
	public void setNumberOfGames(int numberOfGames) {
		this.numberOfGames = numberOfGames;
	}
	public int getNumberOfTurns() {
		return numberOfTurns;
	}
	public void setNumberOfTurns(int numberOfTurns) {
		this.numberOfTurns = numberOfTurns;
	}
    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(ArrayList<String> playerNames) {
        this.playerNames = playerNames;
    }

    public ArrayList<String> getPlayerTypes() {
        return playerTypes;
    }

    public void setPlayerTypes(ArrayList<String> playerTypes) {
        this.playerTypes = playerTypes;
    }

}
