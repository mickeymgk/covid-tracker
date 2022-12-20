package com.phonics.covid19tracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Country country);

    @Query("DELETE FROM country_table")
    void deleteAll();

    @Delete()
    void delete(Country country);

    @Query("SELECT * from country_table ORDER BY country ASC")
    LiveData<List<Country>> getAllCountries();
}
