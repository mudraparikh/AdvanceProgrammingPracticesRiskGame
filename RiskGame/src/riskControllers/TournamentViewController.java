package riskControllers;

import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskView.TournamentView;
import tournamentMode.TournamentModel;

public class TournamentViewController {

	public TournamentViewController(TournamentView add, TournamentModel tournamentModel) {
		// TODO Auto-generated constructor stub
		GameMap gameMap = GameMap.getInstance();
		MapModel mapModel = new MapModel();
		for(String mapFile : tournamentModel.getMapFiles()) {
			gameMap= mapModel.readMapFile(mapFile);
			if(!gameMap.isCorrectMap) {
				System.out.println(mapFile+" is not Valid");
				System.out.println(gameMap.getErrorMessage());
				break;
			}
			for(int i=1;i<= tournamentModel.getNumberOfGames();i++) {
				System.out.println("Game"+i);
			}
			System.out.println("--------------------");
		}
	}

}
