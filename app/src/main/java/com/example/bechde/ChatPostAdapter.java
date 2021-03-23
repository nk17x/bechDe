package com.example.bechde;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ChatPostAdapter extends FirebaseRecyclerAdapter<chatpost,ChatPostAdapter.PostViewHolder> {
    Context context;

    public ChatPostAdapter(@NonNull FirebaseRecyclerOptions<chatpost> options,chat chat) {
        super(options);
        this.context=chat;
    }

    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull chatpost model) {
        holder.textView7.setText(model.getMyname().toUpperCase());
        holder.textView8.setText(model.getMymessage().toUpperCase());

    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item_layout, parent, false);

        return new ChatPostAdapter.PostViewHolder(view);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView textView7,textView8;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            textView7=itemView.findViewById(R.id.textView7);
            textView8=itemView.findViewById(R.id.textView8);
        }
    }
}
