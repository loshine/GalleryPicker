package me.loshine.gallerypicker.ui.preview;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.loshine.gallerypicker.GalleryPicker;
import me.loshine.gallerypicker.R;
import me.loshine.gallerypicker.base.BaseFragment;
import me.loshine.gallerypicker.entity.MediaFile;
import uk.co.senab.photoview.PhotoView;

/**
 * 描    述：
 * 作    者：longs@13322.com
 * 时    间：2016/12/23
 */
public class PreviewFragment extends BaseFragment {

    private static final String MEDIA_FILE = "media_file";

    PhotoView mPhotoView;

    private MediaFile mMediaFile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mMediaFile = args.getParcelable(MEDIA_FILE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gallery_picker_fragment_preview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPhotoView = (PhotoView) view.findViewById(R.id.photo_view);

        int color = ContextCompat.getColor(getContext(), android.R.color.darker_gray);
        ColorDrawable drawable = new ColorDrawable(color);
        GalleryPicker.INSTANCE.getImageLoader()
                .display(getContext(), mMediaFile.getOriginalPath(), mPhotoView, drawable,
                        GalleryPicker.INSTANCE.getImageConfig(),
                        false, 0, 0);
    }

    public static PreviewFragment newInstance(@NonNull MediaFile file) {

        Bundle args = new Bundle();
        args.putParcelable(MEDIA_FILE, file);
        PreviewFragment fragment = new PreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
