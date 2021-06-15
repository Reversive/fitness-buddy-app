package ar.edu.itba.fitness.buddy.comparator;

import java.util.Comparator;

import ar.edu.itba.fitness.buddy.model.RoutineCard;

public class RoutineTitleComparator implements Comparator<RoutineCard> {
    @Override
    public int compare(RoutineCard lhs, RoutineCard rhs) {
        return lhs.getTitle().compareTo(rhs.getTitle());
    }
}
