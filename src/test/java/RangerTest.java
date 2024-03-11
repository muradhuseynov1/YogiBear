/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.rx15mw_assignment3.Obstacle;
import com.mycompany.rx15mw_assignment3.Ranger;
import java.util.ArrayList;
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
public class RangerTest {
    
    public RangerTest() {
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
    private Ranger ranger = new Ranger(100, 100, "data/media/sprite.png", 'H', 50, 50);;
    
    @Test
    public void testHorizontalMovement() {
        int initialX = ranger.getX();
        ranger.update(new ArrayList<>()); 
        assertNotEquals(initialX, ranger.getX()); 
    }
    
    @Test
    public void testCollisionWithObstacle() {
        ranger = new Ranger(100, 100, "data/media/sprite.png", 'H', 50, 50);
        Obstacle obstacle = new Obstacle(110, 100, "data/media/obstacle.png", 50, 50);
        List<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(obstacle);

        ranger.update(obstacles);
        assertEquals(100, ranger.getX()); 
    }
    
}
