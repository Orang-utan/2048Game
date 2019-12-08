import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.lang.reflect.Array;
import java.util.Arrays;

import org.junit.Test;

/** 
 *  You can use this file (and others) to test your
 *  implementation.
 */

public class GameTest {
	
	public void print2DArr(int[][] arr) {
		for(int i = 0; i < arr.length; i ++) {
			for(int j = 0; j < arr[0].length; j ++) {
				System.out.print(arr[i][j]+ ",");
			}
			System.out.println("");
		}
	}

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
    
    @Test
    public void placeRandomTileTest() {
    	int[][] initialState = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    	int[][] zeros = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    	int[][] newArr = GameBoard.placeRandomTile(initialState);
    	System.out.println("Place random tile test");
    	print2DArr(initialState);
    	System.out.println("///////////");
    	// Observe new array will have a 2 in a random location
        print2DArr(newArr);
        assertEquals("Compare", Arrays.deepEquals(initialState, zeros), true);
    }
    
    @Test
    public void sameStateCheckTest() {
    	int[][] arr1 = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    	int[][] arr2 = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    	
        assertEquals("Compare", GameBoard.isSameState(arr1, arr2), true);
    }
    
    @Test
    public void slideRightTest() {
    	int[][] arr1 = {{0,0,2,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    	int[][] arr2 = {{0,0,0,2},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    	
    	int[][] modified = GameBoard.slideRight(arr1);
    	System.out.println("Slide right test");
    	print2DArr(modified); 
        assertEquals("Compare", GameBoard.isSameState(modified, arr2), true);
    }
    
    @Test
    public void slideRightTest2() {
    	int[][] arr1 = {{2,0,2,0},{0,0,0,0},{0,0,0,8},{2,2,2,0}};
    	int[][] arr2 = {{0,0,2,2},{0,0,0,0},{0,0,0,8},{0,2,2,2}};
    	
    	int[][] modified = GameBoard.slideRight(arr1);
    	System.out.println("Slide right test 2");
    	print2DArr(modified); 
        assertEquals("Compare", GameBoard.isSameState(modified, arr2), true);
    }
    
    @Test
    public void mergeRightTest() {
    	int[][] arr1 = {{0,0,2,2},{0,0,0,0},{0,0,0,8},{0,2,2,2}};
    	int[][] arr2 = {{0,0,0,4},{0,0,0,0},{0,0,0,8},{0,2,0,4}};
    	
    	int[][] modified = GameBoard.mergeRight(arr1);
    	System.out.println("Merge right test");
    	System.out.println("Modified");
    	print2DArr(modified); 
    	
    	System.out.println("Expected");
    	print2DArr(arr2); 
        assertEquals("Compare", GameBoard.isSameState(modified, arr2), true);
    }

}
