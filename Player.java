import java.util.*;
/**
 * Player class that takes care of the cards, territories and the turn based system this game provides.
 * @author MacKenzie Wallace
 * @version 1.0.0
 */
public class Player {
    private ArrayList<Territory> territories;
    private ArrayList<Card> cards;
    private int infantry;
    private int artillery;
    private int cavalry;
    private String playerName;

    public Player(int infantry, String name){
        territories = new ArrayList<Territory>();
        cards = new ArrayList<Card>();
        this.infantry = infantry;
        cavalry = 0;
        artillery = 0;
        this.playerName = name;
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

    public void sendInfantry(Territory territory, int amount){
        territory.addInfantry(amount);
        infantry -= amount;
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
    public int getTotalTroops(){
        return infantry + (5 * cavalry) + (10 * artillery);
    }

    /**
     * Method to add more infantry
     * @param inf Number of infantry to be added
     */
    public void addInfantry(int inf){
        infantry += inf;
    }

    /**
     * Adding more territories (Territories conquered)
     * @param newTerritory territory to be added
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
    public ArrayList<Territory> getTerritories(){
        return territories;
    }

    /**
     * @return the Player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * handles the turn based system of the game
     */
    public void takeTurn() {
        int conquered = 0;
        if (Math.floorDiv(territories.size(), 3) < 3) {
            infantry = infantry + 3;
        } else {
            infantry = infantry + Math.floorDiv(territories.size(), 3);
        }
        boolean fight = true;
        if (fight) {
            Player winner = temp1.Attack(temp2, temp3); //temp1 will be of type Territory and it is the territory they're attacking FROM
            if (winner.getPlayerName().equals(this.playerName)) { //temp2 is the territory which they are attacking (it is also type Territory)
                conquered++; // temp3 is the int number of troops being sent in
            } else {
                conquered = conquered;
            }
            for (int i = 0; i < conquered; i++) {
                fakeDeck.selectCard().addCard(i);
            }
        } else {
            return;
        }
    }
}
