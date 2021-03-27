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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
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
    DatabaseReference databaseReference, databaseReference2, databaseReference3;
    Toolbar toolbar;
    TextView adprice, addesc, adsubject, adloc, adusername, aduserprofile, adhowold;
    ImageView adimg, userimg;
    Button button4;
    NewChatHelperClass newChatHelperClass1,newChatHelperClass2;
    adhelperclass adhelperclass;
    String chatsid,fcuid1, fcuid2, fcimgurl1, fcimgurl2, fcname1, fcname2;
    MessageHelperClass messageHelperClass;
    String imgurl1,adtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showad);

        adprice = findViewById(R.id.adprice);
        addesc = findViewById(R.id.addesc);
        adsubject = findViewById(R.id.adsubject);
        adloc = findViewById(R.id.adloc);
        adusername = findViewById(R.id.adusername);
        aduserprofile = findViewById(R.id.aduserprofile);
        adhowold = findViewById(R.id.adhowold);
        userimg = findViewById(R.id.userimg);
        adimg = findViewById(R.id.adimg);
        button4 = findViewById(R.id.button4);


        Bundle extras = getIntent().getExtras();
        selectedadkey = extras.getString("selectedadkey");
        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent5));
        toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(showad.this, MainActivity.class);
                startActivity(i2);
                finish();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = rootNode.getReference("chat/");
                newChatHelperClass2 = new NewChatHelperClass(chatsid,fcuid1, fcuid2, fcimgurl1, fcimgurl2, fcname1, fcname2,imgurl1,adtitle);
                newChatHelperClass1 = new NewChatHelperClass(chatsid, fcuid2,fcuid1, fcimgurl2, fcimgurl1, fcname2, fcname1,imgurl1,adtitle);
                databaseReference.child("chatid/").child(fcuid1).child(fcuid1+fcuid2).setValue(newChatHelperClass1);
                databaseReference.child("chatid/").child(fcuid2).child(fcuid1+fcuid2).setValue(newChatHelperClass2);
                Intent intent=new Intent(showad.this,chat.class);
                intent.putExtra("fccuid",fcuid1+fcuid2);
                intent.putExtra("aduname",fcname1);
                intent.putExtra("aduimgurl",fcimgurl1);
                startActivity(intent);
            }
        });
        aduserprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(showad.this,profileView.class);
                intent.putExtra("fcuid1",fcuid1);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query addetails = databaseReference.child("ad/" + selectedadkey);
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
                    String username = useridFromDb.toString();
                    imgurl1=imgurlFromDb;
                    adtitle=adtitleFromDb;
                    fcuid1 = username;
                    databaseReference2 = FirebaseDatabase.getInstance().getReference();
                    Query userdetails5 = databaseReference2.child("users/" + username);
                    userdetails5.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String fullnameFromDb = snapshot.child("fullname").getValue(String.class);
                                String userimgurlFromDb = snapshot.child("imgurl").getValue(String.class);
                                fcname1 = fullnameFromDb;
                                fcimgurl1 = userimgurlFromDb;
                                adusername.setText(fullnameFromDb.toUpperCase());
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        Glide.with(userimg).load(userimgurlFromDb).fitCenter().circleCrop().into(userimg);
                                    }
                                }, 150);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    fcuid2 = mAuth.getUid();
                    databaseReference3 = FirebaseDatabase.getInstance().getReference();
                    Query userdetails6 = databaseReference3.child("users/" + fcuid2);
                    userdetails6.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String fullnameFromDb2 = snapshot.child("fullname").getValue(String.class);
                                String userimgurlFromDb2 = snapshot.child("imgurl").getValue(String.class);
                                fcname2 = fullnameFromDb2;
                                fcimgurl2 = userimgurlFromDb2;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    adprice.setText("₹ " + priceFromDb.format("%,d", Integer.parseInt(priceFromDb)));
                    adsubject.setText(adtitleFromDb.toUpperCase());
                    /* adcategory.setText("Catergory :"+categoryFromDb.toUpperCase());*/
                    adloc.setText(locationFromDb.toUpperCase());
                    addesc.setText(descFromDb.toUpperCase());
                    adhowold.setText(howoldFromDb.toUpperCase());

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Glide.with(adimg).load(imgurlFromDb)
                                    .transform(new FitCenter(),new RoundedCorners(22))
                                    .into(adimg);
                        }
                    }, 150);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        String userid = adusername.getText().toString();

    }
}
