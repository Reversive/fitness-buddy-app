package ar.edu.itba.fitness.buddy.model;

import java.util.ArrayList;

import ar.edu.itba.fitness.buddy.api.model.Exercise;
import ar.edu.itba.fitness.buddy.api.model.Media;

public class CycleCard {
    private FullCycle cycle;
    private final ArrayList<ExerciseCard> exerciseCards =new ArrayList<>();

    public CycleCard(FullCycle cycle, ArrayList<Media> images) {
        this.cycle=cycle;
        setExerciseCards(images);
    }

    public ArrayList<Exercise> getExercises() {
        return this.cycle.getExercises();
    }

    private void setExerciseCards(ArrayList<Media> images) {
        ArrayList<Exercise> exercises = getExercises();
        for (int i = 0; i < exercises.size(); i++) {
            Exercise exercise = exercises.get(i);
            ExerciseCard card = new ExerciseCard(exercise, images.get(i));
            exerciseCards.add(card);
        }
    }

    public ArrayList<ExerciseCard> getExerciseCards(){
        return exerciseCards;
    }
    public FullCycle getCycle(){
        return cycle;
    }


}