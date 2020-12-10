import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;

/**
 * Board class to illustrate the game board and territory locations in relation to one another.
 * @author Marko M and Tamilore Odunlami
 * @version 1.0.0
 */

public class Board implements Externalizable, Serializable
{
    private static final long serialVersionUID = 1420672609912364063L;
    private ArrayList<Territory> territories;// new territories
    private ArrayList<ArrayList<Territory>> adjacents;// new adjacents
    private ArrayList<Continent> continents;// new continent list
    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;
    private Document doc;
    private NodeList nodeList;

    public Board() {
    }

    /**
     * Board constructor to initialize all territory objects and set their adjacencies.
     */
    public Board(File file) {
        try {
            territories = new ArrayList<>();
            adjacents = new ArrayList<>();
            continents = new ArrayList<>();

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
                    for (Territory territory : territories) {
                        for (int j = 0; j < eElement.getElementsByTagName("Adjacent").getLength(); j++) {
                            if (eElement.getElementsByTagName("Adjacent").item(j).getTextContent().equals(territory.getTerritoryName())) {
                                adj.add(territory);
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
                        for (Territory territory : territories) {
                            if (eElement.getElementsByTagName("Territory").item(i).getTextContent().equals(territory.getTerritoryName())) {
                                incTerr.add(territory);
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

    /**
     * The object implements the writeExternal method to save its contents
     * by calling the methods of DataOutput for its primitive values or
     * calling the writeObject method of ObjectOutput for objects, strings,
     * and arrays.
     *
     * @param out the stream to write the object to
     * @throws IOException Includes any I/O exceptions that may occur
     * @serialData Overriding methods should use this tag to describe
     * the data layout of this Externalizable object.
     * List the sequence of element types and, if possible,
     * relate the element to a public/protected field and/or
     * method of this Externalizable class.
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(territories.size());
        for(Territory territory: territories){
            out.writeObject(territory);
        }
        out.writeInt(adjacents.size());
        for (ArrayList<Territory> adjacent : adjacents) {
            for (Territory territory : adjacent) {
                out.writeObject(territory);
            }
        }
        out.writeInt(continents.size());
        for(Continent continent: continents){
            out.writeObject(continent);
        }
        out.writeObject(dbf);
        out.writeObject(db);
        out.writeObject(doc);
        out.writeInt(nodeList.getLength());
        for(int i=0; i<nodeList.getLength(); i++){
            out.writeObject(nodeList.item(i));
        }
    }

    /**
     * The object implements the readExternal method to restore its
     * contents by calling the methods of DataInput for primitive
     * types and readObject for objects, strings and arrays.  The
     * readExternal method must read the values in the same sequence
     * and with the same types as were written by writeExternal.
     *
     * @param in the stream to read data from in order to restore the object
     * @throws IOException            if I/O errors occur
     * @throws ClassNotFoundException If the class for an object being
     *                                restored cannot be found.
     */
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        territories = new ArrayList<>();
        int territoriesSize = (int) in.readObject();
        for (int i = 0; i < territoriesSize; i++) {
            Territory territory = (Territory) in.readObject();
            territories.add(territory);
        }
        adjacents = new ArrayList<>();
        int adjacentsSize = (int) in.readObject();
        for (int i = 0; i < adjacentsSize; i++) {
            ArrayList<Territory> incTerr = new ArrayList<>();
            for (int j = 0; j < adjacents.get(i).size(); j++) {
                Territory territory = (Territory) in.readObject();
                incTerr.add(territory);
            }
            adjacents.add(incTerr);
        }
        continents = new ArrayList<>();
        int continentsSize = (int) in.readObject();
        for (int i = 0; i < continentsSize; i++) {
            Continent continent = (Continent) in.readObject();
            continents.add(continent);
        }
        dbf = (DocumentBuilderFactory) in.readObject();
        db = (DocumentBuilder) in.readObject();
        doc = (Document) in.readObject();
        nodeList = null;
        int nodeListSize = (int) in.readObject();
        for (int i = 0; i < nodeListSize; i++) {
            Node node = (Node) in.readObject();
            nodeList.item(0).appendChild(node);
        }
    }
}
