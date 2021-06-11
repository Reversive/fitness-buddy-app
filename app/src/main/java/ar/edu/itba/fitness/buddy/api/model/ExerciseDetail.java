package ar.edu.itba.fitness.buddy.api.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExerciseDetail {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("date")
    @Expose
    private long date;
    @SerializedName("order")
    @Expose
    private int order;

    /**
     * No args constructor for use in serialization
     *
     */
    public ExerciseDetail() {
    }

    /**
     *
     * @param date
     * @param name
     * @param id
     * @param detail
     * @param type
     * @param order
     */
    public ExerciseDetail(int id, String name, String detail, String type, long date, int order) {
        super();
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.type = type;
        this.date = date;
        this.order = order;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}