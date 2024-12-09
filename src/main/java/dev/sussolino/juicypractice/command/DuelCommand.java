package dev.sussolino.juicypractice.command;

import dev.sussolino.juicyapi.reflection.annotations.AntiSocial;
import dev.sussolino.juicypractice.Juicy;
import dev.sussolino.juicypractice.duels.boxpvp.BoxPvP;
import dev.sussolino.juicypractice.duels.crystal.Crystal;
import dev.sussolino.juicypractice.duels.diapot.DiaPot;
import dev.sussolino.juicypractice.duels.nethpot.NethPot;
import dev.sussolino.juicypractice.duels.smp.Smp;
import dev.sussolino.juicypractice.duels.sword.Sword;
import dev.sussolino.juicypractice.duels.tridentpvp.TridentPvP;
import dev.sussolino.juicyapi.reflection.abstracts.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@AntiSocial
public class DuelCommand extends PlayerCommand implements TabCompleter {

    @Override
    protected void execute(Player p, String[] args) {
        if (args.length == 0) {
            //TODO HELP MSG
        }
        else if (args.length == 2) {
            Player t = Bukkit.getPlayerExact(args[0]);

            if (t == null) {
                //TODO PLAYER-OFFLINE
                return;
            }
            if (t == p) {
                //TODO RETARDED
                return;
            }

            String duel = args[1];

            switch (duel) {
                case "cpvp":
                    new Crystal(duel, List.of(p, t));
                    break;
                case "sword":
                    new Sword(duel, List.of(p, t));
                    break;
                case "diapot":
                    new DiaPot(duel, List.of(p, t));
                    break;
                case "nethpot":
                    new NethPot(duel, List.of(p, t));
                    break;
                case "smp":
                    new Smp(duel, List.of(p, t));
                    break;
                case "boxpvp":
                    new BoxPvP(duel, List.of(p, t));
                    break;
                case "tridentpvp":
                    new TridentPvP(duel, List.of(p, t));
                    break;
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> duels = new ArrayList<>();
        if (args.length == 1) Bukkit.getOnlinePlayers().forEach(player -> duels.add(player.getName()));
        else if (args.length == 2) duels.addAll(Juicy.SETTINGS.getDuels());

        return duels;
    }
}
