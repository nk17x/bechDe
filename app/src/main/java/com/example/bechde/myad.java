package com.example.bechde;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class myad extends AppCompatActivity {
   
    private RecyclerView recyclermyad;
    private MyAdPostAdapter adapter;
    Toolbar toolbar;
    FirebaseAuth mAuth;
    Context context;
    ImageButton homebutton,chatbutton,adbutton,newadbutton,accountbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myad);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorAccent2));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent3));
        homebutton=findViewById(R.id.homebutton);
        chatbutton=findViewById(R.id.chatbutton);
        accountbutton=findViewById(R.id.accountbutton);
        adbutton=findViewById(R.id.adbutton);
        newadbutton=findViewById(R.id.newadbutton);
        mAuth=FirebaseAuth.getInstance();
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(myad.this,MainActivity.class);
                startActivity(i2);
                finish();
            }
        });

        recyclermyad=findViewById(R.id.recyclermyad);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,false);
        recyclermyad.setLayoutManager(mLayoutManager);
        String userId=mAuth.getUid();
        /*recyclermyad.setLayoutManager(new LinearLayoutManager(this));*/

        FirebaseRecyclerOptions<mainpost> options =
                new FirebaseRecyclerOptions.Builder<mainpost>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("ad").orderByChild("userId").equalTo(userId), mainpost.class)
                        .build();

        adapter=new MyAdPostAdapter(options,this);
        recyclermyad.setAdapter(adapter);

        newadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(myad.this,newad.class);
                startActivity(i2);
                finish();
            }
        });

        adbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(myad.this,myad.class);
                startActivity(i2);
                finish();
            }
        });

        chatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(myad.this, "new ad pressed", Toast.LENGTH_SHORT).show();
            }
        });

        accountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(myad.this, "new ad pressed", Toast.LENGTH_SHORT).show();
            }
        });

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(myad.this,MainActivity.class);
                startActivity(i2);
                finish();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i2 =new Intent(myad.this,MainActivity.class);
        startActivity(i2);
        finish();
    }
}