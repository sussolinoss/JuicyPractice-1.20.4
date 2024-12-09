package dev.sussolino.juicypractice.party;

import java.util.*;

public class PartyManager {

    public final Set<UUID> PARTY_CHAT;
    private final Map<UUID, Party> PARTIES;

    public PartyManager() {
        this.PARTIES = new HashMap<>();
        this.PARTY_CHAT = new HashSet<>();
    }

    public void createParty(UUID leader) {
        if (PARTIES.containsKey(leader)) {
            throw new IllegalStateException("Il leader ha già un party...");
        }
        Party newParty = new Party(leader);
        PARTIES.put(leader, newParty);
    }

    public void disbandParty(UUID leader) {
        Party party = PARTIES.remove(leader);
        if (party != null) {
            party.disband();
        }
    }

    public Party getParty(UUID player) {
        for (Party party : PARTIES.values()) {
            if (party.isMember(player)) {
                return party;
            }
        }
        return null;
    }

    public void addPlayerToParty(UUID leader, UUID player) {
        Party party = PARTIES.get(leader);
        if (party != null) party.addMember(player);
        else {
            throw new IllegalStateException("Party non trovato");
        }
    }

    public void removePlayerFromParty(UUID player) {
        Party party = getParty(player);
        if (party != null) {
            party.removeMember(player);
            if (party.getLeader().equals(player)) {
                disbandParty(player);
            }
        }
    }
}
