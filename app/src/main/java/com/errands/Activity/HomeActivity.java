package com.errands.Activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import androidx.viewpager2.widget.ViewPager2;

import com.errands.Activity.home.ServerActivity;
import com.errands.Adapter.ViewPager2Adapter;
import com.errands.Fragment.Fragment_home;
import com.errands.Fragment.Fragment_message;
import com.errands.Fragment.Fragment_mine;
import com.errands.Sophix.R;
import com.yw.game.floatmenu.FloatItem;
import com.yw.game.floatmenu.FloatLogoMenu;
import com.yw.game.floatmenu.FloatMenuView;

import java.util.ArrayList;


public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout navigation_home, navigation_message, navigation_mine;
    private Fragment fragment_home;
    private Fragment fragment_message;
    private Fragment fragment_mine;
    private ViewPager2 viewPager2;
    private FloatLogoMenu mFloatMenu;//悬浮菜单
    ArrayList<FloatItem> itemList = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();
    private int[] menuIcons = new int[]{R.drawable.addtask};
    String[] MENU_ITEMS = {"下单"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initData();
        FloatMenuView();
        initView();
        initPager();
    }

    private void FloatMenuView() {
        for (int i = 0; i < menuIcons.length; i++) {
            itemList.add(new FloatItem(MENU_ITEMS[i], 0x99000000, 0x99000000, BitmapFactory.decodeResource(this.getResources(), menuIcons[i]), String.valueOf(i + 1)));
        }
        if (mFloatMenu == null) {
            mFloatMenu = new FloatLogoMenu.Builder()
                    .withActivity(this)
//                    .withContext(mActivity.getApplication())//这个在7.0（包括7.0）以上以及大部分7.0以下的国产手机上需要用户授权，需要搭配<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
                    .logo(BitmapFactory.decodeResource(getResources(), R.drawable.errand))
                    .drawCicleMenuBg(true)
                    .backMenuColor(0xffe4e3e1)
                    .setBgDrawable(this.getResources().getDrawable(R.color.wait))
                    //这个背景色需要和logo的背景色一致
                    .setFloatItems(itemList)
                    .defaultLocation(FloatLogoMenu.RIGHT)
                    .drawRedPointNum(false)
                    .showWithListener(new FloatMenuView.OnMenuClickListener() {
                        @Override
                        public void onItemClick(int position, String title) {
//                            Toast.makeText(HomeActivity.this, "position " + position + " title:" + title + " is clicked.", Toast.LENGTH_SHORT).show();
//                            Log.i(TAG, "onItemClick: "+position);
                            switch (position){
                                case 0:
                                    Intent server=new Intent(HomeActivity.this, ServerActivity.class);
                                    startActivity(server);
                                    break;
                                default:
                                    break;
                            }
                        }

                        @Override
                        public void dismiss() {

                        }
                    });
        }

    }

    private void initData() {
//        getpersonalmessage();
    }

    private void initPager() {
        fragments.add(fragment_home);
        fragments.add(fragment_message);
        fragments.add(fragment_mine);
        ViewPager2Adapter adapter = new ViewPager2Adapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewPager2.setAdapter(adapter);
        navigation_home.performClick();
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

    private void initView() {
        fragment_home = new Fragment_home();
        fragment_message = new Fragment_message();
        fragment_mine = new Fragment_mine(account);

        navigation_home = findViewById(R.id.navigation_home);
        navigation_home.setOnClickListener(this);
        navigation_message = findViewById(R.id.navigation_message);
        navigation_message.setOnClickListener(this);
        navigation_mine = findViewById(R.id.navigation_mine);
        navigation_mine.setOnClickListener(this);
        navigation_home.setSelected(true);
        navigation_home.performClick();


        viewPager2 = findViewById(R.id.viewpager2);
//        viewPager2.setY(QMUIStatusBarHelper.getStatusbarHeight(this));//QMUIStatusBarHelper.getStatusbarHeight(this)

        navigation_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager2.setCurrentItem(0);
                select(navigation_home);
            }
        });
        navigation_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager2.setCurrentItem(1);
                select(navigation_message);
            }
        });
        navigation_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager2.setCurrentItem(2);
                select(navigation_mine);
            }
        });

    }

    void changeTab(int position) {
        switch (position) {
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
    }


    //导航栏选中颜色变换
    void select(LinearLayout linearLayout) {
        navigation_home.setSelected(false);
        navigation_message.setSelected(false);
        navigation_mine.setSelected(false);
        linearLayout.setSelected(true);
    }


}