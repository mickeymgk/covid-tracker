package com.phonics.covid19tracker.networking;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.phonics.covid19tracker.model.CountryDetail;
import com.phonics.covid19tracker.model.Global;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static Repository mRepository;

    public static synchronized  Repository getInstance() {
        if (mRepository == null) {
            mRepository = new Repository();
        }
        return mRepository;
    }

    private DataApi mApi;

    private Repository() {
        mApi = RetroService.createService(DataApi.class);
    }

    private MutableLiveData<Global> globalData = new MutableLiveData<>();

    public LiveData<Global> getGlobalData() {
        return globalData;
    }

    public LiveData<Global> fetchGlobalData() {
       Call<Global> call = mApi.getGlobalData("stats");
       call.enqueue(new Callback<Global>() {
           @Override
           public void onResponse(@NonNull Call<Global> call,@NonNull Response<Global> response) {
               if(response.body()!= null) {
                   globalData.postValue(response.body());
               }
           }

           @Override
           public void onFailure(@NonNull Call<Global> call,@NonNull Throwable t) {
               globalData.postValue(null);
               t.printStackTrace();
           }
       });
       return globalData;
    }

    /** Functions for getting all countries data at once*/

    private MutableLiveData<ResponseBody> allCountriesData = new MutableLiveData<>();

    public LiveData<ResponseBody> getAllCountriesData() {
        return allCountriesData;
    }

    public LiveData<ResponseBody> fetchAllCountriesData() {
       Call<ResponseBody> call = mApi.getAllCountriesData("ALL");
       call.enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(@NonNull Call<ResponseBody> call,@NonNull Response<ResponseBody> response) {
               if(response.body()!= null) {
                   allCountriesData.postValue(response.body());
               }
           }

           @Override
           public void onFailure(@NonNull Call<ResponseBody> call,@NonNull Throwable t) {
               allCountriesData.postValue(null);
               t.printStackTrace();
           }
       });
       return allCountriesData;
    }

    /** Functions for getting country detail*/

    private MutableLiveData<CountryDetail> detailMutableLiveData = new MutableLiveData<>();

    public LiveData<CountryDetail> getCountryDetail() {
        return detailMutableLiveData;
    }

    public LiveData<CountryDetail> fetchDetails(String countryCode) {
        Call<CountryDetail> call = mApi.getCountryDetail(countryCode);
        call.enqueue(new Callback<CountryDetail>() {
            @Override
            public void onResponse(Call<CountryDetail> call, Response<CountryDetail> response) {
                if (response.body() != null) {
                    detailMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CountryDetail> call, Throwable t) {
                t.printStackTrace();
                detailMutableLiveData.postValue(null);
            }
        });
        return detailMutableLiveData;
    }

    /** Functions for getting country timeline data */

    private MutableLiveData<ResponseBody> countryTimeline = new MutableLiveData<>();

    public LiveData<ResponseBody> getCountryTimeline() {
        return countryTimeline;
    }

    public LiveData<ResponseBody> fetchCountryTimeline(String countryCode) {
        Call<ResponseBody> call = mApi.getCountryTimeline(countryCode);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,@NonNull Response<ResponseBody> response) {
                if(response.body()!= null) {
                    countryTimeline.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call,@NonNull Throwable t) {
                countryTimeline.postValue(null);
                t.printStackTrace();
            }
        });
        return countryTimeline;
    }
}

