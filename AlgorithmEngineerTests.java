import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmEngineerTests {
  protected RedBlackTree<Integer> _r = new RedBlackTree<>();

  @BeforeEach
  public void createInstance() {
    _r = new RedBlackTree<>();
  }

  /**
   * Building a tree through repetitive insertion just to see
   * if it can handle more than just a few nodes being inserted at once
   */
  @Test
  public void fromScratch() {
    for(int i = 0; i < 3; _r.insert(i++)) {}
    // Prelim check
    assertEquals(_r.root.data, 1);
    assertEquals(_r.root.leftChild.data, 0);
    assertEquals(_r.root.rightChild.data, 2);

    for(int i = 3; i < 16; _r.insert(i++)) {}
    assertEquals(_r.toLevelOrderString(), // comparing the level order string to my calculation
            "[ 3, 1, 7, 0, 2, 5, 11, 4, 6, 9, 13, 8, 10, 12, 14, 15 ]");
    createInstance();
  }

  /**
   * Builds a small tree from scratch just to make sure that the color specific ops are working
   * In this case, we test a double-red black uncle insert
   */
  @Test
  public void testRedUncle() {
    // null first
    _r.insert(2);
    _r.insert(5);
    _r.insert(3);

    assertEquals(_r.toLevelOrderString(), "[ 3, 2, 5 ]");
    assertEquals(_r.root.blackHeight, 1);
    assertEquals(_r.root.rightChild.blackHeight, 0);
    assertEquals(_r.root.leftChild.blackHeight, 0);

    _r.insert(6);
    assertEquals(_r.toLevelOrderString(), "[ 3, 2, 5, 6 ]");
    assertEquals(_r.root.blackHeight, 1);
    assertEquals(_r.root.rightChild.blackHeight, 1);
    assertEquals(_r.root.leftChild.blackHeight, 1);
    assertEquals(_r.root.rightChild.rightChild.blackHeight, 0);
    createInstance();
  }

  /**
   * Builds a small tree from scratch just to make sure that the color specific ops are working
   * In this case, we test a double-red red uncle insert
   */
  @Test
  public void testBlackUncle() {
    _r.insert(2);
    _r.insert(5);
    _r.insert(3);

    assertEquals(_r.toLevelOrderString(), "[ 3, 2, 5 ]");
    assertEquals(_r.root.blackHeight, 1);
    assertEquals(_r.root.rightChild.blackHeight, 0);
    assertEquals(_r.root.leftChild.blackHeight, 0);

    createInstance();
  }

  /**
   * Builds a small sample tree, and then removes three red nodes
   */
  @Test
  public void testRemoveRed() {
    _r.insert(7);
    _r.insert(2);
    _r.insert(16);
    _r.insert(1);
    _r.insert(15);
    _r.insert(20);
    _r.insert(12);

    // two children
    assertEquals(16, _r.remove(16));
    assertEquals("[ 7, 2, 20, 1, 15, 12 ]", _r.toLevelOrderString());

    assertEquals(0, _r.root.rightChild.blackHeight);

    // This will only have left subtree
    assertEquals(_r.remove(20), 20);
    assertEquals(_r.toLevelOrderString(), "[ 7, 2, 15, 1, 12 ]");

    // red leaf
    assertEquals(_r.remove(1), 1);
    assertEquals(_r.toLevelOrderString(), "[ 7, 2, 15, 12 ]");

    createInstance();
  }

  /**
   * Builds a small sample tree, and then removes three black nodes
   */
  @Test
  public void testRemoveBlack() {
    _r.insert(7);
    _r.insert(2);
    _r.insert(16);
    _r.insert(1);
    _r.insert(15);
    _r.insert(20);
    _r.insert(12);

    // black leaf
    assertEquals(20, _r.remove(20));
    assertEquals(_r.toLevelOrderString(), "[ 7, 2, 16, 1, 15, 12 ]");

    assertEquals(1, _r.root.rightChild.leftChild.blackHeight); // to make sure this is
    // a valid bst

    // successor (12) should get promoted to root
    assertEquals(_r.remove(7), 7);
    assertEquals(_r.toLevelOrderString(), "[ 12, 2, 16, 1, 15 ]");
    assertEquals(_r.root.blackHeight, 1); // testing to make sure root is black

    // removing only a subtree in the left branch
    assertEquals(_r.root.leftChild.blackHeight, 1); //making sure this node is black
    assertEquals(_r.remove(2), 2);
    assertEquals(_r.toLevelOrderString(), "[ 12, 1, 16, 15 ]"); // just removing 2

    createInstance();
  }

  /**
   * Tests the iterator by going through a for-loop
   */
  @Test
  public void testIterator() {
    for(int i = 0; i < 20; _r.insert(i++)) {}
    int j = 0;
    for(int i : _r) {
      assertEquals(i, j++);
    }
    createInstance();
  }

  /**
   * Tests the PLU validator
   */
  @Test
  public void testPLUValidator() {
    IPLUValidator v = new PLUValidator();
    assertFalse(v.validate("2"));
    assertTrue(v.validate("1234"));
    assertFalse(v.validate("0224"));
    createInstance();
  }

  /**
   * Runs all the testers at once
   */
  @Test
  public void testMyTree() {
    fromScratch();
    testRedUncle();
    testBlackUncle();
    createInstance();
    testRemoveBlack();
    testRemoveRed();
    testPLUValidator();
    testIterator();
  }

  /**
   * Test 1 for Frontend, testing the displayProduce and runCommandLoop
   */
  @Test
  public void CodeReviewOfFrontendDeveloper1() {
	  // First testing a valid printer
	List<IProduce> produceList = new ArrayList<>();
	produceList.add(new Produce("Apple", "2.13", "4123"));
	produceList.add( new Produce("Banana", "9.12", "4919"));
	ProduceFrontend pf = new ProduceFrontend(new java.util.Scanner(System.in), new ProduceBackend(), new PLUValidator()); //just testing output
	TextUITester tst = new TextUITester("");
	String result = tst.checkOutput();
	pf.displayProduce(produceList);

      assertEquals("1Name: Apple Price: 2.13 PLU: 2.13\n2Name: Banana Price: 2.13 PLU: 4919", result);

	produceList.add(new Produce("Orange", "32.33", "5321"));
	produceList.add(new Produce("Grape", "0.01", "1234"));
	

	tst = new TextUITester("");
	result = tst.checkOutput();
	pf.displayProduce(produceList);
	assertEquals("1Name: Apple Price: 2.13 PLU: 2.13\n2Name: Banana Price: 2.13 PLU: 4919\n3Name: Orange Price: 32.33 PLU: 5321\n4Name: Grape Price: 0.01 PLU: 1234",result);
  }


  /**
   * Tests the add produce
   */
  @Test
  public void CodeReviewOfFrontendDeveloper2() {
	ProduceFrontend pf = new ProduceFrontend(new java.util.Scanner(System.in), new ProduceBackend(), new PLUValidator());
	TextUITester tst = new TextUITester("4444\n12.13\ntest");
	
	String result = tst.checkOutput();
	pf.addProduce();
	assertEquals("\n\n\nYou are in the Add Produce Menu:\nEnter the PLU: \n\n\nYou are in the Add Produce Menu:\nEnter the price: \n\n\nYou are in the Add Produce Menu:\nEnter the name: Successfully added", result);
  }

  
  /**
   * An integration test, we add some produce and then get the produce
   */
   @Test
   public void IntegrationTest1() {
	RedBlackTree<IProduce> r = new RedBlackTree<>();
	Produce f1 = new Produce("Apple", "1.12", "4123"),
                f2 = new Produce("Banana", "9.12", "4919"),
                f3 = new Produce("Generic Fruit", "2.33", "4111");
	r.insert(f1);
	r.insert(f2);
	r.insert(f3);

	assertTrue(r.find(f1).equals(f1));
	assertTrue(r.find(f2).equals(f2));
	assertTrue(r.find(f3).equals(f3));
   }	   

   /**
    * Another integration test, this time we'll add some produce and them remove some fruit to make sure delete op works
    */
   @Test
   public void IntegrationTest2() {
	RedBlackTree<IProduce> r = new RedBlackTree<>();
        Produce f1 = new Produce("Apple", "1.12", "4123"),
                f2 = new Produce("Banana", "9.12", "4919"),
                f3 = new Produce("Generic Fruit", "2.33", "4111");
        r.insert(f1);
        r.insert(f2);
        r.insert(f3);

	String expected = "4919, 4123, 4111, ";
	String actual = "";
	for(IProduce p : r) {
		actual += p.getPLU() + ", ";
	}

	assertEquals(expected, actual);// First just checking the tree has been built right
        
	r.remove(f1);
	expected = "4919, 4111, ";
	actual = "";
        for(IProduce p : r) {
                actual += p.getPLU() + ", ";
        }

	assertEquals(expected, actual);
   }

 }	
