package com.phonics.covid19tracker.ui.countries;

import com.phonics.covid19tracker.model.Countries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.ResponseBody;

/** Manual JSON parsing thanks for the weird API, Probably its breaking the arch but at least it works */
public class CustomCountriesParser {

    private ArrayList<Countries.CountriesDetail> countryDetails = new ArrayList<>();

    public CustomCountriesParser(ResponseBody responseBody) {
        String response = null;
        try {
//            remove some unnecessary stuffs adding a last patch item hope that doesn't break anything
            response = responseBody.string().replace("\"stat\":\"ok\"", "\"182\": {\n" +
                    "        \"ourid\": 0,\n" +
                    "        \"title\": \"staying at home\",\n" +
                    "        \"code\": \"fuck wet markets\",\n" +
                    "        \"source\": \"fuck Wuhan\",\n" +
                    "        \"total_cases\": 0,\n" +
                    "        \"total_recovered\": 0,\n" +
                    "        \"total_unresolved\": 0,\n" +
                    "        \"total_deaths\": 0,\n" +
                    "        \"total_new_cases_today\": 0,\n" +
                    "        \"total_new_deaths_today\": 0,\n" +
                    "        \"total_active_cases\": 0,\n" +
                    "        \"total_serious_cases\": 0\n" +
                    "      }");

        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject responseObject = null;
        try {
            if (response != null) {
                responseObject = new JSONObject(response);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray responseArray = new JSONArray();
        try {
            if (responseObject != null) {
                responseArray = responseObject.getJSONArray("countryitems");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < responseArray.length(); i++) {
            JSONObject obj = null;
            try {
                obj = responseArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Iterator keys = obj.keys();
            while (keys.hasNext()) {
                String currentDynamicKey = (String) keys.next();
                JSONObject currentDynamicValue = null;
                try {
                    currentDynamicValue = obj.getJSONObject(currentDynamicKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Countries.CountriesDetail detail = new Countries.CountriesDetail();
                try {
                    detail.setOurid(currentDynamicValue.getInt("ourid"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    detail.setTitle(currentDynamicValue.getString("title"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    detail.setCode(currentDynamicValue.getString("code"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    detail.setSource(currentDynamicValue.getString("source"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    detail.setTotalCases(currentDynamicValue.getInt("total_cases"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    detail.setTotalRecovered(currentDynamicValue.getInt("total_recovered"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    detail.setTotalUnresolved(currentDynamicValue.getInt("total_unresolved"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    detail.setTotalDeaths(currentDynamicValue.getInt("total_deaths"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    detail.setTotalNewCasesToday(currentDynamicValue.getInt("total_new_cases_today"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    detail.setTotalNewDeathsToday(currentDynamicValue.getInt("total_new_deaths_today"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    detail.setTotalActiveCases(currentDynamicValue.getInt("total_active_cases"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    detail.setTotalSeriousCases(currentDynamicValue.getInt("total_serious_cases"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//              ... ends here & finally filling out the array
                countryDetails.add(detail);
            }
        }
        if (countryDetails.size()>=1) {
            countryDetails.remove(countryDetails.size() - 1);
        }
    }

     public ArrayList<Countries.CountriesDetail> getCountryArray() {
        return countryDetails;
    }
}
