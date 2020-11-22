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
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        //create buttons for main panel
        newGameButton = new JButton("New Game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton = new JButton("Quit");
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        numberOfPlayers.setLayout(new BoxLayout(numberOfPlayers, BoxLayout.PAGE_AXIS));

        //initialize and add label to panel
        numberOfPlayersQuestion = new JLabel("How many players?");
        numberOfPlayersQuestion.setAlignmentX(Component.CENTER_ALIGNMENT);
        numberOfPlayers.add(numberOfPlayersQuestion);

        //initialize and add buttons with colour to panel
        twoPlayersButton = new JButton("Two");
        twoPlayersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        threePlayersButton = new JButton("Three");
        threePlayersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        fourPlayersButton = new JButton("Four");
        fourPlayersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        fivePlayersButton = new JButton("Five");
        fivePlayersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sixPlayersButton = new JButton("Six");
        sixPlayersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
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
    private JPanel humanOrAIPanel;
    private JButton startGameButton, backButton;
    private String startGameButtonCommand = "startGameButton";
    private String backButtonCommand = "backButton";
    private JLabel nameLabel, humanOrAILabel;
    private JTextField player1Name, player2Name, player3Name,
            player4Name, player5Name, player6Name;
    private JComboBox player1ComboBox, player2ComboBox, player3ComboBox,
            player4ComboBox, player5ComboBox, player6ComboBox;
    private int numberOfPlayers;
    private String[] humanOrAI = {"Human", "AI"};

    /**
     * Constructs the player names dialog box
     * @param parent is the parent view
     * @param modality allow use of previous windows
     * @param numberOfPlayers number of players in the game
     */
    public PlayerNamesDialog(NumberOfPlayersDialog parent, boolean modality, int numberOfPlayers){
        super(parent, modality);
        this.numberOfPlayers = numberOfPlayers;
        setLayout(new GridLayout(1, 2));

        add(playerNamesPanel());
        add(humanOrAIPanel());
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
        playerNamesPanel.setLayout(new BoxLayout(playerNamesPanel, BoxLayout.PAGE_AXIS));

        nameLabel = new JLabel("Names");
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerNamesPanel.add(nameLabel);

        player1Name = new JTextField("Player 1");
        player1Name.setAlignmentX(Component.CENTER_ALIGNMENT);
        player2Name = new JTextField("Player 2");
        player2Name.setAlignmentX(Component.CENTER_ALIGNMENT);

        playerNamesPanel.add(player1Name);
        playerNamesPanel.add(player2Name);

        if (numberOfPlayers > 2) {
            player3Name = new JTextField("Player 3");
            player3Name.setAlignmentX(Component.CENTER_ALIGNMENT);
            playerNamesPanel.add(player3Name);
        }
        if (numberOfPlayers > 3) {
            player4Name = new JTextField("Player 4");
            player4Name.setAlignmentX(Component.CENTER_ALIGNMENT);
            playerNamesPanel.add(player4Name);
        }
        if (numberOfPlayers > 4) {
            player5Name = new JTextField("Player 5");
            player5Name.setAlignmentX(Component.CENTER_ALIGNMENT);
            playerNamesPanel.add(player5Name);
        }
        if (numberOfPlayers > 5) {
            player6Name = new JTextField("Player 6");
            player6Name.setAlignmentX(Component.CENTER_ALIGNMENT);
            playerNamesPanel.add(player6Name);
        }

        backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerNamesPanel.add(backButton);
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

    public JPanel humanOrAIPanel(){
        humanOrAIPanel = new JPanel();
        humanOrAIPanel.setLayout(new BoxLayout(humanOrAIPanel, BoxLayout.PAGE_AXIS));

        humanOrAILabel = new JLabel("Human/AI?");
        humanOrAILabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        humanOrAIPanel.add(humanOrAILabel);

        player1ComboBox = new JComboBox(humanOrAI);
        player1ComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        player2ComboBox = new JComboBox(humanOrAI);
        player2ComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        humanOrAIPanel.add(player1ComboBox);
        humanOrAIPanel.add(player2ComboBox);

        if (numberOfPlayers > 2) {
            player3ComboBox = new JComboBox(humanOrAI);
            player3ComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
            humanOrAIPanel.add(player3ComboBox);
        }
        if (numberOfPlayers > 3) {
            player4ComboBox = new JComboBox(humanOrAI);
            player4ComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
            humanOrAIPanel.add(player4ComboBox);
        }
        if (numberOfPlayers > 4) {
            player5ComboBox = new JComboBox(humanOrAI);
            player5ComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
            humanOrAIPanel.add(player5ComboBox);
        }
        if (numberOfPlayers > 5) {
            player6ComboBox = new JComboBox(humanOrAI);
            player6ComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
            humanOrAIPanel.add(player6ComboBox);
        }

        startGameButton = new JButton("Start");
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        humanOrAIPanel.add(startGameButton);
        setActionCommand();
        return humanOrAIPanel;
    }

    public String getHumanOrAI(int playerNumber)
    {
        if (playerNumber == 1) return player1ComboBox.getSelectedItem().toString();
        else if (playerNumber == 2) return player2ComboBox.getSelectedItem().toString();
        else if (playerNumber == 3) return player3ComboBox.getSelectedItem().toString();
        else if (playerNumber == 4) return player4ComboBox.getSelectedItem().toString();
        else if (playerNumber == 5) return player5ComboBox.getSelectedItem().toString();
        else return player6ComboBox.getSelectedItem().toString();
    }
}

/**
 * sets up the main game board
 * @author Tamilore Odunlami & Mmedara Josiah
 * @version 1.0
 */
class BoardView extends JDialog {
    //                   left         centre        right
    private JPanel playerInfoPanel, screenPanel, comAdjPanel;
    //panels for each player - in playerInfoPanel
    private JPanel player1Panel, player2Panel, player3Panel,
            player4Panel, player5Panel, player6Panel;
    private JPanel playConPanel, continentsPanel;
    //panels for right panel - comAdjPanel
    private JPanel commandsPanel, adjacenciesPanel;
    private JPanel africaPanel, asiaPanel, australiaPanel, europePanel,
            northAmericaPanel, southAmericaPanel;
    private JTextArea mainTextArea;
    private JScrollPane adjacentScrollPane, screenScrollPane;
    private JScrollPane player1ScrollPane, player2ScrollPane, player3ScrollPane,
            player4ScrollPane, player5ScrollPane, player6ScrollPane;
    private JButton reinforceButton;
    private JButton attackButton;
    private JButton fortifyButton;
    private JButton passButton;
    private JButton helpButton;
    private JButton quitButton;
    private String reinforceButtonCommand = "reinforceButton";
    private String attackButtonCommand = "attackButton";
    private String fortifyButtonCommand = "fortifyButton";
    private String passButtonCommand = "passButton";
    private String helpButtonCommand = "helpButton";
    private String quitButtonCommand = "quitButton";
    private JLabel attackerDiceRollLabel;
    private JLabel defenderDiceRollLabel;
    private JTextField reinforceTextField, fortifyTextField, attackerDiceRollTextField, defenderDiceRollTextField;
    private JList<Territory> player1JList, player2JList, player3JList,
            player4JList, player5JList, player6JList;
    private JList<Territory> africaJList, asiaJList, australiaJList,
            europeJList, northAmericaJList, southAmericaJList;
    private JList<Territory> adjacentJList;
    private AllListModel player1Model, player2Model, player3Model, player4Model,
            player5Model, player6Model, adjacentModel, africaModel, asiaModel,
            australiaModel, europeModel, northAmericaModel, southAmericaModel;
    private ArrayList<JList> allJLists;
    private Observable obs;
    private Game model;
    private DefaultCaret caret;
    private Territory selectedTerritory, selectedAdjacent;

    public BoardView(PlayerNamesDialog parent, boolean modality, Game model)
    {
        super(parent, modality);
        this.model = model;
        allJLists = new ArrayList<>();
        obs = new Observable();

        setLayout(new FlowLayout());
        add(BorderLayout.CENTER, new JScrollPane(playConPanel));

        add(playConPanel());
        add(screenPanel());
        add(continentsPanel());

        setTitle("Risk: Global Domination");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(true);
        pack();
    }

    public JPanel playConPanel(){
        playConPanel = new JPanel();
        playConPanel.setLayout(new GridLayout(2, 1));
        playConPanel.add(playerInfoPanel());
        playConPanel.add(comAdjPanel());
        return playConPanel;
    }

    /**
     * set up playerInfoPanel
     *
     * @return playerInfoPanel
     */
    public JPanel playerInfoPanel()
    {
        //main left panel
        playerInfoPanel = new JPanel();
        playerInfoPanel.setLayout(new GridLayout(1,2));
        playerInfoPanel.setPreferredSize(new Dimension(200, 400));
        if(model.getPlayers().size()>2){
            playerInfoPanel.setLayout(new GridLayout(2, 2));
            playerInfoPanel.setPreferredSize(new Dimension(350, 400));
        }
        if(model.getPlayers().size()>4)
        {
            playerInfoPanel.setLayout(new GridLayout(2, 3));
            playerInfoPanel.setPreferredSize(new Dimension(580, 350));
        }

        playerInfoPanel.add(player1Panel());
        playerInfoPanel.add(player2Panel());
        if(model.getPlayerNames().size()>2) playerInfoPanel.add(player3Panel());
        if(model.getPlayerNames().size()>3) playerInfoPanel.add(player4Panel());
        if(model.getPlayerNames().size()>4) playerInfoPanel.add(player5Panel());
        if(model.getPlayerNames().size()>5) playerInfoPanel.add(player6Panel());

        return playerInfoPanel;
    }

    /**
     * sets up player 1 panel
     *
     * @return player1Panel
     */
    public JPanel player1Panel() {
        //player 1
        player1Panel = new JPanel();
        player1Panel.setLayout(new GridLayout());
        player1Panel.setBorder(BorderFactory.createTitledBorder(model.getPlayerNames().get(0)));
        //set up player 1 model and jlist
        player1Model = new AllListModel(model, "player1");
        model.addObserver(player1Model);
        player1Model.update(obs, "player1");
        player1JList = new JList<>(player1Model);
        player1JList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        player1ScrollPane = new JScrollPane();
        player1ScrollPane.setViewportView(player1JList);
        player1ScrollPane.setPreferredSize(new Dimension(player1JList.getWidth(), player1JList.getHeight()));
        player1JList.setLayoutOrientation(JList.VERTICAL);
        player1JList.setVisibleRowCount(7);
        allJLists.add(player1JList);
        player1Panel.add(player1ScrollPane);
        return player1Panel;
    }

    public JList<Territory> getPlayer1JList(){
        return player1JList;
    }

    /**
     * sets up player 2 panel
     *
     * @return player2Panel
     */
    public JPanel player2Panel() {
        //player 2
        player2Panel = new JPanel();
        player2Panel.setLayout(new GridLayout());
        player2Panel.setBorder(BorderFactory.createTitledBorder(model.getPlayerNames().get(1)));
        //set up player 2 model and jlist
        player2Model = new AllListModel(model, "player2");
        model.addObserver(player2Model);
        player2Model.update(obs, "player2");
        player2JList = new JList<>(player2Model);
        player2JList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        player2ScrollPane = new JScrollPane();
        player2ScrollPane.setViewportView(player2JList);
        player2ScrollPane.setSize(25, 40);
        player2JList.setLayoutOrientation(JList.VERTICAL);
        player2JList.setVisibleRowCount(7);
        allJLists.add(player2JList);
        player2Panel.add(player2ScrollPane);
        return player2Panel;
    }

    public JList<Territory> getPlayer2JList(){
        return player2JList;
    }

    /**
     * sets up player 3 panel
     *
     * @return player3Panel
     */
    public JPanel player3Panel(){
        //player 3
        player3Panel = new JPanel();
        player3Panel.setLayout(new GridLayout());
        player3Panel.setBorder(BorderFactory.createTitledBorder(model.getPlayerNames().get(2)));
        player3Model = new AllListModel(model, "player3");
        model.addObserver(player3Model);
        player3Model.update(obs, "player3");
        player3JList = new JList<>(player3Model);
        player3JList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        player3ScrollPane = new JScrollPane();
        player3ScrollPane.setViewportView(player3JList);
        player3ScrollPane.setSize(25, 40);
        player3JList.setLayoutOrientation(JList.VERTICAL);
        player3JList.setVisibleRowCount(7);
        allJLists.add(player3JList);
        player3Panel.add(player3ScrollPane);
        return player3Panel;
    }

    public JList<Territory> getPlayer3JList(){
        return player3JList;
    }

    /**
     * sets up player 4 panel
     *
     * @return player4Panel
     */
    public JPanel player4Panel(){
        //player 4
        player4Panel = new JPanel();
        player4Panel.setLayout(new GridLayout());
        player4Panel.setBorder(BorderFactory.createTitledBorder(model.getPlayerNames().get(3)));
        player4Model = new AllListModel(model, "player4");
        model.addObserver(player4Model);
        player4Model.update(obs, "player4");
        player4JList = new JList<>(player4Model);
        player4JList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        player4ScrollPane = new JScrollPane();
        player4ScrollPane.setViewportView(player4JList);
        player4ScrollPane.setSize(25, 40);
        player4JList.setLayoutOrientation(JList.VERTICAL);
        player4JList.setVisibleRowCount(7);
        allJLists.add(player4JList);
        player4Panel.add(player4ScrollPane);
        return player4Panel;
    }

    public JList<Territory> getPlayer4JList(){
        return player4JList;
    }

    /**
     * sets up player 5 panel
     *
     * @return player5Panel
     */
    public JPanel player5Panel(){
        //player 5
        player5Panel = new JPanel();
        player5Panel.setLayout(new GridLayout());
        player5Panel.setBorder(BorderFactory.createTitledBorder(model.getPlayerNames().get(4)));
        player5Model = new AllListModel(model, "player5");
        model.addObserver(player5Model);
        player5Model.update(obs, "player5");
        player5JList = new JList<>(player5Model);
        player5JList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        player5ScrollPane = new JScrollPane();
        player5ScrollPane.setViewportView(player5JList);
        player5ScrollPane.setSize(25, 40);
        player5JList.setLayoutOrientation(JList.VERTICAL);
        player5JList.setVisibleRowCount(7);
        allJLists.add(player5JList);
        player5Panel.add(player5ScrollPane);
        return player5Panel;
    }

    public JList<Territory> getPlayer5JList(){
        return player5JList;
    }

    /**
     * sets up player 6 panel
     *
     * @return player6Panel
     */
    public JPanel player6Panel(){
        //player 6
        player6Panel = new JPanel();
        player6Panel.setLayout(new GridLayout());
        player6Panel.setBorder(BorderFactory.createTitledBorder(model.getPlayerNames().get(5)));
        player6Model = new AllListModel(model, "player6");
        model.addObserver(player6Model);
        player6Model.update(obs, "player6");
        player6JList = new JList<>(player6Model);
        player6JList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        player6ScrollPane = new JScrollPane();
        player6ScrollPane.setViewportView(player6JList);
        player6ScrollPane.setSize(25, 40);
        player6JList.setLayoutOrientation(JList.VERTICAL);
        player6JList.setVisibleRowCount(7);
        allJLists.add(player6JList);
        player6Panel.add(player6ScrollPane);
        return player6Panel;
    }

    public JList<Territory> getPlayer6JList(){
        return player6JList;
    }

    /**
     * continents panel
     * @return
     */
    public JPanel continentsPanel(){
        continentsPanel = new JPanel();
        continentsPanel.setLayout(new GridLayout(3, 2));
        continentsPanel.setPreferredSize(new Dimension(350, 700));

        continentsPanel.add(africaPanel());
        continentsPanel.add(asiaPanel());
        continentsPanel.add(australiaPanel());
        continentsPanel.add(europePanel());
        continentsPanel.add(northAmericaPanel());
        continentsPanel.add(southAmericaPanel());

        return continentsPanel;
    }

    /**
     * africa panel
     * @return
     */
    public JPanel africaPanel(){
        africaPanel = new JPanel();
        africaPanel.setLayout(new GridLayout());
        africaPanel.setBorder(BorderFactory.createTitledBorder("Africa"));

        africaModel = new AllListModel(model, "africa");
        model.addObserver(africaModel);
        africaModel.update(obs, "africa");
        africaJList = new JList<>(africaModel);
        africaJList.setBackground(Color.lightGray);
        africaPanel.add(africaJList);
        return africaPanel;
    }

    /**
     * asia panel
     * @return
     */
    public JPanel asiaPanel(){
        asiaPanel = new JPanel();
        asiaPanel.setLayout(new GridLayout());
        asiaPanel.setBorder(BorderFactory.createTitledBorder("Asia"));

        asiaModel = new AllListModel(model, "asia");
        model.addObserver(asiaModel);
        asiaModel.update(obs, "asia");
        asiaJList = new JList<>(asiaModel);
        asiaJList.setBackground(Color.lightGray);
        asiaPanel.add(asiaJList);
        return asiaPanel;
    }

    /**
     * australia panel
     * @return
     */
    public JPanel australiaPanel(){
        australiaPanel = new JPanel();
        australiaPanel.setLayout(new GridLayout());
        australiaPanel.setBorder(BorderFactory.createTitledBorder("Australia"));

        australiaModel = new AllListModel(model, "australia");
        model.addObserver(australiaModel);
        australiaModel.update(obs, "australia");
        australiaJList = new JList<>(australiaModel);
        australiaJList.setBackground(Color.lightGray);
        australiaPanel.add(australiaJList);
        return australiaPanel;
    }

    /**
     * europe panel
     * @return
     */
    public JPanel europePanel(){
        europePanel = new JPanel();
        europePanel.setLayout(new GridLayout());
        europePanel.setBorder(BorderFactory.createTitledBorder("Europe"));

        europeModel = new AllListModel(model, "europe");
        model.addObserver(europeModel);
        europeModel.update(obs, "europe");
        europeJList = new JList<>(europeModel);
        europeJList.setBackground(Color.lightGray);
        europePanel.add(europeJList);
        return europePanel;
    }

    /**
     * northAmerica panel
     * @return
     */
    public JPanel northAmericaPanel(){
        northAmericaPanel = new JPanel();
        northAmericaPanel.setLayout(new GridLayout());
        northAmericaPanel.setBorder(BorderFactory.createTitledBorder("North America"));

        northAmericaModel = new AllListModel(model, "northAmerica");
        model.addObserver(northAmericaModel);
        northAmericaModel.update(obs, "northAmerica");
        northAmericaJList = new JList<>(northAmericaModel);
        northAmericaJList.setBackground(Color.lightGray);
        northAmericaPanel.add(northAmericaJList);
        return northAmericaPanel;
    }

    /**
     * southAmerica panel
     * @return
     */
    public JPanel southAmericaPanel(){
        southAmericaPanel = new JPanel();
        southAmericaPanel.setLayout(new GridLayout());
        southAmericaPanel.setBorder(BorderFactory.createTitledBorder("South America"));

        southAmericaModel = new AllListModel(model, "southAmerica");
        model.addObserver(southAmericaModel);
        southAmericaModel.update(obs, "southAmerica");
        southAmericaJList = new JList<>(southAmericaModel);
        southAmericaJList.setBackground(Color.lightGray);
        southAmericaPanel.add(southAmericaJList);
        return southAmericaPanel;
    }

    /**
     * set up screenPanel
     *
     * @return screenPanel
     */
    public JPanel screenPanel() {
        screenPanel = new JPanel();
        screenPanel.setLayout(new GridLayout());
        screenPanel.setPreferredSize(new Dimension(550, 800));
        if(model.getNoOfPlayers()>4) screenPanel.setPreferredSize(new Dimension(450, 800));
        mainTextArea = new JTextArea();

        System.setOut(new PrintStream(new TextAreaOutputStream(mainTextArea)));
        mainTextArea.setFocusable(false);
        mainTextArea.setLineWrap(true);
        mainTextArea.setWrapStyleWord(true);
        caret = (DefaultCaret)mainTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        screenScrollPane = new JScrollPane(mainTextArea);

        screenPanel.add(screenScrollPane);
        return screenPanel;
    }

    /**
     * set up comAdjPanel
     *
     * @return comAdjPanel
     */
    public JPanel comAdjPanel()
    {
        comAdjPanel = new JPanel();
        comAdjPanel.setLayout(new GridLayout(1, 2));
        //add all elements to comAdjPanel
        comAdjPanel.add(commandsPanel());
        comAdjPanel.add(adjacenciesPanel());
        return comAdjPanel;
    }

    public JPanel commandsPanel(){
        //commands panel - up
        commandsPanel = new JPanel();
        commandsPanel.setLayout(new GridLayout(6, 2));
        //buttons for commands panel
        reinforceButton = new JButton("Reinforce");
        attackButton = new JButton("Attack");
        fortifyButton = new JButton("Fortify");
        passButton = new JButton("Pass");
        helpButton = new JButton("Help");
        quitButton = new JButton("Quit");

        //labels
        attackerDiceRollLabel = new JLabel("Attacker Roll");
        defenderDiceRollLabel = new JLabel("Defender Roll");

        //text field for the roll dice button
        reinforceTextField = new JTextField("0");
        fortifyTextField = new JTextField("0");
        attackerDiceRollTextField = new JTextField("0");
        defenderDiceRollTextField = new JTextField("0");
        setActionCommand();

        commandsPanel.add(reinforceButton);
        commandsPanel.add(reinforceTextField);
        commandsPanel.add(attackerDiceRollLabel);
        commandsPanel.add(attackerDiceRollTextField);
        commandsPanel.add(defenderDiceRollLabel);
        commandsPanel.add(defenderDiceRollTextField);
        commandsPanel.add(attackButton);
        commandsPanel.add(passButton);
        commandsPanel.add(fortifyButton);
        commandsPanel.add(fortifyTextField);
        commandsPanel.add(helpButton);
        commandsPanel.add(quitButton);

        return commandsPanel;
    }

    public void setAttackerDiceRollTextField(String s){
        attackerDiceRollTextField.setText(s);
    }
    public void setDefenderDiceRollTextField(String s){
        defenderDiceRollTextField.setText(s);
    }

    public int getReinforcementTextField(){
        return Integer.parseInt(reinforceTextField.getText());
    }

    public int getFortifyTextField(){
        return Integer.parseInt(fortifyTextField.getText());
    }

    /**
     * return number in attacker rolls text field
     * @return
     */
    public int getNumberOfAttackerDiceRolls(){
        return Integer.parseInt(attackerDiceRollTextField.getText());
    }

    /**
     * return number in defender rolls text field
     * @return
     */
    public int getNumberOfDefenderDiceRolls(){
        return Integer.parseInt(defenderDiceRollTextField.getText());
    }

    /**
     * clear all the text fields
     */
    public void clearAllTextFields(){
        reinforceTextField.setText("0");
        fortifyTextField.setText("0");
        attackerDiceRollTextField.setText("0");
        defenderDiceRollTextField.setText("0");
    }

    public JPanel adjacenciesPanel(){
        //adjacencies panel - down
        adjacenciesPanel = new JPanel();
        //adjacenciesPanel.setSize(400, 400);
        adjacenciesPanel.setBorder(BorderFactory.createTitledBorder("Adjacencies"));
        adjacentModel = new AllListModel(model, "adjacent");
        model.addObserver(adjacentModel);
        adjacentJList = new JList<>(adjacentModel);
        adjacentJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        allJLists.add(adjacentJList);
        adjacenciesPanel.add(adjacentJList);
        return adjacenciesPanel;
    }

    public JList<Territory> getAdjacentJList(){
        return adjacentJList;
    }

    /**
     * set action command
     */
    public void setActionCommand()
    {
        reinforceButton.setActionCommand(reinforceButtonCommand);
        attackButton.setActionCommand(attackButtonCommand);
        passButton.setActionCommand(passButtonCommand);
        fortifyButton.setActionCommand(fortifyButtonCommand);
        helpButton.setActionCommand(helpButtonCommand);
        quitButton.setActionCommand(quitButtonCommand);
    }

    /**
     * add action listener
     *
     * @param e button event
     */
    public void addActionListeners(ActionListener e)
    {
        reinforceButton.addActionListener(e);
        attackButton.addActionListener(e);
        fortifyButton.addActionListener(e);
        passButton.addActionListener(e);
        helpButton.addActionListener(e);
        quitButton.addActionListener(e);
    }

    /**
     * add list selection listener
     * @param e
     */
    public void addListSelectionListeners(ListSelectionListener e){
        adjacentJList.addListSelectionListener(e);
        player1JList.addListSelectionListener(e);
        player2JList.addListSelectionListener(e);
        if(model.getPlayers().size()>2) player3JList.addListSelectionListener(e);
        if(model.getPlayers().size()>3) player4JList.addListSelectionListener(e);
        if(model.getPlayers().size()>4) player5JList.addListSelectionListener(e);
        if(model.getPlayers().size()>5) player6JList.addListSelectionListener(e);
    }

    /**
     * get selected index for all jlists
     * @return
     */
    public int getIndexOfSelectedPlayerTerritory(int n){
        int selectedIndex = -1;
        if(n==1) selectedIndex = player1JList.getSelectedIndex();
        else if(n==2)selectedIndex = player2JList.getSelectedIndex();
        else if(n==3) selectedIndex = player3JList.getSelectedIndex();
        else if(n==4) selectedIndex = player4JList.getSelectedIndex();
        else if(n==5) selectedIndex = player5JList.getSelectedIndex();
        else if(n==6) selectedIndex = player6JList.getSelectedIndex();
        else if(n==0) selectedIndex = adjacentJList.getSelectedIndex();
        return selectedIndex;
    }

    /**
     * get selected territories
     * @return
     */
    public Territory getSelectedPlayerTerritory(int n){
        if(n==1) {
            System.out.println(player1JList.getSelectedValue().getTerritoryName() +
                    " selected by " + model.getPlayers().get(0).getPlayerName());
            selectedTerritory = player1JList.getSelectedValue();
            return selectedTerritory;
        }
        else if(n==2) {
            System.out.println(player2JList.getSelectedValue().getTerritoryName() +
                    " selected by " + model.getPlayers().get(1).getPlayerName());
            selectedTerritory = player2JList.getSelectedValue();
            return selectedTerritory;
        }
        else if(n==3) {
            System.out.println(player3JList.getSelectedValue().getTerritoryName() +
                    " selected by " + model.getPlayers().get(2).getPlayerName());
            selectedTerritory = player3JList.getSelectedValue();
            return selectedTerritory;
        }
        else if(n==4) {
            System.out.println(player4JList.getSelectedValue().getTerritoryName() +
                    " selected by " + model.getPlayers().get(3).getPlayerName());
            selectedTerritory = player4JList.getSelectedValue();
            return selectedTerritory;
        }
        else if(n==5) {
            System.out.println(player5JList.getSelectedValue().getTerritoryName() +
                    " selected by " + model.getPlayers().get(4).getPlayerName());
            selectedTerritory = player5JList.getSelectedValue();
            return selectedTerritory;
        }
        else if(n==6) {
            System.out.println(player6JList.getSelectedValue().getTerritoryName() +
                    " selected by " + model.getPlayers().get(5).getPlayerName());
            selectedTerritory = player6JList.getSelectedValue();
            return selectedTerritory;
        }
        else if(n==0){
                System.out.println(adjacentJList.getSelectedValue().getTerritoryName() +
                        " selected by " + model.getPlayers().get(model.getActivePlayerIndex()).getPlayerName());
                selectedAdjacent = adjacentJList.getSelectedValue();
                return selectedAdjacent;
        }
        return null;
    }

    public Territory getSelectedTerritory(){
        return selectedTerritory;
    }

    public Territory getSelectedAdjacent(){
        return selectedAdjacent;
    }

    /**
     * returns all player jlists
     * @return all player jlists
     */
    public ArrayList<JList> getAllJLists(){
        return allJLists;
    }
}
