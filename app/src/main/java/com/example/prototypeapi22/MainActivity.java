package com.example.prototypeapi22;

// タイトル画面予定地
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {
    int i = 0;
    int fl = 0;
    int time = 0;
    private int ballY;
    private ImageView im;
    private ImageView im2;
    private TextView txt;
    Button okButton;
    int z = 0;
    int cnt = 0;
    int fl2 = 0;
    private Handler handler = new Handler();
    Timer timer = new Timer();

    int imageWidth = 0;
    int imageHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Homeボタンなど消しのおまじない
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        okButton = (Button) findViewById(R.id.okbutton);

        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getApplication(), Map.class);
                intent.putExtra("RoomID", 0);
                intent.putExtra("HPgive",100);
                intent.putExtra("Scoregive",0);


                startActivity(intent);

            }


        });


    } //onCreate終わり





}
