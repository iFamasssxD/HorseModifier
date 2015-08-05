package me.ifamasssxd.horsemodify.commands;

import me.ifamasssxd.horsemodify.HorseModifier;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

public class CmdSetHealth extends HorseModifierCommand {

	public CmdSetHealth(String name) {
		super(name);
	}

	@Override
	public int getArgsSize() {
		return 1;
	}

	@Override
	public String getPermission() {
		return "horsemodify.modify.health";
	}

	@Override
	public String getInfo() {
		return "Set the health of the horse.";
	}

	@Override
	public String getArg() {
		return "sethealth <health>";
	}

	@Override
	public void onCommand(Player p, String[] args) {
		if (HorseModifier.horseSelect.containsKey(p.getName())) {
			try {
				double health = Double.valueOf(args[0]);
				for (Entity ent : HorseModifier.horseSelect.get(p.getName())) {
					if (ent instanceof Horse) {
						Horse h = (Horse) ent;
						h.setMaxHealth(health * 2);
						h.setHealth(h.getMaxHealth());
					}
				}
				p.sendMessage(ChatColor.GOLD + "Horse Health set!");
			} catch (Exception e) {
				p.sendMessage(ChatColor.RED + "That is not a valid " + ChatColor.GREEN + ChatColor.BOLD + "Health" + ChatColor.RED + "!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "You do not have a Horse selected!");
		}
	}

}
