package dev.sussolino.juicypractice.file;

import dev.sussolino.juicyapi.file.BukkitStaticFile;
import org.bukkit.plugin.java.JavaPlugin;

public class EloYml extends BukkitStaticFile {

    public EloYml(JavaPlugin plugin) {
        super(plugin, "elo");
    }
}
