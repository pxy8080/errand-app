package com.errands.Activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.alibaba.fastjson.JSON;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.errands.Activity.mine.AddressActivity;
import com.errands.Https.UtilHttp;
import com.errands.Model.Result;
import com.errands.Model.User;
import com.errands.Sophix.R;
import com.errands.Util.CountDownTimerUtils;
import com.errands.Util.GlideUtil;
import com.errands.Util.RandomNumber;
import com.errands.Util.SendEmailUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.tbruyelle.rxpermissions2.RxPermissions;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyinfoActivity extends BaseActivity implements View.OnClickListener, OnItemClickListener {
    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;
    private TextView nickname, phone, email;
    private Uri takephoto_uri;
    private AlertView phone_alertView, pwd_alertView, nickname_alertView;
    private long verificationCode = 0;
    private Result result = new Result();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        initData();
        initView();

    }

    private void initData() {
        String account_email = account.getEmail();
    }

    private void initView() {
        Toolbar title_bar = findViewById(R.id.title_bar);
        title_bar.setY(QMUIStatusBarHelper.getStatusbarHeight(this));
        TextView title = findViewById(R.id.title);
        title.setText("????????????");
        ImageView back = findViewById(R.id.back_img);
        back.setOnClickListener(this);

        RelativeLayout head_icon = findViewById(R.id.head_icon);
        head_icon.setOnClickListener(this);
        RelativeLayout nickname_tx = findViewById(R.id.nickname_tx);
        nickname_tx.setOnClickListener(this);
        RelativeLayout address_tx = findViewById(R.id.address_tx);
        address_tx.setOnClickListener(this);
        RelativeLayout phone_tx = findViewById(R.id.phone_tx);
        phone_tx.setOnClickListener(this);
        RelativeLayout update_pwd_tx = findViewById(R.id.update_pwd_tx);
        update_pwd_tx.setOnClickListener(this);
        RelativeLayout email_tx = findViewById(R.id.email_tx);
        email_tx.setOnClickListener(this);

        nickname = findViewById(R.id.nickname);

        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        nickname.setText(account.getNickname());
        phone.setText(account.getPhone());
        email.setText(account.getEmail());

        ImageView icon = findViewById(R.id.icon);
        GlideUtil.loadImageViewLodingSize(this, account.getHeadportrait(), 50, 50, icon,
                R.drawable.loading, R.drawable.init_icon);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        updateicon(uriToFileApiQ(takephoto_uri, this));
                    }
                } else
                    showToast("???????????????");
                break;
            case CHOOSE_PHOTO:
                Uri selectedImage = data.getData();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    updateicon(uriToFileApiQ(selectedImage, this));
                } else
                    showToast("?????????????????????");
                break;
            default:
                break;
        }
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                onBackPressed();
                break;
            case R.id.head_icon:
                new AlertView("????????????", null, "??????", null,
                        new String[]{"??????", "??????????????????"},
                        this, AlertView.Style.ActionSheet, new OnItemClickListener() {
                    public void onItemClick(Object o, int position) {
                        switch (position) {
                            case 0:
                                RxPermissions permissions = new RxPermissions(MyinfoActivity.this);
                                //????????????
                                permissions.request(Manifest.permission.CAMERA)
                                        .subscribe(new Consumer<Boolean>() {
                                            //RxJava??????????????????
                                            @Override
                                            public void accept(Boolean aBoolean) throws FileNotFoundException {
                                                if (aBoolean) {
                                                    //??????
                                                    OpenCamera();

                                                } else {
                                                    //??????
                                                    Toast.makeText(MyinfoActivity.this, "?????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                break;
                            case 1:
                                showToast("????????????");
                                Intent intent = new Intent(Intent.ACTION_PICK, null);
                                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(intent, CHOOSE_PHOTO);
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.nickname_tx:
                @SuppressLint("InflateParams") View view2 = getLayoutInflater().inflate(R.layout.nickname_dialog, null);
                final TextView old_nickname = view2.findViewById(R.id.old_nickname);
                old_nickname.setText("????????????" + account.getNickname());
                final EditText new_nickname = view2.findViewById(R.id.new_nickname);
                nickname_alertView = new AlertView("????????????", null,
                        "??????", null, new String[]{"??????"},
                        this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (o == nickname_alertView && position != AlertView.CANCELPOSITION) {
                            String name = new_nickname.getText().toString().trim();
                            if (name.isEmpty()) {
                                showToast("????????????");

                            } else {
                                User user = new User(account.getId(), account.getPhone(), account.getEmail(),
                                        account.getPassword(), name, account.getHeadportrait(), account.getSchool(), account.getMoney());
                                updateuser(user);
                                savepersonalmessage(user);
                                nickname.setText(name);
                            }
                        }

                    }
                });
                nickname_alertView.addExtView(view2);
                nickname_alertView.show();
                break;
            case R.id.address_tx:
                Intent to_address = new Intent(this, AddressActivity.class);
                startActivity(to_address);

                break;
            case R.id.phone_tx:
                View view3 = getLayoutInflater().inflate(R.layout.phone_dialog, null);
                final TextView old_phone = view3.findViewById(R.id.old_phone);
                old_phone.setText("???????????????" + account.getPhone());
                final EditText new_phone = view3.findViewById(R.id.new_phone);
                final EditText phone_dialog_pwd = view3.findViewById(R.id.phone_dialog_pwd);
                phone_alertView = new AlertView("????????????", null,
                        "??????", null, new String[]{"??????"},
                        this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (o == phone_alertView && position != AlertView.CANCELPOSITION) {
                            String newphone = new_phone.getText().toString().trim();
                            String password = phone_dialog_pwd.getText().toString().trim();
                            if (newphone.isEmpty()) {
                                showToast("????????????");
                            } else {
                                if (password.equals(account.getPassword())) {
                                    User user = new User(account.getId(), newphone, account.getEmail(),
                                            account.getPassword(), account.getNickname(), account.getHeadportrait(), account.getSchool(), account.getMoney());
                                    updateuser(user);
                                    savepersonalmessage(user);
                                    phone.setText(newphone);
                                } else showToast("??????????????????");
                            }
                        }
                    }
                });
                phone_alertView.addExtView(view3);
                phone_alertView.show();
                break;
            case R.id.update_pwd_tx:
                @SuppressLint("InflateParams") View view4 = getLayoutInflater().inflate(R.layout.pwd_dialog, null);
                final EditText dialog_old_pwd = view4.findViewById(R.id.dialog_old_pwd);
                final EditText dialog_new_pwd = view4.findViewById(R.id.dialog_new_pwd);
                pwd_alertView = new AlertView("????????????", null,
                        "??????", null, new String[]{"??????"},
                        this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (o == pwd_alertView && position != AlertView.CANCELPOSITION) {
                            String oldpwd = dialog_old_pwd.getText().toString().trim();
                            String newpwd = dialog_new_pwd.getText().toString().trim();
                            if (oldpwd.isEmpty() || newpwd.isEmpty()) {
                                showToast("????????????");
                            } else {
                                if (oldpwd.equals(account.getPassword())) {
                                    User user = new User(account.getId(), account.getPhone(), account.getEmail(),
                                            newpwd, account.getNickname(), account.getHeadportrait(), account.getSchool(), account.getMoney());
                                    updateuser(user);
                                    savepersonalmessage(user);
                                } else showToast("?????????????????????");
                            }
                        }

                    }
                });
                pwd_alertView.addExtView(view4);
                pwd_alertView.show();
                break;
            case R.id.email_tx:
                /**
                 * view5 email?????????????????????
                 */
                @SuppressLint("InflateParams") View view5 = getLayoutInflater().inflate(R.layout.email_dialog, null);
                final EditText email_update_security_code = view5.findViewById(R.id.email_update_security_code);
                final Button btn_send_security_code = view5.findViewById(R.id.btn_send_security_code);
                final EditText new_email = view5.findViewById(R.id.new_email);

                //???????????????????????????
                btn_send_security_code.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //??????????????????
                        SendPhoneYZM_BT(MyinfoActivity.this, btn_send_security_code);
                        sendVerificationCode(new_email.getText().toString().trim());
                    }
                });
                //???????????????
                pwd_alertView = new AlertView("????????????", null,
                        "??????", null, new String[]{"??????"},
                        this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (o == nickname_alertView && position != AlertView.CANCELPOSITION) {
                            String newemail = new_email.getText().toString().trim();
                            if (email_update_security_code.getText().toString().trim().equals(String.valueOf(verificationCode))) {
                                User user = new User(account.getId(), account.getPhone(), newemail,
                                        account.getPassword(), account.getNickname(), account.getHeadportrait(), account.getSchool(), account.getMoney());
                                updateuser(user);
                                savepersonalmessage(user);
                                email.setText(newemail);
                            } else {
                                showToast("?????????????????????????????????");
                            }
                        }

                    }
                });
                pwd_alertView.addExtView(view5);
                pwd_alertView.show();
                break;


            default:
                break;
        }
    }

    /**
     * ????????????
     */
    private void OpenCamera() {
        File outputimage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputimage.exists()) {
                outputimage.delete();
            }
            outputimage.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            takephoto_uri = FileProvider.getUriForFile(MyinfoActivity.this, "com.erradns.Sophix.provider", outputimage);

        } else {
            takephoto_uri = Uri.fromFile(outputimage);
        }
        try {
            Intent open_camera = new Intent("android.media.action.IMAGE_CAPTURE");
            open_camera.putExtra(MediaStore.EXTRA_OUTPUT, takephoto_uri);
            startActivityForResult(open_camera, TAKE_PHOTO);
        } catch (Exception e) {
            System.out.println("?????????" + e.toString());
        }
    }


    //
    @Override
    public void onItemClick(Object o, int position) {
        showToast("?????????" + position + o.getClass().toString());
    }

    /**
     * @param email ??????????????????
     *              verificationCode  ?????????????????????
     */
    public void sendVerificationCode(final String email) {
        try {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        RandomNumber rn = new RandomNumber();
                        verificationCode = rn.getRandomNumber(6);
                        SendEmailUtil se = new SendEmailUtil(email);
                        se.sendHtmlEmail(verificationCode);//??????html??????
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context ???????????????
     * @param view    button??????????????????????????????
     */
    public static void SendPhoneYZM_BT(Context context, Button view) {
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(
                context, view, 60000, 1000);
        if (!CountDownTimerUtils.is_no) {
            mCountDownTimerUtils.start();
        } else {
            Log.i(TAG, "SendPhoneYZM_BT: ");
        }
    }


    void updateuser(User user) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("????????????");
        dialog.show();
//        FormBody.Builder frombody = new FormBody.Builder();
//        frombody.add("id", user.getId());
//        frombody.add("phone", String.valueOf(user.getphone()));
//        frombody.add("email", user.getEmail());
//        frombody.add("password", user.getPassword());
//        frombody.add("nickname", user.getNickname());
//        frombody.add("headportrait", user.getHeadportrait());
//        frombody.add("school", user.getSchool());

        UtilHttp utilHttp = UtilHttp.obtain();
        UtilHttp.ICallBack callback = new UtilHttp.ICallBack() {
            @Override
            public void onFailure(String throwable) {
                Log.i("TAG", "onFailure111: " + throwable);
                showToast(throwable);
            }

            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                result = gson.fromJson(response, new TypeToken<Result>() {
                }.getType());
                Log.i("TAG", "onSuccess: " + result.getCode() + "??????????????????", null);
                dialog.dismiss();
                showToast(result.getMessage());
            }
        };
        try {
            utilHttp.untilPostString("user/updateuser", JSON.toJSON(user).toString(), callback);
        } catch (Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast("????????????????????????" + e.toString());
                }
            });
        }
    }

    //????????????
    void updateicon(File file) {

        OkHttpClient httpClient = new OkHttpClient().newBuilder().connectTimeout(3600000, TimeUnit.MILLISECONDS)
                .readTimeout(3600000, TimeUnit.MILLISECONDS)
                .build();

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("????????????");
        dialog.show();

        MediaType mediaType = MediaType.parse("application/octet-stream");//???????????????????????????????????????
        RequestBody requestBody = RequestBody.create(mediaType, file);//?????????????????????????????????

        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id", account.getId())
                .addFormDataPart("phone", account.getPhone())
                .addFormDataPart("email", account.getEmail())
                .addFormDataPart("headportrait", account.getHeadportrait())
                .addFormDataPart("nickname", account.getNickname())
                .addFormDataPart("school", account.getSchool())
                .addFormDataPart("file", file.getName(), requestBody)//?????????
                .build();

        Request request = new Request.Builder()
                .url("http://81.71.163.138:8080/errand-1.0/user/uploadimg")

                .post(multipartBody)
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("????????????" + e);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //????????????????????????????????????????????????
                final String resdata = response.body().string();
                Gson gson3 = new Gson();
                result = gson3.fromJson(resdata, new TypeToken<Result>() {
                }.getType());
                if (result.getCode() == 100) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("??????????????????,???????????????");
                            dialog.dismiss();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("????????????" + result.getMessage().toString());
                        }
                    });
                }
            }
        });
    }


    //??????uri???file
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static File uriToFileApiQ(Uri uri, Context context) {
        File file = null;
        if (uri == null) return file;
        //android10????????????
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //??????????????????????????????
            ContentResolver contentResolver = context.getContentResolver();
            String displayName = System.currentTimeMillis() + Math.round((Math.random() + 1) * 1000)
                    + "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri));

            try {
                InputStream is = contentResolver.openInputStream(uri);
                File cache = new File(context.getCacheDir().getAbsolutePath(), displayName);
                FileOutputStream fos = new FileOutputStream(cache);
                FileUtils.copy(is, fos);
                file = cache;
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}


