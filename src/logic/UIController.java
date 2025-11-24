package logic;

import javax.swing.JFrame;

import ui.ChooseMazeUI;
import ui.CreateMazeUI;
import ui.LoginUI;
import ui.MazeManagementUI;
import ui.MazeUI;
import ui.ResultsMazeUI;

/**
 * Controller for managing all user interface views.
 */
public class UIController {

    private ChooseMazeUI chooseMazeUI;
    private CreateMazeUI createMazeUI;
    private LoginUI loginUI;
    private MazeManagementUI mazeManagementUI;
    private ResultsMazeUI resultsMazeUI;
    private MazeUI mazeUI;

    /**
     * Changes the visible view from one JFrame to another.
     *
     * @param oldView the current JFrame to hide
     * @param newView the new JFrame to show
     */
    public void changeView(JFrame oldView, JFrame newView) {
        oldView.setVisible(false);
        newView.setVisible(true);
    }

    /**
     * Initializes all UI screens and links them with the main controller.
     *
     * @param controller the main Controller object that manages logic and data
     */
    public void initialize(Controller controller) {
        chooseMazeUI = new ChooseMazeUI(controller);
        createMazeUI = new CreateMazeUI();
        loginUI = new LoginUI();
        mazeManagementUI = new MazeManagementUI(controller);
        resultsMazeUI = new ResultsMazeUI(controller);
        mazeUI = new MazeUI();

        createMazeUI.initialize(controller);
        loginUI.initialize(controller);
        resultsMazeUI.intialize(controller);
        mazeUI.initialize(controller);
    }

    /** @return the ChooseMazeUI instance */
    public ChooseMazeUI getChooseMazeUI() {
        return chooseMazeUI;
    }

    /** @return the CreateMazeUI instance */
    public CreateMazeUI getCreateMazeUI() {
        return createMazeUI;
    }

    /** @return the LoginUI instance */
    public LoginUI getLoginUI() {
        return loginUI;
    }

    /** @return the MazeManagementUI instance */
    public MazeManagementUI getMazeManagementUI() {
        return mazeManagementUI;
    }

    /** @return the ResultsMazeUI instance */
    public ResultsMazeUI getResultsMazeUI() {
        return resultsMazeUI;
    }

    /** @return the MazeUI instance */
    public MazeUI getMazeUI() {
        return mazeUI;
    }
}
