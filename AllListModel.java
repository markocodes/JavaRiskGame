import java.util.Observer;
import java.util.Observable;
import javax.swing.DefaultListModel;

/** This class models all the lists used in the game
 * @author Tamilore Odunlami
 * @version 1.0
 */
public class AllListModel extends DefaultListModel implements Observer {
    private String type;
    private String display;
    private Game model;

    /**
     * This constructs the list Model
     * @param model is the game object
     * @param type is the name of the object
     */
    public AllListModel(Game model, String type) {
        super();
        this.model = model;
        this.type = type;
    }

    /**
     * This updates the state of all the Lists
     * @param obs is the observable object
     * @param obj is the object being observed
     */
    public void update(Observable obs, Object obj)
    {
        display = (String) obj;
        if (type == "player1" && type == display)
        {
            removeAllElements();
            for (int i = 0; i < model.getListOfPlayerTerritories(1).size(); i++)
            {
                addElement(model.getListOfPlayerTerritories(1).get(i));
            }
        }
        else if (type == "player2" && type == display)
        {
            removeAllElements();
            for (int i = 0; i < model.getListOfPlayerTerritories(2).size(); i++)
            {
                addElement(model.getListOfPlayerTerritories(2).get(i));
            }
        }
        else if (type == "player3" && type == display)
        {
            removeAllElements();
            for (int i = 0; i < model.getListOfPlayerTerritories(3).size(); i++)
            {
                addElement(model.getListOfPlayerTerritories(3).get(i));
            }
        }
        else if (type == "player4" && type == display)
        {
            removeAllElements();
            for (int i = 0; i < model.getListOfPlayerTerritories(4).size(); i++)
            {
                addElement(model.getListOfPlayerTerritories(4).get(i));
            }
        }
        else if (type == "player5" && type == display)
        {
            removeAllElements();
            for (int i = 0; i < model.getListOfPlayerTerritories(5).size(); i++)
            {
                addElement(model.getListOfPlayerTerritories(5).get(i));
            }
        }
        else if (type == "player6" && type == display)
        {
            removeAllElements();
            for (int i = 0; i < model.getListOfPlayerTerritories(6).size(); i++)
            {
                addElement(model.getListOfPlayerTerritories(6).get(i));
            }
        }
        else if (type == "adjacent" && type == display)
        {
            removeAllElements();
            for (int i=0; i<model.getListOfAdjacentsOfSelectedTerritory().size(); i++){
                addElement(model.getListOfAdjacentsOfSelectedTerritory().get(i));
            }
        }
        else if (type == "africa" && type == display)
        {
            removeAllElements();
            for (int i=0; i<model.getListOfContinentTerritories(1).size(); i++){
                addElement(model.getListOfContinentTerritories(1).get(i));
            }
        }
        else if (type == "asia" && type == display)
        {
            removeAllElements();
            for (int i=0; i<model.getListOfContinentTerritories(2).size(); i++){
                addElement(model.getListOfContinentTerritories(2).get(i));
            }
        }
        else if (type == "australia" && type == display)
        {
            removeAllElements();
            for (int i=0; i<model.getListOfContinentTerritories(3).size(); i++){
                addElement(model.getListOfContinentTerritories(3).get(i));
            }
        }
        else if (type == "europe" && type == display)
        {
            removeAllElements();
            for (int i=0; i<model.getListOfContinentTerritories(4).size(); i++){
                addElement(model.getListOfContinentTerritories(4).get(i));
            }
        }
        else if (type == "northAmerica" && type == display)
        {
            removeAllElements();
            for (int i=0; i<model.getListOfContinentTerritories(5).size(); i++){
                addElement(model.getListOfContinentTerritories(5).get(i));
            }
        }
        else if (type == "southAmerica" && type == display)
        {
            removeAllElements();
            for (int i=0; i<model.getListOfContinentTerritories(6).size(); i++){
                addElement(model.getListOfContinentTerritories(6).get(i));
            }
        }
    }
}
