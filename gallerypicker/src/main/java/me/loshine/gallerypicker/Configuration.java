package me.loshine.gallerypicker;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 描    述：
 * 作    者：longs@13322.com
 * 时    间：2016/12/23
 */
public class Configuration implements Parcelable {

    public static final int MODE_IMAGE = 0;
    public static final int MODE_VIDEO = 1;

    // 默认选视频，多选，最大9个，不隐藏相机
    private int mMode = MODE_IMAGE; // 图片还是视频
    private boolean mRadio = false; // 单选还是多选
    private int mMaxSize = 9; // 最大选几个，只在多选模式下生效
    private boolean mHideCamera = false; // 是否隐藏相机

    public void setImage() {
        mMode = MODE_IMAGE;
    }

    public boolean isImage() {
        return mMode == MODE_IMAGE;
    }

    public void setVideo() {
        mMode = MODE_VIDEO;
    }

    public void setRadio(boolean isRadio) {
        mRadio = isRadio;
    }


    public void setMaxSize(int maxSize) {
        mMaxSize = maxSize;
    }

    public void setHideCamera(boolean hideCamera) {
        mHideCamera = hideCamera;
    }

    public int getMode() {
        return mMode;
    }

    public boolean isRadio() {
        return mRadio;
    }

    public int getMaxSize() {
        return mMaxSize;
    }

    public boolean isHideCamera() {
        return mHideCamera;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mMode);
        dest.writeByte(this.mRadio ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mMaxSize);
        dest.writeByte(this.mHideCamera ? (byte) 1 : (byte) 0);
    }

    public Configuration() {
    }

    protected Configuration(Parcel in) {
        this.mMode = in.readInt();
        this.mRadio = in.readByte() != 0;
        this.mMaxSize = in.readInt();
        this.mHideCamera = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Configuration> CREATOR = new Parcelable.Creator<Configuration>() {
        @Override
        public Configuration createFromParcel(Parcel source) {
            return new Configuration(source);
        }

        @Override
        public Configuration[] newArray(int size) {
            return new Configuration[size];
        }
    };
}