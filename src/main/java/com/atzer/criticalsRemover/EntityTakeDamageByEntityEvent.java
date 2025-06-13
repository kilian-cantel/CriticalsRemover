package com.atzer.criticalsRemover;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

public class EntityTakeDamageByEntityEvent implements Listener {

    @EventHandler
    public void onEntityTakeDamage(@NotNull EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            boolean flag = player.getFallDistance() > 0.0F && !event.getDamager().isOnGround() && !player.hasPotionEffect(PotionEffectType.BLINDNESS) && player.getVehicle() == null && !player.isSprinting() && PlayerInteractEvent.getPercentageCharge(player) < 81.0;

            if (flag && event.getDamage() > 0.0F) {
                ItemStack weapon = player.getInventory().getItemInMainHand();
                double baseDamage = event.getDamage();
                double enchantDamage = 0.0;

                // Calculer les dégâts d'enchantements.
                if (weapon.containsEnchantment(Enchantment.SHARPNESS)) {
                    enchantDamage += weapon.getEnchantmentLevel(Enchantment.SHARPNESS) * 0.5 + 0.5;
                }
                if (weapon.containsEnchantment(Enchantment.SMITE) && isDeadAlive(event.getEntity())) {
                    enchantDamage += weapon.getEnchantmentLevel(Enchantment.SMITE) * 2.5;
                }
                if (weapon.containsEnchantment(Enchantment.BANE_OF_ARTHROPODS) && isArthropode(event.getEntity())) {
                    enchantDamage += weapon.getEnchantmentLevel(Enchantment.BANE_OF_ARTHROPODS) * 2.5;
                }

                // Soustraire les dégâts d'enchantements pour obtenir les dégâts de base
                baseDamage -= enchantDamage;

                // Appliquer la réduction uniquement sur les dégâts de base
                baseDamage /= 1.5F;

                baseDamage *= CriticalsRemover.getInstance().getMulti();

                // Réappliquer les dégâts d'enchantements
                event.setDamage(baseDamage + enchantDamage);
            }

            if (CriticalsRemover.isDebugging()) player.sendMessage(Component.text("Vous avez fait: " + event.getDamage() + " de dégats !").color(NamedTextColor.GREEN));
            return;
        } else if (event.getDamager() instanceof Arrow arrow) {
            if (arrow.getShooter() instanceof Player player) {
                ItemStack weapon = player.getInventory().getItemInMainHand();
                double damage = event.getDamage();

                if (PlayerInteractEvent.getPercentageCharge(player) > 81.0) {
                    // Suppression de l'effet critique en divisant par 1.5
                    damage /= 1.5;

                    damage *= CriticalsRemover.getInstance().getMulti();

                    // Réapplication de Power
                    if (weapon.containsEnchantment(Enchantment.POWER)) {
                        int powerLevel = weapon.getEnchantmentLevel(Enchantment.POWER);
                        double multiplier = 1 + 0.25 * (powerLevel + 1);
                        damage *= multiplier;
                    }

                    event.setDamage(Math.ceil(damage));
                }

                if (CriticalsRemover.isDebugging()) player.sendMessage(Component.text("Vous avez fait: " + event.getDamage() + " de dégâts !").color(NamedTextColor.GREEN));
            }
        }
    }


    private boolean isDeadAlive(Entity entity) {
        return entity instanceof Skeleton ||
                entity instanceof Stray ||
                entity instanceof WitherSkeleton ||
                entity instanceof Zombie ||
                entity instanceof ZombieVillager ||
                entity instanceof Husk ||
                entity instanceof Drowned ||
                entity instanceof Piglin ||
                entity instanceof Phantom ||
                entity instanceof Wither ||
                entity instanceof SkeletonHorse ||
                entity instanceof ZombieHorse ||
                entity instanceof Zoglin;
    }

    private boolean isArthropode(Entity entity) {
        return entity instanceof Spider ||
                entity instanceof Silverfish ||
                entity instanceof Endermite ||
                entity instanceof Bee;
    }
}

