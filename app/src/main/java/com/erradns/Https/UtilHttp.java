package com.erradns.Https;

import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UtilHttp {
    private static String baseUrl="http://localhost:8080/";//本地地址
    private static OkHttpClient client;
    private String res;
    public UtilHttp() {
        client=new OkHttpClient();
    }
    public String utilGet(String url) throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(baseUrl+url)
                .build();
        Call call = client.newCall(request);
        //同步调用,返回Response,会抛出IO异常
//        Response response = call.execute();
        //异步调用,并设置回调函数
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Toast.makeText(OkHttpActivity.this, "get failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                  res= response.body().string();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        contentTv.setText(res);
//                    }
//                });
            }
        });
        return ""+res;
    }
    /**
     * @param formBody: 表单
     * @param url: 请求地址
     * @return: 结果
     */
    String untilPostForm(FormBody formBody,String url)
    {
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
                 res = response.body().string();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        contentTv.setText(res);
//                    }
//                });
            }
        });
        return ""+res;
    }
    //提交json
    /**
     * @param url: 请求地址
     * @param postJson: 请求的json形势
     * @return: 结果
     */
    String untilPostJson(String url,String postJson)
    {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), postJson);
        Request request = new Request.Builder()
                .url(baseUrl+url)
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
                res=response.body().string();
//                Log.d(TAG, "onResponse: "+response.body().string());
            }
        });
        return ""+res;
    }

    String untilPostString(String url,String postString)
    {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType,postString);
        Request request = new Request.Builder()
                .url(baseUrl+url)
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
                res=response.body().string();
//                Log.d(TAG, "onResponse: "+response.body().string());
            }
        });
        return ""+res;
    }

}
