package tournamentMode;

import java.util.List;

public class TournamentModel {

	List<String> mapFiles;
	int numberOfGames=1;
	int numberOfTurns=1;
	
	
	public TournamentModel(List<String> mapFiles, int numberOfGames, int numberOfTurns) {
		super();
		this.mapFiles = mapFiles;
		this.numberOfGames = numberOfGames;
		this.numberOfTurns = numberOfTurns;
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
}
