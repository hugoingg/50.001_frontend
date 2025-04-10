package com.example.andyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsernameModelRCV extends RecyclerView.Adapter<UsernameModelRCV.MyViewHolder> {
    private final Context context;
    private ArrayList<UsernameModel> usernameModels;

    public UsernameModelRCV(Context context, ArrayList<UsernameModel> usernameModels){
        this.context = context;
        this.usernameModels = usernameModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UsernameModel model = usernameModels.get(position);

        holder.rank.setText(String.format("%02d", position + 4));
        holder.name.setText(model.getName());
        holder.points.setText(model.getNumber_points());
        holder.pointsChange.setText(model.getPointsUpDown());
        holder.imgArrow.setImageResource(model.getArrowUpDown_img());
        holder.imgProfile.setImageResource(model.getImage());
    }

    @Override
    public int getItemCount() {
        return usernameModels.size();
    }

    public void updateData(ArrayList<UsernameModel> newList) {
        this.usernameModels = newList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProfile, imgArrow;
        TextView rank, name, points, pointsChange;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            rank = itemView.findViewById(R.id.rank);
            imgProfile = itemView.findViewById(R.id.profilepicture);
            imgArrow = itemView.findViewById(R.id.arrowUpDown);
            name = itemView.findViewById(R.id.username);
            points = itemView.findViewById(R.id.points);
            pointsChange = itemView.findViewById(R.id.arrowChangeText);
        }
    }
}
