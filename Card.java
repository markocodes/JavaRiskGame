 /** This class implements a game card
 * @author Mmedara Josiah
 * @version 1.0
 **/
public class Card {

    private Territory territory;
    private String regiment;


     /**
      * Card constructor creates a card with specified territory and regiment within
      * @param territory Territory on card
      * @param regiment Regiment on card
      */
    public Card(Territory territory, String regiment) {
        this.territory = territory;
		this.regiment = regiment;
    }

    /**
     * @return Territory object on the card
     * */
    public Territory getCountry() {
        return this.territory;
    }

    /**
     * @return Return regiment on card
     */
    public String getRegiment() {
        return this.regiment;
    }


	public String getName() {
		return this.territory.getTerritoryName() + " - Regiment: " + regiment;
	}
}

