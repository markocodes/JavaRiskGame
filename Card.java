 /** This class implements a game card
 * @author Mmedara Josiah
 * @version 1.0
 **/
public class Card {

    private Territory territory;
    private String regiment;

    public Card(Territory territory, String regiment) {
        this.territory = territory;
		this.regiment = regiment;
    }

    public Territory getCountry() {
        return this.territory;
    }

    public String getRegiment() {
        return this.regiment;
    }

	public String getName() {
		return this.territory.getTerritoryName() + " - Regiment: " + regiment;
	}
}

