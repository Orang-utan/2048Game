
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
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

	public boolean playing = false;
	private JLabel status;

	public static final int COURT_WIDTH = 400;
	public static final int COURT_HEIGHT = 400;

	public static final int INTERVAL = 35;

	private ArrayList<int[][]> history = new ArrayList<int[][]>();

	public static int getRandomNumberInts(int min, int max) {
		Random random = new Random();
		return random.ints(min, (max + 1)).findFirst().getAsInt();
	}

	// copy array
	public static int[][] copyArray(int[][] state) {
		int[][] newArr = new int[state.length][state[0].length];

		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				newArr[i][j] = state[i][j];
			}
		}

		return newArr;
	}

	// place random tile
	// returns a new Array, original array remains unchanged
	public static int[][] placeRandomTile(int[][] state) {
		int randomX = getRandomNumberInts(0, state.length - 1);
		int randomY = getRandomNumberInts(0, state[0].length - 1);

		int[][] newArr = copyArray(state);

		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				newArr[i][j] = state[i][j];
			}
		}

		while (newArr[randomX][randomY] != 0) {
			randomX = getRandomNumberInts(0, 3);
			randomY = getRandomNumberInts(0, 3);
		}

		newArr[randomX][randomY] = 2;

		return newArr;
	}

	// check if two states is the same or not
	public static boolean isSameState(int[][] state1, int[][] state2) {
		assert (state1.length == state2.length) && (state1[0].length == state2[0].length);

		for (int i = 0; i < state1.length; i++) {
			for (int j = 0; j < state1[0].length; j++) {
				if (state1[i][j] != state2[i][j])
					return false;
			}
		}
		return true;
	}

	// if board is fully filled, then player lost
	private boolean playerLost() {
		int[][] latestState = history.get(history.size() - 1);
		for (int i = 0; i < latestState.length; i++) {
			for (int j = 0; j < latestState[0].length; j++) {
				if (latestState[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	public static int[][] slideRight(int[][] latestState) {
		// create a new state to be modified
		int[][] newState = new int[4][4];

		for (int i = 0; i < latestState.length; i++) {
			ArrayList<Integer> newRow = new ArrayList<Integer>();
			for (int j = 0; j < latestState[0].length; j++) {
				if (latestState[i][j] != 0) {
					// get all non-zero vals
					newRow.add(latestState[i][j]);
				}
			}
			// push zeros in front
			int numOfZeros = 4 - newRow.size();
			for (int k = 0; k < numOfZeros; k++) {
				newRow.add(0, 0);
			}

			// add new row to state
			for (int n = 0; n < latestState[0].length; n++) {
				newState[i][n] = newRow.get(n);
			}
		}
		return newState;
	}
	
	public static int[][] mergeRight(int[][] latestState) {
		// create a new state to be modified
		int[][] newState = copyArray(latestState);

		for (int i = 0; i < newState.length; i++) {
			for (int j = newState[0].length - 1; j > 0; j--) {
				int current = newState[i][j];
				int prev = newState[i][j - 1];
				if(current == prev) {
					newState[i][j] = current + prev;
					newState[i][j - 1] = 0;
				} 
			}
		}
		return newState;
	}
	
	private void moveRight() {
		int[][] latestState = history.get(history.size() - 1);
		int[][] newState = slideRight(mergeRight(slideRight(latestState)));
		
		if(isSameState(newState, latestState)) {
			return;
		}
		
		history.add(placeRandomTile(newState));
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
				if(playing) {
					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						// moveLeft();
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						System.out.println("right");
						moveRight();
					} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						System.out.println("down");
					} else if (e.getKeyCode() == KeyEvent.VK_UP) {
						System.out.println("up");
					}
				}
			}
		});

		this.status = status;
	}

	public void reset() {
		// initialize state
		int[][] initialState = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
		// place two random tiles in the beginning
		int[][] modifiedState = placeRandomTile(placeRandomTile(initialState));

		history.add(modifiedState);

		playing = true;
		status.setText("Running...");

		requestFocusInWindow();
		repaint();
	}

	public void revert() {
		System.out.println("revert!");
	}

	public void save() {
		System.out.println("save!");
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

		// paint the last table in the history stack
		int[][] latestState = history.get(history.size() - 1);
		for (int i = 0; i < latestState.length; i++) {
			for (int j = 0; j < latestState[0].length; j++) {
				if (latestState[i][j] != 0) {
					Tile t = new Tile(400, 400, Color.white, j * 100, i * 100);
					t.setNumber(latestState[i][j]);
					t.draw(g);
				}
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}

}