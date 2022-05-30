package com.al3x.staff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Random;

public class StaffModeListener implements Listener {

    String prefix = ChatColor.GRAY + "[" + ChatColor.DARK_RED + "S" + ChatColor.GRAY + "]";

    @EventHandler
    public void click(PlayerInteractEvent e) {

        Player plr = e.getPlayer();

        if (StaffMode.getStaffmodeList().contains(plr.getUniqueId()) && e.getAction() == Action.RIGHT_CLICK_AIR) {

            // THRU
            if (plr.getItemInHand().getType() == Material.COMPASS && plr.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_AQUA + "⋙ " + ChatColor.AQUA + "Phase" + ChatColor.DARK_AQUA + " ⋘")) {
                plr.performCommand("thru");
            }

            // ONLINE STAFF
            if (plr.getItemInHand().getType() == Material.SKULL_ITEM && plr.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_RED + "⋙ " + ChatColor.RED + "Online Staff" + ChatColor.DARK_RED + " ⋘")) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    plr.sendMessage(ChatColor.BLACK.toString() + ChatColor.STRIKETHROUGH + "--------------------------------------");
                    if (players.hasPermission("ratiomc.staff")) {
                        plr.sendMessage(players.getDisplayName());
                    }
                    plr.sendMessage(ChatColor.BLACK.toString() + ChatColor.STRIKETHROUGH + "--------------------------------------");
                }
            }

            // RANDOM TP
            if (plr.getItemInHand().getType() == Material.NETHER_STAR && plr.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_PURPLE + "⋙ " + ChatColor.LIGHT_PURPLE + "Random Teleport" + ChatColor.DARK_PURPLE + " ⋘")) {
                ArrayList<Player> playersList = new ArrayList<>();
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (!players.hasPermission("ratiomc.staff")) {
                        playersList.add(players);
                    }
                }
                if (!(playersList.size() == 0)) {
                    int random = new Random().nextInt(playersList.size());
                    plr.teleport(playersList.get(random));
                } else {
                    plr.sendMessage(ChatColor.RED + "There are no non-staff online.");
                }
            }

            // EXIT
            if (plr.getItemInHand().getType() == Material.BARRIER && plr.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "⋙ " + ChatColor.DARK_RED + "Exit Staffmode" + ChatColor.RED + " ⋘")) {
                plr.performCommand("staffmode");
            }

            // VANISH
            if (plr.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_GREEN + "⋙ " + ChatColor.GREEN + "Vanish Current: ON" + ChatColor.DARK_GREEN + " ⋘")) {
                plr.performCommand("vanish");
            } else if (plr.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_RED + "⋙ " + ChatColor.RED + "Vanish Current: OFF" + ChatColor.DARK_RED + " ⋘")) {
                // UNVANISH
                plr.performCommand("vanish");
            }
        }
    }

    @EventHandler
    public void interact(PlayerInteractEntityEvent e) {

        Player plr = e.getPlayer();

        // INSPECT INV
        if (plr.getItemInHand().getType() == Material.BOOK && plr.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "⋙ " + ChatColor.YELLOW + "Inspect Inventory" + ChatColor.GOLD + " ⋘")) {
            if (e.getRightClicked() instanceof Player) {
                Player target = (Player) e.getRightClicked();
                plr.performCommand("invsee " + target.getDisplayName());
            }
        }

        // FREEZE
        if (plr.getItemInHand().getType() == Material.ICE && plr.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_GRAY + "⋙ " + ChatColor.GRAY + "Freeze" + ChatColor.DARK_GRAY + " ⋘")) {
            if (e.getRightClicked() instanceof Player) {
                Player target = (Player) e.getRightClicked();
                plr.performCommand("freeze " + target.getDisplayName());
            }
        }
    }

    public static void updateInv(Player plr) {
        plr.updateInventory();
    }

}
