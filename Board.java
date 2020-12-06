import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Board class to illustrate the game board and territory locations in relation to one another.
 * The board has 42 Territories.
 * @author Tamilore Odunlami and Marko M
 * @version 1.0.0
 */

public class Board  implements Serializable
{
    private static final long serialVersionUID = 1420672609912364063L;
    private ArrayList<Territory> territories;// new territories
    private ArrayList<ArrayList<Territory>> adjacents;// new adjacents
    private ArrayList<Continent> continents;// new continent list
    private int unoccupied;
    private File file;
    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;
    private Document doc;
    private NodeList nodeList;

    /**
     * Board constructor to initialize all territory objects and set their adjacencies.
     */
    public Board() {
        try {
            territories = new ArrayList<>();
            adjacents = new ArrayList<>();
            continents = new ArrayList<>();
            unoccupied = 0;

            //creating a constructor of file class and parsing an XML file
            file = new File("Adjacencies.xml");
            //an instance of factory that gives a document builder
            dbf = DocumentBuilderFactory.newInstance();
            //an instance of builder to parse the specified xml file
            db = dbf.newDocumentBuilder();
            doc = db.parse(file);
            doc.getDocumentElement().normalize();

            //create territory list
            nodeList = doc.getElementsByTagName("Territory");
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    territories.add(new Territory(eElement.getTextContent()));
                }
            }

            //sort adjacents
            NodeList nodeList2 = doc.getElementsByTagName("Adjacents");
            for (int itr = 0; itr < nodeList2.getLength(); itr++) {
                Node node = nodeList2.item(itr);
                ArrayList<Territory> adj = new ArrayList<>();
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    for (int i = 0; i < territories.size(); i++) {
                        for (int j = 0; j < eElement.getElementsByTagName("Adjacent").getLength(); j++) {
                            if (eElement.getElementsByTagName("Adjacent").item(j).getTextContent().equals(territories.get(i).getTerritoryName())) {
                                adj.add(territories.get(i));
                            }
                        }
                    }
                    if(adj.size()>0) {
                        adjacents.add(adj);
                    }
                }
            }

            //add adjacents to territories
            for(int i=0; i<territories.size(); i++){
                territories.get(i).addAdjacencies(adjacents.get(i));
            }

            //continent creation with included territories
            NodeList nodeList1 = doc.getElementsByTagName("Continent");
            for (int itr = 0; itr < nodeList1.getLength(); itr++) {
                Node node = nodeList1.item(itr);
                ArrayList<Territory> incTerr = new ArrayList<>();
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    for (int i = 0; i < eElement.getElementsByTagName("Territory").getLength(); i++) {
                        for (int j = 0; j < territories.size(); j++) {
                            if (eElement.getElementsByTagName("Territory").item(i).getTextContent().equals(territories.get(j).getTerritoryName())) {
                                incTerr.add(territories.get(j));
                            }
                        }
                    }
                    continents.add(new Continent(eElement.getAttributes().item(1).getNodeValue(), incTerr,
                            Integer.parseInt(eElement.getAttributes().item(0).getNodeValue())));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<Territory> getTerritories(){
        return territories;
    }

    public ArrayList<Continent> getContinents(){
        return continents;
    }

    public int noOfUnoccupiedTerritories(){
        for(Territory territory: territories){
            if(territory.getTerritoryOccupant()==null){
                unoccupied++;
            }
        }
        return unoccupied;
    }
}
