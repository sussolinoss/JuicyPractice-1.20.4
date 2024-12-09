package dev.sussolino.juicypractice.duels.sword;

import dev.sussolino.juicypractice.duels.base.Duel;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.List;

public class Sword extends Duel {

    public Sword(String duelName, List<Player> players) {
        super(duelName, players);
        this.feed = true;
        this.kitOnRespawn = false;
    }

    @Override
    protected void blockBreakEvent(Block block, Player p, BlockBreakEvent e) {}

    @Override
    protected void playerDeathEvent(Player p, PlayerDeathEvent e) {}

    @Override
    protected void playerRespawnEvent(Player p, PlayerRespawnEvent e) {}

    @Override
    protected void playerHitEvent(Player hitter, Player victim, EntityDamageEvent e) {}
}
