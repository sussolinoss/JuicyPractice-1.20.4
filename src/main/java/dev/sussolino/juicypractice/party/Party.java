package dev.sussolino.juicypractice.party;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Party {

    private final UUID leader;
    private final List<UUID> members;

    public Party(UUID leader) {
        this.leader = leader;
        this.members = new ArrayList<>();
        this.members.add(leader);
    }

    public void sendPartyMessage(String message) {
        members.forEach(member -> {
            Player m = Bukkit.getPlayer(member);
            m.sendMessage(message);
        });
    }

    public void addMember(UUID player) {
        if (!members.contains(player)) {
            members.add(player);
        }
    }

    public void removeMember(UUID player) {
        members.remove(player);
    }

    public boolean isMember(UUID player) {
        return members.contains(player);
    }

    public void disband() {
        members.clear();
    }
}
