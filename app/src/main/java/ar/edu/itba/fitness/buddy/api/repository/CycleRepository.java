package ar.edu.itba.fitness.buddy.api.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.api.ApiClient;
import ar.edu.itba.fitness.buddy.api.model.ApiResponse;
import ar.edu.itba.fitness.buddy.api.model.Exercise;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import ar.edu.itba.fitness.buddy.api.service.ApiCycleService;

public class CycleRepository {
    ApiCycleService apiCycleService;

    public CycleRepository(App app) {
        this.apiCycleService = ApiClient.create(app, ApiCycleService.class);
    }

    public LiveData<Resource<PagedList<Exercise>>> getCycleExercises(Integer cycleId,
                                                                     Integer page,
                                                                     Integer size,
                                                                     String orderBy,
                                                                     String direction) {
        return new NetworkBoundResource<PagedList<Exercise>, PagedList<Exercise>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Exercise>>> createCall() {
                return apiCycleService.getCycleExercises(cycleId, page, size, orderBy, direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Exercise>> getCycleExercise(Integer cycleId, Integer exerciseId) {
        return new NetworkBoundResource<Exercise, Exercise>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Exercise>> createCall() {
                return apiCycleService.getCycleExercise(cycleId, exerciseId);
            }
        }.asLiveData();
    }
}
