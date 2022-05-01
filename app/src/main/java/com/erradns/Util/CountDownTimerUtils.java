package com.erradns.Util;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import com.erradns.Sophix.R;

public class CountDownTimerUtils extends CountDownTimer {
    private TextView button;
    private Context context;
    public static boolean is_no = false;

    public CountDownTimerUtils(Context context, TextView button, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.button = button;
        this.context = context;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        is_no = true;
        button.setClickable(false); // 设置不可点击
        button.setText(millisUntilFinished / 1000 + "s"); // 设置倒计时时间
        button.setBackgroundResource(R.drawable.register_code); // 设置按钮为灰色，这时是不能点击的
        button.setTextColor(context.getResources().getColor(R.color.wait));
    }

    @Override
    public void onFinish() {
        is_no = false;
        button.setText("验证");
        button.setClickable(true);// 重新获得点击
        button.setBackgroundResource(R.color.title_bar_bg); // 还原背景色
        button.setTextColor(context.getResources().getColor(R.color.black));
    }




}
