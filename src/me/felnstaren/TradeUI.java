package me.felnstaren;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.felnstaren.command.trade.TradeMaster;
import me.felnstaren.config.Loader;
import me.felnstaren.config.Options;
import me.felnstaren.trade.session.TradeSessionHandler;

public class TradeUI extends JavaPlugin {

	public void onEnable() {
		Options.load(Loader.loadOrDefault("config.yml", "config.yml"));
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(TradeSessionHandler.getInstance(), this);
		
		this.getCommand("trade").setExecutor(new TradeMaster());
	}
	
	public void onDisable() {
		
	}
	
}
