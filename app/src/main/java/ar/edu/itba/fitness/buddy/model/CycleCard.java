package ar.edu.itba.fitness.buddy.model;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CycleCard {
    int id;
    String name;
    int series_number;
    ArrayList<ExerciseCard> exerciseList;

    public CycleCard(int id,String name, int series_number,ArrayList<ExerciseCard> exercises) {
        this.id=id;
        this.name = name;
        this.series_number = series_number;
        this.exerciseList=exercises;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSeries_number() {
        return series_number;
    }

    public ArrayList<ExerciseCard> getExerciseList() {
        return exerciseList;
    }

}
