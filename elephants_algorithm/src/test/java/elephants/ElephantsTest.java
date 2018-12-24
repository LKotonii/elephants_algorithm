package elephants;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.util.StringTokenizer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class ElephantsTest {
	
	private static String projectPath = "/elephants_algorithm/";

	/*
	 * Method creates a .csv file int test/resources folder with two columns: file name(the name of .in file
	 * from which the data is loaded) and output(long extracted from the corresponding .out file) 
	 */

	@BeforeAll
	public static void createFileWithTestData() {
		try {
			//create array of the names of files in testFiles/in folder
			File in = new File(projectPath + "testFiles/in");
			String[] inputFilesNames = in.list();
			//create array of the names of files in testFiles/out folder
			File out = new File(projectPath + "testFiles/out");
			String[] filesWithOutputs = out.list();

			// create .csv file 
			FileOutputStream fos = new FileOutputStream(projectPath + "src/test/resources/" + "test_data.csv");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeChars("fileName,output" + "\n");

			BufferedReader reader = null;
			
			for (int i = 0; i < filesWithOutputs.length; i++) {
				reader = new BufferedReader(new FileReader(projectPath + "testFiles/out/" + filesWithOutputs[i]));
				String output = new StringTokenizer(reader.readLine()).nextToken();
				oos.writeUTF(inputFilesNames[i] + "," + output + "\n");
			}

			reader.close();

			oos.close();

		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@ParameterizedTest(name = "fileName= {0}, output= {1}")
	@CsvFileSource(resources = "/test_data.csv", numLinesToSkip = 1)
	public void should_Count_MinCostOfPermutation(String fileName, String output) {

		// given
		
		long desiredOutput = Long.parseLong(output);
		Elephants testElephants = new Elephants();

		// when
		testElephants.loadDataFromFile(projectPath + "testFiles/in/" + fileName);
		long result = testElephants.countMinCost();

		// then
		assertEquals(result, desiredOutput);

	}
	
	
}
