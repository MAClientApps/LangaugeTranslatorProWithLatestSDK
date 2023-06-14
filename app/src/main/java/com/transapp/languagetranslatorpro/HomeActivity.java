package com.transapp.languagetranslatorpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;



import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    LinearLayout textTrans, voiceTrans, galleryTrans,setting;

    LinearLayout cameraTrans;

    public static String[] lang_code = {"ar", "bn-IN", "bg", "ca", "zh_Hans", "cs", "da", "nl", "en", "et", "fr", "fil", "fi", "de", "el", "gu-IN", "ht", "he", "hi", "hu", "id", "it", "ja", "kn-IN", "km-KH", "ko", "lv", "lt", "ms", "ml-IN", "mr-IN", "ne-NP", "no", "fa", "pl", "pt", "ro", "ru", "sk", "sl", "es", "sv", "ta-IN", "te-IN", "tr", "th", "uk", "ur-IN", "vi"};
    public static ArrayList<ModelLanguage> allLang = new ArrayList<>();
    public static String[] lang;
    String[] permission = {"android.permission.RECORD_AUDIO", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA", "android.permission.MODIFY_AUDIO_SETTINGS"};

    private Intent destIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_home1);

        findIds();

        lang = getResources().getStringArray(R.array.language_in);
        MyUtils.isNetworkConnected(HomeActivity.this);
        MyUtils.checkPermission(HomeActivity.this, this.permission);

        textTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                destIntent = new Intent(HomeActivity.this, TranslatorPage.class);
                destIntent.putExtra("which", 1);
                startActivity(destIntent);


            }
        });

        voiceTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                destIntent = new Intent(HomeActivity.this, TranslatorPage.class);
                destIntent.putExtra("which", 0);
                startActivity(destIntent);


            }
        });


        cameraTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                destIntent = new Intent(HomeActivity.this, MyCropImg.class);
                destIntent.putExtra("camera", true);
                startActivity(destIntent);

            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(HomeActivity.this,SettingActivity.class);
                startActivity(it);
            }
        });
    }

    private void findIds() {

        textTrans = (LinearLayout) findViewById(R.id.TextTranslator);
        voiceTrans = (LinearLayout) findViewById(R.id.voiceTranslator);
        galleryTrans = (LinearLayout) findViewById(R.id.camTranslator);
        setting = (LinearLayout) findViewById(R.id.laySetting);
        cameraTrans = (LinearLayout) findViewById(R.id.camTranslator);

    }

    public static ArrayList<ModelLanguage> getAllLang() {
        if (allLang == null || allLang.size() <= 0) {
            allLang.clear();
            for (int i = 0; i < lang.length; i++) {
                allLang.add(new ModelLanguage(lang[i], lang_code[i]));
            }
        }
        return allLang;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}