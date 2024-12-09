package dev.sussolino.juicypractice.spawn;

import dev.sussolino.juicypractice.utils.config.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Spawn  {

    public static void set(Location loc) {
        ConfigUtils.setDouble(ConfigUtils.SPAWN_LOCATION_X.path, loc.getX());
        ConfigUtils.setDouble(ConfigUtils.SPAWN_LOCATION_Y.path, loc.getY());
        ConfigUtils.setDouble(ConfigUtils.SPAWN_LOCATION_Z.path, loc.getZ());
        ConfigUtils.setDouble(ConfigUtils.SPAWN_LOCATION_YAW.path, loc.getYaw());
        ConfigUtils.setDouble(ConfigUtils.SPAWN_LOCATION_PITCH.path, loc.getPitch());
        ConfigUtils.setString(ConfigUtils.SPAWN_LOCATION_WORLD.path, loc.getWorld().getName());
    }

    public static Location getLocation() {
        double x = ConfigUtils.SPAWN_LOCATION_X.getDouble();
        double y = ConfigUtils.SPAWN_LOCATION_Y.getDouble();
        double z = ConfigUtils.SPAWN_LOCATION_Z.getDouble();

        float pitch = (float) ConfigUtils.SPAWN_LOCATION_PITCH.getDouble();
        float yaw = (float) ConfigUtils.SPAWN_LOCATION_YAW.getDouble();

        World w = Bukkit.getWorld(ConfigUtils.SPAWN_LOCATION_WORLD.getString());

        return new Location(w, x, y, z, yaw, pitch);
    }

    public static void teleport(Player p) {
        if (ConfigUtils.SPAWN_LOCATION_WORLD.getString().equals("null")) {
            p.sendMessage(ConfigUtils.SPAWN_MESSAGES_ERROR.getString());
            return;
        }
        p.teleport(getLocation());
    }
}
