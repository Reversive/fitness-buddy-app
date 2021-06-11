package ar.edu.itba.fitness.buddy.api.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiError {
    public static final int UNEXPECTED_ERROR = 99;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("details")
    @Expose
    private List<String> details = null;

    public ApiError() {
    }

    public ApiError(int code, String description, List<String> details) {
        super();
        this.code = code;
        this.description = description;
        this.details = details;
    }

    public ApiError(int code, String description) {
        super();
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

}