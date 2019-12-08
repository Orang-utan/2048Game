import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.Color;

import org.junit.Test;

/** 
 *  You can use this file (and others) to test your
 *  implementation.
 */

public class GameTest {

    @Test
    public void test() {
        fail();
    }
    
    @Test
    public void tileEquals() {
        Tile t1 = new Tile(100, 200, Color.black, 100, 100);
        Tile t2 = new Tile(100, 200, Color.black, 100, 100);
        
        assertEquals("Compare", t1.equals(t2), true);
    }
    
    @Test
    public void tileNotEquals() {
        Tile t1 = new Tile(100, 200, Color.black, 100, 800);
        Tile t2 = new Tile(100, 200, Color.black, 100, 100);
        
        assertEquals("Compare", t1.equals(t2), false);
    }

}
