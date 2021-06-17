package ar.edu.itba.fitness.buddy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.model.CycleCard;


public class CycleCardAdapter extends RecyclerView.Adapter<CycleCardAdapter.ParentViewHolder> {
    List<CycleCard> cycles;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public CycleCardAdapter(List<CycleCard> cycles) {
        this.cycles = cycles;
    }

    @NonNull
    @NotNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_cycle_item, parent, false);
        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ParentViewHolder holder, int position) {
        CycleCard parentItem = cycles.get(position);
        holder.name.setText(parentItem.getCycle().getName());
        holder.series.setText(String.valueOf(parentItem.getCycle().getRepetitions()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.ChildRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setInitialPrefetchItemCount(parentItem.getExerciseCards().size());
        ExerciseCardAdapter exerciseCardAdapter= new ExerciseCardAdapter(parentItem.getExerciseCards());
        holder.ChildRecyclerView.setLayoutManager(layoutManager);
        holder.ChildRecyclerView.setAdapter(exerciseCardAdapter);
        holder.ChildRecyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return cycles.size();
    }

    public class ParentViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView name, series;
        private final RecyclerView ChildRecyclerView;
        public ParentViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.cycle_name);
            series=itemView.findViewById(R.id.series_number);
            ChildRecyclerView=itemView.findViewById(R.id.exercise_recycler);
        }
    }


}
