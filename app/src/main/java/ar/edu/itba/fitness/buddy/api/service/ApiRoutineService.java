package ar.edu.itba.fitness.buddy.api.service;

import androidx.lifecycle.LiveData;

import ar.edu.itba.fitness.buddy.api.model.ApiResponse;
import ar.edu.itba.fitness.buddy.api.model.Cycle;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import ar.edu.itba.fitness.buddy.api.model.Routine;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRoutineService {
    @GET("routines")
    LiveData<ApiResponse<PagedList<Routine>>> getRoutines(@Query("search") String search,
                                                                @Query("difficulty") String difficulty,
                                                                @Query("page") Integer page,
                                                                @Query("size") Integer size,
                                                                @Query("orderBy") String orderBy,
                                                                @Query("direction") String direction);

    @GET("routines")
    LiveData<ApiResponse<Routine>> getRoutine(@Query("routineId") Integer routineId);

    @GET("routines/{routineId}/cycles")
    LiveData<ApiResponse<PagedList<Cycle>>> getRoutineCycles(@Path("routineId") Integer routineId,
                                                  @Query("page") Integer page,
                                                  @Query("size") Integer size,
                                                  @Query("orderBy") String orderBy,
                                                  @Query("direction") String direction);

    @GET("routines/{routineId}/cycles/{cycleId}")
    LiveData<ApiResponse<Cycle>> getRoutineCycle(@Path("routineId") Integer routineId,
                                                        @Path("cycleId") Integer cycleId);


}
