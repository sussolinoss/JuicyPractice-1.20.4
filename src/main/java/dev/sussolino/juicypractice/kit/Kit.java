package dev.sussolino.juicypractice.kit;

import dev.sussolino.juicyapi.item.ItemUtils;
import dev.sussolino.juicypractice.file.KitsYml;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Kit {

    @Getter
    private final String name;
    private final FileConfiguration config;
    private final String path;

    public Kit(String name) {
        this.name = name;
        this.config = KitsYml.getConfig();
        this.path = "kits." + name;
    }

    /**
     * PRIMARY FUNCTIONS
     */

    public void save(ItemStack[] contents, ItemStack[] armor, ItemStack mainHand, ItemStack offHand) {
        if (mainHand == null) mainHand = new ItemStack(Material.AIR);
        if (offHand == null) offHand = new ItemStack(Material.AIR);

        List<ItemStack> CONTENTS = new ArrayList<>(Arrays.asList(contents));

        CONTENTS.remove(offHand);

        CONTENTS.removeAll(Arrays.asList(armor));

        this.config.set(path + ".contents", CONTENTS.toArray(new ItemStack[0]));
        this.config.set(path + ".armor", armor);
        this.config.set(path + ".mainHand", mainHand);
        this.config.set(path + ".offHand", offHand);

        reload();
    }

    public void delete() {
        this.config.set(path, null);
        reload();
    }

    /**
     * GETTERS
     */

    public ItemStack[] getContents() {
        return ItemUtils.getItemStackList(path + ".contents", config);
    }

    public ItemStack[] getArmor() {
        return ItemUtils.getItemStackList(path + ".armor", config);
    }

    public ItemStack getMainHand() {
        return this.config.getItemStack(path + ".mainHand");
    }

    public ItemStack getOffHand() {
        return this.config.getItemStack(path + ".offHand");
    }

    /**
     * OTHER
     */

    public void load(Player p) {
        p.getInventory().clear();

        ItemStack[] CONTENTS = getContents();
        ItemStack[] ARMOR = getArmor();
        ItemStack MAINHAND = getMainHand();
        ItemStack OFFHAND = getOffHand();

        p.getInventory().setArmorContents(ARMOR);
        p.getInventory().setContents(CONTENTS);

        p.getInventory().setItemInMainHand(MAINHAND);
        p.getInventory().setItemInOffHand(OFFHAND);
    }

    public boolean exist() {
        return config.contains(path);
    }

    private void reload() {
        KitsYml.reload();
    }
}
