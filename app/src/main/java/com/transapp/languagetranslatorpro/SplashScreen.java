package com.transapp.languagetranslatorpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mob.coresdk.Mob;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        App global = (App) this.getApplication();

        global.vldConfig.validate( this);
        global.coreConfig.getConfig(global.getApplicationContext(),  this);
        Mob.onCreate(global.coreConfig);

        global.coreConfig.OnSplashListener(() -> {
                    Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    startActivity(intent);
                    finish();
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Mob.onResume(this);

    }

}