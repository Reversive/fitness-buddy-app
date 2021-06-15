package ar.edu.itba.fitness.buddy.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("date")
    @Expose
    private long date;
    @SerializedName("score")
    @Expose
    private int score;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("routine")
    @Expose
    private Routine routine;


    public Review() {
    }


    public Review(int id, long date, int score, String review, Routine routine) {
        super();
        this.id = id;
        this.date = date;
        this.score = score;
        this.review = review;
        this.routine = routine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

}