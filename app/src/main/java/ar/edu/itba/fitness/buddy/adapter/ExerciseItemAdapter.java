package ar.edu.itba.fitness.buddy.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.model.ExerciseItem;

public class ExerciseItemAdapter extends RecyclerView.Adapter<ExerciseItemAdapter.ViewHolder> {
    private final OnExerciseItemListener exerciseListener;
    private ArrayList<ExerciseItem> exercises;

    public ExerciseItemAdapter(ArrayList<ExerciseItem> exercises, OnExerciseItemListener exerciseListener) {
        this.exercises = exercises;
        this.exerciseListener = exerciseListener;
    }

    @NonNull
    @Override
    public ExerciseItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_execution_list_item, parent,false);
        return new ExerciseItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseItemAdapter.ViewHolder holder, int position) {
        ExerciseItem exercise = exercises.get(position);
        holder.name.setText(exercise.getName());

        String str = "-";
        if (exercise.getRepetitions() != 0){
            str = Integer.toString(exercise.getRepetitions());
        }
        holder.repetitions.setText(str);

        str = "-";
        if (exercise.getDuration() != 0){
            str = Integer.toString(exercise.getDuration());
        }
        holder.duration.setText(str);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppCompatTextView name, repetitions, duration;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.exercise_name);
            repetitions = itemView.findViewById(R.id.repetitions_number);
            duration = itemView.findViewById(R.id.duration_number);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            exerciseListener.onExerciseClick(getBindingAdapterPosition());
        }
    }

    public interface OnExerciseItemListener {
        void onExerciseClick(int position);
    }
}
