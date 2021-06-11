package ar.edu.itba.fitness.buddy;

import android.app.Application;

import ar.edu.itba.fitness.buddy.api.ApiClient;
import ar.edu.itba.fitness.buddy.api.service.ApiUserService;

public class App extends Application {
    private static AppPreferences preferences;

    public static AppPreferences getPreferences() {
        return preferences;
    }
    private static ApiUserService userService;

    public static ApiUserService getUserService() {
        return userService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = new AppPreferences(this);
        userService = ApiClient.create(ApiUserService.class);
    }
}
