package ar.edu.itba.fitness.buddy.navigation.personal;

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

import java.util.ArrayList;
import java.util.Objects;

import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.adapter.RoutineCardAdapter;
import ar.edu.itba.fitness.buddy.model.RoutineCard;
import ar.edu.itba.fitness.buddy.navigation.routine.RoutinePreviewFragment;


public class PersonalRoutinesFragment extends Fragment implements RoutineCardAdapter.OnRoutineCardListener{
    ArrayList<RoutineCard> routineCards;
    RecyclerView routineRecycler;
    RecyclerView.Adapter<RoutineCardAdapter.RoutineCardViewHolder> adapter;

    public PersonalRoutinesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(R.string.personal_routines);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_routines, container, false);
        routineRecycler = view.findViewById(R.id.personal_routine_recycler);
        routineRecycler.setHasFixedSize(true);
        routineRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        routineCards = new ArrayList<>();
        routineCards.add(new RoutineCard(1, "First Personal", "Rookie", "Weight Loss", 5));
        routineCards.add(new RoutineCard(2, "Second Personal", "Intermediate", "Muscle Gain", 3));
        routineCards.add(new RoutineCard(3,"Third Personal", "Rookie", "Muscle Gain", 2));
        routineCards.add(new RoutineCard(4, "Fourth Personal", "Advanced", "Weight Loss", 4));
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