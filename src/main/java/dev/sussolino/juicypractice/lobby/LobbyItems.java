package dev.sussolino.juicypractice.lobby;

import dev.sussolino.juicyapi.color.ColorUtils;
import dev.sussolino.juicypractice.duels.crystal.Crystal;
import dev.sussolino.juicypractice.duels.diapot.DiaPot;
import dev.sussolino.juicypractice.duels.nethpot.NethPot;
import dev.sussolino.juicypractice.duels.smp.Smp;
import dev.sussolino.juicypractice.duels.sword.Sword;
import dev.sussolino.juicypractice.duels.tridentpvp.TridentPvP;
import dev.sussolino.juicyapi.item.ItemUtils;
import dev.sussolino.juicyapi.reflection.annotations.Spartan;
import dev.sussolino.juicypractice.utils.config.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Spartan
public class LobbyItems implements Listener {

    private static final ItemStack PUBLIC_DUELS_ITEM = ItemUtils.item(1, Material.DIAMOND_SWORD, ConfigUtils.INVENTORY.getString());
    private static final ItemStack RANKED_DUELS_ITEM = ItemUtils.item(1, Material.NETHERITE_SWORD, ConfigUtils.INVENTORY.getString());

    private final Queue<Player> SWORD_UNRANKED_QUEUE = new LinkedList<>();
    private final Queue<Player> NETHPOT_UNRANKED_QUEUE = new LinkedList<>();
    private final Queue<Player> DIAPOT_UNRANKED_QUEUE = new LinkedList<>();
    private final Queue<Player> SMP_UNRANKED_QUEUE = new LinkedList<>();
    private final Queue<Player> CPVP_UNRANKED_QUEUE = new LinkedList<>();
    private final Queue<Player> TRIDENT_UNRANKED_QUEUE = new LinkedList<>();

    private final Queue<Player> SWORD_RANKED_QUEUE = new LinkedList<>();
    private final Queue<Player> NETHPOT_RANKED_QUEUE = new LinkedList<>();
    private final Queue<Player> DIAPOT_RANKED_QUEUE = new LinkedList<>();
    private final Queue<Player> SMP_RANKED_QUEUE = new LinkedList<>();
    private final Queue<Player> CPVP_RANKED_QUEUE = new LinkedList<>();
    private final Queue<Player> TRIDENT_RANKED_QUEUE = new LinkedList<>();

    private final String UNRANKED_INVENTORY_TITLE = "&eUnranked &7- &6Queue";
    private final String RANKED_INVENTORY_TITLE = "&eRanked &7- &6Queue";

    private final ItemStack SWORD = ItemUtils.item(1, Material.DIAMOND_SWORD, "&bSword");
    private final ItemStack NETHPOT = ItemUtils.item(1, Material.NETHERITE_HELMET, "&8NethPot");
    private final ItemStack DIAPOT = ItemUtils.item(1, Material.DIAMOND_HELMET, "&3DiaPot");
    private final ItemStack SMP = ItemUtils.item(1, Material.NETHERITE_SWORD, "&cSMP");
    private final ItemStack CPVP = ItemUtils.item(1, Material.END_CRYSTAL, "&dVanilla");
    private final ItemStack TRIDENT = ItemUtils.item(1, Material.TRIDENT, "&bTrident &3PvP");

    //TODO ADD COLOR UTILS

    public Inventory unranked() {
        Inventory inv = Bukkit.createInventory(null, 9, UNRANKED_INVENTORY_TITLE);
        inv.addItem(SWORD, NETHPOT, DIAPOT, SMP, CPVP, TRIDENT);
        return inv;
    }

    public Inventory ranked() {
        Inventory inv = Bukkit.createInventory(null, 9, RANKED_INVENTORY_TITLE);
        inv.addItem(SWORD, NETHPOT, DIAPOT, SMP, CPVP, TRIDENT);
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        String INVENTORY_TITLE = e.getView().getTitle();

        ItemStack currentItem = e.getCurrentItem();

        if (currentItem == null) return;

        String duel = "";

        //---- Ugly "Switch" ----
        if (currentItem.equals(SWORD)) duel = "sword";
        else if (currentItem.equals(NETHPOT)) duel = "nethpot";
        else if (currentItem.equals(DIAPOT)) duel = "diapot";
        else if (currentItem.equals(SMP)) duel = "smp";
        else if (currentItem.equals(CPVP)) duel = "cpvp";
        else if (currentItem.equals(TRIDENT)) duel = "tridentpvp";
        //---- Ugly "Switch" ----

        if (duel.isEmpty()) return;

        //----- HANDLER ------ ----- HANDLER ------ ----- HANDLER ------
        if (INVENTORY_TITLE.equals(UNRANKED_INVENTORY_TITLE)) {
            add(p, duel, false);

            e.setCancelled(true);
        }
        else if (INVENTORY_TITLE.equals(RANKED_INVENTORY_TITLE)) {
            add(p, duel, true);

            e.setCancelled(true);
        }
        //----- HANDLER ------ ----- HANDLER ------ ----- HANDLER ------
    }


    private void add(Player p, String duel, boolean ranked) {
        //removeFromQueues(p);

        switch (duel.toLowerCase()) {
            case "sword":
                if (ranked) SWORD_RANKED_QUEUE.add(p);
                else SWORD_UNRANKED_QUEUE.add(p);
                break;
            case "nethpot":
                if (ranked) NETHPOT_UNRANKED_QUEUE.add(p);
                else NETHPOT_RANKED_QUEUE.add(p);
                break;
            case "diapot":
                if (ranked) DIAPOT_UNRANKED_QUEUE.add(p);
                else DIAPOT_RANKED_QUEUE.add(p);
                break;
            case "smp":
                if (ranked) SMP_UNRANKED_QUEUE.add(p);
                else SMP_RANKED_QUEUE.add(p);
                break;
            case "cpvp":
                if (ranked) CPVP_UNRANKED_QUEUE.add(p);
                else CPVP_RANKED_QUEUE.add(p);
                break;
            case "tridentpvp":
                if (ranked) TRIDENT_UNRANKED_QUEUE.add(p);
                else TRIDENT_RANKED_QUEUE.add(p);
                break;
        }
        p.sendMessage("Added in queue - " + duel);

        start(p, duel, ranked);
    }

    public void start(Player p, String duel, boolean ranked) {
        Player victim;
        switch (duel.toLowerCase()) {
            case "sword":
                if (ranked) victim = SWORD_RANKED_QUEUE.peek();
                else victim = SWORD_UNRANKED_QUEUE.peek();

                if (victim != null && !victim.getUniqueId().equals(p.getUniqueId())) {
                    new Sword(duel, List.of(p, victim));

                    if (ranked) SWORD_RANKED_QUEUE.peek();
                    else SWORD_UNRANKED_QUEUE.peek();
                }
                break;
            case "nethpot":
                if (ranked) victim = NETHPOT_RANKED_QUEUE.peek();
                else victim = NETHPOT_UNRANKED_QUEUE.peek();


                if (victim != null && !victim.getUniqueId().equals(p.getUniqueId())) {
                    new NethPot(duel, List.of(p, victim));

                    if (ranked) NETHPOT_RANKED_QUEUE.peek();
                    else NETHPOT_UNRANKED_QUEUE.peek();
                }
                break;
            case "diapot":
                if (ranked) victim = DIAPOT_RANKED_QUEUE.peek();
                else victim = DIAPOT_UNRANKED_QUEUE.peek();

                if (victim != null && !victim.getUniqueId().equals(p.getUniqueId())) {
                    new DiaPot(duel, List.of(p, victim));

                    if (ranked) DIAPOT_RANKED_QUEUE.peek();
                    else DIAPOT_UNRANKED_QUEUE.peek();
                }
                break;
            case "smp":
                if (ranked) victim = SMP_RANKED_QUEUE.peek();
                else victim = SMP_UNRANKED_QUEUE.peek();

                if (victim != null && !victim.getUniqueId().equals(p.getUniqueId())) {
                    new Smp(duel, List.of(p, victim));

                    if (ranked) SMP_RANKED_QUEUE.peek();
                    else SMP_UNRANKED_QUEUE.peek();
                }
                break;
            case "cpvp":
                if (ranked) victim = CPVP_RANKED_QUEUE.peek();
                else victim = CPVP_UNRANKED_QUEUE.peek();

                if (victim != null && !victim.getUniqueId().equals(p.getUniqueId())) {
                    new Crystal(duel, List.of(p, victim));

                    if (ranked) CPVP_RANKED_QUEUE.peek();
                    else CPVP_UNRANKED_QUEUE.peek();
                }
                break;
            case "tridentpvp":
                if (ranked) victim = TRIDENT_RANKED_QUEUE.peek();
                else victim = TRIDENT_UNRANKED_QUEUE.peek();

                if (victim != null && !victim.getUniqueId().equals(p.getUniqueId())) {
                    new TridentPvP(duel, List.of(p, victim));

                    if (ranked) TRIDENT_RANKED_QUEUE.peek();
                    else TRIDENT_UNRANKED_QUEUE.peek();
                }
                break;
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        giveLobbyItems(p);
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        removeFromQueues(p);
    }

    @EventHandler
    public void onHitLobbyItems(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();

        if (item == null) return;

        if (item.equals(PUBLIC_DUELS_ITEM)) {
            e.setCancelled(true);
            p.openInventory(unranked());
        }
        else if (item.equals(RANKED_DUELS_ITEM)) {
            e.setCancelled(true);
            p.openInventory(ranked());
        }
    }

    public static void giveLobbyItems(Player p) {
        p.getInventory().clear();
        p.getInventory().setItem(5, RANKED_DUELS_ITEM);
        p.getInventory().setItem(3, PUBLIC_DUELS_ITEM);
    }

    public boolean removeFromQueues(Player p) {
        DIAPOT_RANKED_QUEUE.remove(p);
        NETHPOT_RANKED_QUEUE.remove(p);
        SMP_RANKED_QUEUE.remove(p);
        CPVP_RANKED_QUEUE.remove(p);
        TRIDENT_RANKED_QUEUE.remove(p);
        SWORD_RANKED_QUEUE.remove(p);

        DIAPOT_UNRANKED_QUEUE.remove(p);
        NETHPOT_UNRANKED_QUEUE.remove(p);
        SMP_UNRANKED_QUEUE.remove(p);
        CPVP_UNRANKED_QUEUE.remove(p);
        TRIDENT_UNRANKED_QUEUE.remove(p);
        SWORD_UNRANKED_QUEUE.remove(p);
        DIAPOT_RANKED_QUEUE.remove(p);
        return
                DIAPOT_UNRANKED_QUEUE.contains(p) ||
                NETHPOT_UNRANKED_QUEUE.contains(p) ||
                SMP_UNRANKED_QUEUE.contains(p) ||
                CPVP_UNRANKED_QUEUE.contains(p) ||
                TRIDENT_UNRANKED_QUEUE.contains(p) ||
                SWORD_UNRANKED_QUEUE.contains(p) ||

                DIAPOT_RANKED_QUEUE.contains(p) ||
                NETHPOT_RANKED_QUEUE.contains(p) ||
                SMP_RANKED_QUEUE.contains(p) ||
                CPVP_RANKED_QUEUE.contains(p) ||
                TRIDENT_RANKED_QUEUE.contains(p) ||
                SWORD_RANKED_QUEUE.contains(p);
    }


    private void log(String duel, int size, String playerName) {
        Bukkit.getLogger().severe(duel + " (" + size + " - " + playerName + ")");
    }
}
