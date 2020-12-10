import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RiskControllerTest {
    private BoardViewController bvc;
    private Game model;
    private BoardView boardView;
    private PlayerNamesDialog playerNamesDialog;
    private NumberOfPlayersDialog numberOfPlayersDialog;
    private RiskView riskView;
    private ArrayList<String> playerNames;
    private ArrayList<String> humanOrAi;

    @Before
    public void setUp() throws Exception {
        playerNames = new ArrayList<>();
        humanOrAi = new ArrayList<>();

        playerNames.add("John");
        playerNames.add("Alex");

        humanOrAi.add("Human");
        humanOrAi.add("Human");

        riskView = new RiskView();
        numberOfPlayersDialog = new NumberOfPlayersDialog(riskView, true);
        playerNamesDialog = new PlayerNamesDialog(numberOfPlayersDialog, false, 2);
        model = new Game();
        model.init(playerNames, humanOrAi);
        boardView = new BoardView(playerNamesDialog, true, model);
        bvc = new BoardViewController(model, boardView);

    }

    @Test
    public void testSave(){
        bvc.executeCommand("saveButton");
        assertNotNull(bvc.getFileChooser());
    }


}