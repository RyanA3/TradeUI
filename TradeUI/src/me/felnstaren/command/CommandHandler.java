package me.felnstaren.command;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {
	private HashMap<String, CommandExecutor> executors = new HashMap<String, CommandExecutor>();
	private FileConfiguration config;
	private FileConfiguration language;
	
	
	public CommandHandler(FileConfiguration config, FileConfiguration language) {
		this.config = config;
		this.language = language;
	}
	
		
	public void addExecutor(String command, CommandExecutor executor) {
		executors.put(command, executor);
	}
	
	public CommandExecutor getExecutor(String command) {
		return executors.get(command);
	}
	
	public boolean exists(String command) {
		return executors.containsKey(command);
	}
	
	
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if(label.equals("trade")) {
    		if(!config.getBoolean("use-commands") && (args.length > 0 && !args[0].equals("reload"))) {
    			sender.sendMessage(ChatColor.RED + language.getString("commands-disabled"));
    			return true;
    		} else if(!sender.hasPermission("trade_ui.trade") && !sender.isOp()) {
    			sender.sendMessage(ChatColor.RED + language.getString("no-permission").replace("[command]", label).replace("[permission]", "trade_ui.trade"));
    			return true;
    		}
    		
    		if(sender instanceof Player) {
    			Player player = (Player) sender;
    			if(player.getGameMode() == GameMode.SPECTATOR) {
    				player.sendMessage(ChatColor.RED + language.getString("no-spectator-mode"));
    				return true;
    			}
    		}
    	}
  
    	
    	if(args.length == 0 && exists(label)) return getExecutor(label).onCommand(sender, command, label, args);
    	else if(args.length == 0) {
    		for(String sub : executors.keySet()) {
    			if(sub.replaceAll(".wild", "").equals(label)) return getExecutor(sub).onCommand(sender, command, label, args);
    		}
    	}
    	
    	for(String sub : executors.keySet()) {
    		if(isSub(sub, args, label, true)) {
    			return getExecutor(sub).onCommand(sender, command, label, args);
    		}
    	}
    	
    	return true;
    }
    
    
    
    private boolean isSub(String sub, String[] etest, String label, boolean cutting) {
    	String[] subs = sub.split("\\.");
    	//Bukkit.broadcastMessage(subs.length + "");
    	
    	String[] test = insert(etest, label, 0);
		
    	//Cuts off any extra args
    	if(test.length > subs.length && cutting) {
    		//Bukkit.broadcastMessage("Cutting test from " + test.length + " to " + subs.length);
    		String[] new_test = new String[subs.length];
    		
    		for(int i = 0; i < subs.length; i++) {
    			new_test[i] = test[i];
    		}
    		
    		test = new_test;
    		//Bukkit.broadcastMessage("Cut test to " + test.length);
    	}
    	
    	//Check if there are any trailing [wild]s
    	if(subs.length > test.length) {
    		//Bukkit.broadcastMessage("Subs is longer than test");
    		for(int i = test.length; i < subs.length; i++) {
    			//Bukkit.broadcastMessage("Checking if pos " + i + " (" + subs[i] + ") is [wild]");
    			if(!subs[i].equals("[wild]")) return false;
    		}
    	}
    	
    	//Compare args
		for(int i = 0; i < subs.length && i < test.length; i++) {
			//Bukkit.broadcastMessage("Comparing " + subs[i] + " to " + test[i]);
			if(subs[i].equals("wild") || subs[i].equals("[wild]") || subs[i].equals(test[i])) continue;
			else return false;
		}
		
		return true;
    }
    
    
    public String[] insert(String[] array, String in, int pos) {
    	String[] new_arr = new String[array.length + 1];
    	
    	//Bukkit.broadcastMessage("Inserting " + in + " into " + Arrays.toString(array) + " at position " + pos);

    	for(int i = 0; i < pos; i++) {
    		new_arr[i] = array[i];
    	}
    	new_arr[pos] = in;
    	for(int i = pos; i < array.length; i++) {
    		new_arr[i + 1] = array[i];
    	}
    	
    	//Bukkit.broadcastMessage("Inserted as " + Arrays.toString(new_arr));
    	
    	return new_arr;
    }
}
