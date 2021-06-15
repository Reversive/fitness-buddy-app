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
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import ar.edu.itba.fitness.buddy.model.FullRoutine;
import ar.edu.itba.fitness.buddy.model.RoutineCard;

public class RoutineExecutionListFragment extends Fragment {

    private final int routineId;
    private final String routineName;
    private ArrayList<ExerciseItem> exerciseItems;
    private FullRoutine rout;
    private ExerciseItemAdapter adapter;
    RecyclerView exerciseRecycler;

    public RoutineExecutionListFragment(int id, String name) {
        this.routineId = id;
        this.routineName = name;
    }

    private void fillExercises() {
        for (int i = 0; i < rout.getCycles(); i++) {
            ArrayList<Exercise> e = rout.getCycle(i).getExercises();
            e.forEach(exercise ->
                    exerciseItems.add(
                            new ExerciseItem(
                                    exercise.getExercise().getName(),
                                    exercise.getDuration(),
                                    exercise.getRepetitions()
                            )
                    )
            );
            adapter = new ExerciseItemAdapter(exerciseItems);
            exerciseRecycler.setAdapter(adapter);
        }
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
        App app = (App)requireActivity().getApplication();
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
