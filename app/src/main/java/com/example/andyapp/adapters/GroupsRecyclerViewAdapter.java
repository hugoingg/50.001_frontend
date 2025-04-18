package com.example.andyapp.adapters;
import com.bumptech.glide.Glide;
import com.example.andyapp.DataObserver;
import com.example.andyapp.LoginActivity;
import com.example.andyapp.NavigationDrawerActivity;
import com.example.andyapp.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andyapp.models.GroupsModel;
import com.example.andyapp.models.GroupsModels;
import com.example.andyapp.queries.GroupsService;
import com.example.andyapp.queries.NotificationService;
import com.example.andyapp.queries.UserService;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GroupsRecyclerViewAdapter extends RecyclerView.Adapter<GroupsRecyclerViewAdapter.MyViewHolder> implements DataObserver<GroupsModels> {
    Context context;
    ArrayList<GroupsModel> groupsModels;
    SharedPreferences mPref;
    NotificationService notificationService;
    private String TAG = "LOGCAT";

    public GroupsRecyclerViewAdapter(ArrayList<GroupsModel> groupsModels, Context context) {
        this.groupsModels = groupsModels;
        this.context = context;
        this.notificationService = new NotificationService(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.groups_recycler_view_row, parent, false);
        return new GroupsRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        mPref = context.getSharedPreferences(LoginActivity.PREFTAG, Context.MODE_PRIVATE);
        GroupsModel model = groupsModels.get(position);
        String connectionId = model.getUserId();
        String fromUsername = mPref.getString(LoginActivity.USERNAMEKEY, LoginActivity.DEFAULT_USERNAME);
        String toUsername = model.getName();
        String imageUrl = model.getImageUrl();
        Glide.with(context)
                .load(imageUrl)
                .circleCrop()
                .into(holder.imageView);
        holder.nameView.setText(model.getName());
        holder.relationView.setText("Connection");
        holder.btnNudge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Looper looper = Looper.getMainLooper();
                Handler handler = new Handler(looper);
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        notificationService.sendNudge(fromUsername, toUsername, handler);
                    }
                });
            }
        });
        holder.groupsBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = mPref.edit();
                editor.putString(LoginActivity.VIEWERKEY, connectionId);
                editor.putString(LoginActivity.VIEWERIMAGEKEY, imageUrl);
                editor.apply();
                UserService userService = new UserService(context);
                userService.updateViewDashboard(fromUsername);
                Intent intent = new Intent(context, NavigationDrawerActivity.class);
                intent.putExtra(NavigationDrawerActivity.CONNECTION_NAME_TAG, model.getName());
                intent.putExtra(NavigationDrawerActivity.FRAGMENT_TAG, "ViewExpense");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupsModels.size();
    }

    @Override
    public void updateData(GroupsModels data) {
        this.groupsModels = data.getGroupsModels();
        for(GroupsModel model: groupsModels){
            Log.d(TAG, model.getImageUrl());
        }
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView nameView, relationView;
        AppCompatButton btnNudge;
        ImageButton groupsBtnView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.avatarView);
            this.nameView = itemView.findViewById(R.id.nameView);
            this.relationView = itemView.findViewById(R.id.relationView);
            this.btnNudge = itemView.findViewById(R.id.btnNudge);
            this.groupsBtnView = itemView.findViewById(R.id.groupsBtnView);
        }
    }
}
