package logic;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.ChooseMazeUI;
import ui.CreateMazeUI;
import ui.LoginUI;
import ui.MazeManagementUI;
import ui.MazeUI;
import ui.ResultsMazeUI;

public class UIController {

	private ChooseMazeUI chooseMazeUI;
	private CreateMazeUI createMazeUI;
	private LoginUI loginUI;
	private MazeManagementUI mazeManagementUI;
	private ResultsMazeUI resultsMazeUI;
	private MazeUI mazeUI;
	
	public void changeView(JFrame oldView, JFrame newView) {
		oldView.setVisible(false);
		newView.setVisible(true);
	}
	
	public void initialize(Controller controller) {
		chooseMazeUI = new ChooseMazeUI(controller);
		createMazeUI = new CreateMazeUI(controller);
		loginUI = new LoginUI(controller);
		mazeManagementUI = new MazeManagementUI(controller);
		resultsMazeUI = new ResultsMazeUI(controller);
		mazeUI = new MazeUI(controller);
	}

	public ChooseMazeUI getChooseMazeUI() {
		return chooseMazeUI;
	}

	public CreateMazeUI getCreateMazeUI() {
		return createMazeUI;
	}

	public LoginUI getLoginUI() {
		return loginUI;
	}

	public MazeManagementUI getMazeManagementUI() {
		return mazeManagementUI;
	}

	public ResultsMazeUI getResultsMazeUI() {
		return resultsMazeUI;
	}

	public MazeUI getMazeUI() {
		return mazeUI;
	}
	
	

	
}