package dev.sussolino.juicypractice.command;

import dev.sussolino.juicyapi.reflection.abstracts.PlayerCommand;
import dev.sussolino.juicyapi.reflection.annotations.AntiSocial;
import dev.sussolino.juicypractice.stats.Stats;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static dev.sussolino.juicypractice.utils.config.ConfigUtils.*;

@AntiSocial
public class EloCommand extends PlayerCommand implements TabCompleter {

    //elo set/view/reset player

    @Override
    protected void execute(Player p, String[] args) {
        if (args.length < 2) {
            p.sendMessage(STATS_HELP.getString());
            return;
        }

        String action = args[0];
        Player target = Bukkit.getPlayer(args[1]);
        String duel = args[2];
        Stats.StatsType statsType = null;

        if (args.length > 3) {
            try {
                statsType = Stats.StatsType.valueOf(args[3].toUpperCase());
            } catch (IllegalArgumentException e) {
                p.sendMessage(STATS_ERROR.getString());
                return;
            }
        }

        int amount = 0;
        if (args.length > 4) {
            try {
                amount = Integer.parseInt(args[4]);
            }
            catch (NumberFormatException e) {
                p.sendMessage(STATS_ERROR.getString());
                return;
            }
        }

        if (target == null) {
            p.sendMessage(STATS_ERROR.getString());
            return;
        }

        Stats stats = new Stats(duel, target.getName());

        switch (action.toLowerCase()) {
            case "view":
                if (args.length == 3) {
                    p.sendMessage(
                            String.format(
                                    "WINS - %d\nLOSES - %d\nPLAYED - %d",
                                    stats.get(Stats.StatsType.WINS),
                                    stats.get(Stats.StatsType.LOSES),
                                    stats.get(Stats.StatsType.PLAYED)
                            )
                    );
                } else {
                    p.sendMessage(STATS_HELP.getString());
                }
                break;

            case "set":
                if (args.length == 5) {
                    stats.set(statsType, amount);
                    p.sendMessage(STATS_SET.getString()
                            .replace("{player}", target.getName())
                            .replace("{amount}", String.valueOf(amount))
                            .replace("{type}", statsType.name()));
                }
                else p.sendMessage(STATS_HELP.getString());
                break;
            case "reset":
                if (args.length == 4) {
                    stats.set(statsType, 0);
                    p.sendMessage(STATS_RESET.getString()
                            .replace("{player}", target.getName())
                            .replace("{type}", statsType.name()));
                }
                else p.sendMessage(STATS_HELP.getString());

                break;

            default:
                p.sendMessage(STATS_HELP.getString());
                break;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> TAB = new ArrayList<>();
        switch (args.length) {
            case 1:
                TAB.addAll(List.of("view", "set", "reset"));
                break;
            case 2:
                Bukkit.getOnlinePlayers().forEach(player -> TAB.add(player.getName()));
                break;
        }
        return TAB.stream().filter(suggestion -> suggestion.toLowerCase().startsWith(args[args.length - 1].toLowerCase())).collect(Collectors.toList());
    }
}
