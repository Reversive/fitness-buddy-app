package ar.edu.itba.fitness.buddy.api.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ApiResponse<T> {
    private T data;
    private ApiError error;

    public T getData() {
        return data;
    }

    public ApiError getError() {
        return error;
    }

    public ApiResponse(Response<T> response) {
        parseResponse(response);
    }

    public ApiResponse(Throwable t) {
        this.error = buildError(t.getMessage());
    }

    private void parseResponse(Response<T> response) {
        if(response.isSuccessful()) {
            this.data = response.body();
            return;
        }

        if(response.errorBody() == null) {
            this.error = buildError("Missing error body");
            return;
        }

        String message;
        try {
            message = response.errorBody().string();
        } catch (IOException e) {
            this.error = buildError(e.getMessage());;
            return;
        }

        if(message.trim().length() > 0) {
            Gson gson = new Gson();
            gson.fromJson(message, new TypeToken<ApiError>() {}.getType());
        }

    }

    private ApiError buildError(String message) {
        ApiError error = new ApiError(ApiError.UNEXPECTED_ERROR, "Unexpected Error");
        if(message != null) {
            List<String> details = new ArrayList<>();
            details.add(message);
            error.setDetails(details);
        }
        return error;
    }
}
