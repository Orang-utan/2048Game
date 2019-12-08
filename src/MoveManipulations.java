import java.util.ArrayList;

public class MoveManipulations {
	
	// after tile reaches upper bound, it gets reset to 0
	public static final int UPPER_BOUND = 2048; 

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
		int[][] newState = Utils.copyArray(latestState);

		for (int i = 0; i < newState.length; i++) {
			for (int j = newState[0].length - 1; j > 0; j--) {
				int current = newState[i][j];
				int prev = newState[i][j - 1];
				if(current == prev) {
					if(current + prev >= UPPER_BOUND) {
						newState[i][j] = 0;
					} else {
						newState[i][j] = current + prev;
					}
					newState[i][j - 1] = 0;
				} 
			}
		}
		return newState;
	}
	
	public static int[][] slideLeft(int[][] latestState) {
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
			// push zeros in to back
			int numOfZeros = 4 - newRow.size();
			for (int k = 0; k < numOfZeros; k++) {
				newRow.add(newRow.size(), 0);
			}

			// add new row to state
			for (int n = 0; n < latestState[0].length; n++) {
				newState[i][n] = newRow.get(n);
			}
		}
		return newState;
	}
	
	public static int[][] mergeLeft(int[][] latestState) {
		// create a new state to be modified
		int[][] newState = Utils.copyArray(latestState);

		for (int i = 0; i < newState.length; i++) {
			for (int j = 0; j < newState[0].length - 1; j++) {
				int current = newState[i][j];
				int prev = newState[i][j + 1];
				if(current == prev) {
					if(current + prev >= UPPER_BOUND) {
						newState[i][j] = 0;
					} else {
						newState[i][j] = current + prev;
					}
					newState[i][j + 1] = 0;
				} 
			}
		}
		return newState;
	}
	
	public static int[][] slideDown(int[][] latestState) {
		// create a new state to be modified
		int[][] newState = new int[4][4];

		for (int i = 0; i < latestState.length; i++) {
			ArrayList<Integer> newCol = new ArrayList<Integer>();
			for (int j = 0; j < latestState[0].length; j++) {
				if (latestState[j][i] != 0) {
					// get all non-zero vals
					newCol.add(latestState[j][i]);
				}
			}
			// push zeros in front
			int numOfZeros = 4 - newCol.size();
			for (int k = 0; k < numOfZeros; k++) {
				newCol.add(0, 0);
			}

			// add new row to state
			for (int n = 0; n < latestState[0].length; n++) {
				newState[n][i] = newCol.get(n);
			}
		}
		return newState;
	}
	
	public static int[][] mergeDown(int[][] latestState) {
		// create a new state to be modified
		int[][] newState = Utils.copyArray(latestState);

		for (int i = 0; i < newState.length; i++) {
			for (int j = newState[0].length - 1; j > 0; j--) {
				int current = newState[j][i];
				int prev = newState[j - 1][i];
				if(current == prev) {
					if(current + prev >= UPPER_BOUND) {
						newState[j][i] = 0;
					} else {
						newState[j][i] = current + prev;
					}
					newState[j - 1][i] = 0;
				} 
			}
		}
		return newState;
	}
	
	public static int[][] slideUp(int[][] latestState) {
		// create a new state to be modified
		int[][] newState = new int[4][4];

		for (int i = 0; i < latestState.length; i++) {
			ArrayList<Integer> newCol = new ArrayList<Integer>();
			for (int j = 0; j < latestState[0].length; j++) {
				if (latestState[j][i] != 0) {
					// get all non-zero vals
					newCol.add(latestState[j][i]);
				}
			}
			// push zeros in to back
			int numOfZeros = 4 - newCol.size();
			for (int k = 0; k < numOfZeros; k++) {
				newCol.add(newCol.size(), 0);
			}

			// add new row to state
			for (int n = 0; n < latestState[0].length; n++) {
				newState[n][i] = newCol.get(n);
			}
		}
		return newState;
	}
	
	public static int[][] mergeUp(int[][] latestState) {
		// create a new state to be modified
		int[][] newState = Utils.copyArray(latestState);

		for (int i = 0; i < newState.length; i++) {
			for (int j = 0; j < newState[0].length - 1; j++) {
				int current = newState[j][i];
				int prev = newState[j + 1][i];
				if(current == prev) {
					if(current + prev >= UPPER_BOUND) {
						newState[j][i] = 0;
					} else {
						newState[j][i] = current + prev;
					}
					newState[j + 1][i] = 0;
				} 
			}
		}
		return newState;
	}
	
}
