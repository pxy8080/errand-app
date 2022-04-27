package com.erradns.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.erradns.Sophix.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;


import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Fragment_home extends Fragment implements View.OnClickListener {
    int[] sampleImages = {R.drawable.logo, R.drawable.abc_vector_test, R.drawable.ali_feedback_common_back_btn_bg, R.drawable.logo, R.drawable.logo};
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
//            imageView.setImageResource(sampleImages[position]);
//            Glide.with(getActivity()).load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Finews.gtimg.com%2Fnewsapp_bt%2F0%2F10048724177%2F1000.jpg&refer=http%3A%2F%2Finews.gtimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1653621577&t=619e48af3ceb11873fab986a9811b4e7")
//                    .into(imageView);
            Glide.with(getActivity()).load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.aiimg.com%2Fuploads%2Fallimg%2F200603%2F263915-200603113151.jpg&refer=http%3A%2F%2Fimg.aiimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1653621968&t=6bbaf01ed311451e2e33d6571bc1a47d")
                    .into(imageView);
        }
    };
    private ImageView back;
    private TextView title, test;
    private View rootView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CarouselView cardView;
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
        cardView = rootView.findViewById(R.id.banner);
        cardView.setPageCount(sampleImages.length);

        cardView.setImageListener(imageListener);
        cardView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Uri uri = Uri.parse("https://www.baidu.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
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
        return "";
    }

}