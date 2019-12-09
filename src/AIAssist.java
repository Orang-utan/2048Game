import java.util.ArrayList;
import java.util.HashMap;

public class AIAssist {
	
	private static ArrayList<int[]> al = new ArrayList<int[]>();
	
	public static void addPermutations(int[] n, int[] Nr, int idx) {
	    if (idx == n.length) {  
	        int[] arr = new int[idx];
	        for(int i = 0; i < arr.length; i ++) {
	        	arr[i] = n[i];
	        }
	        
	        al.add(arr);
	        return;
	    }
	    for (int i = 0; i <= Nr[idx]; i++) { 
	        n[idx] = i;
	        addPermutations(n, Nr, idx+1); 
	    }
	}
	   
	
	public static String greedyOptimizer(int movesAhead, int[][] initialState, int scoreNum) {
		// Create possible moves
		int[] n = new int[movesAhead]; // possible moves ahead
		int[] Nr = new int[movesAhead];
		for(int i = 0; i < Nr.length; i ++) {
			Nr[i] = 3; // possible action, left, right, up, down
		}
		addPermutations(n, Nr, 0);
		
		HashMap<Integer, int[]> scoreMap = new HashMap<Integer, int[]>();
		
		// Run all possible moves in a sandbox
		for(int[] move: al) {
			GameBoard sandbox = new GameBoard(null, null, null);
			sandbox.setSandboxMode(true);
			sandbox.sandboxReset(Utils.copyArray(initialState), scoreNum);
			
			for(int i = 0; i < move.length; i ++) {
				parseMoves(sandbox, move[i]);
			}
			
			int[] arr = new int[move.length];
	        for(int i = 0; i < arr.length; i ++) {
	        	arr[i] = move[i];
	        }
			
			scoreMap.put(sandbox.getScoreNum(), arr);
		}
		
		int maxVal = 0;
		
		for (Integer score: scoreMap.keySet()){
            if(score >= maxVal) {
            	maxVal = score;
            }
		} 
		
		int[] optimalMoves = scoreMap.get(maxVal);
		
		return parseNumbersToMoves(optimalMoves) + " (Expected: " + maxVal + ")";
		
	}
	
	private static void parseMoves(GameBoard g, int move) {
		switch(move) {
		case 0:
			g.move(Direction.LEFT);
			break;
		case 1:
			g.move(Direction.RIGHT);
			break;
		case 2:
			g.move(Direction.UP);
			break;
		case 3:
			g.move(Direction.DOWN);
			break;
		default:
			g.move(Direction.UP);
			break;
		}	
	}
	
	private static String parseNumbersToMoves(int[] moves) {
		String s = "";
		for(int i = 0; i < moves.length; i ++) {
			s += parseNumberToMove(moves[i]);
			if(i < moves.length - 1) {
				s += ", ";
			}
		}
		return s;
	}
	
	private static String parseNumberToMove(int move) {
		switch(move) {
		case 0:
			return "Left";
		case 1:
			return "Right";
		case 2:
			return "Up";
		case 3:
			return "Down";
		default:
			return "Up";
		}	
	}
	

}
