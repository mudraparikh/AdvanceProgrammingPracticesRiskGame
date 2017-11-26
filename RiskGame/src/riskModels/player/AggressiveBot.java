package riskModels.player;

import riskModels.country.Country;
import riskModels.dice.Dice;
import riskModels.map.GameMap;
import riskModels.map.MapModel;
import riskView.GameView;

import javax.swing.*;
import javax.swing.plaf.basic.BasicIconFactory;
import java.util.Objects;
import java.util.Random;

public class AggressiveBot implements PlayerStrategy {
    public Country countryA;
    public Country countryB;
    public Dice dice;

    public int attackerLosses;
    public int defenderLosses;
    public int attackerDice;
    public int defenderDice;

    public Integer[] attackerRolls;
    public Integer[] defenderRolls;

    public Random rng;

    @Override
    public void attack(String country1, String country2, GameView gameView, Player model) {
        countryA = MapModel.getCountryObj(country1, GameMap.getInstance());
        countryB = MapModel.getCountryObj(country2, GameMap.getInstance());
        model.updatePhaseDetails("Repaint");
        model.updatePhaseDetails("==Attack Phase==");
        while (checkPlayerTurnCanContinue(countryA,countryB)) {

            dice = new Dice();

            // Set default values
            attackerLosses = 0;
            defenderLosses = 0;
            attackerDice = 1;
            defenderDice = 1;

            // Attacker chooses how many dice to roll
            rng = new Random();
            if (countryA.getCurrentArmiesDeployed() <= 3) {
                attackerDice = 1;
            } else {
                attackerDice = rng.nextInt(2) + 1;
            }

            try {
                // Defender chooses how many dice to roll after attacker
                if(countryB.getBelongsToPlayer().isBot()){
                    rng = new Random();
                    if (countryB.getCurrentArmiesDeployed() <= 1) {
                        defenderDice = 1;
                    } else {
                        defenderDice = rng.nextInt(1) + 1;
                    }
                }
                else {
                    defenderDice = showDefenderDiceDialogBox(gameView, model);
                }
            } catch (Exception e) {
                // Error: defender inputs invalid number of dice
                defenderDice = 1;
                GameView.displayLog("Roll either 1 or 2 dice. To roll 2 dice, you must have at least 2 armies on your country.");
            }
            attackerRolls = Dice.rollDice(attackerDice).getDiceResult();
            defenderRolls = Dice.rollDice(defenderDice).getDiceResult();

            GameView.displayLog("\nAttackers threw  dice(s) : ");
            for (int attackerRoll : attackerRolls) {
                GameView.displayLog(" " + attackerRoll + " ");
            }
            GameView.displayLog("\nDefender threw  dice(s) : ");
            for (int defenderRoll : defenderRolls) {
                GameView.displayLog(" " + defenderRoll + " ");
            }
            // Rolls arrays have been ordered in descending order. Index 0 = highest pair
            compareDiceResultsAndCalculateLosses();
            GameView.displayLog("\n\n<COMBAT REPORT>");

            updateArmiesBasedOnDiceResult(attackerLosses, defenderLosses);

            GameView.displayLog("Attacker Losses : " + attackerLosses + " army.");
            GameView.displayLog("Defender Losses : " + defenderLosses + " army.");
            GameView.displayLog(countryA.getCountryName() + " has now " + countryA.getCurrentArmiesDeployed());
            GameView.displayLog(countryB.getCountryName() + " has now " + countryB.getCurrentArmiesDeployed());
            GameView.displayLog("\n\n");
            model.updatePhaseDetails("<Based On Dice Results> \n");
            model.updatePhaseDetails("Attacker Losses : " + attackerLosses + " army." + "\n" + "Defender Losses : " + defenderLosses + " army.");

            // If defending country loses all armies
            if (countryB.getCurrentArmiesDeployed() < 1) {

                GameView.displayLog(countryA.getBelongsToPlayer().getName() + " has defeated all of " + countryB.getBelongsToPlayer().getName() + "'s armies in " + country2 + " and has occupied the country!");
                defendingPlayerLostCountry(countryA, countryB, model);
            }

            //If player conquered all the country and have won the game
            if (model.currentPlayer.assignedCountries.size() == GameMap.getInstance().getCountries().size()) {
                model.hasBotWon = true;
                GameView.displayLog("" + model.currentPlayer.getName() + " has won the game ! Congratulations ! ");
                model.updatePhaseDetails(model.currentPlayer.getName() + "Won");
            }
            GameView.updateMapPanel();

        }

    }

    private int showDefenderDiceDialogBox(GameView gameView, Player model) {
        Integer[] selectOptions = new Integer[getMaxNumberOfDicesForDefender(countryB)];
        for (int i = 0; i < selectOptions.length; i++) {
            selectOptions[i] = i + 1;
        }
        model.updatePhaseDetails(countryB.getBelongsToPlayer().getName()+" is Defending ");
        return (Integer) JOptionPane.showInputDialog(gameView,
                countryB.getBelongsToPlayer().getName() + ", you are defending " + countryB.getCountryName() + " from " + countryA.getBelongsToPlayer().getName() + "! How many dice will you roll?",
                "Input", JOptionPane.OK_OPTION, BasicIconFactory.getMenuArrowIcon(), selectOptions,
                selectOptions[0]);
    }

    private int getMaxNumberOfDicesForDefender(Country country) {
        return country.getCurrentArmiesDeployed() >= 2 ? 2 : 1;
    }

    private boolean checkPlayerTurnCanContinue(Country countryA, Country countryB) {
        if(countryA.getCurrentArmiesDeployed() > 1 && !countryB.getBelongsToPlayer().getName().equals(countryA.getBelongsToPlayer().getName())){
            return true;
        }
        return false;
    }

    private void defendingPlayerLostCountry(Country countryA, Country countryB, Player model) {

        // Remove country from defender's list of occupied territories and adds to attacker's list
        countryB.getBelongsToPlayer().assignedCountries.remove(countryB);
        countryA.getBelongsToPlayer().assignedCountries.add(countryB);

        // Check if defender is eliminated from game
        if (countryB.getBelongsToPlayer().getAssignedCountries().size() == 0) {
            model.playerLostRule(countryA, countryB);
        }
        // Set country player to attacker
        countryB.setBelongsToPlayer(countryA.getBelongsToPlayer());
        model.updatePhaseDetails("\n"+countryB.getCountryName()+" has been captured ! ");

        //The attacking player must then place a number of armies
        //in the conquered country which is greater or equal than the number of dice that was used in the attack that
        //resulted in conquering the country
        int moveArmies = attackerDice;

        countryA.subtractArmy(moveArmies);
        countryB.addArmy(moveArmies);
        model.hasCountryCaptured = true;
        model.updateDomination();
    }

    private void compareDiceResultsAndCalculateLosses() {
        // Calculate losses
        if (attackerRolls[0] > defenderRolls[0]) {
            defenderLosses++;
        } else if (attackerRolls[0] < defenderRolls[0] || Objects.equals(attackerRolls[0], defenderRolls[0])) {
            attackerLosses++;
        }
        // Index 1 = second highest pair
        if (attackerDice > 1 && defenderDice > 1) {

            if (attackerRolls[1] > defenderRolls[1]) {
                defenderLosses++;

            } else if (attackerRolls[1] < defenderRolls[1] || Objects.equals(attackerRolls[1], defenderRolls[1])) {
                attackerLosses++;
            }
        }
    }

    private void updateArmiesBasedOnDiceResult(int attackerLosses, int defenderLosses) {
        countryA.subtractArmy(attackerLosses);
        countryB.subtractArmy(defenderLosses);
    }

    @Override
    public void fortify(String country1, String country2, GameView gameView, Player model) {

        countryA = MapModel.getCountryObj(country1, GameMap.getInstance());
        countryB = MapModel.getCountryObj(country2, GameMap.getInstance());

        // Player inputs how many armies to move from country A to country B
        model.updatePhaseDetails("Repaint");
        model.updatePhaseDetails("===Fortification phase===");

        int armies = countryA.getCurrentArmiesDeployed() - 1;

        model.moveArmyFromTo(countryA, countryB, armies);
        GameView.updateMapPanel();
        model.updatePhaseDetails("You moved "+armies+" army from "+countryA.getCountryName()+" to " + countryB.getCountryName());
        model.checkHasCountryCaptured();
        model.updatePhaseDetails("===Fortification ends===");
    }

    @Override
    public void reinforce(String country, GameView gameView, Player model) {
        countryA = MapModel.getCountryObj(country, GameMap.getInstance());
        GameView.displayLog(model.currentPlayer.name + " gets " + model.currentPlayerReinforceArmies + " armies");
        try {
            if (model.currentPlayer.getTotalArmies() > 0) {
                int armies = model.currentPlayer.getTotalArmies();
                model.currentPlayer.subArmy(armies);
                countryA.addArmy(armies);
                GameView.displayLog(model.currentPlayer.getName() + " has chosen to reinforce " + countryA.getCountryName() + " with " + armies + " armies.");
                if (model.currentPlayer.getTotalArmies() == 0) {
                    GameView.displayLog("You do not have any armies left to reinforce");
                    model.updatePhaseDetails("Reinforcement Phase ends");
                }
                GameView.updateMapPanel();
            }

        } catch (Exception e) {
            GameView.displayLog("System Error or Exception is thrown for reinforce method");
        }

    }
}
