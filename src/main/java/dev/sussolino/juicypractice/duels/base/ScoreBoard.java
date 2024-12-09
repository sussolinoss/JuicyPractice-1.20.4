package dev.sussolino.juicypractice.duels.base;

import dev.sussolino.juicypractice.Juicy;
import dev.sussolino.juicypractice.utils.config.ConfigUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static dev.sussolino.juicyapi.color.ColorUtils.color;

@Getter
public class ScoreBoard {

    private final BukkitRunnable task;
    private final List<Player> players;
    private final List<String> lines;
    private final Duel duel;

    public ScoreBoard(Duel duel) {
        this.duel = duel;
        this.players = new CopyOnWriteArrayList<>(duel.players);
        this.lines = ConfigUtils.DUELS_SCOREBOARD_LINES.getStringList();

        this.task = new BukkitRunnable() {
            @Override
            public void run() {
                if (duel.state.equals(Duel.State.FINISH)) {
                    duel.players.forEach(player -> player.setScoreboard(Bukkit.getServer().getScoreboardManager().getMainScoreboard()));
                    cancel();
                    return;
                }
                updateScoreboards();
            }
        };
    }

    public void run() {
        task.runTaskTimer(Juicy.INSTANCE, 20L, 20L);
    }

    private void updateScoreboards() {
        for (Player player : players) {
            createScoreboard(player);
        }
    }

    private void createScoreboard(Player p) {
        if (p == null || !p.isOnline()) return;

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("scoreboard", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(color(ConfigUtils.DUELS_SCOREBOARD_TITLE.getString().replace("{duel}", duel.duelName)));

        Player enemy = getEnemy(p);

        //-------------------------------------------------------
        Team BLUE = scoreboard.registerNewTeam("blue");
        BLUE.setPrefix(ChatColor.BLUE + "[BLUE] ");
        BLUE.setDisplayName(ChatColor.BLUE + enemy.getName());
        BLUE.addEntry(p.getName());

        Team RED = scoreboard.registerNewTeam("red");
        RED.setPrefix(ChatColor.RED + "[RED] ");
        RED.setDisplayName(ChatColor.RED + enemy.getName());
        RED.addEntry(enemy.getName());
        //-------------------------------------------------------

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i)
                    .replace("{time}", duel.timeManager.getTime())
                    .replace("{you}", p.getName())
                    .replace("{health}", String.valueOf(p.getHealth()))
                    .replace("{enemy}", enemy.getName());
            Score score = objective.getScore(color(line));
            score.setScore(lines.size() - 1 - i);
        }

        p.setScoreboard(scoreboard);
    }

    private Player getEnemy(Player player) {
        return players.stream().filter(p -> !p.equals(player)).findFirst().orElse(player);
    }
}
