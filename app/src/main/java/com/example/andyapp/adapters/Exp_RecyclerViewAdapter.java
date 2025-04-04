package com.example.andyapp.adapters;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andyapp.R;
import com.example.andyapp.models.ExpensesModel;

import java.util.ArrayList;

public class Exp_RecyclerViewAdapter extends RecyclerView.Adapter<Exp_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<ExpensesModel> expensesModels;
    double totalExpense;

    public Exp_RecyclerViewAdapter(Context context, ArrayList<ExpensesModel> expensesModels){
        this.context = context;
        this.expensesModels = expensesModels;
        this.totalExpense = 0;
        for (int i = 0; i < expensesModels.size(); i++){
            this.totalExpense += expensesModels.get(i).getAmount();
        }
    }
    @NonNull
    @Override
    public Exp_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate layout and give look to rows
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expense_recycler_view_row, parent, false);
        return new Exp_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Exp_RecyclerViewAdapter.MyViewHolder holder, int position) {
        //assigning values to each view we create in recycler_row_view file
        //based on the position of the recycler view
        holder.catView.setText(expensesModels.get(position).getCategory());
        holder.amtView.setText(String.format("$%.2f", expensesModels.get(position).getAmount()));
        holder.iconView.setImageResource(expensesModels.get(position).getImage());
        setBarWidth(holder.roundedBarView, expensesModels.get(position).getAmount());
        setBarColor(holder.roundedBarView, position);
    }

    @Override
    public int getItemCount() {
        //Number of total items
        return expensesModels.size();
    }

    public void updateData(ArrayList<ExpensesModel> expensesModels){
        this.expensesModels = expensesModels;
        notifyDataSetChanged();
    }

    private void setBarColor(View barView, int position){
        Drawable background =  barView.getBackground();
        int[] colors = context.getResources().getIntArray(R.array.category_colors);
        background.setColorFilter(colors[position], android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private void setBarWidth(View barView, double amtSpent){
        ViewGroup.LayoutParams layoutParams = barView.getLayoutParams();
        layoutParams.width = (int) ((amtSpent / totalExpense) * 1000);
        layoutParams.width = Math.min(900, layoutParams.width);
        layoutParams.width = Math.max(100, layoutParams.width);
        barView.setLayoutParams(layoutParams);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //grabbing views from recycler_row_view.xml file
        ImageView iconView;
        View barView;
        TextView catView, amtView;
        View roundedBarView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iconView = itemView.findViewById(R.id.iconView);
            catView = itemView.findViewById(R.id.categoryView);
            amtView = itemView.findViewById(R.id.amtView);
            roundedBarView = itemView.findViewById(R.id.roundedBarView);
        }
    }
}
