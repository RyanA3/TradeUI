package me.felnstaren.command;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

public abstract class SubCommand extends CommandContinuator {

	protected SubCommand(CommandStub stub, String label) {
		super(stub, label);
	}
	
	
	
	public ArrayList<String> tab(CommandSender sender, String[] args, int current) {
		return forwardTab(sender, args, current);
	}

}
