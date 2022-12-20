package com.phonics.covid19tracker.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.phonics.covid19tracker.MainFuckingActivity;
import com.phonics.covid19tracker.R;
import com.phonics.covid19tracker.adapter.TimelineAdapter;
import com.phonics.covid19tracker.model.CountryDetail;
import com.phonics.covid19tracker.model.Timeline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class DetailFragment extends Fragment {

    private DetailViewModel detailViewModel;
    private String code;
    private TimelineAdapter timelineAdapter;
    private ArrayList<Timeline> timelineList = new ArrayList<>();
    private RecyclerView rv;
    private AppCompatTextView timeline;

    //    sneezed while creating this class!!! o_O
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        rv = root.findViewById(R.id.recycler_timeline);
        timeline = root.findViewById(R.id.label_timeline);
        SwipeRefreshLayout swipe = root.findViewById(R.id.home_refresh);
        swipe.setOnRefreshListener(() -> {
            detailViewModel.getDetailLiveData(code);
            detailViewModel.getTimelineLiveData(code);
        });
        LinearLayout rootLayout = root.findViewById(R.id.root_home);
        TextView total_cases = root.findViewById(R.id.cases);
        TextView cases_today = root.findViewById(R.id.cases_today);
        TextView total_death = root.findViewById(R.id.death);
        TextView death_today = root.findViewById(R.id.death_today);
        TextView total_recovery = root.findViewById(R.id.recovery);
        TextView unresolved = root.findViewById(R.id.unresolved);
        TextView total_active = root.findViewById(R.id.active);
        TextView total_serious = root.findViewById(R.id.serious);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            code = bundle.getString("code");
        }
        detailViewModel.init();
        swipe.setRefreshing(true);
        detailViewModel.getDetailLiveData(code).observe(getViewLifecycleOwner(), countryDetail -> {
            if (countryDetail != null) {
                swipe.setRefreshing(false);
                CountryDetail.Countrydatum data = countryDetail.countrydata.get(0);
                ((MainFuckingActivity) getActivity()).setToolbarTitle(countryDetail.countrydata.get(0).info.title);
                total_cases.setText(String.format(Locale.getDefault(), "%,d", data.totalCases));
                cases_today.setText(String.format(Locale.getDefault(), "%,d", data.totalNewCasesToday));
                total_death.setText(String.format(Locale.getDefault(), "%,d", data.totalDeaths));
                death_today.setText(String.format(Locale.getDefault(), "%,d", data.totalNewDeathsToday));
                total_recovery.setText(String.format(Locale.getDefault(), "%,d", data.totalRecovered));
                unresolved.setText(String.format(Locale.getDefault(), "%,d", data.totalUnresolved));
                total_active.setText(String.format(Locale.getDefault(), "%,d", data.totalActiveCases));
                total_serious.setText(String.format(Locale.getDefault(), "%,d", data.totalSeriousCases));
                rootLayout.setVisibility(View.VISIBLE);
            } else {
                swipe.setRefreshing(false);
                Snackbar.make(root, "Error requesting server, swipe down to retry", Snackbar.LENGTH_INDEFINITE)
                        .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                        .setAction("Retry", view -> swipe.setRefreshing(true)).show();
            }
        });

        //TODO: this shits performance replace it with async
        detailViewModel.getTimelineLiveData(code).observe(getViewLifecycleOwner(), responseBody -> {
            setupRecyclerView();
            if (responseBody != null) {

//                    Welcome to the try-catch party I know I can suppress warnings through annotations
//                    But I wanted to a crash free app
                String response = null;
                try {
                    response = responseBody.string().replace("\"stat\":\"ok\""," \"12\": {\n" +
                            "        \"new_daily_cases\": 0,\n" +
                            "        \"new_daily_deaths\": 0,\n" +
                            "        \"total_cases\": 0,\n" +
                            "        \"total_recoveries\": 0,\n" +
                            "        \"total_deaths\": 0\n" +
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
                        responseArray = responseObject.getJSONArray("timelineitems");
                        System.out.println(responseArray.toString());
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
                        Timeline detail = new Timeline();
                        try {
                            detail.setTotalDailyCases(currentDynamicValue.getInt("new_daily_cases"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            detail.setTotalDailyDeaths(currentDynamicValue.getInt("new_daily_deaths"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            detail.setTotalCases(currentDynamicValue.getInt("total_cases"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            detail.setTotalRecovered(currentDynamicValue.getInt("total_recoveries"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            detail.setTotalDeaths(currentDynamicValue.getInt("total_deaths"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        detail.setDate(currentDynamicKey);
//                           ... ends here & finally filling out the array
                        timelineList.add(detail);
                        timelineAdapter.notifyDataSetChanged();
                    }
                    timelineList.remove(timelineList.size()-1);

                }
                timeline.setVisibility(View.VISIBLE);
            } else {
                swipe.setRefreshing(false);
                Snackbar.make(root, "Error requesting server, swipe down to retry", Snackbar.LENGTH_INDEFINITE)
                        .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                        .setAction("Retry", view -> swipe.setRefreshing(true)).show();
            }
        });
        return root;
    }

    private void setupRecyclerView() {
        timelineAdapter = new TimelineAdapter(getContext(), timelineList);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(timelineAdapter);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);
    }

}