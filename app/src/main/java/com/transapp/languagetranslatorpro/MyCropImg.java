package com.transapp.languagetranslatorpro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.os.StrictMode.VmPolicy.Builder;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.theartofdev.edmodo.cropper.CropImageView.CropResult;
import com.theartofdev.edmodo.cropper.CropImageView.OnCropImageCompleteListener;
import com.theartofdev.edmodo.cropper.CropImageView.OnSetImageUriCompleteListener;

import java.io.File;

public class MyCropImg extends AppCompatActivity {

    private int RESULT_CAMERA = 11;

    ImageView back, crop, rotate;
    CropImageView cropImageV;
    Context f61cn = this;
    LinearLayout layTop;
    LayoutParams layoutParams;
    private File nFileTemp;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_crop);


        findIds();
        getWindow().setFlags(1024, 1024);
        if (getIntent().getBooleanExtra("camera", false)) {
            if ("mounted".equals(Environment.getExternalStorageState())) {
                this.nFileTemp = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "tmp.jpeg");
            } else {
                this.nFileTemp = new File(getFilesDir(), "tmp.jpeg");
            }
            StrictMode.setVmPolicy(new Builder().build());
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra("output", Uri.fromFile(this.nFileTemp));
            startActivityForResult(intent, this.RESULT_CAMERA);
        } else {
            Intent i = new Intent("android.intent.action.PICK");
            i.setType("image/*");
            startActivityForResult(i, 10);
        }
        this.cropImageV.setOnCropImageCompleteListener(new OnCropImageCompleteListener() {
            public void onCropImageComplete(CropImageView view, CropResult result) {

                TextRecognizer textRecognizer = new TextRecognizer.Builder(MyCropImg.this).build();
                StringBuilder imageText = new StringBuilder();
                Frame imageFrame = new Frame.Builder()
                        .setBitmap(result.getBitmap())
                        .build();
                SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);
                for (int i = 0; i < textBlocks.size(); i++) {
                    TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
                    imageText.append(textBlock.getValue());
                }
                Log.e("AAA", "Text : " + imageText);

                if (imageText.length() > 0) {
                    Intent i1 = new Intent(MyCropImg.this.f61cn, TranslatorPage.class);
                    i1.putExtra("which", 3);
                    i1.putExtra("text", imageText.toString());
                    MyCropImg.this.startActivity(i1);
                    MyCropImg.this.finish();
                    return;
                }
                MyUtils.Toast(MyCropImg.this.f61cn, "No Text Detect...");
                MyCropImg.this.finish();
            }
        });
        this.cropImageV.setOnSetImageUriCompleteListener(new OnSetImageUriCompleteListener() {
            public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
                if (error != null) {
                    Log.e("AAA", error.getLocalizedMessage());
                }
            }
        });
        this.rotate.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyCropImg.this.cropImageV.rotateImage(90);
            }
        });
        this.crop.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyCropImg.this.rotate.setEnabled(false);
                MyCropImg.this.cropImageV.getCroppedImageAsync();
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == -1 && data != null) {
            Uri uri = data.getData();
            Log.e("AAA", "Gal Uri : " + uri.toString());
            this.cropImageV.setImageUriAsync(uri);
        } else if (requestCode == this.RESULT_CAMERA && resultCode == -1) {
            this.cropImageV.setImageUriAsync(Uri.fromFile(this.nFileTemp));
        } else {
            MyUtils.Toast(this.f61cn, "Selection Cancel");
            finish();
        }
    }


    public void findIds() {
        this.crop = (ImageView) findViewById(R.id.cropBtn);
        this.rotate = (ImageView) findViewById(R.id.rotateBtn);
        this.cropImageV = (CropImageView) findViewById(R.id.setCropImage);
        this.back = (ImageView) findViewById(R.id.backImg);
        this.layTop = (LinearLayout) findViewById(R.id.layTop);
        resize();
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyCropImg.this.onBackPressed();
            }
        });
    }


    public void resize() {
        this.layoutParams = MyUtils.getParamsL(this.f61cn, 450, 170);
        this.rotate.setLayoutParams(this.layoutParams);
        this.crop.setLayoutParams(this.layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = MyUtils.getParamsR(this.f61cn, 110, 110);
        this.layTop.setLayoutParams(MyUtils.getParamsR(this.f61cn, 1080, 197));
    }
}
