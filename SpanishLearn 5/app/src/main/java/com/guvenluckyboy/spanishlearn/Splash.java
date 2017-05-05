package com.guvenluckyboy.spanishlearn;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    TextView Erkan, Guven;
    //boolean allDone =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Typeface mytf = Typeface.createFromAsset(getAssets(), "p.ttf");

        Erkan = (TextView)findViewById(R.id.EK);
        Erkan.setTypeface(mytf);
        Guven = (TextView)findViewById(R.id.GA);
        Guven.setTypeface(mytf);

        ImageView hola = (ImageView)findViewById(R.id.holaImg);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(3000);
        hola.setAnimation(fadeIn);
        fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(3000);
        fadeIn.setStartOffset(2000);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                nextPage();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Erkan.setAnimation(fadeIn);
        Guven.setAnimation(fadeIn);

    }
    void nextPage(){
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {

    }
}
