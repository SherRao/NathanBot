package tech.sherrao.discord.ccubed.imgs;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import tech.sherrao.discord.ccubed.Bot;
import tech.sherrao.discord.ccubed.BotData;

public class MessageListener {

	private final Bot bot;
	private Map<String, ArrayList<File>> map;
	private boolean allowPost = true;
	
	public MessageListener(Bot bot) {
		this.bot = bot;
		this.map = new HashMap<>();
		
	}
	
	public void load() {
		/** Nausher */
		map.put("(.*)([N|n][A|a][U|u][S|s][H|h][E|e][R|r]|[N|n][O|o][S|s][H|h])(.*)", new ArrayList<File>( Arrays.asList( new File("nausher/1.png") )));
		
		/** Nathan */
		map.put("(.*)([N|n][A|a][T|t][H|h][A|a][N|n]|[N|n][A|a][T|t][E|e])(.*)", new ArrayList<File>( Arrays.asList( new File("nathan/1.png"), new File("nathan/2.png"), new File("nathan/3.png") )));
		
		/** Johnny */
		map.put("(.*)([J|j][O|o][H|h][N|n][N|n][Y|y])(.*)", new ArrayList<File>( Arrays.asList( new File("johnny/1.png"), new File("johnny/2.png") )));

		/** Zaza */
		map.put("(.*)([Z|z][A|a][Z|z][A|a])(.*)", new ArrayList<File>( Arrays.asList( new File("zaza/1.png"), new File("zaza/2.png") )));
		
		/** Leanne */
		map.put("(.*)([L|l][E|e][A|a][N|n][N|n][E|e])(.*)", new ArrayList<File>( Arrays.asList( new File("leanne/1.png") )));

		/** Jenn */
		map.put("(.*)([J|j][E|e][N|n][N|n])(.*)", new ArrayList<File>( Arrays.asList( new File("jenn/1.png"), new File("jenn/2.png") )));
		
		/** Shamsi */
		map.put("(.*)([S|s][H|h][A|a][M|m][S|s][I|i])(.*)", new ArrayList<File>( Arrays.asList( new File("shamsi/1.png"), new File("shamsi/2.png"), new File("shamsi/3.png") )));

		/** Saqif */
		map.put("(.*)([S|s][A|a][Q|q][I|i][F|f])(.*)", new ArrayList<File>( Arrays.asList( new File("saqif/1.png"), new File("saqif/2.png"), new File("saqif/3.png") )));
		
		/** Danielle */
		map.put("(.*)([D|d][A|a][N|n][I|i][E|e][L|l][L|l][E|e])(.*)", new ArrayList<File>( Arrays.asList( new File("danielle/1.png"), new File("danielle/2.png") )));
		
		/** Tarandeep */
		map.put("(.*)([T|t][A|a][R|r][A|a][N|n])(.*)", new ArrayList<File>( Arrays.asList( new File("taran/1.png"), new File("taran/2.png") )));

		/** Annie */ 
		map.put("(.*)([A|a][N|n][N|n][I|i][E|e])(.*)", new ArrayList<File>( Arrays.asList( new File("annie/1.png") )));
		
	}
	
	@SubscribeEvent
	public void onMessageSend(GuildMessageReceivedEvent event) {
		TextChannel channel = event.getChannel();
		if(channel.getId().equals(BotData.TEST_CHANNEL_ID) && event.getMessage().getContentRaw().toLowerCase().contains("!stop")) {
			channel.sendMessage(":exclamation: Shutting down bot, check the linux screen instance to make sure there isn't an error.").complete();
			bot.stop();
			
		}
			
	}
	
	/**
	@SubscribeEvent
	public void onMessageSend(GuildMessageReceivedEvent event) {
		if(!event.getAuthor().isBot() && event.getChannel().getParent().getId().equals(BotData.STAFF_CATEGORY_ID)) {
			String message = event.getMessage().getContentRaw();
			TextChannel channel = event.getChannel();
			
			for(Iterator<Entry< String, ArrayList<File> >> it = map.entrySet().iterator(); it.hasNext();) {
				Entry<String, ArrayList<File>> entry = it.next();
				String regex = entry.getKey();
			
				if(Pattern.matches(regex, message)) {
					if(allowPost) {
						List<File> images = entry.getValue();
						File image = images.get(RandomUtils.nextInt( images.size() ));
						channel.sendFile(image).complete();
						
						
					}
				}
				
			}

		} else
			return;
		
	}
	
	*/
	
}
