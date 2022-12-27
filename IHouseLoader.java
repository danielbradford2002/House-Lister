import java.io.FileNotFoundException;
import java.util.List;

/**
 * This interface defines the method that reads and processes the XML file
 * containing the dataset for the project
 */
public interface IHouseLoader {

    /**
     * Reads the objects in from the XML file and converts them into a list of >
     * objects for the Backend Engineer to use
     *
     * @param filepathToXML the filepath in String format with the location of >
     * @return a List containing the House objects from the XML file
     */
    List<IHouse> loadHouses(String filepathToXML) throws FileNotFoundException;

}
