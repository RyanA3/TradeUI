package me.felnstaren.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class MasterCommand extends CommandContinuator implements CommandExecutor {
	
	protected String permission;
	
	protected MasterCommand(CommandStub stub, String permission) {
		super(stub);
		this.permission = permission;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return handle(sender, args, -1);
	}
	
}
