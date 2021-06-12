package ar.edu.itba.fitness.buddy.api.service;

import androidx.lifecycle.LiveData;

import ar.edu.itba.fitness.buddy.api.model.ApiResponse;
import ar.edu.itba.fitness.buddy.api.model.Exercise;
import ar.edu.itba.fitness.buddy.api.model.Media;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiExerciseService {
    @GET("exercises")
    LiveData<ApiResponse<PagedList<Exercise>>> getExercises(@Query("search") String search,
                                                            @Query("page") Integer page,
                                                            @Query("size") Integer size,
                                                            @Query("orderBy") String orderBy,
                                                            @Query("direction") String direction);

    @GET("exercises/{exerciseId}")
    LiveData<ApiResponse<Exercise>> getExercise(@Path("exerciseId") Integer exerciseId);

    @GET("exercises/{exerciseId}/images")
    LiveData<ApiResponse<PagedList<Media>>> getExerciseImages(@Path("exerciseId") Integer exerciseId,
                                                   @Query("page") Integer page,
                                                   @Query("size") Integer size,
                                                   @Query("orderBy") String orderBy,
                                                   @Query("direction") String direction);

    @GET("exercises/{exerciseId}/images/{imageId}")
    LiveData<ApiResponse<Media>> getExerciseImage(@Path("exerciseId") Integer exerciseId,
                                                         @Path("imageId") Integer imageId);

    @GET("exercises/{exerciseId}/videos")
    LiveData<ApiResponse<PagedList<Media>>> getExerciseVideos(@Path("exerciseId") Integer exerciseId,
                                                   @Query("page") Integer page,
                                                   @Query("size") Integer size,
                                                   @Query("orderBy") String orderBy,
                                                   @Query("direction") String direction);

    @GET("exercises/{exerciseId}/videos/{videoId}")
    LiveData<ApiResponse<Media>> getExerciseVideo(@Path("exerciseId") Integer exerciseId,
                                                         @Path("videoId") Integer videoId);

}
