package ar.edu.itba.fitness.buddy.navigation.routine;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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

    private CycleCard warmupCycle,cooldownCycle;
    private final String name;
    private final ArrayList<CycleCard> workoutCycles = new ArrayList<>();
    private RecyclerView cycleRecycler;
    private RecyclerView.Adapter<CycleCardAdapter.ViewHolder> adapter;
    private final int id;

    public RoutinePreviewFragment(int id, String name) {
        this.id = id;
        this.name=name;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routine_preview, container, false);
        View warmupView = view.findViewById(R.id.warmup_cycle);
        View cooldownView = view.findViewById(R.id.cooldown_cycle);

        FloatingActionButton routineExecutor = view.findViewById(R.id.routine_executor);
        cycleRecycler = view.findViewById(R.id.cycle_recycler);
        cycleRecycler.setHasFixedSize(true);
        cycleRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        routineExecutor.setOnClickListener((l) -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true);
            transaction.replace(R.id.frame_container, new RoutineExecutionFragment(this.id, this.name));
            transaction.commit();
        });

        getWorkoutCycles();

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
   /* ArrayList<ExerciseCard> getExercises(int cycleId){
        ArrayList<ExerciseCard> exerciseCards=new ArrayList<>();
        App app = (App)requireActivity().getApplication();
        app.getCycleRepository().getCycleExercises(cycleId,0,10,"exerciseId","asc").observe(getViewLifecycleOwner(),e->{
            if(e.getStatus()==Status.SUCCESS){
                PagedList<Exercise> exercisePagedList=e.getData();
                if(exercisePagedList!=null){
                    ArrayList<Exercise> exercises=(ArrayList<Exercise>) exercisePagedList.getContent();
                    exercises.forEach(exercise -> {
                        ExerciseCard exerciseCard=new ExerciseCard(exercise.getExercise().getName(),
                                exercise.getDuration(),exercise.getRepetitions());
                        exerciseCards.add(exerciseCard);
                    });
                }
            }else{
                defaultResourceHandler(e);
            }
        });
    }*/
    void getWorkoutCycles(){
        App app = (App)requireActivity().getApplication();
        app.getRoutineRepository().getRoutineCycles(id,0,10,"id","asc").observe(getViewLifecycleOwner(), e -> {
            if (e.getStatus() == Status.SUCCESS) {
                PagedList<Cycle> cyclePage = e.getData();
                if(cyclePage != null) {
                    ArrayList<Cycle> cycles= (ArrayList<Cycle>)cyclePage.getContent();
                    ArrayList<CycleCard> workoutCycles=new ArrayList<>();
                    cycles.forEach(cycle -> {
                        app.getCycleRepository().getCycleExercises(cycle.getId(),0,10,"exerciseId","asc").observe(getViewLifecycleOwner(),f->{
                            if(f.getStatus()==Status.SUCCESS){
                                PagedList<Exercise> exercisePagedList=f.getData();
                                if(exercisePagedList!=null){
                                    ArrayList<Exercise> exercises=(ArrayList<Exercise>) exercisePagedList.getContent();
                                    ArrayList<ExerciseCard> exerciseCards=new ArrayList<>();
                                    exercises.forEach(exercise -> {
                                        ExerciseCard exerciseCard=new ExerciseCard(exercise.getExercise().getName(),
                                                exercise.getDuration(),exercise.getRepetitions());
                                        exerciseCards.add(exerciseCard);
                                    });
                                    if(cycle.getType().compareTo("warmup")==0){
                                        warmupCycle=new CycleCard(cycle.getId(),cycle.getName(),cycle.getRepetitions(),exerciseCards);
                                    }else if(cycle.getType().compareTo("cooldown")==0){
                                        cooldownCycle=new CycleCard(cycle.getId(),cycle.getName(),cycle.getRepetitions(),exerciseCards);
                                    }else{
                                        CycleCard cycleCard=new CycleCard(cycle.getId(),cycle.getName(),cycle.getRepetitions(),exerciseCards);
                                        workoutCycles.add(cycleCard);
                                    }
                                }
                            }else{
                                defaultResourceHandler(f);
                            }
                        });
                    });
                    adapter = new CycleCardAdapter(workoutCycles);
                    cycleRecycler.setAdapter(adapter);
                }
            }else {
                defaultResourceHandler(e);
            }
        });
    }

}