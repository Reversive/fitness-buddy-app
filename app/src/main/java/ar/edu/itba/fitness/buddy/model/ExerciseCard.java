package ar.edu.itba.fitness.buddy.model;

public class ExerciseCard {
    String exercise_name,imageUrl;
    int duration,repetitions;

    public ExerciseCard(String exercise_name, int duration, int repetitions, String image) {
        this.exercise_name = exercise_name;
        this.duration = duration;
        this.repetitions = repetitions;
        this.imageUrl = image;
    }

    public String getExercise_name() {
        return exercise_name;
    }

    public int getDuration() {
        return duration;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
