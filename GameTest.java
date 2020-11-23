import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * This class implements all tests for the game logic and function
 * @author Marko M, Mack W, Mmedara Josiah
 * @version 1.0
 */

public class GameTest {
    Game testGame;
    ArrayList<String> playerNames;
    ArrayList<String> humanOrAi;

    @org.junit.Before
    public void setUp() {
        testGame = new Game();
        playerNames = new ArrayList<>();
        humanOrAi = new ArrayList<>();
    }

    /* ***** CAN ADD PLAYERS ***** */
    @Test
    public void add2PlayerNames(){
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();
        assertEquals("George", testGame.getPlayerNames().get(0));
        assertEquals("David", testGame.getPlayerNames().get(1));
    }

    @Test
    public void add3PlayerNames(){
        playerNames.add("George");
        playerNames.add("David");
        playerNames.add("Mary");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
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
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
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
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
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
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();
        assertEquals("George", testGame.getPlayerNames().get(0));
        assertEquals("David", testGame.getPlayerNames().get(1));
        assertEquals("Mary", testGame.getPlayerNames().get(2));
        assertEquals("Adams", testGame.getPlayerNames().get(3));
        assertEquals("Thomas", testGame.getPlayerNames().get(4));
        assertEquals("Steve", testGame.getPlayerNames().get(5));
    }

    /* **** CAN CREATE PLAYER OBJECTS **** */
    @Test
    public void create2Players() {
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();
        assertEquals(2, testGame.getPlayers().size());
    }

    @Test
    public void create3Players() {
        playerNames.add("George");
        playerNames.add("David");
        playerNames.add("Daniel");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();
        assertEquals(3, testGame.getPlayers().size());
    }

    @Test
    public void create4Players(){
        playerNames.add("George");
        playerNames.add("David");
        playerNames.add("Daniel");
        playerNames.add("Alex");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
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
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
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
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();
        assertEquals(6, testGame.getPlayers().size());
    }

    @Test
    public void autoDeploy2Players(){
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
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

        // check that each player has at least one territory
        for(Player p: testGame.getPlayers()){
            assertTrue(p.getTerritories().size() > 1);
        }

        // check that each player has appropriate # of soldiers on board
        for(Player p: testGame.getPlayers()) {
            int soldiersPlaced = 0;
            for (Territory t : p.getTerritories()){
                soldiersPlaced +=  t.getTotalTroops();
            }
            assertTrue(soldiersPlaced > 1);
        }
    }

    @Test
    public void autoDeploy3Players(){
        playerNames.add("George");
        playerNames.add("David");
        playerNames.add("Daniel");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
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

        // check that each player has at least one territory
        for(Player p: testGame.getPlayers()){
            assertTrue(p.getTerritories().size() > 1);
        }

        // check that each player has appropriate # of soldiers on board
        for(Player p: testGame.getPlayers()) {
            int soldiersPlaced = 0;
            for (Territory t : p.getTerritories()){
                soldiersPlaced +=  t.getTotalTroops();
            }
            assertTrue(soldiersPlaced > 1);
        }
    }

    @Test
    public void autoDeploy4Players(){
        playerNames.add("George");
        playerNames.add("David");
        playerNames.add("Daniel");
        playerNames.add("Alex");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
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

        // check that each player has at least one territory
        for(Player p: testGame.getPlayers()){
            assertTrue(p.getTerritories().size() > 1);
        }

        // check that each player has appropriate # of soldiers on board
        for(Player p: testGame.getPlayers()) {
            int soldiersPlaced = 0;
            for (Territory t : p.getTerritories()){
                soldiersPlaced +=  t.getTotalTroops();
            }
            assertTrue(soldiersPlaced > 1);
        }
    }

    @Test
    public void autoDeploy5Players(){
        playerNames.add("George");
        playerNames.add("David");
        playerNames.add("Daniel");
        playerNames.add("Alex");
        playerNames.add("Jones");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
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
            assertTrue(p.getTerritories().size() > 1);
        }

        // check that each player has appropriate # of soldiers on board
        for(Player p: testGame.getPlayers()) {
            int soldiersPlaced = 0;
            for (Territory t : p.getTerritories()){
                soldiersPlaced +=  t.getTotalTroops();
            }
            assertTrue(soldiersPlaced > 1);
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
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
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

        // check that each player has at least one territory
        for(Player p: testGame.getPlayers()){
            assertTrue(p.getTerritories().size() > 1);
        }

        // check that each player has appropriate # of soldiers on board
        for(Player p: testGame.getPlayers()) {
            int soldiersPlaced = 0;
            for (Territory t : p.getTerritories()){
                soldiersPlaced +=  t.getTotalTroops();
            }
            assertTrue(soldiersPlaced > 1);
        }
    }

    /* *****TURN CAN BE PASSED***** */
    @Test
    public void passPlayer2Players(){
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
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
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        assertTrue(testGame.isDeployed());
        testGame.startGame();

        testGame.nextPlayer();
        testGame.nextPlayer();
        testGame.nextPlayer();
        assertEquals(testGame.getActivePlayer().getPlayerName(), "Alex");
    }

    /* ***** CAN ATTACK TERRITORIES ***** */
    @Test
    public void attackTest() {
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();

        Territory targetTerritory = null;
        Territory originTerritory = null;
        for(Territory t : testGame.getPlayers().get(0).getTerritories()){
            for(Territory a : t.getAdjacencies()){
                if(!testGame.getPlayers().get(0).getTerritories().contains(a)){
                    targetTerritory = t;
                    originTerritory = a;
                }
            }
        }

        testGame.reinforce(testGame.getPlayers().get(0).getTerritories().get(0), testGame.getPlayers().get(0).getTotalTroops());
        testGame.attack(originTerritory, targetTerritory, 1, 1);
        assertTrue(testGame.getHasAttacked());
    }

    /* testing using number 4 as the attacker dice roll */
    @Test
    public void attackBadly1(){
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();
        Territory targetTerritory = null;
        Territory originTerritory = null;
        for(Territory t : testGame.getPlayers().get(0).getTerritories()){
            for(Territory a : t.getAdjacencies()){
                if(!testGame.getPlayers().get(0).getTerritories().contains(a)){
                    targetTerritory = t;
                    originTerritory = a;
                }
            }
        }

        testGame.reinforce(testGame.getPlayers().get(0).getTerritories().get(0), testGame.getPlayers().get(0).getTotalTroops());
        testGame.attack(originTerritory, targetTerritory, 4, 2);
        assertFalse(testGame.getHasAttacked());
    }

    /* testing using number 4 as the defender dice roll */
    @Test
    public void attackBadly2(){
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();
        Territory targetTerritory = null;
        Territory originTerritory = null;
        for(Territory t : testGame.getPlayers().get(0).getTerritories()){
            for(Territory a : t.getAdjacencies()){
                if(!testGame.getPlayers().get(0).getTerritories().contains(a)){
                    targetTerritory = t;
                    originTerritory = a;
                }
            }
        }

        testGame.reinforce(testGame.getPlayers().get(0).getTerritories().get(0), testGame.getPlayers().get(0).getTotalTroops());
        testGame.attack(originTerritory, targetTerritory, 2, 4);
        assertFalse(testGame.getHasAttacked());
    }

    
    // ***** REINFORCE TESTS *****
    @Test
    public void reinforceGoodTerritory(){
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();
        testGame.reinforce(testGame.getPlayers().get(0).getTerritories().get(0), testGame.getPlayers().get(0).getTotalTroops());
        assertTrue(testGame.isCanAttack());
        assertTrue(testGame.isCanFortify());
    }

    @Test
    public void reinforceBadTerritory(){
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();
        testGame.reinforce(testGame.getPlayers().get(1).getTerritories().get(0), testGame.getPlayers().get(0).getTotalTroops()); // Using second players territory
        assertFalse(testGame.isCanAttack());
        assertFalse(testGame.isCanFortify());
    }

    @Test
    public void reinforceLessSoldiers(){
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();
        testGame.reinforce(testGame.getPlayers().get(0).getTerritories().get(0), 0);
        assertFalse(testGame.isCanAttack());
        assertFalse(testGame.isCanFortify());
    }

    @Test
    public void reinforceMoreSoldiers(){
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();
        testGame.reinforce(testGame.getPlayers().get(0).getTerritories().get(0), 200);
        assertFalse(testGame.isCanAttack());
        assertFalse(testGame.isCanFortify());
    }

    @Test
    public void cannotSkipReinforceBad(){
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();
        testGame.attack(testGame.getActivePlayer().getTerritories().get(0), testGame.getActivePlayer().getTerritories().get(0).getAdjacencies()[1], 1,1);  // Need to make it so defender territory is never owned by attacker
        assertFalse(testGame.getHasAttacked());
    }

    @Test
    public void cannotSkipReinforceGood(){
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();
        Territory targetTerritory = null;
        Territory originTerritory = null;
        for(Territory t : testGame.getPlayers().get(0).getTerritories()){
            for(Territory a : t.getAdjacencies()){
                if(!testGame.getPlayers().get(0).getTerritories().contains(a)){
                    targetTerritory = t;
                    originTerritory = a;
                }
            }
        }
        testGame.reinforce(testGame.getPlayers().get(0).getTerritories().get(0), testGame.getPlayers().get(0).getTotalTroops());
        testGame.attack(originTerritory, targetTerritory, 1, 1);
        assertTrue(testGame.getHasAttacked());
    }

    @Test
    public void reinforceSuccessful(){
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();
        int troopsMoved = testGame.getPlayers().get(0).getTotalTroops();
        int troopsInTerritoryBefore = testGame.getPlayers().get(0).getTerritories().get(0).getTotalTroops();
        int troopsHeldBefore = testGame.getPlayers().get(0).getTotalTroops();
        testGame.reinforce(testGame.getPlayers().get(0).getTerritories().get(0), testGame.getPlayers().get(0).getTotalTroops());

        assertEquals(testGame.getPlayers().get(0).getTerritories().get(0).getTotalTroops(), troopsInTerritoryBefore + troopsMoved);
        assertEquals(testGame.getPlayers().get(0).getTotalTroops(), troopsHeldBefore - troopsMoved);
    }

    // ***** FORTIFY TESTS *****
    @Test
    public void fortifyGoodTerritory(){
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();

        Territory targetTerritory = null;
        Territory originTerritory = null;
        for(Territory t : testGame.getPlayers().get(0).getTerritories()){
            for(Territory a : t.getAdjacencies()){
                if(testGame.getPlayers().get(0).getTerritories().contains(a)){
                    targetTerritory = t;
                    originTerritory = a;
                }
            }
        }
        int troopsInTargetBefore = targetTerritory.getTotalTroops();
        int troopsInOriginBefore = originTerritory.getTotalTroops();
        int troopsMoved = 1;

        testGame.reinforce(testGame.getPlayers().get(0).getTerritories().get(0), testGame.getPlayers().get(0).getTotalTroops());
        testGame.fortify(originTerritory, targetTerritory, troopsMoved);

        assertEquals(targetTerritory.getTotalTroops(), troopsInTargetBefore + troopsMoved);
        assertEquals(originTerritory.getTotalTroops(), troopsInOriginBefore - troopsMoved);
    }

    @Test
    public void fortifyBadTerritory(){
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();

        Territory targetTerritory = null;
        Territory originTerritory = null;
        for(Territory t : testGame.getPlayers().get(0).getTerritories()){
            for(Territory a : t.getAdjacencies()){
                if(!testGame.getPlayers().get(0).getTerritories().contains(a)){
                    targetTerritory = t;
                    originTerritory = a;
                }
            }
        }
        int troopsInTargetBefore = targetTerritory.getTotalTroops();
        int troopsInOriginBefore = originTerritory.getTotalTroops();
        int troopsMoved = 1;

        testGame.reinforce(testGame.getPlayers().get(0).getTerritories().get(0), testGame.getPlayers().get(0).getTotalTroops());
        testGame.fortify(originTerritory, targetTerritory, troopsMoved);

        assertEquals(targetTerritory.getTotalTroops(), troopsInTargetBefore);
        assertEquals(originTerritory.getTotalTroops(), troopsInOriginBefore);
    }

    @Test
    public void fortifyBadSoldiers(){
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("Human");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();

        Territory targetTerritory = null;
        Territory originTerritory = null;
        for(Territory t : testGame.getPlayers().get(0).getTerritories()){
            for(Territory a : t.getAdjacencies()){
                if(testGame.getPlayers().get(0).getTerritories().contains(a)){
                    targetTerritory = t;
                    originTerritory = a;
                }
            }
        }
        int troopsInTargetBefore = targetTerritory.getTotalTroops();
        int troopsInOriginBefore = originTerritory.getTotalTroops();
        int troopsMoved = 200;

        testGame.reinforce(testGame.getPlayers().get(0).getTerritories().get(0), testGame.getPlayers().get(0).getTotalTroops());
        testGame.fortify(originTerritory, targetTerritory, troopsMoved);

        assertEquals(targetTerritory.getTotalTroops(), troopsInTargetBefore);
        assertEquals(originTerritory.getTotalTroops(), troopsInOriginBefore);
    }

    @Test
    public void createPlayerAI(){
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("AI");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        assertEquals(2, testGame.getPlayers().size());
        assertTrue("George", testGame.getPlayers().get(0).getAI());
        assertEquals("George", "George");
    }

    @Test
    public void testAIReinforce(){
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("AI");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();
        assertTrue(testGame.isAiHasReinforced());
    }

    @Test
    public void testAIAttacked(){
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("AI");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();
        assertTrue(testGame.isAiHasAttacked());
    }

    @Test
    public void testAIFortified(){
        playerNames.add("George");
        playerNames.add("David");
        humanOrAi.add("AI");
        humanOrAi.add("Human");
        testGame.init(playerNames, humanOrAi);
        testGame.startGame();
        assertTrue(testGame.isAiHasFortified());
    }
}
