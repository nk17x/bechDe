package com.example.bechde;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.squareup.picasso.Picasso;

public class MainPostAdapter extends FirebaseRecyclerAdapter<mainpost,MainPostAdapter.PostViewHolder> {
Context context;
    public MainPostAdapter(@NonNull FirebaseRecyclerOptions<mainpost> options, MainActivity mainActivity) {
        super(options);
        this.context=mainActivity;
    }

    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull mainpost model) {
        holder.price.setText("Rs."+model.getPrice());
        holder.adtitle.setText(model.getAdtitle().toUpperCase());
        holder.desc.setText(model.getDesc());
        holder.location.setText(model.getLocation().toUpperCase());
        Glide.with(holder.adimage).load(model.getImgurl()).centerCrop().into(holder.adimage);

    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_layout, parent, false);

        return new PostViewHolder(view);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView price,desc,location,adtitle;
        ImageView adimage;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.price);
            desc=itemView.findViewById(R.id.desc);
            adtitle=itemView.findViewById(R.id.adtitle);
            location=itemView.findViewById(R.id.location);
            adimage=itemView.findViewById(R.id.adimage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemPosition = getLayoutPosition();
                    String selected =getRef(itemPosition).getKey();
                    Intent intent=new Intent(context,showad.class);
                    intent.putExtra("selectedadkey",selected);
                    context.startActivity(intent);

        }
    });}}
}
