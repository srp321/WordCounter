package assign.text;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

public class AllWords {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String folder = "D:\\Workspace_New\\WordCounter\\files\\";
		// Get all the Pages
		File file = new File(folder);
		List<String> allPages = new ArrayList<>();
		Stream.of(file.list((pFile, pString) -> pString.startsWith("page"))).forEach(allPages::add);
		System.out.println(allPages);

		// Create a map with key as page number and value as page data
		HashMap<Integer, HashSet<String>> allData = new HashMap<>();
		for (int i = 0; i < allPages.size(); i++) {
			String text = new String(
					Files.readAllBytes(Paths.get(folder + allPages.get(i))),
					StandardCharsets.UTF_8);
			text = text.replaceAll("[!?,.]", "");
			String[] tmpVals = text.split("\\s+");
			HashSet<String> allText = new HashSet<>(Arrays.asList(tmpVals));
			System.out.println(allText);
			allData.put(i, allText);
		}
		
		// Get the words from the exclusion list
		String text = new String(
				Files.readAllBytes(Paths.get(folder+"exclusion-list.txt")),
				StandardCharsets.UTF_8);
		text = text.replaceAll("[!?,.]", "");
		String[] tmpExcl = text.split("\\s+");
		HashSet<String> exclude = new HashSet<>(Arrays.asList(tmpExcl));
		System.out.println(exclude);
		
		// Remove the Exclusion List Words from All The Pages
		
		
	}

}
