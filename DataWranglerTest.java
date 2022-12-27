import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;

/**
 * This class tests the various functionalities of classes used by DataWrangler
 */
public class DataWranglerTest {

    private HouseLoader testLoader;

    @BeforeEach
    public void initialize() {

        testLoader = new HouseLoader();
    }

    @Test
    /**
     * This method ensures that all houses in the file are correctly processed
     */
    public void test1() {
        try {
            ArrayList<House> houses = testLoader.loadHouses("test.xml");
            int totalPrice = 0;
            for (House house : houses) {
                totalPrice += house.getPrice();
            }

            assertEquals(totalPrice, 1530000);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    /**
     * This method ensures that the list of houses is the correct size
     */
    public void test2() {
        try {
            ArrayList<House> houses = testLoader.loadHouses("test.xml");

            assertEquals(houses.size(), 3);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
/**
 * This method ensures that an InputMismatchException is thrown when a file not
 * containing correct data is passed
 */
    public void test3() {
        try {
            ArrayList<House> houses = testLoader.loadHouses("invalid.xml");

        } catch (InputMismatchException e) {
            assertTrue(true);
        }

        catch (Exception e) {

            fail("An unexpected exception was thrown");
        }

    }

    @Test
    /**
     * This method ensures that the getter functions defined in house class work
     * properly
     */
    public void test4() {
        House house = new House(100, 50, "St.Paul");
        assertEquals(house.getPrice(), 100);
        assertEquals(house.getFootage(), 50);
        assertEquals(house.getLocation(), "St.Paul");
    }

    @Test
    /**
     * This method ensures a FileNotFoundException is thrown when the filepath
     * specified is not valid
     */
    public void test5() {
        try {

            testLoader.loadHouses("notreal.xml");
            fail("Error, a FileNotFoundException was not thrown");

        } catch (FileNotFoundException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("Error, an unexpected Exception was thrown");
        }
    }

}

