package me.loshine.gallerypicker.ui.media;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import me.loshine.gallerypicker.Configuration;
import me.loshine.gallerypicker.entity.MediaBucket;
import me.loshine.gallerypicker.entity.MediaFile;
import me.loshine.gallerypicker.model.MediaModel;
import me.loshine.gallerypicker.model.MediaRepository;

/**
 * 描    述：
 * 作    者：loshine1992@gmail.com
 * 时    间：2016/12/22
 */
public class MediaPresenter implements MediaContract.Presenter {

    private MediaContract.View mView;
    private Context mContext;
    private List<MediaFile> mItems;
    private List<MediaBucket> mBuckets;
    private MediaBucket mMediaBucket;

    private int mPageIndex = 1;
    private int mPageSize = 40;

    private MediaModel mModel;
    private Configuration mConfiguration;

    public MediaPresenter(MediaFragment view, Configuration configuration) {
        mView = view;
        mContext = view.getContext();
        mItems = new ArrayList<>();
        mBuckets = new ArrayList<>();
        mModel = new MediaRepository(mContext);
        mConfiguration = configuration;
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
    public void load(boolean isFirst) {
        if (isFirst) {
            loadBuckets();
            mView.onLoadBucketsComplete();
        }
        mItems.addAll(getMediaList(mMediaBucket.getBucketId()));
        mPageIndex++;
        mView.onLoadComplete(mItems.size());
    }

    @Override
    public void reloadMediaList(MediaBucket mediaBucket) {
        mPageIndex = 1;
        mMediaBucket = mediaBucket;
        mItems.clear();
        mItems.addAll(getMediaList(mMediaBucket.getBucketId()));
        mPageIndex++;
        mView.onReloadComplete();
    }

    private void loadBuckets() {
        if (mConfiguration.getMode() == Configuration.MODE_IMAGE) {
            mBuckets.addAll(mModel.getImageBucket());
        } else {
            mBuckets.addAll(mModel.getVideoBucket());
        }
        mMediaBucket = mBuckets.get(0);
    }

    private List<MediaFile> getMediaList(String bucketId) {
        if (mConfiguration.getMode() == Configuration.MODE_IMAGE) {
            return mModel.getImageMediaList(bucketId, mPageIndex, mPageSize);
        } else {
            return mModel.getVideoMediaList(bucketId, mPageIndex, mPageSize);
        }
    }
}
