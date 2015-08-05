package me.ifamasssxd.horsemodify.commands;

import me.ifamasssxd.horsemodify.HorseModifier;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

public class CmdSetJump extends HorseModifierCommand {

	public CmdSetJump(String name) {
		super(name);
	}

	@Override
	public int getArgsSize() {
		return 1;
	}

	@Override
	public String getPermission() {
		return "horsemodify.modify.jump";
	}

	@Override
	public String getInfo() {
		return "Set the jump height of the horse.";
	}

	@Override
	public String getArg() {
		return "setjump <strength>";
	}

	@Override
	public void onCommand(Player p, String[] args) {
		if (HorseModifier.horseSelect.containsKey(p.getName())) {
			try {
				double jump = Double.valueOf(args[0]);
				for (Entity id : HorseModifier.horseSelect.get(p.getName())) {
					Entity ent = id;
					Horse h = (Horse) ent;
					h.setJumpStrength(jump);
					// horseSelect.remove(player.getName());
				}
				p.sendMessage(ChatColor.GOLD + "Horse " + ChatColor.GREEN + ChatColor.BOLD + "Jump " + ChatColor.GOLD + "Modified!");
			} catch (Exception e) {
				p.sendMessage(ChatColor.RED + "That is not a valid " + ChatColor.GREEN + ChatColor.BOLD + "Jump Strength" + ChatColor.RED + "!");
			}
		} else {
			p.sendMessage(ChatColor.RED + "You do not have a Horse selected!");
		}
	}

}
