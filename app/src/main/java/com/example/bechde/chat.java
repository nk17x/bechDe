package com.example.bechde;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class chat extends AppCompatActivity {
    private RecyclerView recyclerchats;
    private ChatPostAdapter adapter;
    FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference databaseReference,databaseReference2,databaseReference3;
    Context context;
    TextView ctextView;
    EditText cEditText;
    ImageView backcimageView,cimageView;
    String fccuid;
    ImageButton csendimageview;
    MessageHelperClass messageHelperClass;
    String aduname,aduimgurl;
    String suid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mAuth=FirebaseAuth.getInstance();
        suid = mAuth.getUid();
        cimageView=findViewById(R.id.cimageView);
        csendimageview=findViewById(R.id.csendimageview);
        ctextView=findViewById(R.id.ctextView);
        cEditText=findViewById(R.id.cEditText);
        backcimageView=findViewById(R.id.backcimageView);
        Bundle extras = getIntent().getExtras();
        fccuid = extras.getString("fccuid");
        aduname=extras.getString("aduname");
        aduimgurl=extras.getString("aduimgurl");

        databaseReference3 = FirebaseDatabase.getInstance().getReference();
        Query userdetails6 = databaseReference3.child("chat/chatid/"+suid+"/"+fccuid+"/");
        userdetails6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String fcunameFromDb = snapshot.child("fcname1").getValue(String.class);
                    String fcuimgurlFromDb = snapshot.child("fcimgurl1").getValue(String.class);
                    ctextView.setText(fcunameFromDb.toUpperCase());
                    Glide.with(chat.this).load(fcuimgurlFromDb).fitCenter().circleCrop().into(cimageView);
                }}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        backcimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(chat.this,chatting.class);
                startActivity(i2);
                finish();
            }
        });
        csendimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Messagec =cEditText.getText().toString();
                databaseReference2 = FirebaseDatabase.getInstance().getReference();
                databaseReference = FirebaseDatabase.getInstance().getReference();
                Query userdetails5 = databaseReference2.child("users/" + suid);
                userdetails5.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            rootNode = FirebaseDatabase.getInstance();
                            String fullnameFromDb = snapshot.child("fullname").getValue(String.class);
                            String userimgurlFromDb = snapshot.child("imgurl").getValue(String.class);
                            databaseReference = rootNode.getReference("chat/");
                            messageHelperClass=new MessageHelperClass(fullnameFromDb,Messagec);
                            databaseReference.child("chats/").child(fccuid).push().setValue(messageHelperClass);
                        }}
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                cEditText.setText("");
            }
        });


        recyclerchats=findViewById(R.id.recyclerchats);
        String userId=mAuth.getUid();
        recyclerchats.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<chatpost> options =
                new FirebaseRecyclerOptions.Builder<chatpost>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("chat/chats/"+fccuid+"/"), chatpost.class)
                        .build();

        adapter=new ChatPostAdapter(options,chat.this);
        recyclerchats.setAdapter(adapter);
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