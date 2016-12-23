package me.loshine.gallerypicker.ui.preview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import me.loshine.gallerypicker.R;
import me.loshine.gallerypicker.entity.MediaFile;

/**
 * 描    述：
 * 作    者：loshine1992@gmail.com
 * 时    间：2016/12/23
 */
public class PreviewActivity extends AppCompatActivity {

    private static final String MEDIA_FILE_LIST = "media_file_list";
    private static final String MEDIA_POSITION = "media_position";

    AppBarLayout mAppBarLayout;
    Toolbar mToolbar;
    ViewPager mViewPager;

    private ArrayList<MediaFile> mMediaFiles;
    private int mPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_picker_activity_preview);

        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        Intent intent = getIntent();
        mMediaFiles = intent.getParcelableArrayListExtra(MEDIA_FILE_LIST);
        mPosition = intent.getIntExtra(MEDIA_POSITION, 0);

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                MediaFile mediaFile = mMediaFiles.get(position);
                return PreviewFragment.newInstance(mediaFile);
            }

            @Override
            public int getCount() {
                return mMediaFiles.size();
            }
        });

        mViewPager.setCurrentItem(mPosition, false);
    }

    public static void start(Context context, @NonNull ArrayList<MediaFile> mediaFiles, int position) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putParcelableArrayListExtra(MEDIA_FILE_LIST, mediaFiles);
        intent.putExtra(MEDIA_POSITION, position);
        context.startActivity(intent);
    }
}
