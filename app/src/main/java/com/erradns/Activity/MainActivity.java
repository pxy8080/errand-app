package com.erradns.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.erradns.Sophix.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Button login;
    private ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
//        SophixManager.getInstance().queryAndLoadNewPatch();

    }

    private void initView() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            default:
                break;
        }

//                //上报自定义信息
//                AliHaAdapter.getInstance().addCustomInfo("key", "value"); //配置项：自定义环境信息
//
//                //按异常类型上报自定义信息
//                AliHaAdapter.getInstance().setErrorCallback(new ErrorCallback() {
//                    @Override
//                    public Map<String, String> onError(ErrorInfo callbackInfo) {
//                        Map<String, String> infos = new HashMap<>();
//                        infos.put("key", "value"); //配置项：异常信息
//                        return infos;
//                    }
//                });
//                //上报自定义错误
//                AliHaAdapter.getInstance().reportCustomError(new RuntimeException("custom error")); //配置项：自定义错误
    }


}