package com.phonics.covid19tracker.model;

public class Timeline {

    private Integer totalCases;
    private Integer totalRecovered;
    private Integer totalDeaths;
    private Integer totalDailyCases;
    private Integer totalDailyDeaths;
    private String date;

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

    public Integer getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(Integer totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public Integer getTotalDailyCases() {
        return totalDailyCases;
    }

    public void setTotalDailyCases(Integer totalDailyCases) {
        this.totalDailyCases = totalDailyCases;
    }

    public Integer getTotalDailyDeaths() {
        return totalDailyDeaths;
    }

    public void setTotalDailyDeaths(Integer totalDailyDeaths) {
        this.totalDailyDeaths = totalDailyDeaths;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
