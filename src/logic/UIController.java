package logic;

import javax.swing.JPanel;

import ui.ChooseMazeUI;
import ui.CreateMazeUI;
import ui.LoginUI;
import ui.MazeManagementUI;
import ui.ResultsMazeUI;

public class UIController {

	private ChooseMazeUI chooseMazeUI;
	private CreateMazeUI createMazeUI;
	private LoginUI loginUI;
	private MazeManagementUI mazeManagementUI;
	private ResultsMazeUI resultsMazeUI;
	
	public static void main(String[] args) {
		chooseMazeUI = new ChooseMazeUI();
		createMazeUI = new CreateMazeUI();
		loginUI = new LoginUI();
		mazeManagementUI = new MazeManagementUI();
		resultsMazeUI = new ResultsMazeUI();
		
	}
	
	public void changeView(JPanel oldView, JPanel newView) {
		oldView.setVisible(false);
		newView.setVisible(true);
	}
}