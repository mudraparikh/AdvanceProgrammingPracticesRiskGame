package riskModels.player;

import riskView.GameView;

/**
 * This is an interface of player strategy
 * @author hnath
 *
 */
public interface PlayerStrategy {

    void attack(String country1, String country2, GameView gameView, Player model);

    void fortify(String country1, String country2, GameView gameView, Player model);

    void reinforce(String country, GameView gameView, Player model);
}
