import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class FrontendDeveloperTest {
    public static Scanner scanner;

    @BeforeEach
    public void creatInstance() {
        scanner = new Scanner(System.in);
        TextUITester uiTester = new TextUITester(" ");
        ProduceBackend backend = new ProduceBackend();
        PLUValidator validator = new PLUValidator();
        ProduceFrontend frontend = new ProduceFrontend(scanner, backend, validator);

    }

    /**
     * test displayProduce
     */
    @Test
    public void test1() {
        ProduceFrontend frontend = new ProduceFrontend(new java.util.Scanner(System.in), new ProduceBackend(), null);
        TextUITester tester = new TextUITester("");

        String result = tester.checkOutput();
        frontend.runCommandLoop();

        assertEquals(result, "Welcome to the Produce Finder Application!" +
                "\n*-**-**-**-**-**-**-**-**-**-**-**-**-*" +
                "\nGoodbye, see you next time.\n");
    }

    /**
     * test displayMainMenu
     */
    @Test
    public boolean test2() {
        ProduceFrontend frontend = new ProduceFrontend(new java.util.Scanner(System.in), new ProduceBackend(), null);
        TextUITester tester = new TextUITester(" ");

        String result = tester.checkOutput();
        frontend.runCommandLoop();

        assertEquals(result, "You are in the Main Menu:" +
                "\n1) Lookup PLU" +
                "\n2)Add Produce" +
                "\n3)Remove Produce" +
                "\n4) Exit Application");
    }

    /**
     * test getName
     */
    @Test
    public void test3() {
        TextUITester tester = new TextUITester("APPLES\nALMONDS\n");
        ProduceBackend backend = new ProduceBackend();
        PLUValidator validator = new PLUValidator();
        ProduceFrontend frontend = new ProduceFrontend(scanner, backend, validator);
        List<IProduce> produceList = new ArrayList<>();

        Produce produce1 = new Produce("1.50", "APPLES", "4098");
        Produce produce2 = new Produce("1.00", "ALMONDS", "4924");

        String str = tester.checkOutput();

        assertEquals(produce1.getName(), "APPLES");
        assertEquals(produce2.getName(), "ALMONDS");

    }

    /**
     * test getPrice
     */
    @Test
    public void test4() {

        TextUITester tester = new TextUITester("1.50\n1.00\n");
        ProduceBackend backend = new ProduceBackend();
        PLUValidator validator = new PLUValidator();
        ProduceFrontend frontend = new ProduceFrontend(scanner, backend, validator);
        List<IProduce> produceList = new ArrayList<>();

        Produce produce1 = new Produce("1.50", "APPLES", "4098");
        Produce produce2 = new Produce("1.00", "ALMONDS", "4924");

        String str = tester.checkOutput();

        assertEquals(produce1.getPrice(), "1.50");
        assertEquals(produce2.getPrice(), "1.00");

    }

    /**
     * test getPLU
     */
    @Test
    public void test5() {
        Produce produce1 = new Produce("1.50", "APPLES", "4098");
        Produce produce2 = new Produce("1.00", "ALMONDS", "4924");

        assertEquals(produce1.getPLU(), "4098");
        assertEquals(produce2.getPLU(), "4924");

    }

    /**
     * Tests iterator
     */
    @Test
    public void CodeReviewOfAlgorithmEngineer1() {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        for (int i = 0; i < 20; rbt.insert(i++)) {
        }
        int j = 0;
        for (int i : rbt) {
            assertEquals(i, j++);
        }
    }

    /**
     * Tests the iterator by going through a for-loop
     */
    @Test
    public void CodeReviewOfAlgorithmEngineer2() {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        rbt.insert(6);
        rbt.insert(11);
        rbt.insert(7);
        assertEquals(rbt.toLevelOrderString(), "[ 7, 6, 11 ]");
        assertEquals(rbt.root.rightChild.blackHeight, 0);
        assertEquals(rbt.root.leftChild.blackHeight, 0);
        assertEquals(rbt.root.blackHeight, 1);

    }

    /**
     * Tests
     */
    @Test
    public void IntegrationTest1() {
        RedBlackTree<IProduce> rbt = new RedBlackTree<>();
        Produce produce = new Produce("ALMONDS", "1.00", "4924"),
        rbt.insert(produce);
        assertTrue(rbt.find(produce).equals(produce));
        assertEquals(produce.getName(), "ALMONDS");
        assertEquals(produce.getPLU(), "4924");
        assertEquals(produce.getPrice(), "1.00");
    }
    /**
     * Tests the PLU Validator
     */
    @Test
    public void IntegrationTest2() {
        IPLUValidator validator = new PLUValidator();
        assertFalse(validator.validate("831"));
        assertTrue(validator.validate("4098"));

    }

}

