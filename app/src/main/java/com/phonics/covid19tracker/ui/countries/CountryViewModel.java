package com.phonics.covid19tracker.ui.countries;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.phonics.covid19tracker.networking.Repository;

import okhttp3.ResponseBody;

public class CountryViewModel extends ViewModel {

    private  LiveData<ResponseBody> countriesLiveData;
    private static Repository mRepository;

    public void init() {
        if (countriesLiveData != null) {
            return;
        }
        mRepository = Repository.getInstance();
        countriesLiveData = mRepository.getAllCountriesData();
    }

    public LiveData<ResponseBody> getCountriesLiveData() {
        return mRepository.fetchAllCountriesData();
    }

}