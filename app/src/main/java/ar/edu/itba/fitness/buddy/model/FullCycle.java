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

public class FullCycle extends Cycle {
    private final ArrayList<Exercise> exercises;

    public FullCycle(Cycle cycle) {
        super(cycle.getId(), cycle.getName(),
                cycle.getDetail(), cycle.getType(),
                cycle.getOrder(), cycle.getRepetitions(),
                cycle.getMetadata());
        exercises = new ArrayList<>();
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void fillData(App app, LifecycleOwner owner, Runnable callback, Consumer<? super Resource<?>> fail) {
        app.getCycleRepository().getCycleExercises(getId(), 0, 10, null, null).observe(owner, (r) -> {
            if (r.getStatus() == Status.SUCCESS) {
                PagedList<Exercise> exercisePage = r.getData();
                if(exercisePage != null) {
                    exercises.addAll(exercisePage.getContent());
                    callback.run();
                }
            } else {
                fail.accept(r);
            }
        });
    }
}
