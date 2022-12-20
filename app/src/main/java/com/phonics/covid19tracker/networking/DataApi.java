package com.phonics.covid19tracker.networking;

import com.phonics.covid19tracker.model.CountryDetail;
import com.phonics.covid19tracker.model.Global;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataApi {

     String pre = "free-api";
     @GET(pre)
     Call<Global> getGlobalData(@Query("global") String stats);
     
     @GET(pre)
     Call<ResponseBody> getAllCountriesData(@Query("countryTotals") String all);
     
     @GET(pre)
     Call<CountryDetail> getCountryDetail(@Query("countryTotal") String code);
     
     @GET(pre)
     Call<ResponseBody> getCountryTimeline(@Query("countryTimeline") String code);

/** uncomment this if "thevirustracker.com" is offline*/
   
   /**@GET("timeline")
   Call<Global> getGlobalTimeline();

   @GET("countries")
   Call<Repository> getCountriesStat(@Query("include") String timeline);

   @GET("countries?include=timeline")
   Call<Repository> getCountriesWithTimeline();

   getting a single country by path then querying for timeline
   @GET("countries/{code}")
   Call<Repository> getCountryStat(@Path("code") String countryCode, @Query("include") String timeline);

   @GET("countries/")
   Call<Repository> getC 
   */
}
