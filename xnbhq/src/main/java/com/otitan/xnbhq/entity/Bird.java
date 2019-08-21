package com.otitan.xnbhq.entity;

/**
 * Created by sp on 2019/3/9.
 * 鸟
 */
public class Bird {

    /*{
        "ID":"38",
        "ATID":"31",
        "CODE":"0501030004",
        "NAME":"红胸黑雁",
        "LDNAME":"Branta ruficollis",
        "ENNAME":"Red-breasted Goose",
        "PINYIN":"",
        "BNAME":"",
        "BHJB":"2",
        "CITES":"2",
        "IUCN":"EN",
        "ZRBH":"F",
        "ZABH":"F",
        "SHX":"水",
        "STX":"游",
        "JLX":"冬",
        "QSX":"食草",
        "BZ":"",
        "ZYSBTZ":"全长55厘米左右。体羽有金属光泽。头、后颈黑褐色；两侧眼和嘴之间有一椭圆形白斑。",
        "TPDZ":"",
        "SPDZ":""
    }*/

    private String ID; //

    private String ATID; //

    private String CODE; // 种编码

    private String NAME; // 中文名称

    private String LDNAME; // 拉丁名称

    private String ENNAME; // 英文名称

    private String PINYIN; // 拼音检索

    private String BNAME; // 别名

    private String BHJB; // 保护级别

    private String CITES; // CITES

    private String IUCN; // IUCN

    private String ZRBH; // 中日双边保护

    private String ZABH; // 中澳双边保护

    private String SHX; // 生活型

    private String STX; // 生态型

    private String JLX; // 居留型

    private String QSX; // 取食型

    private String BZ; //

    private String ZYSBTZ; //主要识别特征

    private String TPDZ; // 图片

    private String SPDZ; // 视频

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

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
}
