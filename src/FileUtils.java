import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtils {
	
	// save the state to a text file, it overwrites original file content
	public static void saveStateToFile(String filePath, int[][] state) {
		
		try {
			File newFile = new File(filePath);
			newFile.createNewFile();
			
            FileWriter writer = new FileWriter(filePath, false);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            
            for(int i = 0; i < state.length; i ++) {
            	for (int j = 0; j < state[0].length; j ++) {
            		bufferedWriter.write(state[i][j] + ",");
            	}
            	bufferedWriter.newLine();
            }
 
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	// returns all lines in the form of an arraylist, each line is an element
	public static ArrayList<String> readFromFile(String filePath) {
		
		ArrayList<String> allLines = new ArrayList<String>();
		
		try {
            FileReader reader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(reader);
 
            String line;
 
            while ((line = bufferedReader.readLine()) != null) {
                allLines.add(line);
            }
            reader.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return new ArrayList<String>(allLines);
	}
	
	// parse string list back to int[][] state
	public static int[][] parseStateFromStringList(ArrayList<String> al) {
		int numRows = al.size();
		int numCols = al.get(0).split(",").length;
		int[][] state = new int[numRows][numCols];
		
		for(int i = 0; i < state.length; i ++) {
			for(int j = 0; j < state[0].length; j ++) {
				String[] row = al.get(i).split(",");
				state[i][j] = Integer.parseInt(row[j]);
			}
		}	
		
		return Utils.copyArray(state);	
	}
	
	public static int[][] readStateFromFile(String filePath) {
		ArrayList<String> al = readFromFile(filePath);
		int[][] state = parseStateFromStringList(al);
		return state;
	}

}
