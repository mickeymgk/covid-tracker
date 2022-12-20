package com.phonics.covid19tracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Country.class}, version = 1)
public abstract class CountryRoomDatabase extends RoomDatabase {

    public abstract CountryDao countryDao();
    private static CountryRoomDatabase INSTANCE;

    static CountryRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CountryRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CountryRoomDatabase.class, "country_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
