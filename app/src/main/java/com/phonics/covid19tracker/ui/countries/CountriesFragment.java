package com.phonics.covid19tracker.ui.countries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.phonics.covid19tracker.R;
import com.phonics.covid19tracker.adapter.CountriesAdapter;
import com.phonics.covid19tracker.model.Countries;

import java.util.ArrayList;

public class CountriesFragment extends Fragment {

    private CountryViewModel countryViewModel;
    private CountriesAdapter countriesAdapter;
    private ArrayList<Countries.CountriesDetail> countryList = new ArrayList<>();
    private RecyclerView rv;
    private SwipeRefreshLayout swipe;

    @SuppressWarnings("InterruptedException")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        countryViewModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_countries, container, false);
        rv = root.findViewById(R.id.recycler_all);
        swipe = root.findViewById(R.id.swipe_countries);
        swipe.setOnRefreshListener(() -> countryViewModel.getCountriesLiveData());
        swipe.setRefreshing(true);
        countryViewModel.init();
        countryViewModel.getCountriesLiveData().observe(getViewLifecycleOwner(), responseBody -> {
            setupRecyclerView();
            if(responseBody != null) {
                swipe.setRefreshing(false);
                countryList.clear();
                CustomCountriesParser countriesParser = new CustomCountriesParser(responseBody);
                countryList.addAll(countriesParser.getCountryArray());
                countriesAdapter.notifyDataSetChanged();
            } else {
                // handling loose connectivity
                swipe.setRefreshing(false);
                Snackbar.make(root, "Error requesting server, swipe down to retry", Snackbar.LENGTH_INDEFINITE)
                        .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
                        .setAction("Retry", view -> {
                            swipe.setRefreshing(true);
                            countryViewModel.getCountriesLiveData();
                        }).show();
            }
        });
        return root;
    }

    private void setupRecyclerView() {
            countriesAdapter = new CountriesAdapter(getContext(), countryList);
            rv.setLayoutManager(new LinearLayoutManager(getContext()));
            rv.setAdapter(countriesAdapter);
            rv.setItemAnimator(new DefaultItemAnimator());
            rv.setHasFixedSize(true);
    }

}
