package dev.sussolino.juicypractice.zones;

import dev.sussolino.juicyapi.reflection.annotations.Spartan;
import dev.sussolino.juicypractice.utils.config.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.List;

@Spartan
public class ZonesListener implements Listener {

    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        String world = p.getWorld().getName();

        List<String> list = ConfigUtils.ZONES.getStringList();

        if (list.contains(world)) {

            String command = "kit load " + world.toLowerCase();

            p.getInventory().clear();

            Bukkit.getServer().dispatchCommand(p, command);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();

        //Ho il bypass si è cosi'
        if (p.getName().equals("sussolino")) return;

        List<String> list = ConfigUtils.ZONES.getStringList();

        if (list.contains(p.getWorld().getName())) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onAfrica(FoodLevelChangeEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;

        List<String> list = ConfigUtils.ZONES.getStringList();

        if (list.contains(p.getWorld().getName())) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onFall(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;

        if (!e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) return;

        List<String> list = ConfigUtils.ZONES.getStringList();

        if (list.contains(p.getWorld().getName())) {
            e.setCancelled(true);
        }
    }
}
