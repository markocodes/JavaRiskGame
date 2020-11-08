import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {
    Game testGame;
    ArrayList<String> playerNames;

    @org.junit.Before
    public void setUp() throws Exception {
        testGame = new Game();
        playerNames = new ArrayList<>();
    }
}