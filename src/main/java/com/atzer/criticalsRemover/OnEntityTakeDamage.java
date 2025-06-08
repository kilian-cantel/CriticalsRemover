package com.atzer.criticalsRemover;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class OnEntityTakeDamage implements Listener {

    @EventHandler
    public boolean onEntityTakeDamage(@NotNull EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return false;

        @SuppressWarnings("deprecation")
        boolean flag = player.getFallDistance() > 0.0F && !player.isOnGround() && !player.hasPotionEffect(PotionEffectType.BLINDNESS) && player.getVehicle() == null;

        if (flag) {
            Objects.requireNonNull(event.getEntity().getLastDamageCause()).setDamage(event.getDamage() / 1.5F);
        }
        return true;
    }
}
