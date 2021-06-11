package ar.edu.itba.fitness.buddy.api.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Routine {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("date")
    @Expose
    private long date;
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
    @SerializedName("metadata")
    @Expose
    private Object metadata;

    public Routine() {
    }

    public Routine(int id, String name, String detail, long date, int averageRating, boolean isPublic, String difficulty, User user, Category category, Object metadata) {
        super();
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.date = date;
        this.averageRating = averageRating;
        this.isPublic = isPublic;
        this.difficulty = difficulty;
        this.user = user;
        this.category = category;
        this.metadata = metadata;
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
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

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

}