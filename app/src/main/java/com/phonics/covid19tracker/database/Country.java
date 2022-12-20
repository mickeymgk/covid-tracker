package com.phonics.covid19tracker.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "country_table")
public class Country {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "country")
    private String code;

    public Country(@NonNull String code) {
        this.code = code;
    }

    @NonNull
    public String getCode() {
        return code;
    }

    public void setCode(@NonNull String code) {
        this.code = code;
    }
}
