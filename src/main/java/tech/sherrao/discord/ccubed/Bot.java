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
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import tech.sherrao.discord.ccubed.imgs.MessageListener;

public class Bot {

	private JDA api;
	private MessageListener messages;
	private ScheduledExecutorService executor;
	private Color color;
	
	public Bot() {
		try {
			api = JDABuilder.createDefault(BotData.TOKEN)
					.setMemberCachePolicy(MemberCachePolicy.ALL)
					.enableIntents(GatewayIntent.GUILD_MEMBERS)
					.setEventManager(new AnnotatedEventManager())
					.build();
			api.awaitReady();

		} catch (LoginException | InterruptedException e) {
			e.printStackTrace();

		}
		
		messages = new MessageListener(this);
		//messages.load();
		
		executor = Executors.newScheduledThreadPool(1);
		api.getPresence().setActivity(Activity.watching("over all " + api.getGuildById(BotData.SERVER_ID).getMemberCount() + " of you! 🙃"));
		api.setEventManager(new AnnotatedEventManager());
		api.addEventListener(messages);
		
		color = new Color(51,186,197);
		
		//sendSocials();
		//sendSchools();
		//sendPronouns();
		//sendRules();
		//sendInvite();
		//sendWelcome();
		
		executor.scheduleWithFixedDelay(() -> {
			api.getGuildById(BotData.SERVER_ID).loadMembers().onSuccess( a -> {
				api.getTextChannelById(BotData.TEST_CHANNEL_ID).sendMessage(":white_check_mark: Reloaded members!").complete().delete().queueAfter(1, TimeUnit.MINUTES);
				
			} );
			
			api.getPresence().setActivity(Activity.watching("over all " + api.getGuildById(BotData.SERVER_ID).getMemberCount() + " of you! 🙃"));
			api.getTextChannelById(BotData.TEST_CHANNEL_ID).sendMessage(":white_check_mark: Updated activity members!").complete().delete().queueAfter(1, TimeUnit.MINUTES);
			
		}, 0, 1, TimeUnit.MINUTES);
		
	}
	
	public void stop() {
		TextChannel c = api.getTextChannelById(BotData.TEST_CHANNEL_ID);
		try {
			c.sendMessage(":question: Trying to stop the ScheduledExecutorService for 10 seconds").complete();
			executor.awaitTermination(10, TimeUnit.SECONDS);
			c.sendMessage(":white_check_mark: ScheduledExecutorService finished").complete();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			c.sendMessage(":warning: Attempt finished").complete();

		}
		
		c.sendMessage(":question: Sending shutdown to JDA").complete();
		api.shutdownNow();
		//c.sendMessage(":white_check_mark: JDA shutdown").complete();
		Runtime.getRuntime().exit(0);
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
		Message c = api.getTextChannelById(BotData.SCHOOLS_CHANNEL_ID).getHistory().getMessageById(BotData.RR_SCHOOLS_MESSAGE_ID);
		c.editMessage( new EmbedBuilder()
				.setThumbnail( api.getSelfUser().getAvatarUrl() )
				.setFooter("School list last updated on the " + BotData.LAST_UPDATED)
				.setTitle("C³ Discord School Reaction Roles")
				.setColor(color)
				.setDescription("Below you can find every school that we're in collaboration with listed with an emote. Refer to the legend, and react to the university that you're apart of - show some school spirit!")
				
				// List of all schools, alongside their respective reaction role emote
				.addField("Wilfrid Laurier University", ":regional_indicator_a:" , true)
				.addField("University of Guelph", ":regional_indicator_b:" , true)
				.addField("Université Laval", ":regional_indicator_c:" , true)
				.addField("University of Waterloo", ":regional_indicator_d:" , true)
				.addField("Western University", ":regional_indicator_e:" , true)
				.addField("Concordia University", ":regional_indicator_f:" , true)
				.addField("Ryerson University", ":regional_indicator_g:" , true)
				.addField("University of Windsor", ":regional_indicator_h:" , true)
				.addField("University of Toronto", ":regional_indicator_i:" , true)
				.addField("Université de Montréal", ":regional_indicator_j:" , true)
				.addField("University of British Columbia", ":regional_indicator_k:" , true)
				.addField("Carleton University", ":regional_indicator_l:" , true)
				.addField("Queen's University", ":regional_indicator_m:" , true)
				.addField("McGill University", ":regional_indicator_n:" , true)
				.addField("University of Calgary", ":regional_indicator_o:" , true)
				.addField("University of Manitoba", ":regional_indicator_p:" , true)

			.build() ).complete();
	}
	
	
	public void sendPronouns() {
		TextChannel c= api.getTextChannelById(BotData.PRONOUNS_CHANNEL_ID);
		deleteMessages(c);

		c.sendMessage( new EmbedBuilder()
				.setThumbnail( api.getSelfUser().getAvatarUrl() )
				.setFooter("Pronoun list last updated on the " + BotData.LAST_UPDATED)
				.setTitle("C³ Discord Pronoun Reaction Roles")
				.setColor(color)
				.setDescription("Below you can find pronouns that you can assign to yourself! Refer to the legend, and react to the pronoun you identify with!")
				
				// List of all pronouns, alongside their respective reaction role emote
				.addField("He/Him", ":male_sign:" , true)
				.addField("She/Her", ":female_sign:" , true)
				.addField("They/Them", ":transgender_symbol:" , true)
				.addField("Other", ":peace:" , true)

			.build() ).complete();
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
				.setDescription("**Note:** Discord in this document refers to the C³ organization’s personal Discord Server. The ‘administrators’ refers to the administrators of this organization, a list is available on the members side panel of the server, with the role of 'Staff'." +
		
				"\n\n**C³ is dedicated to providing a harassment-free** experience for everyone. We do not tolerate harassment of participants in any form." + 
				"This code of conduct applies to all C³ spaces, including public channels, private channels and direct messages, both online and off. Anyone who violates this code of conduct may be sanctioned or expelled from these spaces at the discretion of the administrators." + 
				"The moderators' words are final.If you have a complaint about how a specific situation was handled, please reach out to an administrator via PM or via email."
				)
		.build() ).complete()
		.pin().queueAfter(5, TimeUnit.SECONDS);
		
		c.sendMessage( new EmbedBuilder()
				.setFooter("Rules last updated on the " + BotData.LAST_UPDATED)
				.setTitle("C³ Terms of Service Cont'd (2)")
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
				.setTitle("C³ Terms of Service Cont'd (3)")
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
				.setTitle("C³ Terms of Service Cont'd (4)")
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
				
				.addField("Welcome!", "Hey, welcome to the server! This is the official server for C³ (also known as CCubed). C³ is a national organization of computing students aiming to create and foster an inclusive community for all students in computing-related fields across Canada. Our goal is to improve inter-school collaboration between computing student organizations, provide a support network, and connect students nationwide through our events. Thank you for joining our Discord server and being a part of our national community!", false)
				.addBlankField(false)
				.addField("What is Discord?", "If you're new to Discord, that’s perfectly cool! You'll find that Discord is incredibly useful for conversing in a more individual manner within a group than most other platforms. To navigate the server's full list of chat channels, check out the panel on the left. To check out a list of server members, check out the right panel. You can discuss things and meet new people in the chats with everyone on the server in the different text channels that are organized by topic. Discord also makes it easy to embed code and share images. To learn more about Discord, visit the official Beginner’s Guide to Discord: https://support.discord.com/hc/en-us/articles/360045138571-Beginner-s-Guide-to-Discord", false)
				.addBlankField(false)
				.addField("Rules", "If you haven't yet already, please make yourself familiar with all the rules that can be found in the #rules channel! These rules are important in our mission to foster a safe, inclusive community, so just remember to be respectful and you’re golden! If someone/something does bother you in/about the server, please contact a staff member and we’ll do our best to help you out. All staff members will have the “staff” role in their profile on the server.", false)
				.addBlankField(false)
				.addField("General Text Channels", "You can find all our fun text channels under the 'General Text' section in the left panel! From sharing memes, talking about coops, getting ideas/critiques for a resume, or just talking about music, we hope you’ll be able to meet some new people, have some fun, and perhaps learn something new in these channels.", false)
				.addBlankField(false)
				.addField("Represent Your School", "Show some school spirit by adding your institution to your server profile! You can do so by navigating to the Role Assignment section then the #schools channel and react to the bot’s message to select your school. If your school isn't on the list, feel free to contact someone from the C³ team and we'll get it sorted!", false)
				.addBlankField(false)
				.addField("Battle Your Friends With Your Own Piggy!", "Piggy is on this server. What is Piggy? It’s a cool RPG-style minigame that can be played directly in the server! You can learn more about Piggy here: https://piggy.gg/", false)
				.addBlankField(false)
				.addField("Thank You for Joining Our Community", "We're hoping we can grow this server and make it a fun, safe, and inclusive space for everyone to meet, connect, and hangout with fellow computing students across Canada. We want this server to benefit everyone in our community, regardless of age, gender, alma mater, background, or anything else. Thank you for being a part of our community!", false)

			.build() ).complete()
			.pin().complete();
				
	}
	
	public void sendInterests() {
		
	}
	
	private void deleteMessages(TextChannel channel) {
		channel.purgeMessages( channel.getIterableHistory().complete() );
		Logger.getGlobal().info("Clearing channel: " + channel.getName() );
		
	}
	
	public JDA getApi() {
		return this.api;
		
	}
	
	/**
	 * 
	 * Triggers CarlBot to add reaction roles for the schools embed - doesn't work.
	 * @unused
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
	
}