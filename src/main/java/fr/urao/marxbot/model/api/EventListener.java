package fr.urao.marxbot.model.api;

import fr.urao.marxbot.model.database.CitationManager;
import fr.urao.marxbot.model.database.DBManager;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventListener extends ListenerAdapter {
	
	private static CitationManager citationManager = new CitationManager();
	private static DBManager dbManager = new DBManager();
	
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
					// Case where MarxBot should stop.
					System.exit(0);
				} else if(msgParts[1].equals("words")
						&& event.getGuild() != null &&
						(event.getGuild().getId().equals("93251981056970752") || event.getGuild().getId().equals("363070323412697088"))){
					// TODO Case where MarxBot should send the stats about the number of words.
					
					// Getting the link between each nickname and the user id.
					Map<String, String> idNicknameMap = new HashMap<>();
					Guild guild = event.getGuild();
					List<Member> memberList = guild.getMembers();
					memberList.forEach(member -> {
						if(!member.getUser().isBot()) {
							if(member.getNickname() != null) {
								idNicknameMap.put(member.getUser().getId(), member.getNickname());
							} else {
								idNicknameMap.put(member.getUser().getId(), member.getUser().getName());
							}
						}
					});
					
					String message = dbManager.getNumberWords();
					
					for(String id: idNicknameMap.keySet()){
						message = message.replace(id, idNicknameMap.getOrDefault(id, "Unknown"));
					}
					
					textChannel.sendMessage(message).queue();
				}
				
			} else if(!event.getAuthor().isBot() &&
					event.getGuild() != null && event.getGuild().getId().equals("93251981056970752")){
				dbManager.addNumberWords(event.getAuthor().getId(), msgText.split(" ").length);
			}
		}
	}
	
	
}