package me.loshine.gallerypicker.ui.gallery;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import java.util.List;

import me.loshine.gallerypicker.R;
import me.loshine.gallerypicker.base.BaseRecyclerViewAdapter;
import me.loshine.gallerypicker.entity.MediaFile;

/**
 * 描    述：
 * 作    者：longs@13322.com
 * 时    间：2016/12/22
 */
public class GalleryAdapter extends BaseRecyclerViewAdapter<GalleryAdapter.ViewHolder> {

    private List<MediaFile> mItems;

    public GalleryAdapter(@NonNull List<MediaFile> files) {
        mItems = files;
    }

    @Override
    public ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.galley_picker_media_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MediaFile mediaFile = mItems.get(position);
        holder.bind(mediaFile);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        CheckBox mCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    MediaFile mediaFile = mItems.get(getAdapterPosition());
                    mediaFile.setChecked(isChecked);
                }
            });
        }

        public void bind(MediaFile mediaFile) {
            mCheckBox.setChecked(mediaFile.isChecked());
        }
    }
}
