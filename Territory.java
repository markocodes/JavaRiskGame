import java.util.ArrayList;

/**
 * Territory class to illustrate an individual territory within the Risk map
 * @author Marko M
 * @version 1.0.0
 */
public class Territory {
    private String name;
    private ArrayList<Territory> adjacencies;
    private Boolean isOccupied;
    private Player territoryOccupant;
    private int numOccupants;

    /**
     * Constructor to initialize a new territory on the Risk map
     * @param name name of the territory
     * @param adjacencies arrayList containing the countries adjacencies
     * @param territoryOccupant player who is occupying the territory
     * @param numOccupants number of occupying soldiers in the territory
     */
    public Territory(String name, ArrayList<Territory> adjacencies, Player territoryOccupant, int numOccupants) {
        this.name = name;
        this.adjacencies = adjacencies;
        this.isOccupied = false;
        this.territoryOccupant = null;
        this.numOccupants = 0;
    }

    /**
     * Method to execute attack on another adjacent territory
     * @param territory the territory to attack
     */
    public void Attack(Territory territory){
        if(adjacencies.contains(territory)){
            System.out.println("How many soldiers would you like to engage in the attack?");
            // Take user input here.
            // Check that user input is not less than 1 and not more than 3.
            // Roll dice, compare dice
            // Call appropriate method to handle attack outcome.
        }
    }
}
