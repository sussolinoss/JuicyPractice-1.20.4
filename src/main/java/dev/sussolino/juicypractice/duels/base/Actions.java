package dev.sussolino.juicypractice.duels.base;

import dev.sussolino.juicypractice.utils.location.LocationUtil;
import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@Getter
public class Actions {

    private final Duel duel;

    private final ItemStack[] kit;
    private final ItemStack[] armor;

    private final Location lobby;
    private final Location pos1;
    private final Location pos2;
    private final Location posGlobal;

    private final List<Player> players;

    public Actions(Duel duel) {
        this.duel = duel;

        this.kit = duel.kit;
        this.armor = duel.armor;

        String path = duel.duelName + ".";
        this.lobby = LocationUtil.get(path + "lobby.");

        this.pos1 = LocationUtil.get(path + "pos1.");
        this.pos1.setWorld(duel.world);

        this.posGlobal = LocationUtil.get(path + "global-position.");
        this.posGlobal.setWorld(duel.world);

        this.pos2 = LocationUtil.get(path + "pos2.");
        this.pos2.setWorld(duel.world);

        this.players = duel.players;
    }

    protected void myLifeMyRules() {
        players.forEach(p -> {
            p.clearActivePotionEffects();
            p.setGameMode(GameMode.SURVIVAL);
            p.setHealth(20);
            p.setFoodLevel(20);
            p.setAllowFlight(false);
            p.setFlying(false);
            p.setPlayerWeather(WeatherType.CLEAR);
            p.setPlayerTime(0, true);
        });
    }
    protected void giveKits() {
        players.forEach(player -> {
            player.getInventory().clear();
            player.getInventory().addItem(kit);
            player.getInventory().setArmorContents(armor);

            if (duel.offhand != null) player.getInventory().setItemInOffHand(duel.offhand);

            if (Arrays.stream(player.getInventory().getContents()).toList().contains(duel.offhand)) {
                player.getInventory().remove(duel.offhand);
            }
        });
    }

    protected void teleportLobby() {
        players.forEach(player -> {
            player.getInventory().clear();
            player.teleport(lobby);
        });
    }

    protected void teleportArena() {
        if (players.size() == 2) {
            players.get(0).teleport(pos1);
            players.get(1).teleport(pos2);
        }
        else {
            players.forEach(player -> player.teleport(posGlobal));
        }
    }
}
