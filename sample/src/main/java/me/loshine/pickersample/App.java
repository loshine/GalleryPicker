package me.loshine.pickersample;

import android.app.Application;

import me.loshine.gallerypicker.GalleryPicker;
import me.loshine.gallerypicker.imageloader.ImageLoaderType;

/**
 * 描    述：
 * 作    者：loshine1992@gmail.com
 * 时    间：2016/12/22
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GalleryPicker.INSTANCE.init(ImageLoaderType.GLIDE);
    }
}
