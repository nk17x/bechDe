package com.example.bechde;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class showad extends AppCompatActivity {
String selectedadkey;
    FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference databaseReference,databaseReference2;
    Toolbar toolbar;
    TextView adprice,addesc,adsubject,adloc,adusername,aduserprofile,adcategory,adhowold;
    ImageView adimg,userimg;
    Button button4;
    UserHelperClass userHelperClass;
    adhelperclass adhelperclass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showad);

        adprice=findViewById(R.id.adprice);
        addesc=findViewById(R.id.addesc);
        adsubject=findViewById(R.id.adsubject);
        adloc=findViewById(R.id.adloc);
        adusername=findViewById(R.id.adusername);
        aduserprofile=findViewById(R.id.aduserprofile);
        adcategory=findViewById(R.id.adcategory);
        adhowold=findViewById(R.id.adhowold);
        userimg=findViewById(R.id.userimg);
        adimg=findViewById(R.id.adimg);
        button4=findViewById(R.id.button4);


        Bundle extras = getIntent().getExtras();
        selectedadkey = extras.getString("selectedadkey");
        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent5));
        toolbar=findViewById(R.id.toolbar3);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(showad.this,MainActivity.class);
                startActivity(i2);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query addetails = databaseReference.child("ad/"+selectedadkey);
       addetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String priceFromDb = dataSnapshot.child("price").getValue(String.class);
                    String categoryFromDb = dataSnapshot.child("category").getValue(String.class);
                    String descFromDb = dataSnapshot.child("desc").getValue(String.class);
                    String imgurlFromDb = dataSnapshot.child("imgurl").getValue(String.class);
                    String locationFromDb = dataSnapshot.child("location").getValue(String.class);
                    String adtitleFromDb = dataSnapshot.child("adtitle").getValue(String.class);
                    String howoldFromDb = dataSnapshot.child("howold").getValue(String.class);
                    String useridFromDb = dataSnapshot.child("userId").getValue(String.class);
                    String username=useridFromDb.toString();
                    databaseReference2 = FirebaseDatabase.getInstance().getReference();
                    Query userdetails5 = databaseReference2.child("users/"+username);
                    userdetails5.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                String fullnameFromDb = snapshot.child("fullname").getValue(String.class);
                                String userimgurlFromDb = snapshot.child("imgurl").getValue(String.class);
                    adusername.setText(fullnameFromDb);
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        Glide.with(userimg).load(userimgurlFromDb).fitCenter().into(userimg);
                                    }
                                }, 300);
                            } }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    adsubject.setText(adtitleFromDb);
                    adcategory.setText(categoryFromDb);
                    adloc.setText(locationFromDb);
                    addesc.setText(descFromDb);
                    adhowold.setText(howoldFromDb);

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Glide.with(adimg).load(imgurlFromDb).fitCenter().into(adimg);
                        }
                    }, 300);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        String userid=adusername.getText().toString();

    }
    }
