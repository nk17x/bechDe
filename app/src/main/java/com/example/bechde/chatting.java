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

public class chatting extends AppCompatActivity {
    private RecyclerView recyclerchatting;
    private chattingPostAdapter adapter;
    Toolbar toolbar;
    FirebaseAuth mAuth;
    Context context;
    ImageButton homebutton,chatbutton,adbutton,newadbutton,accountbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        homebutton=findViewById(R.id.homebutton);
        chatbutton=findViewById(R.id.chatbutton);
        accountbutton=findViewById(R.id.accountbutton);
        adbutton=findViewById(R.id.adbutton);
        newadbutton=findViewById(R.id.newadbutton);
        mAuth=FirebaseAuth.getInstance();
        toolbar=findViewById(R.id.toolbarchat);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(chatting.this,MainActivity.class);
                startActivity(i2);
                finish();
            }
        });
       /* recyclerchatting=findViewById(R.id.recyclerchatting);
        recyclerchatting.setLayoutManager(new LinearLayoutManager(this));
        String uid=mAuth.getUid();
        Toast.makeText(chatting.this, "this is it"+uid, Toast.LENGTH_SHORT).show();
        FirebaseRecyclerOptions<chattingpost> options =
                new FirebaseRecyclerOptions.Builder<chattingpost>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("chat/"+"chatid/"+uid).child("DKG3dODV8wW4TQPByTGG0n6DvOE3svamm9Faz2ZJ8JwJbzwGJLNE08U2"), chattingpost.class)
                        .build();

        adapter=new chattingPostAdapter(options,chatting.this);
        recyclerchatting.setAdapter(adapter);*/

        recyclerchatting=findViewById(R.id.recyclerchatting);
        String userId=mAuth.getUid();
        recyclerchatting.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<chattingpost> options =
                new FirebaseRecyclerOptions.Builder<chattingpost>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("chat/chatid/"+userId+"/"), chattingpost.class)
                        .build();

        adapter=new chattingPostAdapter(options,chatting.this);
        recyclerchatting.setAdapter(adapter);

        newadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(chatting.this,newad.class);
                startActivity(i2);
                finish();
            }
        });

        adbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(chatting.this,myad.class);
                startActivity(i2);
                finish();
            }
        });

        chatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenta=new Intent(chatting.this,chatting.class);
                startActivity(intenta);
                finish();
            }
        });

        accountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenta=new Intent(chatting.this,account.class);
                startActivity(intenta);
                finish();
            }
        });

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(chatting.this,MainActivity.class);
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
}