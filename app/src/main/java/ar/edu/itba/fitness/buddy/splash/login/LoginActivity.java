package ar.edu.itba.fitness.buddy.splash.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.navigation.MainNavigationActivity;
import ar.edu.itba.fitness.buddy.splash.SplashScreenActivity;
import ar.edu.itba.fitness.buddy.splash.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    public void callSplashScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
        startActivity(intent);
    }

    public void callLoginVerification(View view) {
        Intent intent = new Intent(getApplicationContext(), MainNavigationActivity.class);
        startActivity(intent);
    }

    public void callSignUpScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
}