import java.util.*;
/**
 * Player class that takes care of the cards, territories and the turn based system this game provides.
 * @author MacKenzie Wallace
 * @version 1.0.0
 */
public class Player {
    private ArrayList<Territory> territories;
    private ArrayList<Card> cards;
    private int yourTurn;
    private int troops;
    int infantry;
    int artillery;
    int cavalry;
    private String playerName;

    public Player(int troops, String name){
        territories = new ArrayList<Territory>();
        cards = new ArrayList<Card>();
        this.troops = troops;
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

    /**
     * Method that provides a way to list ALL cards in hand
     */
    public void getCards(){
        for(int i = 0; i < cards.size(); i++){
            System.out.println(cards.get(i));
        }
    }

    public int getTroops(){
        return troops;
    }

    public void addTroops(int newTroops){
        troops = troops + newTroops;
        System.out.println(playerName + "now has, " + troops + " on this territory");
    }

    public void addTerritories(Territory newTerritory){
        territories.add(newTerritory);
    }

    public void removeTerritories(){
        territories.remove(0);
    }

    public void getTerritories(){
        for(int i = 0; i < territories.size(); i++){
            System.out.println(territories.get(i));
        }
    }

    public void defend(Territory t){

    }
    public void takeTurn(){
        if(territories.size()%3 < 3){
            infantry = infantry + 3;
        }else{
            infantry = infantry + territories.size()%3;
        }

    }

    public String getPlayerName() {
        return playerName;
    }
}
