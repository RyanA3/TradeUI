package me.felnstaren.command;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

public abstract class CommandContinuator implements CommandElement {
	
	protected ArrayList<SubCommand> commands;
	protected ArrayList<SubArgument> arguments;
	protected CommandStub stub;
	
	protected CommandContinuator(CommandStub stub) {
		this.commands = new ArrayList<SubCommand>();
		this.arguments = new ArrayList<SubArgument>();
		this.stub = stub;
	}
	
	protected boolean forward(CommandSender sender, String[] args, int current) {
		current++;
		
		if(current >= args.length) {
			stub(sender, args);
			return true;
		}
		
		for(SubCommand sub : commands) 
			if(sub.getSubLabel().equals(args[current])) 
				if(sub.handle(sender, args, current)) return true;
		
		for(SubArgument arg : arguments) 
			if(arg.handle(sender, args, current)) return true;
		
		return false;
	}
	
	protected boolean stub(CommandSender sender, String[] args) {
		return stub.handle(sender, args, args.length - 1);
	}
	
}
