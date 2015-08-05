package me.ifamasssxd.horsemodify.commands;

import me.ifamasssxd.horsemodify.HorseModifier;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Player;

public class CmdSetColor extends HorseModifierCommand {

	public CmdSetColor(String name) {
		super(name);
	}

	@Override
	public int getArgsSize() {
		return 1;
	}

	@Override
	public String getPermission() {
		return "horsemodify.modify.color";
	}

	@Override
	public String getInfo() {
		return "Set the color of the horse.";
	}

	@Override
	public String getArg() {
		return "setcolor <color>";
	}

	@Override
	public void onCommand(Player p, String[] args) {
		try {
			if (HorseModifier.horseSelect.containsKey(p.getName())) {
				Color e = Color.valueOf(args[0].toUpperCase());
				for (Entity ent : HorseModifier.horseSelect.get(p.getName())) {
					if (ent instanceof Horse) {
						Horse h = (Horse) ent;
						h.setColor(e);
					}
				}
				p.sendMessage(ChatColor.GOLD + "Horse " + ChatColor.GREEN + ChatColor.BOLD + "Color " + ChatColor.GOLD + "Modified!");
			} else {
				p.sendMessage(ChatColor.RED + "You do not have a Horse selected!");
			}
		} catch (Exception ei) {
			p.sendMessage(ChatColor.RED + "Valid Colors: " + ChatColor.BOLD + ChatColor.GOLD + "Black, Brown, Chestnut, Creamy, Dark_Brown, Gray, White");
		}
	}

}
