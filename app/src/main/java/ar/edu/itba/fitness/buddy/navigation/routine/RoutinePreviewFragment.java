package ar.edu.itba.fitness.buddy.navigation.routine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.adapter.CycleCardAdapter;
import ar.edu.itba.fitness.buddy.api.model.Error;
import ar.edu.itba.fitness.buddy.api.model.Exercise;
import ar.edu.itba.fitness.buddy.api.model.Media;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import ar.edu.itba.fitness.buddy.api.model.Routine;
import ar.edu.itba.fitness.buddy.api.repository.Resource;
import ar.edu.itba.fitness.buddy.api.repository.Status;
import ar.edu.itba.fitness.buddy.model.CycleCard;
import ar.edu.itba.fitness.buddy.model.FullRoutine;


public class RoutinePreviewFragment extends Fragment {

    private final String name;
    private RecyclerView cycleRecycler;
    CycleCardAdapter adapter;
    private final List <CycleCard> warmupCycles=new ArrayList<>();
    private final List<CycleCard> workoutCycles=new ArrayList<>();
    private final List<CycleCard> cooldownCycles=new ArrayList<>();
    private final FullRoutine routine;
    private View view;

    private final int id;
    private boolean isFavorite = false;

    public RoutinePreviewFragment(int id, String name) {
        this.id = id;
        this.name=name;
        routine=new FullRoutine(id);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(this.name);
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(this.name);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.routine_preview_toolbar, menu);
        MenuItem favItem = menu.findItem(R.id.action_favorite);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        favItem.setIcon(R.drawable.ic_favorite_border);
        App app = (App) requireActivity().getApplication();
        app.getFavoriteRepository().getFavorites(0, 10).observe(getViewLifecycleOwner(), f -> {
            if (f.getStatus() == Status.SUCCESS) {
                PagedList<Routine> favoritePagedList = f.getData();
                if (favoritePagedList != null) {
                    List<Routine> favoriteRoutines = new ArrayList<>(favoritePagedList.getContent());
                    favoriteRoutines.forEach(r -> {
                        if (r.getId() == this.id) {
                            favItem.setIcon(R.drawable.ic_favorite);
                            isFavorite = true;
                        }
                    });
                }
            } else {
                defaultResourceHandler(f);
            }
        });

        favItem.setOnMenuItemClickListener(menuItem -> {
            if (this.isFavorite) {
                app.getFavoriteRepository().unsetFavorite(this.id).observe(getViewLifecycleOwner(), u -> {
                    if (u.getStatus() == Status.SUCCESS)
                        Toast.makeText(app, "Removed from favorites", Toast.LENGTH_SHORT).show();
                    else
                        defaultResourceHandler(u);
                });
                menuItem.setIcon(R.drawable.ic_favorite_border);
                this.isFavorite = false;
            } else {
                app.getFavoriteRepository().setFavorite(this.id).observe(getViewLifecycleOwner(), u -> {
                    if (u.getStatus() == Status.SUCCESS)
                        Toast.makeText(app, "Added to favorites", Toast.LENGTH_SHORT).show();
                    else
                        defaultResourceHandler(u);
                });
                menuItem.setIcon(R.drawable.ic_favorite);
                this.isFavorite = true;
            }
            return false;
        });

        shareItem.setOnMenuItemClickListener(menuItem -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            String tmpName = name.replaceAll(" ", "+");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "http://fitness-buddy.com/?" + id + "," + tmpName);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return false;
        });
    }

    private void gotData() {
        ArrayList<ArrayList<Media>> imageList = new ArrayList<>();
        getImagesRec(imageList, 0, 0, () -> filterCycles(imageList));
    }

    private void getImagesRec(ArrayList<ArrayList<Media>> imageList, int cycleIdx, int exerciseIdx, Runnable callback) {
        if (exerciseIdx == routine.getCycle(cycleIdx).getExercises().size()) {
            cycleIdx = cycleIdx + 1;
            exerciseIdx = 0;
        }

        if (cycleIdx == routine.getCycles()) {
            callback.run();
            return;
        }

        if (exerciseIdx == 0)
            imageList.add(new ArrayList<>());

        Exercise exercise = routine.getCycle(cycleIdx).getExercises().get(exerciseIdx);
        App app = (App) requireActivity().getApplication();
        int finalCycleIdx = cycleIdx;
        int finalExerciseIdx = exerciseIdx;
        app.getExerciseRepository().getExerciseImages(exercise.getExercise().getId(), null, 0, 1, null, null).observe(getViewLifecycleOwner(), (r) -> {
            if (r.getStatus() == Status.SUCCESS) {
                PagedList<Media> list = r.getData();
                if (list != null) {
                    Media media = list.getContent().get(0);
                    imageList.get(finalCycleIdx).add(media);
                    getImagesRec(imageList, finalCycleIdx, finalExerciseIdx + 1, callback);
                }
            } else {
                defaultResourceHandler(r);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        App app = (App)requireActivity().getApplication();
        routine.fillData(app,getViewLifecycleOwner(),this::gotData,this::defaultResourceHandler);
        view = inflater.inflate(R.layout.fragment_routine_preview, container, false);
        FloatingActionButton routineExecutor = view.findViewById(R.id.routine_executor);

        routineExecutor.setOnClickListener((l) -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true);
            transaction.replace(R.id.frame_container, new RoutineExecutionFragment(this.id, this.name));
            transaction.addToBackStack(null);
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

    private void filterCycles(ArrayList<ArrayList<Media>> imageList) {
        for(int i=0;i < routine.getCycles(); i++){
            ArrayList<Media> images = imageList.get(i);
            if(routine.getCycle(i).getType().compareTo("warmup")==0)
                warmupCycles.add(new CycleCard(routine.getCycle(i), images));
            else if(routine.getCycle(i).getType().compareTo("cooldown")==0)
                cooldownCycles.add(new CycleCard(routine.getCycle(i), images));
            else
                workoutCycles.add(new CycleCard(routine.getCycle(i), images));
        }

        for(int i=0;i<3;i++){
            switch (i){
                case 0: cycleRecycler = view.findViewById(R.id.warmup_cycle_recycler);
                    break;
                case 1:cycleRecycler = view.findViewById(R.id.exercise_cycle_recycler);
                    break;
                case 2:cycleRecycler = view.findViewById(R.id.cooldown_cycle_recycler);
                    break;
            }

            cycleRecycler.setHasFixedSize(true);
            cycleRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

            switch (i) {
                case 0:
                    adapter = new CycleCardAdapter(warmupCycles);
                    break;
                case 1:
                    adapter = new CycleCardAdapter(workoutCycles);
                    break;
                case 2:
                    adapter = new CycleCardAdapter(cooldownCycles);
            }
            cycleRecycler.setAdapter(adapter);

        }
    }
}