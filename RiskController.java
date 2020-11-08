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

/**
 * sets up the Board View Controller
 * @author Tamilore Odunlami & Mmedara Josiah
 * @version 1.0
 */
class BoardViewController implements ActionListener{
    private Game model;
    private BoardView view;
    private Territory attacker;
    private PlayerListController player1;
    private PlayerListController player2;

    /**
     * constructs the board view controller
     * @param model is Game class
     * @param view is BoardView class
     */
    public BoardViewController(Game model, BoardView view){
        this.model = model;
        this.view = view;
        model.startGame();
    }


    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();

        if(command.equals("passButton")){
            model.notifyAllObservers();
            view.clearAllTextFields();
            model.nextPlayer();
        }

        if(command.equals("helpButton")){
            model.notifyAllObservers();
            view.clearAllTextFields();
            model.help();
        }

        if(command.equals("quitButton")){
            System.exit(0);
        }

        if(command.equals("attackButton")){
            Territory attacker = view.getSelectedTerritory();
            Territory defender = view.getSelectedAdjacent();
            int attackerDiceRolls = view.getNumberOfAttackerDiceRolls();
            int defenderDiceRolls = view.getNumberOfDefenderDiceRolls();

            if(attacker!=null && defender!=null && attackerDiceRolls>0 &&
                    attackerDiceRolls<4 && defenderDiceRolls>0 && defenderDiceRolls<4){
                model.attack(attacker, defender, attackerDiceRolls, defenderDiceRolls);
            }
            else{
                System.out.println("\nYou don't seem ready to attack. Click help to see what you missed.\n");
            }
            model.notifyAllObservers();
            view.clearAllTextFields();
        }

    }
}

/**
 * this class implements the controller for all player lists
 */
class PlayerListController implements ListSelectionListener{
    private Game model;
    private BoardView view;

    /**
     * constructs player list controller
     * @param model is the game
     * @param view is board view
     */
    public PlayerListController(Game model, BoardView view){
        this.model = model;
        this.view = view;
    }

    /**
     * Called whenever the value of the selection changes.
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        if(model.getActivePlayerIndex() == 0) {
            if (!e.getValueIsAdjusting()) {
                if (view.getIndexOfSelectedPlayerTerritory(1) == -1) {
                } else {
                    model.setPlayerTerritorySelection(view.getSelectedPlayerTerritory(1), 1);
                }
            }
        }
        else if(model.getActivePlayerIndex() == 1) {
            if (!e.getValueIsAdjusting()) {
                if (view.getIndexOfSelectedPlayerTerritory(2) == -1) {
                } else {
                    model.setPlayerTerritorySelection(view.getSelectedPlayerTerritory(2), 2);
                }
            }
        }
        else if(model.getActivePlayerIndex() == 2) {
            if (!e.getValueIsAdjusting()) {
                if (view.getIndexOfSelectedPlayerTerritory(3) == -1) {
                } else {
                    model.setPlayerTerritorySelection(view.getSelectedPlayerTerritory(3), 3);
                }
            }
        }
        else if(model.getActivePlayerIndex() == 3) {
            if (!e.getValueIsAdjusting()) {
                if (view.getIndexOfSelectedPlayerTerritory(4) == -1) {
                } else {
                    model.setPlayerTerritorySelection(view.getSelectedPlayerTerritory(4), 4);
                }
            }
        }
        else if(model.getActivePlayerIndex() == 4) {
            if (!e.getValueIsAdjusting()) {
                if (view.getIndexOfSelectedPlayerTerritory(5) == -1) {
                } else {
                    model.setPlayerTerritorySelection(view.getSelectedPlayerTerritory(5), 5);
                }
            }
        }
        else if(model.getActivePlayerIndex() == 5) {
            if (!e.getValueIsAdjusting()) {
                if (view.getIndexOfSelectedPlayerTerritory(6) == -1) {
                } else {
                    model.setPlayerTerritorySelection(view.getSelectedPlayerTerritory(6), 6);
                }
            }
        }
    }
}
/**
 *  Implements the controller for the list of adjacent territories
 * @author Tamilore Odunlami
 * @version 1.0
 */
class AdjacentListController implements ListSelectionListener{
    private Game model;
    private BoardView view;

    public AdjacentListController(Game model, BoardView view){
        this.model = model;
        this.view = view;
    }

    /**
     * Called whenever the value of the selection changes.
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        if (!e.getValueIsAdjusting()) {
            if (view.getIndexOfSelectedPlayerTerritory(0) == -1) {
            }
            else {
                model.setPlayerTerritorySelection(view.getSelectedPlayerTerritory(0), 0);
            }
        }
    }
}
