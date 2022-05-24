package com.errands.Sophix;
import android.app.Application;
import android.util.Log;

import com.alibaba.ha.adapter.AliHaAdapter;
import com.alibaba.ha.adapter.AliHaConfig;
import com.alibaba.ha.adapter.Plugin;
/**
 * Date:16/5/17
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initHa();
        Log.i("MainApplication", "原有的Application，可以通过补丁修改");
    }
    private void initHa() {
        AliHaConfig config = new AliHaConfig();
        config.appKey = "333695951";
        config.appVersion = "1.1";
        config.appSecret = "4a87981768424e2f952695a75e461412";
        config.channel = "mqc_test";
        config.userNick = null;
        config.application = this;
        config.context = getApplicationContext();
        config.isAliyunos = false;
        //启动CrashReporter
        AliHaAdapter.getInstance().addPlugin(Plugin.crashreporter);
        AliHaAdapter.getInstance().start(config);
    }




}
