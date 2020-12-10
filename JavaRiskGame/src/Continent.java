import java.io.Serializable;
import java.util.ArrayList;
/**
 * This class implements a continent
 * @author Tamilore Odunlami
 * @version 1.0.0
 */
public class Continent implements Serializable
{
    private static final long serialVersionUID = 1420672609912364066L;
    private String name;
    private int bonusTroops;
    private ArrayList<Territory> includedTerritories;

    public Continent(String name, ArrayList<Territory> includedTerritories, int bonusTroops){
        this.name = name;
        this.includedTerritories = includedTerritories;
        this.bonusTroops = bonusTroops;
    }

    public String getName(){
        return name;
    }

    public ArrayList<Territory> getIncludedTerritories(){
        return includedTerritories;
    }

    public int getBonusTroops(){
        return bonusTroops;
    }
}
