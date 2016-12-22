package me.loshine.gallerypicker;

import android.graphics.Bitmap;

import me.loshine.gallerypicker.imageloader.GlideImageLoader;
import me.loshine.gallerypicker.imageloader.ImageLoader;
import me.loshine.gallerypicker.imageloader.ImageLoaderType;
import me.loshine.gallerypicker.imageloader.PicassoImageLoader;

/**
 * 描    述：GalleryPicker 单例，使用枚举实现
 * 作    者：longs@13322.com
 * 时    间：2016/12/22
 */
public enum GalleryPicker {

    INSTANCE;

    @ImageLoaderType.ImageLoader
    private int imageLoaderType = ImageLoaderType.PICASSO; // 默认使用 picasso 加载
    private int imageConfig;

    private ImageLoader mImageLoader;

    public void init(@ImageLoaderType.ImageLoader int imageLoaderType) {
        this.imageLoaderType = imageLoaderType;
    }

    public void init(@ImageLoaderType.ImageLoader int imageLoaderType, int imageConfig) {
        this.imageLoaderType = imageLoaderType;
        this.imageConfig = imageConfig;
    }

    @ImageLoaderType.ImageLoader
    public int getImageLoaderType() {
        return imageLoaderType;
    }

    /**
     * 获取 ImageLoader
     *
     * @return imageLoader
     */
    public ImageLoader getImageLoader() {

        if (mImageLoader == null) {
            switch (imageLoaderType) {
                case ImageLoaderType.GLIDE:
                    mImageLoader = new GlideImageLoader();
                    break;
                case ImageLoaderType.PICASSO:
                default:
                    mImageLoader = new PicassoImageLoader();
                    break;
            }
        }
        return mImageLoader;
    }

    public Bitmap.Config getImageConfig() {
        switch (imageConfig) {
            case 1:
                return Bitmap.Config.ALPHA_8;
            case 2:
                return Bitmap.Config.ARGB_4444;
            case 3:
                return Bitmap.Config.ARGB_8888;
            case 4:
                return Bitmap.Config.RGB_565;
        }
        return Bitmap.Config.ARGB_8888;
    }
}
