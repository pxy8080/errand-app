package com.erradns.Fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.erradns.Activity.home.ServerActivity;
import com.erradns.Adapter.TaskAdapter;
import com.erradns.Sophix.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;


public class Fragment_home extends Fragment implements View.OnClickListener {
    private static final String TAG = "tips";

    int[] sampleImages = {R.drawable.logo, R.drawable.abc_vector_test, R.drawable.ali_feedback_common_back_btn_bg, R.drawable.logo, R.drawable.logo};
    private View rootView;
    private DrawerLayout drawerLayout;
    private ImageView add_menu;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CarouselView cardView;
    private String mParam1;
    private String mParam2;
    private SearchView searchView;
    private RecyclerView home_recyclerview;
    private RelativeLayout service_1, service_2, service_3, service_4;

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
        cardView = rootView.findViewById(R.id.banner);
        cardView.setPageCount(sampleImages.length);
        cardView.setImageListener(imageListener);
        initview();

        cardView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Uri uri = Uri.parse("https://www.baidu.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
//        initSearchView();

//        UtilHttp utilHttp = UtilHttp.obtain();
//        UtilHttp.ICallBack callback = new UtilHttp.ICallBack() {
//            @Override
//            public void onFailure(String throwable) {
//                test.setText(throwable);
//            }
//
//            @Override
//            public void onSuccess(String response) {
//                test.setText("111" + response);
//
//            }
//        };
//        try {
//            utilHttp.utilGet("user/listuser", callback);
//        } catch (Exception e) {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    test.setText("1111" + e.toString());
//                    System.out.println("111" + e.toString());
//                }
//            });
//        }
        return rootView;
    }

    private void initview() {
        home_recyclerview = rootView.findViewById(R.id.home_recyclerview);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        home_recyclerview.setLayoutManager(staggeredGridLayoutManager);
        TaskAdapter adapter = new TaskAdapter();
        home_recyclerview.setAdapter(adapter);
        adapter.setOnItemClikListener(new TaskAdapter.OnItemClikListener() {
            @Override
            public void onItem(int position) {
                Log.i("TAG", "onItem: 点击了" + position);
                Toast.makeText(getActivity(), "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
        add_menu = rootView.findViewById(R.id.add_menu);
        add_menu.setOnClickListener(this);
        drawerLayout = rootView.findViewById(R.id.drawer_layout);

        service_1=rootView.findViewById(R.id.service_1);
        service_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent server1=new Intent(getActivity(), ServerActivity.class);
                startActivity(server1);
            }
        });
        service_2=rootView.findViewById(R.id.service_2);
        service_3=rootView.findViewById(R.id.service_3);
        service_4=rootView.findViewById(R.id.service_4);



    }




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
        switch (view.getId()) {
            case R.id.add_menu:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
//            case R.id.service_1:
//                Log.i(TAG, "onClick: 点击了服务1");
//                break;
            default:
                break;
        }

    }


}