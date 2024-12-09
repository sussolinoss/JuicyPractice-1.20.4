package dev.sussolino.juicypractice;

import dev.sussolino.juicypractice.duels.base.Duel;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class DuelsAPI {

    public static Set<Duel> duels = new LinkedHashSet<>();

    public void add(Duel... duel) {
        duels.addAll(Arrays.asList(duel));
    }
    public void remove(Duel... duel) {
        Arrays.asList(duel).forEach(duels::remove);
    }
}
