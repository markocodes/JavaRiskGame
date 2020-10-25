import java.util.ArrayList;

/**
 * Board class to illustrate the game board and territory locations in relation to one another.
 * The board has 42 Territories.
 * @author Marko M
 * @version 1.0.0
 */

public class Board {

    private Territory[] territoryList;

    private Territory alaska, alberta, centralAmerica, easternUnitedStates, greenland,
            northWestTerritory, ontario, quebec, westernUnitedStates, argentina, brazil, peru, venezuela,
            greatBritain, iceland, northernEurope, scandinavia, southernEurope, ukraine, westernEurope, congo,
            eastAfrica, egypt, madagascar, northAfrica, southAfrica, afghanistan, china, india, irkutsk, japan, kamchatka, middleEast,
            mongolia, siam, siberia, ural, yakutsk, easternAustralia, indonesia, newGuinea, westernAustralia;

    private Territory[] alaskaAdjacencies, albertaAdjacencies, centralAmericaAdjacencies, easternUnitedStatesAdjacencies, greenlandAdjacencies,
            northWestTerritoryAdjacencies, ontarioAdjacencies, quebecAdjacencies, westernUnitedStatesAdjacencies, argentinaAdjacencies, brazilAdjacencies,
            peruAdjacencies, venezuelaAdjacencies, greatBritainAdjacencies, icelandAdjacencies, northernEuropeAdjacencies, scandinaviaAdjacencies,
            southernEuropeAdjacencies, ukraineAdjacencies, westernEuropeAdjacencies, congoAdjacencies, eastAfricaAdjacencies, egyptAdjacencies,
            madagascarAdjacencies, northAfricaAdjacencies, southAfricaAdjacencies, afghanistanAdjacencies, chinaAdjacencies, indiaAdjacencies, irkutskAdjacencies, japanAjacencies,
            kamchatkaAdjacencies, middleEastAdjacencies, mongoliaAdjacencies, siamAdjacencies, siberiaAdjacencies, uralAdjacencies, yakutskAdjacencies,
            easternAustraliaAdjacencies, indonesiaAdjacencies, newGuineaAdjacencies, westernAustraliaAdjacencies;


    /**
     * Board constructor to initialize all territory objects and set their adjacencies.
     */
    public Board() {

        // Initialize territories
        alaska = new Territory("Alaska");
        alberta = new Territory("Alberta");
        centralAmerica = new Territory("Central America");
        easternUnitedStates = new Territory("Eastern United States");
        greenland = new Territory("Greenland");
        northWestTerritory = new Territory("Northwest Territory");
        ontario = new Territory("Ontario");
        quebec = new Territory("Quebec");
        westernUnitedStates = new Territory("Western United States");
        argentina = new Territory("Argentina");
        brazil = new Territory("Brazil");
        peru = new Territory("Peru");
        venezuela = new Territory("Venezuela");
        greatBritain = new Territory("Great Britain");
        iceland = new Territory("Iceland");
        northernEurope = new Territory("Northern Europe");
        scandinavia = new Territory("Scandinavia");
        southernEurope = new Territory("Southern Europe");
        ukraine = new Territory("Ukraine");
        westernEurope = new Territory("Western Europe");
        congo = new Territory("Congo");
        eastAfrica = new Territory("East Africa");
        egypt = new Territory("Egypt");
        madagascar = new Territory("Madagascar");
        northAfrica = new Territory("NorthAfrica");
        southAfrica = new Territory("SouthAfrica");
        afghanistan = new Territory("Afghanistan");
        china = new Territory("China");
        india = new Territory("India");
        irkutsk = new Territory("Irkutsk");
        japan = new Territory("Japan");
        kamchatka = new Territory("Kamchatka");
        middleEast = new Territory("MiddleEast");
        mongolia = new Territory("Mongolia");
        siam = new Territory("Siam");
        siberia = new Territory("Siberia");
        ural = new Territory("Ural");
        yakutsk = new Territory("Yakutsk");
        easternAustralia = new Territory("Eastern Australia");
        indonesia = new Territory("Indonesia");
        newGuinea = new Territory("New Guinea");
        westernAustralia = new Territory("Scandinavia");

        // Initialize arrays of Territory objects to contain each countries adjacent territories
        alaskaAdjacencies = new Territory[]{alberta, northWestTerritory, kamchatka};
        albertaAdjacencies = new Territory[]{alaska, northWestTerritory, ontario, westernUnitedStates};
        centralAmericaAdjacencies = new Territory[]{westernUnitedStates, venezuela};
        easternUnitedStatesAdjacencies = new Territory[]{westernUnitedStates, ontario, quebec};
        greenlandAdjacencies = new Territory[]{northWestTerritory, ontario, quebec, iceland};
        northWestTerritoryAdjacencies = new Territory[]{alaska, greenland, ontario, alberta};
        ontarioAdjacencies = new Territory[]{northWestTerritory, greenland, quebec, easternUnitedStates, westernUnitedStates, alberta};
        quebecAdjacencies = new Territory[]{ontario, greenland, easternUnitedStates};
        westernUnitedStatesAdjacencies = new Territory[]{alberta, ontario, easternUnitedStates, centralAmerica};
        argentinaAdjacencies = new Territory[]{venezuela, brazil};
        brazilAdjacencies = new Territory[]{venezuela, northAfrica, argentina};
        peruAdjacencies = new Territory[]{venezuela, brazil, argentina};
        venezuelaAdjacencies = new Territory[]{northAfrica, argentina};
        greatBritainAdjacencies = new Territory[]{iceland, scandinavia, northernEurope, westernEurope};
        icelandAdjacencies = new Territory[]{greenland, greatBritain};
        northernEuropeAdjacencies = new Territory[]{greatBritain, ukraine, southernEurope, westernEurope};
        scandinaviaAdjacencies = new Territory[]{greatBritain, ukraine};
        southernEuropeAdjacencies = new Territory[]{westernEurope, northernEurope, ukraine, egypt, northAfrica};
        ukraineAdjacencies = new Territory[]{scandinavia, ural, afghanistan, middleEast, southernEurope, northernEurope};
        westernEuropeAdjacencies = new Territory[]{greatBritain, northernEurope, southernEurope, northAfrica};
        congoAdjacencies = new Territory[]{northAfrica, egypt, eastAfrica, southAfrica};
        eastAfricaAdjacencies = new Territory[]{egypt, madagascar, southAfrica, congo};
        egyptAdjacencies = new Territory[]{southernEurope, middleEast, eastAfrica, congo,  northAfrica};
        madagascarAdjacencies = new Territory[]{eastAfrica, southAfrica};
        northAfricaAdjacencies = new Territory[]{westernEurope, southernEurope, egypt, congo, brazil};
        southAfricaAdjacencies = new Territory[]{congo, eastAfrica, madagascar};
        afghanistanAdjacencies = new Territory[]{ukraine, ural, siberia, china, india, middleEast};
        chinaAdjacencies = new Territory[]{afghanistan, siberia, mongolia, siam, india};
        indiaAdjacencies = new Territory[]{middleEast, afghanistan, china, siam};
        irkutskAdjacencies = new Territory[]{siberia, yakutsk, kamchatka, mongolia};
        japanAjacencies = new Territory[]{kamchatka, mongolia};
        kamchatkaAdjacencies = new Territory[]{yakutsk, alaska, japan, mongolia, irkutsk};
        middleEastAdjacencies = new Territory[]{ukraine, afghanistan, india, egypt};
        mongoliaAdjacencies = new Territory[]{siberia, irkutsk, kamchatka, japan, china};
        siamAdjacencies = new Territory[]{india, china, indonesia};
        siberiaAdjacencies = new Territory[]{ural, yakutsk, irkutsk,mongolia, china, afghanistan};
        uralAdjacencies = new Territory[]{ukraine, siberia, afghanistan};
        yakutskAdjacencies = new Territory[]{siberia, kamchatka, irkutsk};
        easternAustraliaAdjacencies = new Territory[]{newGuinea, westernAustralia};
        indonesiaAdjacencies = new Territory[]{siam, newGuinea, westernAustralia};
        newGuineaAdjacencies = new Territory[]{indonesia, easternAustralia, westernAustralia};
        westernAustraliaAdjacencies = new Territory[]{indonesia, newGuinea, easternAustralia};

        // Add adjacencies to respective Territory
        alaska.addAdjacencies(alaskaAdjacencies);
        alberta.addAdjacencies(albertaAdjacencies);
        centralAmerica.addAdjacencies(centralAmericaAdjacencies);
        easternUnitedStates.addAdjacencies(easternUnitedStatesAdjacencies);
        greenland.addAdjacencies(greenlandAdjacencies);
        northWestTerritory.addAdjacencies(northWestTerritoryAdjacencies);
        ontario.addAdjacencies(ontarioAdjacencies);
        quebec.addAdjacencies(quebecAdjacencies);
        westernUnitedStates.addAdjacencies(westernUnitedStatesAdjacencies);
        argentina.addAdjacencies(argentinaAdjacencies);
        brazil.addAdjacencies(brazilAdjacencies);
        peru.addAdjacencies(peruAdjacencies);
        venezuela.addAdjacencies(venezuelaAdjacencies);
        greatBritain.addAdjacencies(greatBritainAdjacencies);
        iceland.addAdjacencies(icelandAdjacencies);
        northernEurope.addAdjacencies(northernEuropeAdjacencies);
        scandinavia.addAdjacencies(scandinaviaAdjacencies);
        southernEurope.addAdjacencies(southernEuropeAdjacencies);
        ukraine.addAdjacencies(ukraineAdjacencies);
        westernEurope.addAdjacencies(westernEuropeAdjacencies);
        congo.addAdjacencies(congoAdjacencies);
        eastAfrica.addAdjacencies(eastAfricaAdjacencies);
        egypt.addAdjacencies(egyptAdjacencies);
        madagascar.addAdjacencies(madagascarAdjacencies);
        northAfrica.addAdjacencies(northAfricaAdjacencies);
        southAfrica.addAdjacencies(southAfricaAdjacencies);
        afghanistan.addAdjacencies(afghanistanAdjacencies);
        china.addAdjacencies(chinaAdjacencies);
        india.addAdjacencies(indiaAdjacencies);
        irkutsk.addAdjacencies(irkutskAdjacencies);
        japan.addAdjacencies(japanAjacencies);
        kamchatka.addAdjacencies(kamchatkaAdjacencies);
        middleEast.addAdjacencies(middleEastAdjacencies);
        mongolia.addAdjacencies(mongoliaAdjacencies);
        siam.addAdjacencies(siamAdjacencies);
        siberia.addAdjacencies(siberiaAdjacencies);
        ural.addAdjacencies(uralAdjacencies);
        yakutsk.addAdjacencies(yakutskAdjacencies);
        easternAustralia.addAdjacencies(easternAustraliaAdjacencies);
        indonesia.addAdjacencies(indonesiaAdjacencies);
        newGuinea.addAdjacencies(newGuineaAdjacencies);
        westernAustralia.addAdjacencies(westernAustraliaAdjacencies);

        // Populate territory array
        territoryList = new Territory[]{alaska, alberta, centralAmerica, easternUnitedStates, greenland,
                northWestTerritory, ontario, quebec, westernUnitedStates, argentina, brazil, peru, venezuela,
                greatBritain, iceland, northernEurope, scandinavia, southernEurope, ukraine, westernEurope, congo,
                eastAfrica, egypt, madagascar, northAfrica, southAfrica, afghanistan, china, india, irkutsk, japan, kamchatka, middleEast,
                mongolia, siam, siberia, ural, yakutsk, easternAustralia, indonesia, newGuinea, westernAustralia};
    }

    /**
     * @param s The name of the territory being checked
     * @return True if s is an existing territory, false otherwise
     */
    public boolean isTerritory(String s){
        for(Territory t: territoryList){
            if(t.getTerritoryName().equals(s)){
                return true;
            }
        }
        return false;
    }

    /**
     * @param s Name of territory being searched for
     * @return The territory object being searched for
     */
    public Territory getTerritory(String s){
        if(isTerritory(s)){
            for(Territory t: territoryList){
                if(t.getTerritoryName().equals(s)){
                    return t;
                }
            }
        }
        return null;
    }
}
