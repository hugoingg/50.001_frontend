package com.example.andyapp.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andyapp.R;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private final Context context;
    private final List<LocalDate> days;
    private final Set<LocalDate> activeDates;
    private OnDateClickListener listener;

    public CalendarAdapter(Context context, List<LocalDate> days, Set<LocalDate> activeDates) {
        this.context = context;
        this.days = days;
        this.activeDates = new HashSet<>(activeDates);
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public interface OnDateClickListener {
        void onClick(LocalDate date);
    }

    public void setOnDateClickListener(OnDateClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayText;

        public ViewHolder(View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.dayCellText);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    LocalDate date = days.get(getAdapterPosition());
                    listener.onClick(date);
                }
            });
        }
    }

    @Override
    public CalendarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.day_cell_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CalendarAdapter.ViewHolder holder, int position) {
        LocalDate date = days.get(position);
        holder.dayText.setText(date == null ? "" : String.valueOf(date.getDayOfMonth()));

        if (date != null && activeDates.contains(date)) {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadius(100);
            drawable.setColor(ContextCompat.getColor(context, R.color.streak_active));
            holder.dayText.setBackground(drawable);
            holder.dayText.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            holder.dayText.setBackground(null);
            holder.dayText.setTextColor(ContextCompat.getColor(context, R.color.black));
        }
    }
}
