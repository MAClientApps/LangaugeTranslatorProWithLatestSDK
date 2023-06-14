package com.transapp.languagetranslatorpro;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.UtteranceProgressListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class TranslatorPage extends AppCompatActivity {

    public int LANGPICK = 11;
    public int RECORD_REQUEST = 10;

    ImageView goBack;

    TextView btnDest2;
    TextView btnLangTxt1;
    ImageView btnSpeech;

    ImageView btnSwipe;
    Button translateBt;

    Context f70cn = this;
    EditText editBox1;
    int flag;
    String from = "en";
    RelativeLayout layOutBox,RelayMainBox;
    LayoutParams layoutParams;
    LinearLayout laytop;
    ModelLanguage f71ms = new ModelLanguage("English", "en");
    ModelLanguage f72mt = new ModelLanguage("Hindi", "hi");
    ProgressDialog f73pd;
    String query;

    TextView setOutp;

    TextView Title;
    TextView f75t2;
    TextView f74t1;
    String f77to = "hi";

    TextView f76t3;
    TextToSpeech tts;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.text_translate_page);
        getWindow().setFlags(1024, 1024);
        widgets();
        iniTtxS();
        String t = " ";
        if (VERSION.SDK_INT >= 21) {
            this.tts.speak(t, 0, null, "utteranceId");
        } else {
            this.tts.speak(t, 0, null);
        }
        this.flag = getIntent().getIntExtra("which", 0);
        if (this.flag == 3) {
            String text = getIntent().getStringExtra("text");
            this.editBox1.setText(text);
            this.editBox1.setSelection(text.length());
            this.btnSpeech.setVisibility(View.GONE);
            this.Title.setText("Translator");
        } else if (this.flag == 1) {
            this.btnSpeech.setVisibility(View.GONE);
            this.Title.setText("Translator");
        }
        this.translateBt.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (TranslatorPage.this.editBox1.getText().toString().length() > 0) {
                    TranslatorPage.this.query = TranslatorPage.this.editBox1.getText().toString();
                    new AsyncTranslate(TranslatorPage.this.f73pd, TranslatorPage.this.from, TranslatorPage.this.f77to, TranslatorPage.this.query, new AsyncTranslate.OnResponse() {
                        public void GetAns(ModelAns modelAns, Boolean isSuccess) {
                            if (isSuccess.booleanValue()) {
                                String op = modelAns.getOp();
                                TranslatorPage.this.setOutp.setText(op);
                                return;
                            }
                            MyUtils.Toast(TranslatorPage.this.f70cn, "Try Again...");
                        }
                    }).execute(new Void[0]);
                    return;
                }
                TranslatorPage.this.setOutp.setText("");
            }
        });
        this.btnSpeech.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent("android.speech.action.RECOGNIZE_SPEECH");
                i.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
                i.putExtra("android.speech.extra.LANGUAGE", TranslatorPage.this.from);
                i.putExtra("android.speech.extra.MAX_RESULTS", 3);
                i.putExtra("android.speech.extra.PROMPT", "Speech");
                try {
                    TranslatorPage.this.startActivityForResult(i, TranslatorPage.this.RECORD_REQUEST);
                } catch (ActivityNotFoundException e) {
                    Log.e("AAA", "Your device doesn't support Speech Recognition");
                }
            }
        });
        this.btnLangTxt1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(TranslatorPage.this.f70cn, LangSelect.class);
                i.putExtra("which", 0);
                TranslatorPage.this.startActivityForResult(i, TranslatorPage.this.LANGPICK);
            }
        });
        this.btnDest2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(TranslatorPage.this.f70cn, LangSelect.class);
                i.putExtra("which", 1);
                TranslatorPage.this.startActivityForResult(i, TranslatorPage.this.LANGPICK);
            }
        });
        this.btnSwipe.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                TranslatorPage.this.editBox1.setText("");
                TranslatorPage.this.setOutp.setText("");
                ModelLanguage tmp = TranslatorPage.this.f71ms;
                TranslatorPage.this.f71ms = TranslatorPage.this.f72mt;
                TranslatorPage.this.f72mt = tmp;
                TranslatorPage.this.SetSelectedLanguage();
            }
        });

        this.editBox1.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    TranslatorPage.this.setOutp.setText("");
                }
            }
        });
    }


    public void iniTtxS() {
        this.tts = new TextToSpeech(this.f70cn, new OnInitListener() {
            public void onInit(int status) {
                if (status == 0) {
                    Log.e("AAA", "TTS Status : " + status);
                }
            }
        });
        this.tts.setLanguage(Locale.getDefault());
        this.tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            public void onStart(String utteranceId) {
                Log.e("AAA", "TTS Status Start ");
            }

            public void onDone(String utteranceId) {
                Log.e("AAA", "TTS Status Done");
            }

            public void onError(String utteranceId) {
                Log.e("AAA", "TTS Status Error ");
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == -1 && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra("android.speech.extra.RESULTS");
            if (result != null) {
                Iterator it = result.iterator();
                while (it.hasNext()) {
                    Log.e("AAA", (String) it.next());
                }
                String ans = (String) result.get(0);
                this.editBox1.setText(ans);
                this.editBox1.setSelection(ans.length());
                this.translateBt.performClick();
                Log.e("AAA", "" + result.size());
            }
        } else if (requestCode == this.LANGPICK && resultCode == -1 && data != null) {
            ModelLanguage ml = (ModelLanguage) data.getExtras().get("lang");
            String lang = ml.getLanguage();
            String lang_code = ml.getLang_code();
            if (data.getIntExtra("which", 0) == 0) {
                this.f71ms = ml;
            } else {
                this.f72mt = ml;
            }
            SetSelectedLanguage();
            Log.e("AAA", "Selected : " + lang + " : " + lang_code);
        }
    }


    public void widgets() {
        this.editBox1 = (EditText) findViewById(R.id.editBox1);
        this.setOutp = (TextView) findViewById(R.id.setOutp);
        this.translateBt = (Button) findViewById(R.id.translateBt);
        this.btnSpeech = (ImageView) findViewById(R.id.btnSpeak);
        this.btnLangTxt1 = (TextView) findViewById(R.id.btnLangTxt1);
        this.btnDest2 = (TextView) findViewById(R.id.btnDest2);
        this.btnSwipe = (ImageView) findViewById(R.id.btnSwipe);
        this.goBack = (ImageView) findViewById(R.id.goBack);
        this.f73pd = new ProgressDialog(this.f70cn);
        this.f73pd.setMessage("wait...");
        this.f73pd.setCancelable(false);
        this.Title = (TextView) findViewById(R.id.Title);
        this.f74t1 = (TextView) findViewById(R.id.t1);
        this.f75t2 = (TextView) findViewById(R.id.t2);
        this.f76t3 = (TextView) findViewById(R.id.t3);
        this.layOutBox = (RelativeLayout) findViewById(R.id.layOutBox);
        this.RelayMainBox = (RelativeLayout) findViewById(R.id.RelayMainBox);
        this.laytop = (LinearLayout) findViewById(R.id.layTop);
        resize();
    }


    public void resize() {
        this.layoutParams = MyUtils.getParamsL(this.f70cn, 330, 330);
        this.btnSpeech.setLayoutParams(this.layoutParams);
        this.layoutParams = MyUtils.getParamsL(this.f70cn, 1042, 562);
        this.layOutBox.setLayoutParams(this.layoutParams);
        this.RelayMainBox.setLayoutParams(this.layoutParams);
        this.layoutParams = MyUtils.getParamsL(this.f70cn, 320, 140);
        this.layoutParams = MyUtils.getParamsL(this.f70cn, 436, 156);
        this.btnLangTxt1.setLayoutParams(this.layoutParams);
        this.btnDest2.setLayoutParams(this.layoutParams);
        this.layoutParams = MyUtils.getParamsL(this.f70cn, 100, 50);
        this.btnSwipe.setLayoutParams(this.layoutParams);
        this.layoutParams = MyUtils.getParamsL(this.f70cn, 580, 180);
        this.translateBt.setLayoutParams(this.layoutParams);
        this.layoutParams = MyUtils.getParamsL(this.f70cn, 1080, 197);
        this.laytop.setLayoutParams(this.layoutParams);
        LayoutParams l = new LayoutParams((this.f70cn.getResources().getDisplayMetrics().widthPixels * 436) / 1080, -2);
        this.f74t1.setLayoutParams(l);
        this.f75t2.setLayoutParams(l);
        this.f76t3.setLayoutParams(new LayoutParams((this.f70cn.getResources().getDisplayMetrics().widthPixels * 120) / 1080, -2));
        RelativeLayout.LayoutParams layoutParams2 = MyUtils.getParamsR(this.f70cn, 110, 110);
        this.goBack.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                TranslatorPage.this.onBackPressed();
            }
        });
    }


    public void onDestroy() {
        super.onDestroy();
        this.f73pd.dismiss();
    }


    public void SetSelectedLanguage() {
        this.from = this.f71ms.getLang_code();
        this.btnLangTxt1.setText(this.f71ms.getLanguage());
        this.f77to = this.f72mt.getLang_code();
        this.btnDest2.setText(this.f72mt.getLanguage());
    }
}
