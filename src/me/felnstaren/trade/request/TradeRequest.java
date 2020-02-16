package me.felnstaren.trade.request;

import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.felnstaren.config.ChatVar;
import me.felnstaren.config.Language;
import me.felnstaren.config.Options;
import net.minecraft.server.v1_15_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_15_R1.PacketPlayOutChat;

public class TradeRequest {

	private Player sender;
	private Player receiver;
	private int display_timeout;
	
	public TradeRequest(Player sender, Player receiver) {
		this.sender = sender;
		this.receiver = receiver;
		this.display_timeout = Options.trade_request_timeout;
	}
	
	
	
	public Player getSender() {
		return sender;
	}
	
	public Player getReceiver() {
		return receiver;
	}
	
	
	
	public void sendInitialMessage() {
		sender.sendMessage(Language.msg("ifo.sent-request", new ChatVar("[Player]", receiver.getName()), new ChatVar("[Timeout]", display_timeout + "")));
		if(Options.use_commands) {
			String sjson = "[\"\",{\"text\":\"" + Language.msg("cmd.cancel-button") + "\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/trade cancel\"}}]";
			PacketPlayOutChat rpacket = new PacketPlayOutChat(ChatSerializer.b(sjson));
			((CraftPlayer) sender).getHandle().playerConnection.sendPacket(rpacket);
		}
		
		receiver.sendMessage(Language.msg("ifo.request-received", new ChatVar("[Player]", sender.getName()), new ChatVar("[Timeout]", display_timeout + "")));
		if(Options.use_commands) {
			String rjson = "[\"\",{\"text\":\"" + Language.msg("cmd.accept-button") + "\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/trade accept " + sender.getName() + "\"}},{\"text\":\"   \",\"color\":\"gray\"},{\"text\":\"" + Language.msg("cmd.deny-button") + "\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/trade deny " + sender.getName() + "\"}}]";
			PacketPlayOutChat spacket = new PacketPlayOutChat(ChatSerializer.b(rjson));
			((CraftPlayer) receiver).getHandle().playerConnection.sendPacket(spacket);
		}
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
	
}
