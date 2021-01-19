package tech.sherrao.discord.ccubed;

import java.awt.Color;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import tech.sherrao.discord.ccubed.imgs.MessageListener;

public class Bot {

	private JDA api;
	private MessageListener messages;
	private ScheduledExecutorService executor;
	private Color color;
	
	public Bot() {
		try {
			api = JDABuilder.createDefault(BotData.TOKEN).build();
			api.awaitReady();

		} catch (LoginException | InterruptedException e) {
			e.printStackTrace();

		}
		
		messages = new MessageListener(this);
		messages.load();
		
		executor = Executors.newScheduledThreadPool(1);
		api.getPresence().setActivity(Activity.watching("over all " + api.getGuildById(BotData.SERVER_ID).getMemberCount() + " of you! 🙃"));
		api.setEventManager(new AnnotatedEventManager());
		api.addEventListener(messages);
		
		color = new Color(51,186,197);
		
		//sendSocials();
		//sendSchools();
		//sendRules();
		//sendInvite();
		
		executor.scheduleWithFixedDelay(() -> {
			api.getPresence().setActivity(Activity.watching("over all " + api.getGuildById(BotData.SERVER_ID).getMemberCount() + " of you! 🙃"));
			
		}, 5, 5, TimeUnit.MINUTES);
		
	}

	public void sendSocials() {
		// Prevents repeated message
		TextChannel c = api.getTextChannelById(BotData.SOCIAL_CHANNEL_ID);
		deleteMessages(c);
		
		c.sendMessage( new EmbedBuilder()
				.setThumbnail( api.getSelfUser().getAvatarUrl() )
				.setFooter("Socials last updated on the " + BotData.LAST_UPDATED)
				.setTitle("C³ Social Media Links")
				.setColor(color)
				.setDescription("Below you can find all our social media links for different platforms - make sure you're folloing us to keep up with the latest!")
				.addField("Instagram", "https://instagram.com/ccubed_dev" , false)
				.addField("Facebook", "https://www.facebook.com/CScouncilsCA", false)
				.addField("Twitter", "https://twitter.com/CScouncilsofCA", false)
				.addField("Discord", "https://discord.ccubed.dev", false)
				.addField("LinkedIn", "https://www.linkedin.com/company/cs-councils-ca", false)
				.addField("Website", " https://ccubed.dev", false)
				.addField("Github", "https://github.com/orgs/CSCouncilsCA", false)
				
			.build() ).complete()
			.pin().complete();
		
	}
	
	public void sendSchools() {
		// Prevents repeated message
		TextChannel c = api.getTextChannelById(BotData.SCHOOLS_CHANNEL_ID);
		deleteMessages(c);
		
		c.sendMessage( new EmbedBuilder()
				.setThumbnail( api.getSelfUser().getAvatarUrl() )
				.setFooter("School list last updated on the " + BotData.LAST_UPDATED)
				.setTitle("C³ Discord School Reaction Roles")
				.setColor(color)
				.setDescription("Below you can find every school that we're in collaboration with listed with an emote. Refer to the legend, and react to the university that you're apart of - show some school spirit!")
				
				// List of all schools, alongside their respective reaction role emote
				.addField("Wilfrid Laurier University", ":regional_indicator_a:" , true)
				.addField("University of Guelph", ":regional_indicator_b:" , true)
				.addField("Laval University", ":regional_indicator_c:" , true)
				.addField("University of Waterloo", ":regional_indicator_d:" , true)
				.addField("Western University", ":regional_indicator_e:" , true)
				.addField("Concordia University", ":regional_indicator_f:" , true)
				.addField("Ryerson University", ":regional_indicator_g:" , true)
				.addField("University of Windsor", ":regional_indicator_h:" , true)
				.addField("Ontario Tech University", ":regional_indicator_i:" , true)
				.addField("University of Toronto", ":regional_indicator_j:" , true)
				.addField("University of Montreal", ":regional_indicator_k:" , true)
				.addField("University of British Columbia", ":regional_indicator_l:" , true)
				.addField("University of Montreal", ":regional_indicator_m:" , true)
				.addField("Carleton University", ":regional_indicator_n:" , true)
				.addField("Queen's University", ":regional_indicator_o:" , true)
				.addField("McGil University", ":regional_indicator_p:" , true)
				
			.build() ).complete()
			.pin().complete();
		
		
	}
	
	public void sendRules() {
		TextChannel c = api.getTextChannelById(BotData.RULES_CHANNEL_ID);
		deleteMessages(c);
			
		c.sendMessage( new EmbedBuilder()
				.setThumbnail( api.getSelfUser().getAvatarUrl() )
				.setFooter("Rules last updated on the " + BotData.LAST_UPDATED)
				.setTitle("C³ Terms of Service + Rules")
				.setColor(color)
				.setDescription("**Note:** Discord in this document refers to the C3 organization’s personal Discord Server. The ‘administrators’ refers to the administrators of this organization, a list is available on the members side panel of the server, with the role of MODERATOR." +
		
				"\n\n**C³ is dedicated to providing a harassment-free** experience for everyone. We do not tolerate harassment of participants in any form." + 
				"This code of conduct applies to all C3 spaces, including public channels, private channels and direct messages, both online and off. Anyone who violates this code of conduct may be sanctioned or expelled from these spaces at the discretion of the administrators." + 
				"The moderators' words are final.If you have a complaint about how a specific situation was handled, please reach out to an administrator via PM or via email."
				)
		.build() ).complete()
		.pin().queueAfter(5, TimeUnit.SECONDS);
		
		c.sendMessage( new EmbedBuilder()
				.setFooter("Rules last updated on the " + BotData.LAST_UPDATED)
				.setTitle("CCubed Terms of Service Cont'd (2)")
				.setColor(color)
				.setDescription("**Harassment Includes:**" +
						"\n**•** Offensive comments related to gender, gender identity and expression, sexual orientation, disability, mental illness, neuro(a)typicality, physical appearance, body size, age, race, national origin, ethnic origin, nationality, immigration status, language, religion or lack thereof, or other identity marker. This includes anti•Indigenous/Nativeness and anti•Blackness." +
						"\n**•** Unwelcome comments regarding a person’s lifestyle choices and practices, including those related to food, health, parenting, relationships, drugs, and employment." +
						"\n**•** Deliberate misgendering or use of “dead” or rejected names." +
						"\n**•** Unwelcome sexual attention." +
						"\n**•** Threats of violence." +
						"\n**•** Incitement of violence towards any individual, including encouraging a person to commit suicide or to engage in self•harm." +
						"\n**•** Deliberate intimidation, including repeatedly asking for assignment answers." +
						"\n**•** Harassing photography or recording, including logging online activity for harassment purposes." +
						"\n**•** Sustained disruption of discussion." +
						"\n**•** Pattern of inappropriate social contact." +
						"\n**•** Continued one•on•one communication after requests to cease." +
						"\n**•** Publication of non•harassing private communication." +
						"\n**•** Continual patronizing behavior targeted at other users." +
						
						"\n⠀\nJokes that resemble the above, such as ‘hipster racism’, still count as harassment even if meant satirically or ironically. " + 
						
						"\n⠀\nC³ prioritizes marginalized people’s safety over privileged people’s comfort. The administrators will not act on complaints regarding:" + 
						"\n**1.** Reverse”-isms, including “reverse racism,” “reverse sexism,” and “cisphobia”" + 
						"\n**2.** Reasonable communication of boundaries, such as “leave me alone,” “go away,” or “I’m not discussing this with you.”" + 
						"\n**3.** Communicating in a “tone” you don’t find congenial" + 
						"\n**4.** Criticizing racist, sexist, cissexist, or otherwise oppressive behavior or assumptions"
						)	
		.build() ).complete()
		.pin().queueAfter(5, TimeUnit.SECONDS);
		
		c.sendMessage( new EmbedBuilder()
				.setFooter("Rules last updated on the " + BotData.LAST_UPDATED)
				.setTitle("CCubed Terms of Service Cont'd (3)")
				.setColor(color)
				.setDescription("**Reporting:**" +
						"\nIf you are being harassed by a member of C3 ‘s discord, notice that someone else is being harassed, or have any other concerns, please contact an admin directly via DM. If the person who is harassing you is on the team, they will recuse themselves from handling your incident. We will respond as promptly as we can." +
						"\n⠀\nThis code of conduct applies to C3 spaces, but if you are being harassed by a member of C3 outside our spaces, we still want to know about it. We will take all good-faith reports of harassment by C3 members, especially the administrators, seriously. This includes harassment outside our spaces and harassment that took place at any point in time. The admin team reserves the right to exclude people from C3 based on their past behavior, including behavior outside C3 spaces and behavior towards people who are not in C3." +
						"\n⠀\nWe will respect confidentiality requests for the purpose of protecting victims of abuse. At our discretion we may approach third parties if we believe action must be taken. We will not name harassment victims without their affirmative consent."
						)
		.build() ).complete()
		.pin().queueAfter(5, TimeUnit.SECONDS);
		
		c.sendMessage( new EmbedBuilder()
				.setFooter("Rules last updated on the " + BotData.TEST_CHANNEL_ID)
				.setTitle("CCubed Terms of Service Cont'd (4)")
				.setColor(color)
				.setDescription("**Consequences:**" +
						"\nParticipants asked to stop any harassing behavior are expected to comply immediately." + 
						"\n⠀\nIf a participant engages in harassing behavior, the administrators may take any action they deem appropriate, up to and including expulsion from all C3 spaces and identification of the participant as a harasser to other C3 members or third parties."
						)
		.build() ).complete()
		.pin().queueAfter(5, TimeUnit.SECONDS);
		
	}
	
	public void sendInvite() {
		// Prevents repeated message
		TextChannel c = api.getTextChannelById(BotData.INVITE_CHANNEL_ID);
		deleteMessages(c);
		
		c.sendMessage( new EmbedBuilder()
				.setThumbnail( api.getSelfUser().getAvatarUrl() )
				.setFooter("Invite link last updated on the " + BotData.LAST_UPDATED)
				.setTitle("C³ Discord Server Invite Link")
				.setColor(color)
				.setDescription("If you invite other people to this Discord, feel free! Just send the person the link below and tell them to click it!\n"
						+ "https://discord.ccubed.dev/")
				
			.build() ).complete()
			.pin().complete();
		
	}
	
	public void sendWelcome() {
		
	}
	
	public void sendInterests() {
		
	}
	
	private void deleteMessages(TextChannel channel) {
		channel.purgeMessages( channel.getIterableHistory().complete() );
		Logger.getGlobal().info("Clearing channel: " + channel.getName() );
		
	}
	
}