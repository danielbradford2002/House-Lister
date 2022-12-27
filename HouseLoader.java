import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class loads in house data from an xml file and stores it in a form with
 * which the BackendDeveloper can then use
 */
public class HouseLoader {


    /**
     * This method process house data from the specified xml file
     *
     * @param filename the name of xml file (must be in project directory)
     * @return an arraylist containing all house objects
     */
    public ArrayList<House> loadHouses(String filename)
            throws InputMismatchException, IOException, SAXException, ParserConfigurationException {

        File xmlFile = new File(filename);
        if (!xmlFile.exists()) {
            throw new FileNotFoundException();
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);

        NodeList housesXML = doc.getElementsByTagName("house");
        if (housesXML.getLength() == 0) {
            throw new InputMismatchException();
        }
        ArrayList<House> houses = new ArrayList<>();

        for (int i = 0; i < housesXML.getLength(); ++i) {

            Node houseNode = housesXML.item(i);
            if (houseNode.getNodeType() == Node.ELEMENT_NODE) {
                System.out.println(houseNode);

                Element element = (Element) houseNode;

                houses.add(
                        new House(Integer.valueOf(element.getElementsByTagName("price").item(0).getTextContent()),
                                Integer.valueOf(element.getElementsByTagName("footage").item(0).getTextContent()),
                                element.getElementsByTagName("location").item(0).getTextContent()));
            }
        }
        return houses;

    }

}

