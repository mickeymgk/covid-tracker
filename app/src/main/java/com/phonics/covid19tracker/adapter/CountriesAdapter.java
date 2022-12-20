package com.phonics.covid19tracker.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.phonics.covid19tracker.R;
import com.phonics.covid19tracker.model.Countries;
import com.phonics.covid19tracker.ui.flagkit.FlagKit;

import java.util.ArrayList;
import java.util.Locale;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountryViewHolder> {
    private Context context;
    private ArrayList<Countries.CountriesDetail> data;

    public CountriesAdapter(Context context, ArrayList<Countries.CountriesDetail> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CountriesAdapter.CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_countries, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesAdapter.CountryViewHolder holder, int position) {
        holder.countryName.setText(data.get(position).getTitle());
        holder.deaths.setText(String.format(Locale.getDefault(),"%,d", data.get(position).getTotalDeaths()));
        holder.recovered.setText(String.format(Locale.getDefault(),"%,d", data.get(position).getTotalRecovered()));
        holder.cases.setText(String.format(Locale.getDefault(),"%,d", data.get(position).getTotalCases()));
        try {
            holder.countryFlag.setImageDrawable(FlagKit.drawableWithFlag(context,data.get(position).getCode().toLowerCase()));
        } catch (Exception e){
            e.printStackTrace();
        }
        holder.itemView.setClickable(true);
        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("code", data.get(position).getCode());
            Navigation.findNavController(view).navigate(R.id.action_nav_countries_to_nav_detail,bundle);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class CountryViewHolder extends RecyclerView.ViewHolder {

        ImageView countryFlag;
        TextView countryName, cases, recovered, deaths;

        CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            countryFlag = itemView.findViewById(R.id.countries_flag);
            countryName = itemView.findViewById(R.id.countries_name);
            cases = itemView.findViewById(R.id.countries_cases);
            recovered = itemView.findViewById(R.id.countries_recovered);
            deaths = itemView.findViewById(R.id.countries_deaths);
        }
    }
}
