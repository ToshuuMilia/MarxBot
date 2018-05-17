package fr.urao.marxbot.model;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter {
	
	private static CitationManager citationManager = new CitationManager();
	
	@Override
	public void onReady(ReadyEvent event){
		System.out.println("[INFO] MarxBot ready !");
		citationManager.reload();
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
					
					citationManager.manageCall(event, msgParts);
				} else if(msgParts[1].equals("shutdown") && event.getAuthor().getId().equals("133318295003594752")){
					System.exit(0);
				}
				
			}
		}
	}
	
	
}