package com.phonics.covid19tracker.adapter;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

/**
 *  An implementation of a {@link ItemDetailsLookup} to be used to get details when a user make a selection of an item.
 */

public class SelectedLookup extends ItemDetailsLookup<String> {

    private final RecyclerView mRecyclerView;

    public SelectedLookup(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetails<String> getItemDetails(@NonNull MotionEvent e) {
        View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(view);
            if (holder instanceof SelectAdapter.CountryViewHolder) {
                return ((SelectAdapter.CountryViewHolder) holder).getItemDetails(e);
            }
        }
        return null;
    }
}
