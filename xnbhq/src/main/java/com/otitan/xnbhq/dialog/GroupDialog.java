package com.otitan.xnbhq.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.otitan.xnbhq.BaseActivity;
import com.otitan.xnbhq.R;
import com.otitan.xnbhq.entity.Group;
import com.otitan.xnbhq.entity.MonitorPoint;
import com.otitan.xnbhq.service.RetrofitHelper;
import com.otitan.xnbhq.util.ToastUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sp on 2019/2/27.
 * 调查组
 */
public class GroupDialog extends Dialog {

    @BindView(R.id.sp_group)
    Spinner mSp_group;
    @BindView(R.id.ll_new)
    LinearLayout mLl_new;
    @BindView(R.id.et_name)
    EditText mEt_name;
    @BindView(R.id.et_personnel)
    EditText mEt_personnel;

    private Context mContext;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static SharedPreferences titansp;

    private String gCode = "";
    private boolean isChoose = true;

    public GroupDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_group);

        ButterKnife.bind(this);

        titansp = mContext.getSharedPreferences(PREFS_NAME, 0);

        initView();
    }

    private void initView() {
        this.setCanceledOnTouchOutside(false);

        String lon = String.valueOf(BaseActivity.gpspoint.getX()); // 经度
        String lat = String.valueOf(BaseActivity.gpspoint.getY()); // 纬度

        String monitorPoint = titansp.getString("MonitorPoint", "");
        String newPoint = titansp.getString("NewMonitorPoint", "");

        if (monitorPoint.isEmpty() && newPoint.isEmpty()) {
            ToastUtil.setToast(mContext, "请选择监测点");
            return;
        }

        getGroupData();
    }

    private void getGroupData() {
        Observable<String> observable = RetrofitHelper.getInstance(mContext).getServers().queryGroup("", "");
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.setToast(mContext, "获取信息失败" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                final ArrayList<Group> list = new Gson().fromJson(s, new TypeToken<ArrayList<Group>>() {}.getType());

                List<String> groupList = new ArrayList<>();
                groupList.add("=请选择调查组=");
                for (Group group : list) {
                    groupList.add(group.getGNAME());
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, groupList);
                arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_stytle);

                mSp_group.setAdapter(arrayAdapter);
                mSp_group.setVisibility(View.VISIBLE);

                mSp_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            gCode = "";
                        } else {
                            gCode = list.get(i - 1).getGCODE();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });
    }

    @OnClick({R.id.iv_close, R.id.tv_choose, R.id.tv_new, R.id.tv_determine, R.id.tv_cancel})
    public void myClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;

            case R.id.tv_choose:
                mSp_group.setVisibility(View.VISIBLE);
                mLl_new.setVisibility(View.GONE);

                mEt_name.setText("");
                mEt_personnel.setText("");

                isChoose = true;
                break;

            case R.id.tv_new:
                mSp_group.setVisibility(View.GONE);
                mLl_new.setVisibility(View.VISIBLE);

                mSp_group.setSelection(0);

                isChoose = false;
                break;

            case R.id.tv_determine:
                if (isChoose) {
                    getGroupInfo();
                    titansp.edit().putString("NewGroup", "").apply();
                } else {
                    getNewInfo();
                    titansp.edit().putString("GroupCode", "").apply();
                }
                break;

            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    private void getGroupInfo() {
        if (gCode.isEmpty()) {
            ToastUtil.setToast(mContext, "请选择调查组");
        } else {
            titansp.edit().putString("GroupCode", gCode).apply(); // 调查组
            dismiss();
        }
    }

    private void getNewInfo() {
        String groupName = mEt_name.getText().toString().trim();
        String personnel = mEt_personnel.getText().toString().trim();

        if (groupName.isEmpty()) {
            ToastUtil.setToast(mContext, "请输入调查组名称");
        } else if (personnel.isEmpty()) { // 没人
            ToastUtil.setToast(mContext, "请输入调查组成员");
        } else {
            List<String> list = new ArrayList<>();
            list.add(groupName);
            list.add(personnel);
            String s = new Gson().toJson(list);
            titansp.edit().putString("NewGroup", s).apply(); // 新建调查组
            dismiss();
        }

        /*else if (personnel.contains("，")) { // 中文“，”
            String[] perArray = personnel.split("，");
        } else if (personnel.contains(",")) { // 英文“,”
            String[] perArray = personnel.split(",");
        } else { // 一人
            //
        }*/
    }
}
