package com.atzer.criticalsRemover;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;


public class OnEntityTakeDamage implements Listener {

    @EventHandler
    public boolean onEntityTakeDamage(@NotNull EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) return false;
        player = player.getKiller();

        if (player == null) return false;

        @SuppressWarnings("deprecation")
        boolean flag = player.getFallDistance() > 0.0F && !player.isOnGround() && !player.hasPotionEffect(PotionEffectType.BLINDNESS) && player.getVehicle() == null;

        if (flag) {
            player.damage(event.getDamage() / 1.5F);
        }
        return true;
    }
}
