package com.example.bechde;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class chattingPostAdapter extends FirebaseRecyclerAdapter<chattingpost,chattingPostAdapter.PostViewHolder> {
    Context context;
    public chattingPostAdapter(@NonNull FirebaseRecyclerOptions<chattingpost> options, chatting chatting) {
        super(options);
        this.context=chatting;
    }

    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull chattingpost model) {
        holder.uctextView.setText(model.getFcname1().toUpperCase());
        Glide.with(holder.ucimageView).load(model.getImgurl()).fitCenter().into(holder.ucimageView);
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userchats_item_layout, parent, false);

        return new PostViewHolder(view);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView uctextView;
        ImageView ucimageView;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
           uctextView=itemView.findViewById(R.id.uctextView);
            ucimageView=itemView.findViewById(R.id.ucimageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemPosition = getLayoutPosition();
                    String selected =getRef(itemPosition).getKey();
                    Intent intent=new Intent(context,chat.class);
                    intent.putExtra("fccuid",selected);
                    context.startActivity(intent);




                }
            });

        }
    }
}
