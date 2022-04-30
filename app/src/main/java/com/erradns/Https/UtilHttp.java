package com.erradns.Https;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UtilHttp {
    private static String baseUrl = "http://10.0.2.2:8080/";//本地地址
    private static OkHttpClient client;
    private static UtilHttp mInstance;
    private Handler mHandler;

    public UtilHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
//                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));//设置缓存的路径


        client = builder.build();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static UtilHttp obtain() {
        if (mInstance == null) {
            synchronized (UtilHttp.class) {
                if (mInstance == null) {
                    mInstance = new UtilHttp();
                }
            }
        }
        return mInstance;
    }

    public void utilGet(String url, final ICallBack callBack) throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(baseUrl + url)
                .build();
        Call call = client.newCall(request);
        //同步调用,返回Response,会抛出IO异常
//        Response response = call.execute();
        //异步调用,并设置回调函数
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "onFailure: " + e.toString(), null);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.i("TAG", "onResponse: " + Thread.currentThread());
                boolean isSuccessful = response.isSuccessful();
                sendSuccessCallback(callBack, isSuccessful, response);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        contentTv.setText(res);
//                    }
//                });
            }
        });
    }

    /**
     * @param formBody: 表单
     * @param url:      请求地址
     * @return: 结果
     */
    public void untilPostForm(FormBody formBody, String url, final ICallBack callBack) {
        final Request request = new Request.Builder()
                .url(baseUrl+url)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Toast.makeText(OkHttpActivity.this, "Post Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean isSuccessful = response.isSuccessful();
                sendSuccessCallback(callBack, isSuccessful, response);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        contentTv.setText(res);
//                    }
//                });
            }
        });
    }
    //提交json

    /**
     * @param url:      请求地址
     * @param postJson: 请求的json形势
     * @return: 结果
     */
    void untilPostJson(String url, String postJson, final ICallBack callBack) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), postJson);
        Request request = new Request.Builder()
                .url(baseUrl + url)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean isSuccessful = response.isSuccessful();
                sendSuccessCallback(callBack, isSuccessful, response);
//                Log.d(TAG, "onResponse: "+response.body().string());
            }
        });
    }

    void untilPostString(String url, String postString, final ICallBack callBack) {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, postString);
        Request request = new Request.Builder()
                .url(baseUrl + url)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean isSuccessful = response.isSuccessful();
                sendSuccessCallback(callBack, isSuccessful, response);
//                Log.d(TAG, "onResponse: "+response.body().string());
            }
        });
    }

    /**
     * 请求成功
     *
     * @param callback
     * @param isSuccess
     * @param response
     */
    private void sendSuccessCallback(final ICallBack callback, final boolean isSuccess, final Response response) {

        try {
            final String responseString = response.body().string();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (isSuccess == true) {
                        callback.onSuccess(responseString);
                    } else
                        callback.onFailure(response.message().toString());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求失败
     *
     * @param callback
     * @param throwable
     */
    private void sendFailCallback(final ICallBack callback, final String throwable) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(throwable);
            }
        });
    }

    public interface ICallBack {
        void onFailure(String throwable);

        void onSuccess(String response);
    }
}
