package me.felnstaren.command;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public abstract class MasterCommand extends CommandContinuator implements CommandExecutor, TabCompleter {
	
	protected String permission;
	
	protected MasterCommand(CommandStub stub, String label, String permission) {
		super(stub, label);
		this.permission = permission;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return handle(sender, args, -1);
	}
	
	public ArrayList<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return tab(sender, args, -1);
	}
	
}
