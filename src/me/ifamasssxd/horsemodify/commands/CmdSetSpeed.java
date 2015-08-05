package me.ifamasssxd.horsemodify.commands;

import me.ifamasssxd.horsemodify.HorseModifier;
import net.minecraft.server.v1_7_R4.EntityHorse;
import net.minecraft.server.v1_7_R4.GenericAttributes;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

public class CmdSetSpeed extends HorseModifierCommand {

	public CmdSetSpeed(String name) {
		super(name);
	}

	@Override
	public int getArgsSize() {
		return 1;
	}

	@Override
	public String getPermission() {
		return "horsemodify.modify.speed";
	}

	@Override
	public String getInfo() {
		return "Set the speed of the horse.";
	}

	@Override
	public String getArg() {
		return "setspeed <speed>";
	}

	@Override
	public void onCommand(Player p, String[] args) {
		if (HorseModifier.horseSelect.containsKey(p.getName())) {
			try {
				double speed = Double.valueOf(args[0]);
				for (Entity ent : HorseModifier.horseSelect.get(p.getName())) {
					if (ent instanceof Horse) {
						Horse h = (Horse) ent;
						EntityHorse horse = ((EntityHorse) ((CraftHorse) h).getHandle());
						speed = speed * .1D;
						horse.getAttributeInstance(GenericAttributes.d).setValue(speed);
					}
				}
				p.sendMessage(ChatColor.GOLD + "Horse " + ChatColor.GREEN + ChatColor.BOLD + "Speed " + ChatColor.GOLD + "Modified!");
			} catch (Exception e) {
				p.sendMessage(ChatColor.RED + "That is not a valid " + ChatColor.GREEN + ChatColor.BOLD + "Speed" + ChatColor.RED + "!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "You do not have a Horse selected!");
		}
	}

}
