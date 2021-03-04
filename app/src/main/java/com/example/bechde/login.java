package com.example.bechde;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    Button button2,signup;
    TextView textView3,forgotpassword;
    FirebaseAuth mAuth;
    TextInputEditText usernameedit,passwordedit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        initilaize();
        forgotpassword=findViewById(R.id.forgotpassword);
        usernameedit=findViewById(R.id.username);
        passwordedit=findViewById(R.id.password);
        if(mAuth.getCurrentUser() !=null){
            /*if(mAuth.getCurrentUser().getEmail().equals("admin@gmail.com")){
                Intent i2 =new Intent(login.this,adminactivity.class);
                startActivity(i2);
                finish();
            }else{*/
                Intent i =new Intent(login.this,MainActivity.class);
                startActivity(i);
                finish();}
      /*  }*/else{

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i =new Intent(login.this,registration.class);
                    startActivity(i);
                    finish();
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email, password;
                    email = usernameedit.getText().toString();
                    password = passwordedit.getText().toString();
                    if (TextUtils.isEmpty(email)) {
                        usernameedit.setError("Enter Valid Email");
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        passwordedit.setError("Enter Valid Password");
                        return;
                    }

                    String str = email;
                    final String adminemail=email;
                    String [] twoStringArray= str.split("@",2);
                    final String username= twoStringArray[0];
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.i("login","login is going");
                                /*if(adminemail.equals("admin@gmail.com")){
                                    Intent i2 =new Intent(login.this,adminactivity.class);
                                    startActivity(i2);
                                    finish();
                                }else{*/
                                    Intent i =new Intent(login.this,MainActivity.class);
                                    i.putExtra("usernameintent",username);
                                    startActivity(i);
                                    finish();}
                           /* }*/else{

                                Toast.makeText(login.this, "enter valid details...", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }



            });
            forgotpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email=usernameedit.getText().toString();
                    if(TextUtils.isEmpty(email)){
                        usernameedit.setError("enter email");
                        return;
                    }
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(login.this, "Password Reset Email Sent Successful", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(login.this, "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                            }
                        }               });
                }        });
        }}
    public void initilaize() {
        button2 = findViewById(R.id.button2);
        signup = findViewById(R.id.signup);
    }
    }
