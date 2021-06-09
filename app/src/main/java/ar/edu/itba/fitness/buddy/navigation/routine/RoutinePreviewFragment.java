package ar.edu.itba.fitness.buddy.navigation.routine;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.edu.itba.fitness.buddy.R;

public class RoutinePreviewFragment extends Fragment {


    public RoutinePreviewFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_routine_preview, container, false);
    }
}