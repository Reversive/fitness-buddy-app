package ar.edu.itba.fitness.buddy.navigation.routine;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.RegexValidator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Objects;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.adapter.RoutineCardAdapter;
import ar.edu.itba.fitness.buddy.api.model.Cycle;
import ar.edu.itba.fitness.buddy.api.model.Error;
import ar.edu.itba.fitness.buddy.api.model.Exercise;
import ar.edu.itba.fitness.buddy.api.model.Media;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import ar.edu.itba.fitness.buddy.api.model.Routine;
import ar.edu.itba.fitness.buddy.api.repository.ExerciseRepository;
import ar.edu.itba.fitness.buddy.api.repository.Resource;
import ar.edu.itba.fitness.buddy.api.repository.Status;
import ar.edu.itba.fitness.buddy.api.service.ApiExerciseService;
import ar.edu.itba.fitness.buddy.listener.YouTubeListener;
import ar.edu.itba.fitness.buddy.model.PausableTimer;
import ar.edu.itba.fitness.buddy.model.RoutineCard;
import ar.edu.itba.fitness.buddy.navigation.MainNavigationActivity;
import ar.edu.itba.fitness.buddy.navigation.community.CommunityRoutinesFragment;

public class RoutineExecutionFragment extends Fragment {

    private YouTubePlayerView videoView;
    private TextView titleView;
    private TextView descView;
    private TextView repsView;
    private TextView timerView;
    private FloatingActionButton toggleBtn;
    private FloatingActionButton prevBtn;
    private FloatingActionButton nextBtn;

    private boolean isPlaying = true;

    private YouTubeListener videoPlayer;
    private PausableTimer timer;

    private final int routineId;
    private final String routineName;
    private int currentCycle;
    private int cycleRounds;
    private int currentExercise;

    private ArrayList<Cycle> cycles;
    private ArrayList<Exercise> exercises;
    private boolean noTimer;

    public RoutineExecutionFragment(int routineId, String routineName) {
        this.routineId = routineId;
        this.routineName = routineName;
        this.currentCycle = 0;
        this.currentExercise = 0;
        this.cycleRounds = 0;
    }

    private void getCycles() {
        App app = (App) requireActivity().getApplication();
        app.getRoutineRepository().getRoutineCycles(routineId, 0, 10, null, null).observe(getViewLifecycleOwner(), t -> {
            if(t.getStatus() == Status.SUCCESS) {
                PagedList<Cycle> cyclePage = t.getData();
                if(cyclePage != null) {
                    cycles = (ArrayList<Cycle>) cyclePage.getContent();
                    getCycleExercises();
                }
            } else {
                defaultResourceHandler(t);
            }
        });
    }

    private void getCycleExercises() {
        App app = (App) requireActivity().getApplication();
        app.getCycleRepository().getCycleExercises(cycles.get(currentCycle).getId(), 0, 10, null, null).observe(getViewLifecycleOwner(), t -> {
            if(t.getStatus() == Status.SUCCESS) {
                PagedList<Exercise> exercisePage = t.getData();
                if(exercisePage != null) {
                    exercises = (ArrayList<Exercise>) exercisePage.getContent();
                    if (currentExercise != -1)
                        currentExercise = 0;
                    else
                        currentExercise = exercises.size() - 1;

                    if (cycleRounds != -1)
                        cycleRounds = 0;
                    else
                        cycleRounds = cycles.get(currentCycle).getRepetitions() - 1;

                    loadExercise();
                }
            } else {
                defaultResourceHandler(t);
            }
        });
    }

    public void timerCallback() {
        timer.finish();
        currentExercise++;
        loadExercise();
    }

    public void loadExercise() {
        videoPlayer.pauseVideo();
        if (currentExercise == 0 && currentCycle == 0 && cycleRounds == 0) {
            prevBtn.hide();
        } else {
            if (currentCycle == cycles.size() - 1 && currentExercise == exercises.size() - 1 && cycleRounds == cycles.get(currentCycle).getRepetitions() - 1) {
                nextBtn.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_done_24));
            }
            prevBtn.show();
        }
        if (currentExercise < 0) {
            currentCycle--;
            cycleRounds = -1;
            currentExercise = -1;
            getCycleExercises();
            return;
        } else if (currentExercise >= exercises.size()) {
            if (cycleRounds == cycles.get(currentCycle).getRepetitions() - 1) {
                cycleRounds = 0;
                currentCycle++;
                if (currentCycle == cycles.size()) { // finished routine
                    Log.d("ROUTINE", "finished");
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true);
                    transaction.replace(R.id.frame_container, new CommunityRoutinesFragment()); // Maybe show congratulations screen
                    transaction.commit();
                    return;
                }
                getCycleExercises();
            } else {
                cycleRounds++;
                currentExercise = 0;
                loadExercise();
            }
            return;
        }

        loadExerciseVideo();

        Exercise exercise = exercises.get(currentExercise);
        Log.d("EXERCISE", "got");
        titleView.setText(exercise.getExercise().getName());
        descView.setText(exercise.getExercise().getDetail());

        int reps = exercise.getRepetitions();
        String repsStr = "-";
        if (reps != 0) {
            repsStr = Integer.toString(exercise.getRepetitions());
        }
        repsView.setText(repsStr);

        int time = exercise.getDuration();
        String timerStr = "-";
        noTimer = (time == 0);
        if (!noTimer) {
            timerStr = Integer.toString(exercise.getDuration());
            timer = new PausableTimer(time, timerView, this::timerCallback);
        }

        toggleBtn.setImageDrawable(ContextCompat.getDrawable(requireContext(), android.R.drawable.ic_media_pause));
        isPlaying = true;
        timerView.setText(timerStr);
    }

    private void loadExerciseVideo() {
        App app = (App) requireActivity().getApplication();
        app.getExerciseRepository().getExerciseVideos(exercises.get(currentExercise).getExercise().getId(), null,0, 1, null, null).observe(getViewLifecycleOwner(), t -> {
            if(t.getStatus() == Status.SUCCESS) {
                Log.d("VIDEO", "success");
                PagedList<Media> exercisePage = t.getData();
                if(exercisePage != null && exercisePage.getSize() > 0) {
                    String[] parsedUrl = (exercisePage.getContent()).get(0).getUrl().split("\\?v=");
                    if (parsedUrl.length < 2) {
                        videoPlayer.playVideo("");
                        return;
                    }
                    String id = (exercisePage.getContent()).get(0).getUrl().split("\\?v=")[1].split("&")[0];
                    Log.d("VIDEO", id);
                    videoPlayer.playVideo(id);
                }
            } else {
                defaultResourceHandler(t);
            }
        });
    }

    private void defaultResourceHandler(Resource<?> resource) {
        switch (resource.getStatus()) {
            case LOADING:
                Log.d("GET EXERCISES", "loading");
                break;
            case ERROR:
                Log.d("GET EXERCISES", "fail");
                Error error = resource.getError();
                String message = getString(R.string.error, Objects.requireNonNull(error).getDescription(), error.getCode());
                Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onClickToggle(View view) {
        if (noTimer)
            return;

        if (isPlaying) {
            timer.pause();
            toggleBtn.setImageDrawable(ContextCompat.getDrawable(requireContext(), android.R.drawable.ic_media_play));
        } else {
            timer.play();
            toggleBtn.setImageDrawable(ContextCompat.getDrawable(requireContext(), android.R.drawable.ic_media_pause));
        }

        isPlaying = !isPlaying;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(this.routineName);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routine_execution, container, false);

        titleView = view.findViewById(R.id.exerciseName);
        descView = view.findViewById(R.id.exerciseDescription);
        repsView = view.findViewById(R.id.repetitionsCounter);
        timerView = view.findViewById(R.id.timerCounter);
        videoView = view.findViewById(R.id.exerciseVideo);

        noTimer = true;

        prevBtn = view.findViewById(R.id.prevExerciseBtn);
        nextBtn = view.findViewById(R.id.nextExerciseBtn);
        toggleBtn = view.findViewById(R.id.toggleExerciseBtn);

        prevBtn.setOnClickListener(v -> {
            currentExercise--;
            timer.finish();
            loadExercise();
        });

        nextBtn.setOnClickListener(v -> {
            currentExercise++;
            timer.finish();
            loadExercise();
        });

        toggleBtn.setOnClickListener(this::onClickToggle);

        videoPlayer = new YouTubeListener();
        videoView.initialize(videoPlayer);

        getLifecycle().addObserver(videoView);

        getCycles();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        videoView.release();
        timer.finish();
    }
}
