package dev.sussolino.juicypractice.command.core;

import dev.sussolino.juicypractice.Juicy;
import dev.sussolino.juicypractice.playerdata.Profile;
import dev.sussolino.juicyapi.color.ColorUtils;
import dev.sussolino.juicyapi.reflection.abstracts.PlayerCommand;
import dev.sussolino.juicyapi.item.ItemUtils;
import dev.sussolino.juicyapi.reflection.annotations.Spartan;
import dev.sussolino.juicyapi.reflection.annotations.AntiSocial;
import io.papermc.paper.event.player.PlayerPickItemEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@AntiSocial
@Spartan
public class TrainCommand extends PlayerCommand implements Listener, TabCompleter {

    @Override
    protected void execute(Player p, String[] args) {
        if (args.length != 1) {
            //TODO HELP MESSAGE
            return;
        }

        Profile profile = Juicy.PROFILE_MANAGER.getProfile(p);

        if (args[0].equals("zombie")) {
            if (profile.getTrainManager().getZombie() == null || profile.getTrainManager().getZombie().isDead()) {

                ItemStack totem = ItemUtils.item(Material.TOTEM_OF_UNDYING, "&e&lJuice&d&lPvP" , List.of("Suck My Huge Cock"));
                totem.setAmount(64);

                Location zombieLocation = p.getLocation().clone().add(2, 0, 2);

                Zombie zombie = p.getWorld().spawn(zombieLocation, Zombie.class);

                List<Enchantment> enchants = List.of(Enchantment.DURABILITY);
                List<Enchantment> leggings_di_pelle = List.of(Enchantment.DURABILITY, Enchantment.PROTECTION_EXPLOSIONS);
                String name = "&d&lJuice&e&lPvP";

                ItemStack helmet = ItemUtils.item(Material.NETHERITE_HELMET, name, enchants, List.of());
                ItemStack chestplate = ItemUtils.item(Material.NETHERITE_CHESTPLATE, name, enchants, List.of());
                ItemStack leggings = ItemUtils.item(Material.NETHERITE_LEGGINGS, name, leggings_di_pelle, List.of());
                ItemStack boots = ItemUtils.item(Material.NETHERITE_BOOTS, name, enchants, List.of());
                ItemStack sword = ItemUtils.item(Material.NETHERITE_SWORD, name, enchants, List.of());

                EntityEquipment eq = zombie.getEquipment();

                eq.setHelmet(helmet);
                eq.setChestplate(chestplate);
                eq.setLeggings(leggings);
                eq.setBoots(boots);
                eq.setItemInMainHand(sword);

                eq.setItemInMainHand(totem, true);

                zombie.customName(Component.text(ColorUtils.color("&cLet me cook!")));
                zombie.setCustomNameVisible(true);

                profile.getTrainManager().setZombie(zombie);

                //TODO ZOMBIE_SPAWNED MESSAGE
                p.sendMessage("Il frocio è spawnato!");
            } else {
                //TODO ZOMBIE_COOLDOWN MESSAGE
                p.sendMessage("Prima uccidi il frocio, poi ne spawni un altro");
            }
        }
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) return List.of("zombie");
        return List.of();
    }
}
