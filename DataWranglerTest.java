import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is a Junit 5 Testing class
 *
 * @author Jie Wang
 */
class DataWranglerTest {
    ProduceLoader produceLoader;
    List<IProduce> list;

    /**
     * This method will instanciate the ProduceLoader object and List object before each Test method
     * @throws FileNotFoundException    throw FileNotFoundException if the file is not found
     */
    @BeforeEach
    void instanceCreate() throws FileNotFoundException {
        produceLoader = new ProduceLoader();
        list = produceLoader.loadProduce("producelist.xml");
    }

    /**
     * This method will test the number of pairs of the Produce in XML file
     */
    @Test
    void testSizeOfList() {
        assertEquals(20, list.size());
    }

    /**
     * This method will test the correctness of the Produce name of each Produce object
     */
    @Test
    void testNameOfProduce() {
        // index 0;
        assertEquals("APPLES", list.get(0).getName());
        // index 1;
        assertEquals("ALMONDS", list.get(1).getName());
        // index 19;
        assertEquals("OREGANO", list.get(19).getName());
        // index 15
        assertEquals("ONIONS", list.get(15).getName());
    }

    /**
     * This method will test the correctness of the Produce price of each Produce object
     */
    @Test
    void testPriceOfProduce() {
        // index 0;
        assertEquals("1.50", list.get(0).getPrice());
        // index 1;
        assertEquals("1.00", list.get(1).getPrice());
        // index 19;
        assertEquals("1.79", list.get(19).getPrice());
        // index 14
        assertEquals("3.40", list.get(14).getPrice());
        // index 15
        assertEquals("0.90", list.get(15).getPrice());
    }

    /**
     * This method will test the correctness of the Produce PLU code of each Produce object
     */
    @Test
    void testPLUOfProduce() {
        // index 0;
        assertEquals("4098", list.get(0).getPLU());
        // index 1;
        assertEquals("4924", list.get(1).getPLU());
        // index 19;
        assertEquals("4897", list.get(19).getPLU());
        // index 14
        assertEquals("4060", list.get(14).getPLU());
        // index 15
        assertEquals("4658", list.get(15).getPLU());
    }

    /**
     * This method is testing the size and the correctness of three attributes after
     * adding a new Produce into this list
     */
    @Test
    void testSizeAfterAddingNewProduce(){
        list.add(new Produce("StartFruit","6.66","8900"));
        assertEquals(21,list.size());
        assertEquals("6.66",list.get(20).getPrice());
        assertEquals("StartFruit",list.get(20).getName());
        assertEquals("8900",list.get(20).getPLU());
    }
    
    /**
     * This test method is using for to review the functionalities of the responsibility of
     * BackendDeveloper
     */
    @Test
    void CodeReviewOfBackendDeveloperOfGetSize(){
        ProduceBackend2 test = new ProduceBackend2();
        for (int i = 0; i < list.size(); i++){
            test.addProduce((Produce) list.get(i));
            assertEquals(i+1,test.getSize());
        }
        assertEquals(20,test.getSize());
        assertNotEquals(21,test.getSize());
        assertNotEquals(22,test.getSize());
    }

    /**
     * This test method is using for to review the functionalities of the responsibility of
     * BackendDeveloper
     */
    @Test
    void CodeReviewOfBackendDeveloperOfSearchByPLU(){
        ProduceBackend2 test = new ProduceBackend2();
        for (int i = 0; i < list.size(); i++){
            test.addProduce((Produce) list.get(i));
            assertEquals(i+1,test.getSize());
        }
            assertEquals(list.get(0), test.searchProduce("4098"));
            assertEquals(list.get(1), test.searchProduce("4924"));
            assertEquals(list.get(16),test.searchProduce("4656"));
            assertEquals(list.get(19),test.searchProduce("4897"));
    }
    /**
     * Tests the iterator by going through a for-loop
     */
    @Test
    public void IntegrationTest1OfIterator() {
        RedBlackTree<Integer> rbt= new RedBlackTree<>();
        for(int i = 0; i < 40; rbt.insert(i++)) {}
        int j = 0;
        for(int i : rbt) {
            assertEquals(i, j++);
        }
    }

    /**
     * Tests the PLU validator
     */
    @Test
    public void IntegrationTest2OfValidator() {
        IPLUValidator v = new PLUValidator();
        assertFalse(v.validate("232"));
        assertTrue(v.validate("4444"));
        assertFalse(v.validate("0224"));
        assertFalse(v.validate("432"));
        assertTrue(v.validate("4544"));
        assertFalse(v.validate("24"));
        assertFalse(v.validate("232"));
        assertTrue(v.validate("2334"));
        assertFalse(v.validate("024424"));
    }


}
