package ar.edu.itba.fitness.buddy.navigation.routine;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.adapter.CycleCardAdapter;
import ar.edu.itba.fitness.buddy.api.model.Cycle;
import ar.edu.itba.fitness.buddy.api.model.Error;
import ar.edu.itba.fitness.buddy.api.model.Exercise;
import ar.edu.itba.fitness.buddy.api.model.Media;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import ar.edu.itba.fitness.buddy.api.model.Routine;
import ar.edu.itba.fitness.buddy.api.repository.Resource;
import ar.edu.itba.fitness.buddy.api.repository.Status;
import ar.edu.itba.fitness.buddy.model.CycleCard;
import ar.edu.itba.fitness.buddy.model.ExerciseCard;

public class RoutinePreviewFragment extends Fragment {

    private final String name;
    private final ArrayList<CycleCard> workoutCycles = new ArrayList<>();
    private RecyclerView cycleRecycler;
    private RecyclerView.Adapter<CycleCardAdapter.ViewHolder> adapter;
    private final int id;
    private boolean isFavorite = false;

    public RoutinePreviewFragment(int id, String name) {
        this.id = id;
        this.name=name;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(this.name);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.routine_preview_toolbar, menu);
        MenuItem favItem = menu.findItem(R.id.action_favorite);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        favItem.setIcon(R.drawable.ic_favorite_border);
        App app = (App)requireActivity().getApplication();
        app.getFavoriteRepository().getFavorites(0, 10).observe(getViewLifecycleOwner(), f -> {
            if(f.getStatus() == Status.SUCCESS) {
                PagedList<Routine> favoritePagedList = f.getData();
                if(favoritePagedList != null) {
                    List<Routine> favoriteRoutines = new ArrayList<>(favoritePagedList.getContent());
                    favoriteRoutines.forEach(r -> { if(r.getId() == this.id) {
                        favItem.setIcon(R.drawable.ic_favorite);
                        isFavorite = true;
                    }});
                }
            } else {
                defaultResourceHandler(f);
            }
        });

        favItem.setOnMenuItemClickListener(menuItem -> {
            if(this.isFavorite) {
                app.getFavoriteRepository().unsetFavorite(this.id).observe(getViewLifecycleOwner(), u -> {
                    if(u.getStatus() == Status.SUCCESS)
                        Toast.makeText(app, "Removed from favorites", Toast.LENGTH_SHORT).show();
                    else
                        defaultResourceHandler(u);
                });
                menuItem.setIcon(R.drawable.ic_favorite_border);
                this.isFavorite = false;
            } else {
                app.getFavoriteRepository().setFavorite(this.id).observe(getViewLifecycleOwner(), u -> {
                    if(u.getStatus() == Status.SUCCESS)
                        Toast.makeText(app, "Added to favorites", Toast.LENGTH_SHORT).show();
                    else
                        defaultResourceHandler(u);
                });
                menuItem.setIcon(R.drawable.ic_favorite);
                this.isFavorite = true;
            }
            return false;
        });

        shareItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "fitness-buddy://" + id);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routine_preview, container, false);

        FloatingActionButton routineExecutor = view.findViewById(R.id.routine_executor);
        for(int i=0;i<3;i++){
            switch (i){
                case 0: cycleRecycler = view.findViewById(R.id.warmup_cycle_recycler);
                    break;
                case 1: cycleRecycler = view.findViewById(R.id.exercise_cycle_recycler);
                    break;
                case 2: cycleRecycler = view.findViewById(R.id.cooldown_cycle_recycler);
                    break;
            }
            cycleRecycler.setHasFixedSize(true);
            cycleRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
            getWorkoutCycles(i);
        }

        routineExecutor.setOnClickListener((l) -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true);
            transaction.replace(R.id.frame_container, new RoutineExecutionListFragment(this.id, this.name));
            transaction.commit();
        });



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
   void getWorkoutCycles(int recyclerIndex){
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
                                   switch (recyclerIndex){
                                       case 0: if(cycle.getType().compareTo("warmup")==0) {
                                           workoutCycles.add(new CycleCard(cycle.getId(),cycle.getName(),cycle.getRepetitions(),exerciseCards));
                                       }break;
                                       case 1: workoutCycles.add(new CycleCard(cycle.getId(),cycle.getName(),cycle.getRepetitions(),exerciseCards));
                                           break;
                                       case 2:  if(cycle.getType().compareTo("cooldown")==0) {
                                           workoutCycles.add(new CycleCard(cycle.getId(),cycle.getName(),cycle.getRepetitions(),exerciseCards));
                                       }break;
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