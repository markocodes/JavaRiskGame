import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Player class that takes care of the cards, territories and the turn based system this game provides.
 * @author MacKenzie Wallace
 * @version 1.0.0
 */

public class BoardTest {
    private Board board;
    private File file;

    @Before
    public void setUp() throws Exception {
        file = new File("TestMap.xml");
        board = new Board(file);
    }

    @Test
    public void testXML(){
        assertEquals(board.getTerritories().get(0).getTerritoryName(),"Kirghiz");
        assertEquals(board.getTerritories().get(0).getAdjacencies().get(0).getTerritoryName(), "Sinkiang");
    }
}