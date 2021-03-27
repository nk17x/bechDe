package com.example.bechde;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class profileView extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String fcuid1;
    TextView nametext,emailtext,phonetext;
    Uri mImageURi;
    Toolbar toolbar;
    ImageView imageprofile;
    ImageButton homebutton,chatbutton,adbutton,newadbutton,accountbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        Bundle extras = getIntent().getExtras();
        nametext = findViewById(R.id. textView11);

        emailtext = findViewById(R.id.textView13);
        phonetext = findViewById(R.id.textView12);
        homebutton=findViewById(R.id.homebutton);
        chatbutton=findViewById(R.id.chatbutton);
        accountbutton=findViewById(R.id.accountbutton);
        imageprofile=findViewById(R.id.proimageView);
        adbutton=findViewById(R.id.adbutton);
        newadbutton=findViewById(R.id.newadbutton);
        fcuid1 = extras.getString("fcuid1");
        toolbar=findViewById(R.id.toolbar5);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(profileView.this,MainActivity.class);
                startActivity(i2);
                finish();
            }
        });
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference("users/");
        Query userdetails2 = databaseReference.child(fcuid1);
        userdetails2.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String fullnameFromDb=dataSnapshot.child("fullname").getValue(String.class);
                    String phoneFromDb=dataSnapshot.child("phone").getValue(String.class);
                    String emailFromDb=dataSnapshot.child("email").getValue(String.class);
                    String imgurlfromdb=dataSnapshot.child("imgurl").getValue(String.class);
                    emailtext.setText(emailFromDb);
                    nametext.setText(fullnameFromDb);
                    phonetext.setText(phoneFromDb);
                    Glide.with(imageprofile).load(imgurlfromdb).fitCenter().circleCrop().into(imageprofile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }});

        newadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(profileView.this,newad.class);
                startActivity(i2);
                finish();
            }
        });

        adbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(profileView.this,myad.class);
                startActivity(i2);
                finish();
            }
        });

        chatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(profileView.this,chatting.class);
                startActivity(i2);
                finish();
            }
        });

        accountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenta=new Intent(profileView.this,account.class);
                startActivity(intenta);
                finish();
            }
        });

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(profileView.this,MainActivity.class);
                startActivity(i2);
                finish();
            }
        });

    }
}