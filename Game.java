import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Implements the game of risk
 *
 */
public class Game{

    private Board board;
    private Deck deck;
    private Player activePlayer;
    private Parser parser;
    private Dice dice;
    private Scanner reader;
    private Random random;

    private boolean deployPhase;
    private boolean deployed;
    private static boolean correctPlayersNumber;

    private int activePlayerIndex;
    private int playerCount;
    private int attacksWon;
    private int noOfPlayers;
    private int r;

    private ArrayList<Player> players;
    private ArrayList<String> playerNames;

    /**
     *This is the constructor for the RiskModel object.
     **/
    public Game() {
    }

    /**
     * Initialize the game
     * @param playerNames is an ArrayList of the player names.
     **/
    public void init(ArrayList<String> playerNames) {
        dice = new Dice();
        board = new Board();

        reader = new Scanner(System.in);
        deployed = false;
        deployPhase = false;

        players = new ArrayList<>();
        parser = new Parser();

        playerCount = 0;
        activePlayerIndex = -1;

        this.playerNames = playerNames;
        System.out.println("Filling up the deck...");
        deck = new Deck(board.getAllTerritories());

    }

    /**
     * Starts the game and prints out welcome messages.
     **/
    public void startGame() {
        ArrayList<String> playerNamess = new ArrayList<>();
        correctPlayersNumber = false;
        int playerCount = 0;
        //request and store number of players
        Scanner playerNumber = new Scanner(System.in);
        System.out.println("Welcome to Risk: Global Domination \n" +
                "How many players(2-6) would be playing?");
        while (correctPlayersNumber == false)
        {
            /*if((playerNumber.nextInt()) instanceof int){
                //don't allow strings through
            }*/
            playerCount = playerNumber.nextInt();
            if (playerCount > 1 && playerCount < 7)
            {
                setNoOfPlayers(playerCount);
                correctPlayersNumber = true;
            }
            else
            {
                System.out.println("This game is designed for 2-6 players\n" +
                        "How many players(2-6) would be playing?");
            }
        }

        //request and store player names in arrayList
        for(int i = 0; i<playerCount; i++){
            Scanner pName = new Scanner(System.in);
            System.out.println("Player " + (i+1) + " - Type in your name.");
            playerNamess.add(pName.nextLine());
        }
        init(playerNamess);
        createPlayer();
        autoDeploy();
        System.out.println("Here is the order of turns:");
        //print statement telling players possible command and what each does
        for (int i = 0; i < players.size(); i++) {
            System.out.println((i + 1) + ": " + players.get(i).getPlayerName());
        }
        help();
        nextPlayer();
    }

    /**
     * Create players
     */
    public void createPlayer(){
        System.out.println("Preparing players...");
        for (int i = 0; i < playerNames.size(); i++) {
            players.add(new Player(50 - (playerNames.size() * 5), playerNames.get(i)));
        }
    }

    /**
     * Sets the number of players.
     * @param noOfPlayers is an integer input by the player
     **/
    public void setNoOfPlayers(int noOfPlayers) {
        this.noOfPlayers = noOfPlayers;
    }

    /**
     * Allocates random countries to each player
     * and random armies to each country
     * allocates a random number of armies to each country
     */
    public void autoDeploy(){
        deployed = false;
        Player player = players.get(0);
        System.out.println("Starting Auto Deployment...");
        Territory[] holdTerritories = board.getAllTerritories();
        random = new Random();

        for(int i = 0; i<holdTerritories.length; i++){
            r = random.nextInt((holdTerritories.length - i));
            if(players.size() == 2) {player = players.get(i/21);}
            else if(players.size() == 3) {player = players.get(i/14);}
            else if(players.size() == 4) {player = players.get(i/12);}
            else if(players.size() == 5) {player = players.get(i/10);}
            else if(players.size() == 6) {player = players.get(i/7);}

            if(player.getTotalTroops()>0)
            {
                Territory territory = holdTerritories[r];
                //number of armies deployed per country depends on number of armies left
                int a = 1 + random.nextInt((player.getTotalTroops() / 5) + 1);
                territory.setTerritoryOccupant(player);
                player.addTerritories(territory);
                player.sendInfantry(territory, a);
                System.out.println(player.getPlayerName() + " now has " + territory.getTotalTroops() + " troops deployed in " +
                        territory.getTerritoryName());
                player.removeInfantry(a);

                //move already allocated country to the end
                for (int j = r; j < holdTerritories.length - 1; j++)
                {
                    holdTerritories[j] = holdTerritories[j+1];
                }
            }
            else{
                System.out.println(player.getPlayerName() + ": Your armies have been randomly deployed");
                i++;
            }
        }
        deployed = true;
        System.out.println("Auto deployment complete...");
    }

    /**
     * Handles turn transitions
     **/
    public void nextPlayer() {
        //the game is not yet over
        if (players.size() > 1)
        {
            if (deployed == true)
            {
                activePlayerIndex++;
                //loop back to first player if necessary
                if (activePlayerIndex >= players.size())
                {
                    activePlayerIndex = 0;
                }
                //select a card
                activePlayer = players.get(activePlayerIndex);
                System.out.println("\n /////" + activePlayer.getPlayerName().toUpperCase() + "/////");

                for(int i=0; i<attacksWon; i++)
                {
                    activePlayer.addCard(deck.selectCard());
                }
                //adds troops based on how many territories are owned
                if (activePlayer.getTerritories().size() < 12)
                {
                    activePlayer.addInfantry(3);
                }
                else
                {
                    activePlayer.addInfantry(activePlayer.getTerritories().size() / 3);
                }
                System.out.println("It is now " + activePlayer.getPlayerName() +
                        "'s turn\nYou have " + activePlayer.getTotalTroops() + " troops left.");
            }
        }
    }

    /**
     * processes different commands from the user
     * @param command is the command put in by the user
     * @return true is user wishes to quit
     */
    public boolean processCommand(Command command){
        boolean quit = false;

        String commandWord = command.getFirstWord();
        String secondWord = command.getSecondWord();

        if(!command.isUnknown())
        {
            if (commandWord.equals("state"))
            {
                printState();
            } else if (commandWord.equals("pass"))
            {
                nextPlayer();
            } else if (commandWord.equals("quit"))
            {
                quit = quit(command);
            } else if (commandWord.equals("adjacent"))
            {
                adjacent(command);
            }
            else if (commandWord.equals("attack")){
                processAttackRequest(command);
            }
            else if (commandWord.equals("help")){
                help();
            }
        }
        else
        {
            System.out.println("This is a serious game. Please input a correct command");
        }
        return quit;
    }

    /**
     * processes the quit command
     * @param command is the quit command being processed
     * @return true is user says quit
     */
    public boolean quit(Command command)
    {
        if(command.hasSecondWord())
        {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Processes the attack from command
     * @param command is the attack from command being processed
     */
    public void adjacent(Command command)
    {
        String thirdWord = command.getThirdWord();
        String fourthWord = command.getFourthWord();

        if (thirdWord != null && fourthWord == null)
        {
            if (board.isTerritory(thirdWord))
            {
                Territory territory = board.getTerritory(thirdWord);
                System.out.println("Attack:");
                for (int i = 0; i < territory.getAdjacencies().length; i++)
                {
                    System.out.println(territory.getAdjacencies()[i].getTerritoryName());
                }
                System.out.println("From " + territory + "\n");
            } else if (thirdWord != null && fourthWord != null)
            {
                String completeWord = thirdWord + " " + fourthWord;
                if (board.isTerritory(completeWord))
                {
                    Territory territory = board.getTerritory(completeWord);
                    System.out.println("Attack:");
                    for (int i = 0; i < territory.getAdjacencies().length; i++)
                    {
                        System.out.println(territory.getAdjacencies()[i].getTerritoryName());
                    }
                    System.out.println("From " + territory + "\n");
                }
            }
        }
    }

    public void processAttackRequest (Command command){
        //ask user to type in "attack countryA from countryB"
        String secondWord = command.getSecondWord();
        String thirdWord = command.getThirdWord();
        String fourthWord = command.getFourthWord();
        String fifthWord = command.getFifthWord();
        String sixthWord = command.getSixthWord();

        int rollNo;
        System.out.print("How many times do you want to roll the dice? > ");
        rollNo = reader.nextInt();

        if (thirdWord.equals("from"))
        {
            //if input is - "attack CountryA from CountryB"
            //wordIndex         1       2      3      4
            if (command.hasFourthWord())
            {
                if (!command.hasFifthWord())
                {
                    if (!command.hasSixthWord())
                    {
                        if (board.isTerritory(secondWord))
                        {
                            if(board.isTerritory(fourthWord))
                            {
                                Territory attacker = board.getTerritory(fourthWord);
                                Territory defender = board.getTerritory(secondWord);
                                System.out.println(attacker.getTerritoryOccupant().getPlayerName() + "You are now attacking " +
                                        secondWord + " from " + fourthWord);
                                attacker.Attack(defender, rollNo);
                            }
                        }
                    }
                }
            }

            //if input is - "attack CountryA from CountryB1 CountryB2"
            //eg -          "attack Egypt from New Zealand"
            //wordIndex         1     2     3   4     5
            else if (command.hasFourthWord())
            {
                if(command.hasFifthWord())
                {
                    if(!command.hasSixthWord())
                    {
                        String completeFourthWord = fourthWord + " " + fifthWord;
                        if (board.isTerritory(secondWord)){
                            if(board.isTerritory(completeFourthWord))
                            {
                                Territory attacker = board.getTerritory(completeFourthWord);
                                Territory defender = board.getTerritory(secondWord);
                                attacker.Attack(defender, rollNo);
                                System.out.println(attacker.getTerritoryOccupant().getPlayerName() + " is now attacking " +
                                        secondWord + " from " + completeFourthWord);
                            }
                        }
                    }
                }
            }

            //if country names are wrong
            else
            {
                help();
            }
        }

        if (fourthWord.equals("from"))
        {
            //if input is - "attack CountryA1 CountryA2 from CountryB"
            //eg -          "attack New Zealand from Egypt"
            //wordIndex         1    2     3      4    5
            if (command.hasSecondWord())
            {
                if(command.hasThirdWord())
                {
                    if (command.hasFifthWord())
                    {
                        if (!command.hasSixthWord())
                        {
                            String completeSecondWord = secondWord + " " + thirdWord;
                            if (board.isTerritory(completeSecondWord))
                            {
                                if (board.isTerritory(fifthWord))
                                {
                                    Territory attacker = board.getTerritory(fifthWord);
                                    Territory defender = board.getTerritory(completeSecondWord);
                                    attacker.Attack(defender, rollNo);
                                    System.out.println("You are now attacking " + completeSecondWord + " from " + fifthWord);
                                }
                            }
                        }
                    }
                }

            }
            //if input is - "attack CountryA1 CountryA2 from CountryB1 CountryB2"
            //eg -          "attack New Zealand from North Korea"
            //wordIndex         1    2     3      4    5     6
            else if(command.hasSecondWord())
            {
                if(command.hasThirdWord())
                {
                    if (command.hasFifthWord())
                    {
                        if (command.hasSixthWord())
                        {
                            String completeSecondWord = secondWord + " " + thirdWord;
                            String completeFifthWord = fifthWord + " " + sixthWord;
                            if (board.isTerritory(completeSecondWord))
                            {
                                if (board.isTerritory(completeFifthWord))
                                {
                                    Territory attacker = board.getTerritory(completeFifthWord);
                                    Territory defender = board.getTerritory(completeSecondWord);
                                    attacker.Attack(defender, rollNo);
                                    System.out.println("You are now attacking " + completeSecondWord + " from " + completeFifthWord);
                                }
                            }
                        }
                    }
                }

            }
            else
            {
                help();
            }
        }
    }

    /**
     * prints the state of the game
     */
    public void printState () {
        for (int i = 0; i < board.getAllTerritories().length; i++)
        {
            System.out.println(board.getAllTerritories()[i].toString());
        }
    }

    /**
     * Prints instructions to the user
     */
    public void help(){
        System.out.println("\n--You can input the following command words while playing:\n" +
                "> state - Shows which player is in which territory and with how many troops\n" +
                "> pass -  Gives up a player's turn and the next player can go\n" +
                "> adjacent - Shows all the territories that can be attacked from each territory\n" +
                "> attack TerritoryA from TerritoryB - Executes an attack on TerritoryA from TerritoryB\n" +
                "  ^^^^^^^^^^^^^^^^ - You must be an occupant of TerritoryB ^^^^^^^^^^^^^^^^^^^^^^^\n" +
                "                   - You must be not be an occupant of TerritoryA\n" +
                "                   - TerritoryA must be adjacent to TerritoryB\n" +
                "                   - Territory names must begin with a capital letters\n" +
                "                   - (eg. Egypt, North America, New Guinea, Congo)\n" +
                "> quit - Ends the game\n" +
                "> help - Prints this help statement anytime during the game\n");
    }

    /**
     * causes the game to begin playing
     */
    public void play () {
        startGame();
        boolean finished = false;
        while (!finished)
        { Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Good bye.");
    }
}
