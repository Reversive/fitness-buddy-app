package ar.edu.itba.fitness.buddy.splash.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chaos.view.PinView;

import java.util.Objects;

import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.splash.login.LoginActivity;

public class RegisterVerificationActivity extends AppCompatActivity {

    private String fullNameText, emailText, passwordText, heightText, weightText, dateText, genderText;
    private PinView pinView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_register_verification);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent receiver = getIntent();
        fullNameText = receiver.getStringExtra("fullName");
        emailText = receiver.getStringExtra("email");
        passwordText = receiver.getStringExtra("password");
        heightText = receiver.getStringExtra("height");
        weightText = receiver.getStringExtra("weight");
        genderText = receiver.getStringExtra("gender");
        dateText = receiver.getStringExtra("date");
        pinView = findViewById(R.id.verification_pin_view);
    }

    public void callPreviousStep(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterStepActivity.class);
        intent.putExtra("fullName", fullNameText);
        intent.putExtra("email", emailText);
        intent.putExtra("password", passwordText);
        intent.putExtra("height", heightText);
        intent.putExtra("weight", weightText);
        intent.putExtra("gender", genderText);
        intent.putExtra("date", dateText);
        startActivity(intent);
    }

    public void callVerifyCode(View view) {
        String code = Objects.requireNonNull(pinView.getText()).toString();
        if(code.isEmpty()) {
            Toast.makeText(this, "Please input all code digits", Toast.LENGTH_SHORT).show();
            return;
        }
        // Verify code....

        // Go to login
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        Toast.makeText(this, "User created successfully!", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}