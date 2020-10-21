 /** This class implements a game card
 * @author Mmedara Josiah
 * @version 1.0
 **/
public class Card {

    private Country country;
    private String regiment;

    public Card(Country country, String regiment) {
        this.country = country;
		this.regiment = regiment;
    }

    public Country getCountry() {
        return this.country;
    }

    public String getRegiment() {
        return this.regiment;
    }

	public String getName() {
		return this.country.getName() + " - Regiment: " + regiment;
	}
}

