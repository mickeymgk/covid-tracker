package com.phonics.covid19tracker.ui.select;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.phonics.covid19tracker.R;
import com.phonics.covid19tracker.adapter.ChoiceAdapter;
import com.phonics.covid19tracker.adapter.SelectAdapter;
import com.phonics.covid19tracker.adapter.SelectedKeyProvider;
import com.phonics.covid19tracker.adapter.SelectedLookup;
import com.phonics.covid19tracker.database.Country;
import com.phonics.covid19tracker.model.Countries;
import com.phonics.covid19tracker.ui.countries.CountryViewModel;
import com.phonics.covid19tracker.ui.countries.CustomCountriesParser;
import com.phonics.covid19tracker.ui.saved.SavedViewModel;

import java.util.ArrayList;
import java.util.List;

public class SelectFragment extends Fragment {

    private CountryViewModel countryViewModel;
    private SavedViewModel savedViewModel;
    private SelectAdapter selectAdapter;
    private List<Countries.CountriesDetail> countryList = new ArrayList<>();
    private RecyclerView rv;
    private RecyclerView selectionRecycler;
    private SwipeRefreshLayout swipe;
    private SelectionTracker<String> selectionTracker;
    private ArrayList<Country> choices = new ArrayList<>();
    private ChoiceAdapter choiceAdapter;
    private MaterialCardView cardView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        countryViewModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        savedViewModel = ViewModelProviders.of(this).get(SavedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_select, container, false);
        cardView = root.findViewById(R.id.card_selection);
        rv = root.findViewById(R.id.recycler_all);
        selectionRecycler = root.findViewById(R.id.recycler_items);
        swipe = root.findViewById(R.id.swipe_countries);
        swipe.setOnRefreshListener(() -> countryViewModel.getCountriesLiveData());
        setupRecyclerView();
        countryViewModel.init();
        countryViewModel.getCountriesLiveData().observe(getViewLifecycleOwner(), responseBody -> {
            if (responseBody != null) {
                swipe.setRefreshing(false);
                countryList.clear();
                CustomCountriesParser countriesParser = new CustomCountriesParser(responseBody);
                countryList.addAll(countriesParser.getCountryArray());
                selectAdapter.notifyDataSetChanged();
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
        savedViewModel.getSavedCountry().observe(getViewLifecycleOwner(), countries -> {
            choices.clear();
            choices.addAll(countries);
            choiceAdapter.notifyDataSetChanged();
        });

        selectionTracker = new SelectionTracker.Builder<>(
                "country-selection",
                rv,
                new SelectedKeyProvider(1,countryList),
                new SelectedLookup(rv),
                StorageStrategy.createStringStorage()).withSelectionPredicate(SelectionPredicates.createSelectAnything())
                .build();

        selectAdapter.setSelectionTracker(selectionTracker);

        selectionTracker.addObserver(new SelectionTracker.SelectionObserver() {
            @Override
            public void onItemStateChanged(@NonNull Object key, boolean selected) {
                super.onItemStateChanged(key, selected);
                if (selected) {
                    Country country = new Country(String.valueOf(key));
                    savedViewModel.insert(country);
                } else {
                    Country country = new Country(String.valueOf(key));
                    savedViewModel.delete(country);
                }
            }
        });
        return root;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        selectionTracker.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        selectionTracker.onSaveInstanceState(outState);
    }

    private void setupRecyclerView() {
        selectAdapter = new SelectAdapter(getContext(), countryList);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rv.setAdapter(selectAdapter);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);

        choiceAdapter = new ChoiceAdapter(getContext(), choices, code -> {
            Country country = new Country(code);
            savedViewModel.delete(country);
        });
        selectionRecycler.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false));
        selectionRecycler.setAdapter(choiceAdapter);
    }

}
