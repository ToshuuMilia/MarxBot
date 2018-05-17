package fr.urao.marxbot.model;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CitationManager {
	
	private static List<String> citations = null;
	
	public static boolean reload(){
		boolean reloaded = false;
		
		try(BufferedReader reader = new BufferedReader(new FileReader("external_ressources/citations.txt"))){
			citations = new ArrayList<>();
			String line;
			
			while((line = reader.readLine()) != null){
					citations.add(line);
			}
			
			reloaded = true;
		}catch(IOException exception){
			System.err.println("[ERR] Unable to reload the citation file.");
		}
		
		return reloaded;
	}
	
	@Nullable
	public static String getRandom(){
		if(citations == null || citations.size() == 0){
			reload();
		}
		
		String citation = null;
		if(citations != null && citations.size() >= 1) {
			int nc = (int) (Math.random() * citations.size());
			
			citation = citations.get(nc);
		}
		
		return citation;
	}
}
