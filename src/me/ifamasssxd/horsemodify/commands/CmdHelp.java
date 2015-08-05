package me.ifamasssxd.horsemodify.commands;

import org.bukkit.entity.Player;

public class CmdHelp extends HorseModifierCommand {

	public CmdHelp(String name) {
		super(name);
	}

	public int getArgsSize() {
		return 0;
	}

	public String getPermission() {
		return null;
	}

	public String getInfo() {
		// TODO Auto-generated method stub
		return "Shows the help menu for the HorseModifier plugin.";
	}

	public String getArg() {
		return "help";
	}

	public void onCommand(Player p, String[] args) {
		CommandHorseModifier.sendHelp(p);
	}

}
