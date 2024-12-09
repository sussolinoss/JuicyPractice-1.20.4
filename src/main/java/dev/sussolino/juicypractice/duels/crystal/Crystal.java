package dev.sussolino.juicypractice.duels.crystal;

import dev.sussolino.juicypractice.duels.base.Duel;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Arrays;
import java.util.List;

public class Crystal extends Duel {

    public Crystal(String duelName, List<Player> players) {
        super(duelName, players);
        this.kitOnRespawn = false;
        this.allowedBlocks.addAll(
                Arrays.asList(
                Material.OBSIDIAN, Material.RESPAWN_ANCHOR,
                Material.GLOWSTONE,
                Material.SAND, Material.GRASS_BLOCK, Material.DIRT, Material.STONE, Material.COBBLESTONE, Material.ANDESITE));
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
