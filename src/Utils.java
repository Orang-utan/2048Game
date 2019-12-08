import java.awt.Color;

public class Utils {
	
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
		
		/*
		 * Color scheme is cited from 2048
		 * */
		public static Color getColor(int number) {
			switch(number) {
			case 0:
				return new Color(236, 240, 241);
			case 2:
				return new Color(82, 101, 112);
			case 4:
				return new Color(58, 172, 156);
			case 8:
				return new Color(147,85,17);
			case 16:
				return new Color(176,39,135);
			case 32:
				return new Color(255, 203, 6);
			case 64:
				return new Color(0, 161, 208);
			case 128:
				return new Color(2, 124, 72);
			case 256:
				return new Color(255, 97, 1);
			case 512:
				return new Color(191, 208, 4);
			case 1024:
				return new Color(0, 61, 113);
			case 2048:
				return new Color(241, 57, 99);
			default:
				return new Color(236, 240, 241);
			}
		}

}
