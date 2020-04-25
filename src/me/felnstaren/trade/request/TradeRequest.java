package me.felnstaren.trade.request;

import org.bukkit.entity.Player;

import me.felnstaren.config.ChatVar;
import me.felnstaren.config.Language;

public abstract class TradeRequest {

	protected Player sender;
	protected Player receiver;
	protected int timeout;
	
	public TradeRequest(Player sender, Player receiver, int timeout) {
		this.sender = sender;
		this.receiver = receiver;
		this.timeout = timeout;
	}
	
	public abstract void sendInitialMessage();
	
	public void sendCancelMessage() {
		sender.sendMessage(Language.msg("ifo.cancel-request", new ChatVar("[Player]", receiver.getName())));
		receiver.sendMessage(Language.msg("ifo.request-cancelled", new ChatVar("[Player]", sender.getName())));
	}
	
	public void sendAcceptMessage() {
		sender.sendMessage(Language.msg("ifo.request-accepted", new ChatVar("[Player]", receiver.getName())));
		receiver.sendMessage(Language.msg("ifo.accept-request", new ChatVar("[Player]", sender.getName())));
	}
	
	public void sendDenyMessage() {
		sender.sendMessage(Language.msg("ifo.request-denied", new ChatVar("[Player]", receiver.getName())));
		receiver.sendMessage(Language.msg("ifo.deny-request", new ChatVar("[Player]", sender.getName())));
	}
	
	public void sendTimeoutMessage() {
		sender.sendMessage(Language.msg("ifo.request-timed-out", new ChatVar("[Player]", receiver.getName())));
		receiver.sendMessage(Language.msg("ifo.time-out-request", new ChatVar("[Player]", sender.getName())));
	}
	
	public void sendWalkAwayMessage(boolean sender_walked) {
		if(sender_walked) {
			sender.sendMessage(Language.msg("ifo.walk-away-request", new ChatVar("[Player]", receiver.getName())));
			receiver.sendMessage(Language.msg("ifo.request-walked-away", new ChatVar("[Player]", sender.getName())));
		} else {
			sender.sendMessage(Language.msg("ifo.request-walked-away", new ChatVar("[Player]", receiver.getName())));
			receiver.sendMessage(Language.msg("ifo.walk-away-request", new ChatVar("[Player]", sender.getName())));
		}
	}
	
	
	
	public Player getSender() {
		return sender;
	}
	
	public Player getReceiver() {
		return receiver;
	}
	
}
