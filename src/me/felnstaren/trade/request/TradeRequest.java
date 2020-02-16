package me.felnstaren.trade.request;

import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.felnstaren.config.Options;
import me.felnstaren.util.chat.Messenger;
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
		sender.sendMessage(Messenger.color("&aYou sent a trade request to &7" + receiver.getDisplayName() + "&a, they have &7" + display_timeout + " &aseconds to respond!"));
		if(Options.use_commands) {
			String sjson = "[\"\",{\"text\":\"[\",\"color\":\"gray\"},{\"text\":\"Cancel\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/trade cancel\"}},{\"text\":\"]\",\"color\":\"gray\"}]";
			PacketPlayOutChat rpacket = new PacketPlayOutChat(ChatSerializer.b(sjson));
			((CraftPlayer) sender).getHandle().playerConnection.sendPacket(rpacket);
		}
		
		receiver.sendMessage(Messenger.color("&7" + sender.getDisplayName() + " &asent you a trade request, you have &7" + display_timeout + " &aseconds to respond!"));
		if(Options.use_commands) {
			String rjson = "[\"\",{\"text\":\"[\",\"color\":\"gray\"},{\"text\":\"Accept\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/trade accept " + sender.getName() + "\"}},{\"text\":\"] [\",\"color\":\"gray\"},{\"text\":\"Deny\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/trade deny " + sender.getName() + "\"}},{\"text\":\"]\",\"color\":\"gray\"}]";
			PacketPlayOutChat spacket = new PacketPlayOutChat(ChatSerializer.b(rjson));
			((CraftPlayer) receiver).getHandle().playerConnection.sendPacket(spacket);
		}
	}
	
	public void sendCancelMessage() {
		sender.sendMessage(Messenger.color("&aYou cancelled your trade request to &7" + receiver.getDisplayName() + "&a!"));
		receiver.sendMessage(Messenger.color("&7" + sender.getDisplayName() + " &acancelled their trade request to you!"));
	}
	
	public void sendAcceptMessage() {
		sender.sendMessage(Messenger.color("&7" + receiver.getDisplayName() + " &aaccepted your trade request!"));
		receiver.sendMessage(Messenger.color("&aYou accepted &7" + sender.getDisplayName() + "&a's trade request!"));
	}
	
	public void sendDenyMessage() {
		sender.sendMessage(Messenger.color("&7" + receiver.getDisplayName() + " &adenied your trade request!"));
		receiver.sendMessage(Messenger.color("&aYou denied &7" + sender.getDisplayName() + "&a's trade request!"));
	}
	
	public void sendTimeoutMessage() {
		sender.sendMessage(Messenger.color("&aYour trade request with &7" + receiver.getDisplayName() + " &atimed out!"));
		receiver.sendMessage(Messenger.color("&7" + sender.getDisplayName() + "&a's trade request timed out!"));
	}
	
}
