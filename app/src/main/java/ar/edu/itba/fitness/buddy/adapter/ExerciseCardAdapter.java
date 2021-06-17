package ar.edu.itba.fitness.buddy.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.model.ExerciseCard;

public class ExerciseCardAdapter extends RecyclerView.Adapter<ExerciseCardAdapter.ViewHolder> {

    private final List<ExerciseCard> exerciseCardList;

    @NonNull
    @Override
    public ExerciseCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_exercise_item, parent,false);
        return new ExerciseCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ExerciseCard exercise = exerciseCardList.get(position);
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

        Log.d("IMAGE", exercise.getImage().getUrl());

        Picasso.get().load(exercise.getImage().getUrl()).into(holder.image);
    }

    public ExerciseCardAdapter(ArrayList<ExerciseCard> exerciseCardList) {
        this.exerciseCardList = exerciseCardList;
    }


    @Override
    public int getItemCount() {
        return exerciseCardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView name, repetitions, duration;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.exercise_name);
            repetitions = itemView.findViewById(R.id.repetitions_number);
            duration = itemView.findViewById(R.id.duration_number);
            image = itemView.findViewById(R.id.exercise_image);
        }
    }
}
