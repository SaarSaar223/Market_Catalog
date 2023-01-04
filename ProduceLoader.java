import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is using for reading the XML file provided, saving them together as a List that
 * contains IProduce object
 *
 * @author Jie Wang
 */
public class ProduceLoader implements IProduceLoader {
    /**
     * The method is using for rading the XML file
     * @param filepathToXML path to the XML file relative to the executable
     * @return  List that contain IProduce Object
     * @throws FileNotFoundException        the file is not found
     */
    @Override
    public List<IProduce> loadProduce(String filepathToXML) throws FileNotFoundException {
        ArrayList<IProduce> listOfProduce = new ArrayList<>();

        try {
            //creating a constructor of file class and parsing an XML file
            File file = new File(filepathToXML);
            //an instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //an instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("produce");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    String str1 = eElement.getElementsByTagName("name").item(0).getTextContent();
                    String str2 = eElement.getElementsByTagName("price").item(0).getTextContent();
                    String str3 = eElement.getElementsByTagName("plu").item(0).getTextContent();
                    Produce p = new Produce(str1, str2, str3);
                    listOfProduce.add(p);
                }
            }
        } catch (FileNotFoundException e1) {
            System.out.println("No file Found");

        } catch (Exception e2) {
            System.out.println("Something goes wrong");

        }

        return listOfProduce;
    }
}
