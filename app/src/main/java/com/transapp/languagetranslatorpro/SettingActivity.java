package com.transapp.languagetranslatorpro;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class SettingActivity extends AppCompatActivity {

    LinearLayout share;

    MaterialToolbar toolBar;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        share = findViewById(R.id.share);
        toolBar = findViewById(R.id.toolBar);

        toolBar.setNavigationOnClickListener(v -> {
            finish();
        });


        share.setOnClickListener(v -> {

            MyUtils.share(SettingActivity.this);

        });

    }
}