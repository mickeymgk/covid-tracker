package com.phonics.covid19tracker.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.phonics.covid19tracker.R;
import com.phonics.covid19tracker.model.Global;
import com.phonics.covid19tracker.model.Notification;

import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private AppCompatTextView messsage;
    private MaterialButton buttonPositive, buttonNegative;
    private ConstraintLayout constraintLayout;
    private FirebaseAnalytics mFirebaseAnalytics;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        messsage = root.findViewById(R.id.message);
        buttonNegative = root.findViewById(R.id.btn_negative);
        buttonPositive = root.findViewById(R.id.btn_positive);
        constraintLayout = root.findViewById(R.id.root_push);
        LinearLayout rootLayout = root.findViewById(R.id.root_home);
        SwipeRefreshLayout swipe = root.findViewById(R.id.home_refresh);
        swipe.setOnRefreshListener(() -> homeViewModel.getGlobalData());
//        TODO: Replace this manual binding with the new viewBinding lib that google introduced
        TextView total_cases = root.findViewById(R.id.cases);
        TextView cases_today = root.findViewById(R.id.cases_today);
        TextView total_death = root.findViewById(R.id.death);
        TextView death_today = root.findViewById(R.id.death_today);
        TextView total_recovery = root.findViewById(R.id.recovery);
        TextView unresolved = root.findViewById(R.id.unresolved);
        TextView total_active = root.findViewById(R.id.active);
        TextView total_serious = root.findViewById(R.id.serious);
        homeViewModel.init();
        swipe.setRefreshing(true);
        homeViewModel.getGlobalData().observe(getViewLifecycleOwner(), global -> {
//            null safety not sure if this is legitimate
            if (global != null) {
                Global.Result data = global.getResults().get(0);
                rootLayout.setVisibility(View.VISIBLE);
                total_cases.setText(String.format(Locale.getDefault(),"%,d", data.totalCases));
                cases_today.setText(String.format(Locale.getDefault(),"%,d",data.totalNewCasesToday));
                total_death.setText(String.format(Locale.getDefault(),"%,d",data.totalDeaths));
                death_today.setText(String.format(Locale.getDefault(),"%,d",data.totalNewDeathsToday));
                total_recovery.setText(String.format(Locale.getDefault(),"%,d",data.totalRecovered));
                unresolved.setText(String.format(Locale.getDefault(),"%,d",data.totalUnresolved));
                total_active.setText(String.format(Locale.getDefault(),"%,d",data.totalActiveCases));
                total_serious.setText(String.format(Locale.getDefault(),"%,d",data.totalSeriousCases));
                swipe.setRefreshing(false);
            } else {
                swipe.setRefreshing(false);
                Snackbar.make(root, "Error requesting server, swipe down to retry", Snackbar.LENGTH_INDEFINITE)
                        .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                        .setAction("Retry", view -> {
                            swipe.setRefreshing(true);
                            homeViewModel.getGlobalData();
                        }).show();
            }
        });
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        getPushNotifiaction();
        return root;
    }

    private void getPushNotifiaction() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("notifications");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                constraintLayout.setVisibility(View.VISIBLE);
               Notification notification = dataSnapshot.getValue(Notification.class);
               messsage.setText(notification.getMessage());
               buttonPositive.setText(notification.getPositiveText());
               buttonPositive.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Bundle bundle = new Bundle();
                       bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "SAID YES");
                       mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                       Intent intent = new Intent(Intent.ACTION_VIEW);
                       intent.setData(Uri.parse(notification.getUrl()));
                       startActivity(intent);
                   }
               });
               buttonNegative.setText(notification.getNegativeText());
               buttonNegative.setOnClickListener(view -> {
                   constraintLayout.setVisibility(View.GONE);
                   Bundle bundle = new Bundle();
                   bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "SAID NO");
                   mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
               });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
