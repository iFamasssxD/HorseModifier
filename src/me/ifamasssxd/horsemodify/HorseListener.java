package me.ifamasssxd.horsemodify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_7_R4.EntityHorse;
import net.minecraft.server.v1_7_R4.GenericAttributes;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHorse;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HorseListener implements Listener {
	Random random = new Random();

	public void changeEgg(ItemStack is, Player p, String change) {
		if (p.getItemInHand() != null && is.getType() != Material.AIR) {
			ItemMeta im = is.getItemMeta();
			if (is.getItemMeta().hasLore()) {
				List<String> lore = im.getLore();
				lore.add(change);
			} else {
				List<String> newLore = Collections.singletonList(change);
				im.setLore(newLore);
			}
			is.setItemMeta(im);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntity(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if ((isHorseModifier(p.getItemInHand()) && (event.getAction().equals(Action.LEFT_CLICK_AIR)) || event.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
			if (HorseModifier.horseSelect.containsKey(p.getName())) {
				if (HorseModifier.horseSelect.get(p.getName()).size() > 1) {
					p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + HorseModifier.horseSelect.get(p.getName()).size() + ChatColor.RED
							+ " horses removed!");
				} else {
					p.sendMessage(ChatColor.RED + "Horse removed!");
				}
				HorseModifier.horseSelect.remove(p.getName());
			}
		}
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && p.getItemInHand() != null) {
			/* Ensures that its atleast a horse egg. */
			if (p.getItemInHand().getType().equals(Material.MONSTER_EGG) && p.getItemInHand().getDurability() == (short) 100) {
				ItemStack is = p.getItemInHand();
				if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
					event.setCancelled(true);
					double max_health = Double.parseDouble(ChatColor.stripColor(is.getItemMeta().getLore().get(0).split(": ")[1]));
					Color color = Color.valueOf(ChatColor.stripColor(is.getItemMeta().getLore().get(1).split(": ")[1]));
					Style style = Style.valueOf(ChatColor.stripColor(is.getItemMeta().getLore().get(2).split(": ")[1]));
					double speed = Double.valueOf(ChatColor.stripColor(is.getItemMeta().getLore().get(3).split(": ")[1]));
					double jump = Double.valueOf(ChatColor.stripColor(is.getItemMeta().getLore().get(4).split(": ")[1]));
					createHorse(event.getClickedBlock().getLocation().add(0, 1, 0), is.getItemMeta().hasDisplayName() ? is.getItemMeta().getDisplayName() : "",
							max_health, jump, speed, color, style);
					if (is.getAmount() > 1) {
						is.setAmount(is.getAmount() - 1);
						p.setItemInHand(is);
					} else {
						p.getInventory().removeItem(is);
					}
					p.updateInventory();
				}
			}
		}
	}

	public ItemStack getEggItem(Horse h) {
		ItemStack is = new ItemStack(Material.MONSTER_EGG, 1);
		is.setDurability((short) 100);
		List<String> lore = new ArrayList<String>();
		ItemMeta im = is.getItemMeta();
		if (h.getCustomName() != null && !h.getCustomName().isEmpty()) {
			im.setDisplayName(h.getCustomName());
		}
		EntityHorse horse = ((EntityHorse) ((CraftHorse) h).getHandle());
		double speed = horse.getAttributeInstance(GenericAttributes.d).getValue();
		lore.add(ChatColor.YELLOW + "Max Health: " + ChatColor.RED + (int) h.getMaxHealth());
		lore.add(ChatColor.YELLOW + "Color: " + ChatColor.RED + h.getColor().toString());
		lore.add(ChatColor.YELLOW + "Style: " + ChatColor.RED + h.getStyle().toString());
		lore.add(ChatColor.YELLOW + "Speed: " + ChatColor.RED + speed * 10);
		lore.add(ChatColor.YELLOW + "Jump: " + ChatColor.RED + h.getJumpStrength());
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		if (isHorseModifier(player.getItemInHand())) {
			if (!player.hasPermission("horsemodify.modify.modifier")) {
				player.sendMessage(ChatColor.RED + "You do not have permission to use the Horse Modifier!");
				return;
			}
			if (event.getRightClicked() != null) {
				if (event.getRightClicked() instanceof Horse) {
					event.setCancelled(true);
					Horse h = (Horse) event.getRightClicked();
					EntityHorse horse = ((EntityHorse) ((CraftHorse) h).getHandle());
					if (HorseModifier.horseSelect.containsKey(player.getName())) {
						List<Horse> ent = HorseModifier.horseSelect.get(player.getName());
						if (ent.contains(event.getRightClicked())) {
							ent.remove(event.getRightClicked());
							player.sendMessage(ChatColor.RED + "Horse removed!");
						} else if (!ent.contains(event.getRightClicked())) {
							ent.add((Horse) event.getRightClicked());
							player.sendMessage(ChatColor.GOLD + "Horse added!");
						}
						HorseModifier.horseSelect.put(player.getName(), ent);
					} else {
						List<Horse> ent1 = new ArrayList<>();
						ent1.add((Horse) event.getRightClicked());
						HorseModifier.horseSelect.put(player.getName(), ent1);
						player.sendMessage(ChatColor.GOLD + "Horse added!");
					}
					if (player.hasMetadata("horse_stats")) {
						player.sendMessage(ChatColor.GOLD + "------" + ChatColor.BOLD + "Current Stats" + ChatColor.GOLD + "------");
						player.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + "Jump: " + ChatColor.GOLD + (h.getJumpStrength()));
						player.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + "Speed: " + ChatColor.GOLD
								+ (horse.getAttributeInstance(GenericAttributes.d).getValue() * 10));
						player.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + "Max Health: " + ChatColor.GOLD + Math.round((h.getMaxHealth() / 2))
								+ ChatColor.RED + ChatColor.BOLD + " ❤");
						player.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + "Has Chest: " + ChatColor.GOLD
								+ String.valueOf(h.isCarryingChest()).toUpperCase());
						player.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + "Is Tamed: " + ChatColor.GOLD
								+ String.valueOf(h.isTamed()).toUpperCase());
					}
				} else {
					player.sendMessage(ChatColor.RED + "You must be looking at a Horse!");
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEggHit(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Egg && event.getEntity() instanceof Horse) {
			if (((Egg) event.getDamager()).getShooter() instanceof Player) {
				Player p_shooter = (Player) ((Egg) event.getDamager()).getShooter();
				Horse h = (Horse) event.getEntity();
				if (HorseModifier.isHorseEggEnabled) {
					if (p_shooter.getInventory().containsAtLeast(new ItemStack(HorseModifier.req_item.getType()), HorseModifier.req_item.getAmount())) {
						if (p_shooter.hasPermission("horsemodify.pickup")) {
							if (random.nextDouble() * 100 <= HorseModifier.capture_chance) {
								p_shooter.getInventory().removeItem(HorseModifier.req_item);
								p_shooter.playSound(p_shooter.getLocation(), Sound.LEVEL_UP, 1, 1.5F);
								event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), getEggItem((Horse) event.getEntity()));
								h.setHealth(0);
								p_shooter.updateInventory();
							} else {
								p_shooter.playSound(p_shooter.getLocation(), Sound.ANVIL_LAND, 1, 1.4f);
								p_shooter.sendMessage(ChatColor.RED + "Horse capture " + ChatColor.DARK_RED + ChatColor.BOLD.toString() + "FAILED.");
								h.setHealth(0);
							}
						}
					}
				}
			}
		}
	}

	public Horse createHorse(Location spawn, String name, double max_health, double jump, double speed, Color color, Style style) {
		Horse horse = (Horse) spawn.getWorld().spawnEntity(spawn, EntityType.HORSE);
		horse.setMaxHealth(max_health);
		horse.setJumpStrength(jump);
		if (!name.isEmpty()) {
			horse.setCustomName(name);
			horse.setCustomNameVisible(true);
		}
		EntityHorse h = ((EntityHorse) ((CraftHorse) horse).getHandle());
		h.getAttributeInstance(GenericAttributes.d).setValue(speed / 10);
		horse.setColor(color);
		horse.setStyle(style);
		return horse;
	}

	@EventHandler
	public void onVehicleEnter(VehicleEnterEvent event) {
		if (event.getEntered() instanceof Player) {
			Player player = (Player) event.getEntered();
			Entity entered = event.getVehicle();
			if (HorseModifier.horseSelect.containsKey(player.getName()) && isHorseModifier(player)) {
				if (HorseModifier.horseSelect.get(player.getName()).equals(entered)) {
					event.setCancelled(true);
				}
			}
		}

	}

	public boolean isHorseModifier(Player player) {
		if (player.getItemInHand().getType().equals(Material.SADDLE)) {
			if (player.getItemInHand().hasItemMeta() && player.getItemInHand().getItemMeta().hasDisplayName()) {
				if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD.toString() + ChatColor.BOLD + "Horse Modifier")) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isHorseModifier(ItemStack is) {
		if (is != null) {
			if (is.getType().equals(Material.SADDLE) && is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
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
