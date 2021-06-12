package ar.edu.itba.fitness.buddy.api.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.api.ApiClient;
import ar.edu.itba.fitness.buddy.api.model.ApiResponse;
import ar.edu.itba.fitness.buddy.api.model.Exercise;
import ar.edu.itba.fitness.buddy.api.model.Media;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import ar.edu.itba.fitness.buddy.api.service.ApiExerciseService;

public class ExerciseRepository {
    ApiExerciseService apiExerciseService;

    public ExerciseRepository(App app) {
        this.apiExerciseService = ApiClient.create(app, ApiExerciseService.class);
    }

    public LiveData<Resource<PagedList<Exercise>>> getExercises(String search,
                                                              Integer page,
                                                              Integer size,
                                                              String orderBy,
                                                              String direction) {
        return new NetworkBoundResource<PagedList<Exercise>, PagedList<Exercise>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Exercise>>> createCall() {
                return apiExerciseService.getExercises(search, page, size, orderBy, direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Exercise>> getExercise(Integer exerciseId) {
        return new NetworkBoundResource<Exercise, Exercise>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Exercise>> createCall() {
                return apiExerciseService.getExercise(exerciseId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<Media>>> getExerciseImages(Integer exerciseId,
                                                                  String search,
                                                                  Integer page,
                                                                  Integer size,
                                                                  String orderBy,
                                                                  String direction) {
        return new NetworkBoundResource<PagedList<Media>, PagedList<Media>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Media>>> createCall() {
                return apiExerciseService.getExerciseImages(exerciseId, page, size, orderBy, direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Media>> getExerciseImage(Integer exerciseId, Integer imageId) {
        return new NetworkBoundResource<Media, Media>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Media>> createCall() {
                return apiExerciseService.getExerciseImage(exerciseId, imageId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<Media>>> getExerciseVideos(Integer exerciseId,
                                                                  String search,
                                                                  Integer page,
                                                                  Integer size,
                                                                  String orderBy,
                                                                  String direction) {
        return new NetworkBoundResource<PagedList<Media>, PagedList<Media>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Media>>> createCall() {
                return apiExerciseService.getExerciseVideos(exerciseId, page, size, orderBy, direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Media>> getExerciseVideo(Integer exerciseId, Integer videoId) {
        return new NetworkBoundResource<Media, Media>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Media>> createCall() {
                return apiExerciseService.getExerciseVideo(exerciseId, videoId);
            }
        }.asLiveData();
    }

}
