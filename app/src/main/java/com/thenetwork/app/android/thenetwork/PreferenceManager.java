package com.thenetwork.app.android.thenetwork;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    // Shared preferences file et_date_of_birth_get_started
    private static final String PREF_NAME = "intro_slider-welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // shared pref mode
    int PRIVATE_MODE = 0;

    @SuppressLint("CommitPrefEdits")
    public PreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }
}