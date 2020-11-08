import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {
    Game testGame;
    ArrayList<String> playerNames;

    @org.junit.Before
    public void setUp() {
        testGame = new Game();
        playerNames = new ArrayList<>();
    }

    @Test
    public void add2PlayerNames(){
        playerNames.add("George");
        playerNames.add("David");
        testGame.init(playerNames);
        testGame.startGame();
        assertEquals("George", testGame.getPlayerNames().get(0));
        assertEquals("David", testGame.getPlayerNames().get(1));
    }

    @Test
    public void add3PlayerNames(){
        playerNames.add("George");
        playerNames.add("David");
        playerNames.add("Mary");
        testGame.init(playerNames);
        testGame.startGame();
        assertEquals("George", testGame.getPlayerNames().get(0));
        assertEquals("David", testGame.getPlayerNames().get(1));
        assertEquals("Mary", testGame.getPlayerNames().get(2));
    }

    @Test
    public void add4PlayerNames(){
        playerNames.add("George");
        playerNames.add("David");
        playerNames.add("Mary");
        playerNames.add("Adams");
        testGame.init(playerNames);
        testGame.startGame();
        assertEquals("George", testGame.getPlayerNames().get(0));
        assertEquals("David", testGame.getPlayerNames().get(1));
        assertEquals("Mary", testGame.getPlayerNames().get(2));
        assertEquals("Adams", testGame.getPlayerNames().get(3));
    }

    @Test
    public void add5PlayerNames(){
        playerNames.add("George");
        playerNames.add("David");
        playerNames.add("Mary");
        playerNames.add("Adams");
        playerNames.add("Thomas");
        testGame.init(playerNames);
        testGame.startGame();
        assertEquals("George", testGame.getPlayerNames().get(0));
        assertEquals("David", testGame.getPlayerNames().get(1));
        assertEquals("Mary", testGame.getPlayerNames().get(2));
        assertEquals("Adams", testGame.getPlayerNames().get(3));
        assertEquals("Thomas", testGame.getPlayerNames().get(4));
    }

    @Test
    public void add6PlayerNames(){
        playerNames.add("George");
        playerNames.add("David");
        playerNames.add("Mary");
        playerNames.add("Adams");
        playerNames.add("Thomas");
        playerNames.add("Steve");
        testGame.init(playerNames);
        testGame.startGame();
        assertEquals("George", testGame.getPlayerNames().get(0));
        assertEquals("David", testGame.getPlayerNames().get(1));
        assertEquals("Mary", testGame.getPlayerNames().get(2));
        assertEquals("Adams", testGame.getPlayerNames().get(3));
        assertEquals("Thomas", testGame.getPlayerNames().get(4));
        assertEquals("Steve", testGame.getPlayerNames().get(5));
    }

    @Test
    public void attack() {
        Board board = new Board();
        playerNames.add("George");
        playerNames.add("David");
        testGame.init(playerNames);
        testGame.startGame();
        testGame.attack(testGame.getPlayers().get(0).getTerritories().get((int)(Math.random()*21)), testGame.getPlayers().get(1).getTerritories().get((int)(Math.random()*21)), 2, 2);
        assertTrue(testGame.getHasAttacked());
    }
}