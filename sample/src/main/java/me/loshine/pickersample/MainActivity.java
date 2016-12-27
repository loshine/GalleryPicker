package me.loshine.pickersample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.loshine.gallerypicker.ui.media.MediaActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startImage(View v) {
        Intent intent = new Intent(this, MediaActivity.class);
        intent.putExtra("isImage", true);
        startActivity(intent);
    }

    public void startVideo(View v) {
        Intent intent = new Intent(this, MediaActivity.class);
        intent.putExtra("isImage", false);
        startActivity(intent);
    }
}
