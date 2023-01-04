import java.io.FileNotFoundException;
import java.util.List;

/**
 * Instances of this interface can be used to load Produce data from an XML file.
 * @author Jie Wang
 */
public interface IProduceLoader {
    /**
     * This method loads the list of Produce from an XML file.
     * @param filepathToXML path to the XML file relative to the executable
     * @return a list of Produce objects
     * @throws FileNotFoundException
     */
    List<IProduce> loadProduce(String filepathToXML) throws FileNotFoundException;
}
