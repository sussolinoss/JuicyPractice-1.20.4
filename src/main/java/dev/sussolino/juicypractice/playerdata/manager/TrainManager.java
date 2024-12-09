package dev.sussolino.juicypractice.playerdata.manager;

import dev.sussolino.juicypractice.playerdata.Profile;
import dev.sussolino.juicypractice.utils.config.ConfigUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Zombie;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TrainManager {

    private final Profile profile;

    public TrainManager(Profile profile) {
        this.profile = profile;
    }

    private final List<Long> totemsClickDelay = new ArrayList<>();

    private long lastTotemTrain;

    private double totemTrainDuration = ConfigUtils.TRAIN_TOTEM_DURATION.getDouble();

    private boolean inTotemTrain = !totemsClickDelay.isEmpty();

    private Zombie zombie;
}
