package riskControllers;

import riskView.TournamentView;
import static util.RiskGameUtil.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import riskView.ResultView;

public class TournamentViewController implements ActionListener {
	ResultView add;
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		String event = actionEvent.getActionCommand();
		if (event.equals(resultBtnName)) {
			add = new ResultView();
			add.setVisible(true);
		}
	}
	
	public TournamentViewController(TournamentView add) {
    	//TODO Generated code
    }
}
