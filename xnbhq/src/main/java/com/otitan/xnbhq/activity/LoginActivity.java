package com.otitan.xnbhq.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.otitan.xnbhq.R;
import com.otitan.xnbhq.service.RetrofitHelper;
import com.otitan.xnbhq.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 登录页
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_username)
    EditText mEt_username;
    @BindView(R.id.et_password)
    EditText mEt_passwoed;
    @BindView(R.id.tv_login)
    TextView mTv_login;

    private Context mContext;

    public static final String PREFS_NAME = "MyPrefsFile";
    public static SharedPreferences titansp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dth);

        ButterKnife.bind(this);

        mContext = LoginActivity.this;

        titansp = this.getSharedPreferences(PREFS_NAME, 0);

        initView();
    }

    private void initView() {
        mEt_username.setText(titansp.getString("lastusername", "").trim());
        mEt_passwoed.setText(titansp.getString("lastpassword", "").trim());
        mTv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mEt_username.getText().toString().trim();
                String pass = mEt_passwoed.getText().toString().trim();
                if (!user.equals("") && !pass.equals("")) {
                    checkLogin(user, pass);
                } else {
                    ToastUtil.setToast(mContext, "请输入用户名密码");
                }
            }
        });
    }

    private void checkLogin(final String user, final String pass) {
        // admin / dth_123
        Observable<String> observable = RetrofitHelper.getInstance(mContext).getServer().userLogin(user, pass);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.setToast(mContext, "登录失败" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        String[] statusArray = s.split("STATUS");
                        String status = statusArray[1].substring(3, 4);

                        switch (status) {
                            case "0": // 用户名不存在
                                ToastUtil.setToast(mContext, "用户名不存在");
                                break;
                            case "1": // 登录成功
                                startActivity(new Intent(mContext, YzlActivity.class));
                                titansp.edit().putString("isLogin", "1").apply();

                                // 保存上次登陆信息
                                titansp.edit().putString("lastusername", user).apply(); // 账号
                                titansp.edit().putString("lastpassword", pass).apply(); // 密码

                                // 用户信息
                                titansp.edit().putString("USERINFO", s.substring(1, s.length() - 1)).apply();

                                finish();
                                break;
                            case "2": // 登录失败
                                ToastUtil.setToast(mContext, "密码错误");
                                break;
                        }
                    }
                });
    }
}
