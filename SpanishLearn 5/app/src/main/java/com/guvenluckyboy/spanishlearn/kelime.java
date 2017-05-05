package com.guvenluckyboy.spanishlearn;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static com.guvenluckyboy.spanishlearn.R.id.toolbar;

public class kelime extends AppCompatActivity implements TextToSpeech.OnInitListener{
    public TextView resultTV;
    DatabaseHelper myDb;
    int dbLevel,grade;
    String kelime,anlam;

    TextToSpeech tts;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelime);
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

        resultTV = (TextView)findViewById(R.id.resultTV);
        Typeface mytf = Typeface.createFromAsset(getAssets(), "default.ttf");
        resultTV.setTypeface(mytf);
        tts=new TextToSpeech(this,this);

        grade = getIntent().getIntExtra("grade",1);

        myDb = new DatabaseHelper(this);
        Cursor res = myDb.getData(myDb.kelimeLevel, grade);
        res.moveToPosition(0);
        dbLevel = res.getInt(1);

        if(dbLevel>=10){
            Intent intent = new Intent(this, finish.class);
            startActivity(intent);
            return;
        }

        res = myDb.getData(myDb.kelimeStrings, (grade-1)*10 + dbLevel + 1);
        res.moveToPosition(0);
        kelime = res.getString(1);
        anlam = res.getString(2);

        /*ImageView image = new ImageView(kelime.this);
        image.setBackgroundResource(R.drawable.bg);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(120,120);

        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);

        image.setLayoutParams(lp);
*/



        String uri;
        if(kelime.equals("café"))
             uri = "@drawable/cafe";
        else if(kelime.equals("sillón"))
            uri = "@drawable/sillon";
        else
            uri = "@drawable/"+kelime.toLowerCase();



        int resource = getResources().getIdentifier(uri, "drawable", this.getPackageName());
        ImageView image= (ImageView) findViewById(R.id.kelimePic);
        /*String x="R.drawable.bg";*/
        image.setImageResource(resource);


        tv = (TextView)findViewById(R.id.result);
        tv.setText(kelime+" = "+ anlam);
        tv.setTypeface(mytf);
        //tts = new TextToSpeech(this, (TextToSpeech.OnInitListener) this);

    }

    public void promptSpeech(View view) {
        Intent i= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, kelime);

        try{
            this.startActivityForResult(i, 100);
            //resultTV.setText(i.getStringExtra(RecognizerIntent.EXTRA_RESULTS));
            //Toast.makeText(kelime.this, "Başarılı",Toast.LENGTH_SHORT).show();
        }catch(ActivityNotFoundException a){
            Toast.makeText(kelime.this, "Sorry!!!!", Toast.LENGTH_LONG).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 100:
                if(resultCode == -1 && data != null) {
                    ArrayList result = data.getStringArrayListExtra("android.speech.extra.RESULTS");
                    this.resultTV.setText((CharSequence)result.get(0));
                    String answer = kelime;
                    answer = answer.replace("!", "").replace("?", "").replace(".", "").replace(",", "").replace("¡", "").replace("¿", "");
                    if (answer.toLowerCase().equals(result.get(0).toString().toLowerCase())) {
                        this.resultTV.setText("Sizin dediğiniz:\n"+ (CharSequence)result.get(0));
                        Toast.makeText(kelime.this, "Doğru cevap",Toast.LENGTH_LONG).show();
                        trueAns();
                    }
                    else {
                        this.resultTV.setText("Sizin dediğiniz:\n" + (CharSequence) result.get(0));
                        Toast.makeText(kelime.this, "Yanlış cevap!!", Toast.LENGTH_LONG).show();
                    }
                }

            default:
        }
    }

    /*public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            int resultLang=tts.setLanguage(new Locale("es-ES"));
            if(resultLang==tts.LANG_NOT_SUPPORTED || resultLang==tts.LANG_MISSING_DATA)
                Toast.makeText(getApplicationContext(), "Bu özellik telefonunuzda desteklenmiyor.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Konuşmada hata!",Toast.LENGTH_SHORT).show();
        }
    }*/
    public void trueAns(){

        Log.d("level", Integer.toString(dbLevel));
        myDb.updatekelimelevel(grade, dbLevel + 1);
        Intent intent = new Intent(this, kelime.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("grade", grade);
        startActivity(intent);

    }

    public void helpMe(View view){
        tts.speak(kelime, TextToSpeech.QUEUE_FLUSH, null);
    }


    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            int resultLang=tts.setLanguage(new Locale("es-ES"));
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



