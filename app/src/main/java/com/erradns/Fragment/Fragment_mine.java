package com.erradns.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erradns.Activity.mine.AboutActivity;
import com.erradns.Activity.mine.FeedbackActivity;
import com.erradns.Activity.LoginActivity;
import com.erradns.Activity.mine.MoneyActivity;
import com.erradns.Activity.MyinfoActivity;
import com.erradns.Model.account;
import com.erradns.Sophix.R;
import com.erradns.Util.GlideUtil;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView2;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Fragment_mine extends Fragment implements View.OnClickListener {
    private View rootView;
    private RelativeLayout mine_center;
    private account account;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LinearLayout money, order_managerment, bill_record, about_app, feedback; //余额，订单管理，账单，关于，反馈
    private TextView tip_tx, id_tx; //提示注册登录，id展示
    private Button exit_login;
    private QMUIRadiusImageView2 portrait;
    private String mParam1;
    private String mParam2;

    public Fragment_mine(account account) {
        this.account = account;
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
        rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        initView();

        return rootView;
    }

    private void initView() {
        mine_center = rootView.findViewById(R.id.mine_center);
        mine_center.setOnClickListener(this);
        money = rootView.findViewById(R.id.money);
        money.setOnClickListener(this);
        order_managerment = rootView.findViewById(R.id.order_managerment);
        order_managerment.setOnClickListener(this);
        bill_record = rootView.findViewById(R.id.bill_record);
        bill_record.setOnClickListener(this);
        about_app = rootView.findViewById(R.id.about_app);
        about_app.setOnClickListener(this);
        feedback = rootView.findViewById(R.id.feedback);
        feedback.setOnClickListener(this);
        exit_login = rootView.findViewById(R.id.exit_login);
        exit_login.setOnClickListener(this);

        portrait = rootView.findViewById(R.id.portrait);
        GlideUtil.loadImageViewSize(getActivity(), account.getHeadportrait(), 30, 30, portrait);

        tip_tx = rootView.findViewById(R.id.tip_tx);
        id_tx = rootView.findViewById(R.id.id_tx);
        exit_login = rootView.findViewById(R.id.exit_login);
        exit_login.setOnClickListener(this);

        if (!account.getIslogin()) {
            tip_tx.setVisibility(View.INVISIBLE);
            exit_login.setVisibility(View.INVISIBLE);
        } else {
            tip_tx.setVisibility(View.VISIBLE);
            exit_login.setVisibility(View.VISIBLE);
            tip_tx.setText("昵称:" + account.getNickname());
            id_tx.setText("id:" + account.getId());

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_center:
                if (account.getIslogin()) {
                    Intent to_mine_info = new Intent(getActivity(), MyinfoActivity.class);
                    startActivity(to_mine_info);
                } else {
                    Intent to_login = new Intent(getActivity(), LoginActivity.class);
                    startActivity(to_login);
                }
                break;
            case R.id.money:
                if (account.getIslogin()) {
                    Intent to_mine_info = new Intent(getActivity(), MoneyActivity.class);
                    startActivity(to_mine_info);
                } else {
                    Intent to_login = new Intent(getActivity(), LoginActivity.class);
                    startActivity(to_login);
                }
                break;
            case R.id.order_managerment:
                if (account.getIslogin()) {
                    Intent to_mine_info = new Intent(getActivity(), MoneyActivity.class);
                    startActivity(to_mine_info);
                } else {
                    Intent to_login = new Intent(getActivity(), LoginActivity.class);
                    startActivity(to_login);
                }
                break;
            case R.id.bill_record:
                if (account.getIslogin()) {
                    Intent to_mine_info = new Intent(getActivity(), LoginActivity.class);
                    startActivity(to_mine_info);
                } else {
                    Intent to_login = new Intent(getActivity(), LoginActivity.class);
                    startActivity(to_login);
                }
                break;
            case R.id.about_app:
                if (account.getIslogin()) {
                    Intent to_mine_info = new Intent(getActivity(), AboutActivity.class);
                    startActivity(to_mine_info);
                } else {
                    Intent to_login = new Intent(getActivity(), LoginActivity.class);
                    startActivity(to_login);
                }
                break;
            case R.id.feedback:
                if (account.getIslogin()) {
                    Intent to_mine_info = new Intent(getActivity(), FeedbackActivity.class);
                    startActivity(to_mine_info);
                } else {
                    Intent to_login = new Intent(getActivity(), LoginActivity.class);
                    startActivity(to_login);
                }
                break;
            case R.id.exit_login:
                savepersonalmessage();
                Intent to_mine_info = new Intent(getActivity(), LoginActivity.class);
                startActivity(to_mine_info);
                break;

        }
    }

    //存储个人账户信息，后面每个活动都可以获取
    void savepersonalmessage() {
        SharedPreferences pref = getActivity().getSharedPreferences("userinfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_id", "");
        editor.putInt("user_phone", 0);
        editor.putString("user_email", "");
        editor.putString("user_password","");
        editor.putString("user_nickname", "");
        editor.putString("user_headportrait", "");
        editor.putString("user_school", "");
        editor.putBoolean("user_islogin", false);
        editor.commit();
    }
}