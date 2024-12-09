package dev.sussolino.juicypractice.kit;

import dev.sussolino.juicypractice.file.KitsYml;
import dev.sussolino.juicyapi.item.ItemUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnderKit {

    private final String name;
    private final FileConfiguration config;
    private final String path;

    public EnderKit(String name) {
        this.name = name;
        this.config = KitsYml.getConfig();
        this.path = "ec." + name;
    }

    public void save(ItemStack[] contents) {
        this.config.set(path, contents);
        reload();
    }

    public void delete() {
        this.config.set(path, null);
        reload();
    }

    public void load(Player p) {
        p.getEnderChest().clear();

        ItemStack[] CONTENTS = ItemUtils.getItemStackList(name, config);

        p.getEnderChest().setContents(CONTENTS);
    }



    private void reload() {
        KitsYml.reload();
    }
}
