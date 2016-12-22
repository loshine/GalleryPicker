package me.loshine.gallerypicker.utils;

import android.content.Context;

import java.io.File;

/**
 * 描    述：
 * 作    者：longs@13322.com
 * 时    间：2016/12/22
 */
public class ThumbnailUtils {

    private ThumbnailUtils() {
    }

    public static File createThumbnailBigFileName(Context context, String originalPath) {
        File storeFile = StorageUtils.getCacheDirectory(context, true);
        return new File(storeFile, "big_" + FilenameUtils.getName(originalPath));
    }

    public static File createThumbnailSmallFileName(Context context, String originalPath) {
        File storeFile = StorageUtils.getCacheDirectory(context);
        return new File(storeFile, "small_" + FilenameUtils.getName(originalPath));
    }
}
