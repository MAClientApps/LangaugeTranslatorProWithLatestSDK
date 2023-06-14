package com.transapp.languagetranslatorpro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;
import androidx.appcompat.app.AppCompatActivity;
import java.io.Serializable;
import java.util.ArrayList;

public class LangSelect extends AppCompatActivity {

    ArrayList<ModelLanguage> allLang;
    Ad_Language ad_language;
    Context f52cn = this;
    ImageView backImg;
    ListView listLang;

    LinearLayout layTop;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.lang_select);
        getWindow().setFlags(1024, 1024);


        findId();
        this.allLang = HomeActivity.getAllLang();
        this.ad_language = new Ad_Language(this.f52cn, this.allLang);
        this.listLang.setAdapter(this.ad_language);
        this.listLang.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(LangSelect.this.f52cn, TranslatorPage.class);
                i.putExtra("lang", (Serializable) LangSelect.this.allLang.get(position));
                i.putExtra("which", LangSelect.this.getIntent().getIntExtra("which", 0));
                LangSelect.this.setResult(-1, i);
                LangSelect.this.finish();
            }
        });

    }


    public void findId() {
        this.listLang = (ListView) findViewById(R.id.langList);
        this.backImg = (ImageView) findViewById(R.id.backImg);
        this.layTop = (LinearLayout) findViewById(R.id.layTop);
        LayoutParams layoutParams = MyUtils.getParamsR(this.f52cn, 110, 110);
        layoutParams.addRule(15);
        this.backImg.setLayoutParams(layoutParams);
        this.layTop.setLayoutParams(MyUtils.getParamsL(this.f52cn, 1080, 197));
        this.backImg.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                LangSelect.this.onBackPressed();
            }
        });
    }




}
