package com.guvenluckyboy.spanishlearn;

import android.content.Intent;
import android.database.Cursor;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class menu extends AppCompatActivity {
    ImageView[] imageList=new ImageView[12];
    DatabaseHelper myDb;
    int grade;
    int max[]={14,12,14,12};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
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

        imageList[0]=(ImageView) findViewById(R.id.imageView1);
        imageList[1]=(ImageView) findViewById(R.id.imageView2);
        imageList[2]=(ImageView) findViewById(R.id.imageView3);
        imageList[3]=(ImageView) findViewById(R.id.imageView4);
        imageList[4]=(ImageView) findViewById(R.id.imageView5);
        imageList[5]=(ImageView) findViewById(R.id.imageView6);
        imageList[6]=(ImageView) findViewById(R.id.imageView7);
        imageList[7]=(ImageView) findViewById(R.id.imageView8);
        imageList[8]=(ImageView) findViewById(R.id.imageView9);
        imageList[9]=(ImageView) findViewById(R.id.imageView10);
        imageList[10]=(ImageView) findViewById(R.id.imageView11);
        imageList[11]=(ImageView) findViewById(R.id.imageView12);

        for(int i=1;i<5;i++) {
            myDb = new DatabaseHelper(this);
            Cursor res = myDb.getData(myDb.cumleLevel, i);
            res.moveToPosition(0);
            int cumleLevel = res.getInt(1);

            res = myDb.getData(myDb.kelimeLevel, i);
            res.moveToPosition(0);
            int kelimeLevel = res.getInt(1);

            Log.d("level",Integer.toString(i));
            res = myDb.getDialogLevel(i, 3);
            res.moveToPosition(0);
            int dialogLevel = res.getInt(2);

            if(!(kelimeLevel>=10 || cumleLevel>=10 || dialogLevel>=max[i-1])){
                grade = i;
                break;
            }
            else if(i==4)
                grade=4;
        }

        //grade=2;
        for(int i=(grade)*3;i<12;i++){
            imageList[i].setAlpha(0.4f);
            imageList[i].setClickable(false);
        }

    }



    public void clickBubble1(View v) {
        Intent intent;
        switch(v.getId()) {
            case R.id.imageView1:
                intent = new Intent(this, kelime.class);
                intent.putExtra("grade", 1);
                startActivity(intent);
                break;
            case R.id.imageView2:
                intent = new Intent(this, cumleyerlestirme.class);
                intent.putExtra("grade", 1);
                startActivity(intent);
                break;
            case R.id.imageView3:
                intent = new Intent(this, dialog.class);
                intent.putExtra("grade", 1);
                startActivity(intent);
                break;
            case R.id.imageView4:
                intent = new Intent(this, kelime.class);
                intent.putExtra("grade", 2);
                startActivity(intent);
                break;
            case R.id.imageView5:
                intent = new Intent(this, cumleyerlestirme.class);
                intent.putExtra("grade", 2);
                startActivity(intent);
                break;
            case R.id.imageView6:
                intent = new Intent(this, dialog.class);
                intent.putExtra("grade", 2);
                startActivity(intent);
                break;
            case R.id.imageView7:
                intent = new Intent(this, kelime.class);
                intent.putExtra("grade", 3);
                startActivity(intent);
                break;
            case R.id.imageView8:
                intent = new Intent(this, cumleyerlestirme.class);
                intent.putExtra("grade", 3);
                startActivity(intent);
                break;
            case R.id.imageView9:
                intent = new Intent(this, dialog.class);
                intent.putExtra("grade", 3);
                startActivity(intent);
                break;
            case R.id.imageView10:
                intent = new Intent(this, kelime.class);
                intent.putExtra("grade", 4);
                startActivity(intent);
                break;
            case R.id.imageView11:
                intent = new Intent(this, cumleyerlestirme.class);
                intent.putExtra("grade", 4);
                startActivity(intent);
                break;
            case R.id.imageView12:
                intent = new Intent(this, dialog.class);
                intent.putExtra("grade", 4);
                startActivity(intent);
                break;

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);

    }
}
