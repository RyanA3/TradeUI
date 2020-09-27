package me.felnstaren.trade.request;

import org.bukkit.entity.Player;

import me.felnstaren.config.ChatVar;
import me.felnstaren.config.Language;
import me.felnstaren.config.Options;
import me.felnstaren.util.chat.Message;
import me.felnstaren.util.chat.Messenger;

public class TradeRequest {

	protected Player sender;
	protected Player receiver;
	protected int timeout;
	
	public TradeRequest(Player sender, Player receiver, int timeout) {
		this.sender = sender;
		this.receiver = receiver;
		this.timeout = timeout;
	}
	
	public void sendInitialMessage() {
		sender.sendMessage(Language.msg("ifo.sent-request", new ChatVar("[Player]", receiver.getName()), new ChatVar("[Timeout]", timeout + "")));
		Message sender_json = new Message();
		sender_json.addraw("[\"\",{\"text\":\"" + Language.msg("cmd.cancel-button") + "\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/trade cancel\"}}]");
		if(Options.use_commands) Messenger.sendJSON(sender, sender_json.build());
		
		receiver.sendMessage(Language.msg("ifo.request-received", new ChatVar("[Player]", sender.getName()), new ChatVar("[Timeout]", timeout + "")));
		Message receiver_json = new Message();
		receiver_json.addraw("[\"\",{\"text\":\"" + Language.msg("cmd.accept-button") + "\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/trade accept " + sender.getName() + "\"}},{\"text\":\"   \",\"color\":\"gray\"},{\"text\":\"" + Language.msg("cmd.deny-button") + "\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/trade deny " + sender.getName() + "\"}}]");
		if(Options.use_commands) Messenger.sendJSON(receiver, receiver_json.build());
	}
	
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
