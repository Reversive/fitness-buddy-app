package ar.edu.itba.fitness.buddy.navigation.routine;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Objects;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.api.model.Error;
import ar.edu.itba.fitness.buddy.api.model.Exercise;
import ar.edu.itba.fitness.buddy.api.model.Media;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import ar.edu.itba.fitness.buddy.api.model.Review;
import ar.edu.itba.fitness.buddy.api.repository.Resource;
import ar.edu.itba.fitness.buddy.api.repository.Status;
import ar.edu.itba.fitness.buddy.listener.OnSwipeTouchListener;
import ar.edu.itba.fitness.buddy.listener.YouTubeListener;
import ar.edu.itba.fitness.buddy.model.FullRoutine;
import ar.edu.itba.fitness.buddy.model.PausableTimer;
import ar.edu.itba.fitness.buddy.navigation.community.CommunityRoutinesFragment;
import ar.edu.itba.fitness.buddy.splash.login.LoginActivity;

public class RoutineExecutionFragment extends Fragment {

    private YouTubePlayerView videoView;
    private TextView titleView;
    private TextView descView;
    private TextView repsView;
    private TextView timerView;
    private FloatingActionButton toggleBtn;
    private FloatingActionButton prevBtn;
    private FloatingActionButton nextBtn;

    private FullRoutine fullRoutine;

    private boolean isPlaying = true;

    private YouTubeListener videoPlayer;
    private PausableTimer timer;

    private final int routineId;
    private final String routineName;
    private boolean isFavourite;
    private int currentCycle;
    private int currentRound;
    private int currentExercise;

    private boolean noTimer;

    Dialog finishDialog;
    private boolean isLastExercise = false;

    public RoutineExecutionFragment(int routineId, String routineName, boolean isFavourite) {
        this(routineId, routineName, isFavourite, 0, 0, 0);
    }

    public RoutineExecutionFragment(int routineId, String routineName, boolean isFavourite, int currentCycle, int currentExercise, int currentRound) {
        this.routineId = routineId;
        this.routineName = routineName;
        this.currentCycle = currentCycle;
        this.currentExercise = currentExercise;
        this.currentRound = currentRound;
        this.isFavourite = isFavourite;
    }

    public void timerCallback() {
        timer.finish();
        currentExercise++;
        loadExercise();
    }

    public void loadExercise() {
        videoPlayer.pauseVideo();

        if (currentExercise < 0) {
            if (currentRound == 0) {
                currentCycle--;
                currentRound = fullRoutine.getCycle(currentCycle).getRepetitions() - 1;
            } else {
                currentRound--;
            }
            currentExercise = fullRoutine.getCycle(currentCycle).getExercises().size() - 1;
        } else if (currentExercise >= fullRoutine.getCycle(currentCycle).getExercises().size()) {
            currentRound++;
            currentExercise = 0;
            if (currentRound >= fullRoutine.getCycle(currentCycle).getRepetitions()) {
                currentCycle++;
                currentRound = 0;
                if (currentCycle == fullRoutine.getCycles()) {
                    finishDialog.setContentView(R.layout.routine_finish_dialog);
                    finishDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    finishDialog.show();
                    Button finishButton = finishDialog.findViewById(R.id.finishRoutineButton);
                    RatingBar ratingBar = finishDialog.findViewById(R.id.routineFinishRatingBar);
                    AppCompatImageButton shareButton = finishDialog.findViewById(R.id.dialog_share);
                    AppCompatImageButton favoriteButton = finishDialog.findViewById(R.id.dialog_add_to_fav);
                    shareButton.setOnClickListener(new ShareButtonLister());
                    favoriteButton.setOnClickListener(new FavoriteButtonListener());
                    if (isFavourite)
                        favoriteButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite));
                    else
                        favoriteButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_border));

                    finishButton.setOnClickListener(new FinishButtonListener());
                    ratingBar.setOnRatingBarChangeListener(new RoutineBarListener());
                    return;
                }
            }
        }

        Log.d("ROUND", String.valueOf(currentRound));
        if (currentExercise == 0 && currentCycle == 0 && currentRound == 0){
            prevBtn.hide();
        } else {
            prevBtn.show();
        }

        if (currentCycle == fullRoutine.getCycles() - 1 && currentExercise == fullRoutine.getCycle(currentCycle).getExercises().size() - 1 && currentRound == fullRoutine.getCycle(currentCycle).getRepetitions() - 1) {
            isLastExercise = true;
            nextBtn.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_done_24));
        }

        Exercise exercise = fullRoutine.getCycle(currentCycle).getExercises().get(currentExercise);

        loadExerciseVideo(exercise.getExercise().getId());

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

    private void loadExerciseVideo(int id) {
        App app = (App) requireActivity().getApplication();
        app.getExerciseRepository().getExerciseVideos(id, null,0, 1, null, null).observe(getViewLifecycleOwner(), t -> {
            if(t.getStatus() == Status.SUCCESS) {
                Log.d("VIDEO", "success");
                PagedList<Media> exercisePage = t.getData();
                if(exercisePage != null && exercisePage.getSize() > 0) {
                    String[] parsedUrl = (exercisePage.getContent()).get(0).getUrl().split("\\?v=");
                    if (parsedUrl.length < 2) {
                        videoPlayer.playVideo("");
                        return;
                    }
                    String videoId = (exercisePage.getContent()).get(0).getUrl().split("\\?v=")[1].split("&")[0];
                    Log.d("VIDEO", videoId);
                    videoPlayer.playVideo(videoId);
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
        finishDialog = new Dialog(requireActivity());
        App app = (App)requireActivity().getApplication();
        if(app.getPreferences().getAuthToken() == null) {
            Intent i = new Intent(requireContext(), LoginActivity.class);
            startActivity(i);
        }
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

        FloatingActionButton listViewBtn = view.findViewById(R.id.listExecutionBtn);

        prevBtn.setOnClickListener(v -> {
            currentExercise--;
            timer.finish();
            isLastExercise = false;
            nextBtn.setImageDrawable(ContextCompat.getDrawable(requireContext(), android.R.drawable.ic_media_next));
            loadExercise();
        });

        nextBtn.setOnClickListener(v -> {
            currentExercise++;
            timer.finish();
            loadExercise();
        });

        view.setOnTouchListener(new OnSwipeTouchListener(requireContext()) {
            public void onSwipeLeft() {
                if (isLastExercise)
                    return;

                currentExercise++;
                timer.finish();
                loadExercise();
            }

            public void onSwipeRight() {
                if (currentCycle == 0 && currentRound == 0 && currentExercise == 0)
                    return;

                isLastExercise = false;
                currentExercise--;
                timer.finish();
                nextBtn.setImageDrawable(ContextCompat.getDrawable(requireContext(), android.R.drawable.ic_media_next));
                loadExercise();
            }
        });

        toggleBtn.setOnClickListener(this::onClickToggle);

        listViewBtn.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true);
            transaction.replace(R.id.frame_container, new RoutineExecutionListFragment(routineId, routineName, isFavourite, currentCycle, currentExercise,  currentRound));
            transaction.addToBackStack(null);
            transaction.commit();
        });

        videoPlayer = new YouTubeListener();
        videoView.initialize(videoPlayer);

        getLifecycle().addObserver(videoView);

        fullRoutine = new FullRoutine(routineId);
        App app = (App) requireActivity().getApplication();
        fullRoutine.fillData(app, getViewLifecycleOwner(), this::loadExercise, this::defaultResourceHandler);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        videoView.release();
        timer.finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        timer.finish();
        videoView.release();
    }

    private class FinishButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            finishDialog.dismiss();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true);
            transaction.replace(R.id.frame_container, new CommunityRoutinesFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private class RoutineBarListener implements RatingBar.OnRatingBarChangeListener {

        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
            Review review = new Review(0, 0, (int)v, "", null);
            App app = (App)requireActivity().getApplication();
            app.getReviewRepository().addRoutineReview(routineId, review).observe(getViewLifecycleOwner(), r -> {
                if(r.getStatus() == Status.ERROR) defaultResourceHandler(r);
            });
        }
    }

    private class ShareButtonLister implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            String tmpName = routineName.replaceAll(" ", "+");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "http://fitness-buddy.com/?" + routineId + "," + routineName);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        }
    }

    private class FavoriteButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            AppCompatImageButton favButton = (AppCompatImageButton)view;
            App app = (App)requireActivity().getApplication();
            if (!isFavourite) {
                app.getFavoriteRepository().setFavorite(routineId).observe(getViewLifecycleOwner(), f -> {
                    if (f.getStatus() == Status.SUCCESS) {
                        isFavourite = !isFavourite;
                        favButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite));
                        Toast.makeText(app, "Added to favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        defaultResourceHandler(f);
                    }
                });
            } else {
                app.getFavoriteRepository().unsetFavorite(routineId).observe(getViewLifecycleOwner(), f -> {
                    if (f.getStatus() == Status.SUCCESS) {
                        isFavourite = !isFavourite;
                        favButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_border));
                        Toast.makeText(app, "Removed from favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        defaultResourceHandler(f);
                    }
                });
            }
        }
    }
}
