package com.errands.Fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.errands.Activity.home.AcceptActivity;
import com.errands.Adapter.TaskAdapter;
import com.errands.Https.UtilHttp;
import com.errands.Model.OrderBase;
import com.errands.Model.Result;
import com.errands.Sophix.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;


public class Fragment_home extends Fragment implements View.OnClickListener {
    private static final String TAG = "tips";

    int[] sampleImages = {R.drawable.logo, R.drawable.abc_vector_test, R.drawable.ali_feedback_common_back_btn_bg, R.drawable.logo, R.drawable.logo};
    private View rootView;
    private ImageView add_menu;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SearchView searchView;
    private RecyclerView home_recyclerview;
    private Result result = new Result();
    private List<OrderBase> orderBases = new ArrayList<>();
    private Handler handler;

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
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @SuppressLint({"HandlerLeak", "InflateParams"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, null);
        CarouselView cardView = rootView.findViewById(R.id.banner);
        cardView.setPageCount(sampleImages.length);
        cardView.setImageListener(imageListener);
        initview();
        getOrderBase();
        handler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    Gson gson2 = new Gson();
                    if (result.getCode() == 100) {
                        orderBases = gson2.fromJson(new Gson().toJson(result.getData()),
                                new TypeToken<List<OrderBase>>() {
                                }.getType());

                        TaskAdapter adapter = new TaskAdapter(orderBases, getActivity());
                        home_recyclerview.setAdapter(adapter);
                        adapter.setOnItemClikListener(new TaskAdapter.OnItemClikListener() {
                            @Override
                            public void onItem(int position) {
                                Intent intent = new Intent(getActivity(), AcceptActivity.class);
                                intent.putExtra("orderbase", JSON.toJSON(orderBases.get(position)).toString());
                                startActivity(intent);
                            }
                        });
                    }
                }

            }
        };

//广告点击
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

    //获取订单
    private void getOrderBase() {
        UtilHttp utilHttp = UtilHttp.obtain();
        UtilHttp.ICallBack callback = new UtilHttp.ICallBack() {
            @Override
            public void onFailure(String throwable) {
                Log.i("TAG", "getAllOrderBase onFailure: " + throwable);
            }

            @Override
            public void onSuccess(String response) {
                Gson gson1 = new Gson();
                result = gson1.fromJson(response, new TypeToken<Result>() {
                }.getType());
                Message message = new Message();
                message.obj = new Gson().toJson(result.getData());
                message.what = 1;   //标志消息的标志
                handler.sendMessage(message);
            }
        };

        try {
            utilHttp.utilGet("order/showOrderStartTimeDesc", callback);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initview() {
        home_recyclerview = rootView.findViewById(R.id.home_recyclerview);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        home_recyclerview.setLayoutManager(staggeredGridLayoutManager);

    }


    //广告图片
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
//            imageView.setImageResource(sampleImages[position]);
            Glide.with(getActivity()).load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.aiimg.com%2Fuploads%2Fallimg%2F200603%2F263915-200603113151.jpg&refer=http%3A%2F%2Fimg.aiimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1653621968&t=6bbaf01ed311451e2e33d6571bc1a47d")
                    .into(imageView);
        }
    };


    @Override
    public void onClick(View view) {
    }


}