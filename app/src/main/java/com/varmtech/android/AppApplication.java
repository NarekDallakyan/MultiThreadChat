package com.varmtech.android;

import android.app.Application;

public class AppApplication extends Application {
    public static AppApplication appApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        appApplication = this;
    }
}
