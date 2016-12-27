package me.loshine.gallerypicker.ui.media;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import me.loshine.gallerypicker.R;
import me.loshine.gallerypicker.entity.MediaFile;

import static me.loshine.gallerypicker.Constants.ID_LIST;
import static me.loshine.gallerypicker.Constants.PREVIEW_SELECT_REQUEST;
import static me.loshine.gallerypicker.entity.MediaFile.EXTRA_KEY_MEDIA_FILE;

public class MediaActivity extends AppCompatActivity
        implements MediaFragment.OnCheckedSizeChangedListener {

    TextView mDoneTextView;

    MediaFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_picker_activity_media);
        mDoneTextView = (TextView) findViewById(R.id.done_text_view);
        mDoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
        // 此处显示
        MediaFile mediaFile = getIntent().getParcelableExtra(EXTRA_KEY_MEDIA_FILE);
        if (mediaFile != null) {
            Toast.makeText(this, mediaFile.toString(), Toast.LENGTH_SHORT).show();
        }
        if (!mFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onCheckedSizeChanged(int size) {
        mDoneTextView.setEnabled(size > 0);
        String string = getString(R.string.gallery_over_button_with_num, size);
        mDoneTextView.setText(string);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || requestCode != PREVIEW_SELECT_REQUEST) return;
        ArrayList<Long> idList = (ArrayList<Long>) data.getSerializableExtra(ID_LIST);
        if (idList == null) return;
        mFragment.selectImages(idList);
    }
}
