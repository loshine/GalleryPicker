package me.loshine.gallerypicker.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.loshine.gallerypicker.R;
import me.loshine.gallerypicker.entity.MediaBucket;
import me.loshine.gallerypicker.entity.MediaFile;
import me.loshine.gallerypicker.utils.ThumbnailUtils;

/**
 * 描    述：
 * 作    者：longs@13322.com
 * 时    间：2016/12/22
 */
public class MediaRepository implements MediaModel {

    private Context mContext;

    public MediaRepository(Context context) {
        mContext = context;
    }

    /**
     * 根据 bucketId 分页获取图片资源列表
     *
     * @param bucketId  bucketId
     * @param pageIndex 页码
     * @param pageSize  每页大小
     * @return List<MediaFile>
     */
    @Override
    public List<MediaFile> getImageMediaList(String bucketId, int pageIndex, int pageSize) {
        List<MediaFile> list = new ArrayList<>();
        int offset = (pageIndex - 1) * pageSize;

        String[] columns;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            columns = new String[]{
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.TITLE,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.BUCKET_ID,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Images.Media.MIME_TYPE,
                    MediaStore.Images.Media.DATE_ADDED,
                    MediaStore.Images.Media.DATE_MODIFIED,
                    MediaStore.Images.Media.LATITUDE,
                    MediaStore.Images.Media.LONGITUDE,
                    MediaStore.Images.Media.ORIENTATION,
                    MediaStore.Images.Media.SIZE,
                    MediaStore.Images.Media.WIDTH,
                    MediaStore.Images.Media.HEIGHT
            };
        } else {
            columns = new String[]{
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.TITLE,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.BUCKET_ID,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Images.Media.MIME_TYPE,
                    MediaStore.Images.Media.DATE_ADDED,
                    MediaStore.Images.Media.DATE_MODIFIED,
                    MediaStore.Images.Media.LATITUDE,
                    MediaStore.Images.Media.LONGITUDE,
                    MediaStore.Images.Media.ORIENTATION,
                    MediaStore.Images.Media.SIZE
            };
        }
/*        String selection = MediaStore.Images.Media.MIME_TYPE + "=? or " +
                MediaStore.Images.Media.MIME_TYPE + "=? or " +
                MediaStore.Images.Media.MIME_TYPE + "=?";
        String[] selectionArgs = {"image/jpeg", "image/png", "image/gif"};*/
        String selection = MediaStore.Images.Media.BUCKET_ID + "=?";
        String[] selectionArgs = {bucketId};
        String sortOrder = MediaStore.Images.Media.DATE_ADDED
                + " DESC LIMIT " + pageSize + " OFFSET " + offset;
        Cursor cursor = mContext.getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        columns, selection, selectionArgs, sortOrder);
        if (cursor != null) {
            int count = cursor.getCount();
            if (count > 0) {
                cursor.moveToFirst();
                do {
                    MediaFile mediaFile = parseImageMediaFileFromCursor(mContext, cursor);
                    list.add(mediaFile);
                } while (cursor.moveToNext());
            }
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        cursor = null;
        return list;
    }

    /**
     * 获取资源文件夹
     *
     * @param isImage 是否是图片
     * @return 文件夹列表
     */
    @Override
    public List<MediaBucket> getAllBucket(boolean isImage) {
        List<MediaBucket> bucketList = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        String[] projection;
        if (isImage) {
            projection = new String[]{
                    MediaStore.Images.Media.BUCKET_ID,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Images.Media.ORIENTATION,
            };
        } else {
            projection = new String[]{
                    MediaStore.Video.Media.BUCKET_ID,
                    MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            };
        }
        MediaBucket allMediaBucket = new MediaBucket();
        allMediaBucket.setBucketId(String.valueOf(Integer.MIN_VALUE));
        Uri uri;
        if (isImage) {
            allMediaBucket.setBucketName(mContext.getString(R.string.gallery_all_image));
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else {
            allMediaBucket.setBucketName(mContext.getString(R.string.gallery_all_video));
            uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        }
        bucketList.add(allMediaBucket);
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(uri, projection, null, null, MediaStore.Video.Media.DATE_ADDED + " DESC");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                MediaBucket bucketBean = new MediaBucket();
                String bucketId;
                String bucketKey;
                String cover;
                if (isImage) {
                    bucketId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
                    bucketBean.setBucketId(bucketId);
                    String bucketDisplayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                    bucketBean.setBucketName(bucketDisplayName);
                    bucketKey = MediaStore.Images.Media.BUCKET_ID;
                    cover = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    int orientation = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.ORIENTATION));
                    bucketBean.setOrientation(orientation);
                } else {
                    bucketId = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID));
                    bucketBean.setBucketId(bucketId);
                    String bucketDisplayName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));
                    bucketBean.setBucketName(bucketDisplayName);
                    bucketKey = MediaStore.Video.Media.BUCKET_ID;
                    cover = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                }
                if (TextUtils.isEmpty(allMediaBucket.getCover())) {
                    allMediaBucket.setCover(cover);
                }
                if (bucketList.contains(bucketBean)) {
                    continue;
                }
                //获取数量
                Cursor c = contentResolver.query(uri, projection, bucketKey + "=?", new String[]{bucketId}, null);
                if (c != null && c.getCount() > 0) {
                    bucketBean.setImageCount(c.getCount());
                }
                bucketBean.setCover(cover);
                if (c != null && !c.isClosed()) {
                    c.close();
                }
                c = null;
                bucketList.add(bucketBean);
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        cursor = null;
        return bucketList;
    }

    /**
     * 获取图片文件夹
     *
     * @return 文件夹列表
     */
    @Override
    public List<MediaBucket> getImageBucket() {
        return getAllBucket(true);
    }

    /**
     * 获取视频文件夹
     *
     * @return 文件夹列表
     */
    @Override
    public List<MediaBucket> getVideoBucket() {
        return getAllBucket(false);
    }

    /**
     * 从 Cursor 对象中转化 图片类型的 MediaFile 并创建缩略图
     *
     * @param context context
     * @param cursor  cursor
     * @return MediaFile
     */
    private MediaFile parseImageMediaFileFromCursor(Context context, Cursor cursor) {
        MediaFile mediaBean = new MediaFile();
        long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
        mediaBean.setId(id);
        String title = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE));
        mediaBean.setTitle(title);
        String originalPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        mediaBean.setOriginalPath(originalPath);
        String bucketId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
        mediaBean.setBucketId(bucketId);
        String bucketDisplayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
        mediaBean.setBucketDisplayName(bucketDisplayName);
        String mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));
        mediaBean.setMimeType(mimeType);
        long createDate = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
        mediaBean.setCreateDate(createDate);
        long modifiedDate = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED));
        mediaBean.setModifiedDate(modifiedDate);
        File thumbnailBig = ThumbnailUtils.createThumbnailBigFileName(context, originalPath);
        mediaBean.setThumbnailBigPath(thumbnailBig.getAbsolutePath());
        File thumbnailSmall = ThumbnailUtils.createThumbnailSmallFileName(context, originalPath);
        mediaBean.setThumbnailSmallPath(thumbnailSmall.getAbsolutePath());
        int width = 0, height = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            width = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.WIDTH));
            height = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.HEIGHT));
        } else {
            try {
                ExifInterface exifInterface = new ExifInterface(originalPath);
                width = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 0);
                height = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 0);
            } catch (IOException e) {
                Log.e("MediaModel", e.getLocalizedMessage());
            }
        }
        mediaBean.setWidth(width);
        mediaBean.setHeight(height);
        double latitude = cursor.getDouble(cursor.getColumnIndex(MediaStore.Images.Media.LATITUDE));
        mediaBean.setLatitude(latitude);
        double longitude = cursor.getDouble(cursor.getColumnIndex(MediaStore.Images.Media.LONGITUDE));
        mediaBean.setLongitude(longitude);
        int orientation = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.ORIENTATION));
        mediaBean.setOrientation(orientation);
        long length = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
        mediaBean.setLength(length);

        return mediaBean;
    }
}