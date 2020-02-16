package me.felnstaren.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import me.felnstaren.TradeMain;

public class ConfigMan {

	private Plugin main;
	private List<TradeConfig> configs = new ArrayList<TradeConfig>();
	
	public ConfigMan(Plugin plugin) {
		this.main = plugin;
	}
	
	public void init() {
		TradeMain.sendConsoleMessage(ChatColor.GRAY + "Loading config files...");
		if (!main.getDataFolder().exists()) main.getDataFolder().mkdirs(); 
		
		for(TradeConfig config : configs) {
			config.init(main);
		}
	}

	
	
	public void addConfig(String name) {
		TradeConfig config = new TradeConfig(name);
		configs.add(config);
	}
	
	
	
	public TradeConfig getTradeConfig(String name) {
		for(TradeConfig config : configs) {
			if(config.name.equals(name)) return config;
		}
		
		return null;
	}
	
	public FileConfiguration getConfig(String name) {
		for(TradeConfig config : configs) {
			if(config.name.equals(name)) return config.file_config;
		}
		
		return null;
	}
	
	
	
	public void saveConfig(String name) {
		TradeConfig config = getTradeConfig(name);
		if(config == null) return;
		
		config.save();
	}
	
	public void reloadConfig(String name) {
		TradeConfig config = getTradeConfig(name);
		if(config == null) return;
		
		config.reload(main);
	}
	
	public void saveAll() {
		for(TradeConfig config : configs) {
			config.save();
		}
	}
	
	public void reloadAll() {
		for(TradeConfig config : configs) {
			config.reload(main);
		}
	}
}
