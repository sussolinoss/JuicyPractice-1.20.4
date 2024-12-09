package dev.sussolino.juicypractice.duels.base;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;


import static dev.sussolino.juicyapi.color.ColorUtils.color;
import static dev.sussolino.juicypractice.duels.base.Duel.State.RUNNING;

public class StartManager extends BukkitRunnable implements Listener {

    private final Duel duel;

    public StartManager(Duel duel) {
        this.duel = duel;
        this.PRE_START.addAll(duel.players);
    }

    private final Set<Player> PRE_START = new HashSet<>();
    private int delay = 5;


    @EventHandler
    public void hitDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;
        if (PRE_START.contains(p)) e.setCancelled(true);
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!PRE_START.contains(p)) return;
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) && p.getItemInHand().getType().equals(Material.ENDER_PEARL)) e.setCancelled(true);
    }

    @Override
    public void run() {

        delay--;

        duel.players.forEach(p -> p.sendMessage(color("&a" + delay  + "&2s &fallo start")));

        if (delay <= 0) {
            PRE_START.clear();
            duel.players.forEach(p -> p.sendTitle(color("&a&lVIA&f!"), ""));
            duel.setState(RUNNING);
            cancel();
        }
    }
}
