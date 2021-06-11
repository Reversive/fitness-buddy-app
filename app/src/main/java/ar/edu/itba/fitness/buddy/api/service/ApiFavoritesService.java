package ar.edu.itba.fitness.buddy.api.service;

import androidx.lifecycle.LiveData;

import ar.edu.itba.fitness.buddy.api.model.ApiResponse;
import ar.edu.itba.fitness.buddy.api.model.Favorites;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiFavoritesService {
    @GET("favourites")
    LiveData<ApiResponse<Favorites>> getFavorites(@Query("page") Integer page,
                                                  @Query("size") Integer size,
                                                  @Query("orderBy") String orderBy,
                                                  @Query("direction") String direction);

    @POST("favourites")
    LiveData<ApiResponse<Void>> setFavorite(@Query("routineId") Integer routineId);

    @DELETE("favourites")
    LiveData<ApiResponse<Void>> unsetFavorite(@Query("routineId") Integer routineId);
}
