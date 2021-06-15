package ar.edu.itba.fitness.buddy.model;

public class ExerciseItem {
    String name;
    //String imageUrl;
    int duration,repetitions;

    public ExerciseItem(String exercise_name, int duration, int repetitions) {
        this.name = exercise_name;
        this.duration = duration;
        this.repetitions = repetitions;

    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getRepetitions() {
        return repetitions;
    }
}
