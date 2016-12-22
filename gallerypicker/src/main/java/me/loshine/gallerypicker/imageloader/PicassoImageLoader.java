package me.loshine.gallerypicker.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

/**
 * 描    述：Picasso 图片加载器
 * 作    者：longs@13322.com
 * 时    间：2016/12/22
 */
public class PicassoImageLoader implements ImageLoader {

    @Override
    public void display(Context context, String path, ImageView imageView, Drawable defaultDrawable,
                        Bitmap.Config config, boolean resize, int width, int height, int rotate) {
        RequestCreator creator = Picasso.with(context)
                .load(new File(path))
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .rotate(rotate)
                .networkPolicy(NetworkPolicy.NO_STORE)
                .config(config)
                .tag(context);
        if (resize) {
            creator = creator.resize(width, height);
        }
        creator.into(imageView);
    }

    @Override
    public void displayCenterCrop(Context context, String path, ImageView imageView, Drawable defaultDrawable,
                                  Bitmap.Config config, boolean resize, int width, int height, int rotate) {
        RequestCreator creator = Picasso.with(context)
                .load(new File(path))
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .rotate(rotate)
                .networkPolicy(NetworkPolicy.NO_STORE)
                .config(config)
                .tag(context);
        if (resize) {
            creator = creator.resize(width, height);
        }
        creator.centerCrop();
        creator.into(imageView);
    }
}
