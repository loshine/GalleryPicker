package me.loshine.gallerypicker.ui.gallery;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import me.loshine.gallerypicker.GalleryPicker;
import me.loshine.gallerypicker.R;
import me.loshine.gallerypicker.base.BaseRecyclerViewAdapter;
import me.loshine.gallerypicker.entity.MediaBucket;

/**
 * 描    述：
 * 作    者：longs@13322.com
 * 时    间：2016/12/22
 */
public class BucketAdapter extends BaseRecyclerViewAdapter<BucketAdapter.ViewHolder> {

    private List<MediaBucket> mBuckets;
    private Drawable mDefaultImage;

    public BucketAdapter(List<MediaBucket> buckets) {
        mBuckets = buckets;
    }

    @Override
    public ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.gallery_picker_bucket_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        maybeInitDrawable();
        holder.bind(mBuckets.get(position));
    }

    private void maybeInitDrawable() {
        if (mDefaultImage == null) {
            int color = ContextCompat.getColor(mContext, android.R.color.darker_gray);
            mDefaultImage = new ColorDrawable(color);
        }
    }

    @Override
    public int getItemCount() {
        return mBuckets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView mTextView;
        RadioButton mRadioButton;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.bucket_cover);
            mTextView = (TextView) itemView.findViewById(R.id.bucket_name);
            mRadioButton = (RadioButton) itemView.findViewById(R.id.radio_button);
        }

        public void bind(MediaBucket bucket) {
            String path = bucket.getCover();
            GalleryPicker.INSTANCE.getImageLoader()
                    .displayImage(mContext, path, mImageView, mDefaultImage,
                            GalleryPicker.INSTANCE.getImageConfig(),
                            true, 100, 100, bucket.getOrientation());
            mTextView.setText(bucket.getBucketName());
            mRadioButton.setVisibility(bucket.isChecked() ? View.VISIBLE : View.GONE);
        }
    }
}
