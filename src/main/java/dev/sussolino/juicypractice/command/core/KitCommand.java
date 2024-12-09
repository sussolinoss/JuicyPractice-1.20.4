/*package dev.sussolino.juicypractice.command.core;

import dev.sussolino.juicypractice.file.KitsFile;
import dev.sussolino.juicypractice.kit.Kit;
import dev.sussolino.juicyapi.reflection.abstracts.PlayerCommand;
import dev.sussolino.juicyapi.item.ItemUtils;
import dev.sussolino.juicyapi.reflection.annotations.Spartan;
import dev.sussolino.juicyapi.reflection.annotations.AntiSocial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@AntiSocial
@Spartan
public class KitCommand extends PlayerCommand implements Listener, TabCompleter {

    //kit save/delete/view/load [nome]



    @Override
    protected void execute(Player p, String[] args) {
        if (args.length < 2) {
            //TODO KIT_HELP MESSAGE
            p.sendMessage("kit save/delete/view/load [name]");
            return;
        }
        if (args.length == 2) {
            PlayerInventory inv = p.getInventory();

            String cmd = args[0].toLowerCase();
            Kit kit = new Kit(args[1]);

            switch (cmd) {
                case "save" -> {
                    kit.save(inv.getContents(), inv.getArmorContents(), p.getItemInHand() , inv.getItemInOffHand());
                    p.sendMessage("Kit saved as " + kit.getName());
                    //TODO KIT_SAVE MESSAGE
                }
                case "delete" -> {
                    if (!kit.exist()) {
                        //TODO KIT_ERROR MESSAGE
                        p.sendMessage("Questo kit porca madonna non esiste");
                        return;
                    }
                    kit.delete();
                    p.sendMessage("Kit deleted!");
                }
                case "view" -> {
                    // TODO REPLACE THE "kit.getName()" with the format KIT_TITLE
                    Inventory kitPreview = Bukkit.createInventory(p, 54, "KIT_TITLE - " + kit.getName());
                    ItemStack pane = ItemUtils.item(0, Material.PURPLE_STAINED_GLASS_PANE, "&e&lJuice&d&lPvP");

                    ItemStack[] armor = kit.getArmor();
                    ItemStack[] contents = kit.getContents();
                    ItemStack mainHand = kit.getMainHand();
                    ItemStack offHand = kit.getOffHand();

                    // Set panes in fixed positions
                    for (int i = 0; i < 9; i++) {
                        kitPreview.setItem(i, pane);
                        kitPreview.setItem(45 + i, pane);
                    }

                    kitPreview.setItem(45, armor[0]); // Boots
                    kitPreview.setItem(46, armor[1]); // Leggings
                    kitPreview.setItem(47, armor[2]); // Chestplate
                    kitPreview.setItem(48, armor[3]); // Helmet

                    kitPreview.setItem(49, mainHand); // Main hand
                    kitPreview.setItem(50, offHand);  // Off hand

                    int index = 9;
                    for (ItemStack item : contents) {
                        if (item != null) {
                            kitPreview.setItem(index, item);
                            index++;
                            if (index >= 45) break;
                        }
                    }

                    p.openInventory(kitPreview);
                }

                case "load" -> {
                    if (!kit.exist()) {
                        //TODO KIT_ERROR MESSAGE
                        p.sendMessage("Questo kit porca madonna non esiste");
                        return;
                    }
                    kit.load(p);
                    p.sendMessage("Kit loaded!");
                    //TODO KIT_LOAD MESSAGE
                }
            }
        }
    }

    @Spartan
    public void onPreview(InventoryClickEvent e) {}


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> TAB = new ArrayList<>();

        switch (strings.length) {
            case 1 -> {
                TAB.addAll(List.of("save", "delete", "view", "load"));
            }
            case 2 -> {
              if (!strings[0].equals("save")) TAB.addAll(KitsFile.getKits());
            }
        }
        return TAB;
    }
}
*/