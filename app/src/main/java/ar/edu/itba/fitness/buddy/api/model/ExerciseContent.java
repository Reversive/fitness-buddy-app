package ar.edu.itba.fitness.buddy.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExerciseContent {

    @SerializedName("exercise")
    @Expose
    private ExerciseDetail exercise;
    @SerializedName("order")
    @Expose
    private int order;
    @SerializedName("duration")
    @Expose
    private int duration;
    @SerializedName("repetitions")
    @Expose
    private int repetitions;
    @SerializedName("metadata")
    @Expose
    private Object metadata;


    public ExerciseContent() {
    }


    public ExerciseContent(ExerciseDetail exercise, int order, int duration, int repetitions, Object metadata) {
        super();
        this.exercise = exercise;
        this.order = order;
        this.duration = duration;
        this.repetitions = repetitions;
        this.metadata = metadata;
    }

    public ExerciseDetail getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseDetail exercise) {
        this.exercise = exercise;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

}