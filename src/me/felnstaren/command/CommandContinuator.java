package me.felnstaren.command;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

public abstract class CommandContinuator implements CommandElement, Tabbable {
	
	protected ArrayList<SubCommand> commands;
	protected ArrayList<SubArgument> arguments;
	protected CommandStub stub;
	protected String label;
	
	protected CommandContinuator(CommandStub stub, String label) {
		this.commands = new ArrayList<SubCommand>();
		this.arguments = new ArrayList<SubArgument>();
		this.stub = stub;
		this.label = label;
	}
	
	protected boolean forward(CommandSender sender, String[] args, int current) {
		current++;
		
		if(current >= args.length) 
			return stub(sender, args);
		
		for(SubCommand sub : commands) 
			if(sub.getLabel().equals(args[current])) 
				if(sub.handle(sender, args, current)) return true;
		
		for(SubArgument arg : arguments) 
			if(arg.handle(sender, args, current)) return true;
		
		return false;
	}
	
	protected ArrayList<String> forwardTab(CommandSender sender, String[] args, int current) {
		current++;
		
		ArrayList<String> tabs = new ArrayList<String>();
		
		if(current >= args.length) {
			tabs.add(label);
			return tabs;
		}

		for(SubCommand sub : commands) 
			tabs.addAll(sub.tab(sender, args, current));
		
		for(SubArgument arg : arguments) 
			tabs.addAll(arg.tab(sender, args, current));
			
		return tabs;
	}
	
	protected boolean stub(CommandSender sender, String[] args) {
		return stub.handle(sender, args, args.length - 1);
	}
	
	
	
	public String getLabel() {
		return label;
	}
	
}
