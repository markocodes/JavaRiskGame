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

/**
 * This class models the panel that asks for number of players
 * @author Tamilore Odunlami
 * @version 1.0
 */
class NumberOfPlayersController implements ActionListener{
    private Game model;
    private NumberOfPlayersDialog view;
    private PlayerNamesDialog playerNamesDialog;

    /**
     * constructs number of players controller
     * @param model is Game class
     * @param view is NumberOfPlayersDialog class
     */
    public NumberOfPlayersController(Game model, NumberOfPlayersDialog view){
        System.out.println("NumberOfPlayersController has loaded++++++++++");
        this.model = model;
        this.view = view;
    }

    /**
     * displays player names dialog box
     */
    public void displayPlayerNamesDialog(){
        System.out.println("PlayerNamesDialog is loading----------");
        playerNamesDialog = new PlayerNamesDialog(view, true, model.getNoOfPlayers());
        playerNamesDialog.addActionListeners(new PlayerNamesController(model, playerNamesDialog));
        playerNamesDialog.setVisible(true);
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

        if(command.equals("twoPlayersButton")){
            model.setNoOfPlayers(2);
            displayPlayerNamesDialog();
        }
        else if(command.equals("threePlayersButton")){
            model.setNoOfPlayers(3);
            displayPlayerNamesDialog();
        }
        else if(command.equals("fourPlayersButton")){
            model.setNoOfPlayers(4);
            displayPlayerNamesDialog();
        }
        else if(command.equals("fivePlayersButton")){
            model.setNoOfPlayers(5);
            displayPlayerNamesDialog();
        }
        else if(command.equals("sixPlayersButton")){
            model.setNoOfPlayers(6);
            displayPlayerNamesDialog();
        }
        else if(command.equals("backButton")){
            view.dispose();
        }
        else{
            System.out.println("Error: " + command + " command is invalid!");
        }
    }
}

/**
 * This class models the panel that asks for players names
 * @author Mmedara Josiah
 * @version 1.0
 */
class PlayerNamesController implements ActionListener{
    private Game model;
    private PlayerNamesDialog view;
    private ArrayList<String> playerNames;
    private BoardView boardView;
    private boolean load;

    /**
     * constructs player names controller
     * @param model is Game class
     * @param view is PlayerNamesDialog class
     */
    public PlayerNamesController(Game model, PlayerNamesDialog view){
        System.out.println("PlayerNamesController has loaded++++++++++");
        this.model = model;
        this.view = view;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e event
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        load = false;
        String command = e.getActionCommand();
        playerNames = new ArrayList<>();
        if(command.equals("startGameButton")){
            System.out.println("Retrieving player names----------");
            playerNames.add(view.getPlayersNames(1));
            playerNames.add(view.getPlayersNames(2));
            if(model.getNoOfPlayers()>2){
                playerNames.add(view.getPlayersNames(3));
            }
            if(model.getNoOfPlayers()>3){
                playerNames.add(view.getPlayersNames(4));
            }
            if(model.getNoOfPlayers()>4){
                playerNames.add(view.getPlayersNames(5));
            }
            if(model.getNoOfPlayers()>5){
                playerNames.add(view.getPlayersNames(6));
            }
            System.out.println("The game is being prepared----------");
            load = model.init(playerNames);
            if(load){
                System.out.println("All Preparations Complete++++++++++\n" +
                        "Board is loading----------");
                boardView = new BoardView(view, true, model);
                boardView.addActionListeners(new BoardViewController(model, boardView));
                boardView.addListSelectionListeners(new AdjacentListController(model, boardView));
                boardView.addListSelectionListeners(new PlayerListController(model, boardView));
                boardView.setVisible(true);
            }
        }
        else if(command.equals("backButton")){
            view.dispose();
        }
        else{
            System.out.println("Error: " + command + " command is invalid!");
        }
    }
}
