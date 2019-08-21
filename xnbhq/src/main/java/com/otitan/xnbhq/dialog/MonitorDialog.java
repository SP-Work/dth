package com.otitan.xnbhq.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.github.abel533.echarts.code.ColorMappingBy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.otitan.xnbhq.BaseActivity;
import com.otitan.xnbhq.R;
import com.otitan.xnbhq.entity.DtUser;
import com.otitan.xnbhq.entity.MonitorPoint;
import com.otitan.xnbhq.service.RetrofitHelper;
import com.otitan.xnbhq.util.ToastUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.github.abel533.echarts.code.ColorMappingBy.id;

/**
 * Created by sp on 2019/2/26.
 * 监测点
 */
public class MonitorDialog extends Dialog {

    @BindView(R.id.sp_below)
    Spinner mSp_below;
    @BindView(R.id.sp_monitor)
    Spinner mSp_monitor;
    @BindView(R.id.ll_new)
    LinearLayout mLl_new;
    @BindView(R.id.et_name)
    EditText mEt_name;
    @BindView(R.id.et_lon)
    EditText mEt_lon;
    @BindView(R.id.et_lat)
    EditText mEt_lat;
    @BindView(R.id.sp_area)
    Spinner mSp_area;
    @BindView(R.id.et_location)
    EditText mEt_location;

    private Context mContext;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static SharedPreferences titansp;

    private String pointCode = "";
    private String patrolArea = "";
    private String lastCode = "";
    private boolean isChoose = true;
    private DtUser dtUser;

    public MonitorDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_monitor);

        ButterKnife.bind(this);

        titansp = mContext.getSharedPreferences(PREFS_NAME, 0);

        initView();
    }

    private void initView() {
        this.setCanceledOnTouchOutside(false);

        mEt_lon.setText(String.valueOf(BaseActivity.gpspoint.getX())); // 经度
        mEt_lat.setText(String.valueOf(BaseActivity.gpspoint.getY())); // 纬度

        String userinfo = titansp.getString("USERINFO", "").trim();
        dtUser = new Gson().fromJson(userinfo, DtUser.class);

        getMonitorPoint("");

        initSpinner();
    }

    private void initSpinner() {
        List<String> list = new ArrayList<>();
        list.add("=请选择保护区=");
        list.addAll(Arrays.asList(mContext.getResources().getStringArray(R.array.dth)));
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, list);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_stytle);

        mSp_below.setAdapter(arrayAdapter);
        mSp_below.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        mSp_monitor.setVisibility(View.GONE);
                        break;
                    case 1: // 湖南东洞庭湖 H100096
                        getMonitorPoint("湖南东洞庭湖");
                        break;
                    case 2: // 湖南西洞庭湖 H100100
                        getMonitorPoint("湖南西洞庭湖");
                        break;
                    case 3: // 湖南南洞庭湖 H100099
                        getMonitorPoint("湖南南洞庭湖");
                        break;
                    case 4: // 湖南横岭湖 H100098
                        getMonitorPoint("湖南横岭湖");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSp_area.setAdapter(arrayAdapter);
        mSp_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        patrolArea = "";
                        break;
                    case 1:
                        patrolArea = "东洞庭湖";
                        break;
                    case 2:
                        patrolArea = "西洞庭湖";
                        break;
                    case 3:
                        patrolArea = "南洞庭湖";
                        break;
                    case 4:
                        patrolArea = "横岭湖";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * 获取监测点
     */
    private void getMonitorPoint(final String area) {
        Observable<String> observable = RetrofitHelper.getInstance(mContext).getServers().queryMonitoring(area);
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
                final ArrayList<MonitorPoint> list = new Gson().fromJson(s, new TypeToken<ArrayList<MonitorPoint>>() {}.getType());

                if (area.isEmpty()) {
                    lastCode = list.get(list.size() - 1).getCODE();
                    return;
                }

                List<String> pointList = new ArrayList<>();
                pointList.add("=请选择监测点=");
                for (MonitorPoint point : list) {
                    pointList.add(point.getNAME());
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, pointList);
                arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_stytle);

                mSp_monitor.setAdapter(arrayAdapter);
                mSp_monitor.setVisibility(View.VISIBLE);

                mSp_monitor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != 0) {
                            pointCode = list.get(i - 1).getCODE();
                        } else {
                            pointCode = "";
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
                mSp_below.setVisibility(View.VISIBLE);
                mSp_monitor.setVisibility(View.GONE);
                mLl_new.setVisibility(View.GONE);

                mSp_area.setSelection(0);
                mEt_name.setText("");
                mEt_location.setText("");

                isChoose = true;
                break;

            case R.id.tv_new:
                mSp_below.setVisibility(View.GONE);
                mSp_monitor.setVisibility(View.GONE);
                mLl_new.setVisibility(View.VISIBLE);

                mSp_below.setSelection(0);
                mSp_monitor.setSelection(0);

                isChoose = false;
                break;

            case R.id.tv_determine:
                if (isChoose) {
                    getChooseInfo();
                    titansp.edit().putString("NewMonitorPoint", "").apply();
                } else {
                    getNewInfo();
                    titansp.edit().putString("MonitorPoint", "").apply();
                }
                break;

            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    private void getChooseInfo() {
        if (pointCode.isEmpty()) {
            ToastUtil.setToast(mContext, "请选择监测点");
        } else {
            titansp.edit().putString("MonitorPoint", pointCode).apply(); // 监测点
            dismiss();
        }
    }

    /**
     * 监测点信息
     */
    private void getNewInfo() {
        String name = mEt_name.getText().toString().trim(); // 名称
        String lon = mEt_lon.getText().toString().trim(); // 经度
        String lat = mEt_lat.getText().toString().trim(); // 纬度
        String location = mEt_location.getText().toString().trim(); // 位置描述

        int id = dtUser.getID();

        if (name.isEmpty()) {
            ToastUtil.setToast(mContext, "请输入监测点名称");
        } else if (patrolArea.isEmpty()) {
            ToastUtil.setToast(mContext, "请选择所属保护区");
        } else {
            addMonitor(name, location, Double.parseDouble(lon), Double.parseDouble(lat), id);
        }
    }

    /**
     * 上传监测点
     */
    private void addMonitor(final String name, final String location, double lon, double lat, int id) {
        Observable<String> observable = RetrofitHelper.getInstance(mContext).getServer().addMonitorPoint(name, patrolArea, location, lon, lat, id);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                List<String> list = new ArrayList<>();
                list.add(name);
                list.add(patrolArea);
                list.add(location);
                list.add(lastCode);
                String s = new Gson().toJson(list);
                titansp.edit().putString("NewMonitorPoint", s).apply(); // 新建监测点
                dismiss();
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.setToast(mContext, "创建失败" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                if (s.contains("1")) {
                    ToastUtil.setToast(mContext, "监测点创建完成");
                } else {
                    ToastUtil.setToast(mContext, "监测点创建失败");
                }
            }
        });
    }
}
