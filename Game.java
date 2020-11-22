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
    private boolean AI;
    private boolean canReinforce;
    private boolean canAttack;
    private boolean canFortify;

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
    private ArrayList<String> humanOrAI;
    private ArrayList<Territory> territoriesList;
    private ArrayList<Territory> continentTerritoriesList;
    private ArrayList<Territory> AISelectedTerritories;
    private ArrayList<Territory> AITargetTerritories;

    /**
     *This is the constructor for the RiskModel object.
     **/
    public Game() {
    }

    /**
     * Initialize the game
     **/
    public boolean init(ArrayList<String> playerNames, ArrayList<String> humanOrAI) {
        load = false;
        board = new Board();

        deployed = false;
        load = false;
        hasAttacked = false;
        canAttack = false;
        canFortify = false;
        canReinforce = false;

        players = new ArrayList<>();

        noOfPlayers = 0;
        activePlayerIndex = -1;


        System.out.println("Filling up the deck...");
        deck = new Deck(board.getAllTerritories());
        this.playerNames = playerNames;
        this.humanOrAI = humanOrAI;
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
                if(humanOrAI.get(i).equals("Human")) {
                    AI = false;
                }
                else if (humanOrAI.get(i).equals("AI")) {
                    AI = true;
                }
                else {
                    System.out.println(humanOrAI.get(i) + " is not a valid selection");
                }
                players.add(new Player(50, playerNames.get(i), AI));
            }
        }
        else {
            for (int i = 0; i < playerNames.size(); i++) {
                if(humanOrAI.get(i).equals("Human")) {
                    AI = false;
                }
                else if (humanOrAI.get(i).equals("AI")) {
                    AI = true;
                }
                else {
                    System.out.println(humanOrAI.get(i) + " is not a valid selection");
                }
                players.add(new Player(50 - (playerNames.size() * 5), playerNames.get(i), AI));
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
                System.out.println(player.getPlayerName() + ": All your armies have been randomly deployed");
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
                canFortify = false;
                canReinforce = false;
                canAttack = false;
                activePlayerIndex++;

                //loop back to first player if necessary
                if (activePlayerIndex >= players.size())
                {
                    activePlayerIndex = 0;
                }
                activePlayer = players.get(activePlayerIndex);
                AI = activePlayer.getAI();
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
                                " bonus troops for controlling " + board.getAllContinents()[i].getName() + ".");
                    }
                }
                System.out.println("It is now " + activePlayer.getPlayerName() +
                        "'s turn\nYou have " + activePlayer.getTotalTroops() + " troops left.\n");

                if(AI){
                    System.out.println(activePlayer.getPlayerName() + " is AI");
                    AIGameplay();
                    nextPlayer();
                }
                else {
                    canReinforce = true;
                }
                notifyAllObservers();
            }
            notifyAllObservers();
        }
        notifyAllObservers();
    }

    /**
     * if next player is AI
     */
    public void AIGameplay(){
        boolean add, add1, add2;
        Random random = new Random();
        int r = 0;
        int r1 = 0;
        int r2 = 0;
        int repeat = 0;

        //AI reinforcement
        AISelectedTerritories = new ArrayList<>();
        for(int i = 0; i<activePlayer.getTerritories().size(); i++){
            add = false;
            for(int j = 0; j<activePlayer.getTerritories().get(i).getAdjacencies().length; j++){
                if(activePlayer.getTerritories().get(i).getAdjacencies()[j].getTerritoryOccupant()!=null) {
                    if (!activePlayer.getTerritories().get(i).getAdjacencies()[j].getTerritoryOccupant().equals(activePlayer)) {
                        add = true;
                    }
                }
            }
            if(add){
                AISelectedTerritories.add(activePlayer.getTerritories().get(i));
            }
        }
        if(AISelectedTerritories.size()>0){
            do{
                //70% chance of reoccurrence
                r = random.nextInt(AISelectedTerritories.size());
                reinforce(AISelectedTerritories.get(r), 0);
                repeat = random.nextInt(9);
            }
            while(repeat>=3 && activePlayer.getTotalTroops()>0);
        }

        //AI attack
        do{
            //50% chance of reoccurrence
            AISelectedTerritories = new ArrayList<>();
            for(int i=0; i<activePlayer.getTerritories().size(); i++) {
                add = false;
                for (int j = 0; j < activePlayer.getTerritories().get(i).getAdjacencies().length; j++) {
                    if(activePlayer.getTerritories().get(i).getAdjacencies()[j].getTerritoryOccupant() != null) {
                        if (activePlayer.getTerritories().get(i).getTotalTroops() > 2 &&
                                !activePlayer.getTerritories().get(i).getAdjacencies()[j].getTerritoryOccupant().equals(activePlayer)) {
                            add = true;
                        }
                    }
                }
                if (add) {
                    AISelectedTerritories.add(activePlayer.getTerritories().get(i));
                }
            }
            if(AISelectedTerritories.size()>0){
                r1 = random.nextInt(AISelectedTerritories.size());
                AITargetTerritories = new ArrayList<>();
                for(int i = 0; i< AISelectedTerritories.get(r1).getAdjacencies().length; i++){
                    if(AISelectedTerritories.get(r1).getAdjacencies()[i].getTerritoryOccupant() != null) {
                        if (!AISelectedTerritories.get(r1).getAdjacencies()[i].getTerritoryOccupant().
                                equals(activePlayer.getPlayerName())) {
                            AITargetTerritories.add(AISelectedTerritories.get(r1).getAdjacencies()[i]);
                        }
                    }
                }
                if(AITargetTerritories.size()>0){
                    r2 = random.nextInt(AITargetTerritories.size());
                    attack(AISelectedTerritories.get(r1), AITargetTerritories.get(r2), random.nextInt(2) + 1, random.nextInt(1) + 1);
                    repeat = random.nextInt(9);
                }
            }
        }
        while(repeat >= 5 && activePlayer.getTotalTroops() > 0);

        //AI fortify
        AITargetTerritories = new ArrayList<>();
        for(int i=0; i<activePlayer.getTerritories().size(); i++){
            add1 = false;
            add2 = false;
            for(int j=0; j<activePlayer.getTerritories().get(i).getAdjacencies().length; j++){
                if(activePlayer.getTerritories().get(i).getAdjacencies()[j].getTerritoryOccupant() != null) {
                    if (!activePlayer.getTerritories().get(i).getAdjacencies()[j].getTerritoryOccupant().equals(activePlayer)) {
                        add1 = true;
                    } else {
                        add2 = true;
                    }
                }
            }
            if(add1 && add2){
                AITargetTerritories.add(activePlayer.getTerritories().get(i));
            }
        }
        if(AITargetTerritories.size()>0){
            AISelectedTerritories = new ArrayList<>();
            r1 = random.nextInt(AITargetTerritories.size());
            for(int i=0; i<AITargetTerritories.get(r1).getAdjacencies().length; i++){
                if(AITargetTerritories.get(r1).getAdjacencies()[i].getTerritoryOccupant() != null) {
                    if (AITargetTerritories.get(r1).getAdjacencies()[i].getTerritoryOccupant().equals(activePlayer) &&
                            AITargetTerritories.get(r1).getAdjacencies()[i].getTotalTroops() > 1) {
                        AISelectedTerritories.add(AITargetTerritories.get(r1).getAdjacencies()[i]);
                    }
                }
            }
            if(AISelectedTerritories.size()>0){
                r2 = random.nextInt(AISelectedTerritories.size());
                fortify(AISelectedTerritories.get(r2), AITargetTerritories.get(r1), 0);
            }
        }
    }

    /**
     * Place bonus troops on country
     * @param territory is the territory where bonus armies would be placed
     */
    public void reinforce(Territory territory, int humanTroops){
        if(canReinforce || AI) {
            System.out.println("\nREINFORCEMENT");
            int troops;
            //if territory is empty or occupied by the active player
            if (territory.getTerritoryOccupant() == null || activePlayer.equals(territory.getTerritoryOccupant())) {
                //if all territories are occupied
                if (board.noOfUnoccupiedTerritories() < 1) {
                    if (AI) {
                        random = new Random();
                        troops = random.nextInt(activePlayer.getTotalTroops());
                        if (activePlayer.getTotalTroops() > 0 && troops == 0) {
                            troops = 1;
                        }
                    } else {
                        troops = humanTroops;
                    }
                    //if player has enough armies
                    if (troops > 0) {
                        if (activePlayer.getTotalTroops() >= troops) {
                            activePlayer.sendInfantry(territory, troops);
                            notifyAllObservers();
                            System.out.println(activePlayer.getPlayerName() + " has " + activePlayer.getTotalTroops() + " troops left.\n");
                            if (activePlayer.getTotalTroops() < 1) {
                                canAttack = true;
                                canFortify = true;
                                System.out.println("You are now ready to attack or fortify if you wish");
                            }
                        } else {
                            System.out.println(activePlayer.getPlayerName() + ", you do not have enough troops to reinforce " +
                                    territory.getTerritoryName() + " with " + troops + " troops.\nYou have only " + activePlayer.getTotalTroops() + " troops");
                        }
                    } else {
                        System.out.println(activePlayer.getPlayerName() + ", you must reinforce with at least 1 player");
                    }
                } else {
                    territory.setTerritoryOccupant(activePlayer);
                    activePlayer.addTerritories(territory);
                    territory.addInfantry(1);
                    activePlayer.removeInfantry(1);
                    if (!AI) {
                        nextPlayer();
                    }
                    notifyAllObservers();
                } /*else {
                    System.out.println("Error: There is something wrong with reinforcement");
                }*/
            } else {
                System.out.println("You do not occupy " + territory.getTerritoryName());
            }
        }
        else{
            System.out.println("You cannot reinforce your territories at this time");
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
        if(numberOfDefenderDiceRolls==2 && defender.getTotalTroops()<2){
            canAttack = false;
            System.out.println(defender.getTerritoryOccupant().getPlayerName() + ", you need at least 2 troops in " + defender.getTerritoryName() +
                    " to roll the dice 2 times");
            return;
        }
        if(canAttack || AI) {
            System.out.println("\nATTACK");
            if (attacker != null && defender != null) {
                //stops player from attacking his territory
                if (attacker.getTerritoryOccupant() != defender.getTerritoryOccupant()) {
                    //defender must be adjacent to attacker
                    if (Arrays.asList(attacker.getAdjacencies()).contains(defender)) {
                        if(attacker.getTotalTroops()>1) {
                            //if defending territory is not empty
                            if (defender.getTotalTroops() > 0) {
                                if(attacker.getTotalTroops()>numberOfAttackerDiceRolls) {
                                    System.out.println(attacker.getTerritoryName() + " is about to attack " + defender.getTerritoryName());
                                    dice = new Dice();
                                    attackerLosses = 0;
                                    defenderLosses = 0;

                                    //roll results are ordered from highest (0) to lowest
                                    if (numberOfAttackerDiceRolls > 0 &&
                                            numberOfAttackerDiceRolls < 4 && numberOfDefenderDiceRolls > 0 && numberOfDefenderDiceRolls < 3) {
                                        attackerDiceRollResults = dice.rollDice(numberOfAttackerDiceRolls);
                                        System.out.println(activePlayer.getPlayerName() + " has rolled the dice " +
                                                numberOfAttackerDiceRolls + " times.");
                                        defenderDiceRollResults = dice.rollDice(numberOfDefenderDiceRolls);
                                        System.out.println(defender.getTerritoryOccupant().getPlayerName() +
                                                " has rolled the dice " + numberOfDefenderDiceRolls + " times.");

                                        //compare highest results
                                        if (attackerDiceRollResults[0] > defenderDiceRollResults[0])
                                            defenderLosses++;
                                        else if (attackerDiceRollResults[0] < defenderDiceRollResults[0])
                                            attackerLosses++;

                                        //compare second highest results if both attacker and defender have
                                        if (numberOfAttackerDiceRolls > 1 && numberOfDefenderDiceRolls > 1) {
                                            if (attackerDiceRollResults[1] > defenderDiceRollResults[1])
                                                defenderLosses++;
                                            else if (attackerDiceRollResults[1] < defenderDiceRollResults[1])
                                                attackerLosses++;
                                        }

                                        //Summarize battle
                                        System.out.println("\n<<<<<<<<<<<<<<<BATTLE REPORT>>>>>>>>>>>>>>>");
                                        attacker.removeInfantry(attackerLosses);
                                        defender.removeInfantry(defenderLosses);
                                        notifyAllObservers();
                                        hasAttacked = true;

                                        //if defender has lost all its troops
                                        if (defender.getTotalTroops() < 1) {
                                            //add defender to attacker occupant's list of territories
                                            System.out.println(attacker.getTerritoryOccupant().getPlayerName() +
                                                    " has just defeated " + defender.getTerritoryOccupant().getPlayerName() +
                                                    " in " + defender.getTerritoryName() + " and now occupies the territory.\n");
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
                                            attacker.removeInfantry(numberOfAttackerDiceRolls);
                                            defender.addInfantry(numberOfAttackerDiceRolls);
                                            hasAttacked = true;
                                        }
                                    }
                                    else {
                                        System.out.println("Cannot attack!! attacker dice roll number must be either 1, 2 or 3\ndefender dice roll must be 1 or 2");
                                    }
                                }
                                else{
                                    System.out.println("You must have at least one more troop in your territory than the number of dice you roll");
                                }
                            }
                            //if defending territory has no troop
                            else {
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
                            notifyAllObservers();
                            canReinforce = false;
                        }
                        else{
                            System.out.println("You need at least 2 troops to attack from " + attacker.getTerritoryName());
                        }
                        notifyAllObservers();
                    }
                    else {
                        System.out.println(attacker.getTerritoryName() + " is not adjacent to " + defender.getTerritoryName() + "\n");
                    }
                    notifyAllObservers();
                } else {
                    System.out.println(activePlayer.getPlayerName() + ", you cannot attack your own territory\n");
                }
                notifyAllObservers();
            }
            else {
                System.out.println("You have not selected both territories");
            }
            notifyAllObservers();
        }
        else {
            System.out.println("You cannot attack at the moment. Click help for attacking information");
        }
        notifyAllObservers();
    }

    /**
     *
     * @param fortifyFrom send troops from this territory
     * @param fortifyTo send troops to this territory
     * @param humanTroops number of troops to send
     */
    public void fortify(Territory fortifyFrom, Territory fortifyTo, int humanTroops){
        int troops = 0;
        if(canFortify || AI){
            System.out.println("\nFORTIFICATION");
            //player must own both territories
            if(activePlayer.equals(fortifyFrom.getTerritoryOccupant()) && activePlayer.equals(fortifyTo.getTerritoryOccupant())){
                //both territories must be adjacent
                List<Territory> list = Arrays.asList(fortifyFrom.getAdjacencies());
                if(list.contains(fortifyTo)){
                    if(AI){
                        random = new Random();
                        troops = random.nextInt(fortifyFrom.getTotalTroops());
                        if(fortifyFrom.getTotalTroops()>0 && troops==0){
                            troops = 1;
                        }
                    }
                    else{
                        troops = humanTroops;
                    }

                    //decrement troops in fortifyFrom and increment troops in fortifyTo
                    if(fortifyFrom.getTotalTroops() >= troops){
                        fortifyFrom.removeInfantry(troops);
                        fortifyTo.addInfantry(troops);
                        if(!AI){
                            notifyAllObservers();
                        }
                        nextPlayer();
                    }
                    else {
                        System.out.println("You do not have enough troops in " + fortifyFrom.getTerritoryName() + " to fortify " + fortifyTo.getTerritoryName() +
                                " with " + troops + "troops\n" + fortifyFrom.getTerritoryName() + " has only " + fortifyTo.getTotalTroops() + " troops");
                    }
                    notifyAllObservers();
                }
                else{
                    System.out.println(fortifyFrom.getTerritoryName() + " is not adjacent to " + fortifyTo.getTerritoryName());
                }
                notifyAllObservers();
            }
            else{
                System.out.println("You do not own both " + fortifyFrom.getTerritoryName() + " and " + fortifyTo.getTerritoryName());
            }
            notifyAllObservers();
        }
        else{
            System.out.println("You cannot fortify right now");
        }
        notifyAllObservers();
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
            setChanged();
            notifyObservers("player6");
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
