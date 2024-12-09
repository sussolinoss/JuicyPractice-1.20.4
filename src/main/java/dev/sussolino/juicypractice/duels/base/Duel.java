package dev.sussolino.juicypractice.duels.base;

import dev.sussolino.juicyapi.world.WorldUtils;
import dev.sussolino.juicypractice.Juicy;
import dev.sussolino.juicypractice.duels.DuelsList;
import dev.sussolino.juicypractice.elo.Elo;
import dev.sussolino.juicypractice.file.DuelsYml;
import dev.sussolino.juicypractice.lobby.LobbyItems;
import dev.sussolino.juicypractice.playerdata.Profile;
import dev.sussolino.juicypractice.stats.Stats;
import dev.sussolino.juicypractice.utils.config.ConfigUtils;
import lombok.Setter;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Duel implements Listener {

    protected final String duelName;

    protected boolean kitOnRespawn;
    protected boolean feed;
    protected boolean unbreakableArmor;

    @Setter protected Player winner;
    @Setter protected List<Player> losers;

    protected final List<Player> nabbi;
    protected final List<Player> players;
    protected final List<Profile> profiles;
    protected final List<Material> allowedBlocks;

    protected final @NotNull ItemStack[] kit;
    protected final @NotNull ItemStack[] armor;

    protected ItemStack offhand;

    protected final World world;

    protected final TimeManager timeManager;
    protected final ScoreBoard scoreBoard;
    protected final Actions actions;
    protected final StartManager startManager;

    protected State state;

    public Duel(String duelName, List<Profile> profiles) {
        final int count = switch (duelName) {
            case "sword" -> DuelsList.SWORD++;
            case "nethpot" -> DuelsList.NETHPOT++;
            case "diapot" -> DuelsList.DIAPOT++;
            case "cpvp" -> DuelsList.CPVP++;
            case "smp" -> DuelsList.SMP++;
            case "tridentpvp" -> DuelsList.TRIDENT_PVP++;
            case "boxpvp" -> DuelsList.BOXPVP++;
            default -> 0;
        };

        // Duel's stuff
        this.duelName = duelName;
        this.kitOnRespawn = true;

        this.nabbi = new ArrayList<>();
        this.losers = new ArrayList<>();
        this.allowedBlocks = new ArrayList<>();
        this.profiles = new ArrayList<>(profiles);
        this.players = profiles.stream().map(Profile::getPlayer).toList();

        DuelsList.PLAYERS_IN_DUEL.addAll(this.players);

        //-------------------------------------------------------------------------------------
        this.armor = DuelsYml.getItemStackList(duelName + ".kit.armor");

        ItemStack[] CONTENTS = DuelsYml.getItemStackList(duelName + ".kit.contents");

        Set<ItemStack> armorSet = Arrays.stream(armor).collect(Collectors.toSet());

        List<ItemStack> filteredKit = Arrays.stream(CONTENTS)
                .filter(itemStack -> !armorSet.contains(itemStack))
                .toList();

        this.kit = filteredKit.toArray(new ItemStack[0]);

        if (DuelsYml.getConfig().getItemStack(duelName + ".kit.offhand") != null) {
            this.offhand = DuelsYml.getConfig().getItemStack(duelName + ".kit.offhand");
        }
        //-------------------------------------------------------------------------------------

        this.world = WorldUtils.copyWorld(WorldUtils.getWorld(DuelsYml.getConfig().getString(duelName + ".world")), duelName + "_" + count);
        this.world.setTime(200);
        this.world.setDifficulty(Difficulty.HARD);
        this.world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        this.world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        this.world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        this.world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);

        // Managers
        this.timeManager = new TimeManager(this);
        this.scoreBoard = new ScoreBoard(this);
        this.actions = new Actions(this);
        this.startManager = new StartManager(this);

        Bukkit.getPluginManager().registerEvents(startManager, Juicy.INSTANCE);

        this.setState(State.STARTING);
    }

    protected void setState(State state) {
        switch (state) {
            case FINISH:
                finish();
                break;
            case RUNNING:
                running();
                break;
            case STARTING:
                starting();
                break;
        }
        this.state = state;
    }

    protected void running() {
        timeManager.runTaskTimer(Juicy.INSTANCE, 20L, 20L);
    }

    protected void starting() {
        Juicy.INSTANCE.getServer().getPluginManager().registerEvents(this, Juicy.INSTANCE);

        actions.myLifeMyRules();

        players.forEach(p -> {
            final TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(p.getUniqueId());

            if (TabAPI.getInstance().getScoreboardManager() != null) {

                assert tabPlayer != null;

                TabAPI.getInstance().getScoreboardManager().toggleScoreboard(tabPlayer, false);
            }
        });

        scoreBoard.run();

        actions.teleportArena();
        actions.giveKits();

        startManager.runTaskTimer(Juicy.INSTANCE, 20, 20);
    }

    protected void finish() {
        Bukkit.getScheduler().runTaskLater(Juicy.INSTANCE, () -> {

            // DRITTO IN LOBBY!
            actions.teleportLobby();

            players.forEach(LobbyItems::giveLobbyItems);

            // RESULT
            Stats winnerStats = new Stats(duelName, winner.getName());
            winnerStats.increment(Stats.StatsType.WINS, 1);
            winnerStats.increment(Stats.StatsType.PLAYED, 1);

            // ELO
            final Elo WINNER_ELO = new Elo(winner.getName());
            final double WINNER_ELO_OLD = WINNER_ELO.getElo();

            for (Player loser : losers) {
                final Elo LOSER_ELO = new Elo(loser.getName());
                final double LOSER_ELO_OLD = LOSER_ELO.getElo();

                WINNER_ELO.win(LOSER_ELO_OLD);
                LOSER_ELO.lose(WINNER_ELO_OLD);
            }

            // handle lose
            losers.forEach(loser -> {
                Stats loserStats = new Stats(duelName, loser.getName());
                loserStats.increment(Stats.StatsType.LOSES, 1);
                loserStats.increment(Stats.StatsType.PLAYED, 1);
                loser.sendTitle(ConfigUtils.DUELS_TITLE_LOSER.getString(), ConfigUtils.DUELS_SUBTITLE_LOSER.getString());
            });

            winner.sendTitle(ConfigUtils.DUELS_TITLE_WINNER.getString(), ConfigUtils.DUELS_SUBTITLE_WINNER.getString());

            // Unregister events
            HandlerList.unregisterAll(startManager);
            HandlerList.unregisterAll(this);

            DuelsList.PLAYERS_IN_DUEL.removeAll(players);
            WorldUtils.unloadWorld(world);

            // ScoreBoard
            players.forEach(p -> {
                TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(p.getUniqueId());
                TabAPI.getInstance().getScoreboardManager().toggleScoreboard(tabPlayer, true);
            });

            nabbi.clear();
            players.clear();
        }, 5);
    }

    protected boolean inDuel(Player p) {
        return players.contains(p);
    }

    protected void end(Player p) {
        winner = p;
        losers = nabbi;
        setState(State.FINISH);
    }

    /**
     *
     *     EVENTS
     *
     */
    @EventHandler
    public void onTritolo(BlockExplodeEvent e) {
        if (!allowedBlocks.contains(e.getBlock().getType())) e.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player player) {
            if (inDuel(player) && unbreakableArmor) repairArmor(player);
        }
    }


    @EventHandler
    public void onItemBreaking(PlayerItemDamageEvent e) {
        Player p = e.getPlayer();
        if (inDuel(p)) {
            ItemStack item = e.getItem();

            if (item.equals(offhand)) e.setCancelled(true);
            else if (Arrays.stream(kit).toList().contains(item)) e.setCancelled(true);
            else if (unbreakableArmor) {
                if (Arrays.stream(armor).toList().contains(item)) e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSendCommand(PlayerCommandPreprocessEvent e) {
        if (inDuel(e.getPlayer())) e.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (inDuel(p)) {
            nabbi.add(p);
            this.end(getWinner());
        }
    }

    @EventHandler
    public void africa(FoodLevelChangeEvent e) {
        if (feed) e.setFoodLevel(20);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (inDuel(e.getPlayer())) {
            Block block = e.getBlock();
            if (!allowedBlocks.contains(block.getType())) e.setCancelled(true);
            blockBreakEvent(e.getBlock(), e.getPlayer(), e);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();

        if (inDuel(p)) {
            e.getDrops().clear();
            e.setNewTotalExp(0);

            if (kitOnRespawn) {
                p.getInventory().addItem(kit);
                if (offhand == null) p.getInventory().setItemInOffHand(offhand);
                p.getInventory().setArmorContents(armor);
            }
            else {
                nabbi.add(p);

                if (nabbi.size() < players.size() - 1) {
                    Location deathLoc = p.getLocation();

                    p.spigot().respawn();

                    p.setGameMode(GameMode.SPECTATOR);
                    p.sendMessage("Sei schiattato");
                    //TODO DUEAL_DEATH
                    p.teleport(deathLoc);
                    return;
                }
                this.end(getWinner());
            }
            playerDeathEvent(p, e);
        }
    }


    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        if (inDuel(e.getPlayer())) playerRespawnEvent(e.getPlayer(), e);
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player hitter && e.getEntity() instanceof Player victim) {
            if (inDuel(hitter) && inDuel(victim)) playerHitEvent(hitter, victim, e);
        }
    }

    /**
     *
     *   SOME STUFF
     *
     */

    private void repairArmor(Player player) {
        ItemStack[] armorContents = player.getInventory().getArmorContents();
        Arrays.stream(armorContents).filter(item -> item != null && item.getType() != Material.AIR).forEach(item -> item.setDurability((short) 0));
    }


    private Player getWinner() {
        return players.stream().filter(win -> !nabbi.contains(win)).findFirst().orElse(null);
    }

    protected abstract void blockBreakEvent(Block block, Player p, BlockBreakEvent e);
    protected abstract void playerDeathEvent(Player p, PlayerDeathEvent e);
    protected abstract void playerRespawnEvent(Player p, PlayerRespawnEvent e);
    protected abstract void playerHitEvent(Player hitter, Player victim, EntityDamageEvent e);

    protected enum State {
        STARTING,
        RUNNING,
        FINISH
    }
}