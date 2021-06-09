package ar.edu.itba.fitness.buddy.navigation.community_routines;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.adapter.RoutineCardAdapter;
import ar.edu.itba.fitness.buddy.helper.RoutineCard;


public class CommunityRoutinesFragment extends Fragment {

    RecyclerView routineRecycler;
    RecyclerView.Adapter<RoutineCardAdapter.RoutineCardViewHolder> adapter;
    public CommunityRoutinesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);
        // set search filter
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_routines, container, false);
        routineRecycler = view.findViewById(R.id.routine_recycler);
        routineRecycler.setHasFixedSize(true);
        routineRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        ArrayList<RoutineCard> routineCards = new ArrayList<>();
        routineCards.add(new RoutineCard("First Routine", "Rookie", "Weight Loss", 5));
        routineCards.add(new RoutineCard("Second Routine", "Intermediate", "Muscle Gain", 3));
        routineCards.add(new RoutineCard("Third Routine", "Rookie", "Muscle Gain", 2));
        routineCards.add(new RoutineCard("Fourth Routine", "Advanced", "Weight Loss", 4));
        routineCards.add(new RoutineCard("Fifth Routine", "Intermediate", "Muscle Gain", 5));
        routineCards.add(new RoutineCard("Sixth Routine", "Rookie", "Weight Loss", 3));
        adapter = new RoutineCardAdapter(routineCards);
        routineRecycler.setAdapter(adapter);
        return view;
    }
}