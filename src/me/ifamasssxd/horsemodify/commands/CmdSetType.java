package me.ifamasssxd.horsemodify.commands;

import me.ifamasssxd.horsemodify.HorseModifier;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Player;

public class CmdSetType extends HorseModifierCommand {

	public CmdSetType(String name) {
		super(name);
	}

	@Override
	public int getArgsSize() {
		return 1;
	}

	@Override
	public String getPermission() {
		return "horsemodify.modify.type";
	}

	@Override
	public String getInfo() {
		return "Set the type of the horse.";
	}

	@Override
	public String getArg() {
		return "settype <type>";
	}

	@Override
	public void onCommand(Player p, String[] args) {
		try {
			if (HorseModifier.horseSelect.containsKey(p.getName())) {
				Variant variant = Variant.valueOf(args[0].toUpperCase());
				for (Entity ent : HorseModifier.horseSelect.get(p.getName())) {
					if (ent instanceof Horse) {
						Horse h = (Horse) ent;
						h.setVariant(variant);
					}
				}
				p.sendMessage(ChatColor.GOLD + "Horse Type set!");
			} else {
				p.sendMessage(ChatColor.RED + "You do not have a Horse selected!");
			}
		} catch (Exception exc) {
			p.sendMessage(ChatColor.RED + "That is not a valid " + ChatColor.GREEN + ChatColor.BOLD + "Horse Type" + ChatColor.RED + "!");
			p.sendMessage(ChatColor.GOLD + "Valid Horse Types: " + ChatColor.RED + "DONKEY, MULE, UNDEAD_HORSE, SKELETON_HORSE, MULE, HORSE");
		}
	}
}
