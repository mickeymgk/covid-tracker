
package com.phonics.covid19tracker.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryDetail {

    @SerializedName("countrydata")
    @Expose
    public List<Countrydatum> countrydata = null;
    @SerializedName("stat")
    @Expose
    public String stat;

    public static class Countrydatum {

        @SerializedName("info")
        @Expose
        public Info info;
        @SerializedName("total_cases")
        @Expose
        public Integer totalCases;
        @SerializedName("total_recovered")
        @Expose
        public Integer totalRecovered;
        @SerializedName("total_unresolved")
        @Expose
        public Integer totalUnresolved;
        @SerializedName("total_deaths")
        @Expose
        public Integer totalDeaths;
        @SerializedName("total_new_cases_today")
        @Expose
        public Integer totalNewCasesToday;
        @SerializedName("total_new_deaths_today")
        @Expose
        public Integer totalNewDeathsToday;
        @SerializedName("total_active_cases")
        @Expose
        public Integer totalActiveCases;
        @SerializedName("total_serious_cases")
        @Expose
        public Integer totalSeriousCases;
        @SerializedName("total_danger_rank")
        @Expose
        public Integer totalDangerRank;

    }

    public static class Info {

        @SerializedName("ourid")
        @Expose
        public Integer ourid;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("code")
        @Expose
        public String code;
        @SerializedName("source")
        @Expose
        public String source;

    }
}
