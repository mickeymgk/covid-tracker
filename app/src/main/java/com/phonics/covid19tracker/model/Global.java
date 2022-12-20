package com.phonics.covid19tracker.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Global {

    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("stat")
    @Expose
    public String stat;

    public List<Result> getResults() {
        return results;
    }

    public  class Result {

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
        @SerializedName("total_affected_countries")
        @Expose
        public Integer totalAffectedCountries;
        @SerializedName("source")
        @Expose
        public Source source;

        public class Source {

            @SerializedName("url")
            @Expose
            public String url;

        }

    }
}
