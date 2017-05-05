package com.guvenluckyboy.spanishlearn;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Typeface mytf = Typeface.createFromAsset(getAssets(), "default.ttf");
        TextView txt = (TextView) findViewById(R.id.playTxt);
        TextView txt2 = (TextView) findViewById(R.id.settingsTxt);
        TextView txt3 = (TextView) findViewById(R.id.AboutUsTxt);
        txt.setTypeface(mytf);
        txt2.setTypeface(mytf);
        txt3.setTypeface(mytf);

    }

    public void clickBubble1(View v) {
        Intent intent = new Intent(this, menu.class);
        startActivity(intent);
    }

    public void clickBubble2(View v) {

        Intent intent = new Intent(this, dinlePage.class);
        startActivity(intent);
    }

    public void clickBubble3(View v) {
        TextView txt = (TextView) findViewById(R.id.AboutUsTxt);
        Intent intent = new Intent(this, Splash.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }




}
