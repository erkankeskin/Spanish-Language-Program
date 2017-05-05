package com.guvenluckyboy.spanishlearn;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class dialog extends AppCompatActivity implements TextToSpeech.OnInitListener{
    public TextView resultTV;
    List<TextView> textList= new ArrayList<TextView>();
    TextView text;
    DatabaseHelper myDb;
    TextToSpeech tts;
    int resultLang;
    String ttsString;
    String[] ttsArray;
    int level=12;
    int width,height,dialogNum,grade;
    int max[]={14,14,14,12,8,12,10,12,14,12,12,12};
    HashMap<String, String> map = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
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

        resultTV = (TextView)findViewById(R.id.result);
        Typeface mytf = Typeface.createFromAsset(getAssets(), "default.ttf");
        resultTV.setTypeface(mytf);

        myDb = new DatabaseHelper(this);

        grade = getIntent().getIntExtra("grade",1);
        Log.d("grade",Integer.toString(grade));

        for(int i=1;;i++) {
            if(i>3){
                Intent intent = new Intent(this, finish.class);
                startActivity(intent);
                return;
            }
            Cursor res = myDb.getDialogLevel(grade,i);
            res.moveToPosition(0);
            level = res.getInt(2);
            if (level<max[i]) {
                dialogNum=i;
                res = myDb.getDialogStr(grade, i);
                res.moveToPosition(0);
                ttsString = res.getString(2);
                break;
            }



        }


        ttsArray=ttsString.split("-");


        int size = ttsArray.length,
            leftMargin=0,topMargin=0;
        RelativeLayout relativeLayout= (RelativeLayout)findViewById(R.id.RL);

        for (int i = 0; i<size;i++){
            TextView tv = new TextView(dialog.this);
            //tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);

            if(i%2==0) {
                if (ttsArray[i].equals("1"))
                    tv.setText("SpanishLearn: ");
                else
                    tv.setText("Usted: ");
                    //Log.d("Usted", ttsArray[i]);
                tv.setTypeface(null, Typeface.BOLD);
            }
            else
                tv.setText(ttsArray[i]);



            Display display = getWindowManager().getDefaultDisplay();
            width=display.getWidth();
            height=display.getHeight();
            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(display.getWidth(), View.MeasureSpec.AT_MOST);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            tv.measure(widthMeasureSpec, heightMeasureSpec);


            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(relativeLayout.ALIGN_PARENT_LEFT);
            params.topMargin=topMargin;
            if(i%2==0) {
                leftMargin = tv.getMeasuredWidth();


            }
            else{
                params.leftMargin=leftMargin;
                topMargin += tv.getMeasuredHeight();

            }

            tv.setLayoutParams(params);
            relativeLayout.addView(tv);
            textList.add(tv);

        }


        tts=new TextToSpeech(this,this);
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
        if(level<textList.size())
            textList.get(level + 1).setTextColor(Color.RED);

        if(level>10) {
            for (int i = 0; i < textList.size(); i++) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) textList.get(i).getLayoutParams();
                lp.topMargin -= (level-10)*30; // use topmargin for the y-property, left margin for the x-property of your view
                Log.d("Height",Integer.toString(textList.get(level + 1).getHeight()));
                textList.get(i).setLayoutParams(lp);
            }
        }
        if(ttsArray[level].equals("1"))
            sayNext();


    }

    public void clk(View view){
        //level+=2;
        sayNext();
        //Toast.makeText(getApplicationContext(), "Hola",Toast.LENGTH_SHORT).show();
    }

    public void promptSpeech(View view) {
        Intent i= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, ttsArray[level + 1]);

        try{
            this.startActivityForResult(i, 100);
            //resultTV.setText(i.getStringExtra(RecognizerIntent.EXTRA_RESULTS));
            Toast.makeText(dialog.this, "Başarılı",Toast.LENGTH_SHORT).show();
        }catch(ActivityNotFoundException a){
            Toast.makeText(dialog.this, "Bir sorun oluştu!!!", Toast.LENGTH_LONG).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 100:
                if(resultCode == -1 && data != null) {
                    ArrayList result = data.getStringArrayListExtra("android.speech.extra.RESULTS");
                    String answer = ttsArray[level + 1];
                    answer = answer.replace("!", "").replace("?", "").replace(".", "").replace(",", "").replace("¡", "").replace("¿", "");

                    if (answer.toLowerCase().equals(result.get(0).toString().toLowerCase())) {
                        this.resultTV.setText("Sizin dediğiniz:\n"+ (CharSequence)result.get(0));
                        Toast.makeText(dialog.this, "Doğru cevap",Toast.LENGTH_SHORT).show();
                        level+=2;
                        sayNext();
                    }
                    else {
                        this.resultTV.setText("Sizin dediğiniz:\n" + (CharSequence) result.get(0));
                        Toast.makeText(dialog.this, "Yanlış cevap!!", Toast.LENGTH_LONG).show();
                    }
                }
            default:
        }
    }

    public void helpMe(View view){
        tts.speak(ttsArray[level + 1], TextToSpeech.QUEUE_FLUSH, null);
    }

    void sayNext(){

        //Log.d("level",Integer.toString(level));

        if(level>=textList.size()){
            Intent intent = new Intent(this, dialog.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("grade", grade);
            startActivity(intent);
            return;
        }



        if(level<textList.size()){
            //Log.d("dıs",Integer.toString(level));
            for(int i=0;i<textList.size();i++){


                final int finalI = i;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Log.d("ic",Integer.toString(level));
                        //resultTV.setText(ttsArray[level+1]);
                        if(level/2>5) {
                            TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -1 * textList.get(level+1).getHeight());
                            animation.setDuration(500);
                            animation.setFillEnabled(true);
                            animation.setAnimationListener(
                                    new Animation.AnimationListener() {

                                        @Override
                                        public void onAnimationStart(Animation animation) {
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {
                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            //if(level<textList.size()) {
                                                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) textList.get(finalI).getLayoutParams();

                                                lp.topMargin -= textList.get(level + 1).getHeight(); // use topmargin for the y-property, left margin for the x-property of your view
                                                textList.get(finalI).setLayoutParams(lp);

                                        }
                                    }
                            );
                            textList.get(finalI).startAnimation(animation);
                        }
                        //resultTV.setText(Integer.toString(level));
                        textList.get(level + 1).setTextColor(Color.RED);
                        if (level != 0)
                            textList.get(level - 1).setTextColor(Color.LTGRAY);
                    }
                });
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("speak", Integer.toString(level));
            tts.speak(ttsArray[level + 1], TextToSpeech.QUEUE_FLUSH, map);
            level += 2;
            myDb.updateDialogLevel(grade, dialogNum, level);
        }



    }



    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            resultLang=tts.setLanguage(new Locale("es-ES"));
            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onDone(String utteranceId) {

                    if(level+2<ttsArray.length && !ttsArray[level+2].equals("1"))
                        sayNext();
                    else if(level==ttsArray.length-2 && ttsArray[level].equals("1"))
                        sayNext();
                    else if(level< ttsArray.length){
                        Log.d("son", Integer.toString(level));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textList.get(level + 1).setTextColor(Color.RED);
                                if (level != 0)
                                    textList.get(level - 1).setTextColor(Color.LTGRAY);
                            }
                        });

                    }
                    else {

                        sayNext();
                    }
                }

                @Override
                public void onError(String utteranceId) {
                }

                @Override
                public void onStart(String utteranceId) {
                }
            });
            if(resultLang==tts.LANG_NOT_SUPPORTED || resultLang==tts.LANG_MISSING_DATA)
                Toast.makeText(getApplicationContext(), "Bu özellik telefonunuzda desteklenmiyor.",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Konuşmada hata!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, menu.class);
        startActivity(intent);
        tts.stop();
        tts.shutdown();

    }

}
