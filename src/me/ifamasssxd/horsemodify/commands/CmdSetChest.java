package me.ifamasssxd.horsemodify.commands;

import me.ifamasssxd.horsemodify.HorseModifier;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

public class CmdSetChest extends HorseModifierCommand {
	public CmdSetChest(String name) {
		super(name);
	}

	@Override
	public int getArgsSize() {
		return 0;
	}

	@Override
	public String getPermission() {
		return "horsemodify.modify.chest";
	}

	@Override
	public String getInfo() {
		return "Toggles chests for all selected horses.";
	}

	@Override
	public String getArg() {
		return "setchest";
	}

	@Override
	public void onCommand(Player p, String[] args) {
		if (HorseModifier.horseSelect.containsKey(p.getName())) {
			for (Entity ent : HorseModifier.horseSelect.get(p.getName())) {
				if (ent instanceof Horse) {
					Horse h = (Horse) ent;
					if (h.isCarryingChest()) {
						h.setCarryingChest(false);
					} else {
						h.setCarryingChest(true);
					}
				}
			}
			p.sendMessage(ChatColor.GOLD + "Horse Chest set!");
		}
	}

}
