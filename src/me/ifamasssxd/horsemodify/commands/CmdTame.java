package me.ifamasssxd.horsemodify.commands;

import me.ifamasssxd.horsemodify.HorseModifier;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

public class CmdTame extends HorseModifierCommand {
	public CmdTame(String name) {
		super(name);
	}

	@Override
	public int getArgsSize() {
		return 0;
	}

	@Override
	public String getPermission() {
		return "horsemodify.modify.tame";
	}

	@Override
	public String getInfo() {
		return "Tames all of your selected horses.";
	}

	@Override
	public String getArg() {
		return "tame";
	}

	@Override
	public void onCommand(Player p, String[] args) {
		if (HorseModifier.horseSelect.containsKey(p.getName())) {
			for (Entity ent : HorseModifier.horseSelect.get(p.getName())) {
				if (ent instanceof Horse) {
					Horse h = (Horse) ent;
					h.setTamed(true);
					h.setOwner(p);
				}
			}
			p.sendMessage(ChatColor.GOLD + "The horse is now tamed!");
		} else {
			p.sendMessage(ChatColor.RED + "You do not have a Horse selected!");
		}
	}

}
