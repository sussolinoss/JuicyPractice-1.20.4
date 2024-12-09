package dev.sussolino.juicypractice.file;

import dev.sussolino.juicyapi.file.BukkitStaticFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class KitsYml extends BukkitStaticFile {

    public KitsYml(JavaPlugin plugin) {
        super(plugin, "kits");
    }

    public List<String> getKits() {
        return getConfig()
                .getKeys(false)
                .stream()
                .filter(s -> s.equals("kits"))
                .map(Objects::toString)
                .collect(Collectors.toList());
    }
}
