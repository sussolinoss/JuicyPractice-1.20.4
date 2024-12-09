package dev.sussolino.juicypractice.command;

import dev.sussolino.juicypractice.duels.Duels;
import dev.sussolino.juicypractice.Juicy;
import dev.sussolino.juicypractice.file.DuelsYml;
import dev.sussolino.juicypractice.file.StatsYml;
import dev.sussolino.juicyapi.reflection.abstracts.PlayerCommand;
import dev.sussolino.juicyapi.reflection.annotations.AntiSocial;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@AntiSocial
public class DuelsCommand extends PlayerCommand implements TabCompleter {

    private final HashMap<Player, HashMap<Integer, Location>> PRE_AREA_POSITIONS = new HashMap<>();

    @Override
    protected void execute(Player p, String[] args) {
        if (args.length < 2) return;

        String cmd = args[0];
        String duelName = args[1];
        Duels duel = new Duels(duelName);

        if (args.length == 2) {
            switch (cmd) {
                case "pos1":
                case "pos2":
                    int setType = cmd.equalsIgnoreCase("pos1") ? 1 : 2;
                    PRE_AREA_POSITIONS.computeIfAbsent(p, k -> new HashMap<>()).put(setType, p.getLocation());
                    duel.setPosition(setType, p.getLocation());
                    p.sendMessage("You have successfully set pos" + setType + " for " + duelName);
                    break;
                case "set-kit":
                    p.sendMessage("You have successfully set the kit for " + duelName);
                    duel.setKit(p.getInventory().getContents(), p.getInventory().getArmorContents(), p.getInventory().getItemInOffHand());
                    break;
                case "remove-kit":
                    p.sendMessage("You have successfully removed the kit for " + duelName);
                    duel.removeKit();
                    break;
                case "set-lobby":
                    p.sendMessage("You have successfully set the lobby for " + duelName);
                    duel.setLobby(p.getLocation());
                    break;
                case "remove-lobby":
                    p.sendMessage("You have successfully removed the lobby for " + duelName);
                    duel.removeLobby();
                    break;
                case "set-world":
                    p.sendMessage("You have successfully set the world for " + duelName);
                    duel.setWorld(p.getWorld().getName());
                    break;
                case "reload":
                    DuelsYml.reload();
                    StatsYml.reload();
                    Juicy.SETTINGS.reload();
                    p.sendMessage("Duel reloaded!");
                    break;
                case "set-global-position":
                    p.sendMessage("You have sucessfully set the global position for " + duelName);
                    duel.setGlobalPosition(p.getLocation());
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> PEKKA = new ArrayList<>();

        if (strings.length == 1) {
            PEKKA.addAll(Arrays.asList(
                    "set-kit", "remove-kit",
                    "set-world",
                    "reload",
                    "set-global-position",
                    "bed-host", "bed-enemy",
                    "pos1", "pos2", "set-lobby", "remove-lobby"
            ));
        } else if (strings.length == 2) {
            PEKKA.addAll(Juicy.SETTINGS.getDuels());
        }
        return PEKKA;
    }
}
