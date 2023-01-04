import java.util.List;
import java.util.Scanner;

public class ProduceFrontend implements IProduceFrontend {
    private Scanner scanner;
    private ProduceBackend backend;
    private PLUValidator validator;

    public ProduceFrontend(Scanner userInputScanner, ProduceBackend backend,
        PLUValidator validator) {
        this.scanner = userInputScanner;
        this.backend = backend;
        this.validator = validator;
    }

    @Override public void runCommandLoop() {
        boolean keepRunning = true;
        boolean valid = false;
        int chosenValue = -1;
        System.out.println("Welcome to the Produce Finder Application!");
        System.out.println("*-**-**-**-**-**-**-**-**-**-**-**-**-*");
        System.out.println();

        displayMainMenu();
        while (keepRunning) {

            while (!valid) {
                try {
                    chosenValue = Integer.parseInt(scanner.nextLine());
                    if (chosenValue > 0 && chosenValue < 5) {
                        break;
                    } else {
                        System.out.println("Invalid Value!");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid Value!");
                    scanner.nextLine();
                }
            }

            if (chosenValue == 1) {

                pluLookup();

                displayMainMenu();

            } else if (chosenValue == 2) {

                addProduce();
                displayMainMenu();

            } else if (chosenValue == 3) {

                removeProduce();
                displayMainMenu();

            } else if (chosenValue == 4) {

                System.out.println("Goodbye, see you next time.\n");
                keepRunning = false;
            }
            chosenValue = -1;
        }
    }


    @Override public void displayMainMenu() {
        System.out.println("You are in the Main Menu:\n" + "1) Lookup by PLU\n"
            + "2) Add Produce\n" + "3) Remove Produce\n"
            + "4) Exit Application\n");
    }



    @Override public void displayProduce(List<IProduce> produceList) {
        for (int i = 0; i < produceList.size(); ++i) {
            IProduce temp = produceList.get(i);
            String produceName = temp.getName();
            String producePrice = temp.getPrice();
            String plu = temp.getPLU();

            System.out.println(
                (i + 1) + "Name: " + produceName + " Price: " + producePrice + " PLU: " + plu);
        }
    }



    @Override public void pluLookup() {
        System.out.println("\n\n\nYou are in the Lookup PLU Menu:\nEnter the PLU: ");
        String PLU = scanner.nextLine();
        if (validator.validate(PLU)) {
            try{
            IProduce temp = backend.searchProduce(PLU);
            String produceName = temp.getName();
            String producePrice = temp.getPrice();
            String plu = temp.getPLU();
            System.out.println("Name: " + produceName + " Price: " + producePrice + " PLU: " + plu);
            return;
        }catch(Exception e){
                System.out.println("Invalid PLU");
            }
            return;
        }
        else{
            System.out.println("Invalid PLU");
        }
    }

    public void addProduce() {

        System.out.println("\n\n\nYou are in the Add Produce Menu:\nEnter the PLU: ");
        String PLU = scanner.nextLine();
        System.out.println("\n\n\nYou are in the Add Produce Menu:\nEnter the price: ");
        String price = scanner.nextLine();
        System.out.println("\n\n\nYou are in the Add Produce Menu:\nEnter the name: ");
        String name = scanner.nextLine();
        if (validator.validate(PLU)) {
            Produce produce = new Produce(name, price, PLU);
            Boolean temp = backend.addProduce(produce);
            if (temp = true) {
                System.out.println("Successfully added");
            } else {
                System.out.println("Invalid input, please try again");
            }

        } else {
            System.out.println("Invalid PLU");
        }
    }

    public void removeProduce() {
        System.out.println("\n\n\nYou are in the Remove Produce Menu:\nEnter the PLU: ");
        String PLU = scanner.nextLine();
        if (validator.validate(PLU)) {
            String temp = backend.removeProduce(PLU);
            System.out.println(temp);

        } else {
            System.out.println("Invalid PLU");
        }
    }

}
