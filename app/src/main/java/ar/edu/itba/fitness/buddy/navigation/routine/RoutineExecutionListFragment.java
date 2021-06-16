package ar.edu.itba.fitness.buddy.navigation.routine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.adapter.ExerciseItemAdapter;
import ar.edu.itba.fitness.buddy.api.model.Error;
import ar.edu.itba.fitness.buddy.api.model.Exercise;
import ar.edu.itba.fitness.buddy.api.repository.Resource;
import ar.edu.itba.fitness.buddy.model.ExerciseItem;
import ar.edu.itba.fitness.buddy.model.FullRoutine;

public class RoutineExecutionListFragment extends Fragment {
    static int EXERCISES_SHOWN = 3;

    private final int routineId;
    private final String routineName;
    private ArrayList<ExerciseItem> exerciseItems;
    private FullRoutine rout;

    private int currentCycle;
    private int currentExercise;
    private int currentRound;

    RecyclerView exerciseRecycler;
    private View doneLayout;

    public RoutineExecutionListFragment(int id, String name) {
        this(id, name, 0, 0, 0);
    }

    public RoutineExecutionListFragment(int routineId, String routineName, int currentCycle, int currentExercise, int currentRound) {
        this.routineId = routineId;
        this.routineName = routineName;
        this.currentCycle = currentCycle;
        this.currentExercise = currentExercise;
        this.currentRound = currentRound;
    }

    private void fillExercises() {
        exerciseItems = new ArrayList<>();
        int added = 0;
        boolean done = false;
        boolean first = true;
        int i, j, k;
        for (i = currentCycle; i < rout.getCycles() && !done; i++) {
            ArrayList<Exercise> exerciseList = rout.getCycle(i).getExercises();
            for (k = 0; k < rout.getCycle(i).getRepetitions(); k++) {
                for (j = 0; j < exerciseList.size() && !done; j++) {
                    if (first) {
                        k = currentRound;
                        j = currentExercise;
                        first = false;
                    }
                    Exercise exercise = exerciseList.get(j);
                    exerciseItems.add(
                            new ExerciseItem(
                                    exercise.getExercise().getName(),
                                    exercise.getDuration(),
                                    exercise.getRepetitions()
                            )
                    );
                    added++;
                    if (added == EXERCISES_SHOWN)
                        done = true;
                }
            }
        }

        ExerciseItemAdapter adapter = new ExerciseItemAdapter(exerciseItems, this::navToExercise);
        exerciseRecycler.setAdapter(adapter);
    }

    private void navToExercise(int position) {
        if (position == 0)
            return;

        currentExercise += position;
        int rounds = rout.getCycle(currentCycle).getRepetitions();
        int size = rout.getCycle(currentCycle).getExercises().size();
        while (currentExercise >= size) {
            currentExercise -= size;
            currentRound++;
            if (currentRound >= rounds) {
                currentCycle++;
                size = rout.getCycle(currentCycle).getExercises().size();
                rounds = rout.getCycle(currentCycle).getRepetitions();
                currentRound = 0;
            }
        }

        if (currentCycle == rout.getCycles() - 1 && currentRound == rounds - 1 && currentExercise == size - 1) {
            doneLayout.setVisibility(View.VISIBLE);
        }
        fillExercises();
    }

    private void defaultResourceHandler(Resource<?> resource) {
        switch (resource.getStatus()) {
            case LOADING:
                break;
            case ERROR:
                Error error = resource.getError();
                String message = getString(R.string.error, Objects.requireNonNull(error).getDescription(), error.getCode());
                Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
                break;
        }
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
        View view = inflater.inflate(R.layout.fragment_routine_execution_other, container, false);

        exerciseRecycler = view.findViewById(R.id.routine_execution_recycler);
        exerciseRecycler.setHasFixedSize(true);
        exerciseRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        doneLayout = view.findViewById(R.id.finishRoutineLayout);
        FloatingActionButton doneBtn = view.findViewById(R.id.finishRoutineButton);
        FloatingActionButton detailBtn = view.findViewById(R.id.exerciseDetailBtn);

        doneBtn.setOnClickListener(l -> {
            // done
        });

        detailBtn.setOnClickListener(l -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true);
            transaction.replace(R.id.frame_container, new RoutineExecutionFragment(routineId, routineName, currentCycle, currentExercise,  currentRound));
            transaction.commit();
        });

        App app = (App) requireActivity().getApplication();
        exerciseItems = new ArrayList<>();

        this.rout = new FullRoutine(routineId);
        rout.fillData(app, getViewLifecycleOwner(), this::fillExercises, this::defaultResourceHandler);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
