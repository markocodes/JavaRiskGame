import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
 * This class models the first panel that opens when the game is run
 * @author Mmedara Josiah
 * @version 1.0
 */
public class RiskController implements ActionListener {
    private Game model;
    private Game loadedModel;
    private RiskView view;
    private BoardView boardView;
    private JFileChooser fileChooser;
    private ObjectInputStream objectReader;
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
            numberOfPlayersDialog = new NumberOfPlayersDialog(view, true);
            numberOfPlayersDialog.addActionListeners(new NumberOfPlayersController(model, numberOfPlayersDialog));
            view.setVisible(false);
            numberOfPlayersDialog.setVisible(true);
        }
        else if(command.equals("loadButton")){
            fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Risk Saved Files", "rsf");
            fileChooser.setFileFilter(filter);
            if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
                try {
                    objectReader = new ObjectInputStream(new FileInputStream(fileChooser.getSelectedFile()));
                    loadedModel = (Game)objectReader.readObject();
                    objectReader.close();
                    boardView = new BoardView(view, true, loadedModel);
                    boardView.addActionListeners(new BoardViewController(loadedModel, boardView));
                    boardView.addListSelectionListeners(new PlayerListController(loadedModel, boardView));
                    boardView.addListSelectionListeners(new AdjacentListController(loadedModel, boardView));
                    view.setVisible(false);
                    boardView.setVisible(true);
                } catch (IOException e1) {
                    System.out.println(e1.getMessage());
                } catch (ClassNotFoundException e1)	{
                    System.out.println(e1.getMessage());
                }
            }
        }

        else if(command.equals("quitButton")){
            model.quitGame();
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
        view.setVisible(false);
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
            view.getRiskView().setVisible(true);
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
    private ArrayList<String> humanOrAI;
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
        humanOrAI = new ArrayList<>();
        if(command.equals("startGameButton")){
            System.out.println("Retrieving player names----------");
            playerNames.add(view.getPlayersNames(0));
            playerNames.add(view.getPlayersNames(1));
            humanOrAI.add(view.getHumanOrAI(0));
            humanOrAI.add(view.getHumanOrAI(1));
            if(model.getNoOfPlayers()>2){
                playerNames.add(view.getPlayersNames(2));
                humanOrAI.add(view.getHumanOrAI(2));
            }
            if(model.getNoOfPlayers()>3){
                playerNames.add(view.getPlayersNames(3));
                humanOrAI.add(view.getHumanOrAI(3));
            }
            if(model.getNoOfPlayers()>4){
                playerNames.add(view.getPlayersNames(4));
                humanOrAI.add(view.getHumanOrAI(4));
            }
            if(model.getNoOfPlayers()>5){
                playerNames.add(view.getPlayersNames(5));
                humanOrAI.add(view.getHumanOrAI(5));
            }
            System.out.println("The game is being prepared----------");
            load = model.init(playerNames, humanOrAI);
            if(load){
                System.out.println("All Preparations Complete++++++++++\n" +
                        "Board is loading----------");
                boardView = new BoardView(view, true, model);
                boardView.addActionListeners(new BoardViewController(model, boardView));
                boardView.addListSelectionListeners(new AdjacentListController(model, boardView));
                boardView.addListSelectionListeners(new PlayerListController(model, boardView));
                view.setVisible(false);
                boardView.setVisible(true);
            }
        }
        else if(command.equals("backButton")){
            view.dispose();
            view.getNumberView().setVisible(true);
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
    private Game loadedModel;
    private RiskView riskView;
    private JFileChooser fileChooser;
    private ObjectOutputStream objectWriter;
    private ObjectInputStream objectReader;

    /**
     * constructs the board view controller
     * @param model is Game class
     * @param view is BoardView class
     */
    public BoardViewController(Game model, BoardView view){
        this.model = model;
        this.view = view;
        riskView = view.getRiskView();
        model.startGame();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        executeCommand(command);
    }
    public void executeCommand(String command){
        if(command.equals("reinforceButton")){
            Territory territory = view.getSelectedTerritory();
            int troops = view.getReinforcementTextField();
            if(territory!=null) model.reinforce(territory, troops);
            view.clearAllTextFields();
        }

        else if(command.equals("attackButton")){
            Territory attacker = view.getSelectedTerritory();
            Territory defender = view.getSelectedAdjacent();
            int attackerDiceRolls = view.getNumberOfAttackerDiceRolls();
            if (attacker != null && defender != null) {
                if (attackerDiceRolls > 0 && attackerDiceRolls < 4) {
                    model.attack(attacker, defender, attackerDiceRolls);
                }
                else {
                    System.out.println("Cannot attack!! attacker dice roll must be either 1, 2 or 3");
                }
            } else {
                System.out.println("You must select both an attacking and defending territory");
            }
            view.clearAllTextFields();
        }

        else if(command.equals("passButton")){
            view.clearAllTextFields();
            model.nextPlayer();
        }

        else if(command.equals("fortifyButton")) {
            Territory fortifyFrom = view.getSelectedTerritory();
            Territory fortifyTo = view.getSelectedAdjacent();
            int troops = view.getFortifyTextField() / 1;
            if (fortifyFrom != null && fortifyTo != null && troops > 0){
                model.fortify(fortifyFrom, fortifyTo, troops);
            }
            else{
                System.out.println("You must select two adjacent territories that you own \nand the number of troops you wish to move");
            }
            view.clearAllTextFields();
        }

        else if(command.equals("saveButton")){
            fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Risk Saved Files", "rsf");
            fileChooser.setFileFilter(filter);

            if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
                try {
                    objectWriter = new ObjectOutputStream(new FileOutputStream(fileChooser.getSelectedFile()));
                    objectWriter.writeObject(model);
                    objectWriter.close();
                    System.out.println("Game Saved");
                } catch (IOException e1) {
                    System.out.println(e1.getMessage());
                }
            }
        }

        else if(command.equals("loadButton")){
            fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Risk Saved Files", "rsf");
            fileChooser.setFileFilter(filter);
            if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
                try {
                    objectReader = new ObjectInputStream(new FileInputStream(fileChooser.getSelectedFile()));
                    loadedModel = (Game)objectReader.readObject();
                    objectReader.close();
                    System.out.println(loadedModel.getPlayerNames().get(0));
                    view = new BoardView(riskView, true, loadedModel);
                    view.addActionListeners(new BoardViewController(loadedModel, view));
                    view.addListSelectionListeners(new PlayerListController(loadedModel, view));
                    view.addListSelectionListeners(new AdjacentListController(loadedModel, view));
                    view.setVisible(true);
                } catch (IOException e1) {
                    System.out.println(e1.getMessage());
                } catch (ClassNotFoundException e1)	{
                    System.out.println(e1.getMessage());
                }
            }
        }

        else if(command.equals("helpButton")){
            model.notifyAllObservers();
            view.clearAllTextFields();
            model.help();
        }

        else if(command.equals("quitButton")){
            System.exit(0);
        }
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
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
        Territory selectedTerritory = null;
        if(model.getActivePlayerIndex() == 0) {
            if (!e.getValueIsAdjusting()) {
                if (view.getIndexOfSelectedPlayerTerritory(0) == -1) {
                } else {
                     selectedTerritory = model.setPlayerTerritorySelection(view.getSelectedPlayerTerritory(0), 0);
                }
            }
        }
        else if(model.getActivePlayerIndex() == 1) {
            if (!e.getValueIsAdjusting()) {
                if (view.getIndexOfSelectedPlayerTerritory(1) == -1) {
                } else {
                    selectedTerritory = model.setPlayerTerritorySelection(view.getSelectedPlayerTerritory(1), 1);
                }
            }
        }
        else if(model.getActivePlayerIndex() == 2) {
            if (!e.getValueIsAdjusting()) {
                if (view.getIndexOfSelectedPlayerTerritory(2) == -1) {
                } else {
                    selectedTerritory = model.setPlayerTerritorySelection(view.getSelectedPlayerTerritory(2), 2);
                }
            }
        }
        else if(model.getActivePlayerIndex() == 3) {
            if (!e.getValueIsAdjusting()) {
                if (view.getIndexOfSelectedPlayerTerritory(3) == -1) {
                } else {
                    selectedTerritory = model.setPlayerTerritorySelection(view.getSelectedPlayerTerritory(3), 3);
                }
            }
        }
        else if(model.getActivePlayerIndex() == 4) {
            if (!e.getValueIsAdjusting()) {
                if (view.getIndexOfSelectedPlayerTerritory(4) == -1) {
                } else {
                    selectedTerritory = model.setPlayerTerritorySelection(view.getSelectedPlayerTerritory(4), 4);
                }
            }
        }
        else if(model.getActivePlayerIndex() == 5) {
            if (!e.getValueIsAdjusting()) {
                if (view.getIndexOfSelectedPlayerTerritory(5) == -1) {
                } else {
                    selectedTerritory = model.setPlayerTerritorySelection(view.getSelectedPlayerTerritory(5), 5);
                }
            }
        }
        //clear all continent selections
        for(int i=0; i<view.getContinentJLists().size(); i++){
            view.getContinentJLists().get(i).clearSelection();
        }
        //highlight selected territory in continent list
        for(int i=0; i<view.getContinentModels().size(); i++){
            for(int j=0; j<view.getContinentModels().get(i).getSize(); j++){
                if(view.getContinentModels().get(i).get(j).equals(selectedTerritory)){
                    view.getContinentJLists().get(i).setSelectedIndex(j);
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
        Territory selectedTerritory = null;
        if (!e.getValueIsAdjusting()) {
            if (view.getIndexOfSelectedPlayerTerritory(6) == -1) {
            }
            else {
                selectedTerritory = model.setPlayerTerritorySelection(view.getSelectedPlayerTerritory(6), 6);
            }
        }
        //highlight selected territory in continent list
        for(int i=0; i<view.getContinentModels().size(); i++){
            for(int j=0; j<view.getContinentModels().get(i).getSize(); j++){
                if(view.getContinentModels().get(i).get(j).equals(selectedTerritory)){
                    view.getContinentJLists().get(i).setSelectedIndex(j);
                }
            }
        }
    }
}
