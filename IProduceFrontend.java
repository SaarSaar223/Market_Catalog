import java.util.List;

public interface IProduceFrontend {
    
    public void runCommandLoop();//starts the command loop, terminates when user exits

    public void displayMainMenu(); // prints command options to System.out

    public void displayProduce(List<IProduce> produce);// displays a list of produce

    public void pluLookup();// reads word from System.in, displays results
			    
    public void addProduce();//User inputs name, price, and plu to add produce to the produce list

    public void removeProduce();//user inputs the PLU to remove a produce from the produce list
}
