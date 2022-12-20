package com.phonics.covid19tracker.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CountryRepository {

    private CountryDao countryDao;
    private LiveData<List<Country>> allCountries;

    public CountryRepository(Application application) {
        CountryRoomDatabase db = CountryRoomDatabase.getDatabase(application);
        countryDao = db.countryDao();
        allCountries = countryDao.getAllCountries();
    }

    public LiveData<List<Country>> getAllCountries() {
        return allCountries;
    }

    public void insert(Country country) {
        new insertAsyncTask(countryDao).execute(country);
    }

    public void delete(Country country) {
        new deleteAsyncTask(countryDao).execute(country);
    }

    private static class insertAsyncTask extends AsyncTask<Country, Void, Void> {

        private CountryDao asyncTaskDao;

        insertAsyncTask(CountryDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Country... countries) {
            asyncTaskDao.insert(countries[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Country, Void, Void> {

        private CountryDao asyncTaskDao;

        deleteAsyncTask(CountryDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Country... countries) {
            asyncTaskDao.delete(countries[0]);
            return null;
        }
    }

}
