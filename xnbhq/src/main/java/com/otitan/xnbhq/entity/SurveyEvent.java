package com.otitan.xnbhq.entity;

import com.github.abel533.echarts.code.Y;

/**
 * Created by sp on 2019/3/10.
 * 调查事件
 */
public class SurveyEvent {

    /*private String GROUPID; // 所属调查组

    private String STARTDATE; // 开始时间

    private String ENDDATE; // 截止时间

    private String JCODE; // 监测点编码

    private String BIRDCODE; // 鸟种编码

    private String ENVIRONMENT; // 分布环境

    private String DISCOVERER; // 发现人

    private String DISCOVERYTIME; // 发现时间

    private String DISCOVERYSITE; // 发现地点

    private String BIRDNUM; // 数量

    private String SHZT; // 审核状态（0-未审核 1-通过 2-不通过）*/

    private String ATID; // 目科属ID

    private String CODE; // 鸟种编号

    private String NAME; // 中文名

    private String LDNAME; // 拉丁名

    private String ENNAME; // 英文名

    private String PINYIN; // 拼音首字母

    private String BNAME; // 别名

    private String BHJB; // 保护级别：1、2、3

    private String CITES; // CITES：附录1、2

    private String IUCN; // IUCN：LC,EN,VU,NT

    private String ZRBH; // 中日双边保护：T,F

    private String ZABH; // 中澳双边保护：T,F

    private String SHX; // 生活型：林,水

    private String STX; // 生态型：猛、游、涉、鸣、走、攀

    private String JLX; // 居留型：夏、冬、留、旅、谜

    private String QSX; // 取食型：食草、食虫、食鱼、杂食

    private String BZ; // 1%标准

    private String ZYSBTZ; // 主要识别特征

    private String TPDZ; // 图片地址

    private String SPDZ; // 鸟叫声音地址

    private String CREATEID; // 创建人ID

    private String CREATENAME; // 创建人名称

    private String CREATEDATE; // 创建日期

    public String getATID() {
        return ATID;
    }

    public void setATID(String ATID) {
        this.ATID = ATID;
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

    public String getLDNAME() {
        return LDNAME;
    }

    public void setLDNAME(String LDNAME) {
        this.LDNAME = LDNAME;
    }

    public String getENNAME() {
        return ENNAME;
    }

    public void setENNAME(String ENNAME) {
        this.ENNAME = ENNAME;
    }

    public String getPINYIN() {
        return PINYIN;
    }

    public void setPINYIN(String PINYIN) {
        this.PINYIN = PINYIN;
    }

    public String getBNAME() {
        return BNAME;
    }

    public void setBNAME(String BNAME) {
        this.BNAME = BNAME;
    }

    public String getBHJB() {
        return BHJB;
    }

    public void setBHJB(String BHJB) {
        this.BHJB = BHJB;
    }

    public String getCITES() {
        return CITES;
    }

    public void setCITES(String CITES) {
        this.CITES = CITES;
    }

    public String getIUCN() {
        return IUCN;
    }

    public void setIUCN(String IUCN) {
        this.IUCN = IUCN;
    }

    public String getZRBH() {
        return ZRBH;
    }

    public void setZRBH(String ZRBH) {
        this.ZRBH = ZRBH;
    }

    public String getZABH() {
        return ZABH;
    }

    public void setZABH(String ZABH) {
        this.ZABH = ZABH;
    }

    public String getSHX() {
        return SHX;
    }

    public void setSHX(String SHX) {
        this.SHX = SHX;
    }

    public String getSTX() {
        return STX;
    }

    public void setSTX(String STX) {
        this.STX = STX;
    }

    public String getJLX() {
        return JLX;
    }

    public void setJLX(String JLX) {
        this.JLX = JLX;
    }

    public String getQSX() {
        return QSX;
    }

    public void setQSX(String QSX) {
        this.QSX = QSX;
    }

    public String getBZ() {
        return BZ;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }

    public String getZYSBTZ() {
        return ZYSBTZ;
    }

    public void setZYSBTZ(String ZYSBTZ) {
        this.ZYSBTZ = ZYSBTZ;
    }

    public String getTPDZ() {
        return TPDZ;
    }

    public void setTPDZ(String TPDZ) {
        this.TPDZ = TPDZ;
    }

    public String getSPDZ() {
        return SPDZ;
    }

    public void setSPDZ(String SPDZ) {
        this.SPDZ = SPDZ;
    }

    public String getCREATEID() {
        return CREATEID;
    }

    public void setCREATEID(String CREATEID) {
        this.CREATEID = CREATEID;
    }

    public String getCREATENAME() {
        return CREATENAME;
    }

    public void setCREATENAME(String CREATENAME) {
        this.CREATENAME = CREATENAME;
    }

    public String getCREATEDATE() {
        return CREATEDATE;
    }

    public void setCREATEDATE(String CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }
}
