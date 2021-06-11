package ar.edu.itba.fitness.buddy.navigation.routine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Objects;

import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.listener.YouTubeListener;
import ar.edu.itba.fitness.buddy.model.PausableTimer;

public class RoutineExecutionFragment extends Fragment {

    private YouTubePlayerView video;
    private PausableTimer timer;
    private boolean isPlaying = true;

    public RoutineExecutionFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Routine Name");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routine_execution, container, false);

        // get info from api?
        final TextView title = view.findViewById(R.id.exerciseName);
        final TextView desc = view.findViewById(R.id.exerciseDescription);
        final TextView reps = view.findViewById(R.id.repetitionsCounter);
        final TextView timerTxt = view.findViewById(R.id.timerCounter);
        final FloatingActionButton prevBtn = view.findViewById(R.id.prevExerciseBtn);
        final FloatingActionButton nextBtn = view.findViewById(R.id.nextExerciseBtn);
        final FloatingActionButton toggleBtn = view.findViewById(R.id.toggleExerciseBtn);

        int time = 30;

        title.setText("Jumping Jacks");
        //desc.setText("Lorem ipsum dolor sit amet constectur");
        reps.setText("15");
        String timerStr = Integer.toString(time);
        timerTxt.setText(timerStr);

        video = view.findViewById(R.id.exerciseVideo);
        video.initialize(new YouTubeListener("4vQ8If7f374"));

        getLifecycle().addObserver(video);

        timer = new PausableTimer(time, timerTxt, () -> {
            // next exercise
        });

        prevBtn.setOnClickListener(v -> {
            // previous exercise
        });

        nextBtn.setOnClickListener(v -> {
            // next exercise
        });


        toggleBtn.setOnClickListener(v -> {
            if (isPlaying) {
                timer.pause();
                toggleBtn.setImageDrawable(ContextCompat.getDrawable(requireContext(), android.R.drawable.ic_media_play));
            } else {
                timer.play();
                toggleBtn.setImageDrawable(ContextCompat.getDrawable(requireContext(), android.R.drawable.ic_media_pause));
            }
            isPlaying = !isPlaying;
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        video.release();
        timer.finish();
    }
}
