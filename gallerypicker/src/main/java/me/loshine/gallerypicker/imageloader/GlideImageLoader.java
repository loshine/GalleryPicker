package me.loshine.gallerypicker.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

/**
 * 描    述：
 * 作    者：loshine1992@gmail.com
 * 时    间：2016/12/22
 */
public class GlideImageLoader implements ImageLoader {

    @Override
    public void display(Context context, String path, ImageView imageView, Drawable defaultDrawable,
                        Bitmap.Config config, boolean resize, int width, int height) {
        DrawableRequestBuilder builder = Glide.with(context)
                .load(new File(path))
                .placeholder(defaultDrawable);
        if (resize) {
            builder = builder.override(width, height);
        }
        builder.crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    @Override
    public void displayCenterCrop(Context context, String path, ImageView imageView, Drawable defaultDrawable,
                                  Bitmap.Config config, boolean resize, int width, int height) {
        DrawableRequestBuilder builder = Glide.with(context)
                .load(new File(path))
                .placeholder(defaultDrawable);
        if (resize) {
            builder = builder.override(width, height);
        }
        builder.crossFade()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }
}
