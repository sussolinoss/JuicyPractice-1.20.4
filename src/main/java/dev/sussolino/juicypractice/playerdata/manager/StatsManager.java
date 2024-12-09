package dev.sussolino.juicypractice.playerdata.manager;

import dev.sussolino.juicypractice.playerdata.Profile;
import lombok.Getter;

@Getter
public class StatsManager {

    private final Profile profile;

    public StatsManager(Profile profile) {
        this.profile = profile;
    }
}
