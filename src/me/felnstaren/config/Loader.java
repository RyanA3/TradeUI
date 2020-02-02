package me.felnstaren.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import me.felnstaren.TradeUI;
import me.felnstaren.util.logger.Level;
import me.felnstaren.util.logger.Logger;

public class Loader {
	
	public static Plugin plugin = TradeUI.getPlugin(TradeUI.class);
	
	
	
	public static YamlConfiguration loadOrDefault(String name, String defalt) {
		dirs();
		
		File file = new File(plugin.getDataFolder(), name);
		if(!file.exists()) {
			try { 
				file.createNewFile(); 
				Logger.log(Level.DEBUG, "Created file; " + file.getPath());
			} 
			catch (IOException e) { 
				e.printStackTrace(); 
				Logger.log(Level.SEVERE, "A fatal error occured while creating this file; " + file.getPath());
				return null;
			}
			copy(file, defalt);
		}
		
		return YamlConfiguration.loadConfiguration(file);
	}
	
	public static File copy(File copy, String original) {
		try {
			if(Loader.class.getResourceAsStream("resources/" + original) != null) {
				InputStream initial_stream = Loader.class.getResourceAsStream("resources/" + original);
				byte[] buffer = new byte[initial_stream.available()];
				initial_stream.read(buffer);
				initial_stream.close();
				
				OutputStream out_stream = new FileOutputStream(copy);
				out_stream.write(buffer);
				out_stream.close();
				
				Logger.log(Level.DEBUG, "Copied file; " + copy.getPath() + " from plugin resource file; " + original);
			}
		} catch (Exception e) { 
			e.printStackTrace(); 
			Logger.log(Level.SEVERE, "An error occured while copying to this file; " + copy.getPath());
			return null;
		}
		
		return copy;
	}
	
	public static YamlConfiguration load(String path) {
		dirs();
		Logger.log(Level.DEBUG, "Loading configuration file; " + path);
		return YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), path));
	}
	
	public static void save(YamlConfiguration config, String name) {
		File file = new File(plugin.getDataFolder(), name);
		save(config, file);
	}
	
	public static void save(YamlConfiguration config, File file) {
		try { 
			config.save(file); 
			Logger.log(Level.DEBUG, "Saved file; " + file.getPath());
		} 
		catch (IOException e) { 
			e.printStackTrace(); 
			Logger.log(Level.WARNING, "An error occured saving this file; " + file.getPath());
		}
	}
	
	
	
	private static void dirs() {
		Logger.log(Level.DEBUG, "Marking non-existant directories");
		
		File data_folder = plugin.getDataFolder();
		if (!data_folder.exists()) data_folder.mkdirs(); 
	}

}
