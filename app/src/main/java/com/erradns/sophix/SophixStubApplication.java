package com.erradns.sophix;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Keep;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Sophix入口类，专门用于初始化Sophix，不应包含任何业务逻辑。
 * 此类必须继承自SophixApplication，onCreate方法不需要实现。
 * 此类不应与项目中的其他类有任何互相调用的逻辑，必须完全做到隔离。
 * AndroidManifest中设置application为此类，而SophixEntry中设为原先Application类。
 * 注意原先Application里不需要再重复初始化Sophix，并且需要避免混淆原先Application类。
 * 如有其它自定义改造，请咨询官方后妥善处理。
 */
public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";
    private final String HOT_ID = "333695951-1";
    private final String HOT_SECRET = "4a87981768424e2f952695a75e461412";
    private final String HOT_RSASECRET = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCz9rv4Kfo3IMDWwwngcaN5hpEJZWl+fWH/1TFybvR+05YjpAS5xrzakMtHYGzbkfrPLUV6vhYaj5vL298nU68Bg+oidiUugSxTQ/RSmtfg4xTclMvgPqV3hr5Dfjl/8o4X/6tyhOQiEaUbl+ReMtbK0HYieRcIXRkLeVjE3TmfEjzrb//TgNo9+7CM5gSysRfZ4tcG3Mj6JJitIUaJHdqLTuCrn9ZfBjZvVR+RIFqTeO6psId8BUKHFH6et9dEzVXRP2Kkg0eLxIGMo40Xc23KVKUISIxRgyyL5u8vwLzteo9MRJzWD11KCsLkobkT6NGc9AkQfWDi52WIj/ZHCPDLAgMBAAECggEAEDWmIqktR2/kzrNMnXG+5sBPKgh/YwxKGnAKfyMtVIscV+N7JXyYIBuwo6I0y+YvMCAExRSACy5qOwd35yPrfHh0S1xW5wRtiKZzSEl4LmJly6R/ZJhf8Q8q7gtdhH/FerXlrG/52HO8amrY//8WR7hVG6/Kek9gZR8t9rVybjhgTqsWhh5ZyFM7YRxRfTIQBBQTPSiIqDAr62JCoTVTUenDHBPNB2sbA7fAu8FKklKHHyoKfDqn+DzxkAqoMp4+KnsCLn1Xyw7hplvREwU2FZ6CIKwSUIKLxTFFsADHiwJLgc2/VtYhAtomfp2/fmnv7/vGUfu1jM48mnedSG6eQQKBgQD1bQjh8UBYAgq+kspQ+o4+NzTil8lwJlaZdT8KBHv43NWUgpk/X6sPr7LV0eXe4HETcRwoace+1OBs9H7VZdzSMSbSeWYF4BwceXXpAXghkFySp5msgOVO7izyJDsY6X1/EBzDc7tKh7LarUviAvcCwow9DIPkcOexo18Haa2TYQKBgQC7t6yrmco0qcqIwHxMp7gc5I0clRNa4LE9S/+joFinYoiEwx2G2uvZ0mD//+6eaQp7hYziSlog6K1VfibIT1RmRwLOo8i4b/9yVcVdtP+3e3jXefU3px6n5RCORZI8rekXhDjMvR7H6D1k87bkdbZsG6+7HNKxM5oTA37rTnbfqwKBgQDIZOdD1cE8Gy66vREFXhCXLGy6OEX6lNNRKjL28wJnCao7QQfwfyUmAch8Xo7blVe/EDR1qym14Av/6wNFK49K8WdJpGZxlbRea66jOtfkUAPGm6MW/4g9Eq0QahT3Gm+mOHnD/dOH35ViiO3U9abLUh9AGd/3v1jv6v3FB7z7AQKBgE1eF3Gmcfugm1QfmVtyqORnom1MV+zVvNcZsqQ62y8p4kBttoa1Rg3Bsq7wSeXKP0LHuVgu8tSrcX1SACQA5jU9QT/vqzOxHWR/S1gi2F5EKnsMze+BFpZ2R4uLywjtWKuTmGFx9PTJmDw6Yn/osCYAhz4kVAYq0W7SuwW6ki3/AoGARDUa56KGdfcYWLXJ9Tdf12IRkkUtHiViGIDXuibLFzsQMgXwU0HLoS4nzqfagt0s4Wv9uiLS6l2dEbZF/b11WMSwAYjfB28yaRGCEgIK49S+WyzJnWrQ13Fdze3h8aUvQc1p/Dx5KGAo/SxjIdNUClrigRV1flghvsOKCEMVyXI=";

    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(MainApplication.class)
    static class RealApplicationStub {
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
//         MultiDex.install(this);
        initSophix();
    }

    private void initSophix() {
        String appVersion = "1.1.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData(HOT_ID, HOT_SECRET, HOT_RSASECRET)
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");

                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(),"补丁成功",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).initialize();
    }
}