package com.phonics.covid19tracker.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.phonics.covid19tracker.model.CountryDetail;
import com.phonics.covid19tracker.model.Global;
import com.phonics.covid19tracker.networking.Repository;

import okhttp3.ResponseBody;

public class DetailViewModel extends ViewModel {

    private LiveData<CountryDetail> detailLiveData;
    private LiveData<ResponseBody> timelineLiveData;
    private static Repository mRepository;

    public void init() {
        if (detailLiveData != null && timelineLiveData != null) {
            return;
        }
        mRepository = Repository.getInstance();
        detailLiveData = mRepository.getCountryDetail();
        timelineLiveData = mRepository.getCountryTimeline();
    }

    public LiveData<CountryDetail> getDetailLiveData(String countryCode) {
        return mRepository.fetchDetails(countryCode);
    }

    public LiveData<ResponseBody> getTimelineLiveData(String countryCode) {
        return mRepository.fetchCountryTimeline(countryCode);

    }

}