package com.example.bechde;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class chattingPostAdapter extends FirebaseRecyclerAdapter<chattingpost,chattingPostAdapter.PostViewHolder> {
    Context context;
    String aduname,aduimgurl;

    public chattingPostAdapter(@NonNull FirebaseRecyclerOptions<chattingpost> options, chatting chatting) {
        super(options);
        this.context=chatting;
    }

    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull chattingpost model) {
        holder.uctextView.setText(model.getFcname1().toUpperCase());
        holder.uctitleView.setText(model.getAdtitle());
        Glide.with(holder.ucimageView).load(model.getImgurl())
                .transform(new FitCenter(),new RoundedCorners(15))
                .into(holder.ucimageView);
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userchats_item_layout, parent, false);

        return new PostViewHolder(view);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView uctextView,uctitleView;
        ImageView ucimageView;
        Button ucButton;
        String u1id,u2id;
        FirebaseAuth mAuth;
        FirebaseDatabase rootNode;
        DatabaseReference databaseReference,databaseReference2,databaseReference3,databaseReference4;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
           uctextView=itemView.findViewById(R.id.uctextView);
            ucimageView=itemView.findViewById(R.id.ucimageView);
            uctitleView=itemView.findViewById(R.id.uctitleView);
            ucButton=itemView.findViewById(R.id.ucButton);
            mAuth=FirebaseAuth.getInstance();
            uctextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemPosition = getLayoutPosition();
                    String selected =getRef(itemPosition).getKey();
                    Intent intent=new Intent(context,chat.class);
                    intent.putExtra("aduname",aduname);
                    intent.putExtra("fccuid",selected);
                    intent.putExtra("aduimgurl",aduimgurl);
                    context.startActivity(intent);
                }
            });
            ucimageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemPosition = getLayoutPosition();
                    String selected =getRef(itemPosition).getKey();
                    Intent intent=new Intent(context,chat.class);
                    intent.putExtra("aduname",aduname);
                    intent.putExtra("fccuid",selected);
                    intent.putExtra("aduimgurl",aduimgurl);
                    context.startActivity(intent);
                }
            });
            uctitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemPosition = getLayoutPosition();
                    String selected =getRef(itemPosition).getKey();
                    Intent intent=new Intent(context,chat.class);
                    intent.putExtra("aduname",aduname);
                    intent.putExtra("fccuid",selected);
                    intent.putExtra("aduimgurl",aduimgurl);
                    context.startActivity(intent);
                }
            });
            ucButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int itemPosition = getLayoutPosition();
                    String selected =getRef(itemPosition).getParent().getKey();
                    String selected2 =getRef(itemPosition).getKey();
                    Toast.makeText(context,selected , Toast.LENGTH_SHORT).show();
                    Toast.makeText(context,selected2 , Toast.LENGTH_SHORT).show();
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    Query userdetails5 = databaseReference.child("chat/chatid/"+selected+"/"+selected2+"/");
                    userdetails5.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String fcuid1 = snapshot.child("fcuid1").getValue(String.class);
                                databaseReference2 = FirebaseDatabase.getInstance().getReference();
                                databaseReference2.child("chat/chatid/"+selected+"/").child(selected2).removeValue();
                                databaseReference3 = FirebaseDatabase.getInstance().getReference();
                                databaseReference3.child("chat/chatid/"+fcuid1+"/").child(selected2).removeValue();
                                databaseReference4 = FirebaseDatabase.getInstance().getReference();
                                databaseReference4.child("chat/chats/").child(selected2).removeValue();

                            }}
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });

        }
    }
}
