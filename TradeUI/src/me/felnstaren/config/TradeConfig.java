package me.felnstaren.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import me.felnstaren.TradeMain;

public class TradeConfig {
	public FileConfiguration file_config;
	public File file;
	public String name;

    public TradeConfig(String name) {
        this.name = name;
    }
    
    
    
    public void init(Plugin main) {
    	TradeMain.sendConsoleMessage(ChatColor.GRAY + "Loading the " + name + " file...");
		file = new File(main.getDataFolder(), name);
		
		boolean strm_null = false;

		if(!file.exists()) {
			try {
				TradeMain.sendConsoleMessage(ChatColor.GRAY + name + " does not exist, creating now...");
				file.createNewFile();
				
				InputStream initial_stream = TradeConfig.class.getResourceAsStream("resources/" + name);
				if(initial_stream == null) strm_null = true;
				byte[] buffer = new byte[initial_stream.available()];
				initial_stream.read(buffer);
				initial_stream.close();
				
				OutputStream out_stream = new FileOutputStream(file);
				out_stream.write(buffer);
				out_stream.close();
			} catch (Exception e) {
				e.printStackTrace();
				TradeMain.sendConsoleMessage(ChatColor.RED + "An error has occured while creating the " + name + " file!");
				if(strm_null) TradeMain.sendConsoleMessage(ChatColor.YELLOW + "DO NOT RELOAD THE SERVER IF THIS PLUGIN HAS NOT GENERATED IT'S CONFIG FILES YET!!!!!! Stop your server, delete the TradeUI folder, and start it again to properly generate the config files.");
			}
		}
		
		file_config = YamlConfiguration.loadConfiguration(file);
		TradeMain.sendConsoleMessage(ChatColor.GREEN + "Successfully loaded the " + name + " file...");
    }
    
    public void reload(Plugin main) {
    	this.file = new File(main.getDataFolder(), name);
    	this.file_config = YamlConfiguration.loadConfiguration(file);
		TradeMain.sendConsoleMessage(ChatColor.GREEN + "Successfully reloaded the " + name + " file...");
    }

    public void save() {
    	try {
			file_config.save(file);
			TradeMain.sendConsoleMessage(ChatColor.GREEN + "Successfully saved the " + name + " file...");
		} catch (Exception e) {
			TradeMain.sendConsoleMessage(ChatColor.RED + "An error has occured while saving the " + name + " file!");
		}
    }
}
