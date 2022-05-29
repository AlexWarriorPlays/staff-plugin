package com.al3x.staff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Vanish implements CommandExecutor {

    String prefix = ChatColor.GRAY + "[" + ChatColor.DARK_RED + "S" + ChatColor.GRAY + "]";

    private static List<UUID> vanished = new ArrayList<UUID>();

    public static List<UUID> getVanishList() {
        return vanished;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player plr = (Player) sender;

            if (sender.hasPermission("ratiomc.vanish")) {

                if (vanished.contains(((Player) sender).getUniqueId())) {

                    vanished.remove(((Player) sender).getUniqueId());

                    for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                        players.showPlayer((Player) sender);

                        if (players.hasPermission("ratiomc.vanish")) {
                            players.sendMessage(prefix + ChatColor.RED + " " + ((Player) sender).getDisplayName() + " just unvanished");
                        }
                    }

                    if (StaffMode.getStaffmodeList().contains(plr.getUniqueId())) {
                        ItemStack vanish = new ItemStack(Material.INK_SACK, 1, (short) 8);
                        ItemMeta vanishmeta = vanish.getItemMeta();
                        vanishmeta.setDisplayName(ChatColor.DARK_RED + "⋙ " + ChatColor.RED + "Vanish Current: OFF" + ChatColor.DARK_RED + " ⋘");
                        vanish.setItemMeta(vanishmeta);

                        plr.getInventory().setItem(8, vanish);
                    }

                } else {

                    vanished.add(((Player) sender).getUniqueId());

                    for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                        if (!players.hasPermission("ratiomc.vanish")) {
                            players.hidePlayer((Player) sender);
                        } else {
                            players.sendMessage(prefix + ChatColor.RED + " " + ((Player) sender).getDisplayName() + " just went into vanish");
                        }
                    }

                    if (StaffMode.getStaffmodeList().contains(plr.getUniqueId())) {
                        ItemStack vanish = new ItemStack(Material.INK_SACK, 1, (short) 10);
                        ItemMeta vanishmeta = vanish.getItemMeta();
                        vanishmeta.setDisplayName(ChatColor.DARK_GREEN + "⋙ " + ChatColor.GREEN + "Vanish Current: ON" + ChatColor.DARK_GREEN + " ⋘");
                        vanish.setItemMeta(vanishmeta);

                        plr.getInventory().setItem(8, vanish);
                    }


                }
            } else {

                sender.sendMessage(ChatColor.RED + "You don't have permission :(");

            }
        }
        return false;
    }


}

