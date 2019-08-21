package com.otitan.xnbhq.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.promeg.pinyinhelper.Pinyin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lling.photopicker.PhotoPickerActivity;
import com.otitan.xnbhq.BaseActivity;
import com.otitan.xnbhq.R;
import com.otitan.xnbhq.adapter.Recyc_imageAdapter;
import com.otitan.xnbhq.entity.Bird;
import com.otitan.xnbhq.entity.DtUser;
import com.otitan.xnbhq.entity.Group;
import com.otitan.xnbhq.entity.MonitorPoint;
import com.otitan.xnbhq.entity.SurveyEvent;
import com.otitan.xnbhq.mview.IPicView;
import com.otitan.xnbhq.service.RetrofitHelper;
import com.otitan.xnbhq.util.ToastUtil;
import com.titan.baselibrary.timepaker.TimePopupWindow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.otitan.xnbhq.BaseActivity.PICK_PHOTO_AREA;
import static com.otitan.xnbhq.BaseActivity.PICK_PHOTO_EMP;

/**
 * Created by sp on 2019/3/5.
 * 调查记录
 */
public class SurveyDialog extends Dialog implements Recyc_imageAdapter.PicOnclick {

    @BindView(R.id.ll_condition)
    LinearLayout mLl_condiction;
    @BindView(R.id.et_start)
    EditText mEt_start;
    @BindView(R.id.et_end)
    EditText mEt_end;
    @BindView(R.id.et_weather)
    EditText mEt_weather;
    @BindView(R.id.ll_area)
    LinearLayout mLl_area;
    @BindView(R.id.et_transportation)
    EditText mEt_transportation;
    @BindView(R.id.et_tool)
    EditText mEt_tool;
    @BindView(R.id.et_team)
    EditText mEt_team;
    @BindView(R.id.et_num)
    EditText mEt_num;
    @BindView(R.id.et_region)
    EditText mEt_region;
    @BindView(R.id.cb_east)
    CheckBox mCb_east;
    @BindView(R.id.cb_west)
    CheckBox mCb_west;
    @BindView(R.id.cb_south)
    CheckBox mCb_south;
    @BindView(R.id.cb_other)
    CheckBox mCb_other;
    @BindView(R.id.et_emphasis)
    EditText mEt_emphasis;
    @BindView(R.id.et_area)
    EditText mEt_area;
    @BindView(R.id.tv_area)
    TextView mTv_area;
    @BindView(R.id.rv_area)
    RecyclerView mRv_area;
    @BindView(R.id.tv_emphasis)
    TextView mTv_emphasis;
    @BindView(R.id.rv_emphasis)
    RecyclerView mRv_emphasis;

    @BindView(R.id.ll_content)
    LinearLayout mLl_content;
    @BindView(R.id.sp_bird)
    Spinner mSp_bird;
    @BindView(R.id.et_find)
    EditText mEt_find;
    @BindView(R.id.et_place)
    EditText mEt_place;
    @BindView(R.id.et_environment)
    EditText mEt_environment;
    @BindView(R.id.et_number)
    EditText mEt_number;
    @BindView(R.id.et_residence)
    EditText mEt_residence;
    @BindView(R.id.et_discover)
    EditText mEt_discover;
    @BindView(R.id.et_photo)
    EditText mEt_photo;
    @BindView(R.id.et_video)
    EditText mEt_video;
    @BindView(R.id.et_access)
    EditText mEt_access;

    private BaseActivity mContext;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static SharedPreferences titansp;

    private IPicView picView;

    private DtUser dtUser;

    private List<String> acList;
    private String bCode = "";
    private String PCode;
    private String GCode;
    private boolean isNewPoint = true;
    private boolean isNewGroup = true;

    private Group group = new Group();

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    private static WindowManager.LayoutParams dialogParams;
    private Bird bird;

    public void setDialogParams(WindowManager.LayoutParams dialogParams) {
        SurveyDialog.dialogParams = dialogParams;
    }

    public SurveyDialog(@NonNull BaseActivity context, int themeResId, IPicView iPicView) {
        super(context, themeResId);
        mContext = context;
        picView = iPicView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_survey);

        ButterKnife.bind(this);

        titansp = mContext.getSharedPreferences(PREFS_NAME, 0);

        initView();
    }

    private void initView() {
        this.setCanceledOnTouchOutside(false);

        String userInfo = titansp.getString("USERINFO", "").trim();
        dtUser = new Gson().fromJson(userInfo, DtUser.class);
        String realName = dtUser.getREALNAME();
        mEt_discover.setText(realName);

        String lon = String.valueOf(BaseActivity.gpspoint.getX()); // 经度
        String lat = String.valueOf(BaseActivity.gpspoint.getY()); // 纬度

        String lastName = Pinyin.toPinyin(realName,"/").substring(0, 1);
        String monitorPoint = titansp.getString("MonitorPoint", "");
        String newPoint = titansp.getString("NewMonitorPoint", "");
        if (!monitorPoint.isEmpty()) {
            PCode = monitorPoint;
        } else if (!newPoint.isEmpty()) {
            if (isNewPoint) {
                List<String> list = new Gson().fromJson(newPoint, new TypeToken<ArrayList<String>>() {}.getType());
                String s0 = list.get(0); // 名称
                String s1 = list.get(1); // 保护区
                String s2 = list.get(2); // 描述
                String s3 = list.get(3); // 最后编码
                int i = Integer.parseInt(s3.substring(6, 8));
                MonitorPoint point = new MonitorPoint();
                switch (s1) {
                    case "东洞庭湖":
                        PCode = "43001" + lastName + (i + 1);
                        point.setCODE(PCode);
                        point.setBHQ_NAME("湖南东洞庭湖");
                        break;
                    case "西洞庭湖":
                        PCode = "43002" + lastName + (i + 1);
                        point.setCODE(PCode);
                        point.setBHQ_NAME("湖南西洞庭湖");
                        break;
                    case "南洞庭湖":
                        PCode = "43003" + lastName + (i + 1);
                        point.setCODE(PCode);
                        point.setBHQ_NAME("湖南南洞庭湖");
                        break;
                    case "横岭湖":
                        PCode = "43004" + lastName + (i + 1);
                        point.setCODE(PCode);
                        point.setBHQ_NAME("湖南横岭湖");
                        break;
                }
                point.setNAME(s0);
                point.setJD(lon);
                point.setWD(lat);
                point.setDETAIL(s2);
                String s = new Gson().toJson(point);
                addPoint(s);
            }
        } else {
            ToastUtil.setToast(mContext, "请选择监测点");
            return;
        }

        String groupCode = titansp.getString("GroupCode", "");
        String newGroup = titansp.getString("NewGroup", "");
        if (!groupCode.isEmpty()) {
            getGroupInfo(groupCode);
        } else if (!newGroup.isEmpty()) {
            // GCODE; // 调查组编码 GNAME; // 调查组名称 GMEMBER; // 调查组成员 YEAR; // 年度 SUMNUM; // 调查组人数
            List<String> list = new Gson().fromJson(newPoint, new TypeToken<ArrayList<String>>() {}.getType());
            String s = list.get(0); // 调查组名称
            String s1 = list.get(1); // 调查组成员
            if (s1.contains("，")) { // 中文“，”
                group.setGMEMBER(s1.replace("，", ","));
                String[] perArray = s1.split("，");
                group.setSUMNUM(perArray.length + "");
            } else if (s1.contains(",")) { // 英文“,”
                group.setGMEMBER(s1);
                String[] perArray = s1.split(",");
                group.setSUMNUM(perArray.length + "");
            } else { // 一人
                group.setGMEMBER(s1);
                group.setSUMNUM("1");
            }

            GCode = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(System.currentTimeMillis());
            group.setGCODE(GCode);
            group.setGNAME(s);
            group.setYEAR(GCode.substring(0, 4));
        } else {
            ToastUtil.setToast(mContext, "请选择调查组");
            return;
        }

        acList = new ArrayList<>();
        acList.add("000");
        acList.add("000");
        acList.add("000");
        acList.add("000");

        getBirdInfo();
    }

    private void addPoint(String s) {
        Observable<String> observable = RetrofitHelper.getInstance(mContext).getServers().addMonitor(s);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.setToast(mContext, "获取监测点信息失败" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                boolean b = Boolean.parseBoolean(s);
                isNewPoint = !b;
            }
        });
    }

    private void getGroupInfo(String code) {
        Observable<String> observable = RetrofitHelper.getInstance(mContext).getServers().queryGroup(code, "");
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
                Group group = new Gson().fromJson(s.substring(1, s.length() - 1), Group.class);
                GCode = group.getGCODE();
                showGroupInfo(group);
            }
        });
    }

    private void getBirdInfo() {
        Observable<String> observable = RetrofitHelper.getInstance(mContext).getServers().queryBird("");
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
                ArrayList<Bird> list = new Gson().fromJson(s, new TypeToken<ArrayList<Bird>>() {}.getType());
                showBirdInfo(list);
            }
        });
    }

    private void showBirdInfo(final List<Bird> list) {
        List<String> birdList = new ArrayList<>();
        birdList.add("=请选择鸟种=");
        for (Bird bird : list) {
            birdList.add(bird.getNAME());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, birdList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_stytle);

        mSp_bird.setAdapter(arrayAdapter);
        mSp_bird.setVisibility(View.VISIBLE);

        mSp_bird.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    bCode = "";
                } else {
                    bird = list.get(i - 1);
                    bCode = list.get(i - 1).getCODE();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void showGroupInfo(Group g) {
        mEt_start.setText(g.getSTARTDATE().split(" ")[0]);
        mEt_start.setEnabled(false);

        mEt_end.setText(g.getENDDATE().split(" ")[0]);
        mEt_end.setEnabled(false);

        mEt_weather.setText(g.getIWEATHER());
        mEt_weather.setEnabled(false);

        mEt_transportation.setText(g.getITRANSPORT());
        mEt_transportation.setEnabled(false);

        mEt_tool.setText(g.getITOOL());
        mEt_tool.setEnabled(false);

        mEt_team.setText(g.getZUNUM());
        mEt_team.setEnabled(false);

        mEt_num.setText(g.getMANNUM());
        mEt_num.setEnabled(false);

        mEt_region.setText("调查保护区");
        mCb_east.setClickable(false);
        mCb_west.setClickable(false);
        mCb_south.setClickable(false);
        mCb_other.setClickable(false);

        String bhq = g.getSSBHQ();
        String[] bhqArray = bhq.split(",");
        for (String region : bhqArray) {
            if (region.contains("H100096")) {
                mCb_east.setChecked(true);
            }
            if (region.contains("H100100")) {
                mCb_west.setChecked(true);
            }
            if (region.contains("H100099")) {
                mCb_south.setChecked(true);
            }
            if (region.contains("H100098")) {
                mCb_other.setChecked(true);
            }
        }

        mEt_emphasis.setText(g.getDCAREA());
        mEt_emphasis.setEnabled(false);

        mEt_area.setText(g.getDCMJ() + " 万亩");
        mEt_area.setEnabled(false);

        mTv_area.setVisibility(View.GONE);
        mTv_emphasis.setVisibility(View.GONE);

        group.setGCODE(g.getGCODE());
        group.setGNAME(g.getGNAME());
        group.setGMEMBER(g.getGMEMBER());
        group.setYEAR(g.getYEAR());
        group.setSUMNUM(g.getSUMNUM());
    }

    @OnClick({R.id.iv_close, R.id.tv_condition, R.id.et_start, R.id.et_end, R.id.tv_area, R.id.tv_emphasis, R.id.tv_content, R.id.et_find, R.id.tv_determine, R.id.tv_cancel})
    public void myClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_condition:
                mLl_condiction.setVisibility(View.VISIBLE);
                mLl_content.setVisibility(View.GONE);
                break;
            case R.id.et_start:
                getTime(mEt_start, false);
                break;
            case R.id.et_end:
                getTime(mEt_end, false);
                break;
            case R.id.tv_area:
                toSelectPic(PICK_PHOTO_AREA);
                break;
            case R.id.tv_emphasis:
                toSelectPic(PICK_PHOTO_EMP);
                break;
            case R.id.tv_content:
                if (isNewGroup) {
                    getCondiction();
                }
                mLl_condiction.setVisibility(View.GONE);
                mLl_content.setVisibility(View.VISIBLE);
                break;
            case R.id.et_find:
                getTime(mEt_find, false);
                break;
            case R.id.tv_determine:
                getSurveyInfo();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    @OnCheckedChanged({R.id.cb_east, R.id.cb_west, R.id.cb_south, R.id.cb_other})
    public void myCheckedChanged(CompoundButton button, boolean isChecked) {
        switch (button.getId()) {
            case R.id.cb_east: // 湖南东洞庭湖
                if (isChecked) {
                    acList.remove(0);
                    acList.add(0, "H100096");
                } else {
                    acList.remove(0);
                    acList.add(0, "000");
                }
                break;
            case R.id.cb_west: // 湖南西洞庭湖
                if (isChecked) {
                    acList.remove(1);
                    acList.add(1, "H100100");
                } else {
                    acList.remove(1);
                    acList.add(1, "000");
                }
                break;
            case R.id.cb_south: // 湖南南洞庭湖
                if (isChecked) {
                    acList.remove(2);
                    acList.add(2, "H100099");
                } else {
                    acList.remove(2);
                    acList.add(2, "000");
                }
                break;
            case R.id.cb_other: // 湖南横岭湖
                if (isChecked) {
                    acList.remove(3);
                    acList.add(3, "H100098");
                } else {
                    acList.remove(3);
                    acList.add(3, "000");
                }
                break;
        }
    }

    private void getCondiction() {
        //     MANNUM; // 几人一组 ZUNUM; // 分成几小组 ZHAIYAO; // 摘要 KEYWORDS; // 关键字 STARTDATE; // 开始时间
        //     ENDDATE; // 结束时间 IWEATHER; // 天气 ITRANSPORT; // 交通工具 ITOOL; // 调查工具
        //     ICONTENT; // 记录内容 SSBHQ; // 调查保护区 DCAREA; // 重点监测点
        //     DCMJ; // 调查区域总面积 DCQYT; // 调查区域图 ZDQYT; // 重点监测区域图
        String areaCode = "";
        for (String ac : acList) {
            if (!ac.equals("000")) {
                areaCode = areaCode + ac + ",";
            }
        }
        if (areaCode.isEmpty()) {
            ToastUtil.setToast(mContext, "请选择调查区域");
        } else {
            //            ToastUtil.setToast(mContext, areaCode.substring(0, areaCode.length() - 1));
        }

        String start = mEt_start.getText().toString().trim();
        String end = mEt_end.getText().toString().trim();
        String weather = mEt_weather.getText().toString().trim();
        String transportation = mEt_transportation.getText().toString().trim();
        String tool = mEt_tool.getText().toString().trim();
        String team = mEt_team.getText().toString().trim(); // 小组数量
        String num = mEt_num.getText().toString().trim(); // 小组人数
        String emphasis = mEt_emphasis.getText().toString().trim();
        String area = mEt_area.getText().toString().trim();

        group.setMANNUM(num);
        group.setZUNUM(team);
        group.setZHAIYAO("");
        group.setKEYWORDS("");
        group.setSTARTDATE(start);
        group.setENDDATE(end);
        group.setIWEATHER(weather);
        group.setITRANSPORT(transportation);
        group.setITOOL(tool);
        group.setICONTENT("");
        group.setSSBHQ(areaCode.substring(0, areaCode.length() - 1));
        group.setDCAREA(emphasis.substring(0, 12) + "...");
        group.setDCMJ(area);
        group.setDCQYT(""); // 调查区域图
        group.setZDQYT(""); // 重点监测区域图

        String s = new Gson().toJson(group);
        addGroup(s);
    }

    private void addGroup(String s) {
        Observable<String> observable = RetrofitHelper.getInstance(mContext).getServers().addGroup(s);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.setToast(mContext, e.getMessage());
            }

            @Override
            public void onNext(String s) {
                boolean b = Boolean.parseBoolean(s);
                isNewGroup = !b;
            }
        });
    }

    private void toSelectPic(final int PHOTO) {
        if (picView.getPicList(PHOTO).size() != 0 && picView.getPicList(PHOTO).size() != 9) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("重新选择会覆盖之前的图片");
            builder.setMessage("是否重新选择");
            builder.setCancelable(true);
            builder.setPositiveButton("重新选择", new OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(mContext, PhotoPickerActivity.class);
                    intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);//是否显示相机
                    intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI);//选择模式（默认多选模式）
                    intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, PhotoPickerActivity.DEFAULT_NUM);//最大照片张数
                    mContext.startActivityForResult(intent, PHOTO);
                }
            });
            builder.setNegativeButton("取消", new OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(16);
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(16);
        }
        if (picView.getPicList(PHOTO).size() == 9) {
            ToastUtil.setToast(mContext, "照片最多只能选择9张");
            return;
        }
        if (picView.getPicList(PHOTO).size() == 0) {
            Intent intent = new Intent(mContext, PhotoPickerActivity.class);
            intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);//是否显示相机
            intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI);//选择模式（默认多选模式）
            intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, PhotoPickerActivity.DEFAULT_NUM);//最大照片张数
            mContext.startActivityForResult(intent, PHOTO);
        }
    }

    public void loadPhoto(int TYPE) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        Recyc_imageAdapter adapter = new Recyc_imageAdapter(mContext, picView.getPicList(TYPE), dialogParams.width / 4);

        switch (TYPE) {
            case PICK_PHOTO_AREA:
                mRv_area.setLayoutManager(layoutManager);
                mRv_area.setAdapter(adapter);
                adapter.setPicOnclick(this);
                break;
            case PICK_PHOTO_EMP:
                mRv_emphasis.setLayoutManager(layoutManager);
                mRv_emphasis.setAdapter(adapter);
                adapter.setPicOnclick(this);
                break;
        }
    }

    private void getSurveyInfo() {
        String findTime = mEt_find.getText().toString().trim();
        String place = mEt_place.getText().toString().trim();
        String environment = mEt_environment.getText().toString().trim();
        String number = mEt_number.getText().toString().trim();
        String discover = mEt_discover.getText().toString().trim();
        String access = mEt_access.getText().toString().trim();

        if (findTime.isEmpty()) {
            ToastUtil.setToast(mContext, "请选择发现时间");
            return;
        }
        if (place.isEmpty()) {
            ToastUtil.setToast(mContext, "请输入发现地点");
            return;
        }
        if (environment.isEmpty()) {
            ToastUtil.setToast(mContext, "请输入分布环境");
            return;
        }
        if (number.isEmpty()) {
            ToastUtil.setToast(mContext, "请输入鸟种数量");
            return;
        }

        /*SurveyEvent se = new SurveyEvent(GCode, mEt_start.getText().toString().trim(), mEt_end.getText().toString().trim(),
                titansp.getString("MonitorPoint", ""), bCode, environment, discover, findTime, place, number, "0");
        String s = new Gson().toJson(se);*/

        /*ATID	N	INTEGER	Y			目科属ID
        CODE	N	VARCHAR2(100)	Y			鸟种编号
        NAME	N	NVARCHAR2(200)	N			中文名
        LDNAME	N	NVARCHAR2(200)	Y			拉丁名
        ENNAME	N	NVARCHAR2(200)	Y			英文名
        PINYIN	N	NVARCHAR2(200)	Y			拼音首字母
        BNAME	N	NVARCHAR2(200)	Y			别名

        BHJB	N	INTEGER	Y			保护级别：1、2、3
        CITES	N	INTEGER	Y			CITES：附录1、2
        IUCN	N	VARCHAR2(10)	Y			IUCN：LC,EN,VU,NT
        ZRBH	N	VARCHAR2(10)	Y			中日双边保护：T,F
        ZABH	N	VARCHAR2(10)	Y			中澳双边保护：T,F
        SHX	N	VARCHAR2(10)	Y			生活型：林,水
        STX	N	VARCHAR2(10)	Y			生态型：猛、游、涉、鸣、走、攀
        JLX	N	VARCHAR2(10)	Y			居留型：夏、冬、留、旅、谜
        QSX	N	VARCHAR2(10)	Y			取食型：食草、食虫、食鱼、杂食
        BZ	N	INTEGER	Y			1%标准
        ZYSBTZ	N	VARCHAR2(1000)	Y			主要识别特征
        TPDZ	N	NVARCHAR2(200)	Y			图片地址
        SPDZ	N	NVARCHAR2(200)	Y			鸟叫声音地址
        CREATEID	N	VARCHAR2(20)	Y			创建人ID
        CREATENAME	N	NVARCHAR2(20)	Y			创建人名称
        CREATEDATE	N	DATE	Y			创建日期
*/

        SurveyEvent se = new SurveyEvent();

        se.setATID(bird.getATID());
        se.setCODE(bird.getCODE());
        se.setNAME(bird.getNAME());
        se.setLDNAME(bird.getLDNAME());
        se.setENNAME(bird.getENNAME());
        se.setPINYIN(bird.getPINYIN());
        se.setBNAME(bird.getBNAME());
        se.setBHJB(bird.getBHJB());
        se.setCITES(bird.getCITES());
        se.setIUCN(bird.getIUCN());
        se.setZRBH(bird.getZRBH());
        se.setZABH(bird.getZABH());
        se.setSHX(bird.getSHX());
        se.setSTX(bird.getSTX());
        se.setJLX(bird.getJLX());
        se.setQSX(bird.getQSX());
        se.setBZ(bird.getBZ());
        se.setZYSBTZ(bird.getZYSBTZ());
        se.setTPDZ(bird.getTPDZ());
        se.setSPDZ(bird.getSPDZ());
        se.setCREATEID(dtUser.getID() + "");
        se.setCREATENAME(dtUser.getREALNAME());
        se.setCREATEDATE(findTime);

        String s = new Gson().toJson(se);
        addEvent(s);
    }

    private void addEvent(String s) {
        Observable<String> observable = RetrofitHelper.getInstance(mContext).getServers().editEvent(s);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.setToast(mContext, "上报信息失败" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                boolean b = Boolean.parseBoolean(s);
                /*if (b) {
                    ToastUtil.setToast(mContext, "上报成功");
                } else {
                    ToastUtil.setToast(mContext, "上报失败");
                }*/
                ToastUtil.setToast(mContext, "完成上报");
            }
        });
    }

    @Override
    public void setPicOnclick(View item, int position) {
        /*Intent intent = new Intent(mContext, ImageBrowseActivity.class);
        intent.putStringArrayListExtra("images", picView.getPicList());
        intent.putExtra("position", position);
        mContext.startActivity(intent);*/
    }

    private void getTime(final EditText editText, boolean isBefore) {
        TimePopupWindow timePopupWindow = new TimePopupWindow(mContext, TimePopupWindow.Type.ALL);
        timePopupWindow.setCyclic(true);

        timePopupWindow.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                String time = format.format(date);
                editText.setText(time);
            }
        });

        timePopupWindow.showAtLocation(editText, Gravity.BOTTOM, 0, 0, new Date(), isBefore);
    }
}
