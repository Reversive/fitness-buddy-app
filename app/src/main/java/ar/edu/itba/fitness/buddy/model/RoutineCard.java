package ar.edu.itba.fitness.buddy.model;

public class RoutineCard implements Comparable<RoutineCard> {
    int id;
    String title, difficulty, category;
    int rating;

    public RoutineCard(int id, String title, String difficulty, String category, int rating) {
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.category = category;
        this.rating = rating;
    }

    public int getId() { return id; }

    public String getTitle() {
        return title;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getCategory() {
        return category;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public int compareTo(RoutineCard o) {
        return 0;
    }
}
