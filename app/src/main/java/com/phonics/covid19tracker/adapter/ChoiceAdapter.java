package com.phonics.covid19tracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.phonics.covid19tracker.R;
import com.phonics.covid19tracker.database.Country;
import com.phonics.covid19tracker.ui.flagkit.FlagKit;

import java.util.ArrayList;

public class ChoiceAdapter extends RecyclerView.Adapter<ChoiceAdapter.CountryViewHolder> {
    private Context context;
    private ArrayList<Country> data;
    SelectionInterface itemSelection;

    public interface SelectionInterface {
        void setSelected(String code);
    }

    public ChoiceAdapter(Context context, ArrayList<Country> data, SelectionInterface itemSelection) {
        this.context = context;
        this.data = data;
        this.itemSelection = itemSelection;
    }

    @NonNull
    @Override
    public ChoiceAdapter.CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chip, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoiceAdapter.CountryViewHolder holder, int position) {
        holder.chip.setText(data.get(position).getCode());

        holder.chip.setIconStartPadding(12);
        try {
            holder.chip.setChipIcon(FlagKit.drawableWithFlag(context, data.get(position).getCode().toLowerCase()));
        } catch (Exception ignored){}
        holder.chip.setOnClickListener(view -> itemSelection.setSelected(data.get(position).getCode()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class CountryViewHolder extends RecyclerView.ViewHolder {

        Chip chip;

        CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            chip = itemView.findViewById(R.id.chip);
        }
    }
}
