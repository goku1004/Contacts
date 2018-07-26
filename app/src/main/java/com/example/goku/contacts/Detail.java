package com.example.goku.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.widget.TextView;


public class Detail extends Activity{
    private TextView mTextViewN;
    private TextView mTextViewP;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        init();
    }

    private void init() {
        mTextViewN=findViewById(R.id.textn);
        mTextViewP=findViewById(R.id.textp);

        Intent intent=getIntent();
        String name=intent.getStringExtra(MainActivity.NAME);
        String phone=intent.getStringExtra(MainActivity.PHONE);
        mTextViewP.setText(phone);
        mTextViewN.setText(name);
    }
}
