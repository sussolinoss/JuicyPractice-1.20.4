package dev.sussolino.juicypractice.duels.boxing;

import dev.sussolino.juicypractice.duels.base.Duel;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.List;

public class Boxing extends Duel {

    private int hostCombo, enemyCombo;

    public Boxing(String duelName, List<Player> players) {
        super(duelName, players);
        this.feed = true;
        this.kitOnRespawn = false;
    }

    @Override
    protected void blockBreakEvent(Block block, Player p, BlockBreakEvent e) {

    }

    @Override
    protected void playerDeathEvent(Player p, PlayerDeathEvent e) {
        end(p);
    }

    @Override
    protected void playerRespawnEvent(Player p, PlayerRespawnEvent e) {

    }

    @Override
    protected void playerHitEvent(Player hitter, Player victim, EntityDamageEvent e) {

        final int counter = hitter.equals(players.get(0)) ? hostCombo : enemyCombo;

        if (this.hostCombo >= 100 || this.enemyCombo >= 100) this.end(hitter);
    }
}
