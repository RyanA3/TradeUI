package me.felnstaren;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.felnstaren.command.trade.TradeMaster;
import me.felnstaren.config.Language;
import me.felnstaren.config.Loader;
import me.felnstaren.config.Options;
import me.felnstaren.listener.TradeRequestListener;
import me.felnstaren.listener.TradeWalkAwayListener;
import me.felnstaren.metrics.Metrics;
import me.felnstaren.trade.request.TradeRequestHandler;
import me.felnstaren.trade.session.TradeSessionHandler;
import me.felnstaren.util.logger.Level;
import me.felnstaren.util.logger.Logger;

public class TradeUI extends JavaPlugin {

	public void onEnable() {
		Options.load(Loader.loadOrDefault("config.yml", "config.yml"));
		Language.load(Loader.loadOrDefault("lang.yml", "lang.yml"));

		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(TradeSessionHandler.getInstance(), this);
		pm.registerEvents(new TradeRequestListener(), this);
		pm.registerEvents(new TradeWalkAwayListener(), this);
		
		this.getCommand("trade").setExecutor(new TradeMaster());
		
		try {
			Metrics metrics = new Metrics(this, 7321);
			if(metrics.isEnabled()) Logger.log(Level.DEBUG, "Enabled plugin metrics");
		} catch(Exception e) {
			Logger.log(Level.WARNING, "Failed to init plugin metrics");
		}
	}
	
	public void onDisable() {
		if(TradeSessionHandler.hasInstance()) TradeSessionHandler.getInstance().closeAll();
		if(TradeRequestHandler.hasInstance()) TradeRequestHandler.getInstance().close();
	}
	
}
