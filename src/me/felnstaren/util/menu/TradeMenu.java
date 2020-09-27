package me.felnstaren.util.menu;

import org.bukkit.Material;

import me.felnstaren.config.Language;
import me.felnstaren.util.item.ItemBuild;

public class TradeMenu extends Menu {

	public TradeMenu(String title) {
		super(title, 54);
		setItem(new ItemBuild(Material.RED_TERRACOTTA).setName(Language.msg("cmd.accept-button")).setTags("element", "accept_button").get(), 3, 5);
		fillItems(new ItemBuild(Material.RED_STAINED_GLASS_PANE).setTags("element").get(), 4, 0, 1, 6);
	}

}
