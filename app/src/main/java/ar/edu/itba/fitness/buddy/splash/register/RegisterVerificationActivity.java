package ar.edu.itba.fitness.buddy.splash.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chaos.view.PinView;

import java.util.Objects;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.api.model.Error;
import ar.edu.itba.fitness.buddy.api.model.Verification;
import ar.edu.itba.fitness.buddy.api.repository.Resource;
import ar.edu.itba.fitness.buddy.api.repository.Status;
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
        App app = (App)getApplication();
        Verification data = new Verification(emailText, code);
        app.getUserRepository().verifyEmail(data).observe(this, t -> {
            if(t.getStatus() == Status.SUCCESS) {
                Intent intent = new Intent(this, LoginActivity.class);
                Toast.makeText(this, "User created successfully!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            } else {
                defaultResourceHandler(t);
            }
        });
    }

    private void defaultResourceHandler(Resource<?> resource) {
        switch (resource.getStatus()) {
            case LOADING:
                break;
            case ERROR:
                Error error = resource.getError();
                String message = getString(R.string.error, Objects.requireNonNull(error).getDescription(), error.getCode());
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}