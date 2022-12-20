package com.phonics.covid19tracker.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.phonics.covid19tracker.R;

public class SettingsFragment extends Fragment {

    private AppCompatTextView textTheme;
    private SharedPreferences sharedPreferences;
    private SwitchMaterial switchTheme;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sharedPreferences = getContext().getSharedPreferences("ThemePrefs",0);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        textTheme = root.findViewById(R.id.text_theme);
        switchTheme = root.findViewById(R.id.switch_theme);
        if (sharedPreferences.contains("theme")) {
            if (sharedPreferences.getInt("theme", -1) == AppCompatDelegate.MODE_NIGHT_YES) {
                textTheme.setText("Night");
                switchTheme.setChecked(true);
            } else {
                textTheme.setText("Light");
                switchTheme.setChecked(false);
            }
        }
        ConstraintLayout buttonTheme = root.findViewById(R.id.theme);
        buttonTheme.setOnClickListener(view -> {
            if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES) {
                setTheme(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                setTheme(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });
        switchTheme.setOnClickListener(view -> buttonTheme.callOnClick());
        return root;
    }

    private void setTheme(int themeMode) {
        if (themeMode==AppCompatDelegate.MODE_NIGHT_NO){
            switchTheme.setChecked(false);
        } else {
            switchTheme.setChecked(true);
        }
        AppCompatDelegate.setDefaultNightMode(themeMode);
        saveTheme(themeMode);
    }

    private void saveTheme(int prefmode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("theme", prefmode);
        editor.apply();
    }
}
