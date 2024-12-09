package dev.sussolino.juicypractice.duels.base;

import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class TimeManager extends BukkitRunnable {

    private final Duel duel;
    private int tick;

    public TimeManager(Duel duel) {
        this.duel = duel;
    }

    @Override
    public void run() {
        if (duel.state.equals(Duel.State.FINISH)) cancel();
        tick++;
    }

    public String getTime() {
        int seconds = tick % 60;
        int minutes = (tick / 60) % 60;
        int hours = (tick / 3600);

        return String.format("%dh %dm %ds", hours, minutes, seconds);
    }
}