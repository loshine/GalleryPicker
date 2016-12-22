package me.loshine.gallerypicker.imageloader;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 描    述：图片加载库类型
 * 作    者：longs@13322.com
 * 时    间：2016/12/22
 */
public interface ImageLoaderType {

    int PICASSO = 1;
    int GLIDE = 2;

    @IntDef({PICASSO, GLIDE})
    @Retention(RetentionPolicy.SOURCE)
    @interface ImageLoader {
    }
}
