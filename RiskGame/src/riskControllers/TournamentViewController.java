package riskControllers;

import riskView.TournamentView;
import static util.RiskGameUtil.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import riskView.ResultView;

public class TournamentViewController implements ActionListener {
	
	private TournamentView view;
	private ResultView addview;
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		String event = actionEvent.getActionCommand();
		if (event.equals(resultBtnName)) {
			addview = new ResultView();
			addview.setVisible(true);
		}
	}
	
	public TournamentViewController(TournamentView add) {
		this.view = view;
    }
}
