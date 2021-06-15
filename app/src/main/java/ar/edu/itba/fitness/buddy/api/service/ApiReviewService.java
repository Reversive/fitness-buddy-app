package ar.edu.itba.fitness.buddy.api.service;

import androidx.lifecycle.LiveData;

import ar.edu.itba.fitness.buddy.api.model.ApiResponse;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import ar.edu.itba.fitness.buddy.api.model.Review;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiReviewService {
    @GET("reviews/{routineId}")
    LiveData<ApiResponse<PagedList<Review>>> getRoutineReviews(@Path("routineId") Integer routineId,
                                                               @Query("page") Integer page,
                                                               @Query("size") Integer size,
                                                               @Query("orderBy") String orderBy,
                                                               @Query("direction") String direction);

    @POST("reviews/{routineId}")
    LiveData<ApiResponse<Review>> addRoutineReview(@Path("routineId") Integer routineId, @Body Review review);
}
