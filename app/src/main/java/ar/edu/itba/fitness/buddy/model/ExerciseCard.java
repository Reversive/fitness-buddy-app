package ar.edu.itba.fitness.buddy.model;

import androidx.lifecycle.LifecycleOwner;

import java.util.function.Consumer;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.api.model.Exercise;
import ar.edu.itba.fitness.buddy.api.model.ExerciseDetail;
import ar.edu.itba.fitness.buddy.api.model.Media;
import ar.edu.itba.fitness.buddy.api.repository.Resource;
import ar.edu.itba.fitness.buddy.api.repository.Status;

public class ExerciseCard extends Exercise {
    Exercise exercise;
    Media image;

    public ExerciseCard(Exercise exercise) {
        this.exercise = exercise;


    }
    public void filldata(App app, LifecycleOwner owner, Consumer<? super Resource<?>> fail){
        app.getExerciseRepository().getExerciseImage(exercise.getExercise().getId(),0).observe(owner, (r) -> {
            if (r.getStatus() == Status.SUCCESS) {
                image = r.getData();
            } else {
                fail.accept(r);
            }
        });
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