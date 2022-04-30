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
import android.widget.Toast;

import com.bumptech.glide.Glide;
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



    }




//    private void initSearchView() {
//        searchView=rootView.findViewById(R.id.search);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
//        {
//            //输入完成后，提交时触发的方法，一般情况是点击输入法中的搜索按钮才会触发，表示现在正式提交了
//            public boolean onQueryTextSubmit(String query)
//            {
//                if(TextUtils.isEmpty(query))
//                {
//                    Toast.makeText(getActivity(), "请输入查找内容！", Toast.LENGTH_SHORT).show();
//                    listView.setAdapter(adapter);
//                }
//                else
//                {
//                    findList.clear();
//                    for(int i = 0; i < list.size(); i++)
//                    {
//                        iconInformation information = list.get(i);
//                        if(information.getName().equals(query))
//                        {
//                            findList.add(information);
//                            break;
//                        }
//                    }
//                    if(findList.size() == 0)
//                    {
//                        Toast.makeText(getActivity(), "查找的商品不在列表中", Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                    {
//                        Toast.makeText(getActivity(), "查找成功", Toast.LENGTH_SHORT).show();
//                        findAdapter = new listViewAdapter(getActivity(), findList);
//                        listView.setAdapter(findAdapter);
//                    }
//                }
//                return true;
//            }
//            //在输入时触发的方法，当字符真正显示到searchView中才触发，像是拼音，在输入法组词的时候不会触发
//            public boolean onQueryTextChange(String newText)
//            {
//                if(TextUtils.isEmpty(newText))
//                {
//                    listView.setAdapter(adapter);
//                }
//                else
//                {
//                    findList.clear();
//                    for(int i = 0; i < list.size(); i++)
//                    {
//                        iconInformation information = list.get(i);
//                        if(information.getName().contains(newText))
//                        {
//                            findList.add(information);
//                        }
//                    }
//                    findAdapter = new listViewAdapter(getActivity(), findList);
//                    findAdapter.notifyDataSetChanged();
//                    listView.setAdapter(findAdapter);
//                }
//                return true;
//            }
//        });
//    }


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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_menu:
                drawerLayout.openDrawer(GravityCompat.START);
                break;

            default:
                break;
        }

    }


}