package dev.sussolino.juicypractice.command.core;

import dev.sussolino.juicypractice.file.WarpsYml;
import dev.sussolino.juicyapi.reflection.abstracts.PlayerCommand;
import dev.sussolino.juicyapi.reflection.annotations.AntiSocial;
import dev.sussolino.juicypractice.warps.Warp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AntiSocial
public class WarpCommand extends PlayerCommand implements TabCompleter {

    @Override
    protected void execute(Player p, String[] args) {
        if (args.length == 0) {
            p.sendMessage("Usage: /warp <set|tp|delete> <warpName>");
            return;
        }

        if (args.length == 2) {
            String warpName = args[1];

            Warp warp = new Warp(warpName);

            switch (args[0]) {
                case "set" -> {
                    warp.setLocation(p.getLocation());
                    p.sendMessage("Warp '" + warpName + "' has been set!");
                }
                case "tp" -> {
                    if (warp.getLocation() == null) {
                        p.sendMessage("Warp '" + warpName + "' does not exist!");
                        return;
                    }
                    p.teleport(warp.getLocation());
                    p.sendMessage("Teleported to warp '" + warpName + "'.");
                }
                case "delete" -> {
                    if (warp.getLocation() == null) {
                        p.sendMessage("Warp '" + warpName + "' does not exist!");
                        return;
                    }
                    warp.delete();
                    p.sendMessage("Warp '" + warpName + "' has been deleted!");
                }
                default -> p.sendMessage("Unknown command. Use /warp <set|tp|delete> <warpName>");
            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1) {
            list.addAll(List.of("set", "tp", "delete"));
        }
        else if (strings.length == 2) {
            if (!strings[0].equalsIgnoreCase("set")) {
                Set<String> warps = WarpsYml.getConfig().getKeys(false);
                list.addAll(warps);
            }
        }
        return list;
    }
}
