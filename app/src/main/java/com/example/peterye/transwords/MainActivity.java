package com.example.peterye.transwords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * The main UI of the TransWord App
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent  = new Intent();
        intent.setClass(MainActivity.this,WatchClipBoardService.class);
        startService(intent);
    }
}
