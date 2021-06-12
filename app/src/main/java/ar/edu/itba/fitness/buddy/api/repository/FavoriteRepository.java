package ar.edu.itba.fitness.buddy.api.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.api.ApiClient;
import ar.edu.itba.fitness.buddy.api.model.ApiResponse;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import ar.edu.itba.fitness.buddy.api.model.Routine;
import ar.edu.itba.fitness.buddy.api.service.ApiFavoritesService;

public class FavoriteRepository {
    ApiFavoritesService apiFavoritesService;

    public FavoriteRepository(App app) {
        this.apiFavoritesService = ApiClient.create(app, ApiFavoritesService.class);
    }

    public LiveData<Resource<PagedList<Routine>>> getFavorites(Integer page,
                                                              Integer size,
                                                              String orderBy,
                                                              String direction) {
        return new NetworkBoundResource<PagedList<Routine>, PagedList<Routine>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
                return apiFavoritesService.getFavorites(page, size, orderBy, direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> setFavorite(Integer routineId) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiFavoritesService.setFavorite(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> unsetFavorite(Integer routineId) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiFavoritesService.unsetFavorite(routineId);
            }
        }.asLiveData();
    }


}
