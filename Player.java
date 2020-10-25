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
    int infantry;
    int artillery;
    int cavalry;
    private String name;

    public Player(int troops, String name){
        territories = new ArrayList<Territory>();
        cards = new ArrayList<Card>();
        this.troops = troops;
        this.name = name;
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
        System.out.println(name + "now has, " + troops + " on this territory");
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
     *
     * @param s
     * @return the name of the territory that matches with s
     */
    public String getTerritory(String s){
        if(name == s){
            return name;
        }
        return null;
    }

    /**
     *
     * @param s
     * @return true if a territory with the name s exists
     */
    public Boolean isTerritory(String s){
        if(s != null){
            return true;
        }
        return false;
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

    }
}
