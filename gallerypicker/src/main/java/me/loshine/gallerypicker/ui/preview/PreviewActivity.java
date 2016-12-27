package me.loshine.gallerypicker.ui.preview;

import android.app.Activity;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

import me.loshine.gallerypicker.R;
import me.loshine.gallerypicker.entity.MediaFile;

import static me.loshine.gallerypicker.Constants.ID_LIST;
import static me.loshine.gallerypicker.Constants.PREVIEW_SELECT_REQUEST;

/**
 * 描    述：
 * 作    者：loshine1992@gmail.com
 * 时    间：2016/12/23
 */
public class PreviewActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener, CheckBox.OnCheckedChangeListener {

    private static final String MEDIA_FILE_LIST = "media_file_list";
    private static final String MEDIA_POSITION = "media_position";

    AppBarLayout mAppBarLayout;
    Toolbar mToolbar;
    ViewPager mViewPager;
    CheckBox mCheckBox;

    private ArrayList<MediaFile> mMediaFiles;
    private int mPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_picker_activity_preview);

        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mCheckBox = (CheckBox) findViewById(R.id.gallery_preview_checkbox);

        Intent intent = getIntent();
        mMediaFiles = intent.getParcelableArrayListExtra(MEDIA_FILE_LIST);
        mPosition = intent.getIntExtra(MEDIA_POSITION, 0);
        refreshTitle();
        setSupportActionBar(mToolbar);

        mCheckBox.setOnCheckedChangeListener(this);

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
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onBackPressed() {
        // 遍历找寻出所有勾选的 items
        ArrayList<Long> idList = new ArrayList<>();
        for (MediaFile mediaFile : mMediaFiles) {
            if (mediaFile.isChecked()) {
                idList.add(mediaFile.getId());
            }
        }
        Intent intent = getIntent();
        intent.putExtra(ID_LIST, idList);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        refreshTitle();
        mCheckBox.setChecked(mMediaFiles.get(position).isChecked());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mMediaFiles.get(mPosition).setChecked(isChecked);
    }

    private void refreshTitle() {
        mToolbar.setTitle(getString(R.string.gallery_preview_title, mPosition + 1, mMediaFiles.size()));
    }

    public static void start(Context context, @NonNull ArrayList<MediaFile> mediaFiles, int position) {
        Activity activity = (Activity) context;
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putParcelableArrayListExtra(MEDIA_FILE_LIST, mediaFiles);
        intent.putExtra(MEDIA_POSITION, position);
        activity.startActivityForResult(intent, PREVIEW_SELECT_REQUEST);
    }
}
