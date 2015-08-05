package me.ifamasssxd.horsemodify;

import java.util.HashMap;
import java.util.List;

import me.ifamasssxd.horsemodify.commands.CommandHorseModifier;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class HorseModifier extends JavaPlugin {
	public static HashMap<String, List<Horse>> horseSelect = new HashMap<>();
	public static boolean isHorseEggEnabled = false;
	public static ItemStack req_item = null;
	public static double capture_chance = 100D;
	private static HorseModifier instance;

	@SuppressWarnings("deprecation")
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		getCommand("horsemodify").setExecutor(new CommandHorseModifier());
		capture_chance = getConfig().getDouble("HorseEgg.capture_chance", 100);
		isHorseEggEnabled = getConfig().getBoolean("HorseEgg.Enabled", false);
		req_item = new ItemStack(Material.getMaterial(getConfig().getInt("HorseEgg.Item")), getConfig().getInt("HorseEgg.ItemAmount"));
		getServer().getPluginManager().registerEvents(new HorseListener(), this);
	}

	public static HorseModifier get() {
		return instance;
	}

	public void sendConsoleMessage(String s) {
		getServer().getConsoleSender().sendMessage(s);
	}

	public boolean isHorseModifier(ItemStack is) {
		if (is != null) {
			if (is.getType() == Material.SADDLE && is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
				if (is.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD.toString() + ChatColor.BOLD + "Horse Modifier")) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean hasModifier(Player player) {
		for (ItemStack is : player.getInventory()) {
			if (isHorseModifier(is)) {
				return true;
			}
		}
		return false;
	}

}
