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
        holder.price.setText(model.getPrice());
        holder.desc.setText(model.getDesc());
        holder.location.setText(model.getLocation());
        Picasso.get().load(model.getImgurl()).into(holder.adimage);
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_layout, parent, false);

        return new PostViewHolder(view);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView price,desc,location;
        ImageView adimage;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.price);
            desc=itemView.findViewById(R.id.desc);
            location=itemView.findViewById(R.id.location);
            adimage=itemView.findViewById(R.id.adimage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*nt itemPosition = getLayoutPosition();
                    String selected =getRef(itemPosition).getKey();
                    Intent intent=new Intent(context,bookAppointment.class);
                    intent.putExtra("selectedDoctor",selected);
                    intent.putExtra("speciality","doctors/Gastrology");
                    context.startActivity(intent);*/
                    Toast.makeText(context, "ad pressed", Toast.LENGTH_SHORT).show();

        }
    });}}
}
