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
import java.util.concurrent.TimeUnit;

public class Vanish implements CommandExecutor {

    String prefix = ChatColor.GRAY + "[" + ChatColor.DARK_RED + "S" + ChatColor.GRAY + "]";

    private static List<UUID> vanished = new ArrayList<>();

    public static List<UUID> getVanishList() {
        return vanished;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player plr = (Player) sender;

            if (sender.hasPermission("ratiomc.staff")) {

                if (vanished.contains(((Player) sender).getUniqueId())) {

                    vanished.remove(((Player) sender).getUniqueId());

                    for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                        players.showPlayer((Player) sender);

                        if (players.hasPermission("ratiomc.staff")) {
                            if (args.length == 1) {
                                if (args[0].equalsIgnoreCase("staffmode")) {
                                    System.out.println("Silent Vanish");
                                }
                            }
                        }
                    }

                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
                        if (!players.hasPermission("ratiomc.staff")) {
                            players.hidePlayer((Player) sender);
                        } else {
                            if (args.length == 1) {
                                if (args[0].equalsIgnoreCase("staffmode")) {
                                    System.out.println("Silent Vanish");

                                }
                            }

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

