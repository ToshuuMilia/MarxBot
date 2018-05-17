package fr.urao.marxbot.model;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.annotation.Nullable;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CitationManager {
	
	private List<String> citations = null;
	
	public boolean reload(){
		boolean reloaded = false;
		
		// See https://stackoverflow.com/a/20389418
		InputStream in = getClass().getResourceAsStream("/data/citations.txt");
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
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
	private String getRandom(){
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
	
	public void manageCall(MessageReceivedEvent event, String[] msgParts){
		TextChannel textChannel = event.getTextChannel();
		
		if(msgParts.length >= 3 && msgParts[2].equals("reload")){
			// Case where MarxBot should reload the citations from the citation file.
			boolean reloaded = reload();
			
			if(reloaded){ textChannel.sendMessage("Citation file successfully reloaded !").queue(); }
		} else {
			// Case where MarxBot is asked a random citation.
			String citation = getRandom();
			
			if (citation != null) {
				textChannel.sendMessage(citation).queue();
			}
		}
	}
}
