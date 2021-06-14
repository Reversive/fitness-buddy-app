package ar.edu.itba.fitness.buddy.splash.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

import ar.edu.itba.fitness.buddy.App;
import ar.edu.itba.fitness.buddy.R;
import ar.edu.itba.fitness.buddy.api.model.ApiResponse;
import ar.edu.itba.fitness.buddy.api.model.Error;
import ar.edu.itba.fitness.buddy.api.model.User;
import ar.edu.itba.fitness.buddy.api.repository.Resource;
import ar.edu.itba.fitness.buddy.api.repository.Status;


public class RegisterStepActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton selectedGender;
    DatePicker datePicker;
    String fullNameText, emailText, passwordText, heightText, weightText, dateText, genderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_register_step);
        Objects.requireNonNull(getSupportActionBar()).hide();

        radioGroup = findViewById(R.id.signup_radio_group);
        datePicker = findViewById(R.id.signup_age_picker);

        Intent receiver = getIntent();
        fullNameText = receiver.getStringExtra("fullName");
        emailText = receiver.getStringExtra("email");
        passwordText = receiver.getStringExtra("password");
        heightText = receiver.getStringExtra("height");
        weightText = receiver.getStringExtra("weight");
        dateText = receiver.getStringExtra("date");
        genderText = receiver.getStringExtra("gender");
        if(dateText != null) {
            String[] splitDate = dateText.split("/");
            datePicker.updateDate(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[0]));
        }
    }

    public void callPreviousStep(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        intent.putExtra("fullName", fullNameText);
        intent.putExtra("email", emailText);
        intent.putExtra("password", passwordText);
        intent.putExtra("height", heightText);
        intent.putExtra("weight", weightText);
        startActivity(intent);
    }

    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 12) {
            Toast.makeText(this, "You need to be older than 12 years old", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    public static long getLongFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = new GregorianCalendar(year, month, day);
        return calendar.getTimeInMillis();
    }

    public void callVerificationScreen(View view) {
        if(!validateGender() || !validateAge() ) return;
        selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
        String genderText = selectedGender.getText().toString();
        switch (genderText) {
            case "Masculino":
                genderText = "Male";
                break;
            case "Femenino":
                genderText = "Female";
                break;
            case "Otro":
                genderText = "Other";
                break;
        }
        long date = getLongFromDatePicker(datePicker);
        User user = new User(emailText, passwordText, fullNameText, null, genderText.toLowerCase(), date, emailText, null, null, null);
        App app = (App)getApplication();
        String finalGenderText = genderText;
        app.getUserRepository().register(user).observe(this, t -> {
            if(t.getStatus() == Status.SUCCESS) {
                findViewById(R.id.register_progress_bar).setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(), RegisterVerificationActivity.class);
                intent.putExtra("fullName", fullNameText);
                intent.putExtra("email", emailText);
                intent.putExtra("password", passwordText);
                intent.putExtra("height", heightText);
                intent.putExtra("weight", weightText);
                intent.putExtra("gender", finalGenderText);
                intent.putExtra("date", dateText);
                startActivity(intent);
            } else {
                defaultResourceHandler(t);
            }
        });


    }

    private void defaultResourceHandler(Resource<?> resource) {
        switch (resource.getStatus()) {
            case LOADING:
                findViewById(R.id.register_progress_bar).setVisibility(View.VISIBLE);
                break;
            case ERROR:
                findViewById(R.id.register_progress_bar).setVisibility(View.GONE);
                Error error = resource.getError();
                String message = getString(R.string.error, Objects.requireNonNull(error).getDescription(), error.getCode());
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}