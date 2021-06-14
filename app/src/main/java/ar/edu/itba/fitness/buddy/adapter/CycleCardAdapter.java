package ar.edu.itba.fitness.buddy.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.model.CycleCard;
import ar.edu.itba.fitness.buddy.model.ExerciseCard;

public class CycleCardAdapter extends RecyclerView.Adapter<CycleCardAdapter.ViewHolder> {

    ArrayList<CycleCard> listCycles;
    Context context;

    public CycleCardAdapter(ArrayList<CycleCard> listCycles) {
        this.listCycles = listCycles;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_cycle_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        CycleCard cycleCard = listCycles.get(position);
        holder.cycle_name.setText(cycleCard.getName());
        holder.series_number.setText(String.valueOf( cycleCard.getSeries_number()));

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        holder.nested_rv.setLayoutManager(layoutManager);
        holder.nested_rv.setHasFixedSize(true);
        ExerciseCardAdapter exerciseCardAdapter= new ExerciseCardAdapter(cycleCard.getExerciseList());
        holder.nested_rv.setAdapter(exerciseCardAdapter);
        exerciseCardAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listCycles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView cycle_name,series_number;
        RecyclerView nested_rv;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cycle_name=itemView.findViewById(R.id.exercise_name);
            series_number=itemView.findViewById(R.id.series_number);
            nested_rv=itemView.findViewById(R.id.exercise_recycler);
        }
    }
}
