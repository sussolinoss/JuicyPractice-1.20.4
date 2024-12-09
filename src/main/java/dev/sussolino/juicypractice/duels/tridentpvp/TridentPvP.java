package dev.sussolino.juicypractice.duels.tridentpvp;

import dev.sussolino.juicypractice.duels.base.Duel;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.List;

public class TridentPvP extends Duel {

    public TridentPvP(String duelName, List<Player> players) {
        super(duelName, players);
        this.kitOnRespawn = false;
        this.unbreakableArmor = true;
        this.allowedBlocks.addAll(List.of(Material.OBSIDIAN, Material.COBWEB));
    }

    @Override
    protected void blockBreakEvent(Block block, Player p, BlockBreakEvent e) {

    }

    @Override
    protected void playerDeathEvent(Player p, PlayerDeathEvent e) {

    }

    @Override
    protected void playerRespawnEvent(Player p, PlayerRespawnEvent e) {

    }

    @Override
    protected void playerHitEvent(Player hitter, Player victim, EntityDamageEvent e) {

    }
}
