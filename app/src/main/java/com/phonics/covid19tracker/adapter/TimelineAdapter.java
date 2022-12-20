package com.phonics.covid19tracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phonics.covid19tracker.R;
import com.phonics.covid19tracker.model.Timeline;

import java.util.ArrayList;
import java.util.Locale;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {
    private Context context;
    private ArrayList<Timeline> data;

    public TimelineAdapter(Context context, ArrayList<Timeline> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public TimelineAdapter.TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_timeline, parent, false);
        return new TimelineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineAdapter.TimelineViewHolder holder, int position) {
        holder.date.setText(data.get(position).getDate());
        holder.totalRecovered.setText(String.format(Locale.getDefault(),"%,d", data.get(position).getTotalRecovered()));
        holder.totalCases.setText(String.format(Locale.getDefault(),"%,d", data.get(position).getTotalCases()));
        holder.totalDeaths.setText(String.format(Locale.getDefault(),"%,d", data.get(position).getTotalDeaths()));
        holder.dailyCases.setText(String.format(Locale.getDefault(),"%,d", data.get(position).getTotalDailyCases()));
        holder.dailyDeaths.setText(String.format(Locale.getDefault(),"%,d", data.get(position).getTotalDailyDeaths()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class TimelineViewHolder extends RecyclerView.ViewHolder {

        TextView date, totalCases, totalRecovered, totalDeaths, dailyDeaths, dailyCases;

        TimelineViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            totalCases = itemView.findViewById(R.id.tot_cases);
            totalRecovered = itemView.findViewById(R.id.tot_recovery);
            totalDeaths = itemView.findViewById(R.id.tot_deaths);
            dailyCases = itemView.findViewById(R.id.tot_daily_cases);
            dailyDeaths = itemView.findViewById(R.id.tot_daily_deaths);

        }
    }
}
