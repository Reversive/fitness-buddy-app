package ar.edu.itba.fitness.buddy.api.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MediaContent {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("number")
    @Expose
    private int number;
    @SerializedName("url")
    @Expose
    private String url;


    public MediaContent() {
    }

    public MediaContent(int id, int number, String url) {
        super();
        this.id = id;
        this.number = number;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}