package com.example.bechde;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class account extends AppCompatActivity {
    EditText namestext, emailtext, mobiletext;
    ImageView imageprofile;
    Button updatebt, backbt;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String protemail,protphone,protfullname;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        namestext = findViewById(R.id.namestext);
        emailtext = findViewById(R.id.emailtext);
        mobiletext = findViewById(R.id.mobiletext);
        imageprofile = findViewById(R.id.imageprofile);
        updatebt = findViewById(R.id.updatebt);
        backbt = findViewById(R.id.backbt);

        mAuth=FirebaseAuth.getInstance();

        String email=mAuth.getCurrentUser().getEmail();
        String str = email;
        if(str.indexOf(".")!=-1){
            String [] twoStringArray2= str.split("@",2);
            String username2= twoStringArray2[0];
            String [] twoStringArray= username2.split("\\.",2);
            username= twoStringArray[0];
        }else{
            String [] twoStringArray2= str.split("@",2);
            username= twoStringArray2[0];}
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference("users");
        Query userdetails2 = databaseReference.orderByChild("username").equalTo(username);
        userdetails2.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String fullnameFromDb=dataSnapshot.child(username).child("fullname").getValue(String.class);
                    String phoneFromDb=dataSnapshot.child(username).child("phone").getValue(String.class);
                    String emailFromDb=dataSnapshot.child(username).child("email").getValue(String.class);
                    emailtext.setText(emailFromDb);
                    namestext.setText(fullnameFromDb);
                    mobiletext.setText(phoneFromDb);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }});
        backbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(account.this,login.class);
                startActivity(i);
                finish();
            }
        });

        updatebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                protphone=mobiletext.getText().toString();
                protfullname =namestext.getText().toString();
                protemail=emailtext.getText().toString();
                String str = protemail;
                String username=null;
                if(str.indexOf(".")!=-1){
                    String [] twoStringArray2= str.split("@",2);
                    String username2= twoStringArray2[0];
                    String [] twoStringArray= username2.split("\\.",2);
                    username= twoStringArray[0];
                }else{
                    String [] twoStringArray2= str.split("@",2);
                    username= twoStringArray2[0];}
                UserHelperClass helperClass= new UserHelperClass(protfullname,protphone,protemail,username,"sg");
                databaseReference.child(username).setValue(helperClass);
                if(TextUtils.isEmpty(protemail)){
                    emailtext.setError("enter email");
                    return;
                }
                if(TextUtils.isEmpty(protphone)){
                    mobiletext.setError("enter phone no");
                    return;
                }
                if(TextUtils.isEmpty(protfullname)){
                    namestext.setError("enter fullname");
                    return;}
            }
        });

    }
}