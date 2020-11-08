import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class models the first panel that opens when the game is run
 * @author Mmedara Josiah
 * @version 1.0
 */
public class RiskController implements ActionListener {
    private Game model;
    private RiskView view;
    private NumberOfPlayersDialog numberOfPlayersDialog;

    /**
     * constructs the risk controller
     * @param model is Game class
     * @param view is RiskView class
     */
    public RiskController(Game model, RiskView view){
        System.out.println("RiskController has loaded++++++++++");
        this.model = model;
        this.view = view;
        view.addActionListeners(this);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e event
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        if(command.equals("newGameButton")){
            System.out.println("NumberOfPlayersDialog is Loading----------");
            //Opens the numberOfPlayersDialog box
            numberOfPlayersDialog = new NumberOfPlayersDialog(view, true);
            numberOfPlayersDialog.addActionListeners(new NumberOfPlayersController(model, numberOfPlayersDialog));
            numberOfPlayersDialog.setVisible(true);
        }
        else if(command.equals("quitButton")){
            System.exit(0);
        }
        else{
            System.out.println("Error: " + command + " command is invalid!");
        }
    }
}
