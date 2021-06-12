package ar.edu.itba.fitness.buddy.api.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.api.ApiClient;
import ar.edu.itba.fitness.buddy.api.model.ApiResponse;
import ar.edu.itba.fitness.buddy.api.model.Credentials;
import ar.edu.itba.fitness.buddy.api.model.Token;
import ar.edu.itba.fitness.buddy.api.model.User;
import ar.edu.itba.fitness.buddy.api.model.Verification;
import ar.edu.itba.fitness.buddy.api.service.ApiUserService;


public class UserRepository {

    private final ApiUserService apiService;

    public UserRepository(App app) {
        this.apiService = ApiClient.create(app, ApiUserService.class);
    }

    public LiveData<Resource<Token>> login(Credentials credentials) {
        return new NetworkBoundResource<Token, Token>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Token>> createCall() {
                return apiService.login(credentials);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> logout() {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.logout();
            }
        }.asLiveData();
    }

    public LiveData<Resource<User>> getCurrentUser() {
        return new NetworkBoundResource<User, User>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return apiService.getCurrentUser();
            }
        }.asLiveData();
    }

    public LiveData<Resource<User>> register(User user) {
        return new NetworkBoundResource<User, User>() {
            @NonNull
            @NotNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return apiService.register(user);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> verifyEmail(Verification data) {
        return new NetworkBoundResource<Void, Void>() {
            @NonNull
            @NotNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.verifyEmail(data);
            }
        }.asLiveData();
    }

}
