package com.example.imingen.workoutpal.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.imingen.workoutpal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout email, password;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailField);
        password = (TextInputLayout) findViewById(R.id.passwordField);

    }

    public void registerOnclick(View view){
        register();
    }

    private void register() {
        final String email_ = email.getEditText().getText().toString();
        String password_ = password.getEditText().getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email_, password_)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

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
                                            Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                            //Pressing the back button will exit the app instead of going back to the login page
                                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(mainIntent);
                                            finish();
                                        }
                                    }
                            );
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w("XD", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


}
