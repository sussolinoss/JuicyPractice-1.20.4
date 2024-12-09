package dev.sussolino.juicypractice.playerdata;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ProfileManager {

    private final Map<UUID, Profile> profiles = new ConcurrentHashMap<>();

    public void createProfile(Player player) {
        UUID uuid = player.getUniqueId();

        if (this.profiles.containsKey(uuid)) return;

        this.profiles.put(uuid, new Profile(player));
    }

    public void removeProfile(Player player) {
        this.profiles.remove(player.getUniqueId());
    }

    public Profile getProfile(Player player) {
        return this.profiles.get(player.getUniqueId());
    }

    public Map<UUID, Profile> getProfileMap() {
        return this.profiles;
    }

}