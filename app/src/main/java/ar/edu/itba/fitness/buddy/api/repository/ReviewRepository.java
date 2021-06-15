package ar.edu.itba.fitness.buddy.api.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.api.ApiClient;
import ar.edu.itba.fitness.buddy.api.model.ApiResponse;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import ar.edu.itba.fitness.buddy.api.model.Review;
import ar.edu.itba.fitness.buddy.api.service.ApiReviewService;

public class ReviewRepository {
    ApiReviewService apiReviewService;

    public ReviewRepository(App app) {
        apiReviewService = ApiClient.create(app, ApiReviewService.class);
    }

    public LiveData<Resource<PagedList<Review>>> getRoutineReviews(Integer routineId,
                                                         Integer page,
                                                         Integer size,
                                                         String orderBy,
                                                         String direction) {
        return new NetworkBoundResource<PagedList<Review>, PagedList<Review>>() {
            @NonNull
            @NotNull
            @Override
            protected LiveData<ApiResponse<PagedList<Review>>> createCall() {
                return apiReviewService.getRoutineReviews(routineId, page, size, orderBy, direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Review>> addRoutineReview(Integer routineId, Review review) {
        return new NetworkBoundResource<Review, Review>() {

            @NonNull
            @NotNull
            @Override
            protected LiveData<ApiResponse<Review>> createCall() {
                return apiReviewService.addRoutineReview(routineId, review);
            }
        }.asLiveData();
    }
}
