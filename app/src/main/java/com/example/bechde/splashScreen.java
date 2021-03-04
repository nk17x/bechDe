package com.example.bechde;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class splashScreen extends AppCompatActivity {
    boolean loginstatus=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
            Thread thread =new Thread(){
                public void run(){
                    try{
                        sleep(3000);
                    }catch(Exception e){
                        e.printStackTrace();
                    }finally {
                        Intent i = new Intent(splashScreen.this,login.class);
                        startActivity(i);
                        finish();
                    }
                }
            };
            thread.start();


        }
    }
