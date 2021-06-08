package ar.edu.itba.fitness.buddy.splash.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.splash.SplashScreenActivity;

public class RegisterActivity extends AppCompatActivity {

    ImageView backBtn;
    Button nextBtn;
    TextView titleText;

    TextInputLayout fullName, email, password, height, weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_register);
        Objects.requireNonNull(getSupportActionBar()).hide();

        backBtn = findViewById(R.id.signup_back_button);
        nextBtn = findViewById(R.id.signup_next_btn);
        titleText = findViewById(R.id.signup_title_text);

        fullName = findViewById(R.id.signup_fullname);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        height = findViewById(R.id.signup_height);
        weight = findViewById(R.id.signup_weight);

        Intent receiver = getIntent();
        String cachedFullName = receiver.getStringExtra("fullName");
        String cachedEmail = receiver.getStringExtra("email");
        String cachedPassword = receiver.getStringExtra("password");
        String cachedHeight = receiver.getStringExtra("height");
        String cachedWeight = receiver.getStringExtra("weight");
        if(cachedFullName != null) Objects.requireNonNull(fullName.getEditText()).setText(cachedFullName);
        if(cachedEmail != null) Objects.requireNonNull(email.getEditText()).setText(cachedEmail);
        if(cachedPassword != null) Objects.requireNonNull(password.getEditText()).setText(cachedPassword);
        if(cachedHeight != null) Objects.requireNonNull(height.getEditText()).setText(cachedHeight);
        if(cachedWeight != null) Objects.requireNonNull(weight.getEditText()).setText(cachedWeight);
    }

    public void callSplashScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
        startActivity(intent);
    }

    private boolean validateFullName() {
        String value = Objects.requireNonNull(fullName.getEditText()).getText().toString().trim();
        if (value.isEmpty()) {
            fullName.setError("Field can not be empty");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String value = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
        String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (value.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!value.matches(emailRegex)) {
            email.setError("Invalid email");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String value = Objects.requireNonNull(password.getEditText()).getText().toString().trim();
        String passwordRegex = "^" +
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                "$";
        if (value.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else if (!value.matches(passwordRegex)) {
            password.setError("Invalid password");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }


    public void callNextSignupScreen(View view) {
        if(!validateFullName() || !validateEmail() || !validatePassword() ) return;
        Intent intent = new Intent(getApplicationContext(), RegisterStepActivity.class);
        Pair<View, String>[] pairs = new Pair[3];
        pairs[0] = new Pair<>(backBtn, "transition_back_btn");
        pairs[1] = new Pair<>(nextBtn, "transition_next_btn");
        pairs[2] = new Pair<>(titleText, "transition_title_text");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);
        String fullNameText = Objects.requireNonNull(fullName.getEditText()).getText().toString();
        String emailText = Objects.requireNonNull(email.getEditText()).getText().toString();
        String passwordText = Objects.requireNonNull(password.getEditText()).getText().toString();
        String heightText = Objects.requireNonNull(height.getEditText()).getText().toString();
        String weightText = Objects.requireNonNull(weight.getEditText()).getText().toString();
        intent.putExtra("fullName", fullNameText);
        intent.putExtra("email", emailText);
        intent.putExtra("password", passwordText);
        intent.putExtra("height", heightText);
        intent.putExtra("weight", weightText);
        startActivity(intent, options.toBundle());
    }
}