import javax.swing.*;
import java.util.*;
/**
 * Player class that takes care of the cards, territories and the turn based system this game provides.
 * @author MacKenzie Wallace
 * @version 1.0.0
 */
public class Player
{
    private ArrayList<Territory> territories;
    private ArrayList<Card> cards;
    private int infantry;
    private int artillery;
    private int cavalry;
    private String playerName;
    private ArrayList<Continent> continents;
    private boolean AI;

    public Player(int infantry, String name, boolean AI){
        territories = new ArrayList<>();
        cards = new ArrayList<>();
        this.infantry = infantry;
        cavalry = 0;
        artillery = 0;
        this.playerName = name;
        this.AI = AI;
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
    public ArrayList<Card> getCards(){
        return cards;
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
        if(inf==1) System.out.println(playerName + " has received " + inf + " troop. Total troops: " + infantry);
        else{
            System.out.println(playerName + " has received " + inf + " troops. Total troops: " + infantry);
        }
    }

    /**
     * Method to add more infantry
     * @param inf Number of infantry to be added
     */
    public void removeInfantry(int inf){
        infantry -= inf;
        if(inf==1) System.out.println(playerName + " has lost " + inf + " troop. Total troops: " + infantry);
        else{
            System.out.println(playerName + " has lost " + inf + " troops. Total troops: " + infantry);
        }
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
    public void removeTerritories(Territory territory){
        territories.remove(territory);
        System.out.println(playerName + " has lost ownership of " + territory.getTerritoryName());
    }

    /**
     * returns ALL the territories occupied
     */
    public ArrayList<Territory> getTerritories(){
        return territories;
    }

    /**
     * Adds a continent when a player occupies all territories in the continent
     */
    public void addContinent(Continent continent){
        continents.add(continent);
        System.out.println(playerName + " now occupies " + continent.getName());
    }

    /**
     * remove a continent when a player no longer occupies all territories in the continent
     */
    public void removeContinent(Continent continent){
        continents.add(continent);
        System.out.println(playerName + " no longer occupies " + continent.getName());
    }

    /**
     * @return the Player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * @return true is AI is true
     */
    public boolean getAI(){
        return AI;
    }

    public String toString(){
        String s = "";
        for(int i = 0; i<getTerritories().size(); i++){
            s = s + getTerritories().get(i).getTerritoryName() +
                    " - " + getTerritories().get(i).getTotalTroops() + "\n";
        }
        return s;
    }
}
