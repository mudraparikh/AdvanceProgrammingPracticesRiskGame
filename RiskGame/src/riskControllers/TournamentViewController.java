package riskControllers;

import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskModels.player.Player;
import riskView.GameView;
import riskView.TournamentView;
import tournamentMode.TournamentModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
/**
 * This class implements the controller for the tournament mode
 * @author mudraparikh
 *
 */
public class TournamentViewController implements ActionListener {

    private ArrayList<String> playerNames;
    private ArrayList<String> playerTypes;
    private GameView view;

    /**
     * This constructor adds the various types of players to the view
     * @param view creates object of gameView class
     * @param tournamentModel creates object of TournamentModel
     */
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

	/**
	 * overriding the action performed method
	 */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //Do nothing
    }
}


