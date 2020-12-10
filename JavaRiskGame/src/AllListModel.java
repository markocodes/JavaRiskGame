import javax.swing.*;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/** This class models all the lists used in the game
 * @author Tamilore Odunlami
 * @version 1.0
 */
public class AllListModel extends DefaultListModel implements Observer, Serializable
{
    private static final long serialVersionUID = 1420672609912364067L;
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
        for(int i=0; i<model.getPlayerNames().size(); i++) {
            if (type == model.getPlayerNames().get(i) && type == display) {
                removeAllElements();
                for (int j = 0; j < model.getListOfPlayerTerritories(i).size(); j++) {
                    addElement(model.getListOfPlayerTerritories(i).get(j));
                }
            }
        }
        for(int i=0; i<model.getBoard().getContinents().size(); i++) {
            if (type == model.getBoard().getContinents().get(i).getName() && type == display) {
                removeAllElements();
                for (int j = 0; j < model.getListOfContinentTerritories(i).size(); j++) {
                    addElement(model.getListOfContinentTerritories(i).get(j));
                }
            }
        }
        if (type == "adjacent" && type == display)
        {
            removeAllElements();
            for (int i=0; i<model.getListOfAdjacentsOfSelectedTerritory().size(); i++){
                addElement(model.getListOfAdjacentsOfSelectedTerritory().get(i));
            }
        }
    }
}
