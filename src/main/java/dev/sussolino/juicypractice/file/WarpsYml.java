package dev.sussolino.juicypractice.file;

import dev.sussolino.juicyapi.file.BukkitFile;
import dev.sussolino.juicyapi.file.BukkitStaticFile;
import org.bukkit.plugin.java.JavaPlugin;

public class WarpsYml extends BukkitStaticFile {

    public WarpsYml(JavaPlugin plugin) {
        super(plugin, "warps");
    }
}