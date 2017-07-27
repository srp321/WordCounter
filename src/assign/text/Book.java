package assign.text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Stream;

/**
 * Program to get Set of Words and Their Respective Page Numbers on Which They
 * are Written, Similar to a Glossary of any Book 
 * Input: Set Of Valid Pages; One Exclusion-List 
 * Output: File With List of All Words with Page Numbers
 * 
 * Note: Please Set The Folder Path Correctly in BookUtil.java Before Program Execution
 * 
 * @author Shashi
 */
public class Book {

	public static void main(String[] args) throws IOException {

		// Get All the Pages
		File file = new File(BookUtil.directory);
		List<String> allPages = new ArrayList<>();
		try {
			Stream.of(file.list((pFile, pString) -> pString.startsWith("page"))).forEach(allPages::add);
		}
		catch (Exception e) {
			System.out.println("No Pages Found!! \nCheck The Folder Structure For Reading The Pages.");
		}

		// Get All the Necessary Words With Pages
		LinkedHashMap<Integer, LinkedHashSet<String>> allData = BookUtil.getAllPageData(allPages);
		List<String> words = new ArrayList<>(BookUtil.allWords);

		LinkedHashMap<String, HashSet<Integer>> output = new LinkedHashMap<>();

		// Finding the Matching Words and Their Count
		for (int i = 0; i < words.size(); i++) {
			HashSet<Integer> val = new HashSet<>();
			for (int j = 0; j < allData.size(); j++) {
				if (allData.get(j).toString().toLowerCase().contains(words.get(i).toLowerCase())) {
					val.add(j + 1);
				}
			}
			output.put(words.get(i), val);
		}

		// Writing to Output File in Order of Pages
		BookUtil.writeDataToFile(output);
	}

}
