package com.example.imingen.workoutpal.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imingen.workoutpal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout email, password;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private ProgressBar progressBar;

    private Toast toast;

    private static final String SUCCESSFULL_REG = "Registration successful";
    private static final String PASSWORD_LENGTH_ERROR = "Password must be atleast 6 characters long";
    private static final String BLANK_FIELDS_ERROR = "Fields can't be blank";
    private static final String BAD_EMAIL_FORMAT_ERROR = "Not a valid email";

    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailField);
        password = (TextInputLayout) findViewById(R.id.passwordField);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        view = findViewById(R.id.register_activity);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager inputMethodManager = (InputMethodManager) RegisterActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(RegisterActivity.this.getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });


    }

    public void registerOnclick(View view){
        register();
    }

    private void register() {
        progressBar.setVisibility(View.VISIBLE);

        final String email_ = email.getEditText().getText().toString();
        final String password_ = password.getEditText().getText().toString();

        try {
            if(password_.trim().length() == 0 || email_.trim().length() == 0){
                throw new IllegalStateException(BLANK_FIELDS_ERROR);
            }
            else{
                firebaseAuth.createUserWithEmailAndPassword(email_, password_)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                try {
                                    if (task.isSuccessful()) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        String userID = firebaseAuth.getCurrentUser().getUid();
                                        databaseReference = FirebaseDatabase.getInstance()
                                                .getReference().child("Users").child(userID);

                                        HashMap<String, String> userMap = new HashMap<String, String>();
                                        userMap.put("mail", email_);

                                        databaseReference.setValue(userMap).addOnCompleteListener(
                                                new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Log.d("XD", "createUserWithEmail:success");
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                                        //Pressing the back button will exit the app instead of going back to the login page
                                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(mainIntent);
                                                        finish();
                                                        makeToast(SUCCESSFULL_REG);
                                                    }
                                                }
                                        );
                                    }
                                    else{
                                        //Set the progressbar invisible if registration is unsuccessful
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
//********************************************************************************************************************************************************
                                    if(password_.trim().length() < 6 ){
                                        throw new FirebaseAuthInvalidCredentialsException("XD", PASSWORD_LENGTH_ERROR);
                                    }

                                }
                                catch (FirebaseAuthInvalidCredentialsException e){
                                    if(e.getMessage() == PASSWORD_LENGTH_ERROR){
                                        Log.i("JAU", e.getMessage());
                                        makeToast(e.getMessage());
                                    }

                                }
                            }
                        });
                }
            }
        catch (IllegalStateException e){
            progressBar.setVisibility(View.INVISIBLE);
            makeToast(e.getMessage());
        }
    }


    private void makeToast(String message){
        Toast toast = Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        toast.show();
    }


}
