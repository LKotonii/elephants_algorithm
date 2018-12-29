package elephants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.StringTokenizer;

public class Elephants {

	public static final int MAX_WEIGHT = 6500; // maximum weight of the elephant(from the file with description of the
												// task)

	private int nElephants;
	private int[] weights;
	private int[] origSequence;
	private int[] permutations; // the elephant i (from the origSequence) should turn out on the position of
								// elephant permutations[i]

	private boolean[] checked; // booleans will be used to indicate whether the elephant was checked or it still
								// should change it's position
	
	
	
	public void loadDatafromInputStream(InputStreamReader inputStreamReader) {

		Scanner scanner = new Scanner(inputStreamReader);

		this.nElephants = scanner.nextInt();

		this.weights = new int[this.nElephants];
		this.origSequence = new int[this.nElephants];
		this.permutations = new int[this.nElephants];

		for (int i = 0; i < this.nElephants; i++) {
			this.weights[i] = scanner.nextInt();
		}

		for (int i = 0; i < this.nElephants; i++) {
			this.origSequence[i] = scanner.nextInt() - 1;
		}

		for (int i = 0; i < this.nElephants; i++) {
			this.permutations[scanner.nextInt() - 1] = this.origSequence[i];
		}

		scanner.close();
	}
	

	/*
	 * Method extract the data from the file and and assign it to the corresponding
	 * variables
	 * 
	 * @param : file name
	 */

	public void loadDataFromFile(String fileName) {

		try {
			BufferedReader rd = new BufferedReader(new FileReader(fileName));
			StringTokenizer fromFile = new StringTokenizer(rd.readLine());

			this.nElephants = Integer.parseInt(fromFile.nextToken());

			this.weights = new int[this.nElephants];
			this.origSequence = new int[this.nElephants];
			this.permutations = new int[this.nElephants];

			fromFile = new StringTokenizer(rd.readLine());
			for (int i = 0; i < this.nElephants; i++) {
				this.weights[i] = Integer.parseInt(fromFile.nextToken());
			}
			fromFile = new StringTokenizer(rd.readLine());
			for (int i = 0; i < this.nElephants; i++) {
				this.origSequence[i] = Integer.parseInt(fromFile.nextToken()) - 1;
			}

			fromFile = new StringTokenizer(rd.readLine());
			for (int i = 0; i < this.nElephants; i++) {
				this.permutations[Integer.parseInt(fromFile.nextToken()) - 1] = this.origSequence[i];
			}

			rd.close();
		
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}
	}

	public long countMinCost() {
		int minTotalWeight = MAX_WEIGHT;
		this.checked = new boolean[this.nElephants];
		long result = 0;

		for (int i = 0; i < this.nElephants; ++i) {
			// find minimum weight of the elephant in set
			minTotalWeight = Math.min(minTotalWeight, this.weights[i]);
		}

		for (int i = 0; i < this.nElephants; ++i) {
			if (!this.checked[i]) {
				int minCycleWeight = MAX_WEIGHT;
				long cycleSum = 0;
				int index = i;
				int cycleLength = 0;
				for (;;) {
					// find minimum weight of the elephant in the local cycle
					minCycleWeight = Math.min(minCycleWeight, this.weights[index]);
					cycleSum += this.weights[index];

					index = this.permutations[index];
					this.checked[index] = true;
					++cycleLength;

					if (index == i) {
						break;
					}

				}
				// find out most efficient approach(that which generates smaller cost)
				result += Math.min(cycleSum + (cycleLength - 2) * (long) minCycleWeight,
						cycleSum + minCycleWeight + (cycleLength + 1) * (long) minTotalWeight);
			}
		}

		return result;
	}

}
