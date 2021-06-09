package ar.edu.itba.fitness.buddy.helper;

public class RoutineCard {
    String title, difficulty, category;
    int rating;

    public RoutineCard(String title, String difficulty, String category, int rating) {
        this.title = title;
        this.difficulty = difficulty;
        this.category = category;
        this.rating = rating;
    }

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
}
