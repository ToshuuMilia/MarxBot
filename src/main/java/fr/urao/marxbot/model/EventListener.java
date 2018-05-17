package fr.urao.marxbot.model;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.requests.restaction.MessageAction;

public class EventListener extends ListenerAdapter {
	
	@Override
	public void onReady(ReadyEvent event)
	{
		System.out.println("[INFO] MarxBot ready !");
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event)
	{
		String[] citations = {
				"“Les prolétaires n’ont rien à perdre que leurs chaînes. Ils ont un monde à gagner. Prolétaires de tous les pays, unissez-vous !” - Karl Marx",
				"“Les philosophes n'ont fait qu'interpréter diversement le monde, il s'agit maintenant de le transformer.” - Karl Marx",
				"“Les pensées de la classe dominante sont aussi, à toutes les époques, les pensées dominantes.” - Karl Marx",
				"“Dans la famille, l'homme est le bourgeois ; la femme joue le rôle du prolétariat.” - Karl Marx",
				"“L'athéisme est une négation de Dieu, et par cette négation, il pose l'existence de l'homme.” - Karl Marx",
				"“Il n’y a qu’une seule façon de tuer le capitalisme : des impôts, des impôts et toujours plus d’impôts.” - Karl Marx",
				"“Ce n'est pas la conscience des hommes qui déterminent leur existence, c'est au contraire leur existence sociale qui détermine leur conscience.” - Karl Marx",
				"“Toute classe qui aspire à la domination doit conquérir d'abord le pouvoir politique pour représenter à son tour son intérêt propre comme étant l'intérêt général.” - Karl Marx"};
				
		if(event.isFromType(ChannelType.TEXT)){
			Guild guild = event.getGuild();
			TextChannel textChannel = event.getTextChannel();
			Member member = event.getMember();
			
			String msgText = event.getMessage().getContentDisplay();
			if(msgText.contains("@MarxBot")){
				int nc = (int) (Math.random()*citations.length);
				
				textChannel.sendMessage(citations[nc]).queue();
				System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), member.getNickname(), msgText);
			}
		}
	}
	
	
}