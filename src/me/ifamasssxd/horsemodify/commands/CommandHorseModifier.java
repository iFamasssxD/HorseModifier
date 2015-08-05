package me.ifamasssxd.horsemodify.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHorseModifier implements CommandExecutor {

	public static List<HorseModifierCommand> commands = new ArrayList<>();

	public CommandHorseModifier() {
		// Register the commands.
		new CmdHelp("help");
		new CmdModifier("modifier");
		new CmdSetAge("setage");
		new CmdSetColor("setcolor");
		new CmdSetHealth("sethealth");
		new CmdSetJump("setjump");
		new CmdSetSpeed("setspeed");
		new CmdSetStyle("setstyle");
		new CmdSetType("settype");
		new CmdRemove("remove");
		new CmdTame("tame");
		new CmdSetChest("setchest");
		new CmdStats("stats");
		new CmdSetSaddle("setsaddle");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
			return true;
		}
		Player p = (Player) sender;
		if (args.length == 0) {
			// No args at all.. so send them some help.
			sendHelp(p);
			return true;
		}
		HorseModifierCommand hmc = getCommand(args);
		if (hmc == null) {
			p.sendMessage(ChatColor.RED + "Invalid command.");
			p.sendMessage(ChatColor.GRAY + "Please use /hm help for more info.");
			return true;
		}
		if ((args.length - 1) != hmc.getArgsSize()) {
			// Not the right amount of arguments..
			p.sendMessage(ChatColor.RED + "Invalid amount of arguments!");
			p.sendMessage(ChatColor.GRAY + "Arguments required: " + hmc.getArg());
			return true;
		}
		if (hmc.getPermission() != null && !p.hasPermission(hmc.getPermission()) && !p.hasMetadata("horsemodify.modify.all")) {
			p.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
			return true;
		}
		// Re-contruct the list.
		String[] cmdArgs = new String[args.length - 1];
		for (int i = 1; i < args.length; i++) {
			cmdArgs[i - 1] = args[i];
		}
		hmc.onCommand(p, cmdArgs);
		return true;
	}

	public HorseModifierCommand getCommand(String[] args) {
		for (HorseModifierCommand cmd : commands) {
			if (cmd.getName().equalsIgnoreCase(args[0])) {
				return cmd;
			}
		}
		return null;
	}

	public static void sendHelp(Player p) {
		p.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "       Horse Modifier Commands");
		commands.forEach((cmd) -> {
			p.sendMessage(ChatColor.RED + "/hm " + cmd.getArg());
			p.sendMessage(ChatColor.YELLOW + cmd.getInfo());
		});
	}
}
