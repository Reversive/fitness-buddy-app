package ar.edu.itba.fitness.buddy.navigation.favorites;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.adapter.RoutineCardAdapter;
import ar.edu.itba.fitness.buddy.api.model.Error;
import ar.edu.itba.fitness.buddy.api.model.PagedList;
import ar.edu.itba.fitness.buddy.api.model.Routine;
import ar.edu.itba.fitness.buddy.api.repository.Resource;
import ar.edu.itba.fitness.buddy.api.repository.Status;
import ar.edu.itba.fitness.buddy.model.RoutineCard;
import ar.edu.itba.fitness.buddy.navigation.routine.RoutinePreviewFragment;
import ar.edu.itba.fitness.buddy.splash.login.LoginActivity;


public class FavoriteFragment extends Fragment implements RoutineCardAdapter.OnRoutineCardListener{
    ArrayList<RoutineCard> routineCards;
    RecyclerView routineRecycler;
    RecyclerView.Adapter<RoutineCardAdapter.RoutineCardViewHolder> adapter;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(R.string.favorites);
        App app = (App)requireActivity().getApplication();
        if(app.getPreferences().getAuthToken() == null) {
            Intent i = new Intent(requireContext(), LoginActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(R.string.favorites);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        App app = (App)requireActivity().getApplication();
        if(app.getPreferences().getAuthToken() == null) {
            Intent i = new Intent(requireContext(), LoginActivity.class);
            startActivity(i);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        routineRecycler = view.findViewById(R.id.favorite_recycler);
        routineRecycler.setHasFixedSize(true);
        routineRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        routineCards = new ArrayList<>();
        App app = (App)requireActivity().getApplication();
        app.getFavoriteRepository().getFavorites(0, 10).observe(getViewLifecycleOwner(), r -> {
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
        transaction.replace(R.id.frame_container, new RoutinePreviewFragment(clickedRoutine.getId(), clickedRoutine.getTitle()));
        transaction.addToBackStack(null);
        transaction.commit();
        // Send info to other fragment/activity to show routine info
        // Launch fragment/activity
    }
}