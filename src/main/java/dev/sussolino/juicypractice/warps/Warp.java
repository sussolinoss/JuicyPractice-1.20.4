package dev.sussolino.juicypractice.warps;

import dev.sussolino.juicypractice.file.WarpsYml;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class Warp {

    private final String name;
    private final FileConfiguration config;

    public Warp(String name) {
        this.name = name;
        this.config = WarpsYml.getConfig();
    }

    public void delete() {
        config.set(name, null);
        reload();
    }
    public Location getLocation() {
        return config.getLocation(name + ".location");
    }
    public void setLocation(Location location) {
        config.set(name + ".location", location);
        reload();
    }


    private void reload() {
        WarpsYml.reload();
    }
}
