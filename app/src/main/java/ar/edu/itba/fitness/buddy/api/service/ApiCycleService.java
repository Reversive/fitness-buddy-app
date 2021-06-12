package ar.edu.itba.fitness.buddy.api.service;

import androidx.lifecycle.LiveData;

import ar.edu.itba.fitness.buddy.api.model.ApiResponse;
import ar.edu.itba.fitness.buddy.api.model.Exercise;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCycleService {
    @GET("cycles/{cycleId}/exercises")
    LiveData<ApiResponse<PagedList<Exercise>>> getCycleExercises(@Path("cycleId") Integer cycleId,
                                                                 @Query("page") Integer page,
                                                                 @Query("size") Integer size,
                                                                 @Query("orderBy") String orderBy,
                                                                 @Query("direction") String direction);

    @GET("cycles/{cycleId}/exercises/{exerciseId}")
    LiveData<ApiResponse<Exercise>> getCycleExercise(@Path("cycleId") Integer cycleId,
                                                     @Path("exerciseId") Integer exerciseId);


}
