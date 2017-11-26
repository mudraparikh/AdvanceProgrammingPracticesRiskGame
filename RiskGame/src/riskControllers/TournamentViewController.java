package riskControllers;

import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.Player;
import riskView.GameView;
import riskView.TournamentView;
import static util.RiskGameUtil.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import riskView.ResultView;
import tournamentMode.TournamentModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class TournamentViewController implements ActionListener {

    private ArrayList<String> playerNames;
    private ArrayList<String> playerTypes;
    private GameView view;
	private ResultView addview;

	public TournamentViewController(GameView view, TournamentModel tournamentModel) {
        // TODO Auto-generated constructor stub

        this.view = view;

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
        
    	public TournamentViewController(TournamentView add) {
		// TODO Auto-generated constructor stub
	}

		@Override
    	public void actionPerformed(ActionEvent actionEvent) {
    		String event = actionEvent.getActionCommand();
    		if (event.equals(resultBtnName)) {
    			addview = new ResultView();
    			addview.setVisible(true);
    }
    	}
}


