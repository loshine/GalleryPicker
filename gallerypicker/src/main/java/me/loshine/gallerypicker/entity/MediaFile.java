package me.loshine.gallerypicker.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 描    述：
 * 作    者：loshine1992@gmail.com
 * 时    间：2016/12/22
 */
public class MediaFile implements Parcelable {

    //图片ID
    private long id;

    private String title;
    //图片、视频源地址
    private String originalPath;
    //图片、视频创建时间
    private long createDate;
    //图片、视频最后修改时间
    private long modifiedDate;
    //媒体类型
    private String mimeType;
    //宽
    private int width;
    //高
    private int height;
    //纬度
    private double latitude;
    //经度
    private double longitude;
    //图片方向
    private int orientation;
    //文件大小
    private long length;

    //文件夹相关
    private String bucketId;
    private String bucketDisplayName;

    // 是否已被选中
    private boolean isChecked;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public String getBucketDisplayName() {
        return bucketDisplayName;
    }

    public void setBucketDisplayName(String bucketDisplayName) {
        this.bucketDisplayName = bucketDisplayName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaFile mediaFile = (MediaFile) o;

        return id == mediaFile.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "MediaFile{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", originalPath='" + originalPath + '\'' +
                ", createDate=" + createDate +
                ", modifiedDate=" + modifiedDate +
                ", mimeType='" + mimeType + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", orientation=" + orientation +
                ", length=" + length +
                ", bucketId='" + bucketId + '\'' +
                ", bucketDisplayName='" + bucketDisplayName + '\'' +
                '}';
    }

    public MediaFile() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.originalPath);
        dest.writeLong(this.createDate);
        dest.writeLong(this.modifiedDate);
        dest.writeString(this.mimeType);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeInt(this.orientation);
        dest.writeLong(this.length);
        dest.writeString(this.bucketId);
        dest.writeString(this.bucketDisplayName);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    protected MediaFile(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.originalPath = in.readString();
        this.createDate = in.readLong();
        this.modifiedDate = in.readLong();
        this.mimeType = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.orientation = in.readInt();
        this.length = in.readLong();
        this.bucketId = in.readString();
        this.bucketDisplayName = in.readString();
        this.isChecked = in.readByte() != 0;
    }

    public static final Creator<MediaFile> CREATOR = new Creator<MediaFile>() {
        @Override
        public MediaFile createFromParcel(Parcel source) {
            return new MediaFile(source);
        }

        @Override
        public MediaFile[] newArray(int size) {
            return new MediaFile[size];
        }
    };
}
