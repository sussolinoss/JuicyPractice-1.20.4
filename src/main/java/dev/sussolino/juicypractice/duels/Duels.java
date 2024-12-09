package dev.sussolino.juicypractice.duels;

import dev.sussolino.juicypractice.file.DuelsYml;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class Duels {
    
    private final String path;
    private final FileConfiguration config;

    public Duels(String name) {
        this.path = name;
        this.config = DuelsYml.getConfig();
    }

    public void setPosition(int type, Location location) {
        String path = this.path + ".pos" + type;
        setLocation(path, location);
        DuelsYml.reload();
    }

    public void removeKit() {
        config.set(path + ".kit", null);
        DuelsYml.reload();
    }
    public void setKit(ItemStack[] contents, ItemStack[] armor, ItemStack offhand) {
/*
        List<ItemStack> contentsList = Arrays.asList(contents);
        Arrays.stream(contents).forEach(item -> {
            if (item == null) item.setType(Material.AIR);
            contentsList.remove(item);
        });

        contents = contentsList.toArray(new ItemStack[0]);
 */

        config.set(path + ".kit.contents", contents);
        config.set(path + ".kit.offhand", offhand);
        config.set(path + ".kit.armor", armor);
        DuelsYml.reload();
    }

    public void setLobby(Location location) {
        String path = this.path + ".lobby";
        setLocation(path, location);
        DuelsYml.reload();
    }

    public void removeLobby() {
        config.set(path + ".lobby", null);
        DuelsYml.reload();
    }

    private void setLocation(String path, Location location) {
        config.set(path + ".world", location.getWorld().getName());
        config.set(path + ".x", location.getX());
        config.set(path + ".y", location.getY());
        config.set(path + ".z", location.getZ());
        config.set(path + ".yaw", location.getYaw());
        config.set(path + ".pitch", location.getPitch());
    }

    public void setWorld(String name) {
        config.set(path + ".world", name);
        DuelsYml.reload();
    }
    public void removeWorld() {
        config.set(path + ".world", null);
        DuelsYml.reload();
    }

    public void setGlobalPosition(Location location) {
        String path = this.path + ".global-position";
        setLocation(path, location);
        DuelsYml.reload();
    }
}
