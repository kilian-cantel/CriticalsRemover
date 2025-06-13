package com.atzer.criticalsRemover;


import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.logging.Logger;

public final class CriticalsRemover extends JavaPlugin implements Listener {

    private final static Logger LOGGER = Bukkit.getServer().getLogger();
    private final static PluginManager pm = Bukkit.getPluginManager();
    private static CriticalsRemover instance;
    private static boolean DEBUGGING = false;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        DEBUGGING = getConfig().getBoolean("debug", false);
        instance = this;
        LOGGER.info("CriticalsRemover enabled !");
        LOGGER.info("Author : Atzer722 !");

        pm.registerEvents(new EntityTakeDamageByEntityEvent(), this);
        pm.registerEvents(new PlayerInteractEvent(), this); // Pour calculer le pourcentage de chargement d'une attaque

        getCommand("criticalsremover").setExecutor(new CriticalsRemoverCommand());
    }

    @Override
    public void onDisable() {
        LOGGER.info("CriticalsRemover disabled !");
    }

    public static CriticalsRemover getInstance() {
        return instance;
    }

    public double getMulti() {
        return this.getConfig().getDouble("criticals_multiplier", 1.0);
    }

    public void setMulti(double multi) {
        this.getConfig().set("criticals_multiplier", multi);
        this.saveConfig();
    }

    public static boolean isDebugging() {
        return DEBUGGING;
    }
}
