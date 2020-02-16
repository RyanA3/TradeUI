package me.felnstaren.config;

import org.bukkit.configuration.file.YamlConfiguration;

import me.felnstaren.util.chat.Messenger;

public class Language {
	
	private static YamlConfiguration config;
	
	public static void load(YamlConfiguration loadof) {
		config = loadof;
	}
	
	
	
	public static String msg(String key) {
		return Messenger.color(config.getString(key));
	}
	
	public static String msg(String key, ChatVar... vars) {
		String message = msg(key);
		
		for(ChatVar var : vars) 
			message = var.fill(message);
		
		return message;
	}

}
