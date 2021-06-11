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
import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.api.model.ApiResponse;
import ar.edu.itba.fitness.buddy.api.model.Credentials;
import ar.edu.itba.fitness.buddy.api.model.Token;
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
        TextInputLayout emailInputLayout = findViewById(R.id.login_email);
        TextInputLayout passwordInputLayout = findViewById(R.id.login_password);
        Editable email = Objects.requireNonNull(emailInputLayout.getEditText()).getText();
        Editable password = Objects.requireNonNull(passwordInputLayout.getEditText()).getText();
        Credentials credentials = new Credentials(email.toString(), password.toString());
        LiveData<ApiResponse<Token>> token = App.getUserService().login(credentials);
        token.observe(this, t -> {
            Log.d("DEBUG::", "BP TEST");
        });
    }

    public void callSignUpScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
}