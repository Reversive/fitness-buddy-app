package ar.edu.itba.fitness.buddy.navigation.community;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.Objects;

import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.adapter.RoutineCardAdapter;
import ar.edu.itba.fitness.buddy.model.RoutineCard;
import ar.edu.itba.fitness.buddy.navigation.routine.RoutinePreviewFragment;


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
        routineCards = new ArrayList<>();
        routineCards.add(new RoutineCard(1, "First Routine", "Rookie", "Weight Loss", 5));
        routineCards.add(new RoutineCard(2, "Second Routine", "Intermediate", "Muscle Gain", 3));
        routineCards.add(new RoutineCard(3,"Third Routine", "Rookie", "Muscle Gain", 2));
        routineCards.add(new RoutineCard(4, "Fourth Routine", "Advanced", "Weight Loss", 4));
        routineCards.add(new RoutineCard(5, "Fifth Routine", "Intermediate", "Muscle Gain", 5));
        routineCards.add(new RoutineCard(6, "Sixth Routine", "Rookie", "Weight Loss", 3));
        adapter = new RoutineCardAdapter(routineCards, this);
        routineRecycler.setAdapter(adapter);

        return view;
    }

    @Override
    public void onRoutineCardClick(int position) {
        RoutineCard clickedRoutine = routineCards.get(position);
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction().setReorderingAllowed(true);
        transaction.replace(R.id.frame_container, new RoutinePreviewFragment());
        transaction.commit();
        // Send info to other fragment/activity to show routine info
        // Launch fragment/activity
    }
}