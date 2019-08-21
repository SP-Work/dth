package com.otitan.xnbhq.entity;

/**
 * Created by sp on 2019/2/27.
 * 监测点
 */
public class MonitorPoint {

    private String ID; //

    private String CODE; // 监测点编码

    private String NAME; // 监测点名称

    private String JD; // 经度

    private String WD; // 纬度

    private String SSBHQ; // 所属保护区代码

    private String BHQ_NAME; // 保护区名称

    private String DETAIL; //

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getJD() {
        return JD;
    }

    public void setJD(String JD) {
        this.JD = JD;
    }

    public String getWD() {
        return WD;
    }

    public void setWD(String WD) {
        this.WD = WD;
    }

    public String getSSBHQ() {
        return SSBHQ;
    }

    public void setSSBHQ(String SSBHQ) {
        this.SSBHQ = SSBHQ;
    }

    public String getBHQ_NAME() {
        return BHQ_NAME;
    }

    public void setBHQ_NAME(String BHQ_NAME) {
        this.BHQ_NAME = BHQ_NAME;
    }

    public String getDETAIL() {
        return DETAIL;
    }

    public void setDETAIL(String DETAIL) {
        this.DETAIL = DETAIL;
    }
}
