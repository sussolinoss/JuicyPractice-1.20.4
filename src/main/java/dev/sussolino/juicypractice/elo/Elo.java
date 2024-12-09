package dev.sussolino.juicypractice.elo;

import dev.sussolino.juicypractice.file.EloYml;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class Elo {

    private final String playerName;
    private final FileConfiguration config;

    @Getter private double elo;

    public Elo(String playerName) {
        this.playerName = playerName;
        this.config = EloYml.getConfig();
        this.elo = config.getDouble(playerName);
    }

    public void win(double enemy) {
        double expected = 1 / (1 + Math.pow(10, (enemy - elo) / 400));
        double score = 1;
        double ratio = 32 * (score - expected);
        elo += ratio;
        save();
    }

    public void lose(double enemy) {
        double expected = 1 / (1 + Math.pow(10, (enemy - elo) / 400));
        double score = 0;
        double ratio = 32 * (score - expected);
        elo += ratio;
        save();
    }

    public void kill(double enemy) {
        win(enemy);
    }

    public void death(double enemy) {
        lose(enemy);
    }

    public void winStreak(int streak) {
        elo += streak * 10;
        save();
    }

    public void deathStreak(int streak) {
        elo -= streak * 10;
        save();
    }

    public void killStreak(int streak) {
        elo += streak * 10;
        save();
    }

    public void loseStreak(int streak) {
        elo -= streak * 10;
        save();
    }

    private void save() {
        if (elo < 0) elo = 0;
        config.set(playerName + ".elo", elo);

        EloYml.reload();
    }
}