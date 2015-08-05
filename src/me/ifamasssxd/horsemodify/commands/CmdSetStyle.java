package me.ifamasssxd.horsemodify.commands;

import me.ifamasssxd.horsemodify.HorseModifier;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Player;

public class CmdSetStyle extends HorseModifierCommand {

	public CmdSetStyle(String name) {
		super(name);
	}

	@Override
	public int getArgsSize() {
		return 1;
	}

	@Override
	public String getPermission() {
		return "horsemodify.modify.style";
	}

	@Override
	public String getInfo() {
		return "Set the style of the horse.";
	}

	@Override
	public String getArg() {
		return "setstyle <style>";
	}

	@Override
	public void onCommand(Player p, String[] args) {
		try {
			if (HorseModifier.horseSelect.containsKey(p.getName())) {
				Style style = Style.valueOf(args[0].toUpperCase());
				for (Entity ent : HorseModifier.horseSelect.get(p.getName())) {
					if (ent instanceof Horse) {
						Horse h = (Horse) ent;
						h.setStyle(style);
					}
				}
				p.sendMessage(ChatColor.GOLD + "Horse Style set!");
			} else {
				p.sendMessage(ChatColor.RED + "You do not have a Horse selected!");
			}
		} catch (Exception exc) {
			p.sendMessage(ChatColor.RED + "That is not a valid " + ChatColor.GREEN + ChatColor.BOLD + "Horse Style" + ChatColor.RED + "!");
			p.sendMessage(ChatColor.GOLD + "Valid Horse Styles: " + ChatColor.RED + "BLACK_DOTS, WHITE, WHITE_DOTS, WHITEFIELD, NONE");
		}
	}
}
