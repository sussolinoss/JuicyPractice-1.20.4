package dev.sussolino.juicypractice.file;

import dev.sussolino.juicyapi.file.BukkitStaticFile;
import dev.sussolino.juicypractice.Bnyx;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DuelsYml extends BukkitStaticFile {

    public DuelsYml(JavaPlugin plugin) {
        super(plugin, "duels");
    }

    public static @NotNull ItemStack[] getItemStackList(@NotNull String path) {
        List<?> list = getConfig().getList(path);

        if (list == null) {
            return new ItemStack[0];
        }

        List<ItemStack> result = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof ItemStack) {
                result.add((ItemStack) object);
            }
        }
        return result.toArray(new ItemStack[0]);
    }
}

