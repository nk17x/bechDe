package com.example.bechde;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class newad extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner howoldspinner, categoryspinner;
    EditText adtitletext,desctext,pricetext,locationtext;
    TextView textView44;
    Button button10, button12;
    ProgressBar progressBar;
    ImageView imageView;
    String adtitle,price,desc,location,howold,category,imgurl,userId;
    String username;
    FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    adhelperclass helperclass;
    private static final int PICK_IMAGE_REQUEST = 1;
    Uri mImageURi;
    ImageButton homebutton,chatbutton,adbutton,newadbutton,accountbutton;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newad);
        mAuth = FirebaseAuth.getInstance();
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent3));
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(newad.this,MainActivity.class);
                startActivity(i2);
                finish();
            }
        });

        homebutton=findViewById(R.id.homebutton);
        chatbutton=findViewById(R.id.chatbutton);
        accountbutton=findViewById(R.id.accountbutton);
        adbutton=findViewById(R.id.adbutton);
        newadbutton=findViewById(R.id.newadbutton);

        textView44 = findViewById(R.id.textView44);
        adtitletext=findViewById(R.id.adtitletext);
        pricetext=findViewById(R.id.pricetext);
        desctext=findViewById(R.id.desctext);
        categoryspinner=findViewById(R.id.categoryspinner);
        howoldspinner=findViewById(R.id.howoldspinner);
        locationtext=findViewById(R.id.locationtext);
        button10 = findViewById(R.id.button10);
        button12 = findViewById(R.id.button12);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageView4);
        List<CharSequence> choices = new ArrayList<>();
        choices.add("Select Category!");
        choices.add("Bike");
        choices.add("Cars");
        choices.add("Bicycle");
        choices.add("Mobile Phones");
        choices.add("Cloths");
        choices.add("Shoes");
        choices.add("Electronics");
        choices.add("Laptop");
        choices.add("Furniture");
        choices.add("Books");
        choices.add("Other");
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, choices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryspinner.setAdapter(adapter);
        categoryspinner.setOnItemSelectedListener(this);

        List<CharSequence> choices2 = new ArrayList<>();
        choices2.add("how old is it?");
        choices2.add("Less than 1 year");
        choices2.add("1 year");
        choices2.add("2 year");
        choices2.add("3 year");
        choices2.add("4 year");
        choices2.add("5 year");
        choices2.add("6 year");
        choices2.add("7 year");
        choices2.add("8 year");
        choices2.add("9 year");
        choices2.add("10 year");
        choices2.add("more than 10 years");
        ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, choices2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        howoldspinner.setAdapter(adapter2);
        howoldspinner.setOnItemSelectedListener(this);

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String str= mAuth.getCurrentUser().getEmail();
                username="";
                if(str.indexOf(".")!=-1){
                    String [] twoStringArray2= str.split("@",2);
                    String username2= twoStringArray2[0];
                    String [] twoStringArray= username2.split("\\.",2);
                    username= twoStringArray[0];
                }else{
                    String [] twoStringArray2= str.split("@",2);
                    username= twoStringArray2[0];}
                rootNode = FirebaseDatabase.getInstance();
                adtitle = adtitletext.getText().toString();
                price=pricetext.getText().toString();
                location=locationtext.getText().toString();
                desc=desctext.getText().toString();
                price=pricetext.getText().toString();
                storageReference = FirebaseStorage.getInstance().getReference("ad/" + category);
                databaseReference = FirebaseDatabase.getInstance().getReference("ad/");
                uploadFile();
            }
        });

        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        newadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(newad.this,newad.class);
                startActivity(i2);
                finish();
            }
        });

        adbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(newad.this,myad.class);
                startActivity(i2);
                finish();
            }
        });

        chatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(newad.this, "new ad pressed", Toast.LENGTH_SHORT).show();
            }
        });

        accountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenta=new Intent(newad.this,account.class);
                startActivity(intenta);
                finish();
            }
        });

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 =new Intent(newad.this,MainActivity.class);
                startActivity(i2);
                finish();
            }
        });
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

    private void uploadFile() {

        if (mImageURi != null) {
            final StorageReference fileReference = storageReference.child(username+category+howold + "." + getFileExtension(mImageURi));
            fileReference.putFile(mImageURi).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            userId=mAuth.getUid();
                            String imgurl = uri.toString();
                            helperclass = new adhelperclass(adtitle,price,desc,location,howold,category,imgurl,userId);
                            databaseReference.push().setValue(helperclass);
                        }
                    });
                    Toast.makeText(newad.this, "new ad added Succesfully", Toast.LENGTH_SHORT).show();
                    imageView.setImageResource(R.drawable.bechde);
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(newad.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressBar.setProgress((int) progress);
                }
            });
        } else {
            Toast.makeText(this, "image not selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = categoryspinner.getSelectedItem().toString();
        howold = howoldspinner.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageURi = data.getData();
            Picasso.get().load(mImageURi).into(imageView);
        }
    }
    @Override
    public void onBackPressed() {
        Intent i2 =new Intent(newad.this,MainActivity.class);
        startActivity(i2);
        finish();

    }
}