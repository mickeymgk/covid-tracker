package com.phonics.covid19tracker.model;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Countries {

    public static class CountryItem {
        Map<String, CountriesDetail> mCountryDetailMap;

        public Map<String, CountriesDetail> getCountryDetails() {
            return mCountryDetailMap;
        }

        public void setCountryDetailMap(Map<String, CountriesDetail> mCountryDetailMap) {
            this.mCountryDetailMap = mCountryDetailMap;
        }
    }

    public static class CountriesDetail {
        private Integer ourid;
        private Integer totalCases;
        private Integer totalRecovered;
        private Integer totalUnresolved;
        private Integer totalDeaths;
        private Integer totalNewCasesToday;
        private Integer totalNewDeathsToday;
        private Integer totalActiveCases;
        private Integer totalSeriousCases;
        private String title;
        private String code;
        private String source;

        public Integer getOurid() {
            return ourid;
        }

        public void setOurid(Integer ourid) {
            this.ourid = ourid;
        }

        public Integer getTotalCases() {
            return totalCases;
        }

        public void setTotalCases(Integer totalCases) {
            this.totalCases = totalCases;
        }

        public Integer getTotalRecovered() {
            return totalRecovered;
        }

        public void setTotalRecovered(Integer totalRecovered) {
            this.totalRecovered = totalRecovered;
        }

        public Integer getTotalUnresolved() {
            return totalUnresolved;
        }

        public void setTotalUnresolved(Integer totalUnresolved) {
            this.totalUnresolved = totalUnresolved;
        }

        public Integer getTotalDeaths() {
            return totalDeaths;
        }

        public void setTotalDeaths(Integer totalDeaths) {
            this.totalDeaths = totalDeaths;
        }

        public Integer getTotalNewCasesToday() {
            return totalNewCasesToday;
        }

        public void setTotalNewCasesToday(Integer totalNewCasesToday) {
            this.totalNewCasesToday = totalNewCasesToday;
        }

        public Integer getTotalNewDeathsToday() {
            return totalNewDeathsToday;
        }

        public void setTotalNewDeathsToday(Integer totalNewDeathsToday) {
            this.totalNewDeathsToday = totalNewDeathsToday;
        }

        public Integer getTotalActiveCases() {
            return totalActiveCases;
        }

        public void setTotalActiveCases(Integer totalActiveCases) {
            this.totalActiveCases = totalActiveCases;
        }

        public Integer getTotalSeriousCases() {
            return totalSeriousCases;
        }

        public void setTotalSeriousCases(Integer totalSeriousCases) {
            this.totalSeriousCases = totalSeriousCases;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        @NonNull
        @Override
        public String toString() {
            return "CountriesDetail{" +
                    "ourid=" + ourid +
                    ", title='" + title + '\'' +
                    ", code='" + code + '\'' +
                    ", source='" + source + '\'' +
                    ", total_cases=" + totalCases +
                    ", total_recovered=" + totalRecovered +
                    ", total_unresolved=" + totalUnresolved +
                    ", total_deaths=" + totalDeaths +
                    ", total_new_cases_today=" + totalNewCasesToday +
                    ", total_new_deaths_today=" + totalNewDeathsToday +
                    ", total_active_cases=" + totalActiveCases +
                    ", total_serious_cases=" + totalSeriousCases+
                    '}';
        }
    }

}
