package me.ifamasssxd.horsemodify.commands;

import lombok.Getter;

import org.bukkit.entity.Player;

public abstract class HorseModifierCommand {

	@Getter
	String name;

	public HorseModifierCommand(String name) {
		this.name = name;
		CommandHorseModifier.commands.add(this);
	}

	public abstract int getArgsSize();

	public abstract String getPermission();

	public abstract String getInfo();

	public abstract String getArg();

	public abstract void onCommand(Player p, String[] args);
}
