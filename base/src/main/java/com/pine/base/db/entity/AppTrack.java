package com.pine.base.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "db_app_track")
public class AppTrack {
    // id
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "_id")
    private long id;

    // 埋点所属模块
    @NonNull
    private String moduleTag;

    // 用户id
    private String accountId;

    @NonNull
    private int accountType;

    // 用户名
    private String userName;

    // 埋点类型：0-点击事件；1-页面
    @NonNull
    private int trackType;

    // 业务title
    @ColumnInfo
    private String title;

    // 当前class名
    @NonNull
    private String curClass;

    // 前一个class名
    private String preClass;

    // 按键名称（对应埋点点击事件类型）
    private String buttonName;

    @ColumnInfo(name = "ip")
    private String ip;

    // 进入页面/点击button 时间
    private long timeInStamp;

    // 离开页面时间（对应埋点页面类型）
    private long timeOutStamp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getModuleTag() {
        return moduleTag;
    }

    public void setModuleTag(@NonNull String moduleTag) {
        this.moduleTag = moduleTag;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTrackType() {
        return trackType;
    }

    public void setTrackType(int trackType) {
        this.trackType = trackType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    public String getCurClass() {
        return curClass;
    }

    public void setCurClass(@NonNull String curClass) {
        this.curClass = curClass;
    }

    public String getPreClass() {
        return preClass;
    }

    public void setPreClass(String preClass) {
        this.preClass = preClass;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getTimeInStamp() {
        return timeInStamp;
    }

    public void setTimeInStamp(long timeInStamp) {
        this.timeInStamp = timeInStamp;
    }

    public long getTimeOutStamp() {
        return timeOutStamp;
    }

    public void setTimeOutStamp(long timeOutStamp) {
        this.timeOutStamp = timeOutStamp;
    }

    @Override
    public String toString() {
        return "{moduleTag:" + moduleTag + ",accountId:" + accountId +
                ",accountType:" + accountType + ",userName:" + userName +
                ",trackType:" + trackType + ",title:" + title +
                ",curClass:" + curClass + ",preClass:" + preClass + ",buttonName:" + buttonName +
                ",ip:" + ip + ",timeInStamp:" + timeInStamp +
                ",timeOutStamp:" + timeOutStamp + "}";
    }
}
