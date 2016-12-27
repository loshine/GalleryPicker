package me.loshine.gallerypicker.ui.media;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.loshine.gallerypicker.Configuration;
import me.loshine.gallerypicker.R;
import me.loshine.gallerypicker.anim.Animation;
import me.loshine.gallerypicker.anim.AnimationListener;
import me.loshine.gallerypicker.anim.SlideInUnderneathAnimation;
import me.loshine.gallerypicker.anim.SlideOutUnderneathAnimation;
import me.loshine.gallerypicker.base.BaseFragment;
import me.loshine.gallerypicker.base.BaseRecyclerViewAdapter;
import me.loshine.gallerypicker.entity.MediaBucket;
import me.loshine.gallerypicker.entity.MediaFile;
import me.loshine.gallerypicker.ui.preview.PreviewActivity;

import static me.loshine.gallerypicker.entity.MediaFile.EXTRA_KEY_MEDIA_FILE;

/**
 * 描    述：
 * 作    者：loshine1992@gmail.com
 * 时    间：2016/12/22
 */
public class MediaFragment extends BaseFragment
        implements MediaContract.View, View.OnClickListener,
        BucketAdapter.OnItemCheckedListener, XRecyclerView.LoadingListener {

    private static final String CONFIGURATION = "configuration";

    XRecyclerView mRecyclerView;
    RecyclerView mBucketRecyclerView;
    TextView mBucketNameTextView;
    TextView mPreviewTextView;
    View mBucketOverview;

    MediaAdapter mAdapter;

    BucketAdapter mBucketAdapter;

    private MediaContract.Presenter mPresenter;
    private boolean isAnimating;
    private Configuration mConfiguration;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mConfiguration = args.getParcelable(CONFIGURATION);
        }
        mPresenter = new MediaPresenter(this, mConfiguration);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gallery_picker_fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (XRecyclerView) view.findViewById(R.id.recycler_view);
        mBucketRecyclerView = (RecyclerView) view.findViewById(R.id.bucket_recycler_view);
        mBucketNameTextView = (TextView) view.findViewById(R.id.bucket_name);
        mPreviewTextView = (TextView) view.findViewById(R.id.preview);
        mBucketOverview = view.findViewById(R.id.bucket_overview);

        // item 高度确定的情况下可以提升性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setLoadingListener(this);
        mAdapter = new MediaAdapter(mPresenter.getItems(), mConfiguration);
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                List<MediaFile> items = mPresenter.getItems();
                if (mConfiguration.isRadio()) {
                    FragmentActivity activity = getActivity();
                    Intent intent = activity.getIntent();
                    MediaFile mediaFile = items.get(position - 1);
                    intent.putExtra(EXTRA_KEY_MEDIA_FILE, mediaFile);
                    activity.onBackPressed();
                } else {
                    PreviewActivity.start(getContext(), new ArrayList<>(items), position - 1);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mBucketRecyclerView.setHasFixedSize(true);
        mBucketRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBucketRecyclerView.setItemAnimator(null);
        mBucketAdapter = new BucketAdapter(mPresenter.getBucketList());
        mBucketAdapter.setOnItemCheckedListener(this);
        mBucketRecyclerView.setAdapter(mBucketAdapter);

        mPreviewTextView.setVisibility(mConfiguration.isRadio() ? View.GONE : View.VISIBLE);
        mBucketNameTextView.setText(mConfiguration.isImage() ?
                R.string.gallery_all_image : R.string.gallery_all_video);
        mBucketNameTextView.setOnClickListener(this);
        mBucketOverview.setOnClickListener(this);

        // 初始状态先滑入底部
        new SlideInUnderneathAnimation(mBucketRecyclerView)
                .setDirection(Animation.DIRECTION_DOWN)
                .animate();

        mPresenter.load(true);
    }

    @Override
    public boolean onBackPressed() {
        boolean isShown = mBucketOverview.isShown();
        if (isShown) {
            animateBuckets(true);
        }
        return isShown;
    }

    @Override
    public void onLoadComplete(int size) {
        mAdapter.notifyItemChanged(size - 1);
        mRecyclerView.loadMoreComplete();
    }

    @Override
    public void onLoadBucketsComplete() {
        mBucketAdapter.notifyDataSetChanged();
    }

    @Override
    public void onReloadComplete() {
        mRecyclerView.scrollToPosition(0);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.loadMoreComplete();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bucket_name) {
            animateBuckets(mBucketOverview.isShown());
        } else if (id == R.id.bucket_overview) {
            onBackPressed();
        }
    }

    private void animateBuckets(boolean isShown) {
        mBucketNameTextView.setEnabled(false);
        if (isAnimating) return;
        isAnimating = true;
        if (isShown) {
            new SlideOutUnderneathAnimation(mBucketRecyclerView)
                    .setDirection(Animation.DIRECTION_DOWN)
                    .setDuration(Animation.DURATION_DEFAULT)
                    .setListener(new AnimationListener() {
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mBucketNameTextView.setEnabled(true);
                            mBucketOverview.setVisibility(View.GONE);
                            isAnimating = false;
                        }
                    })
                    .animate();
        } else {
            mBucketOverview.setVisibility(View.VISIBLE);
            new SlideInUnderneathAnimation(mBucketRecyclerView)
                    .setDirection(Animation.DIRECTION_DOWN)
                    .setDuration(Animation.DURATION_DEFAULT)
                    .setListener(new AnimationListener() {
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mBucketNameTextView.setEnabled(true);
                            isAnimating = false;
                        }
                    })
                    .animate();
        }
    }

    @Override
    public void onItemChecked(int position, MediaBucket bucket) {
        bucket.setChecked(true);
        mBucketAdapter.notifyItemChanged(position);
        mBucketNameTextView.setText(bucket.getBucketName());
        mBucketNameTextView.setEnabled(false);
        mPresenter.reloadMediaList(bucket);
        if (mBucketOverview.isShown()) {
            new SlideOutUnderneathAnimation(mBucketRecyclerView)
                    .setDirection(Animation.DIRECTION_DOWN)
                    .setDuration(Animation.DURATION_SHORT)
                    .setListener(new AnimationListener() {
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mBucketNameTextView.setEnabled(true);
                            mBucketOverview.setVisibility(View.GONE);
                        }
                    })
                    .animate();
        }
    }

    @Override
    public void onRefresh() {
        // 不需要实现
    }

    @Override
    public void onLoadMore() {
        mPresenter.load(false);
    }

    /**
     * Builder 模式构造 MediaFragment
     */
    public static class Builder {

        Configuration mConfiguration = new Configuration();

        public Builder image() {
            mConfiguration.setImage();
            return this;
        }

        public Builder video() {
            mConfiguration.setVideo();
            return this;
        }

        public Builder radio() {
            mConfiguration.setRadio(true);
            return this;
        }

        public Builder multiple() {
            mConfiguration.setRadio(false);
            return this;
        }

        public Builder maxSize(@IntRange(from = 1) int maxSize) {
            mConfiguration.setMaxSize(maxSize);
            return this;
        }

        public Builder hideCamera() {
            mConfiguration.setHideCamera(true);
            return this;
        }

        public MediaFragment build() {
            Bundle args = new Bundle();
            args.putParcelable(CONFIGURATION, mConfiguration);
            MediaFragment fragment = new MediaFragment();
            fragment.setArguments(args);
            return fragment;
        }
    }
}
