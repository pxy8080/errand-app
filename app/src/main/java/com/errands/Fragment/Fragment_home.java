package com.errands.Fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
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
import com.errands.Model.Ad;

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
    private String[] ad_target;
    private String[] ad_pic;
    private View rootView;
    private ImageView add_menu;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SearchView searchView;
    private RecyclerView home_recyclerview;
    private Result result = new Result();
    private List<OrderBase> orderBases = new ArrayList<>();
    private Handler handler;
    private List<Ad> datalist = new ArrayList<>();

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

    @SuppressLint({"HandlerLeak", "InflateParams", "ResourceType"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, null);
        CarouselView cardView = rootView.findViewById(R.id.banner);
        cardView.setPageCount(4);
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




        getAdvertise();

        //广告点击
        cardView.setImageClickListener(new ImageClickListener() {

            @Override
            public void onClick(int position) {
                ad_target=getResources().getStringArray(R.array.ad_target);
                System.out.println("1111111111"+ad_target[position]);
                Uri uri = Uri.parse(ad_target[position]);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        return rootView;
    }

    //广告图片
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            ad_pic=getResources().getStringArray(R.array.ad_pic);
            System.out.println("1111111111"+ad_pic[position]);
            Glide.with(getActivity()).load(ad_pic[position])
                    .into(imageView);
        }
    };

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

    //获取订单
    private void getAdvertise() {
        UtilHttp utilHttp = UtilHttp.obtain();
        UtilHttp.ICallBack callback = new UtilHttp.ICallBack() {
            @Override
            public void onFailure(String throwable) {
                Log.i("TAG", "getAdvertise onFailure: " + throwable);
            }

            @Override
            public void onSuccess(String response) {
                Gson gson1 = new Gson();
                result = gson1.fromJson(response, new TypeToken<Result>() {
                }.getType());
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("advertise",
                        Context.MODE_PRIVATE); //私有数据
                SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                datalist = new Gson().fromJson(JSON.toJSON(result.getData()).toString(),
                        new TypeToken<List<Ad>>() {
                        }.getType());
                ArrayList<String> targets = new ArrayList<String>();
                ArrayList<String> pictures = new ArrayList<String>();
                for (int i = 0; i < datalist.size(); i++) {
                    targets.add(datalist.get(i).getTarget());
                    pictures.add(datalist.get(i).getPicture());
                }
                editor.putString("targets", targets.toString());
                editor.putString("pictures", pictures.toString());
                editor.commit();//提交修改
            }
        };
        try {
            utilHttp.utilGet("appdata/adList", callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initview() {
        home_recyclerview = rootView.findViewById(R.id.home_recyclerview);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        home_recyclerview.setLayoutManager(staggeredGridLayoutManager);

    }


    @Override
    public void onClick(View view) {
    }


}