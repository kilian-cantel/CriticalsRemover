package com.atzer.criticalsRemover;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CriticalsRemoverCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!commandSender.hasPermission("criticalsremover.admin")) {
            commandSender.sendMessage(Component.text("Vous n'avez pas la permission !").color(NamedTextColor.RED));
            return false;
        }

        CriticalsRemover.setActive(!CriticalsRemover.isActive());
        commandSender.sendMessage(Component.text("Le plugin " + (CriticalsRemover.isActive() ? "est" : "n'est pas") + " actif !").color(NamedTextColor.GREEN));
        return true;
    }
}
