package dev.sussolino.juicypractice.config;

import dev.sussolino.juicyapi.file.BukkitStaticFile;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class SettingsYml extends BukkitStaticFile {

    public SettingsYml(JavaPlugin plugin) {
        super(plugin, "settings");
    }
}
