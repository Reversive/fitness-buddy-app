package ar.edu.itba.fitness.buddy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.model.ExerciseCard;

public class ExerciseCardAdapter extends RecyclerView.Adapter<ExerciseCardAdapter.ViewHolder>{

    ArrayList<ExerciseCard> exerciseCardArrayList;

    public ExerciseCardAdapter(ArrayList<ExerciseCard> exerciseCardArrayList) {
        this.exerciseCardArrayList = exerciseCardArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ExerciseCardAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_exercise_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ExerciseCardAdapter.ViewHolder holder, int position) {
        ExerciseCard exerciseCard=exerciseCardArrayList.get(position);
        holder.exercise_name.setText(exerciseCard.getExercise_name());
        if(exerciseCard.getRepetitions()==0){
            holder.repetitions.setText("-");
            holder.duration.setText(String.valueOf(exerciseCard.getDuration()));
        }else if(exerciseCard.getDuration()==0){
            holder.repetitions.setText(String.valueOf(exerciseCard.getRepetitions()));
            holder.duration.setText("-");
        }
        else
            holder.repetitions.setText(String.valueOf(exerciseCard.getRepetitions()));
       // holder.exercise_image.;
    }

    @Override
    public int getItemCount() {
        return exerciseCardArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView exercise_name, repetitions,duration;
        //ImageView exercise_image;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            exercise_name=itemView.findViewById(R.id.exercise_name);
            repetitions=itemView.findViewById(R.id.repetitions_number);
            duration=itemView.findViewById(R.id.duration_number);
            //exercise_image=itemView.findViewById(R.id.exercise_image);
        }
    }
}
