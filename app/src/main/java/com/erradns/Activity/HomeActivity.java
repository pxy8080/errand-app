package com.erradns.Activity;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.erradns.Adapter.ViewPager2Adapter;
import com.erradns.Fragment.Fragment_home;
import com.erradns.Fragment.Fragment_message;
import com.erradns.Fragment.Fragment_mine;
import com.erradns.Sophix.R;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.util.ArrayList;


public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout navigation_home, navigation_message, navigation_mine;
    private Fragment fragment_home=new Fragment_home();
    private Fragment fragment_message=new Fragment_message();
    private Fragment fragment_mine=new Fragment_mine();
    private Fragment f;
    private FrameLayout frameLayout;
    private ViewPager2 viewPager2;
    ArrayList<Fragment> fragments=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
    }

    private void initView() {
//        frameLayout=findViewById(R.id.frameLayout);
//        frameLayout.setY(QMUIStatusBarHelper.getStatusbarHeight(this));

        navigation_home = findViewById(R.id.navigation_home);
        navigation_home.setOnClickListener(this);
        navigation_message = findViewById(R.id.navigation_message);
        navigation_message.setOnClickListener(this);
        navigation_mine = findViewById(R.id.navigation_mine);
        navigation_mine.setOnClickListener(this);
        navigation_home.setSelected(true);
        navigation_home.performClick();

        viewPager2=findViewById(R.id.viewpager2);
        fragments.add(fragment_home);
        fragments.add(fragment_message);
        fragments.add(fragment_mine);
        ViewPager2Adapter adapter=new ViewPager2Adapter(getSupportFragmentManager(),getLifecycle(),fragments);
        viewPager2.setAdapter(adapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }

    private void changeTab(int position) {
        switch (position){
            case 0:
                select(navigation_home);
                break;
            case 1:
                select(navigation_message);
                break;
            case 2:
                select(navigation_mine);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.navigation_home:
                changeTab(0);
//                f = fragment_home;
//                fragmentTransaction.replace(R.id.frameLayout, f);
//                fragmentTransaction.commit();
                break;
            case R.id.navigation_message:
                changeTab(1);
//                f = fragment_message;
//                fragmentTransaction.replace(R.id.frameLayout, f);
//                fragmentTransaction.commit();

                break;
            case R.id.navigation_mine:
                changeTab(2);
//                f = fragment_mine;
//                fragmentTransaction.replace(R.id.frameLayout, f);
//                fragmentTransaction.commit();

                break;
            default:
                break;
        }
    }

    //导航栏选中颜色变换
    void select(LinearLayout linearLayout) {
        navigation_home.setSelected(false);
        navigation_message.setSelected(false);
        navigation_mine.setSelected(false);
        linearLayout.setSelected(true);
    }
}