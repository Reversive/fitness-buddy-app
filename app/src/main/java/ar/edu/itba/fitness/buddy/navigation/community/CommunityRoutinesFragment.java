package ar.edu.itba.fitness.buddy.navigation.community;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.adapter.RoutineCardAdapter;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import ar.edu.itba.fitness.buddy.api.model.Routine;
import ar.edu.itba.fitness.buddy.api.repository.Resource;
import ar.edu.itba.fitness.buddy.api.repository.Status;
import ar.edu.itba.fitness.buddy.model.RoutineCard;
import ar.edu.itba.fitness.buddy.navigation.routine.RoutinePreviewFragment;
import ar.edu.itba.fitness.buddy.api.model.Error;

public class CommunityRoutinesFragment extends Fragment implements RoutineCardAdapter.OnRoutineCardListener{
    ArrayList<RoutineCard> routineCards;
    RecyclerView routineRecycler;
    RecyclerView.Adapter<RoutineCardAdapter.RoutineCardViewHolder> adapter;
    public CommunityRoutinesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(R.string.community_routines);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_routines, container, false);
        routineRecycler = view.findViewById(R.id.community_routine_recycler);
        routineRecycler.setHasFixedSize(true);
        routineRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        App app = (App)requireActivity().getApplication();
        routineCards = new ArrayList<>();
        app.getRoutineRepository().getRoutines(null, null, 0, 10, "date", "asc").observe(getViewLifecycleOwner(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                PagedList<Routine> routinePage = r.getData();
                if(routinePage != null) {
                    ArrayList<Routine> routines = (ArrayList<Routine>)routinePage.getContent();
                    routines.forEach(routine -> {
                        routineCards.add(new RoutineCard(routine.getId(), routine.getName(), routine.getDifficulty(), routine.getCategory().toString(), routine.getAverageRating()));
                    });
                    adapter = new RoutineCardAdapter(routineCards, this);
                    routineRecycler.setAdapter(adapter);
                }
            } else {
                defaultResourceHandler(r);
            }
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

    @Override
    public void onRoutineCardClick(int position) {
        RoutineCard clickedRoutine = routineCards.get(position);
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true);
        transaction.replace(R.id.frame_container, new RoutinePreviewFragment());
        transaction.commit();
    }
}