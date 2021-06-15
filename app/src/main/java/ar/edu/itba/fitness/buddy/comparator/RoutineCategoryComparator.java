package ar.edu.itba.fitness.buddy.comparator;

import java.util.Comparator;

import ar.edu.itba.fitness.buddy.model.RoutineCard;

public class RoutineCategoryComparator implements Comparator<RoutineCard> {
    @Override
    public int compare(RoutineCard routineCard, RoutineCard t1) {
        return routineCard.getCategory().compareTo(t1.getCategory());
    }
}
