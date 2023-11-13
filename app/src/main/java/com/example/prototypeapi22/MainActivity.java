package com.example.prototypeapi22;

// タイトル画面予定地
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
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
    MediaPlayer mainbgm;
    SoundPool soundPool;
    int buttonse;
    ImageView titleimg;
    TextView titletxt;
    TextView titletxt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Homeボタンなど消しのおまじない
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //↓効果音を入れるおまじない
        //理解する時間ないので8割コピペ
        //https://akira-watson.com/android/soundpool.html
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                // USAGE_MEDIA
                // USAGE_GAME
                .setUsage(AudioAttributes.USAGE_GAME)
                // CONTENT_TYPE_MUSIC
                // CONTENT_TYPE_SPEECH, etc.
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                // ストリーム数に応じて
                .setMaxStreams(10)
                .build();
        okButton = (Button) findViewById(R.id.okbutton);
        BGM();
        buttonse = soundPool.load(this,R.raw.buttonse,0);
        titleimg = findViewById(R.id.titleimg);
        titleimg.setScaleY(1.2f);
        okButton.setY(800);
        okButton.setText("はじめる");
        titletxt = findViewById(R.id.titletxt);
        titletxt.setScaleX(3);
        titletxt.setScaleY(3);
        titletxt.setText("タイトル(仮)");
        titletxt2 = findViewById(R.id.titletxt2);
        titletxt2.setScaleX(2);
        titletxt2.setScaleY(2);
        titletxt2.setY(-300);
        titletxt2.setX(-30);
        titletxt2.setText("制作：Team_E");
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                soundPool.play(buttonse, 3.0f, 3.0f, 0, 0, 1.0f);
                Intent intent = new Intent(getApplication(), Map.class);
                intent.putExtra("RoomID", 0);
                intent.putExtra("HPgive",100);
                intent.putExtra("Scoregive",0);

                mainbgm.stop();
                startActivity(intent);

            }


        });


    } //onCreate終わり
    public void BGM(){
        mainbgm = MediaPlayer.create(this,R.raw.mainbgm);

        mainbgm.start();
    }




}
