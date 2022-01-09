package com.example.imc;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SavedSharedPreference {


    static final String PREF_Med_Num= "Medical_Number";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setMedicalNumber(Context ctx, String medicalnumber)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_Med_Num, medicalnumber);
        editor.commit();
    }

    public static String getMedicalNumber(Context ctx)
    {

        return getSharedPreferences(ctx).getString(PREF_Med_Num, "");

    }
}


