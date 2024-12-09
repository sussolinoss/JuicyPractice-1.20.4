package dev.sussolino.juicypractice.stats;

import dev.sussolino.juicypractice.file.StatsYml;

public class Stats {

    private final String path;

    public Stats(String duel, String player) {
        this.path = duel + "." + player + ".";
    }

    public int get(StatsType stats) {
        return StatsYml.getConfig().getInt(path + stats.name().toLowerCase());
    }
    public void set(StatsType stats, int value) {
        StatsYml.getConfig().set(path + stats.name().toLowerCase(), value);
        StatsYml.reload();
    }
    public void increment(StatsType stats, int amount) {
        StatsYml.getConfig().set(path + stats.name().toLowerCase(), get(stats) + amount);
        StatsYml.reload();
    }

    public enum StatsType {
        WINS, LOSES, PLAYED
    }
}
