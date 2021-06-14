package ar.edu.itba.fitness.buddy.navigation.routine;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.adapter.CycleCardAdapter;
import ar.edu.itba.fitness.buddy.api.model.Cycle;
import ar.edu.itba.fitness.buddy.api.model.Error;
import ar.edu.itba.fitness.buddy.api.model.Exercise;
import ar.edu.itba.fitness.buddy.api.model.Media;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import ar.edu.itba.fitness.buddy.api.repository.Resource;
import ar.edu.itba.fitness.buddy.api.repository.Status;
import ar.edu.itba.fitness.buddy.model.CycleCard;
import ar.edu.itba.fitness.buddy.model.ExerciseCard;

public class RoutinePreviewFragment extends Fragment {

    CycleCard warmupCycle,cooldownCycle;
    String name;
    ArrayList<CycleCard> workoutCycles=new ArrayList<>();
    RecyclerView cycleRecycler;
    RecyclerView.Adapter<CycleCardAdapter.ViewHolder> adapter;
    FloatingActionButton routineExecutor;
    int id;

    public RoutinePreviewFragment(int id,String name) {
        this.id = id;
        this.name=name;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routine_preview, container, false);
        View warmupView = view.findViewById(R.id.warmup_cycle);
        View cooldownView= view.findViewById(R.id.cooldown_cycle);
        cycleRecycler = view.findViewById(R.id.cycle_recycler);
        cycleRecycler.setHasFixedSize(true);
        cycleRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        fillData();
        return view;
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

    void fillData(){
        App app = (App)requireActivity().getApplication();
        app.getRoutineRepository().getRoutineCycles(id,0,10,"id","asc").observe(getViewLifecycleOwner(), r -> {
            if (r.getStatus() == Status.SUCCESS) {//get cycles within routine
                PagedList<Cycle> cyclePage = r.getData();
                if(cyclePage != null) {
                    ArrayList<Cycle> cycles= (ArrayList<Cycle>)cyclePage.getContent();
                    cycles.forEach(cycle -> {
                        app.getCycleRepository().getCycleExercises(cycle.getId(),0,10,"exerciseId","asc").observe(getViewLifecycleOwner(),e->{
                            if(e.getStatus()==Status.SUCCESS){//get exercises within cycle
                                PagedList<Exercise> exercisePage=e.getData();
                                if(exercisePage!=null){
                                    ArrayList<ExerciseCard> exercises_aux=new ArrayList<>();
                                    ArrayList<Exercise> exercises=(ArrayList<Exercise>) exercisePage.getContent();
                                    exercises.forEach(exercise -> {
                                        app.getExerciseRepository().getExerciseImage(exercise.getExercise().getId(),1).observe(getViewLifecycleOwner(),f->{
                                            if(f.getStatus()==Status.SUCCESS){//get image within exercise
                                                assert f.getData() != null;
                                                exercises_aux.add(new ExerciseCard(exercise.getExercise().getName(),
                                                            exercise.getDuration(),exercise.getRepetitions(),f.getData().getUrl()));
                                            } else {
                                                defaultResourceHandler(f);
                                            }
                                        });
                                    });
                                    if(cycle.getType().compareTo("warmup")==0){
                                        warmupCycle=new CycleCard(cycle.getId(),cycle.getName(),cycle.getRepetitions(),exercises_aux);

                                    }else if(cycle.getType().compareTo("cooldown")==0){
                                        cooldownCycle=new CycleCard(cycle.getId(),cycle.getName(),cycle.getRepetitions(),exercises_aux);
                                    }else{
                                        workoutCycles.add(new CycleCard(cycle.getId(), cycle.getName(),cycle.getRepetitions(),exercises_aux));
                                    }
                                }
                            }else {
                                defaultResourceHandler(e);
                            }
                        });
                    });
                    adapter = new CycleCardAdapter(workoutCycles);
                    cycleRecycler.setAdapter(adapter);
                }
            } else {
                defaultResourceHandler(r);
            }
        });
    }
}