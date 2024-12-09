package dev.sussolino.juicypractice.playerdata.listener;

import dev.sussolino.juicyapi.reflection.annotations.Spartan;
import dev.sussolino.juicypractice.Juicy;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Spartan
public class ProfileInjectListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Juicy.PROFILE_MANAGER.createProfile(event.getPlayer());
    }
}
