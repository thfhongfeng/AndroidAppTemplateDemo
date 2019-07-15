package com.pine.mvp.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.pine.mvp.R;
import com.pine.tool.util.AppUtils;
import com.pine.tool.util.DecimalUtils;

/**
 * Created by tanghongfeng on 2018/9/28
 */

public class MvpShopItemEntity implements Parcelable {

    /**
     * id :
     * name :
     * distance :
     * mainImgUrl :
     * createTime :
     * updateTime :
     */

    private String id;
    private String name;
    private String distance;
    private String formatDistance;
    private String mainImgUrl;
    private String createTime;
    private String updateTime;

    protected MvpShopItemEntity(Parcel in) {
        id = in.readString();
        name = in.readString();
        distance = in.readString();
        formatDistance = in.readString();
        mainImgUrl = in.readString();
        createTime = in.readString();
        updateTime = in.readString();
    }

    public static final Creator<MvpShopItemEntity> CREATOR = new Creator<MvpShopItemEntity>() {
        @Override
        public MvpShopItemEntity createFromParcel(Parcel in) {
            return new MvpShopItemEntity(in);
        }

        @Override
        public MvpShopItemEntity[] newArray(int size) {
            return new MvpShopItemEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(distance);
        dest.writeString(formatDistance);
        dest.writeString(mainImgUrl);
        dest.writeString(createTime);
        dest.writeString(updateTime);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
        if (!TextUtils.isEmpty(distance)) {
            float distanceF = Float.parseFloat(distance);
            if (distanceF >= 1000.0f) {
                formatDistance = DecimalUtils.divide(distanceF, 1000.0f, 2) +
                        AppUtils.getApplication().getString(R.string.unit_kilometre);
            } else {
                formatDistance = distance.split("\\.")[0] + AppUtils.getApplication().getString(R.string.unit_metre);
            }
        }
    }

    public String getMainImgUrl() {
        return mainImgUrl;
    }

    public void setMainImgUrl(String mainImgUrl) {
        this.mainImgUrl = mainImgUrl;
    }

    public String getFormatDistance() {
        if (!TextUtils.isEmpty(distance)) {
            float distanceF = Float.parseFloat(distance);
            if (distanceF >= 1000.0f) {
                formatDistance = DecimalUtils.divide(distanceF, 1000.0f, 2) +
                        AppUtils.getApplication().getString(R.string.unit_kilometre);
            } else {
                formatDistance = distance.split("\\.")[0] + AppUtils.getApplication().getString(R.string.unit_metre);
            }
        } else {
            formatDistance = "";
        }
        return this.formatDistance;
    }

    public void setFormatDistance(String formatDistance) {
        this.formatDistance = formatDistance;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
