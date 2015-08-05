package me.ifamasssxd.horsemodify.commands;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CmdModifier extends HorseModifierCommand {

	public CmdModifier(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getArgsSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPermission() {
		return "horsemodify.modify";
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return "Gives the player a sadle.";
	}

	@Override
	public String getArg() {
		// TODO Auto-generated method stub
		return "modifier";
	}

	@Override
	public void onCommand(Player p, String[] args) {
		if (!hasModifier(p)) {
			if (p.getInventory().firstEmpty() != -1) {
				ItemStack item = new ItemStack(Material.SADDLE);
				ItemMeta im = item.getItemMeta();
				im.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Horse Modifier");
				im.setLore(Arrays.asList(ChatColor.GRAY + "Right Click to select a horse!"));
				item.setItemMeta(im);
				p.getInventory().addItem(item);
			} else {
				p.sendMessage(ChatColor.RED + "You do not have enough room for the modifier!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "You already have a Horse Modifier. Please use /hm help for more info.");
		}
	}

	public boolean isHorseModifier(ItemStack is) {
		if (is != null) {
			if (is.getType() != Material.AIR) {
				if (is.getType().equals(Material.SADDLE)) {
					if (is.hasItemMeta()) {
						if (is.getItemMeta().getDisplayName() != null) {
							if (is.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD.toString() + ChatColor.BOLD + "Horse Modifier")) {
								return true;
							}
						}
					}
				}
			}
		}

		return false;
	}

	public boolean hasModifier(Player player) {
		if (player.getInventory() != null) {
			for (ItemStack is : player.getInventory()) {
				if (isHorseModifier(is)) {
					return true;
				}
			}
		} else {
			return false;
		}
		return false;
	}
}
