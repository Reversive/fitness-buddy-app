package ar.edu.itba.fitness.buddy.api.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.api.ApiClient;
import ar.edu.itba.fitness.buddy.api.model.ApiResponse;
import ar.edu.itba.fitness.buddy.api.model.Cycle;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import ar.edu.itba.fitness.buddy.api.model.Routine;
import ar.edu.itba.fitness.buddy.api.service.ApiRoutineService;

public class RoutineRepository {
    ApiRoutineService apiRoutineService;

    public RoutineRepository(App app) {
        this.apiRoutineService = ApiClient.create(app, ApiRoutineService.class);
    }

    public LiveData<Resource<PagedList<Routine>>> getRoutines( String search,
                                                               String difficulty,
                                                               Integer page,
                                                               Integer size,
                                                               String orderBy,
                                                               String direction) {
        return new NetworkBoundResource<PagedList<Routine>, PagedList<Routine>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
                return apiRoutineService.getRoutines(search, difficulty, page, size, orderBy, direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Routine>> getRoutine(Integer routineId) {
        return new NetworkBoundResource<Routine, Routine>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Routine>> createCall() {
                return apiRoutineService.getRoutine(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<Cycle>>> getRoutineCycles(   Integer routineId,
                                                                    Integer page,
                                                                    Integer size,
                                                                    String orderBy,
                                                                    String direction) {
        return new NetworkBoundResource<PagedList<Cycle>, PagedList<Cycle>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Cycle>>> createCall() {
                return apiRoutineService.getRoutineCycles(routineId, page, size, orderBy, direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Cycle>> getRoutineCycle(Integer routineId, Integer cycleId) {
        return new NetworkBoundResource<Cycle, Cycle>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Cycle>> createCall() {
                return apiRoutineService.getRoutineCycle(routineId, cycleId);
            }
        }.asLiveData();
    }
}
