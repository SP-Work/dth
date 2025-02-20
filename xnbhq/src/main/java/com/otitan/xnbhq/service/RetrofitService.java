package com.otitan.xnbhq.service;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by li on 2017/5/5.
 * 接口 retrofitservice 连接后台数据库
 */

public interface RetrofitService {

    @GET("/gyly/Service/Service.asmx/addMoblieSysInfo")
    Observable<String> addMoblieSysInfo(@Query("sysname") String sysname, @Query("tel") String tel,@Query("dw") String dw, @Query("retime") String retime,@Query("sbmc") String sbmc, @Query("sbh") String sbh,@Query("bz") String bz);

    @GET("/gyly/Service/Service.asmx/selSBUserInfo")
    Observable<String> selMobileSysInfo(@Query("sbh") String sbh,@Query("xlh") String xlh,@Query("sbmc") String type);
    /*提交疫源疫病信息*/
    @GET("/gyly/Service/Service.asmx/addYyybJcdwData")
    Observable<String> addYyybJcdwData(@Query("json") String json);
    /*获取监测单位数据*/
    @GET("/gyly/Service/Service.asmx/getYyybJcdwData")
    Observable<String> getJcdwData();
    /*分页获取运输台账数据*/
    @GET("/gyly/Service/Service.asmx/getYszInfo")
    Observable<String> getYszData(@Query("pageIndex") String pageIndex);

    /*木材经营 许可证查询*/
    @GET("/gyly/Service/Service.asmx/getXkznsInfo")
    Observable<String> getXkzData(@Query("id") int id);

    /*木材经营 企业信息添加*/
    @GET("/gyly/Service/Service.asmx/addQyInfo")
    Observable<String> addQyData(@Query("json") String json);

    //@GET("updateName")
    @GET("updateName")
    Observable<String> addMoblieSysInfo(@Query("name") String sysname, @Query("sbh") String sbh, @Query("tel") String tel);
    @POST("selMobileInfo")
    Observable<String> selMobileSysInfo(@Query("sbh") String sbh);
    /*系统运行时进行设备号和序列号入库*/
    @GET("addMacAddress")
    Observable<String> addMacAddress(@Query("sbh") String sbh, @Query("xlh") String xlh);
    /*检查用户名和手机号是否录入*/
    @GET("checkMobileUser")
    Observable<String> checkMobileUser(@Query("sbh") String sbh);
    /*实时传送GPS坐标*/
    @GET("uPLonLat")
    Observable<String> uPLonLat(@Query("SBH") String sbh,@Query("LON") String LON,@Query("LAT") String LAT,@Query("time")String time);
    /*更新设备使用者信息*/
    @GET("updateMobileInfo")
    Observable<String> updateMobileInfo(@Query("username") String name,@Query("tel") String tel,@Query("dz") String dz,@Query("sbmc") String sbmc,@Query("sbh") String sbh);
    /*更新设备使用者信息*/
    @GET("updateName")
    Observable<String> updateName(@Query("name") String name,@Query("SBH") String sbh,@Query("tel") String tel);
    /*检查登录*/
    @GET("checkUser")
    Observable<String> checkUser(@Query("name") String username,@Query("psw") String password);
    /*上传现场信息*/
    @FormUrlEncoded
    @POST("UPPatrolEvent")
    Observable<String> upRequisitionInfo(@Field("jsonText") String jsonText);



    /**
     * 洞庭湖
     */
    // 登录
    @GET("UserLogin")
    Observable<String> userLogin(@Query("username") String username, @Query("password") String password);

    // 更新设备信息
    @GET("AddDeviceInfo")
    Observable<String> addDeviceInfo(@Query("username") String username, @Query("tel") String tel, @Query("dz") String dz, @Query("sbmc") String sbmc, @Query("sbh") String sbh);

    /*上传现场信息*/
    @FormUrlEncoded
    @POST("UPPatrolEvent")
    Observable<String> upPatrolInfo(@Field("jsonText") String jsonText);

    // 添加监测点
    @GET("AddMonitoring")
    Observable<String> addMonitorPoint(@Query("name") String name, @Query("bhq") String bhq, @Query("detail") String detail, @Query("jd") double jd, @Query("wd") double wd, @Query("userID") int userID);

    // 查询监测点信息
    @GET("queryMonitoring")
    Observable<String> queryMonitoring(@Query("str") String str);

    // 查询调查组信息
    @GET("queryGroup")
    Observable<String> queryGroup(@Query("str") String str, @Query("year") String year);

    // 查询鸟类信息
    @GET("queryBird")
    Observable<String> queryBird(@Query("str") String str);

    // 创建监测点
    @GET("EditMonitor")
    Observable<String> addMonitor(@Query("jsonstr") String jsonstr);

    // 创建调查组
    @GET("EditGroup")
    Observable<String> addGroup(@Query("jsonstr") String jsonstr);

    // 监测事件
    @GET("EditEvent")
    Observable<String> editEvent(@Query("jsonstr") String jsonstr);
}
