package com.guvenluckyboy.spanishlearn;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class sablon extends AppCompatActivity {
    public TextView resultTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sablon);

        resultTV = (TextView)findViewById(R.id.result);

    }

    public void promptSpeech(View view) {
        Intent i= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something!");

        try{
            this.startActivityForResult(i, 100);
            //resultTV.setText(i.getStringExtra(RecognizerIntent.EXTRA_RESULTS));
            Toast.makeText(sablon.this, "Başarılı",Toast.LENGTH_SHORT).show();
        }catch(ActivityNotFoundException a){
            Toast.makeText(sablon.this, "Sorry!!!!", Toast.LENGTH_LONG).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 100:
                if(resultCode == -1 && data != null) {
                    ArrayList result = data.getStringArrayListExtra("android.speech.extra.RESULTS");
                    this.resultTV.setText((CharSequence)result.get(0));
                }
            default:
        }
    }
}
