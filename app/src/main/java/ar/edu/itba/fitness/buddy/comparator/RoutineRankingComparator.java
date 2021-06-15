package ar.edu.itba.fitness.buddy.comparator;

import java.util.Comparator;

import ar.edu.itba.fitness.buddy.model.RoutineCard;

public class RoutineRankingComparator implements Comparator<RoutineCard> {
    @Override
    public int compare(RoutineCard routineCard, RoutineCard t1) {
        return routineCard.getRating() > t1.getRating() ? 1 : (routineCard.getRating() < t1.getRating()) ? -1 : 0;
    }
}
