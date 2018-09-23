package seng202.team5.Model;


import org.junit.Test;
import seng202.team5.DataManipulation.DataBaseController;
import java.util.Date;
import static org.junit.Assert.*;

/**
 * This class provides a test for the getBmi() method within
 * the User class.
 */
public class UserTest {

    /**
     * Tests the method that computes bmi within the User class.
     */
    @Test
    public void testBmiNew() {
        Date date = new Date();
        User user = new User("Test", 12, 1.75, 70, date);
        assertEquals(22.86, user.getBmi(), 0.0);
    }



}