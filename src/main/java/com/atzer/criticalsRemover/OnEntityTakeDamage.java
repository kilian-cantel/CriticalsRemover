package com.atzer.criticalsRemover;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class OnEntityTakeDamage implements Listener {

    @EventHandler
    public boolean onEntityTakeDamage(@NotNull EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return false;

        boolean flag = player.getFallDistance() > 0.0F && !event.getDamager().isOnGround() && player.getLocation().getBlock().getType() != Material.LADDER && player.getLocation().getBlock().getType() != Material.VINE && !player.hasPotionEffect(PotionEffectType.BLINDNESS) && player.getVehicle() == null && !player.isSprinting() && player.getAttackCooldown() == 0.0F;

        if (flag && event.getDamage() > 0.0F) {
            event.setDamage(event.getDamage() / 1.5F);
        }
        return true;
    }
}
