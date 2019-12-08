/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.*;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact with one another. Take
 * time to understand how the timer interacts with the different methods and how it repaints the GUI
 * on every tick().
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    public boolean playing = false; 
    private JLabel status; 

    public static final int COURT_WIDTH = 400;
    public static final int COURT_HEIGHT = 400;

    public static final int INTERVAL = 35;
    
    private ArrayList<Tile[][]> history = new ArrayList<Tile[][]>();
    
    
    private int getRandomNumberInts(int min, int max) {
		Random random = new Random();
		return random.ints(min, (max + 1)).findFirst().getAsInt();
	}
    
    // place tile in random location that is empty
    private void placeRandomTile() {
    	// get a copy of the latest state
    	Tile[][] state = history.get(history.size() - 1); 
    	
    	if(playerLost()) {
    		return;
    	}
    	
    	int random_x = getRandomNumberInts(0, 3);
    	int random_y = getRandomNumberInts(0, 3);
    	
    	while(state[random_x][random_y] != null) {
    		random_x = getRandomNumberInts(0, 3);
    		random_y = getRandomNumberInts(0, 3);
    	}
    	
    	state[random_x][random_y] = new Tile(COURT_WIDTH, COURT_WIDTH, Color.blue, random_y * 100, random_x * 100);	
    	
    	history.set(history.size() - 1, state);
    	
    	repaint();
    }
    
    // if board is fully filled, then player lost
    private boolean playerLost() {
    	// get a copy of the latest state
    	Tile[][] state = history.get(history.size() - 1); 
    	
    	for(int i = 0; i < state.length; i ++) {
        	for(int j = 0; j < state[0].length; j ++) {	
        		if(state[i][j] == null) {
        			return false;
        		}	
            }
        }
    	return true;
    }
   
    // check if two states is the same or not
    private boolean isSameState(Tile[][] state, Tile[][] prevState) {
    	for(int i = 0; i < state.length; i ++) {
	    	for(int j = 0; j < state[0].length; j ++) {	
	    		if(state[i][j] != null && prevState[i][j] != null) {
	    			return true;
	    		}
	    	}
    	}
    	return false;
    }
    
    private void moveLeft() {
    	// get a copy of the latest state
    	Tile[][] state = new Tile[4][4]; 
    	Tile[][] prevState = history.get(history.size() - 1);
    	
    	for(int i = 0; i < state.length; i ++) {
        	for(int j = 0; j < state[0].length; j ++) {	
        		if(prevState[i][j] != null) {
        			state[i][j] = new Tile(prevState[i][j]);
        		}
        		
        		// non-empty value
        		if(state[i][j] != null) {
        			// if it's not on the edge
        			if(!(j == 0)) {
	        			int farthestEmptyIndex = 0;	
	        			for(int k = 0; k < j; k ++) {
	        				if(state[i][k] == null) {
	        					farthestEmptyIndex = k;
	        					state[i][farthestEmptyIndex] = state[i][j];
	    	        			state[i][j] = null;
	        					break;
	        				} else {
	        					// there is collision!!
	        					System.out.println("COLLIDE!!");
	        					for(int n = 0; n < state[0].length - 1; n ++) {
	        						// null check
	        						if(state[i][n] != null) {
	        							System.out.println("not null");
	        							// if tile and next tile equal then merge
		        						if(state[i][n].getNumber() == (state[i][n + 1].getNumber())) {
		        							System.out.println("equl");
		        							// change number to the first tile
		        							state[i][n].setNumber(state[i][n + 1]);
		        							// change the second tile to null
		        							state[i][n + 1] = null;
		        						}
	        						}
	        					}
	        				}
	        				
	        			}
	        			
        			}
        		}
            }
        }
    	
    	history.add(state);
    	repaint();
    }

    public GameBoard(JLabel status) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); 

        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                	moveLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                	System.out.println("right");
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                	System.out.println("down");
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                	System.out.println("up");
                }
                placeRandomTile();
            }
        });

        this.status = status;
    }

    public void reset() {
    	// clear state
    	history.clear();
    	Tile[][] state = new Tile[4][4];
    	history.add(state);
    	// place two random tiles in the beginning
        placeRandomTile();
        placeRandomTile();

        playing = true;
        status.setText("Running...");

        requestFocusInWindow();
        repaint();
    }

    void tick() {
        if (playing) {
        	if (playerLost()) {
                playing = false;
                status.setText("You lose!");
            } 
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // get a reference of the latest state
    	Tile[][] state = history.get(history.size() - 1); 
        // paint all non-null elements
        for(int i = 0; i < state.length; i ++) {
        	for(int j = 0; j < state[0].length; j ++) {	
        		if(state[i][j] != null) {
        			state[i][j].setPx(j * 100);
        			state[i][j].setPy(i * 100);
        			state[i][j].draw(g);
        		}	
            }
        }
        
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
    
   
}