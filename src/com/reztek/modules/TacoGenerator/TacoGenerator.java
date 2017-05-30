package com.reztek.modules.TacoGenerator;

import java.awt.Color;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import com.reztek.Base.CommandModule;
import com.reztek.Utils.BotUtils;
import com.reztek.Utils.ConfigReader;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class TacoGenerator extends CommandModule {

	public static final String PLUGIN_ID               = "TACOGENERATOR";
	public static final String PLUGIN_VER              = "1.2";
	
	private static final String GOOGLE_API_KEY         = ConfigReader.GetConfigReader().getOrCreateConfigString("GOOGLE_API_KEY", "Insert Google API Key Here");
	private static final String GOOGLE_API_CX          = ConfigReader.GetConfigReader().getOrCreateConfigString("GOOGLE_API_CX", "Insert search engine CX Here");
	
	private static final String GOOGLE_CUSTOM_SEARCH   = "https://www.googleapis.com/customsearch/v1?key=" + GOOGLE_API_KEY +
			"&cx=" + GOOGLE_API_CX + "&filter=1&searchType=image&q=";
	
	public TacoGenerator() {
		super(PLUGIN_ID);
		setModuleNameAndAuthor("Taco Generator", "ChaseHQ85");
		setVersion(PLUGIN_VER);
		addCommand("taco");
	}

	@Override
	public void processCommand(String command, String args, MessageReceivedEvent mre) {
		if (command.equals("taco")) showTaco(mre.getChannel());
	}
	
	protected void showTaco(MessageChannel mc) {
		int startIndex = new Random().nextInt(90-1) + 1;
		JSONArray tacoArray = new JSONObject(BotUtils.GetJSONStringGet(GOOGLE_CUSTOM_SEARCH+"taco&start=" + String.valueOf(startIndex), null)).getJSONArray("items");
		String tacoLink = tacoArray.getJSONObject((new Random().nextInt(tacoArray.length()))).getString("link");
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(Color.YELLOW);
		eb.setImage(tacoLink);
		eb.setDescription("**Random Taco Generator Accessed**");
		mc.sendMessage(eb.build()).queue();
	}

}
