package me.felnstaren.config;

import org.bukkit.configuration.file.YamlConfiguration;

import me.felnstaren.util.logger.Level;
import me.felnstaren.util.logger.Logger;

public class Options {

	public static void load(YamlConfiguration config) {
		Logger.log(Level.DEBUG, "Loading general configuration settings");
		Logger.logger_priority = Level.valueOf(config.getString("logger-priority"));
		
		trade_request_timeout = config.getInt("trade-request-timeout");
		trade_max_distance = config.getInt("max-trade-distance");
		
		use_shifting = config.getBoolean("allow-shifting");
		use_commands = config.getBoolean("allow-commands");
		require_trade_permission = config.getBoolean("require-trade-permission");
		cancel_request_on_walk_away = config.getBoolean("cancel-request-on-walk-away");
		allow_trade_self = config.getBoolean("allow-self-trading");
	}
	
	
	
	public static int trade_request_timeout = 20;
	public static int trade_max_distance = -1;
	
	public static boolean use_shifting = true;
	public static boolean use_commands = true;
	public static boolean require_trade_permission = true;
	public static boolean cancel_request_on_walk_away = true;
	public static boolean allow_trade_self = false;
	
}
