package com.atzer.criticalsRemover;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class CriticalsRemover extends JavaPlugin {

    private final static Logger LOGGER = Bukkit.getServer().getLogger();
    private final static PluginManager pm = Bukkit.getPluginManager();
    private static CriticalsRemover instance;
    private static boolean ACTIVE = true;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        if (!getConfig().getBoolean("active")) ACTIVE = false;

        instance = this;
        LOGGER.info("CriticalsRemover enabled !");
        LOGGER.info("Author : Atzer722 !");

        pm.registerEvents(new OnEntityTakeDamage(), this);

        getCommand("criticalsremover").setExecutor(new CriticalsRemoverCommand());
    }

    @Override
    public void onDisable() {
        LOGGER.info("CriticalsRemover disabled !");
    }

    public static CriticalsRemover getInstance() {
        return instance;
    }

    public static boolean isActive() {
        return ACTIVE;
    }

    public static void setActive(boolean active) {
        CriticalsRemover.getInstance().getConfig().set("active", active);
        ACTIVE = active;
    }
}
