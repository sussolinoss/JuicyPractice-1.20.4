package dev.sussolino.juicypractice.file;

import dev.sussolino.juicyapi.file.BukkitFile;
import dev.sussolino.juicyapi.file.BukkitStaticFile;
import org.bukkit.plugin.java.JavaPlugin;

public class StatsYml extends BukkitStaticFile {

    public StatsYml(JavaPlugin plugin) {
        super(plugin, "stats");
    }
}