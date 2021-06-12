package ar.edu.itba.fitness.buddy;

import android.app.Application;

import ar.edu.itba.fitness.buddy.api.repository.CycleRepository;
import ar.edu.itba.fitness.buddy.api.repository.ExerciseRepository;
import ar.edu.itba.fitness.buddy.api.repository.FavoriteRepository;
import ar.edu.itba.fitness.buddy.api.repository.RoutineRepository;
import ar.edu.itba.fitness.buddy.api.repository.UserRepository;



public class App extends Application {

    private AppPreferences preferences;
    private UserRepository userRepository;
    private RoutineRepository routineRepository;
    private CycleRepository cycleRepository;
    private FavoriteRepository favoriteRepository;
    private ExerciseRepository exerciseRepository;

    public RoutineRepository getRoutineRepository() {
        return routineRepository;
    }

    public CycleRepository getCycleRepository() {
        return cycleRepository;
    }

    public FavoriteRepository getFavoriteRepository() {
        return favoriteRepository;
    }

    public ExerciseRepository getExerciseRepository() {
        return exerciseRepository;
    }

    public AppPreferences getPreferences() { return preferences; }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = new AppPreferences(this);
        userRepository = new UserRepository(this);
        routineRepository = new RoutineRepository(this);
        cycleRepository = new CycleRepository(this);
        favoriteRepository = new FavoriteRepository(this);
        exerciseRepository = new ExerciseRepository(this);
    }
}

