package com.al3x.staff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class StaffMode implements CommandExecutor, Listener{

    String prefix = ChatColor.GRAY + "[" + ChatColor.DARK_RED + "S" + ChatColor.GRAY + "]";

    private static List<UUID> staffmode = new ArrayList<UUID>();

    public static List<UUID> getStaffmodeList() {
        return staffmode;
    }

    private static HashMap<UUID, ItemStack[]> armorContents = new HashMap<UUID, ItemStack[]>();
    private static HashMap<UUID, ItemStack[]> inventoryContents = new HashMap<UUID, ItemStack[]>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player plr = ((Player) sender).getPlayer();



        if (sender.hasPermission("ratiomc.staffmode")) {

            if (!staffmode.contains(((Player) sender).getUniqueId())) {

                staffmode.add(plr.getUniqueId());

                saveInv(plr);

                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // THRU
                ItemStack compass = new ItemStack(Material.COMPASS);
                ItemMeta compassmeta = compass.getItemMeta();
                compassmeta.setDisplayName(ChatColor.DARK_AQUA + "⋙ " + ChatColor.AQUA + "Phase" + ChatColor.DARK_AQUA + " ⋘");
                compass.setItemMeta(compassmeta);

                plr.getInventory().setItem(0, compass);
                // THRU

                // ONLINE STAFF
                ItemStack skull = new ItemStack(Material.SKULL_ITEM);
                ItemMeta skullmeta = skull.getItemMeta();
                skullmeta.setDisplayName(ChatColor.DARK_RED + "⋙ " + ChatColor.RED + "Online Staff" + ChatColor.DARK_RED + " ⋘");
                skull.setItemMeta(skullmeta);

                plr.getInventory().setItem(1, skull);
                // ONLINE STAFF


                // RANDOM TP
                ItemStack portal = new ItemStack(Material.NETHER_STAR);
                ItemMeta portalmeta = portal.getItemMeta();
                portalmeta.setDisplayName(ChatColor.DARK_PURPLE + "⋙ " + ChatColor.LIGHT_PURPLE + "Random Teleport" + ChatColor.DARK_PURPLE + " ⋘");
                portal.setItemMeta(portalmeta);

                plr.getInventory().setItem(2, portal);
                // RANDOM TP


                // EXIT
                ItemStack exit = new ItemStack(Material.BARRIER);
                ItemMeta exitmeta = exit.getItemMeta();
                exitmeta.setDisplayName(ChatColor.RED + "⋙ " + ChatColor.DARK_RED + "Exit Staffmode" + ChatColor.RED + " ⋘");
                exit.setItemMeta(exitmeta);

                plr.getInventory().setItem(4, exit);
                // EXIT


                // INSPECT INV
                ItemStack book = new ItemStack(Material.BOOK);
                ItemMeta bookmeta = book.getItemMeta();
                bookmeta.setDisplayName(ChatColor.GOLD + "⋙ " + ChatColor.YELLOW + "Inspect Inventory" + ChatColor.GOLD + " ⋘");
                book.setItemMeta(bookmeta);

                plr.getInventory().setItem(6, book);
                // INSPECT INV


                // FREEZE
                ItemStack freeze = new ItemStack(Material.ICE);
                ItemMeta freezemeta = freeze.getItemMeta();
                freezemeta.setDisplayName(ChatColor.DARK_GRAY + "⋙ " + ChatColor.GRAY + "Freeze" + ChatColor.DARK_GRAY + " ⋘");
                freeze.setItemMeta(freezemeta);

                plr.getInventory().setItem(7, freeze);
                // FREEZE

                // VANISH
                ItemStack vanish = new ItemStack(Material.INK_SACK, 1, (short) 10);
                ItemMeta vanishmeta = vanish.getItemMeta();
                vanishmeta.setDisplayName(ChatColor.DARK_GREEN + "⋙ " + ChatColor.GREEN + "Vanish Current: ON" + ChatColor.DARK_GREEN + " ⋘");
                vanish.setItemMeta(vanishmeta);

                plr.getInventory().setItem(8, vanish);
                // VANISH

                if (!Vanish.getVanishList().contains(plr.getUniqueId())) {
                    plr.performCommand("vanish");
                }

                plr.setGameMode(GameMode.CREATIVE);

                sender.sendMessage(prefix + ChatColor.RED + " " + plr.getDisplayName() + " just entered staffmode!");



            } else {

                staffmode.remove(plr.getUniqueId());

                restoreInv(plr);

                plr.setGameMode(GameMode.SURVIVAL);

                plr.sendMessage(prefix + ChatColor.RED + " " + plr.getDisplayName() + " just left staffmode!");


            }


        }
        return false;
    }

    // Saving / loading inventory

    public static void saveInv(Player plr) {

        //armorContents.put(plr.getUniqueId(), plr.getInventory().getArmorContents());
        inventoryContents.put(plr.getUniqueId(), plr.getInventory().getContents());

        plr.getInventory().setBoots(new ItemStack(Material.AIR));
        plr.getInventory().setLeggings(new ItemStack(Material.AIR));
        plr.getInventory().setChestplate(new ItemStack(Material.AIR));
        plr.getInventory().setHelmet(new ItemStack(Material.AIR));

        plr.getInventory().clear();

    }

    public static void restoreInv (Player plr) {

        //plr.getInventory().setArmorContents(armorContents.get(plr.getUniqueId()));
        plr.getInventory().setContents(inventoryContents.get(plr.getUniqueId()));

        armorContents.remove(plr.getUniqueId());
        inventoryContents.remove(plr.getUniqueId());

    }

    @EventHandler
    public void blockbreak(BlockBreakEvent e) {

        Player plr = e.getPlayer();

        if (staffmode.contains(plr.getUniqueId())) {
            e.setCancelled(true);

            plr.sendMessage(ChatColor.RED + "You cannot break blocks in staffmode.");
        }
    }

    @EventHandler
    public void blockplace(BlockPlaceEvent e) {

        Player plr = e.getPlayer();

        if (staffmode.contains(plr.getUniqueId())) {
            e.setCancelled(true);

            plr.sendMessage(ChatColor.RED + "You cannot place blocks in staffmode.");
        }
    }

    @EventHandler
    public void interactEntity(EntityDamageEvent e) {

        if (e.getEntity() instanceof Player) {
            Player plr = (Player) e.getEntity();

            if (staffmode.contains(plr.getUniqueId())) {
                e.setCancelled(true);

                plr.sendMessage(ChatColor.RED + "You cannot hit players in staffmode.");
            }
        }
    }

    @EventHandler
    public void drop(PlayerDropItemEvent e) {

        Player plr = e.getPlayer();

        if (staffmode.contains(plr.getUniqueId())) {
            e.setCancelled(true);

            plr.sendMessage(ChatColor.RED + "You cannot drop items in staffmode.");
        }

    }

}
