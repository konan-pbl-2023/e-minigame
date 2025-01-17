package com.example.prototypeapi22;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.Path;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Handler;
import android.view.Gravity;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.view.MotionEvent;
import android.graphics.Matrix;
import android.view.ViewGroup;

import org.w3c.dom.Text;

//スコアとかやったこと次第でエンディングの文章変えるとか
//絵とか入れなければ実装自体はとっても楽だけど何をしたかのフラグ管理を
//全プログラムに伝達できるようにしないといけない、ちょっとめんどい...
//とりあえず仮でよくある下から流れてくる形式のものを置いておきます
public class Ending extends AppCompatActivity {
    int tap;
    int gamestart = 0;
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    TextView ending;
    int endingx = 200; //文字の初期位置
    int endingy = 1920;//文字の大きさによって色々変わるので都度実行して調整
    float endingScale = (float)1.5; //文字の大きさ
    //少数を入れる場合は(float)を数字の前につけないと動かないので注意
    int endingspeed = 2; //文字の流れる速さ 2予定
    SoundPool soundPool;
    MediaPlayer mainbgm;
    int MapID;
    int HP;
    int Score;
    int Clearflag;
    int nomiss;
    Button titleButton;
    int buttonse;
    int buttony;
    ImageView wback;
    TextView sosite;
    ImageView hito1;
    int hitoy;
    int dragonx;
    int dragony;
    ImageView dragon;
    ImageView hito2;
    ImageView an;
    ImageView box;
    int boxy;
    ImageView same;
    int samey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);
        Intent intent = getIntent();
        MapID = intent.getIntExtra("MapID",0);
        HP = intent.getIntExtra("HPgive", 100);
        Score = intent.getIntExtra("Scoregive",0);
        Clearflag = intent.getIntExtra("Clearflag",1); //Clearflagに関しては受け取る意味ないと思うけど
        nomiss = intent.getIntExtra("nomissflag",0); //２エンド分けたかったけど明後日までに揃うんか...？
        //nomiss = 0;
        wback = findViewById(R.id.wback);
        sosite = findViewById(R.id.sosite);
        wback.setScaleX(100);
        wback.setScaleY(100);
        sosite.setScaleX(4);
        sosite.setScaleY(4);
        wback.setColorFilter(Color.rgb(255,255,255));
        sosite.setText("そして...");
        dragon = findViewById(R.id.dragon);
        dragon.setScaleX(4);
        dragon.setScaleY(4);
        dragon.setX(3000);
        //Homeボタンなど消しのおまじない
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
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
        buttonse = soundPool.load(this,R.raw.buttonse,0);
        ending = findViewById(R.id.ending);
        ending.setScaleX(endingScale); //文字の大きさ
        ending.setScaleY(endingScale);
        an = findViewById(R.id.an);
        an.setScaleX(3);
        an.setScaleY(3);
        an.setY(-650);
        an.setX(0);
        //ここにif文を挟んでエンディング分岐簡単に！
        if(nomiss == 0) {
            ending.setText("ノーミスゲームクリア！！\n" +
                    "すごい！\n"+
                    "あなたのおかげで町が綺麗になったよ！\n" +
                    "ここまでプレイしてくれてありがとう！\n" +
                    "\n残りHP\n\n" +
                    HP+"\n\n"+
                    "最終Score\n\n"+
                    Score+"\n\n"+
                    "スタッフ(五十音＆敬称略)\n" +
                    "マネージャー\n" +
                    "\n" +
                    "山田\n\n" +
                    "プログラマ\n" +
                    "\n" +
                    "陳\n" +
                    "前田\n" +
                    "山田\n" +
                    "\nコンテンツ\n\n" +
                    "井本\n" +
                    "森井\n\n" +
                    "スペシャルサンクス\n\n" +
                    "新田先生\nTAの方々\n画像、音声引用元\n" +
                    "情報システム室\n\n\n" +
                    "おわり");
        }else{
            ending.setText("ゲームクリア\n" +
                    "おめでとう！\n"+
                    "あなたのおかげで町が綺麗になったよ！\n" +
                    "ここまでプレイしてくれてありがとう！\n" +
                    "\n残りHP\n\n" +
                    HP+"\n\n"+
                    "最終Score\n\n"+
                    Score+"\n\n"+
                    "スタッフ(五十音＆敬称略)\n" +
                    "マネージャー\n" +
                    "\n" +
                    "山田\n\n" +
                    "プログラマ\n" +
                    "\n" +
                    "陳\n" +
                    "前田\n" +
                    "山田\n" +
                    "\nコンテンツ\n\n" +
                    "井本\n" +
                    "森井\n\n" +
                    "スペシャルサンクス\n\n" +
                    "新田先生\nTAの方々\n画像、音声引用元\n" +
                    "情報システム室\n\n\n" +
                    "おわり");
        }
        titleButton = findViewById(R.id.titlebutton);
        titleButton.setY(3000);
        titleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                soundPool.play(buttonse, 3.0f, 3.0f, 0, 0, 1.0f);
                Intent intent = new Intent(getApplication(), MainActivity.class);
                //intent.putExtra("RoomID", 0);
                //intent.putExtra("HPgive",100);
                //intent.putExtra("Scoregive",0);
                intent.putExtra("Clearflag",Clearflag);
                //mainbgm.stop();
                mainbgm.release();
                startActivity(intent);

            }


        });
        BGM();
        buttony = 4900;
        hito1 = findViewById(R.id.hito1);
        hito1.setScaleX(0.5f);
        hito1.setScaleY(0.5f);
        hito1.setX(5000);
        hito1.setY(300);
        hito2 = findViewById(R.id.hito2);
        hito2.setY(1100);
        hito2.setX(-50);

        box = findViewById(R.id.box);
        same = findViewById(R.id.same);
        same.setX(3000);
        box.setX(3000);
        titleButton.setText("タイトルへ戻る");
        //createここまで
    }

    public void BGM(){
        //仮置きでmainbgmを置いています(ほかのステージで使ったやつ)

        mainbgm = MediaPlayer.create(this,R.raw.en);

        mainbgm.start();
    }

    public void game(){
        endingy -= endingspeed;
        ending.setY(endingy);
        buttony -= endingspeed;
        hitoy -= endingspeed;
        hito1.setY(hitoy);
        hito1.setX(500);

        titleButton.setY(buttony);
        if(buttony < 1600){
            timer.cancel();
        }
        dragony -= endingspeed;
        dragon.setY(dragony);
        boxy -= endingspeed;
        box.setY(boxy);
        samey -= endingspeed;
        same.setY(samey);
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {


        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            tap = 1;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            tap = 0;

        }
        if (gamestart == 0) { //最初だけgameを起動
            titleButton.setY(buttony);
            gamestart = 1;
            ending.setX(endingx); //文字の初期位置
            ending.setY(endingy);
            sosite.setX(3000);
            wback.setX(30000);
            hitoy = 2600;
            dragon.setX(700);
            dragon.setY(2600);
            an.setX(3000);
            hito2.setX(3000);
            dragony = 2600;
            box.setX(550);
            box.setY(1900);
            boxy = 1900;
            box.setScaleX(0.5f);
            box.setScaleY(0.5f);
            same.setX(500);
            same.setScaleX(12);
            same.setScaleY(6);
            same.setY(4300);
            samey = 4500;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            game();
                        }
                    });
                }
            }, 0, 5);
        }
        return true;
    }
}