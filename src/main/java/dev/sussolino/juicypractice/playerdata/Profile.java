package dev.sussolino.juicypractice.playerdata;

import dev.sussolino.juicypractice.elo.Elo;
import dev.sussolino.juicypractice.playerdata.manager.StatsManager;
import dev.sussolino.juicypractice.playerdata.manager.TrainManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
public class Profile {

    private final Player player;

    private final Elo eloManager;
    private final TrainManager trainManager;
    private final StatsManager statsManager;

    public Profile(Player player) {
        this.player = player;

        this.eloManager = new Elo(player.getName());
        this.trainManager = new TrainManager(this);
        this.statsManager = new StatsManager(this);
    }
}
