package com.erradns.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.amap.api.maps.MapView;
import com.erradns.Https.UtilHttp;
import com.erradns.Sophix.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Fragment_home extends Fragment implements View.OnClickListener {
    private ImageView back;
    private TextView title, test;
    private View rootView;
    private MapView mapView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_home() {
    }

    public static Fragment_home newInstance(String param1, String param2) {
        Fragment_home fragment = new Fragment_home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, null);
        back = rootView.findViewById(R.id.back_img);
        back.setVisibility(View.INVISIBLE);
        title = rootView.findViewById(R.id.title);
        title.setText("首页");

        test = rootView.findViewById(R.id.test);
        mapView=rootView.findViewById(R.id.maptest);
        mapView.onCreate(savedInstanceState);
        return rootView;
    }


    @Override
    public void onClick(View view) {

    }

    public String utilGet(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url("https://baidu.com")
                .build();
        Call call = client.newCall(request);
        //同步调用,返回Response,会抛出IO异常
//        Response response = call.execute();

        //异步调用,并设置回调函数
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "onFailure: 出错" + e.toString(), null);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String res = response.body().string();
                Log.e("TAG", "onResponse: " + res, null);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        test.setText(res);
                    }
                });
            }
        });
        System.out.println("this2");
        return "";

    }
}