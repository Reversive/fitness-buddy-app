package ar.edu.itba.fitness.buddy.api.service;

import androidx.lifecycle.LiveData;

import ar.edu.itba.fitness.buddy.api.model.ApiResponse;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import ar.edu.itba.fitness.buddy.api.model.Routine;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiFavoritesService {
    @GET("favourites")
    LiveData<ApiResponse<PagedList<Routine>>> getFavorites(@Query("page") Integer page,
                                                           @Query("size") Integer size);

    @POST("favourites/{routineId}/")
    LiveData<ApiResponse<Void>> setFavorite(@Path("routineId") Integer routineId);

    @DELETE("favourites/{routineId}/")
    LiveData<ApiResponse<Void>> unsetFavorite(@Path("routineId") Integer routineId);
}
