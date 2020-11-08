import java.util.*;

/**
 *
 * Implements the game of risk
 *
 */
public class Game extends Observable
{
    private Board board;
    private Deck deck;
    private Player activePlayer;
    private Dice dice;
    private Random random;
    private Territory selectedTerritory;

    private boolean load;
    private boolean deployed;
    private boolean hasAttacked;

    private int activePlayerIndex;
    private int attacksWon;
    private int noOfPlayers;
    private int r;
    private int attackerLosses;
    private int defenderLosses;

    private int[] attackerDiceRollResults;
    private int[] defenderDiceRollResults;

    private ArrayList<Player> players;
    private ArrayList<String> playerNames;
    private ArrayList<Territory> territoriesList;
    private ArrayList<Territory> continentTerritoriesList;

    /**
     *This is the constructor for the RiskModel object.
     **/
    public Game() {
    }

    /**
     * Initialize the game
     **/
    public boolean init(ArrayList<String> playerNames) {
        load = false;
        board = new Board();

        deployed = false;
        load = false;
        hasAttacked = false;

        players = new ArrayList<>();

        noOfPlayers = 0;
        activePlayerIndex = -1;


        System.out.println("Filling up the deck...");
        deck = new Deck(board.getAllTerritories());
        this.playerNames = playerNames;
        createPlayer();
        autoDeploy();

        return true;
    }

    /**
     * Starts the game and prints out welcome messages.
     **/
    public void startGame() {
        System.out.println("Player turns:");
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
        if(playerNames.size()<3){
            for (int i = 0; i < playerNames.size(); i++) {
                players.add(new Player(50, playerNames.get(i)));
            }
        }
        else {
            for (int i = 0; i < playerNames.size(); i++) {
                players.add(new Player(50 - (playerNames.size() * 5), playerNames.get(i)));
            }
        }
        this.noOfPlayers = players.size();
    }

    /**
     * Sets the number of players.
     * @param noOfPlayers is an integer input by the player
     **/
    public void setNoOfPlayers(int noOfPlayers) {
        this.noOfPlayers = noOfPlayers;
    }

    public int getNoOfPlayers(){
        return noOfPlayers;
    }

    public ArrayList<String> getPlayerNames(){
        return playerNames;
    }

    public ArrayList<Player> getPlayers(){
        return players;
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
            if(players.size() == 2) player = players.get(i/21);
            else if(players.size() == 3) player = players.get(i/14);
            else if(players.size() == 4) player = players.get(i/11);
            else if(players.size() == 5) player = players.get(i/9);
            else if(players.size() == 6) player = players.get(i/7);

            if(player.getTotalTroops()>0)
            {
                Territory territory = holdTerritories[r];

                int a = 0;
                //number of armies deployed per country depends on number of armies left
                if(players.size() == 2) {a = 1 + random.nextInt((player.getTotalTroops() / 7) + 1);}
                else if(players.size() == 3) {a = 1 + random.nextInt((player.getTotalTroops() / 5) + 1);}
                else if(players.size() == 4) {a = 1 + random.nextInt((player.getTotalTroops() / 4) + 1);}
                else if(players.size() == 5) {a = 1 + random.nextInt((player.getTotalTroops() / 4) + 1);}
                else if(players.size() == 6) {a = 1 + random.nextInt((player.getTotalTroops() / 3) + 1);}

                territory.setTerritoryOccupant(player);
                player.addTerritories(territory);
                player.sendInfantry(territory, a);
                System.out.println(player.getPlayerName() + " now has " + territory.getTotalTroops() + " troops deployed in " +
                        territory.getTerritoryName());

                //move already allocated territory to the end
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
                for(int i=0; i<board.getAllContinents().length; i++){
                    if(activePlayer.getTerritories().containsAll(board.getAllContinents()[i].getIncludedTerritories())){
                        activePlayer.addInfantry(board.getAllContinents()[i].getBonusTroops());
                        System.out.println(activePlayer.getPlayerName() + " has gained " + board.getAllContinents()[i].getBonusTroops() +
                                "bonus troops for controlling " + board.getAllContinents()[i].getName() + ".");
                    }
                }
                System.out.println("It is now " + activePlayer.getPlayerName() +
                        "'s turn\nYou have " + activePlayer.getTotalTroops() + " troops left.");
                notifyAllObservers();
            }
        }
    }

    /**
     * attacker attacks defender
     * @param attacker is the attacking territory
     * @param defender is the territory being attacked
     * @param numberOfAttackerDiceRolls how many times attacker will roll the dice
     * @param numberOfDefenderDiceRolls how many times defender will roll the dice
     */
    public void attack(Territory attacker, Territory defender,
                       int numberOfAttackerDiceRolls, int numberOfDefenderDiceRolls){
        //stops player from attacking his territory
        if(attacker.getTerritoryOccupant()!=defender.getTerritoryOccupant()){
            //defender must be adjacent to attacker
            if(Arrays.asList(attacker.getAdjacencies()).contains(defender)){
                //if defending territory is not empty
                if(defender.getTotalTroops()>0) {
                    System.out.println("\n" + attacker.getTerritoryName() + " is about to attack " + defender.getTerritoryName());
                    dice = new Dice();
                    attackerLosses = 0;
                    defenderLosses = 0;

                    //roll results are ordered from highest (0) to lowest
                    if (numberOfAttackerDiceRolls > 0 &&
                            numberOfAttackerDiceRolls < 4 && numberOfDefenderDiceRolls > 0 && numberOfDefenderDiceRolls < 4) {
                        attackerDiceRollResults = dice.rollDice(numberOfAttackerDiceRolls);
                        System.out.println(activePlayer.getPlayerName() + " has rolled the dice " +
                                numberOfAttackerDiceRolls + " times.");
                        defenderDiceRollResults = dice.rollDice(numberOfDefenderDiceRolls);
                        System.out.println(defender.getTerritoryOccupant().getPlayerName() +
                                " has rolled the dice " + numberOfDefenderDiceRolls + " times.");

                        //compare highest results
                        if (attackerDiceRollResults[0] > defenderDiceRollResults[0]) defenderLosses++;
                        else if (attackerDiceRollResults[0] < defenderDiceRollResults[0]) attackerLosses++;

                        //compare second highest results if both attacker and defender have
                        if (numberOfAttackerDiceRolls > 1 && numberOfDefenderDiceRolls > 1) {
                            if (attackerDiceRollResults[1] > defenderDiceRollResults[1]) defenderLosses++;
                            else if (attackerDiceRollResults[1] < defenderDiceRollResults[1]) attackerLosses++;
                        }

                        //Summarize battle
                        System.out.println("\n<<<<<<<<<<<<<<<BATTLE REPORT>>>>>>>>>>>>>>>");
                        attacker.removeInfantry(attackerLosses);
                        if (attackerLosses == 1) System.out.println(attacker.getTerritoryName() + " has just lost " +
                                attackerLosses + " troop.");
                        else System.out.println(attacker.getTerritoryName() + " has just lost " +
                                attackerLosses + " troops.");

                        defender.removeInfantry(defenderLosses);
                        if (defenderLosses == 1) System.out.println(defender.getTerritoryName() + " has just lost " +
                                defenderLosses + " troop.\n");
                        else System.out.println(defender.getTerritoryName() + " has just lost " +
                                defenderLosses + " troops.\n");
                        notifyAllObservers();
                        hasAttacked = true;

                        //if defender has lost all its troops
                        if (defender.getTotalTroops() < 1) {
                            //add defender to attacker occupant's list of territories
                            System.out.println(attacker.getTerritoryOccupant().getPlayerName() +
                                    " has just defeated " + defender.getTerritoryOccupant().getPlayerName() +
                                    " in " + defender + " and now occupies the territory.\n");
                            defender.getTerritoryOccupant().removeTerritories(defender);
                            attacker.getTerritoryOccupant().addTerritories(defender);

                            //if defender has lost all countries, eliminate player from game
                            if (defender.getTerritoryOccupant().getTerritories().size() == 0) {
                                System.out.println(defender.getTerritoryOccupant().getPlayerName() +
                                        " has lost all his territories and has been eliminated from the game\n");
                                players.remove(defender.getTerritoryOccupant());
                            }

                            //move one troop from attacker to defender
                            defender.setTerritoryOccupant(attacker.getTerritoryOccupant());
                            attacker.removeInfantry(1);
                            defender.addInfantry(1);
                            notifyAllObservers();
                        }
                    }
                    else {
                        System.out.println("Cannot attack!! Dice roll number must be either 1, 2 or 3");
                    }
                }
                //if defending territory has no troop
                else{
                    System.out.println(attacker.getTerritoryOccupant().getPlayerName() +
                            " now occupies " + defender.getTerritoryName());
                    attacker.getTerritoryOccupant().addTerritories(defender);

                    //move one troop from attacker to defender
                    defender.setTerritoryOccupant(attacker.getTerritoryOccupant());
                    attacker.removeInfantry(1);
                    defender.addInfantry(1);
                    notifyAllObservers();
                    hasAttacked = true;
                }
            }
            else{
                System.out.println(attacker.getTerritoryName() + " is not adjacent to " + defender.getTerritoryName() + "\n");
            }
        }
        else{
            System.out.println(activePlayer.getPlayerName() + ", you cannot attack your own territory\n");
        }
    }

    /**
     * Prints instructions to the user
     */
    public void help(){
        System.out.println("\nATTACK:\n" +
                "In this order:\n" +
                "1. Current player selects a territory to attack from - from his list of territories\n" +
                "2. Current player selects a territory to attack - from the list of adjacent territories\n" +
                "    (The selected adjacent territory must not belong to the current player)\n" +
                "3. Current player inputs the amount of times he wishes to roll the dice - in the attacker roll\n    box\n" +
                "    (This number must be either 1, 2 or 3)\n" +
                "4. Player who owns the defending territory inputs the amount of times he wishes to roll the\n    dice - in the defender roll box\n" +
                "    (This number must be either 1, 2 or 3)\n" +
                "5. Current player clicks on the 'Attack' button to attack\n" +
                "\nPASS:\n" +
                "1. Current player clicks the 'Pass' button to pass his turn to the next player\n" +
                "\nHELP:\n" +
                "1. Click the 'Help' button to display instructions for the game\n" +
                "\nQUIT:\n" +
                "1. Click the 'Quit' button to end the game\n");
    }

    public ArrayList<Territory> getListOfPlayerTerritories(int n) {
        territoriesList = new ArrayList<>();
        if(n==1){
            for (int i = 0; i < players.get(0).getTerritories().size(); i++)
            {
                territoriesList.add(players.get(0).getTerritories().get(i));
            }
        }
        else if(n==2){
            for (int i = 0; i < players.get(1).getTerritories().size(); i++)
            {
                territoriesList.add(players.get(1).getTerritories().get(i));
            }
        }
        else if(n==3){
            for (int i = 0; i < players.get(2).getTerritories().size(); i++)
            {
                territoriesList.add(players.get(2).getTerritories().get(i));
            }
        }
        else if(n==4){
            for (int i = 0; i < players.get(3).getTerritories().size(); i++)
            {
                territoriesList.add(players.get(3).getTerritories().get(i));
            }
        }
        else if(n==5){
            for (int i = 0; i < players.get(4).getTerritories().size(); i++)
            {
                territoriesList.add(players.get(4).getTerritories().get(i));
            }
        }
        else if(n==6){
            for (int i = 0; i < players.get(5).getTerritories().size(); i++)
            {
                territoriesList.add(players.get(5).getTerritories().get(i));
            }
        }
        return territoriesList;
    }

    /**
     * get list of continents
     * @return list of continents
     */
    public ArrayList<Territory> getListOfContinentTerritories(int n){
        continentTerritoriesList = new ArrayList<>();
        if(n==1){
            for (int i = 0; i < board.getAllContinents()[0].getIncludedTerritories().size(); i++)
            {
                continentTerritoriesList.add(board.getAllContinents()[0].getIncludedTerritories().get(i));
            }
        }
        else if(n==2){
            for (int i = 0; i < board.getAllContinents()[1].getIncludedTerritories().size(); i++)
            {
                continentTerritoriesList.add(board.getAllContinents()[1].getIncludedTerritories().get(i));
            }
        }
        else if(n==3){
            for (int i = 0; i < board.getAllContinents()[2].getIncludedTerritories().size(); i++)
            {
                continentTerritoriesList.add(board.getAllContinents()[2].getIncludedTerritories().get(i));
            }
        }
        else if(n==4){
            for (int i = 0; i < board.getAllContinents()[3].getIncludedTerritories().size(); i++)
            {
                continentTerritoriesList.add(board.getAllContinents()[3].getIncludedTerritories().get(i));
            }
        }
        else if(n==5){
            for (int i = 0; i < board.getAllContinents()[4].getIncludedTerritories().size(); i++)
            {
                continentTerritoriesList.add(board.getAllContinents()[4].getIncludedTerritories().get(i));
            }
        }
        else if(n==6){
            for (int i = 0; i < board.getAllContinents()[5].getIncludedTerritories().size(); i++)
            {
                continentTerritoriesList.add(board.getAllContinents()[5].getIncludedTerritories().get(i));
            }
        }
        return continentTerritoriesList;
    }

    public ArrayList<Territory> getListOfAdjacentsOfSelectedTerritory() {
        territoriesList = new ArrayList<>();
        if(selectedTerritory!=null)
        {
            for (int i = 0; i < selectedTerritory.getAdjacencies().length; i++)
            {
                territoriesList.add(selectedTerritory.getAdjacencies()[i]);
            }
        }
        return territoriesList;
    }

    /**
     * set country selection for all players and adjacent
     * @param territory
     */
    public void setPlayerTerritorySelection(Territory territory, int n) {
        if(n==1)
        {
            selectedTerritory = territory;
            setChanged();
            notifyObservers("adjacent");
            setChanged();
            notifyObservers("player1");
        }
        else if(n==2)
        {
            selectedTerritory = territory;
            setChanged();
            notifyObservers("adjacent");
            setChanged();
            notifyObservers("player2");
        }
        else if(n==3){
            selectedTerritory = territory;
            setChanged();
            notifyObservers("adjacent");
            setChanged();
            notifyObservers("player3");
        }
        else if(n==4){
            selectedTerritory = territory;
            setChanged();
            notifyObservers("adjacent");
            setChanged();
            notifyObservers("player4");
        }
        else if(n==5){
            selectedTerritory = territory;
            setChanged();
            notifyObservers("adjacent");
            setChanged();
            notifyObservers("player5");
        }
        else if(n==6)
        {
            selectedTerritory = territory;
            setChanged();
            notifyObservers("adjacent");
            setChanged();
            notifyObservers("player6");
        }
        else if(n==0)
        {
            selectedTerritory = territory;
        }
    }


    public int getActivePlayerIndex(){
        return activePlayerIndex;
    }

    public boolean getHasAttacked(){
        return hasAttacked;
    }

    public void notifyAllObservers(){
        setChanged();
        notifyObservers("player1");
        setChanged();
        notifyObservers("player2");
        setChanged();
        notifyObservers("player3");
        setChanged();
        notifyObservers("player4");
        setChanged();
        notifyObservers("player5");
        setChanged();
        notifyObservers("player6");
        setChanged();
        notifyObservers("africa");
        setChanged();
        notifyObservers("asia");
        setChanged();
        notifyObservers("australia");
        setChanged();
        notifyObservers("europe");
        setChanged();
        notifyObservers("northAmerica");
        setChanged();
        notifyObservers("southAmerica");
        setChanged();
        notifyObservers("adjacent");
    }

    public Board getBoard(){
        return board;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public boolean isDeployed() {
        return deployed;
    }
}
