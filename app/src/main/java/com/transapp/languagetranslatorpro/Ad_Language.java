package com.transapp.languagetranslatorpro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

public class Ad_Language extends BaseAdapter {
    ArrayList<ModelLanguage> allang = new ArrayList<>();

    Context f48cn;

    public Ad_Language(Context context, ArrayList<ModelLanguage> allang2) {
        this.f48cn = context;
        this.allang = allang2;
    }

    public int getCount() {
        return this.allang.size();
    }

    public ModelLanguage getItem(int position) {
        return (ModelLanguage) this.allang.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(this.f48cn).inflate(R.layout.ad_language, parent, false);
        ((LinearLayout) v.findViewById(R.id.laymain)).setLayoutParams(MyUtils.getParamsL(this.f48cn, 1036, 200));
        ((ImageView) v.findViewById(R.id.laybullet)).setLayoutParams(MyUtils.getParamsL(this.f48cn, 50, 50));
        ((TextView) v.findViewById(R.id.setlang)).setText(((ModelLanguage) this.allang.get(position)).getLanguage());
        return v;
    }
}
