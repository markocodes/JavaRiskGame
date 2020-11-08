/**
 * This class implements a continent
 * @author Odunlami Tami
 * @version 1.0.0
 */

import java.util.ArrayList;

public class Continent
{
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
