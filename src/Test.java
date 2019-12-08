import java.util.ArrayList;
import java.util.Random;

public class Test {

	public static int getRandomNumberInts(int min, int max) {
		Random random = new Random();
		return random.ints(min, (max + 1)).findFirst().getAsInt();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ArrayList<String> al = new ArrayList<String>();
		al.add("Hello");
		
		
		String ele = al.get(0);
		ele = "Hello world";
		
		System.out.println(al);
		System.out.println(ele);
	}

}
