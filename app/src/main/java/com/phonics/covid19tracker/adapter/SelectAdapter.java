package com.phonics.covid19tracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import com.phonics.covid19tracker.R;
import com.phonics.covid19tracker.model.Countries;
import com.phonics.covid19tracker.ui.flagkit.FlagKit;

import java.util.ArrayList;
import java.util.List;

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.CountryViewHolder> {
    private Context context;
    private List<Countries.CountriesDetail> data;
    private SelectionTracker selectionTracker;

    public SelectAdapter(Context context, List<Countries.CountriesDetail> data) {
        this.context = context;
        this.data = data;
    }

    public void setSelectionTracker(SelectionTracker<String> mSelectionTracker) {
        this.selectionTracker = mSelectionTracker;
    }

    @NonNull
    @Override
    public SelectAdapter.CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_select, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectAdapter.CountryViewHolder holder, int position) {
        Countries.CountriesDetail countriesDetail = data.get(position);
        boolean isSelected = false;
        if (selectionTracker != null) {
            if (selectionTracker.isSelected(countriesDetail.getCode())) {
                isSelected = true;
            }
        }
        holder.bind(position, countriesDetail, isSelected);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CountryViewHolder extends RecyclerView.ViewHolder {

        ImageView countryFlag;
        TextView countryName;
        ConstraintLayout root;
        SelectedDetails selectedDetails;
        Countries.CountriesDetail detail;

        CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            countryFlag = itemView.findViewById(R.id.countries_flag);
            countryName = itemView.findViewById(R.id.countries_name);
            selectedDetails = new SelectedDetails();
            itemView.setClickable(true);
        }

        void bind(int position, Countries.CountriesDetail detail, boolean isSelected) {
            this.detail = detail;
            selectedDetails.position = position;
            selectedDetails.item = detail.getCode();
            try {
                countryFlag.setImageDrawable(FlagKit.drawableWithFlag(context, detail.getCode().toLowerCase()));
            } catch (Exception ignored){}
            countryName.setText(detail.getTitle());
            itemView.setActivated(isSelected);
        }

        ItemDetailsLookup.ItemDetails<String> getItemDetails(@NonNull MotionEvent motionEvent) {
            return selectedDetails;
        }
    }

    /**
     * An {@link ItemDetailsLookup.ItemDetails} that holds details about a {@link String} item like its position and its value.
     */

    public static class SelectedDetails extends ItemDetailsLookup.ItemDetails<String> {

        int position;
        public String item;

        @Override
        public int getPosition() {
            return position;
        }

        @Nullable
        @Override
        public String getSelectionKey() {
            return item;
        }

        @Override
        public boolean inSelectionHotspot(@NonNull MotionEvent e) {
            return true;
        }

        @Override
        public boolean inDragRegion(@NonNull MotionEvent e) {
            return false;
        }
    }
}
