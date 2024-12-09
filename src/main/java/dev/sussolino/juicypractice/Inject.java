package dev.sussolino.juicypractice;

import dev.sussolino.juicyapi.reflection.annotations.Spartan;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.sound.sampled.Line;
import java.util.Objects;

@Spartan
public class Inject implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(p.getUniqueId());

        if (tabPlayer == null) return;

        TabAPI.getInstance().getScoreboardManager().toggleScoreboard(tabPlayer, true);
        TabAPI.getInstance().getTabListFormatManager().setSuffix(tabPlayer, "");
    }

    @EventHandler
    public void onChangeFFA(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        World w = p.getWorld();

        TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(p.getUniqueId());

        String FFA_NETHPOT = "nethpot";
        String FFA_DIAPOT = "diapot";
        String FFA_SMP = "smp";
        String FFA_SWORD = "sword";
        String FFA_TRIDENT = "";
        String FFA_CPVP = "cpvp";

        String type = switch (w.getName()) {
            case "FFA-NethPot" -> FFA_NETHPOT;
            case "FFA-DiaPot" -> FFA_DIAPOT;
            case "FFA-SMP" -> FFA_SMP;
            case "FFA-Sword" -> FFA_SWORD;
            case "FFA-CPvP" -> FFA_CPVP;
            default -> FFA_TRIDENT;
        };

        assert tabPlayer != null;

        if (type.equals(FFA_TRIDENT)) {
            TabAPI.getInstance().getTabListFormatManager().setSuffix(tabPlayer, "");
            return;
        }

        String suffix = "&e[&6%TierList_" + type + "%&e]";

        TabAPI.getInstance().getTabListFormatManager().setSuffix(tabPlayer, suffix);
    }
}
