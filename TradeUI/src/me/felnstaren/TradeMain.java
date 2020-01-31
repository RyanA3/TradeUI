package me.felnstaren;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.felnstaren.command.CommandHandler;
import me.felnstaren.command.TradeAcceptCommand;
import me.felnstaren.command.TradeCancelCommand;
import me.felnstaren.command.TradeCommand;
import me.felnstaren.command.TradeDenyCommand;
import me.felnstaren.command.TradeReloadCommand;
import me.felnstaren.command.TradeTabCompleter;
import me.felnstaren.config.ConfigMan;
import me.felnstaren.event.TradeEvent;
import me.felnstaren.trade.request.TradeRequestHandler;
import me.felnstaren.trade.session.TradeSessionHandler;

public class TradeMain extends JavaPlugin {
	
	/**
	 * 
	 * TradeUI is a plugin developed independently by Felnstaren
	 * This plugin and the code within is liscensed by a CCBYNC creative commons license
	 * https://creativecommons.org/licenses/by-sa/4.0/
	 * 
	 */
	
	
	private TradeSessionHandler tshandler;
	private TradeRequestHandler trhandler;
	
	public void onEnable() {
		ConfigMan config_man = new ConfigMan(this);
		config_man.addConfig("config.yml");
		config_man.addConfig("language.yml");
		
		config_man.init();
		
		
		FileConfiguration config = config_man.getConfig("config.yml");
		FileConfiguration language = config_man.getConfig("language.yml");
		
		this.tshandler = new TradeSessionHandler(language);
		this.trhandler = new TradeRequestHandler(tshandler, config, language);
		
		CommandHandler cmd = new CommandHandler(config, language);
		cmd.addExecutor("trade." + language.getString("command.sub-commands.accept") + ".[wild]", new TradeAcceptCommand(trhandler, tshandler, config, language));
		cmd.addExecutor("trade." + language.getString("command.sub-commands.deny") + ".[wild]", new TradeDenyCommand(trhandler, language));
		cmd.addExecutor("trade." + language.getString("command.sub-commands.cancel"), new TradeCancelCommand(trhandler, language));
		cmd.addExecutor("trade." + language.getString("command.sub-commands.reload"), new TradeReloadCommand(this, language));
		cmd.addExecutor("trade.wild", new TradeCommand(trhandler, config, language));
		
		this.getCommand("trade").setExecutor(cmd);
		this.getCommand("trade").setTabCompleter(new TradeTabCompleter(config, language));
		this.getCommand("trade").setUsage("/trade <player>");
		
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new TradeEvent(trhandler, tshandler, config, language), this);
		pm.registerEvents(tshandler, this);
	}
	
	public void onDisable() {
		tshandler.closeAllSessions();
		trhandler.closeAllRequests();
	}
	
	
	
	public static void sendConsoleMessage(String msg) {
		Bukkit.getPluginManager().getPlugin("TradeUI").getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[TradeUI]" + ChatColor.YELLOW + " >> " + msg);
	}
}
