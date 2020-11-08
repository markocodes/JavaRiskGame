import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * This class implements all tests for the game logic and function
 * @author Marko M, Mack W
 * @version 1.0
 */

public class GameTest {
    Game testGame;
    ArrayList<String> playerNames;

    @org.junit.Before
    public void setUp() {
        testGame = new Game();
        playerNames = new ArrayList<>();
    }

    /* ***** CAN ADD PLAYERS ***** */
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

    /* ***** CAN ATTACK TERRITORIES ***** */
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

    /* **** CAN CREATE PLAYER OBJECTS **** */
    @Test
    public void create2Players() {
        playerNames.add("George");
        playerNames.add("David");
        testGame.init(playerNames);
        testGame.startGame();
        assertEquals(2, testGame.getPlayers().size());
    }

    @Test
    public void create3Players() {
        playerNames.add("George");
        playerNames.add("David");
        playerNames.add("Daniel");
        testGame.init(playerNames);
        testGame.startGame();
        assertEquals(3, testGame.getPlayers().size());
    }

    @Test
    public void create4Players(){
        playerNames.add("George");
        playerNames.add("David");
        playerNames.add("Daniel");
        playerNames.add("Alex");
        testGame.init(playerNames);
        testGame.startGame();
        assertEquals(4, testGame.getPlayers().size());
    }

    @Test
    public void create5Players(){
        playerNames.add("George");
        playerNames.add("David");
        playerNames.add("Daniel");
        playerNames.add("Alex");
        playerNames.add("Jones");
        testGame.init(playerNames);
        testGame.startGame();
        assertEquals(5, testGame.getPlayers().size());
    }

    @Test
    public void create6Players(){
        playerNames.add("George");
        playerNames.add("David");
        playerNames.add("Daniel");
        playerNames.add("Alex");
        playerNames.add("Jones");
        playerNames.add("James");
        testGame.init(playerNames);
        testGame.startGame();
        assertEquals(6, testGame.getPlayers().size());
    }

    @Test
    public void autoDeploy2Players(){
        playerNames.add("George");
        playerNames.add("David");
        testGame.init(playerNames);
        assertTrue(testGame.isDeployed());
        testGame.startGame();

        // check that each territory has an occupant
        int occupied = 0;
        for(Territory t: testGame.getBoard().getAllTerritories()){
            while(occupied < 38){
                if(t.getTerritoryOccupant() != null){
                    occupied++;
                }
            }
        }
        assertEquals(occupied, 38);

        // check that each player has at least 19 territories
        for(Player p: testGame.getPlayers()){
            assertTrue(p.getTerritories().size() > 18);
        }

        // check that each player has appropriate # of soldiers on board
        for(Player p: testGame.getPlayers()) {
            int soldiersPlaced = 0;
            for (Territory t : p.getTerritories()){
                soldiersPlaced +=  t.getTotalTroops();
            }
            assertTrue(soldiersPlaced > 45);
        }
    }

    @Test
    public void autoDeploy3Players(){
        playerNames.add("George");
        playerNames.add("David");
        playerNames.add("Daniel");
        testGame.init(playerNames);
        assertTrue(testGame.isDeployed());
        testGame.startGame();

        // check that each territory has an occupant
        int occupied = 0;
        for(Territory t: testGame.getBoard().getAllTerritories()){
            while(occupied < 38){
                if(t.getTerritoryOccupant() != null){
                    occupied++;
                }
            }
        }
        assertEquals(occupied, 38);

        // check that each player has at least 12 territories
        for(Player p: testGame.getPlayers()){
            assertTrue(p.getTerritories().size() > 11);
        }

        // check that each player has appropriate # of soldiers on board
        for(Player p: testGame.getPlayers()) {
            int soldiersPlaced = 0;
            for (Territory t : p.getTerritories()){
                soldiersPlaced +=  t.getTotalTroops();
            }
            assertTrue(soldiersPlaced > 30);
        }
    }

    @Test
    public void autoDeploy4Players(){
        playerNames.add("George");
        playerNames.add("David");
        playerNames.add("Daniel");
        playerNames.add("Alex");
        testGame.init(playerNames);
        assertTrue(testGame.isDeployed());
        testGame.startGame();

        // check that each territory has an occupant
        int occupied = 0;
        for(Territory t: testGame.getBoard().getAllTerritories()){
            while(occupied < 38){
                if(t.getTerritoryOccupant() != null){
                    occupied++;
                }
            }
        }
        assertEquals(occupied, 38);

        // check that each player has at least 8 territories
        for(Player p: testGame.getPlayers()){
            assertTrue(p.getTerritories().size() > 7);
        }

        // check that each player has appropriate # of soldiers on board
        for(Player p: testGame.getPlayers()) {
            int soldiersPlaced = 0;
            for (Territory t : p.getTerritories()){
                soldiersPlaced +=  t.getTotalTroops();
            }
            assertTrue(soldiersPlaced > 25);
        }
    }

    @Test
    public void autoDeploy5Players(){
        playerNames.add("George");
        playerNames.add("David");
        playerNames.add("Daniel");
        playerNames.add("Alex");
        playerNames.add("Jones");
        testGame.init(playerNames);
        assertTrue(testGame.isDeployed());
        testGame.startGame();

        // check that each territory has an occupant
        int occupied = 0;
        for(Territory t: testGame.getBoard().getAllTerritories()){
            while(occupied < 38){
                if(t.getTerritoryOccupant() != null){
                    occupied++;
                }
            }
        }
        assertEquals(occupied, 38);

        // check that each player has at least 6 territories
        for(Player p: testGame.getPlayers()){
            assertTrue(p.getTerritories().size() > 5);
        }

        // check that each player has appropriate # of soldiers on board
        for(Player p: testGame.getPlayers()) {
            int soldiersPlaced = 0;
            for (Territory t : p.getTerritories()){
                soldiersPlaced +=  t.getTotalTroops();
            }
            assertTrue(soldiersPlaced > 18);
        }
    }

    @Test
    public void autoDeploy6Players(){
        playerNames.add("George");
        playerNames.add("David");
        playerNames.add("Daniel");
        playerNames.add("Alex");
        playerNames.add("Jones");
        playerNames.add("James");
        testGame.init(playerNames);
        assertTrue(testGame.isDeployed());
        testGame.startGame();

        // check that each territory has an occupant
        int occupied = 0;
        for(Territory t: testGame.getBoard().getAllTerritories()){
            while(occupied < 38){
                if(t.getTerritoryOccupant() != null){
                    occupied++;
                }
            }
        }
        assertEquals(occupied, 38);

        // check that each player has at least 4 territories
        for(Player p: testGame.getPlayers()){
            assertTrue(p.getTerritories().size() > 3);
        }

        // check that each player has appropriate # of soldiers on board
        for(Player p: testGame.getPlayers()) {
            int soldiersPlaced = 0;
            for (Territory t : p.getTerritories()){
                soldiersPlaced +=  t.getTotalTroops();
            }
            assertTrue(soldiersPlaced > 14);
        }
    }

    /* *****TURN CAN BE PASSED***** */
    @Test
    public void passPlayer2Players(){
        playerNames.add("George");
        playerNames.add("David");
        testGame.init(playerNames);
        assertTrue(testGame.isDeployed());
        testGame.startGame();

        testGame.nextPlayer();
        assertEquals(testGame.getActivePlayer().getPlayerName(), "David");
    }

    @Test
    public void passPlayerMoreThan2Players(){
        playerNames.add("George");
        playerNames.add("David");
        playerNames.add("Daniel");
        playerNames.add("Alex");
        playerNames.add("Jones");
        playerNames.add("James");
        testGame.init(playerNames);
        assertTrue(testGame.isDeployed());
        testGame.startGame();

        testGame.nextPlayer();
        testGame.nextPlayer();
        testGame.nextPlayer();
        assertEquals(testGame.getActivePlayer().getPlayerName(), "Alex");
    }
}