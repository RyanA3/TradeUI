package me.felnstaren.command;

public abstract class SubCommand extends CommandContinuator {

	protected String label;
	
	protected SubCommand(CommandStub stub, String label) {
		super(stub);
		this.label = label;
	}
	
	public String getSubLabel() {
		return label;
	}

}
