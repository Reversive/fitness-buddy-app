package ar.edu.itba.fitness.buddy.comparator;

import java.util.Comparator;

import ar.edu.itba.fitness.buddy.model.RoutineCard;

public class RoutineDifficultyComparator implements Comparator<RoutineCard> {
    @Override
    public int compare(RoutineCard routineCard, RoutineCard t1) {
        return routineCard.getDifficulty().compareTo(t1.getDifficulty());
    }
}
