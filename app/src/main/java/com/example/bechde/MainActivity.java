package com.example.bechde;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclermain;
    private MainPostAdapter adapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth mAuth;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        drawerLayout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.nav_view);
        toolbar.setTitle("bechde");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        recyclermain=findViewById(R.id.recyclermain);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false);
        recyclermain.setLayoutManager(mLayoutManager);
        /*recyclermain.setLayoutManager(new LinearLayoutManager(this));*/
        FirebaseRecyclerOptions<mainpost> options =
                new FirebaseRecyclerOptions.Builder<mainpost>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("ad"), mainpost.class)
                        .build();

        adapter=new MainPostAdapter(options,this);
        recyclermain.setAdapter(adapter);
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
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            mAuth.signOut();
                            MainActivity.super.onBackPressed();
                        }
                    }).create().show();
        }

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.navlogout:
                mAuth.signOut();
                Intent intentlogout=new Intent(MainActivity.this,login.class);
                startActivity(intentlogout);
                finish();
                break;
            case R.id.navhome:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.navaccount:
                /*Intent intentaccount=new Intent(MainActivity.this,profile.class);
                startActivity(intentaccount);
                break;*/
            case R.id.navappointments:
                /*Intent intentappointment=new Intent(MainActivity.this,appointment.class);
                startActivity(intentappointment);
                break;*/
            case R.id.navdoctors:
                /*Intent intentdoctors=new Intent(MainActivity.this,search_doctors.class);
                startActivity(intentdoctors);
                break;*/
            case R.id.navmedicalrecords:
               /* Intent intentmedicalrecords=new Intent(MainActivity.this,lab_reports.class);
                startActivity(intentmedicalrecords);
                break;*/
            case R.id.navquerys:
               /* Intent intentquerys=new Intent(MainActivity.this,query.class);
                startActivity(intentquerys);
                break;*/
        }
        return true;
    }
}