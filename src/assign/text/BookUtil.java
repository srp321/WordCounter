package assign.text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Utility Functions to Get Specific Set of Data as Per Requirement
 * 
 * @author Shashi
 */
public class BookUtil {

	// Please Change This As Per Respective Directory
	static String directory = "D:\\Workspace_New\\WordCounter\\";

	// To Store All Unique Words Across All Pages
	static Set<String> allWords = new LinkedHashSet<>();

	/**
	 * @param All Page Names
	 * @return Map With Key as Page Number and Value as Page Data
	 * @throws IOException
	 */
	public static LinkedHashMap<Integer, LinkedHashSet<String>> getAllPageData(List<String> allPages)
			throws IOException {

		LinkedHashMap<Integer, LinkedHashSet<String>> allData = new LinkedHashMap<>();

		// Get the List of Words to be Excluded
		LinkedHashSet<String> exclude = getExclusionList();
		
		if (allPages.size() == 0) {
			System.out.println("No Pages are provided to read!!");
		}
		
		for (int i = 0; i < allPages.size(); i++) {
			String text = new String(Files.readAllBytes(Paths.get(directory + allPages.get(i))),
					StandardCharsets.UTF_8);
			text = text.replaceAll("[!?,.]", "");
			String[] tmpVals = text.split("\\s+");
			LinkedHashSet<String> tmpVal = new LinkedHashSet<>((Arrays.asList(tmpVals)));

			// Get All Relevant Words
			LinkedHashSet<String> allText = getFilteredList(tmpVal, exclude);

			// Add All Words for Further Comparison
			allWords.addAll(allText);

			// Map a Single Page With the Words Present in it
			allData.put(i, allText);
		}
		return allData;
	}

	/**
	 * @return All the Words From the Exclusion List
	 * @throws IOException
	 */
	public static LinkedHashSet<String> getExclusionList() throws IOException {

		String[] tmpExcl = {};
		
		// Read Words From The Given Exclusion-List File
		try {
			String excl = new String(Files.readAllBytes(Paths.get(directory + "exclude-words.txt")),
					StandardCharsets.UTF_8);
			excl = excl.replaceAll("[)(_-!?,.-]:\"", "");
			tmpExcl = excl.split("\\s+");
		}
		catch (Exception e) {
			System.out.println("No Exclusion List Provided.");
		}

		LinkedHashSet<String> exclude = new LinkedHashSet<>(Arrays.asList(tmpExcl));

		return exclude;
	}

	// Remove the Exclusion List Words from All the Pages

	/**
	 * @param Set of All the Words on a Page
	 * @param List of Words for Exclusion
	 * @return Set of All Relevant Words
	 */
	public static LinkedHashSet<String> getFilteredList(LinkedHashSet<String> allValues,
			LinkedHashSet<String> exclude) {

		LinkedHashSet<String> hs = allValues;
		Iterator<String> itr = hs.iterator();
		while (itr.hasNext()) {

			String tmpVal = itr.next();
			// Remove Irrelevant Words Comparing With The Exclusion List
			if (exclude.contains(tmpVal)) {
				itr.remove();
			}
		}
		return hs;
	}

	/**
	 * @param Writes Word With Pages in Which They are Occurring to a New File
	 * @throws IOException
	 */
	public static void writeDataToFile(LinkedHashMap<String, HashSet<Integer>> output) throws IOException {

		FileOutputStream fos = new FileOutputStream(new File(directory+"index.txt"));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		// Traverse to Get All The Words
		for (Entry<String, HashSet<Integer>> entry : output.entrySet()) {
			bw.write(entry.getKey() + ": " + entry.getValue().toString().replace("[", "").replace("]", ""));
			bw.newLine();
		}
		bw.close();
	}

}
