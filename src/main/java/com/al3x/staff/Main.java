package com.al3x.staff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    String prefix = ChatColor.GRAY + "[" + ChatColor.DARK_RED + "S" + ChatColor.GRAY + "]";

    @Override
    public void onEnable() {

        StaffMode staffMode = new StaffMode();
        getCommand("vanish").setExecutor(new Vanish());
        getCommand("staffmode").setExecutor(staffMode);
        Bukkit.getPluginManager().registerEvents(new StaffModeListener(), this);
        Bukkit.getPluginManager().registerEvents(staffMode, this);

        System.out.println("StaffPlugin by AWP#0562 enabled");
        System.out.println("Current prefix is " + prefix);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
