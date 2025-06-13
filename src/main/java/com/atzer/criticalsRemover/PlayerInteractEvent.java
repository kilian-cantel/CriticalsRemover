package com.atzer.criticalsRemover;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class PlayerInteractEvent implements Listener {

    private final Map<Player, Long> attackStartTime = new HashMap<>();
    private final static Map<Player, Double> percentageChargeAttack = new HashMap<>();

    // Pour avoir le temps de chargement d'une attaque.
    @EventHandler
    public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            // Le joueur commence à charger une attaque
            attackStartTime.put(player, System.currentTimeMillis());

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (player.isHandRaised()) {
                        long startTime = attackStartTime.getOrDefault(player, System.currentTimeMillis());
                        long currentTime = System.currentTimeMillis();
                        long chargeTime = currentTime - startTime;

                        // Supposons que le temps maximum pour charger une attaque est de 1000 ms (1 seconde)
                        double chargePercentage = chargeTime / 1000.0 * 100.0;

                        if (chargePercentage > 100.0) chargePercentage = 100.0;

                        percentageChargeAttack.put(player, chargePercentage);

                        if (!player.isHandRaised()) {
                            this.cancel();
                        }
                    } else {
                        this.cancel();
                    }
                }
            }.runTaskTimer(CriticalsRemover.getInstance(), 0L, 1L); // Exécute cette tâche toutes les 1 tick (50 ms)
        }
    }

    public static double getPercentageCharge(Player player) {
        return percentageChargeAttack.getOrDefault(player, 0.0);
    }

}
