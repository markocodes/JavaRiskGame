import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * Territory class to illustrate an individual territory within the Risk map
 * @author Marko M
 * @version 1.1.0
 */
public class Territory extends DefaultListModel
{

    private String territoryName;
    private Territory[] adjacencies;
    private Boolean isOccupied;
    private Player territoryOccupant;
    private int infantry;
    private int cavalry;
    private int artillery;
    private Dice dice;

    /**
     * Constructor to initialize a new territory on the Risk map
     * @param territoryName name of the territory
     */
    public Territory(String territoryName) {
        this.territoryName = territoryName;
        adjacencies = null;
        isOccupied = false;
        territoryOccupant = null;
        infantry = 0;
        cavalry = 0;
        artillery = 0;
        dice = new Dice();
    }


    /**
     * Add arrray of ajdacencies to the Territory object.
     * @param adj Array of adjacent Territory objects
     */
    public void addAdjacencies(Territory[] adj){
        this.adjacencies = adj;
    }

    public Territory[] getAdjacencies(){
        return adjacencies;
    }

    public void addInfantry(int amount){
        infantry += amount;
        if(amount==1) System.out.println(territoryName + " has gained " + amount + " troop");
        else{
            System.out.println(territoryName + " has gained " + amount + " troops");
        }
    }

    public void removeInfantry(int amount){
        infantry -= amount;
        if(amount==1) System.out.println(territoryName + " has lost " + amount + " troop");
        else{
            System.out.println(territoryName + " has lost " + amount + " troops");
        }
    }

    /* Getters and Setters */

    /**
     * @return name of the Territory
     */
    public String getTerritoryName() {
        return territoryName;
    }

    /**
     * @return the Player object that is occupying the Territory
     */
    public Player getTerritoryOccupant() {
        return territoryOccupant;
    }

    public void setTerritoryOccupant(Player territoryOccupant) {
        this.territoryOccupant = territoryOccupant;
    }

    /**
     * @return number of troops in the Territory
     */
    public int getTotalTroops(){
        return infantry + (5 * cavalry) + (10 * artillery);
    }

    /**
     * prints the state of the Territory
     */
    public String toString(){
        return this.getTerritoryName() + " - " + this.getTotalTroops();
    }
}
