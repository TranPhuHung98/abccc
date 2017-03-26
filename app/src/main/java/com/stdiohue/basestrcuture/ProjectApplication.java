package com.stdiohue.basestrcuture;

import android.app.Application;

/**
 * Created by hung.nguyendk on 2/25/17.
 */

public class ProjectApplication extends Application {
    private static ProjectApplication sInstance;

    public static ProjectApplication getInstance() {
        return sInstance;
    }

    private ProjectConfig mProjectConfig;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        // config
        mProjectConfig = new ProjectConfig(this);
    }

    public ProjectConfig getConfig() {
        return mProjectConfig;
    }
}
