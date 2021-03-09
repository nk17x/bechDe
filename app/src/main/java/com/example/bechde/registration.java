package com.example.bechde;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class registration extends AppCompatActivity {
    Button button,login;
    TextView textView2;
    TextInputEditText textphoneno,textfullname,textemail,textpassword;
    FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference databaseReference;
    boolean registerstatus=true;
    String email,password,phone,fullname;
    String username;
    UserHelperClass helperClass;
    StorageReference storageReference,fileReference;
    private static final int PICK_IMAGE_REQUEST = 1;
    Uri mImageURi;
    ImageView imageprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth=FirebaseAuth.getInstance();
        initilaize();
        rootNode=FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(registration.this,login.class);
                startActivity(i);
                finish();
            }
        });
        imageprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phone=textphoneno.getText().toString();
                fullname =textfullname.getText().toString();
                email=textemail.getText().toString();
                password =textpassword.getText().toString();
                if(TextUtils.isEmpty(fullname)){
                    textfullname.setError("Enter Valid FullName");
                    return;
                }
                if(phone.length()<10){
                    textphoneno.setError("Enter Valid Phone No");
                    return;
                }
                if(email.indexOf("@")==-1){
                    textemail.setError("Enter Valid Email");
                    return;
                }
                if(password.length()<8){
                    textpassword.setError("Enter Valid Password");
                    return;
                }
                String str = email;
                username="";
                if(str.indexOf(".")!=-1){
                    String [] twoStringArray2= str.split("@",2);
                    String username2= twoStringArray2[0];
                    String [] twoStringArray= username2.split("\\.",2);
                    username= twoStringArray[0];
                }else{
                    String [] twoStringArray2= str.split("@",2);
                    username= twoStringArray2[0];}
                databaseReference=rootNode.getReference("users");
                storageReference = FirebaseStorage.getInstance().getReference("users");

                if(registerstatus){
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                if (mImageURi != null) {
                                    mAuth.signInWithEmailAndPassword(email,password);
                                    fileReference = storageReference.child(username+ "." + getFileExtension(mImageURi).toString());
                                    fileReference.putFile(mImageURi).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    String imgurl = uri.toString();
                                                    helperClass= new UserHelperClass(fullname,phone,email,username,imgurl);
                                                    databaseReference.child(username).setValue(helperClass);
                                                }
                                            });
                                            Toast.makeText(registration.this, "new ad added Succesfully", Toast.LENGTH_SHORT).show();
                                        }

                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(registration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(registration.this, "image not selected", Toast.LENGTH_SHORT).show();
                                }
                                databaseReference.child(username).setValue(helperClass);
                                registerstatus=false;
                                Intent i =new Intent(registration.this,login.class);
                                startActivity(i);
                                Toast.makeText(registration.this, "registeration succesfull", Toast.LENGTH_SHORT).show();
                                mAuth.signOut();
                                finish();
                            }
                            else{
                                Toast.makeText(registration.this, "not succesfull", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }
                    });
                }
            }



        });
    }

    public void initilaize(){
        button=findViewById(R.id.button);
        login=findViewById(R.id.login);
        textphoneno=findViewById(R.id.phoneno);
        textemail=findViewById(R.id.email);
        textpassword=findViewById(R.id.password);
        textfullname=findViewById(R.id.fullname);
        imageprofile=findViewById(R.id.imageregister);
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageURi = data.getData();
            Picasso.get().load(mImageURi).into(imageprofile);
        }
    }
    }
