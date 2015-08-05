package me.ifamasssxd.horsemodify.commands;

import me.ifamasssxd.horsemodify.HorseModifier;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CmdRemove extends HorseModifierCommand {

	public CmdRemove(String name) {
		super(name);
	}

	@Override
	public int getArgsSize() {
		return 0;
	}

	@Override
	public String getPermission() {
		return "horsemodify.modify.remove";
	}

	@Override
	public String getInfo() {
		return "Removes all of your horses from your selection list.";
	}

	@Override
	public String getArg() {
		return "remove";
	}

	@Override
	public void onCommand(Player p, String[] args) {
		if (HorseModifier.horseSelect.containsKey(p.getName())) {
			HorseModifier.horseSelect.remove(p.getName());
			p.sendMessage(ChatColor.RED + "Horses removed!");
		} else {
			p.sendMessage(ChatColor.RED + "You do not have any horses selected!");
		}
	}

}
