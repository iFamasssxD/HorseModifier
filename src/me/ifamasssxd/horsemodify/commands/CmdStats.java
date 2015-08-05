package me.ifamasssxd.horsemodify.commands;

import me.ifamasssxd.horsemodify.HorseModifier;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class CmdStats extends HorseModifierCommand {
	public CmdStats(String name) {
		super(name);
	}

	@Override
	public int getArgsSize() {
		return 0;
	}

	@Override
	public String getPermission() {
		return "horsemodify.modify.stats";
	}

	@Override
	public String getInfo() {
		return "Toggles stats when clicking horses with the modifier..";
	}

	@Override
	public String getArg() {
		return "stats";
	}

	@Override
	public void onCommand(Player p, String[] args) {
		if (p.hasMetadata("horse_stats")) {
			p.removeMetadata("horse_stats", HorseModifier.get());
			p.sendMessage(ChatColor.YELLOW + "Horse stats " + ChatColor.RED + "Disabled.");
		} else {
			p.setMetadata("horse_stats", new FixedMetadataValue(HorseModifier.get(), "horse_stats"));
			p.sendMessage(ChatColor.YELLOW + "Horse stats " + ChatColor.GREEN + "Enabled.");
		}
	}

}
