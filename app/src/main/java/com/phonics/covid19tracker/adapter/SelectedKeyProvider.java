package com.phonics.covid19tracker.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;

import com.phonics.covid19tracker.model.Countries;

/**
 * A basic implementation of {@link ItemKeyProvider} for String items.
 */

public class SelectedKeyProvider extends ItemKeyProvider<String> {

    private final Map<String, Integer> mKeyToPosition;
    private List<Countries.CountriesDetail> mCountryList;

    public SelectedKeyProvider(int scope,List<Countries.CountriesDetail> countryList) {
        super(scope);
        mCountryList = countryList;
        mKeyToPosition = new HashMap<>(mCountryList.size());
        int i = 0;
        for (Countries.CountriesDetail detail : mCountryList) {
            mKeyToPosition.put(detail.getCode(), i);
            i++;
        }
    }

    @Nullable
    @Override
    public String getKey(int i) {
        return mCountryList.get(i).getCode();
    }

    @Override
    public int getPosition(@NonNull String s) {
        return mCountryList.indexOf(s);
    }
}
