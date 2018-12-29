package elephants;

import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
		Elephants el = new Elephants();
		// reading data from the console 
		el.loadDatafromInputStream(new InputStreamReader(System.in));
		
		System.out.println(el.countMinCost());

	}
}
