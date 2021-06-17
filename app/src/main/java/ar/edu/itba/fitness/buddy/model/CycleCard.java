package ar.edu.itba.fitness.buddy.model;

import androidx.lifecycle.LifecycleOwner;

import java.util.ArrayList;
import java.util.function.Consumer;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.api.model.Cycle;
import ar.edu.itba.fitness.buddy.api.model.Exercise;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import ar.edu.itba.fitness.buddy.api.repository.Resource;
import ar.edu.itba.fitness.buddy.api.repository.Status;

public class CycleCard {
    private FullCycle cycle;
    private final ArrayList<ExerciseCard> exerciseCards =new ArrayList<>();

    public CycleCard(FullCycle cycle) {

        this.cycle=cycle;
    }

    public ArrayList<Exercise> getExercises() {
        return this.cycle.getExercises();
    }
    private void setExerciseCards(){
        for(Exercise exercise: getExercises()){
            exerciseCards.add(new ExerciseCard(exercise));
        }
    }
    public ArrayList<ExerciseCard> getExerciseCards(){
        setExerciseCards();
        return exerciseCards;
    }
    public FullCycle getCycle(){
        return cycle;
    }


}