package com.guvenluckyboy.spanishlearn;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class cumleyerlestirme extends AppCompatActivity {
    public TextView resultTV;
    ImageView micButton;
    DatabaseHelper myDb;
    String wrongStr;
    String realStr;
    String[] splittedStr;
    int[] rightSeq=new int[8];
    int level,dbLevel,grade;
    List<ImageView> imageList=new ArrayList<ImageView>();
    List<TextView> textList=new ArrayList<TextView>();

    int width,height,size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cumleyerlestirme);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        //toolbar.setNavigationIcon(R.drawable.mic);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        micButton = (ImageView)findViewById(R.id.micBtn);
        resultTV = (TextView)findViewById(R.id.result);
        Typeface mytf = Typeface.createFromAsset(getAssets(), "default.ttf");
        resultTV.setTypeface(mytf);


        grade = getIntent().getIntExtra("grade",1);
        Log.d("grade", Integer.toString(grade));

        myDb = new DatabaseHelper(this);
        Cursor res = myDb.getData(myDb.cumleLevel, grade);
        res.moveToPosition(0);
        dbLevel = res.getInt(1);

        if(dbLevel>grade*10){
            Intent intent = new Intent(this, finish.class);
            startActivity(intent);
            return;
        }


        //resultTV.setText(Integer.toString(dbLevel));

        level=dbLevel;

        res = myDb.getData(myDb.cumleStrings,(grade-1)*10+level+1);
        res.moveToPosition(0);
        wrongStr = res.getString(1);
        realStr = res.getString(2);



        splittedStr=wrongStr.split(" ");
        size= splittedStr.length;

        String[] splittedRight = realStr.split(" ");

        for(int i=0;i<size;i++) {
            rightSeq[i]=Arrays.asList(splittedRight).indexOf(splittedStr[i]);

        }







        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.relativeLayout);
        for(int x=0;x<size;x++) {
            ImageView image = new ImageView(cumleyerlestirme.this);
            image.setBackgroundResource(R.drawable.bluesquare);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width/5,120);
            if(size<=4) {
                lp.leftMargin = (width / 5 + width / 25) * x + (width - (size * (width / 5)) - (size - 1) * (width / 25)) / 2;
                lp.topMargin = height / 5;
            }
            else{

                if(x<4) {
                    lp.leftMargin = (width / 5 + width / 25) * x + (width - ((4) * (width / 5)) - ((4) - 1) * (width / 25)) / 2;
                    lp.topMargin = height / 5;
                }
                else {
                    lp.leftMargin = (width / 5 + width / 25) * (x-4) + (width - ((size - 4) * (width / 5)) - ((size - 4) - 1) * (width / 25)) / 2;
                    lp.topMargin = height / 5 + height / 25 + 120;
                }
            }
            image.setLayoutParams(lp);
            rl.addView(image);

            imageList.add(image);

            TextView tv = new TextView(cumleyerlestirme.this);
            tv.setText(splittedStr[x]);
            tv.setGravity(Gravity.CENTER);
            lp = new RelativeLayout.LayoutParams(width/5,60);

            if(size<=4) {
                lp.leftMargin = (width / 5 + width / 25) * x + (width - (size * (width / 5)) - (size - 1) * (width / 25)) / 2;
                lp.topMargin = height / 5+30;
            }
            else{

                if(x<4) {
                    lp.leftMargin = (width / 5 + width / 25) * x + (width - ((4) * (width / 5)) - ((4) - 1) * (width / 25)) / 2;
                    lp.topMargin = height / 5+30;
                }
                else {
                    lp.leftMargin = (width / 5 + width / 25) * (x-4) + (width - ((size - 4) * (width / 5)) - ((size - 4) - 1) * (width / 25)) / 2;
                    lp.topMargin = height / 5 + height / 25 + 150;
                }
            }
            tv.setLayoutParams(lp);
            rl.addView(tv);

            textList.add(tv);


        }



    }

    public void clk(View view) {
        trueAns();
    }

    void trueAns(){
        level++;
        int marginDiff=((width - ((size - 4) * (width / 5)) - ((size - 4) - 1) * (width / 25)) / 2) - width/25;
        micButton.setEnabled(false);

        for(int x=0;x<size;x++) {
            TranslateAnimation animation;
            if(rightSeq[x]>=4 && x<4)
                animation = new TranslateAnimation(0, (rightSeq[x]-4-x)*(width/5+width/25)+marginDiff, 0, height/4+120+height/25);
            else if(rightSeq[x]<4 && x>=4)
                animation = new TranslateAnimation(0, (rightSeq[x]-(x-4))*(width/5+width/25)-marginDiff, 0, height/4-120-height/25);
            else
                animation = new TranslateAnimation(0, (rightSeq[x]-x)*(width/5+width/25), 0, height/4);

            animation.setDuration(2000);
            animation.setFillAfter(true);
            animation.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    for (int x = 0; x < size; x++) {
                        imageList.get(x).setBackgroundResource(R.drawable.greensquare);
                    }

                    Runnable r = new Runnable() {
                        @Override
                        public void run(){
                            Intent intent = new Intent(cumleyerlestirme.this, cumleyerlestirme.class);
                            intent.putExtra("grade", grade);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    };

                    Handler h = new Handler();
                    h.postDelayed(r, 1000);



                }
            });
            imageList.get(x).startAnimation(animation);
            textList.get(x).startAnimation(animation);
            myDb.updatelevel(grade,dbLevel+1);
        }
    }

    public void promptSpeech(View view) {
        Intent i= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, wrongStr);

        try {
            this.startActivityForResult(i, 100);
            //Toast.makeText(cumleyerlestirme.this, "Başarılı",Toast.LENGTH_SHORT).show();
        }catch(ActivityNotFoundException a){
            Toast.makeText(cumleyerlestirme.this, "Sorry!!!!", Toast.LENGTH_LONG).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 100:
                if(resultCode == -1 && data != null) {
                    ArrayList result = data.getStringArrayListExtra("android.speech.extra.RESULTS");
                    this.resultTV.setText((CharSequence)result.get(0));
                    String answer = realStr;
                    answer = answer.replace("!", "").replace("?", "").replace(".", "").replace(",", "").replace("¡", "").replace("¿", "");

                    if (answer.toLowerCase().equals(result.get(0).toString().toLowerCase())) {
                        this.resultTV.setText("Sizin dediğiniz:\n"+ (CharSequence)result.get(0));
                        Toast.makeText(cumleyerlestirme.this, "Doğru cevap",Toast.LENGTH_LONG).show();
                        trueAns();
                    }
                    else {
                        this.resultTV.setText("Sizin dediğiniz:\n" + (CharSequence) result.get(0));
                        Toast.makeText(cumleyerlestirme.this, "Yanlış cevap!!", Toast.LENGTH_LONG).show();
                    }
                }
            default:
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, menu.class);
        startActivity(intent);

    }
}
