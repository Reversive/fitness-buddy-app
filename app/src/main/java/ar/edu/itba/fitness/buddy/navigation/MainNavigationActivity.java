package ar.edu.itba.fitness.buddy.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.navigation.community.CommunityRoutinesFragment;
import ar.edu.itba.fitness.buddy.navigation.favorites.FavoriteFragment;
import ar.edu.itba.fitness.buddy.navigation.personal.PersonalRoutinesFragment;
import ar.edu.itba.fitness.buddy.navigation.profile.ProfileFragment;
import ar.edu.itba.fitness.buddy.navigation.routine.RoutinePreviewFragment;
import ar.edu.itba.fitness.buddy.splash.login.LoginActivity;

public class MainNavigationActivity extends AppCompatActivity {

    CommunityRoutinesFragment communityRoutinesFragment = new CommunityRoutinesFragment();
    PersonalRoutinesFragment personalRoutinesFragment = new PersonalRoutinesFragment();
    FavoriteFragment favoriteFragment = new FavoriteFragment();

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App app = (App)getApplication();
        if(app.getPreferences().getAuthToken() == null) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
        setContentView(R.layout.activity_main_navigation);
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(ContextCompat.getColor(this, R.color.purple_web));
        Objects.requireNonNull(actionBar).setBackgroundDrawable(colorDrawable);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Uri uri = this.getIntent().getData();
        if (uri != null) {
            String[] params = uri.getEncodedQuery().split(",");
            int routineId = Integer.parseInt(params[0]);
            String routineName = params[1].replaceAll("\\+", " ");
            loadFragment(new RoutinePreviewFragment(routineId, routineName));
        } else {
            if (savedInstanceState == null) loadFragment(communityRoutinesFragment);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.communityRoutinesFragment:
                loadFragment(communityRoutinesFragment);
                return true;
            case R.id.personalRoutinesFragment:
                loadFragment(personalRoutinesFragment);
                return true;
            case R.id.favoriteFragment:
                loadFragment(favoriteFragment);
                return true;
        }
        return false;
    };

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setReorderingAllowed(true);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}