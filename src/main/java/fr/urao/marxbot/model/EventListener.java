package fr.urao.marxbot.model;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter {
	
	@Override
	public void onReady(ReadyEvent event)
	{
		System.out.println("[INFO] MarxBot ready !");
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event)
	{
		if(event.isFromType(ChannelType.TEXT)){
			TextChannel textChannel = event.getTextChannel();
			
			String msgText = event.getMessage().getContentDisplay();
			String[] msgParts = msgText.split(" ");
			
			if(msgParts[0].equals("@MarxBot") && msgParts.length >= 2 && !event.getAuthor().isBot()){
				// Case where MarxBot is called
				
				if(msgParts[1].equals("cite")) {
					// Case where MarxBot should do something about citations.
					
					if(msgParts.length >= 3 && msgParts[2].equals("reload")){
						// Case where MarxBot should reload the citations from the citation file.
						boolean reloaded = CitationManager.reload();
						
						if(reloaded){ textChannel.sendMessage("Citation file successfully reloaded !").queue(); }
					} else {
						// Case where MarxBot is asked a random citation.
						String citation = CitationManager.getRandom();
						
						if (citation != null) {
							textChannel.sendMessage(citation).queue();
						}
					}
				} else if(msgParts[1].equals("shutdown") && event.getAuthor().getId().equals("133318295003594752")){
					System.exit(0);
				}
				
			}
		}
	}
	
	
}