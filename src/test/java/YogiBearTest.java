/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.rx15mw_assignment3.YogiBear;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Murad Hüseynov
 */
public class YogiBearTest {
    
    public YogiBearTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testInitialPosition() {
        YogiBear yogi = new YogiBear(100, 100, 40, 40, null); 
        assertEquals(100, yogi.getX(), "X position should be 100");
        assertEquals(100, yogi.getY(), "Y position should be 100");
    }

    @Test
    public void testInitialLives() {
        YogiBear yogi = new YogiBear(100, 100, 40, 40, null); 
        assertEquals(3, yogi.getLives(), "Initial lives should be 3");
    }
}
