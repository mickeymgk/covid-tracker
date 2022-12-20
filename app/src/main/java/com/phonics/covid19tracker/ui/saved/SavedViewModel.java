package com.phonics.covid19tracker.ui.saved;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.phonics.covid19tracker.database.Country;
import com.phonics.covid19tracker.database.CountryRepository;
import com.phonics.covid19tracker.model.Countries;
import com.phonics.covid19tracker.model.CountryDetail;
import com.phonics.covid19tracker.model.Global;
import com.phonics.covid19tracker.networking.Repository;

import java.util.List;

public class SavedViewModel extends AndroidViewModel {

    private LiveData<List<Country>> mSavedCountry;
    private CountryRepository mRepository;

    public SavedViewModel (Application application) {
        super(application);
        mRepository = new CountryRepository(application);
        mSavedCountry = mRepository.getAllCountries();
    }

    public LiveData<List<Country>> getSavedCountry() {
        return mSavedCountry;
    }

    public void insert(Country country) {
        mRepository.insert(country);
    }

    public void delete(Country country) {
        mRepository.delete(country);
    }

}