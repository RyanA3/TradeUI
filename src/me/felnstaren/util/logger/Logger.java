package me.felnstaren.util.logger;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

import me.felnstaren.config.Loader;
import me.felnstaren.util.chat.Messenger;

public class Logger {

	private static ConsoleCommandSender console = Loader.plugin.getServer().getConsoleSender();
	private static String plugin_prefix = ChatColor.AQUA + "[TradeUI]";
	
	public static Level logger_priority = Level.DEBUG;

	public static void log(Level level, String message) {
		if(!level.hasPriority(logger_priority)) return;
		console.sendMessage(Messenger.prefix(Messenger.color(message), plugin_prefix + level.color + "." + level.toString() + " >>   "));
	}

}
