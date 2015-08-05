package me.ifamasssxd.horsemodify.commands;

import me.ifamasssxd.horsemodify.HorseModifier;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdSetSaddle extends HorseModifierCommand {
	public CmdSetSaddle(String name) {
		super(name);
	}

	@Override
	public int getArgsSize() {
		return 0;
	}

	@Override
	public String getPermission() {
		return "horsemodify.modify.saddle";
	}

	@Override
	public String getInfo() {
		return "Toggles saddles for all selected horses.";
	}

	@Override
	public String getArg() {
		return "setsaddle";
	}

	@Override
	public void onCommand(Player p, String[] args) {
		if (HorseModifier.horseSelect.containsKey(p.getName())) {
			for (Entity ent : HorseModifier.horseSelect.get(p.getName())) {
				if (ent instanceof Horse) {
					Horse h = (Horse) ent;
					if (h.getInventory().getSaddle() != null) {
						if (h.getInventory().getSaddle().getType().equals(Material.SADDLE)) {
							h.getInventory().setSaddle(new ItemStack(Material.AIR));
						}
					} else {
						h.getInventory().setSaddle(new ItemStack(Material.SADDLE));
					}
				}
			}
			p.sendMessage(ChatColor.GOLD + "Horse Saddle set!");
		}
	}

}
