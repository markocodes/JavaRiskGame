import java.io.Serializable;
import java.util.*;

/**
 * This class implements a game dice
 * @author Mmedara Josiah
 * @version 1.0
 **/
public class Dice implements Serializable
{
    private static final long serialVersionUID = 1420672609912364062L;

    private int[] rollResults;
    private Random r;

    public Dice() {
	r = new Random();
    }

    /**
     * Rolls the dice n number of times
	 * stores all results in the rollResults array
	 * sorts the array in descending order
	 * @param n is the number of times to roll the dice
     **/
    public int[] rollDice(int n) {
		rollResults = new int[n];
		for(int i: rollResults) {
			rollResults[i] = 1 + r.nextInt((6 - 1) + 1);
		}
		//return rollResults in descending order because we always need the
		// highest result first and second highest next and so on
		Arrays.sort(rollResults);
		//store the rollResults array as list using the asList() method
		Collections.reverse(Arrays.asList(rollResults));
		return rollResults;
    }
}
