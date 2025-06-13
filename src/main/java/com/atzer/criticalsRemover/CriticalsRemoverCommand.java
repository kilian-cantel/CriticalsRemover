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

        if (strings.length != 1) {
            commandSender.sendMessage(Component.text("Usage: /criticalsremover <amount>").color(NamedTextColor.RED));
            return false;
        }

        try {
            double multi = Double.parseDouble(strings[0]);
            CriticalsRemover.getInstance().setMulti(multi);
            commandSender.sendMessage(Component.text("Le multi est maintenant: " + multi).color(NamedTextColor.GREEN));
            return true;
        } catch (NumberFormatException e) {
            commandSender.sendMessage(Component.text("L'argument fourni est incorrect !").color(NamedTextColor.RED));
            return false;
        }
    }
}
