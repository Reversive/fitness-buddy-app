package ar.edu.itba.fitness.buddy.api.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RoutineContent {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("created")
    @Expose
    private long created;
    @SerializedName("averageRating")
    @Expose
    private int averageRating;
    @SerializedName("isPublic")
    @Expose
    private boolean isPublic;
    @SerializedName("difficulty")
    @Expose
    private String difficulty;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("category")
    @Expose
    private Category category;

    public RoutineContent() {
    }

    public RoutineContent(int id, String name, String detail, long created, int averageRating, boolean isPublic, String difficulty, User user, Category category) {
        super();
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.created = created;
        this.averageRating = averageRating;
        this.isPublic = isPublic;
        this.difficulty = difficulty;
        this.user = user;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}