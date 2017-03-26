package com.stdiohue.basestrcuture;

import android.content.Context;
import android.content.SharedPreferences;

/**
 */
public class ProjectConfig {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private static final String NAME = "schoolber";


    public ProjectConfig(Context context) {
        mSharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.apply();
    }

}
