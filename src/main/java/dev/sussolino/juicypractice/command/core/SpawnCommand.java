package dev.sussolino.juicypractice.command.core;

import dev.sussolino.juicypractice.spawn.Spawn;
import dev.sussolino.juicyapi.reflection.abstracts.PlayerCommand;
import dev.sussolino.juicypractice.utils.config.ConfigUtils;
import dev.sussolino.juicyapi.reflection.annotations.AntiSocial;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@AntiSocial
public class SpawnCommand extends PlayerCommand implements TabCompleter {

    @Override
    protected void execute(Player p, String[] args) {
        if (args.length == 0) {
            Spawn.teleport(p);
            p.sendMessage(ConfigUtils.SPAWN_TP.getString());
        }
        else if (args.length == 1) {
            String cmd = args[0];
            if (cmd.equals("set")) {
                Spawn.set(p.getLocation());
                p.sendMessage(ConfigUtils.SPAWN_MESSAGES_SET.getString());
            }
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        List<String> COMMANDS = new ArrayList<>();

        if (strings.length == 1 && commandSender.hasPermission("practice.admin")) {
            COMMANDS.add("set");
        }
        return COMMANDS;
    }
}