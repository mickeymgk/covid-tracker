package com.phonics.covid19tracker.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Notification {
    private String message;
    private String url;
    private String positiveText;
    private String negativeText;

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public String getPositiveText() {
        return positiveText;
    }

    public String getNegativeText() {
        return negativeText;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPositiveText(String positiveText) {
        this.positiveText = positiveText;
    }

    public void setNegativeText(String negativeText) {
        this.negativeText = negativeText;
    }
}
