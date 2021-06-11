package ar.edu.itba.fitness.buddy.api.adapter;

import androidx.lifecycle.LiveData;

import java.lang.reflect.Type;

import ar.edu.itba.fitness.buddy.api.model.ApiResponse;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {
    private final Type responseType;

    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<R>> adapt(Call<R> call) {
        return new LiveData<ApiResponse<R>>() {
            @Override
            protected void onActive() {
                super.onActive();
                call.enqueue(new Callback<R>() {
                    @Override
                    public void onResponse(Call<R> call, Response<R> response) {
                        postValue(new ApiResponse(response));
                    }

                    @Override
                    public void onFailure(Call<R> call, Throwable t) {
                        postValue(new ApiResponse(t));
                    }
                });
            }
        };
    }
}
