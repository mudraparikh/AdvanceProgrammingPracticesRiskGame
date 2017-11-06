package riskControllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import riskModels.GamePlayModel;
import riskView.GameView;

/**
* This class maps the user's actions in the GameView to the data and methods in the model.
*/
public class GameViewController implements ActionListener{

	private int armies;
	private int diceattack;
	private int dicedefend;
	private GamePlayModel model;
	private GameView view;
		
	//Constructor
	public GameViewController(GamePlayModel model, GameView view) {
		
		this.model = model;
		this.view = view;
		model.startGame();
		}

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		String actionEvent = evt.getActionCommand();
		if(actionEvent.equals("reinforceBtn")){
			//call reinforceBtn logic from the model class.
			//add the following code to reinforce.
			armies =Integer.parseInt(JOptionPane.showInputDialog("Enter no. of armies to be sent for reinforcment in selected country?"));
			System.out.println("No of armies :"+ armies);
		}
		else if(actionEvent.equals("attackBtn")){
			//call attackBtn logic from the model class.
			//add the following code to reinforce.
			diceattack =Integer.parseInt(JOptionPane.showInputDialog("Attacker - Enter the no. of dice you want to roll?(btwn 1-3)"));
			System.out.println("No. of Attacker's dice :"+ diceattack);
			dicedefend =Integer.parseInt(JOptionPane.showInputDialog("Defender - Enter the no. of dice you want to roll?(btwn 1-3)"));
			System.out.println("No. of Defender's dice :"+ diceattack);
		}
		else if(actionEvent.equals("fortifyBtn")){
			//call fortifyBtn logic from the model class.
			//add the following code to reinforce.
			armies = Integer.parseInt(JOptionPane.showInputDialog("Enter no. of armes to be fortified from Country1 to Country2"));
			System.out.println("No. of armies fortified from Country1 to Country2 :"+ armies);
		}
		else if(actionEvent.equals("endTurnBtn")){
			model.nextPlayerTurn();
			//call nextPlayer() logic from the model class.
		}
		else if(actionEvent.equals("menuBtn")){
			model.nextPlayerTurn();
			//call nextPlayer() logic from the model class.
		}
		else {
			System.out.println("Error : No relevant action Found" + actionEvent);
		}
	}
}
