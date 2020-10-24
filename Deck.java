import java.util.*;

/**
 * This class models the deck with 42 cards
 * @author Mmedara Josiah
 * @version 1.0
 **/
public class Deck{

	private String infantry = "Infantry";
	private String cavalry = "Cavalry";
	private String artillery = "Artillery";
	private Card selected;
	private List<Card> deck;

	/**
	 * The deck should have 42 cards. Each country is evenly distributed
	 * with either Artillery, Cavalry and Infantry as it's type
	 * @param territories is an arraylist of 42 territories
	**/
	public Deck (ArrayList<Territory> territories){
		deck = new ArrayList<>();
		Collections.shuffle(territories);
		for(int i =0; i<14; i++){
			Card newCardOnDeck = new Card(territories.get(i), infantry);
			deck.add(newCardOnDeck);
			System.out.println(deck.get(i).getName() + " has been added to the deck.");
		}
		for(int i =14; i<28; i++){
			Card newCardOnDeck = new Card(territories.get(i), cavalry);
			deck.add(newCardOnDeck);
			System.out.println(deck.get(i).getName() + " has been added to the deck.");
		}
		for(int i =28; i<42; i++){
			Card newCardOnDeck = new Card(territories.get(i), artillery);
			deck.add(newCardOnDeck);
			System.out.println(deck.get(i).getName() + " has been added to the deck.");
		}
		Collections.shuffle(deck);
	}

	/**
	* Removes and returns the first card from the deck
	**/
	public Card selectCard(){
		selected = deck.get(0);
		deck.remove(0);
		return selected;
	}
}
