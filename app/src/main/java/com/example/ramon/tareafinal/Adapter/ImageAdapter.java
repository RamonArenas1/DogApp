package com.example.ramon.tareafinal.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ramon.tareafinal.Model.Dog;
import com.example.ramon.tareafinal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    private ArrayList<Dog> dogs;
    private Context context;

    public ImageAdapter(Context context) {
        this.context = context;
        dogs=new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.image_dog, viewGroup, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Dog dog = dogs.get(i);
        Glide.with(context)
                .load(dog.getUrl())
                .into(myViewHolder.dogImage);
    }

    @Override
    public int getItemCount() {
        return dogs.size();
    }

    public void addToList(ArrayList<Dog> dogs) {
        this.dogs.addAll(dogs);
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView dogImage;

        MyViewHolder(View v) {
            super(v);

            dogImage = v.findViewById(R.id.dog_photo);
        }
    }
}
