package ar.edu.itba.fitness.buddy.navigation.community_routines;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_routines, container, false);
        routineRecycler = view.findViewById(R.id.routine_recycler);
        routineRecycler.setHasFixedSize(true);
        routineRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        ArrayList<RoutineCard> routineCards = new ArrayList<>();
        routineCards.add(new RoutineCard("Primera Rutina", "Rookie", "Weight Loss", 5));
        routineCards.add(new RoutineCard("Segunda Rutina", "Intermediate", "Muscle Gain", 3));
        routineCards.add(new RoutineCard("Tercera Rutina", "Rookie", "Muscle Gain", 2));
        routineCards.add(new RoutineCard("Cuarta Rutina", "Advanced", "Weight Loss", 4));
        routineCards.add(new RoutineCard("Quinta Rutina", "Intermediate", "Muscle Gain", 5));
        routineCards.add(new RoutineCard("Sexta Rutina", "Rookie", "Weight Loss", 3));
        adapter = new RoutineCardAdapter(routineCards);
        routineRecycler.setAdapter(adapter);
        return view;
    }
}