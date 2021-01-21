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
import net.dv8tion.jda.api.entities.Message;
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
		//api.addEventListener(messages);
		
		color = new Color(51,186,197);
		
		//sendSocials();
		//sendSchools();
		//sendRules();
		//sendInvite();
		//sendWelcome();
		
		executor.scheduleWithFixedDelay(() -> {
			api.getPresence().setActivity(Activity.watching("over all " + api.getGuildById(BotData.SERVER_ID).getMemberCount() + " of you! 🙃"));
			
		}, 5, 5, TimeUnit.MINUTES);
		
	}
	
	/**
	 * 
	 * Sends the social media embed message.
	 * 
	 */
	public void sendSocials() {
		// Prevents repeated message
		TextChannel c = api.getTextChannelById(BotData.SOCIAL_CHANNEL_ID);
		deleteMessages(c);
		
		c.sendMessage( new EmbedBuilder()
				.setThumbnail( api.getSelfUser().getAvatarUrl() )
				.setFooter("Socials last updated on the " + BotData.LAST_UPDATED)
				.setTitle("C³ Social Media Links")
				.setColor(color)
				.setDescription("Below you can find all our social media links for different platforms - make sure you're following us to keep up with the latest!")
				.addField("Instagram", "https://instagram.com/ccubed_dev" , false)
				.addField("Facebook", "https://www.facebook.com/ccubed.dev", false)
				.addField("Twitter", "https://twitter.com/ccubed_dev", false)
				.addField("Discord", "https://discord.ccubed.dev", false)
				.addField("LinkedIn", "https://www.linkedin.com/company/ccubed-dev", false)
				.addField("Website", " https://ccubed.dev", false)
				.addField("Github", "https://github.com/orgs/ccubed-dev", false)
				
			.build() ).complete()
			.pin().complete();
		
	}
	
	/**
	 * 
	 * Edits the schools embed RR message.
	 * 
	 */
	public void sendSchools() {
		// Prevents repeated message
		Message c = api.getTextChannelById(BotData.SCHOOLS_CHANNEL_ID).getHistory().getMessageById(BotData.RR_MESSAGE_ID);
		c.editMessage( new EmbedBuilder()
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
				.addField("University of Toronto", ":regional_indicator_i:" , true)
				.addField("University of Montreal", ":regional_indicator_j:" , true)
				.addField("University of British Columbia", ":regional_indicator_k:" , true)
				.addField("Carleton University", ":regional_indicator_l:" , true)
				.addField("Queen's University", ":regional_indicator_m:" , true)
				.addField("McGill University", ":regional_indicator_n:" , true)
				
			.build() ).complete();
				
	}
	
	/**
	 * 
	 * Triggers CarlBot to add reaction roles for the schools embed - doesn't work.
	 *
	 * 
	 */
	public void addRR(String messageId) {
		TextChannel c = api.getTextChannelById(BotData.TEST_CHANNEL_ID);
		c.sendMessage("!rr add 781272962073624616 " + messageId + ":regional_indicator_a: @WLU").complete();
		c.sendMessage("!rr add 781272962073624616 " + messageId + ":regional_indicator_b: @UGuelph").complete();
		c.sendMessage("!rr add 781272962073624616 " + messageId + ":regional_indicator_c: @ULaval").complete();
		c.sendMessage("!rr add #schools" + messageId + ":regional_indicator_d: @UWaterloo").complete();
		c.sendMessage("!rr add #schools" + messageId + ":regional_indicator_e: @UWestern").complete();
		c.sendMessage("!rr add #schools" + messageId + ":regional_indicator_f: @Concordia").complete();
		c.sendMessage("!rr add #schools" + messageId + ":regional_indicator_g: @RyersonU").complete();
		c.sendMessage("!rr add #schools" + messageId + ":regional_indicator_h: @Windsor").complete();
		c.sendMessage("!rr add #schools" + messageId + ":regional_indicator_i: @UofT").complete();
		c.sendMessage("!rr add #schools" + messageId + ":regional_indicator_j: @UdeM").complete();
		c.sendMessage("!rr add #schools" + messageId + ":regional_indicator_k: @UBC").complete();
		c.sendMessage("!rr add #schools" + messageId + ":regional_indicator_l: @CarletonU").complete();
		c.sendMessage("!rr add #schools" + messageId + ":regional_indicator_m: @Queen's U").complete();
		c.sendMessage("!rr add #schools" + messageId + ":regional_indicator_n: @McGill").complete();
		
	}
	
	/**
	 * 
	 * Sends the rules embed message.
	 * 
	 */
	public void sendRules() {
		TextChannel c = api.getTextChannelById(BotData.RULES_CHANNEL_ID);
		deleteMessages(c);
			
		c.sendMessage( new EmbedBuilder()
				.setThumbnail( api.getSelfUser().getAvatarUrl() )
				.setFooter("Rules last updated on the " + BotData.LAST_UPDATED)
				.setTitle("C³ Terms of Service + Rules")
				.setColor(color)
				.setDescription("**Note:** Discord in this document refers to the C³ organization’s personal Discord Server. The ‘administrators’ refers to the administrators of this organization, a list is available on the members side panel of the server, with the role of MODERATOR." +
		
				"\n\n**C³ is dedicated to providing a harassment-free** experience for everyone. We do not tolerate harassment of participants in any form." + 
				"This code of conduct applies to all C³ spaces, including public channels, private channels and direct messages, both online and off. Anyone who violates this code of conduct may be sanctioned or expelled from these spaces at the discretion of the administrators." + 
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
						"\nIf you are being harassed by a member of C³ ‘s discord, notice that someone else is being harassed, or have any other concerns, please contact an admin directly via DM. If the person who is harassing you is on the team, they will recuse themselves from handling your incident. We will respond as promptly as we can." +
						"\n⠀\nThis code of conduct applies to C³ spaces, but if you are being harassed by a member of C³ outside our spaces, we still want to know about it. We will take all good-faith reports of harassment by C³ members, especially the administrators, seriously. This includes harassment outside our spaces and harassment that took place at any point in time. The admin team reserves the right to exclude people from C³ based on their past behavior, including behavior outside C³ spaces and behavior towards people who are not in C³." +
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
						"\n⠀\nIf a participant engages in harassing behavior, the administrators may take any action they deem appropriate, up to and including expulsion from all C³ spaces and identification of the participant as a harasser to other C³ members or third parties."
						)
		.build() ).complete()
		.pin().queueAfter(5, TimeUnit.SECONDS);
		
	}
	
	/**
	 * 
	 * Sends the Discord invite embed message.
	 * 
	 */
	public void sendInvite() {
		// Prevents repeated message
		TextChannel c = api.getTextChannelById(BotData.INVITE_CHANNEL_ID);
		deleteMessages(c);
		
		c.sendMessage( new EmbedBuilder()
				.setThumbnail( api.getSelfUser().getAvatarUrl() )
				.setFooter("Invite link last updated on the " + BotData.LAST_UPDATED)
				.setTitle("C³ Discord Server Invite Link")
				.setColor(color)
				.setDescription("If you invite other people to this Discord, feel free! Just send the person the link below and tell them to click it!\nhttps://discord.ccubed.dev/")
				
			.build() ).complete()
			.pin().complete();
		
	}
	
	public void sendWelcome() {
		TextChannel c = api.getTextChannelById(BotData.WELCOME_CHANNEL_ID);
		deleteMessages(c);
		
		c.sendMessage( new EmbedBuilder()
				.setThumbnail( api.getSelfUser().getAvatarUrl() )
				.setFooter("Message last updated on the " + BotData.LAST_UPDATED)
				.setTitle("C³ - Computing Councils of Canada")
				.setColor(color)
				
				.addField("Welcome!", "Hey, welcome to the server! This is the official server for the C³ organisation. We are a national organization of computing students aiming to create and foster the Canadian Computer Science community. Our goal is to improve collaboration between computing student organizations by hosting and collaborating on events for all Canadian CS students!", false)
				.addBlankField(false)
				.addField("What is Discord?", "If you're new to Discord, cool! You'll find that Discord is incredibly useful for conversing in a more individual manner within a group than most other platforms. You can then navigate the server's list of chat channels on the left and the list of server members is on the right and discuss things in the chat with everyone else. Discord also makes it easy to embed code and share images.", false)
				.addBlankField(false)
				.addField("Rules", "If you haven't already, we'd highly encourage that you make yourself familiar with the rules that can be found in the #rules channel! If someone/something is bothering you in/about the server, feel free to contact a staff member!", false)
				.addBlankField(false)
				.addField("General Channels", "We have a lot of channels under the 'General Text' section of the Discord - you can share memes, talk about coops, get ideas/critiques for a resume, or just talk about music!", false)
				.addBlankField(false)
				.addField("Schools Channel", "We have a channel where you can select what school you're from - it's a great way to show some school spirit! If your school isn't on the list, feel free to contact someone from the C³ team and we'll get it sorted!", false)
				.addBlankField(false)
				.addField("Piggy Battles", "We also have a cool RPG-style minigame in the server! We have Piggy on the server! For more info, make sure you check your DMs - you should've received a DM from the Piggy bot.", false)
				.addBlankField(false)
				.addField("So What Now?", "We're hoping we can grow this Discord and make it a fun and safe place for everyone. We want to allow this server to benefit anyone - regardles of your year, alma matter, or anything else!", false)

			.build() ).complete()
			.pin().complete();
				
	}
	
	public void sendInterests() {
		
	}
	
	private void deleteMessages(TextChannel channel) {
		channel.purgeMessages( channel.getIterableHistory().complete() );
		Logger.getGlobal().info("Clearing channel: " + channel.getName() );
		
	}
	
}