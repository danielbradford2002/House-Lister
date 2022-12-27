import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class AlgorithmEngineerTests {

    private HouseLoader testLoader;

    @BeforeEach
    public void initialize() {

        testLoader = new HouseLoader();
    }
    /**
     * tests the hasNext() and next() methods' effectiveness by making sure hasNext() returns
     * true 5 times and next successfully moves to the next element on a tree that has five nodes
     * and makes sure that hasNext() finally returns false after it tries to point a sixth
     * element, which does not exist
     */
    @Test
    public void testOne() {
        RedBlackTree<Integer> testRedBlack1 = new RedBlackTree<Integer>();
        RedBlackTreeIterator iter1 = new RedBlackTreeIterator(testRedBlack1);
                testRedBlack1.insert(10);
        testRedBlack1.insert(5);
        testRedBlack1.insert(12);
        testRedBlack1.insert(3);
        testRedBlack1.insert(25);
        iter1.inOrderTraversal(testRedBlack1);
        assertEquals(iter1.hasNext(), true);
        iter1.next();
        assertEquals(iter1.hasNext(), true);
        iter1.next();
        assertEquals(iter1.hasNext(), true);
        iter1.next();
        assertEquals(iter1.hasNext(), true);
        iter1.next();
        assertEquals(iter1.hasNext(), true);
        iter1.next();
        assertEquals(iter1.hasNext(), false);
    }
@Test
    /**
     * tests that the iterator successfully executes an in order traversal on a tree with 5
     * insertions
     */
   public void testTwo(){
       RedBlackTree<Integer> testRedBlack2 = new RedBlackTree<Integer>();
       RedBlackTreeIterator iter2 = new RedBlackTreeIterator(testRedBlack2);
    testRedBlack2.insert(10);
    testRedBlack2.insert(5);
    testRedBlack2.insert(12);
    testRedBlack2.insert(3);
    testRedBlack2.insert(25);
    iter2.inOrderTraversal(testRedBlack2);
        assertEquals(iter2.next(), 3);
       assertEquals(iter2.next(), 5);
       assertEquals(iter2.next(), 10);
       assertEquals(iter2.next(), 12);
       assertEquals(iter2.next(), 25);
   }
@Test
    /**
     * tests iterator's next() method after adding a node that should come first in an in order
     * traversal
     */
    public void testThree(){
//        RedBlackTree<Integer> testRedBlack3 = new RedBlackTree<Integer>();
//        RedBlackTreeIterator iter3 = new RedBlackTreeIterator(testRedBlack3);
        RedBlackTree<Integer> testRedBlack3 = new RedBlackTree<Integer>();
        RedBlackTreeIterator iter3 = new RedBlackTreeIterator(testRedBlack3);
        testRedBlack3.insert(10);
        testRedBlack3.insert(5);
        testRedBlack3.insert(12);
        testRedBlack3.insert(3);
        testRedBlack3.insert(25);
        testRedBlack3.insert(1); //node that comes first in an in-order traversal
        iter3.inOrderTraversal(testRedBlack3);
        assertEquals(iter3.next(), 1);
        assertEquals(iter3.next(), 3);
        assertEquals(iter3.next(), 5);
        assertEquals(iter3.next(), 10);
        assertEquals(iter3.next(), 12);
        assertEquals(iter3.next(), 25);
    }
@Test
    /**
     * test iterator's next() method after inserting a node that should come last in an in order
     * traversal
     */
    public void testFour(){
        RedBlackTree<Integer> testRedBlack4 = new RedBlackTree<Integer>();
        RedBlackTreeIterator iter4 = new RedBlackTreeIterator(testRedBlack4);
        testRedBlack4.insert(10);
        testRedBlack4.insert(5);
        testRedBlack4.insert(12);
        testRedBlack4.insert(3);
        testRedBlack4.insert(25);
        testRedBlack4.insert(1);
        testRedBlack4.insert(90); //node that comes last in an in-order traversal
        iter4.inOrderTraversal(testRedBlack4);
        assertEquals(iter4.next(), 1);
        assertEquals(iter4.next(), 3);
        assertEquals(iter4.next(), 5);
        assertEquals(iter4.next(), 10);
        assertEquals(iter4.next(), 12);
        assertEquals(iter4.next(), 25);
    }
@Test
    /**
     * tests adding a node that should come in the middle of an in-order traversal
     */
    public void testFive() {
    RedBlackTree<Integer> testRedBlack5 = new RedBlackTree<Integer>();
    RedBlackTreeIterator iter5 = new RedBlackTreeIterator(testRedBlack5);
    testRedBlack5.insert(10);
    testRedBlack5.insert(5);
    testRedBlack5.insert(12);
    testRedBlack5.insert(3);
    testRedBlack5.insert(25);
    testRedBlack5.insert(1);
    testRedBlack5.insert(15);
    iter5.inOrderTraversal(testRedBlack5);
    assertEquals(iter5.next(), 1);
    assertEquals(iter5.next(), 3);
    assertEquals(iter5.next(), 5);
    assertEquals(iter5.next(), 10);
    assertEquals(iter5.next(), 12);
    assertEquals(iter5.next(), 15);
    assertEquals(iter5.next(), 25);
}
    @Test
    /**
     * tests iterator in-order traversal on houses prices when inserting house prices by
     * integrating the data wranglers House.java class
     */
    public void TestOneIntegration(){
        House houseInt1 = new House(300000, 2000, "Chicago");
        House houseInt2 = new House(100000, 5000, "Palo Alto");
        House houseInt3 = new House(500000, 1000, "Seattle");
        RedBlackTree<Integer> testRedBlack1Int = new RedBlackTree<Integer>();
        RedBlackTreeIterator iter1Int = new RedBlackTreeIterator(testRedBlack1Int);
        testRedBlack1Int.insert(houseInt1.getPrice());
        testRedBlack1Int.insert(houseInt2.getPrice());
        testRedBlack1Int.insert(houseInt3.getPrice());
        iter1Int.inOrderTraversal(testRedBlack1Int);
        assertEquals(iter1Int.next(), 100000);
        assertEquals(iter1Int.next(), 300000);
        assertEquals(iter1Int.next(), 500000);
    }
    @Test
    /**
     * tests iterator in-order traversal on houses' footage when inserting houses' footage by
     * integrating
     * the data wrangler's House.java class
     */
    public void TestTwoIntegration(){
        House houseInt1 = new House(60000, 3000, "Lexington");
        House houseInt2 = new House(70000, 6000, "Berlin");
        House houseInt3 = new House(15000, 2000, "London");
        RedBlackTree<Integer> testRedBlack1Int = new RedBlackTree<Integer>();
        RedBlackTreeIterator iter1Int = new RedBlackTreeIterator(testRedBlack1Int);
        testRedBlack1Int.insert(houseInt1.getFootage());
        testRedBlack1Int.insert(houseInt2.getFootage());
        testRedBlack1Int.insert(houseInt3.getFootage());
        iter1Int.inOrderTraversal(testRedBlack1Int);
        assertEquals(iter1Int.next(), 2000);
        assertEquals(iter1Int.next(), 3000);
        assertEquals(iter1Int.next(), 6000);
    }

    @Test
    /**
     * tests Data Wrangler's get methods work when there is a house with a very small square
     * footage and price
     */
    public void TestOneCodeReviewOfDataWrangler(){
        House house1 = new House(12, 50, "Green Bay");
        assertEquals(house1.getPrice(), 12);
        assertEquals(house1.getFootage(), 50);
        assertEquals(house1.getLocation(), "Green Bay");
    }

    @Test
    /**
     * tests Data Wrangler's get methods work when multiple houses have been created
     */
    public void testTwoCodeReviewOfDataWrangler(){
        House houseA = new House(123456789, 5000, "Louisville");
        House houseB = new House(55555, 1000, "New York");
        House houseC = new House(88888888, 333, "Memphis");
        assertEquals(houseA.getPrice(), 123456789);
        assertEquals(houseB.getPrice(), 55555);
        assertEquals(houseC.getPrice(), 88888888);
        assertEquals(houseA.getFootage(), 5000);
        assertEquals(houseB.getFootage(), 1000);
        assertEquals(houseC.getFootage(), 333);
        assertEquals(houseA.getLocation(), "Louisville");
        assertEquals(houseB.getLocation(), "New York");
        assertEquals(houseC.getLocation(), "Memphis");

    }

}
