import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Observable;

/**
 * sets up the menu with new game and quit
 * @author Mmedara Josiah
 * @version 1.0.0
 */
public class RiskView extends JFrame{
    private JPanel mainPanel;
    private JButton newGameButton, quitButton;
    private String newGameButtonCommand = "newGameButton";
    private String quitButtonCommand = "quitButton";

    /**
     * constructs the new game menu
     */
    public RiskView (){
        //initialize mainPanel with grid layout
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        //create buttons for main panel
        newGameButton = new JButton("New Game");
        quitButton = new JButton("Quit");
        setActionCommand();

        //add buttons to main panel
        mainPanel.add(newGameButton);
        mainPanel.add(quitButton);

        add(mainPanel);
        setTitle("Risk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
        setVisible(true);
        toFront();
    }

    /**
     * sets action commands on objects
     */
    public void setActionCommand(){
        newGameButton.setActionCommand(newGameButtonCommand);
        quitButton.setActionCommand(quitButtonCommand);
    }

    /**
     * adds action listeners to objects
     * @param e event
     */
    public void addActionListeners(ActionListener e){
        newGameButton.addActionListener(e);
        quitButton.addActionListener(e);
    }  
}


/** 
 * sets up the menu that asks for number of players
 * @author Tamilore Odunlami
 * @version 1.0
 */
class NumberOfPlayersDialog extends JDialog{
    private JPanel numberOfPlayers;
    private JLabel numberOfPlayersQuestion;
    private JButton twoPlayersButton, threePlayersButton, fourPlayersButton,
            fivePlayersButton, sixPlayersButton, backButton;
    private String twoPlayersButtonCommand = "twoPlayersButton";
    private String threePlayersButtonCommand = "threePlayersButton";
    private String fourPlayersButtonCommand = "fourPlayersButton";
    private String fivePlayersButtonCommand= "fivePlayersButton";
    private String sixPlayersButtonCommand = "sixPlayersButton";
    private String backButtonCommand = "backButton";

    public NumberOfPlayersDialog(RiskView parent, boolean modality){
        super(parent, modality);


        add(numberOfPlayers());
        setTitle("Risk");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
    }

    /** 
     * initialize, setup and return numberOfPlayers panel
     * @return numberOfPlayers panel
     */
    public JPanel numberOfPlayers(){
        //initialize panel with grid layout
        numberOfPlayers = new JPanel();
        numberOfPlayers.setLayout(new FlowLayout());

        //initialize and add label to panel
        numberOfPlayersQuestion = new JLabel("How many players?");
        numberOfPlayers.add(numberOfPlayersQuestion);

        //initialize and add buttons with colour to panel
        twoPlayersButton = new JButton("Two");
        threePlayersButton = new JButton("Three");
        fourPlayersButton = new JButton("Four");
        fivePlayersButton = new JButton("Five");
        sixPlayersButton = new JButton("Six");
        backButton = new JButton("Back");
        setActionCommand();

        numberOfPlayers.add(twoPlayersButton);
        numberOfPlayers.add(threePlayersButton);
        numberOfPlayers.add(fourPlayersButton);
        numberOfPlayers.add(fivePlayersButton);
        numberOfPlayers.add(sixPlayersButton);
        numberOfPlayers.add(backButton);

        return numberOfPlayers;
    }

    /**
     * sets action command on elements
     */
    public void setActionCommand(){
        twoPlayersButton.setActionCommand(twoPlayersButtonCommand);
        threePlayersButton.setActionCommand(threePlayersButtonCommand);
        fourPlayersButton.setActionCommand(fourPlayersButtonCommand);
        fivePlayersButton.setActionCommand(fivePlayersButtonCommand);
        sixPlayersButton.setActionCommand(sixPlayersButtonCommand);
        backButton.setActionCommand(backButtonCommand);
    }

    /**
     * add action listeners
     * @param e event
     */
    public void addActionListeners(ActionListener e){
        twoPlayersButton.addActionListener(e);
        threePlayersButton.addActionListener(e);
        fourPlayersButton.addActionListener(e);
        fivePlayersButton.addActionListener(e);
        sixPlayersButton.addActionListener(e);
        backButton.addActionListener(e);
    }
}

/**
 * sets up the menu that asks players for their names
 * @author Mmedara Josiah
 * @version 1.0.0
 */
class PlayerNamesDialog extends JDialog{
    private JPanel playerNamesPanel;
    private JButton startGameButton, backButton;
    private String startGameButtonCommand = "startGameButton";
    private String backButtonCommand = "backButton";
    private JLabel label;
    private JTextField player1Name, player2Name, player3Name,
            player4Name, player5Name, player6Name;
    private int numberOfPlayers;

    /**
     * Constructs the player names dialog box
     * @param parent is the parent view
     * @param modality allow use of previous windows
     * @param numberOfPlayers number of players in the game
     */
    public PlayerNamesDialog(NumberOfPlayersDialog parent, boolean modality, int numberOfPlayers){
        super(parent, modality);
        this.numberOfPlayers = numberOfPlayers;

        add(playerNamesPanel());
        setTitle("Risk");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
    }

    /**
     * returns the player names panel
     * @return the player names panel
     */
    public JPanel playerNamesPanel(){
        playerNamesPanel = new JPanel();
        playerNamesPanel.setLayout(new FlowLayout());

        label = new JLabel("Input your names");
        playerNamesPanel.add(label);

        player1Name = new JTextField("Player 1");
        player2Name = new JTextField("Player 2");

        playerNamesPanel.add(player1Name);
        playerNamesPanel.add(player2Name);

        if (numberOfPlayers > 2) {
            player3Name = new JTextField("Player 3");
            playerNamesPanel.add(player3Name);
        }
        if (numberOfPlayers > 3) {
            player4Name = new JTextField("Player 4");
            playerNamesPanel.add(player4Name);
        }
        if (numberOfPlayers > 4) {
            player5Name = new JTextField("Player 5");
            playerNamesPanel.add(player5Name);
        }
        if (numberOfPlayers > 5) {
            player6Name = new JTextField("Player 6");
            playerNamesPanel.add(player6Name);
        }

        backButton = new JButton("Back");
        startGameButton = new JButton("Start Game");

        playerNamesPanel.add(backButton);
        playerNamesPanel.add(startGameButton);
        setActionCommand();
        return playerNamesPanel;
    }

    /**
     * sets action commands for the buttons
     */
    public void setActionCommand(){
        backButton.setActionCommand(backButtonCommand);
        startGameButton.setActionCommand(startGameButtonCommand);
    }

    /**
     * adds action listeners to the buttons
     * @param e action event
     */
    public void addActionListeners(ActionListener e){
        backButton.addActionListener(e);
        startGameButton.addActionListener(e);
    }

    /**
     * returns the names of all players
     * @param playerNumber
     * @return
     */
    public String getPlayersNames(int playerNumber){
        if(playerNumber==1) return player1Name.getText();
        else if(playerNumber==2) return player2Name.getText();
        else if(playerNumber==3) return player3Name.getText();
        else if(playerNumber==4) return player4Name.getText();
        else if(playerNumber==5) return player5Name.getText();
        else return player6Name.getText();
    }
}
