package dev.sussolino.juicypractice.utils.location;

import dev.sussolino.juicypractice.file.DuelsYml;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class LocationUtil {

    public static Location get(String path) {
        final FileConfiguration config = DuelsYml.getConfig();

        double x = config.getDouble(path + ".x");
        double y = config.getDouble(path + ".y");
        double z = config.getDouble(path + ".z");

        double yaw = config.getDouble(path + ".yaw");
        double pitch = config.getDouble(path + ".pitch");

        World w = Bukkit.getWorld(config.getString(path + ".world"));

        return new Location(w, x, y, z, (float) yaw, (float) pitch);
    }
}