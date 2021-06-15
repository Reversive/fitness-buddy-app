package ar.edu.itba.fitness.buddy.navigation.routine;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.adapter.ExerciseItemAdapter;
import ar.edu.itba.fitness.buddy.adapter.RoutineCardAdapter;
import ar.edu.itba.fitness.buddy.api.model.Cycle;
import ar.edu.itba.fitness.buddy.api.model.Error;
import ar.edu.itba.fitness.buddy.api.model.Exercise;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import ar.edu.itba.fitness.buddy.api.model.Routine;
import ar.edu.itba.fitness.buddy.api.repository.Resource;
import ar.edu.itba.fitness.buddy.api.repository.Status;
import ar.edu.itba.fitness.buddy.model.ExerciseItem;
import ar.edu.itba.fitness.buddy.model.FullCycle;
import ar.edu.itba.fitness.buddy.model.FullRoutine;
import ar.edu.itba.fitness.buddy.model.RoutineCard;

public class RoutineExecutionListFragment extends Fragment {
    static int EXERCISES_SHOWN = 3;

    private final int routineId;
    private final String routineName;
    private ArrayList<ExerciseItem> exerciseItems;
    private FullRoutine rout;
    private ExerciseItemAdapter adapter;

    private int currentCycle;
    private int currentExercise;

    RecyclerView exerciseRecycler;

    public RoutineExecutionListFragment(int id, String name) {
        this.routineId = id;
        this.routineName = name;
        this.currentCycle = 0;
        this.currentExercise = 0;
    }

    private void fillExercises() {
        exerciseItems = new ArrayList<>();
        int added = 0;
        boolean done = false;
        boolean first = true;
        int i, j;
        for (i = currentCycle; i < rout.getCycles() && !done; i++) {
            ArrayList<Exercise> e = rout.getCycle(i).getExercises();
            for (j = 0; j < e.size() && !done; j++) {
                if (first) {
                    j = currentExercise;
                    first = false;
                }
                Exercise exercise = e.get(j);
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

        adapter = new ExerciseItemAdapter(exerciseItems, this::navToExercise);
        exerciseRecycler.setAdapter(adapter);
    }

    private void navToExercise(int position) {
        if (position == 0)
            return;

        currentExercise += position;
        while (currentExercise >= rout.getCycle(currentCycle).getExercises().size()) {
            currentExercise -= rout.getCycle(currentCycle).getExercises().size();
            currentCycle++;
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

        FloatingActionButton doneBtn = view.findViewById(R.id.finishRoutineButton);
        FloatingActionButton detailBtn = view.findViewById(R.id.exerciseDetailBtn);

        doneBtn.setOnClickListener(l -> {

        });

        detailBtn.setOnClickListener(l -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true);
            FullCycle cycle = rout.getCycle(currentCycle);
            transaction.replace(R.id.frame_container, new RoutineExecutionFragment(routineId, routineName, currentExercise, cycle.getId(), cycle.getRepetitions()));
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
