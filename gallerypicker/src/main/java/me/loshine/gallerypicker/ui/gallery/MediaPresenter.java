package me.loshine.gallerypicker.ui.gallery;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import me.loshine.gallerypicker.entity.MediaBucket;
import me.loshine.gallerypicker.entity.MediaFile;
import me.loshine.gallerypicker.model.MediaModel;
import me.loshine.gallerypicker.model.MediaRepository;

/**
 * 描    述：
 * 作    者：longs@13322.com
 * 时    间：2016/12/22
 */
public class MediaPresenter implements MediaContract.Presenter {

    private MediaContract.View mView;
    private Context mContext;
    private List<MediaFile> mItems;
    private List<MediaBucket> mBuckets;
    private MediaBucket mMediaBucket;

    private MediaModel mModel;

    public MediaPresenter(MediaFragment view) {
        mView = view;
        mContext = view.getContext();
        mItems = new ArrayList<>();
        mBuckets = new ArrayList<>();
        mModel = new MediaRepository(mContext);
    }

    @Override
    public List<MediaFile> getItems() {
        return mItems;
    }

    @Override
    public List<MediaBucket> getBucketList() {
        return mBuckets;
    }

    @Override
    public void load() {
        mBuckets.addAll(mModel.getImageBucket());
        mMediaBucket = mBuckets.get(0);
        mItems.addAll(mModel.getImageMediaList(mMediaBucket.getBucketId(), 1, 40));
        mView.onLoadComplete();
    }

    @Override
    public void reloadMediaList(MediaBucket mediaBucket) {
        mMediaBucket = mediaBucket;
        mItems.clear();
        mItems.addAll(mModel.getImageMediaList(mMediaBucket.getBucketId(), 1, 40));
        mView.onReloadComplete();
    }
}
