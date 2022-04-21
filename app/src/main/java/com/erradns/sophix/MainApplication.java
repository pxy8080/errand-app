package com.erradns.sophix;
import android.app.Application;
import android.util.Log;

/**
 * Date:16/5/17
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MainApplication", "原有的Application，可以通过补丁修改");
    }

}
