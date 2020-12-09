import javax.swing.*;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Random;

/**
 *
 * Implements the game of risk
 *
 */
public class Game extends Observable implements Serializable
{
    private static final long serialVersionUID = 1420672609912364060L;

    private Board board;
    private Deck deck;
    private Player activePlayer;
    private Dice dice;
    private Random random;
    private Territory selectedTerritory;
    private File file;

    private boolean deployed;
    private boolean hasAttacked;
    private boolean AI;
    private boolean AIHasReinforced;
    private boolean AIHasAttacked;
    private boolean AIHasFortified;
    private boolean canReinforce;
    private boolean canAttack;
    private boolean canFortify;
    private boolean add;
    private boolean add1;
    private boolean add2;
    private boolean isInt;

    private int activePlayerIndex;
    private int noOfPlayers;
    private int r;
    private int r1;
    private int r2;
    private int repeat;
    private int count;
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

        file = new File("GameMap.xml");
        board = new Board(file);
        random = new Random();

        deployed = false;
        hasAttacked = false;
        canAttack = false;
        canFortify = false;
        canReinforce = false;
        isInt = false;

        AIHasReinforced = false;
        AIHasAttacked = false;
        AIHasFortified = false;

        players = new ArrayList<>();

        noOfPlayers = 0;
        activePlayerIndex = -1;
        r = 0;
        r1 = 0;
        r2 = 0;
        repeat = 0;
        count = 0;

        System.out.println("Filling up the deck...");
        deck = new Deck(board.getTerritories());
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
        ArrayList<Territory> holdTerritories = board.getTerritories();
        random = new Random();

        for (int i = 0; i < holdTerritories.size(); i++) {
            if (players.size()==2) player = players.get(i / (board.getTerritories().size()/2));
            else if (players.size()==3) player = players.get(i / (board.getTerritories().size()/3));
            else if (players.size()==4) player = players.get(i / (board.getTerritories().size()/4 + 1));
            else if (players.size()==5) player = players.get(i / (board.getTerritories().size()/5 + 1));
            else if (players.size()==6) player = players.get(i / (board.getTerritories().size()/6));

            if (player.getTotalTroops() > 0) {
                r = random.nextInt((holdTerritories.size() - i));
                Territory territory = holdTerritories.get(r);

                int a = 0;
                //number of armies deployed per country depends on number of armies left
                if (players.size()==2) {
                    a = 1 + random.nextInt((player.getTotalTroops() / 7) + 1);
                } else if (players.size()==3) {
                    a = 1 + random.nextInt((player.getTotalTroops() / 5) + 1);
                } else if (players.size()==4) {
                    a = 1 + random.nextInt((player.getTotalTroops() / 4) + 1);
                } else if (players.size()==5) {
                    a = 1 + random.nextInt((player.getTotalTroops() / 4) + 1);
                } else if (players.size()==6) {
                    a = 1 + random.nextInt((player.getTotalTroops() / 3) + 1);
                }

                territory.setTerritoryOccupant(player);
                player.addTerritories(territory);
                player.sendInfantry(territory, a);
                System.out.println(player.getPlayerName() + " now has " + territory.getTotalTroops() + " troops deployed in " +
                        territory.getTerritoryName());

                //move already allocated territory to the end
                for (int j = r; j < holdTerritories.size() - 1; j++) {
                    holdTerritories.set(j, holdTerritories.get(j + 1));
                }
            } else {
                System.out.println(player.getPlayerName() + ": All your armies have been randomly deployed");
            }
        }

        //make sure that all player troops are distributed
        for(int i=0; i<players.size(); i++){
            if(players.get(i).getTotalTroops()>0){
                int j = 0;
                Collections.shuffle(players.get(i).getTerritories());
                while(players.get(i).getTotalTroops()>0) {
                    players.get(i).sendInfantry(players.get(i).getTerritories().get(j), 1);
                    j++;
                    if(j>players.get(i).getTerritories().size()-1){
                        j=0;
                    }
                }
            }
            else {
                System.out.println(players.get(i).getPlayerName() + ": All your armies have been randomly deployed");
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

                //adds troops based on how many territories are owned
                if (activePlayer.getTerritories().size() < 12)
                {
                    activePlayer.addInfantry(3);
                }
                else
                {
                    activePlayer.addInfantry(activePlayer.getTerritories().size() / 3);
                }
                for(int i = 0; i<board.getContinents().size(); i++){
                    if(activePlayer.getTerritories().containsAll(board.getContinents().get(i).getIncludedTerritories())){
                        activePlayer.addInfantry(board.getContinents().get(i).getBonusTroops());
                        System.out.println(activePlayer.getPlayerName() + " has gained " + board.getContinents().get(i).getBonusTroops() +
                                " bonus troops for controlling " + board.getContinents().get(i).getName() + ".");
                    }
                }
                System.out.println("It is now " + activePlayer.getPlayerName() + "'s turn\nYou have " + activePlayer.getTotalTroops() + " troops left");

                if(AI){
                    System.out.println(activePlayer.getPlayerName() + " is AI");
                    AIGameplay();
                }
                else {
                    canReinforce = true;
                    notifyAllObservers();
                }
            }
        }
    }

    /**
     * if next player is AI
     */
    public void AIGameplay(){
        AIReinforce();
        AIAttack();
        AIFortify();
    }

    public void AIReinforce(){
        //AI reinforcement
        AISelectedTerritories = new ArrayList<>();
        for(int i = 0; i<activePlayer.getTerritories().size(); i++){
            add = false;
            for(int j = 0; j<activePlayer.getTerritories().get(i).getAdjacencies().size(); j++){
                if(activePlayer.getTerritories().get(i).getAdjacencies().get(j).getTerritoryOccupant()!=null) {
                    if (!activePlayer.getTerritories().get(i).getAdjacencies().get(j).getTerritoryOccupant().equals(activePlayer)) {
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
                r = random.nextInt(AISelectedTerritories.size());
                reinforce(AISelectedTerritories.get(r), 0);
            }
            while(activePlayer.getTotalTroops()>0);
        }
        AIHasReinforced = true;
    }

    public void AIAttack(){
        //AI attack
        do{
            //60% chance of reoccurrence
            AISelectedTerritories = new ArrayList<>();
            for(int i=0; i<activePlayer.getTerritories().size(); i++) {
                add = false;
                for (int j = 0; j < activePlayer.getTerritories().get(i).getAdjacencies().size(); j++) {
                    if(activePlayer.getTerritories().get(i).getAdjacencies().get(j).getTerritoryOccupant() != null) {
                        if (activePlayer.getTerritories().get(i).getTotalTroops() > 2 &&
                                !activePlayer.getTerritories().get(i).getAdjacencies().get(j).getTerritoryOccupant().equals(activePlayer)) {
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
                for(int i = 0; i< AISelectedTerritories.get(r1).getAdjacencies().size(); i++){
                    if(AISelectedTerritories.get(r1).getAdjacencies().get(i).getTerritoryOccupant() != null) {
                        if (!AISelectedTerritories.get(r1).getAdjacencies().get(i).getTerritoryOccupant().
                                equals(activePlayer.getPlayerName())) {
                            AITargetTerritories.add(AISelectedTerritories.get(r1).getAdjacencies().get(i));
                        }
                    }
                }
                if(AITargetTerritories.size()>0){
                    r2 = random.nextInt(AITargetTerritories.size());
                    attack(AISelectedTerritories.get(r1), AITargetTerritories.get(r2), random.nextInt(2) + 1);
                    repeat = random.nextInt(9);
                }
            }
        }
        while(repeat >= 4);
        AIHasAttacked = false;
    }

    public void AIFortify(){
        boolean next = false;
        //AI fortify
        do {
            //50% chance of reoccurrence
            AITargetTerritories = new ArrayList<>();
            for (int i = 0; i < activePlayer.getTerritories().size(); i++) {
                add1 = false;
                add2 = false;
                for (int j = 0; j < activePlayer.getTerritories().get(i).getAdjacencies().size(); j++) {
                    if (activePlayer.getTerritories().get(i).getAdjacencies().get(j).getTerritoryOccupant() != null) {
                        if (!activePlayer.getTerritories().get(i).getAdjacencies().get(j).getTerritoryOccupant().equals(activePlayer)) {
                            add1 = true;
                        } else {
                            add2 = true;
                        }
                    }
                }
                if (add1 && add2) {
                    AITargetTerritories.add(activePlayer.getTerritories().get(i));
                }
            }
            if (AITargetTerritories.size() > 0) {
                AISelectedTerritories = new ArrayList<>();
                r1 = random.nextInt(AITargetTerritories.size());
                for (int i = 0; i < AITargetTerritories.get(r1).getAdjacencies().size(); i++) {
                    if (AITargetTerritories.get(r1).getAdjacencies().get(i).getTerritoryOccupant() != null) {
                        if (AITargetTerritories.get(r1).getAdjacencies().get(i).getTerritoryOccupant().equals(activePlayer) &&
                                AITargetTerritories.get(r1).getAdjacencies().get(i).getTotalTroops() > 1) {
                            AISelectedTerritories.add(AITargetTerritories.get(r1).getAdjacencies().get(i));
                        }
                    }
                }
                if (AISelectedTerritories.size() > 0) {
                    r2 = random.nextInt(AISelectedTerritories.size());
                    fortify(AISelectedTerritories.get(r2), AITargetTerritories.get(r1), 0);
                    repeat = random.nextInt(9);
                }
            }
            else{
                next = true;
                nextPlayer();
            }
        }
        while(repeat >= 6);
        if(!next) {
            nextPlayer();
        }
    }

    /**
     * Place bonus troops on country
     * @param territory is the territory where bonus armies would be placed
     */
    public void reinforce(Territory territory, int humanTroops){
        if(canReinforce || AI) {
            System.out.println("REINFORCEMENT");
            int troops;
            //if territory is empty or occupied by the active player
            if (territory.getTerritoryOccupant() == null || activePlayer.equals(territory.getTerritoryOccupant())) {
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
                        System.out.println(activePlayer.getPlayerName() + " has " + activePlayer.getTotalTroops() + " troops left.");
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
     */
    public void attack(Territory attacker, Territory defender,
                       int numberOfAttackerDiceRolls){
        int defenderRoll = 0;
        //defender can roll 2 dice only if he has at least 2 troops in his territory
        if(defenderRoll==2 && defender.getTotalTroops()<2){
            canAttack = false;
            System.out.println(defender.getTerritoryOccupant().getPlayerName() + ", you need at least 2 troops in " + defender.getTerritoryName() +
                    " to roll the dice 2 times");
            return;
        }
        if(canAttack || AI) {
            System.out.println("ATTACK");
            //stops player from attacking his territory
            if (attacker.getTerritoryOccupant() != defender.getTerritoryOccupant()) {
                //defender must be adjacent to attacker
                if (attacker.getAdjacencies().contains(defender)) {
                    if(attacker.getTotalTroops()>1) {
                        //if defending territory is not empty
                        if (defender.getTotalTroops() > 0) {
                            if(attacker.getTotalTroops()>numberOfAttackerDiceRolls) {
                                System.out.println(attacker.getTerritoryName() + " is about to attack " + defender.getTerritoryName());
                                dice = new Dice();
                                attackerLosses = 0;
                                defenderLosses = 0;
                                isInt = false;

                                if(!defender.getTerritoryOccupant().getAI()){
                                    while(!isInt) {
                                        try {
                                            defenderRoll = Integer.parseInt(JOptionPane.showInputDialog(defender.getTerritoryOccupant().getPlayerName() +
                                                    ", " + defender.getTerritoryName() + " is being attacked from " + attacker.getTerritoryName() + " by " + attacker.getTerritoryOccupant().getPlayerName()
                                                    + "! How many dice will you roll?"));
                                            if (defenderRoll < 1 || defenderRoll > 2 || defenderRoll > defender.getTotalTroops()) {
                                                throw new IllegalArgumentException();
                                            }
                                            isInt = true;
                                        } catch (NumberFormatException e) {
                                            // Error: attacker inputs non-integer
                                            System.out.println("Roll 1 or 2. You must have at least one more troops in your territory than the number of dice you roll.");
                                        } catch (IllegalArgumentException e) {
                                            // Error: attacker inputs invalid number of dice
                                            System.out.println("Roll 1 or 2. You must have at least one more troops in your territory than the number of dice you roll.");
                                        }
                                    }
                                }
                                else{
                                    random = new Random();
                                    if(defender.getTotalTroops()<=1){
                                        defenderRoll=1;
                                    }
                                    else {
                                        if (defender.getTotalTroops() > 1) {
                                            defenderRoll = random.nextInt(2) + 1;
                                        } else {
                                            defenderRoll = random.nextInt(1) + 1;
                                        }
                                    }
                                    isInt = true;
                                    System.out.println("AI rolls: " + defenderRoll);
                                }

                                //roll results are ordered from highest (0) to lowest
                                if(isInt){
                                    attackerDiceRollResults = dice.rollDice(numberOfAttackerDiceRolls);
                                    System.out.println(activePlayer.getPlayerName() + " has rolled the dice " +
                                            numberOfAttackerDiceRolls + " times.");
                                    defenderDiceRollResults = dice.rollDice(defenderRoll);
                                    System.out.println(defender.getTerritoryOccupant().getPlayerName() +
                                            " has rolled the dice " + defenderRoll + " times.");

                                    //compare highest results
                                    if (attackerDiceRollResults[0] > defenderDiceRollResults[0])
                                        defenderLosses++;
                                    else if (attackerDiceRollResults[0] < defenderDiceRollResults[0])
                                        attackerLosses++;

                                    //compare second highest results if both attacker and defender have
                                    if (numberOfAttackerDiceRolls > 1 && defenderRoll > 1) {
                                        if (attackerDiceRollResults[1] > defenderDiceRollResults[1])
                                            defenderLosses++;
                                        else if (attackerDiceRollResults[1] < defenderDiceRollResults[1])
                                            attackerLosses++;
                                    }

                                    if(attackerLosses==0 && defenderLosses==0){
                                        System.out.println("<<<<<<<<<<<<<<<< BATTLE REPORT >>>>>>>>>>>>>>>>");
                                        System.out.println("TIE: " + defender.getTerritoryName() + " has the advantage ");
                                        attacker.removeInfantry(1);
                                        hasAttacked = true;
                                    }
                                    else {
                                        //Summarize battle
                                        System.out.println("<<<<<<<<<<<<<<<< BATTLE REPORT >>>>>>>>>>>>>>>>");
                                        attacker.removeInfantry(attackerLosses);
                                        defender.removeInfantry(defenderLosses);
                                        notifyAllObservers();
                                        hasAttacked = true;
                                    }

                                    //if defender has lost all its troops
                                    if (defender.getTotalTroops() < 1) {
                                        //add defender to attacker occupant's list of territories
                                        System.out.println(attacker.getTerritoryOccupant().getPlayerName() +
                                                " has just defeated " + defender.getTerritoryOccupant().getPlayerName() +
                                                " in " + defender.getTerritoryName() + " and now occupies the territory");
                                        defender.getTerritoryOccupant().removeTerritories(defender);
                                        attacker.getTerritoryOccupant().addTerritories(defender);

                                        //if defender has lost all countries, eliminate player from game
                                        if (defender.getTerritoryOccupant().getTerritories().size() == 0) {
                                            System.out.println(defender.getTerritoryOccupant().getPlayerName() +
                                                    " has lost all his territories and has been eliminated from the game");
                                            players.remove(defender.getTerritoryOccupant());
                                            playerNames.remove(defender.getTerritoryOccupant().getPlayerName());
                                            noOfPlayers--;
                                            if (players.size() < 2) {
                                                JOptionPane.showMessageDialog(null, players.get(0).getPlayerName() +
                                                        " now occupies all territories and has won the game\nCONGRATULATIONS - " + players.get(0).getPlayerName());
                                                System.exit(0);
                                            }
                                        }

                                        //move one troop from attacker to defender
                                        defender.setTerritoryOccupant(attacker.getTerritoryOccupant());
                                        attacker.removeInfantry(numberOfAttackerDiceRolls);
                                        defender.addInfantry(numberOfAttackerDiceRolls);
                                        //hasAttacked = true;
                                    }
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
                }
                else {
                    System.out.println(attacker.getTerritoryName() + " is not adjacent to " + defender.getTerritoryName());
                }
            } else {
                System.out.println(activePlayer.getPlayerName() + ", you cannot attack your own territory");
            }
        }
        else {
            System.out.println("If you have any troop left, keep reinforcing.\nIf you have already fortified, you cannot attack again\n" +
                    "You can keep fortifying or pass turn");
        }
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
            System.out.println("FORTIFICATION");
            //player must own both territories
            if(activePlayer.equals(fortifyFrom.getTerritoryOccupant()) && activePlayer.equals(fortifyTo.getTerritoryOccupant())){
                //both territories must be adjacent
                if(fortifyFrom.getAdjacencies().contains(fortifyTo)){
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
                    if(fortifyFrom.getTotalTroops()>1) {
                        if (fortifyFrom.getTotalTroops() > troops) {
                            fortifyFrom.removeInfantry(troops);
                            fortifyTo.addInfantry(troops);
                            notifyAllObservers();
                            canAttack = false;
                        } else {
                            System.out.println("You do not have enough troops in " + fortifyFrom.getTerritoryName() + " to fortify " + fortifyTo.getTerritoryName() +
                                    " with " + troops + "troops\n" + fortifyFrom.getTerritoryName() + " has only " + fortifyTo.getTotalTroops() + " troops");
                        }
                    }
                    else{
                        System.out.println("You can only fortify from a territory that has at least 2 troops");
                    }
                }
                else{
                    System.out.println(fortifyFrom.getTerritoryName() + " is not adjacent to " + fortifyTo.getTerritoryName());
                }
            }
            else{
                System.out.println("You do not own both " + fortifyFrom.getTerritoryName() + " and " + fortifyTo.getTerritoryName());
            }
        }
        else{
            System.out.println("You cannot fortify right now");
        }
    }

    /**
     * Prints instructions to the user
     */
    public void help(){
        System.out.println(
                "\nREINFORCE:\n" +
                "1. Select a territory to reinforce\n" +
                "2. Input number of troops to send\n" +
                "3. Click the 'Reinforce' button\n" +
                "4. You must reinforce till you have no troop left\n" +
                "ATTACK:\n" +
                "1. Select a territory to attack from\n" +
                "2. Select an adjacent territory to attack\n" +
                "3. Input number of dice to roll (1, 2, or 3)\n" +
                "4. Defending player inputs number of dice to roll (1, 2)\n" +
                "5. Click the 'Attack' button\n" +
                "FORTIFY:\n" +
                "1. Select a territory to fortify from\n" +
                "2. Select an adjacent territory, that you own, to fortify\n" +
                "3. Input number of troops to send\n" +
                "4. Click the 'Fortify' button\n" +
                "PASS:\n" +
                "Click the 'Pass' button to pass turn to the next player at any time\n" +
                "SAVE:\n" +
                "Click the 'Save' button to save the game\n" +
                "LOAD:\n" +
                "Click the 'Load' button to load a previously saved game\n" +
                "HELP:\n" +
                "Click the 'Help' button to display instructions for the game\n" +
                "QUIT:\n" +
                "Click the 'Quit' button to end the game\n");
    }

    public ArrayList<Territory> getListOfPlayerTerritories(int n) {
        territoriesList = new ArrayList<>();
        for (int i = 0; i < players.get(n).getTerritories().size(); i++) {
            territoriesList.add(players.get(n).getTerritories().get(i));
        }
        return territoriesList;
    }

    /**
     * get list of continents
     * @return list of continents
     */
    public ArrayList<Territory> getListOfContinentTerritories(int n){
        continentTerritoriesList = new ArrayList<>();
        for (int j = 0; j < board.getContinents().get(n).getIncludedTerritories().size(); j++)
        {
            continentTerritoriesList.add(board.getContinents().get(n).getIncludedTerritories().get(j));
        }
        return continentTerritoriesList;
    }

    public ArrayList<Territory> getListOfAdjacentsOfSelectedTerritory() {
        territoriesList = new ArrayList<>();
        if(selectedTerritory!=null)
        {
            for (int i = 0; i < selectedTerritory.getAdjacencies().size(); i++)
            {
                territoriesList.add(selectedTerritory.getAdjacencies().get(i));
            }
        }
        return territoriesList;
    }

    /**
     * set country selection for all players and adjacent
     * @param territory
     */
    public Territory setPlayerTerritorySelection(Territory territory, int n) {
        if(n==0)
        {
            if(players.size()>0) {
                selectedTerritory = territory;
                setChanged();
                notifyObservers(getPlayerNames().get(0));
                setChanged();
                notifyObservers("adjacent");
            }
        }
        else if(n==1)
        {
            if(players.size()>1) {
                selectedTerritory = territory;
                setChanged();
                notifyObservers(getPlayerNames().get(1));
                setChanged();
                notifyObservers("adjacent");
            }
        }
        else if(n==2){
            if(players.size()>2) {
                selectedTerritory = territory;
                setChanged();
                notifyObservers(getPlayerNames().get(2));
                setChanged();
                notifyObservers("adjacent");
            }
        }
        else if(n==3){
            if(players.size()>3) {
                selectedTerritory = territory;
                setChanged();
                notifyObservers(getPlayerNames().get(3));
                setChanged();
                notifyObservers("adjacent");
            }
        }
        else if(n==4){
            if(players.size()>4) {
                selectedTerritory = territory;
                setChanged();
                notifyObservers(getPlayerNames().get(4));
                setChanged();
                notifyObservers("adjacent");
            }
        }
        else if(n==5)
        {
            if(players.size()>5) {
                selectedTerritory = territory;
                setChanged();
                notifyObservers(getPlayerNames().get(5));
                setChanged();
                notifyObservers("adjacent");
            }
        }
        else if(n==6)
        {
            selectedTerritory = territory;
            setChanged();
            notifyObservers("adjacent");
        }
        return selectedTerritory;
    }

    public int getActivePlayerIndex(){
        return activePlayerIndex;
    }

    public boolean getHasAttacked(){
        return hasAttacked;
    }

    public void notifyAllObservers(){
        if(players.size()>0) {
            setChanged();
            notifyObservers(getPlayerNames().get(0));
        }
        if(players.size()>1) {
            setChanged();
            notifyObservers(getPlayerNames().get(1));
        }
        if(players.size()>2) {
            setChanged();
            notifyObservers(getPlayerNames().get(2));
        }
        if(players.size()>3) {
            setChanged();
            notifyObservers(getPlayerNames().get(3));
        }
        if(players.size()>4) {
            setChanged();
            notifyObservers(getPlayerNames().get(4));
        }
        if(players.size()>5) {
            setChanged();
            notifyObservers(getPlayerNames().get(5));
        }
        setChanged();
        notifyObservers("adjacent");
        for(int i=0; i<getBoard().getContinents().size(); i++) {
            setChanged();
            notifyObservers(getBoard().getContinents().get(i).getName());
        }
    }

    public void quitGame(){
        System.exit(0);
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
    public boolean isCanAttack(){
        return canAttack;
    }
    public boolean isCanFortify(){
        return canFortify;
    }
    public boolean isAIHasReinforced(){
        return AIHasReinforced;
    }
    public boolean isAIHasAttacked(){
        return AIHasAttacked;
    }
    public boolean isAIHasFortified(){
        return AIHasFortified;
    }

   /* public Object writeReplace(){
        return new Game();
    }
    private void readObject(ObjectInputStream stream)
            throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");

    }*/
}
