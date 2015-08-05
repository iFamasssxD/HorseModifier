package me.ifamasssxd.horsemodify.commands;

import me.ifamasssxd.horsemodify.HorseModifier;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

public class CmdSetAge extends HorseModifierCommand {

	public CmdSetAge(String name) {
		super(name);
	}

	@Override
	public int getArgsSize() {
		return 1;
	}

	@Override
	public String getPermission() {
		return "horsemodify.modify.age";
	}

	@Override
	public String getInfo() {
		return "Set the age of the horse.";
	}

	@Override
	public String getArg() {
		return "setage <age>";
	}

	@Override
	public void onCommand(Player p, String[] args) {
		try {
			if (HorseModifier.horseSelect.containsKey(p.getName())) {
				for (Entity ent : HorseModifier.horseSelect.get(p.getName())) {
					if (ent instanceof Horse) {
						Horse h = (Horse) ent;
						if (args[0].equalsIgnoreCase("baby")) {
							h.setBaby();
						} else if (args[0].equalsIgnoreCase("adult")) {
							h.setAdult();
						}
					}
				}
				if (args[0].equalsIgnoreCase("baby")) {
					p.sendMessage(ChatColor.GOLD + "Horse Age set!");
				} else if (args[0].equalsIgnoreCase("adult")) {
					p.sendMessage(ChatColor.GOLD + "Horse Age set!");
				} else {
					p.sendMessage(ChatColor.RED + "That is not a valid " + ChatColor.GREEN + ChatColor.BOLD + "Age" + ChatColor.RED + "!");
					p.sendMessage(ChatColor.GOLD + "Valid Horse Ages: " + ChatColor.RED + "Baby/Adult");
				}
			} else {
				p.sendMessage(ChatColor.RED + "You do not have a Horse selected!");
			}
		} catch (Exception exc) {
			p.sendMessage(ChatColor.RED + "That is not a valid " + ChatColor.GREEN + ChatColor.BOLD + "Age" + ChatColor.RED + "!");
			p.sendMessage(ChatColor.GOLD + "Valid Horse Ages:" + ChatColor.RED + "Baby/Adult");
		}
	}

}
