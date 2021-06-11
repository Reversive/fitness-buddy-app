package ar.edu.itba.fitness.buddy.api.service;

import androidx.lifecycle.LiveData;

import ar.edu.itba.fitness.buddy.api.model.ApiResponse;
import ar.edu.itba.fitness.buddy.api.model.Exercise;
import ar.edu.itba.fitness.buddy.api.model.ExerciseContent;
import ar.edu.itba.fitness.buddy.api.model.Media;
import ar.edu.itba.fitness.buddy.api.model.MediaContent;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiExerciseService {
    @GET("exercises")
    LiveData<ApiResponse<Exercise>> getExercises(@Query("search") String search,
                                                 @Query("page") Integer page,
                                                 @Query("size") Integer size,
                                                 @Query("orderBy") String orderBy,
                                                 @Query("direction") String direction);

    @GET("exercises/{exerciseId}")
    LiveData<ApiResponse<ExerciseContent>> getExercise(@Path("exerciseId") Integer exerciseId);

    @GET("exercises/{exerciseId}/images")
    LiveData<ApiResponse<Media>> getExerciseImages(@Path("exerciseId") Integer exerciseId,
                                                   @Query("page") Integer page,
                                                   @Query("size") Integer size,
                                                   @Query("orderBy") String orderBy,
                                                   @Query("direction") String direction);

    @GET("exercises/{exerciseId}/images/{imageId}")
    LiveData<ApiResponse<MediaContent>> getExerciseImage(@Path("exerciseId") Integer exerciseId,
                                                         @Path("imageId") Integer imageId);

    @GET("exercises/{exerciseId}/videos")
    LiveData<ApiResponse<Media>> getExerciseVideos(@Path("exerciseId") Integer exerciseId,
                                                   @Query("page") Integer page,
                                                   @Query("size") Integer size,
                                                   @Query("orderBy") String orderBy,
                                                   @Query("direction") String direction);

    @GET("exercises/{exerciseId}/videos/{videoId}")
    LiveData<ApiResponse<MediaContent>> getExerciseVideo(@Path("exerciseId") Integer exerciseId,
                                                         @Path("videoId") Integer videoId);

}
