package com.phonics.covid19tracker.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.phonics.covid19tracker.model.Global;
import com.phonics.covid19tracker.networking.Repository;

public class HomeViewModel extends ViewModel {

    private LiveData<Global> globalLiveData;
    private static Repository mRepository;

    public void init() {
        if (globalLiveData != null) {
            return;
        }
        mRepository = Repository.getInstance();
        globalLiveData = mRepository.getGlobalData();
    }

    public LiveData<Global> getGlobalData() {
        return mRepository.fetchGlobalData();
    }

}