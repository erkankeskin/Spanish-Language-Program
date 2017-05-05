package com.guvenluckyboy.spanishlearn;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class dinlePage extends AppCompatActivity implements TextToSpeech.OnInitListener {
    TextToSpeech tts;
    EditText ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dinle_page);
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

        ed= (EditText)findViewById(R.id.Text);
        tts = new TextToSpeech(this,this);
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            int resultLang=tts.setLanguage(new Locale("es-ES"));
            if(resultLang==tts.LANG_NOT_SUPPORTED || resultLang==tts.LANG_MISSING_DATA)
                Toast.makeText(getApplicationContext(), "Bu özellik telefonunuzda desteklenmiyor.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Konuşmada hata!",Toast.LENGTH_SHORT).show();
        }
    }
    public void clk(View view){
        String text =ed.getText().toString();
        if(text!="")
            tts.speak(text,TextToSpeech.QUEUE_FLUSH, null);
        else
            Toast.makeText(getApplicationContext(), "Alan boş bırakılamaz !!",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
        tts.stop();
        tts.shutdown();

    }
}
