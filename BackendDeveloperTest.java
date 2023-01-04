/*** JUnit imports ***/
//We will use the BeforeEach and Test annotation types to mark methods in
//our test class.
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//The Assertions class that we import from here includes assertion methods like assertEquals()
//which we will used in test1000Inserts().
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//More details on each of the imported elements can be found here:
//https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/package-summary.html
/*** JUnit imports end  ***/


public class BackendDeveloperTest {
  private ProduceBackend backend;
  private ProduceBackend2 backend2;
  private ArrayList<String> fruits; 
  ProduceLoader produceLoader;
  List<IProduce> list;
  /**
   * This method will run before each junit test instantiating the backend and the fruits list
   */
  @BeforeEach
  public void createInstance() {
      backend = new ProduceBackend();
      backend2 = new ProduceBackend2();
      fruits = new ArrayList<>();
      fruits.add("Apples");
      fruits.add("Bananas");
      fruits.add("Lemons");
      fruits.add("Strawberries");
      fruits.add("Oranges");
      fruits.add("Pomegranate");
      fruits.add("Limes");
      
      produceLoader = new ProduceLoader();
      try {
        list = produceLoader.loadProduce("producelist.xml");
      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
  }
  
  /**
   * This method tests the addProduce method by inserting random PLU numbers. This is then checked
   * by the searchProduce method. 
   */
  @Test
  public void testAddProduce() {
    //testing if correct input works
    ArrayList<Produce> fruitsPLU= new ArrayList<>();
    for(int i = 0; i < fruits.size(); ++i) {
      Random rand = new Random();
      double price = rand.nextDouble() * 100;
      String sPrice = String.format("%.2f", price);
      int number = rand.nextInt(9000) + 1000;
      String PLU = Integer.toString(number);
      while(fruitsPLU.contains(PLU)) {
        number = rand.nextInt(9000) + 1000;
        PLU = Integer.toString(number);
      }
      
      Produce produce = new Produce(fruits.get(i), sPrice, PLU);
      backend2.addProduce(produce);
      fruitsPLU.add(produce);
      assertEquals(backend2.searchProduce(PLU), produce);
    }   
  }
  /**
   * This method will test the remove method and then checks if the returned String correctly matches
   * the PLU number added before. It checks both invalid and valid PLUs.
   */
  @Test
  public void testRemove() {
    //inserting the produce
    ArrayList<Produce> fruitsPLU= new ArrayList<>();
    for(int i = 0; i < fruits.size(); ++i) {
      Random rand = new Random();
      double price = rand.nextDouble() * 100;
      String sPrice = String.format("%.2f", price);
      int number = rand.nextInt(9000) + 1000;
      String PLU = Integer.toString(number);
      while(fruitsPLU.contains(PLU)) {
        number = rand.nextInt(9000) + 1000;
        PLU = Integer.toString(number);
      }
      
      Produce produce = new Produce(fruits.get(i), sPrice, PLU);
      fruitsPLU.add(produce);
      backend2.addProduce(produce);
    }
    
    //Checking if returned input has correct values 
    for(int i = 0; i < fruits.size(); ++i) {
      String test = backend2.removeProduce(fruitsPLU.get(i).getPLU());
      assertEquals(test, "The produce, Grapes ($35.0), with the PLU number: " + fruitsPLU.get(i).getPLU() + " has been removed.");
      assertEquals(backend2.getSize(), fruits.size()-1-i);
    }

    //checking if returned input has correct values for invalid PLU numbers.
    for(int i = 1; i < 5; ++i) {
      String test = backend2.removeProduce(Integer.toString(i));
      assertEquals(test, "The given PLU number does not exist!");
    }
    
    
  }
  
  /**
   * This method will check if the search method is working by inserting nodes into the rbTree and
   * then checking if the search returns the correct nodes. 
   */
  @Test
  public void testSearch() {
    
    //Adding produce
    ArrayList<String> fruitsPLU= new ArrayList<>();
    for(int i = 0; i < fruits.size(); ++i) {
      Random rand = new Random();
      double price = rand.nextDouble() * 100;
      String sPrice = String.format("%.2f", price);
      int number = rand.nextInt(9000) + 1000;
      String PLU = Integer.toString(number);
      while(fruitsPLU.contains(PLU)) {
        number = rand.nextInt(9000) + 1000;
        PLU = Integer.toString(number);
      }
      
      Produce produce = new Produce(fruits.get(i), sPrice, PLU);
      backend2.addProduce(produce);
      fruitsPLU.add(PLU);
    }
    
    //Checking if produce is there
    for(int i = 0; i < fruitsPLU.size(); ++i) {
      assertEquals(backend2.searchProduce(fruitsPLU.get(i)).getName(), fruits.get(i));
    }
    
    //checking non inserted numbers
    for(int i = 0; i < 15; ++i) {
      Random rand = new Random();
      int number = rand.nextInt(9000) + 1000;
      String PLU = Integer.toString(number);
      while(fruitsPLU.contains(PLU)) {
        number = rand.nextInt(9000) + 1000;
        PLU = Integer.toString(number);
      }
      
      assertEquals(backend2.searchProduce(PLU), null);
    }
    
    
    
  }
  
  /**
   * This method checks wether the size is correctly updated after each insert and remove. 
   */
  @Test
  public void testSize() {
    //checking if size is correctly returned after each insert
    ArrayList<String> fruitsPLU= new ArrayList<>();
    for(int i = 0; i < 30; ++i) {
      Random rand = new Random();
      double price = rand.nextDouble() * 100;
      String sPrice = String.format("%.2f", price);
      int number = rand.nextInt(9000) + 1000;
      String PLU = Integer.toString(number);
      fruitsPLU.add(PLU);
      
      Produce produce = new Produce(sPrice, null, PLU);
      backend2.addProduce(produce);
      assertEquals(backend2.getSize(), i + 1);
    }
    
  //checking if size is correctly returned after each removal
    for(int i = 0; i < 30; ++i) {
      backend2.removeProduce(fruitsPLU.get(i));
      assertEquals(backend2.getSize(), 29 - i);
    }
  }
  
  
  /**
   * This method checks wether the given PLU number is valid by checking it through the validator. 
   */
  @Test
  public void testValidator() {
    ArrayList<String> fruitsPLU= new ArrayList<>();
    
    //tests 50 valid PLU numbers
    for(int i = 0; i < 50; ++i) {
      Random rand = new Random();
      int number = rand.nextInt(9000) + 1000;
      String PLU = Integer.toString(number);
      
      assertEquals(backend2.validateProduce(PLU), true);
    }
    
    //tests 20 invalid PLU numbers with 3 digits
    for(int i = 0; i < 20; ++i) {
      Random rand = new Random();
      int number = rand.nextInt(1000);
      String PLU = Integer.toString(number);
      
      assertEquals(backend2.validateProduce(PLU), false);
    }
    
    //tests 20 invalid PLU numbers with 5 digits.
    for(int i = 0; i < 20; ++i) {
      Random rand = new Random();
      int number = rand.nextInt(1000) + 10000;
      String PLU = Integer.toString(number);
      
      assertEquals(backend2.validateProduce(PLU), false);
    }
    
    //testing PLU with numbers and letters of 4 length 
    String s1 = "df12";
    String s2 = "dfds";
    String s3 = "d112";
    String s4 = "df1d";
    String s5 = "112f";
    
    assertEquals(backend2.validateProduce(s1), false);
    assertEquals(backend2.validateProduce(s2), false);
    assertEquals(backend2.validateProduce(s3), false);
    assertEquals(backend2.validateProduce(s4), false);
    assertEquals(backend2.validateProduce(s5), false);
    
    
  }
  
  /**
   * This method checks wether all the data has been loaded 
   */
  @Test
  public void CodeReviewOfDataWranglerLoadingData() {
    assertEquals(20, list.size());
    for(int i = 0; i < list.size(); ++i) {
      backend.addProduce(list.get(i));
    }
    assertEquals(backend.getSize(), 20);
  }
  
  /**
   * This method checks wether the data has been loaded in correctly. 
   */
  @Test
  public void CodeReviewOfDataWranglerCorrectlyInputted() {
    backend = new ProduceBackend();
    assertEquals("ALMONDS", list.get(1).getName());
    assertEquals("KALE", list.get(7).getName());
    assertEquals("MALANGA", list.get(11).getName());
    assertEquals("MINT", list.get(17).getName());
  }
  
  /**
   * This method checks wether the data has been loaded in correctly. 
   */
  @Test
  public void CodeReviewOfDataWranglerPriceAndCorrectlyInputted() {
    backend = new ProduceBackend();
    for(int i = 0; i < list.size(); ++i) {
      backend.addProduce(list.get(i));
    }
    
    assertEquals("1.00", backend.searchProduce("4924").getPrice());
    assertEquals("2.00", backend.searchProduce("4627").getPrice());
    assertEquals("1.00", backend.searchProduce("4644").getPrice());
    assertEquals("2.99", backend.searchProduce("4896").getPrice());
  }
  
  
  /**
   * This method checks wether the data has been loaded in correctly. 
   */
  @Test
  public void CodeReviewOfDataWranglerPLUInput() {
    assertEquals("4924", list.get(1).getPLU());
    assertEquals("4627", list.get(7).getPLU());
    assertEquals("4644", list.get(11).getPLU());
    assertEquals("4896", list.get(17).getPLU());
  }
  
  
  
  
  

  
  
  
  
}
