package me.loshine.gallerypicker.ui.media;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import me.loshine.gallerypicker.Configuration;
import me.loshine.gallerypicker.GalleryPicker;
import me.loshine.gallerypicker.R;
import me.loshine.gallerypicker.base.BaseRecyclerViewAdapter;
import me.loshine.gallerypicker.entity.MediaFile;
import me.loshine.gallerypicker.imageloader.ImageLoader;
import me.loshine.gallerypicker.utils.DisplayUtils;

/**
 * 描    述：
 * 作    者：loshine1992@gmail.com
 * 时    间：2016/12/22
 */
public class MediaAdapter extends BaseRecyclerViewAdapter<MediaAdapter.ViewHolder> {

    private List<MediaFile> mItems;
    private ColorDrawable mDefaultImage;
    private int mImageSize;
    private Configuration mConfiguration;

    private OnCheckedChangedListener mOnCheckedChangedListener;

    public void setOnCheckedChangedListener(OnCheckedChangedListener onCheckedChangedListener) {
        mOnCheckedChangedListener = onCheckedChangedListener;
    }

    public MediaAdapter(@NonNull List<MediaFile> files, Configuration configuration) {
        mItems = files;
        mConfiguration = configuration;
    }

    @Override
    public ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.gallery_picker_media_grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        maybeInit();
        MediaFile mediaFile = mItems.get(position);
        holder.bind(mediaFile);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private void maybeInit() {
        if (mDefaultImage == null) {
            int color = ContextCompat.getColor(mContext, android.R.color.darker_gray);
            mDefaultImage = new ColorDrawable(color);
        }
        if (mImageSize == 0) {
            mImageSize = DisplayUtils.getScreenWidth(mContext) / 3;
        }
    }

    public int getCheckedSize() {
        int size = 0;
        for (MediaFile item : mItems) {
            if (item.isChecked()) size++;
        }
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        CheckBox mCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            if (mConfiguration.isRadio()) {
                mCheckBox.setVisibility(View.GONE);
            } else {
                mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // XRecyclerView 的特殊原因需要 -1
                        MediaFile mediaFile = mItems.get(getAdapterPosition() - 1);
                        if (getCheckedSize() == mConfiguration.getMaxSize() && isChecked && !mediaFile.isChecked()) {
                            mCheckBox.setChecked(false);
                            Toast.makeText(mContext, "最多只能选择" + mConfiguration.getMaxSize() + "个", Toast.LENGTH_SHORT).show();
                        } else {
                            mediaFile.setChecked(isChecked);
                        }
                        if (mOnCheckedChangedListener != null) {
                            mOnCheckedChangedListener.onCheckedChanged(buttonView, isChecked, mediaFile);
                        }
                    }
                });
            }
        }

        public void bind(final MediaFile mediaFile) {
            ImageLoader imageLoader = GalleryPicker.INSTANCE.getImageLoader();
            imageLoader.displayCenterCrop(mContext, mediaFile.getOriginalPath(), mImageView, mDefaultImage,
                    GalleryPicker.INSTANCE.getImageConfig(),
                    true, mImageSize, mImageSize);
            mCheckBox.setChecked(mediaFile.isChecked());
        }
    }

    public interface OnCheckedChangedListener {
        void onCheckedChanged(CompoundButton buttonView, boolean isChecked, MediaFile mediaFile);
    }
}
