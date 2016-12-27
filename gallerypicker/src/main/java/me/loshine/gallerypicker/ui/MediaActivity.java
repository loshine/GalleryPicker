package me.loshine.gallerypicker.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.loshine.gallerypicker.R;
import me.loshine.gallerypicker.ui.media.MediaFragment;

public class MediaActivity extends AppCompatActivity {

    MediaFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_picker_activity_media);

        boolean isImage = getIntent().getBooleanExtra("isImage", true);
        if (isImage) {
            mFragment = new MediaFragment.Builder()
                    .image()
                    .multiple()
                    .maxSize(9)
                    .build();
        } else {
            mFragment = new MediaFragment.Builder()
                    .video()
                    .radio()
                    .build();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, mFragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (!mFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
