package ar.edu.itba.fitness.buddy.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.navigation.community_routines.CommunityRoutinesFragment;
import ar.edu.itba.fitness.buddy.navigation.favorites.FavoriteFragment;
import ar.edu.itba.fitness.buddy.navigation.profile.ProfileFragment;

public class MainNavigationActivity extends AppCompatActivity {

    CommunityRoutinesFragment communityRoutinesFragment = new CommunityRoutinesFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    FavoriteFragment favoriteFragment = new FavoriteFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(communityRoutinesFragment);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.communityRoutinesFragment:
                    loadFragment(communityRoutinesFragment);
                    return true;
                case R.id.profileFragment:
                    loadFragment(profileFragment);
                    return true;
                case R.id.favoriteFragment:
                    loadFragment(favoriteFragment);
                    return true;
            }
            return false;
        }
    };

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}