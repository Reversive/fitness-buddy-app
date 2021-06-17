package ar.edu.itba.fitness.buddy.model;

import ar.edu.itba.fitness.buddy.api.model.Exercise;
import ar.edu.itba.fitness.buddy.api.model.Media;

public class ExerciseCard extends Exercise {
    Exercise exercise;
    Media image;

    public ExerciseCard(Exercise exercise, Media image) {
        this.exercise = exercise;
        this.image = image;
    }

    public int getExerciseId() {
        return exercise.getExercise().getId();
    }

    public Media getImage() {
        return image;
    }

    public String getName() {
        return exercise.getExercise().getName();
    }

    public int getDuration() {
        return exercise.getDuration();
    }

    public int getRepetitions() {
        return exercise.getRepetitions();
    }
}