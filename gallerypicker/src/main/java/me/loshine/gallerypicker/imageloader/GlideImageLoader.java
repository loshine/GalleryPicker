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
 * 作    者：longs@13322.com
 * 时    间：2016/12/22
 */
public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Context context, String path, ImageView imageView, Drawable defaultDrawable,
                             Bitmap.Config config, boolean resize, int width, int height, int rotate) {
        DrawableRequestBuilder builder = Glide.with(context)
                .load(new File(path))
                .placeholder(defaultDrawable);
        if (resize) {
            builder = builder.override(width, height);
        }
        builder.crossFade()
                .transform(new RotateTransformation(context, rotate))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }
}
