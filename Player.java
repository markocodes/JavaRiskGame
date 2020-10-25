import java.util.*;
/**
 * Player class that takes care of the cards, territories and the turn based system this game provides.
 * @author MacKenzie Wallace
 * @version 1.0.0
 */
public class Player {
    private ArrayList<Territory> territories;
    private ArrayList<Card> cards;
    private int troops;
    private int infantry;
    private int artillery;
    private int cavalry;
    private String playerName;
    private Territory temp;

    public Player(int troops, String playerName){
        territories = new ArrayList<Territory>();
        cards = new ArrayList<Card>();
        this.troops = troops;
        this.playerName = playerName;
    }

    /**
     * add a new card into the hand.
     * @param newCard
     */
    public void addCard(Card newCard){
        cards.add(newCard);
    }

    /**
     * remove the card and place it back into the deck
     */
    public void removeCard(){
        cards.remove(0);
    }

    /**
     * Method that provides a way to list ALL cards in hand
     */
    public void getCards(){
        for(int i = 0; i < cards.size(); i++){
            System.out.println(cards.get(i));
        }
    }

    /**
     * Method to keep track of the troops
     * @return the troops user has
     */
    public int getTroops(){
        return troops;
    }

    /**
     * Method to add more troops with new troops
     * @param newTroops
     */
    public void addTroops(int newTroops){
        troops = troops + newTroops;
        System.out.println(playerName + "now has, " + troops + " on this territory");
    }

    /**
     * Adding more territories (Territories conquered)
     * @param newTerritory
     */
    public void addTerritories(Territory newTerritory){
        territories.add(newTerritory);
    }

    /**
     * removing territories (Territories lost)
     */
    public void removeTerritories(){
        territories.remove(0);
    }

    /**
     * returns ALL the territories occupied
     */
    public void getTerritory(){
        for(int i = 0; i < territories.size(); i++){
            System.out.println(territories.get(i));
        }
    }

    /**
     *
     * @return the name of the player
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * handles the turn based system of the game
     */
    public void takeTurn(){
        if(Math.floorDiv(territories.size(), 3) < 3){
            infantry = infantry + 3;
        }else{
            infantry = infantry + Math.floorDiv(territories.size(), 3);
        }
        boolean fight = true;
        if(fight){
            temp1.Attack(temp2, temp3); //temp1 will be of type Territory and it is the territory they're attacking FROM
        }else{ //temp2 is the territory which they are attacking (it is also type Territory)
            return; // temp3 is the int number of troops being sent in
        }
        int conquered = 0;
        Player winner = temp1.Attack(temp2, temp3)
        if(winner.getPlayerName().equals(this.playerName)){
            conquered++;
        }else{
            conquered = conquered;
        }
        for(int i = 0; i < conquered; i++){
            selectCard()
        }
    }

}
