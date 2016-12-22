package me.loshine.gallerypicker.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * 描    述：图片加载器接口
 * 作    者：longs@13322.com
 * 时    间：2016/12/22
 */
public interface ImageLoader {

    void display(Context context,
                 String path,
                 ImageView imageView,
                 Drawable defaultDrawable,
                 Bitmap.Config config,
                 boolean resize,
                 int width,
                 int height,
                 int rotate);

    void displayCenterCrop(Context context,
                           String path,
                           ImageView imageView,
                           Drawable defaultDrawable,
                           Bitmap.Config config,
                           boolean resize,
                           int width,
                           int height,
                           int rotate);
}