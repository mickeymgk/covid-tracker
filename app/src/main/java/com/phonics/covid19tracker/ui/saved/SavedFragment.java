package com.phonics.covid19tracker.ui.saved;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.phonics.covid19tracker.R;
import com.phonics.covid19tracker.adapter.CountriesAdapter;
import com.phonics.covid19tracker.adapter.SavedAdapter;
import com.phonics.covid19tracker.database.Country;
import com.phonics.covid19tracker.model.Countries;
import com.phonics.covid19tracker.model.CountryDetail;
import com.phonics.covid19tracker.ui.countries.CountryViewModel;
import com.phonics.covid19tracker.ui.countries.CustomCountriesParser;
import com.phonics.covid19tracker.ui.detail.DetailViewModel;

import java.util.ArrayList;
import java.util.Iterator;

public class SavedFragment extends Fragment {

    private SavedViewModel savedViewModel;
    private CountryViewModel countryViewModel;
    private SavedAdapter savedAdapter;
    private ArrayList<Countries.CountriesDetail> allCountries = new ArrayList<>();
    private ArrayList<Countries.CountriesDetail> filteredList = new ArrayList<>();
    private ArrayList<String> filterCodes = new ArrayList<>();
    private RecyclerView rv;
    private SwipeRefreshLayout swipe;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        savedViewModel = ViewModelProviders.of(this).get(SavedViewModel.class);
        countryViewModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_saved, container, false);
        countryViewModel.init();
        rv = root.findViewById(R.id.recycler_saved);
        setupRecyclerView();
        swipe = root.findViewById(R.id.swipe_countries);
        swipe.setOnRefreshListener(() -> {
            countryViewModel.getCountriesLiveData();
        });
        swipe.setRefreshing(true);
        FloatingActionButton addButton  = root.findViewById(R.id.add_countries);
        addButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_saved_to_nav_select));
        savedViewModel.getSavedCountry().observe(getViewLifecycleOwner(), countries -> {
            filterCodes.clear();
            for (Country country: countries){
                filterCodes.add(country.getCode());
            }
        });
        countryViewModel.getCountriesLiveData().observe(getViewLifecycleOwner(), responseBody -> {
            allCountries.clear();
            filteredList.clear();
            if (responseBody != null) {
                swipe.setRefreshing(false);
                allCountries.clear();
                CustomCountriesParser countriesParser = new CustomCountriesParser(responseBody);
                allCountries.addAll(countriesParser.getCountryArray());
                filter();
            } else {
                swipe.setRefreshing(false);
                Snackbar.make(rv, "Error requesting server, swipe down to retry", Snackbar.LENGTH_LONG)
                        .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                        .setAction("Retry", view -> {
                            swipe.setRefreshing(true);
                            countryViewModel.getCountriesLiveData();
                        }).show();
            }
        });
        return root;
    }

//    TODO: was making this more elegant
    void filter() {
        if (filterCodes!=null) {
            for (Countries.CountriesDetail countriesDetail: allCountries){
                for (String code: filterCodes){
                    if (countriesDetail.getCode().equals(code)) {
                        filteredList.add(countriesDetail);
                    }
                }
            }
        }
        savedAdapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {
        savedAdapter = new SavedAdapter(getContext(), filteredList);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(savedAdapter);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
