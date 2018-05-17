package fr.urao.marxbot;

import javax.security.auth.login.LoginException;

import fr.urao.marxbot.model.EventListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;


public class BotApp {
	
	public static void main(String[] args)
	{
		//We construct a builder for a BOT account. If we wanted to use a CLIENT account
		// we would use AccountType.CLIENT
		try
		{
			JDA jda = new JDABuilder(AccountType.BOT)
					.setToken(args[0])           //The token of the account that is logging in.
					.addEventListener(new EventListener())  //An instance of a class that will handle events.
					.buildBlocking();  //There are 2 ways to login, blocking vs async. Blocking guarantees that JDA will be completely loaded.
		}
		catch (LoginException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}

