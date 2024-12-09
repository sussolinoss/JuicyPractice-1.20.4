package dev.sussolino.juicypractice.party;

import dev.sussolino.juicypractice.Juicy;
import dev.sussolino.juicypractice.utils.config.ConfigUtils;
import dev.sussolino.juicyapi.reflection.annotations.Spartan;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@Spartan
public class PartyListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        var manager =  Juicy.PARTY_MANAGER;

        if (!manager.PARTY_CHAT.contains(e.getPlayer().getUniqueId())) return;

        e.setCancelled(true);

        Party party = manager.getParty(p.getUniqueId());

        if (party == null) return;

        party.getMembers().forEach(m -> {
            Player member = Bukkit.getPlayer(m);
            member.sendMessage(ConfigUtils.PARTY_CHAT_FORMAT.getString()
                    .replace("{player}", p.getName()
                    .replace("{message}", e.getMessage().toLowerCase())));
        } );
    }
}
