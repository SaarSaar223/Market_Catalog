import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Class with main method to run the application.
 */
public class Application {

    public static void main(String[] args) throws FileNotFoundException {
        // load the data from the data file
        List<IProduce> produces = (new ProduceLoader()).loadProduce("producelist.xml");
        // instantiate the backend
        ProduceBackend backend = new ProduceBackend();
        // add all the books to the backend
        for (IProduce produce : produces) {
            backend.addProduce(produce);
        }
        // instantiate the validator (to be used by the front end)
        PLUValidator validator = new PLUValidator();
        // instantiate the scanner for user input (to be used by the front end)
        Scanner userInputScanner = new Scanner(System.in);
        // instantiate the front end and pass references to the scanner, backend, and isbn validator to it
        ProduceFrontend frontend = new ProduceFrontend(userInputScanner, backend, validator);
        // start the input loop of the front end
        frontend.runCommandLoop();
    }
    
}
