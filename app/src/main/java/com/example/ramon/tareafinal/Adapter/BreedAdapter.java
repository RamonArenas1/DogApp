package com.example.ramon.tareafinal.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ramon.tareafinal.Model.Breed;
import com.example.ramon.tareafinal.R;

import java.util.ArrayList;
import java.util.List;

public class BreedAdapter extends RecyclerView.Adapter<BreedAdapter.MyViewHolder> implements View.OnClickListener {

    private ArrayList<Breed> breedList;
    private View.OnClickListener listener;

    public void addToList(List<Breed> breeds) {
        breedList.addAll(breeds);
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView breedName;

        MyViewHolder(View v) {
            super(v);

            breedName = v.findViewById(R.id.breedName);
        }
    }

    public BreedAdapter() {
        this.breedList = new ArrayList<>();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.breed_card, viewGroup, false);
        MyViewHolder vh = new MyViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Breed spider = breedList.get(i);
        myViewHolder.breedName.setText(ucFirst(spider.getName()));
    }

    @Override
    public int getItemCount() {
        return breedList.size();
    }

    public static String ucFirst(String str) {
        if (str.isEmpty()) {
            return str;
        } else {
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
        }
    }
}
