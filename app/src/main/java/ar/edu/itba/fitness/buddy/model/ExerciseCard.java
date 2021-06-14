package ar.edu.itba.fitness.buddy.model;

public class ExerciseCard {
    String exercise_name;
    //String imageUrl;
    int duration,repetitions;

    public ExerciseCard(String exercise_name, int duration, int repetitions) {
        this.exercise_name = exercise_name;
        this.duration = duration;
        this.repetitions = repetitions;

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

    //public String getImageUrl() {return imageUrl;}
}
