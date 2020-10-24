import java.util.Arrays;
import java.util.List;

/**
 * Territory class to illustrate an individual territory within the Risk map
 * @author Marko M
 * @version 1.1.0
 */
public class Territory {

    private String territoryName;
    private Territory[] adjacencies;
    private Boolean isOccupied;
    private Player territoryOccupant;
    private int numOccupants;

    /**
     * Constructor to initialize a new territory on the Risk map
     * @param territoryName name of the territory
     */
    public Territory(String territoryName) {
        this.territoryName = territoryName;
        adjacencies = null;
        isOccupied = false;
        territoryOccupant = null;
        numOccupants = 0;
    }

    /**
     * Method to execute attack on another adjacent territory
     * @param territory the territory to attack
     */
    public Player Attack(Territory territory, int attackerTroops, Dice dice){
        int attackerPoints = 0;
        int defenderPoints = 0;

        int defenderTroops = territory.getNumOccupants();

        List<Territory> terrList = Arrays.asList(this.adjacencies);

        // Check if territory to be attacked is adjacent to current territory.
        if(terrList.contains(territory)){

            // Check if the attacker is the current occupier of the territory they wish to attack.
            if(!this.territoryOccupant.getPlayerName().equals(territory.getTerritoryOccupant().getPlayerName())) {
                if (attackerTroops < 4 && attackerTroops > 0) {

                    int[] attackerRolls = dice.rollDice(attackerTroops);
                    int[] defenderRolls = dice.rollDice(defenderTroops);

                    // Compare dice and allocate points (Scenario 1)
                    if (attackerRolls.length >= defenderRolls.length) {
                        for (int i = 0; i < defenderRolls.length; i++) {
                            if (attackerRolls[i] > defenderRolls[i]) {
                                attackerPoints += 1;
                            } else if (attackerRolls[i] <= defenderRolls[i]) {
                                defenderPoints += 1;
                            }
                        }

                        // Determine winner
                        if (attackerPoints > defenderPoints) {
                            return this.territoryOccupant;
                        } else {
                            return territory.getTerritoryOccupant();
                        }


                    }
                    // // Compare dice and allocate points (Scenario 2)
                    else if (attackerRolls.length < defenderRolls.length) {
                        for (int i = 0; i < attackerRolls.length; i++) {
                            if (attackerRolls[i] > defenderRolls[i]) {
                                attackerPoints += 1;
                            } else if (attackerRolls[i] <= defenderRolls[i]) {
                                defenderPoints += 1;
                            }
                        }
                        // Determine winner
                        if (attackerPoints > defenderPoints) {
                            return this.territoryOccupant;
                        } else {
                            return territory.getTerritoryOccupant();
                        }
                    }
                }else{
                    System.out.println("Please pick a number of troops between 1 and 3.");
                    return null;
                }
            }else{
                System.out.println("This territory belongs to the attacker. Cannot attack already occupied territory.");
                return null;
            }
        }else{
            System.out.println("You cannot attack this territory. Please pick an adjacent territory.");
            return null;
        }
        return null;
    }

    /**
     * Add arrray of ajdacencies to the Territory object.
     * @param adj Array of adjacent Territory objects
     */
    public void addAdjacencies(Territory[] adj){
        this.adjacencies = adj;
    }


    /* Getters and Setters */

    /**
     * @return name of the Territory
     */
    public String getTerritoryName() {
        return territoryName;
    }

    /**
     * @return number of troops in the Territory
     */
    public int getNumOccupants() {
        return numOccupants;
    }

    /**
     * @return the Player object that is occupying the Territory
     */
    public Player getTerritoryOccupant() {
        return territoryOccupant;
    }

}
