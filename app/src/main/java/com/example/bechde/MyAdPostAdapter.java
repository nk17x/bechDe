package com.example.bechde;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MyAdPostAdapter extends FirebaseRecyclerAdapter<mainpost,MyAdPostAdapter.PostViewHolder> {
    Context context;
    FirebaseAuth mAuth;
    String postKey,category,username,howold;

    DatabaseReference databaseReference;
    public MyAdPostAdapter(@NonNull FirebaseRecyclerOptions<mainpost> options,myad myad) {
        super(options);
        this.context=myad;
    }

    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull mainpost model) {
        try {
            final DatabaseReference adRef = getRef(position);
            postKey = adRef.getKey();
            holder.price.setText("Rs." + model.getPrice());
            category=model.getCategory();
            howold=model.getHowold();
            holder.adtitle.setText(model.getAdtitle().toUpperCase());
            holder.desc.setText(model.getDesc());
            holder.location.setText(model.getLocation().toUpperCase());
            Picasso.get().load(model.getImgurl()).into(holder.adimage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.myad_item_layout, parent, false);

        return new PostViewHolder(view);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView price,desc,location,adtitle;
        ImageView adimage;
        Button deletead;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            mAuth = FirebaseAuth.getInstance();
            price=itemView.findViewById(R.id.price);
            desc=itemView.findViewById(R.id.desc);
            adtitle=itemView.findViewById(R.id.adtitle);
            location=itemView.findViewById(R.id.location);
            adimage=itemView.findViewById(R.id.adimage);
            deletead=itemView.findViewById(R.id.deletead);
            deletead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference("ad/" + category);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ad");
                    storageReference.child(username+category+howold+".jpg").delete();
                    Query query = databaseReference.child(postKey);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();
                            }
                            Toast.makeText(itemView.getContext(), "Ad Deleted Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(context,MainActivity.class);
                            context.startActivity(intent);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                }
            });
        }
    }
}
