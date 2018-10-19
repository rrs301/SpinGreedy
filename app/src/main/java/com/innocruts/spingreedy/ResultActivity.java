package com.innocruts.spingreedy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent=getIntent();
        String CoinsEarn=intent.getStringExtra("CoinsEarn");
        Log.i("CoinsEarn",CoinsEarn);
    }
}
