
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

@SuppressWarnings("serial")
public class GameBoard extends JPanel {

	public boolean playing = false;
	private boolean firstLaunch = true;
	private JLabel status;
	private JLabel score;

	public static final int COURT_WIDTH = 400;
	public static final int COURT_HEIGHT = 400;

	public static final int INTERVAL = 35;
	
	public static final String SAVED_GAME_FILEPATH = "files/SavedGame.txt";

	private ArrayList<int[][]> history = new ArrayList<int[][]>();

	public static int getRandomNumberInts(int min, int max) {
		Random random = new Random();
		return random.ints(min, (max + 1)).findFirst().getAsInt();
	}

	// place random tile
	// returns a new Array, original array remains unchanged
	public static int[][] placeRandomTile(int[][] state) {
		int randomX = getRandomNumberInts(0, state.length - 1);
		int randomY = getRandomNumberInts(0, state[0].length - 1);

		int[][] newArr = Utils.copyArray(state);

		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				newArr[i][j] = state[i][j];
			}
		}

		while (newArr[randomX][randomY] != 0) {
			randomX = getRandomNumberInts(0, state.length - 1);
			randomY = getRandomNumberInts(0, state[0].length - 1);
		}
		
		// 33% chance of generating a 4
		if (getRandomNumberInts(0, 2) == 2) {
			newArr[randomX][randomY] = 4;
		} else {
			newArr[randomX][randomY] = 2;
		}

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

	private void move(Direction d) {
		int[][] latestState = history.get(history.size() - 1);
		int[][] newState;

		switch (d) {
		case RIGHT:
			newState = MoveManipulations
					.slideRight(MoveManipulations.mergeRight(MoveManipulations.slideRight(latestState)));
			break;
		case LEFT:
			newState = MoveManipulations
					.slideLeft(MoveManipulations.mergeLeft(MoveManipulations.slideLeft(latestState)));
			break;
		case DOWN:
			newState = MoveManipulations
					.slideDown(MoveManipulations.mergeDown(MoveManipulations.slideDown(latestState)));
			break;
		case UP:
			newState = MoveManipulations.slideUp(MoveManipulations.mergeUp(MoveManipulations.slideUp(latestState)));
			break;
		default:
			newState = Utils.copyArray(latestState);
			break;
		}

		if (isSameState(newState, latestState)) {
			return;
		}
		
		history.add(placeRandomTile(newState));
		repaint();
	}

	public GameBoard(JLabel status, JLabel score) {
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
				if (playing) {
					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						move(Direction.LEFT);
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						move(Direction.RIGHT);
					} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						move(Direction.DOWN);
					} else if (e.getKeyCode() == KeyEvent.VK_UP) {
						move(Direction.UP);
					}
					FileUtils.saveStateToFile(SAVED_GAME_FILEPATH, history.get(history.size() - 1));
				}
			}
		});

		this.status = status;
		this.score = score;
	}

	public void reset() {
		// automatically read saved state on first launch
		if(firstLaunch) {
			int[][] savedState = FileUtils.readStateFromFile(SAVED_GAME_FILEPATH);
			history.add(savedState);
			firstLaunch = false;
		} else {
			// clear history
			history.removeAll(history);
			// initialize state
			int[][] initialState = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
			// place two random tiles in the beginning
			int[][] modifiedState = placeRandomTile(placeRandomTile(initialState));
			FileUtils.saveStateToFile(SAVED_GAME_FILEPATH, modifiedState);
	
			history.add(modifiedState);
		}

		playing = true;
		status.setText("Running...");

		requestFocusInWindow();
		repaint();
	}

	public void undo() {
		if (history.size() > 1) {
			history.remove(history.size() - 1);
			repaint();
		}
		requestFocusInWindow();
	}

	public void aiSolver() {
		System.out.println("ai!");
		requestFocusInWindow();
	}

	void tick() {
		if (playing) {
			if (playerLost()) {
				playing = false;
				status.setText("You lost!");
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
					int number = latestState[i][j];
					Tile t = new Tile(400, 400, Utils.getColor(number), j * 100, i * 100);
					t.setNumber(number);
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