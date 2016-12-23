package me.loshine.pickersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.loshine.gallerypicker.ui.media.MediaFragment;

public class MainActivity extends AppCompatActivity {

    MediaFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragment = new MediaFragment.Builder()
                .video()
                .build();

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
