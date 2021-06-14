package ar.edu.itba.fitness.buddy.splash.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.AppPreferences;
import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.api.model.ApiResponse;
import ar.edu.itba.fitness.buddy.api.model.Credentials;
import ar.edu.itba.fitness.buddy.api.model.Token;
import ar.edu.itba.fitness.buddy.api.repository.Resource;
import ar.edu.itba.fitness.buddy.api.repository.Status;
import ar.edu.itba.fitness.buddy.navigation.MainNavigationActivity;
import ar.edu.itba.fitness.buddy.splash.SplashScreenActivity;
import ar.edu.itba.fitness.buddy.splash.register.RegisterActivity;
import ar.edu.itba.fitness.buddy.api.model.Error;

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
        TextInputLayout emailInputLayout = findViewById(R.id.login_email);
        TextInputLayout passwordInputLayout = findViewById(R.id.login_password);
        Editable email = Objects.requireNonNull(emailInputLayout.getEditText()).getText();
        Editable password = Objects.requireNonNull(passwordInputLayout.getEditText()).getText();
        Credentials credentials = new Credentials(email.toString(), password.toString());
        App app = ((App)getApplication());
        app.getUserRepository().login(credentials).observe(this, t -> {
            if(t.getStatus() == Status.SUCCESS) {
                app.getPreferences().setAuthToken(Objects.requireNonNull(t.getData()).getToken());
                findViewById(R.id.login_progress_bar).setVisibility(View.GONE);
                Intent intent = new Intent(this, MainNavigationActivity.class);
                startActivity(intent);
            } else {
                defaultResourceHandler(t);
            }
        });

    }

    private void defaultResourceHandler(Resource<?> resource) {
        switch (resource.getStatus()) {
            case LOADING:
                findViewById(R.id.login_progress_bar).setVisibility(View.VISIBLE);
                break;
            case ERROR:
                findViewById(R.id.login_progress_bar).setVisibility(View.GONE);
                Error error = resource.getError();
                String message = getString(R.string.error, Objects.requireNonNull(error).getDescription(), error.getCode());
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void callSignUpScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
}