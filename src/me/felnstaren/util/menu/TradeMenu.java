package me.felnstaren.util.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.felnstaren.util.item.ItemBuild;

public class TradeMenu extends Menu {

	public TradeMenu(Player player, String other_player) {
		super(player, "YOU       >>>   " + other_player, 54);
		setItem(new ItemBuild(Material.BARRIER).setName("&cTest").get(), 0, 0);
		setItem(new ItemBuild(Material.RED_TERRACOTTA).setName("&cAccept").get(), 3, 5);
		fillItems(new ItemStack(Material.RED_STAINED_GLASS_PANE), 4, 0, 1, 6);
	}

}
