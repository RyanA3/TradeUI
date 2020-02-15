package me.felnstaren.config;

import org.bukkit.configuration.file.YamlConfiguration;

import me.felnstaren.util.logger.Level;
import me.felnstaren.util.logger.Logger;

public class Options {

	public static void load(YamlConfiguration config) {
		Logger.logger_priority = Level.valueOf(config.getString("logger-priority"));
	}
	
	
	
	public static int trade_request_timeout = 30;
	
}
