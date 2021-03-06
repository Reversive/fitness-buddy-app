package ar.edu.itba.fitness.buddy.api.service;

import androidx.lifecycle.LiveData;

import ar.edu.itba.fitness.buddy.api.model.ApiResponse;
import ar.edu.itba.fitness.buddy.api.model.Credentials;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import ar.edu.itba.fitness.buddy.api.model.Routine;
import ar.edu.itba.fitness.buddy.api.model.Token;
import ar.edu.itba.fitness.buddy.api.model.User;
import ar.edu.itba.fitness.buddy.api.model.Verification;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiUserService {
    @POST("users/login")
    LiveData<ApiResponse<Token>> login(@Body Credentials credentials);

    @POST("users/logout")
    LiveData<ApiResponse<Void>> logout();

    @GET("users/current")
    LiveData<ApiResponse<User>> getCurrentUser();

    @POST("users")
    LiveData<ApiResponse<User>> register(@Body User user);

    @POST("users/verify_email")
    LiveData<ApiResponse<Void>> verifyEmail(@Body Verification data);

    @PUT("users/current")
    LiveData<ApiResponse<User>> updateProfile(@Body User user);

    @GET("users/current/routines/")
    LiveData<ApiResponse<PagedList<Routine>>> getCurrentUserRoutines(@Query("difficulty") String difficulty,
                                                                    @Query("search") String search,
                                                                    @Query("page") Integer page,
                                                                    @Query("size") Integer size,
                                                                    @Query("orderBy") String orderBy,
                                                                    @Query("direction") String direction);

}
