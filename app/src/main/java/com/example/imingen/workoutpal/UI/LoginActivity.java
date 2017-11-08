package com.example.imingen.workoutpal.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.imingen.workoutpal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private TextInputLayout loginEmail, loginPassword;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginEmail = (TextInputLayout) findViewById(R.id.emailField);
        loginPassword = (TextInputLayout) findViewById(R.id.passwordField);

        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void loginOnClick(View view){
        String email = loginEmail.getEditText().getText().toString();
        String password = loginPassword.getEditText().getText().toString();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            login(email, password);
        }
    }

    private void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                            //Pressing the back button will exit the app instead of going back to the login page
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish();
                        }
                        else{
                            Log.w("XD", "SigninUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void loginRegisterOnClick(View view){
        Intent register_intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(register_intent);
    }

}