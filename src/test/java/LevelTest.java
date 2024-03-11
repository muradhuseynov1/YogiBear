/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.rx15mw_assignment3.Basket;
import com.mycompany.rx15mw_assignment3.Level;
import com.mycompany.rx15mw_assignment3.Obstacle;
import com.mycompany.rx15mw_assignment3.Ranger;
import com.mycompany.rx15mw_assignment3.YogiBear;
import java.util.List;
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
public class LevelTest {
    
    public LevelTest() {
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
    private Level level = new Level();
    
    
    @Test
    public void testLoadLevel() {
        level.loadLevel("data/levels/level1.txt", null); // Assuming null is okay for YogiBear in tests

        List<Obstacle> obstacles = level.getObstacles();
        List<Ranger> rangers = level.getRangers();
        List<Basket> baskets = level.getBaskets();
        YogiBear yogiBear = level.getYogiBear();

        assertFalse(obstacles.isEmpty(), "Obstacles should not be empty");
        assertFalse(rangers.isEmpty(), "Rangers should not be empty");
        assertFalse(baskets.isEmpty(), "Baskets should not be empty");
        assertNotNull(yogiBear, "YogiBear should not be null");
    }
}
