package com.example.prototypeapi22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

//GameOver画面
//GameOverになった原因とフレーバーテキストと
//復帰ボタンと
public class GameOver extends AppCompatActivity {
    TextView gameover;
    int HP;

    int Score;
    int MapID;
    int prevscore;
    int nowscore;
    TextView because;
    Button mapbutton;
    Button stagebutton;
    Button titlebutton;
    int Clearflag;
    ImageView back;
    SoundPool soundPool;
    MediaPlayer bgm;
    Button kakunin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        //Homeボタンなど消しのおまじない
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        gameover = findViewById(R.id.gameover);
        because = findViewById(R.id.because);
        Intent intent = getIntent();
        HP = intent.getIntExtra("HPgive", 0);
        Score = intent.getIntExtra("Scoregive",0);
        MapID = intent.getIntExtra("MapID",0);
        Clearflag = intent.getIntExtra("Clearflag",0);
        mapbutton = findViewById(R.id.mapbutton);
        stagebutton = findViewById(R.id.stagebutton);
        titlebutton = findViewById(R.id.titlebutton);
        stagebutton.setText("元のステージをやり直す");
        mapbutton.setText("マップに戻る");
        titlebutton.setText("タイトルに戻る(初めから)");
        back = findViewById(R.id.back);
        back.setScaleX(1.65f);
        back.setScaleY(1.65f);
        prevscore = intent.getIntExtra("Prevscore",0);
        if(MapID == 5 && HP >= 0 && Score - prevscore >= 10000){
            titlebutton.setText("エンディングに行く");
        }
        nowscore = Score - prevscore;
        mapbutton.setBackgroundColor(Color.rgb(0,255,0));
        mapbutton.setTextColor(Color.rgb(0,0,0));
        stagebutton.setBackgroundColor(Color.rgb(0,0,255));
        titlebutton.setBackgroundColor(Color.rgb(255,0,0));
        kakunin = findViewById(R.id.kakunin);
        kakunin.setText("本当に戻りますか？");
        kakunin.setX(3000);
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
        if(HP <= 0 || Score - prevscore <= 9999){
            BGM();
        }else{
            BGM2();
        }
        //jumpse = soundPool.load(this,R.raw.taa,0);
        if(MapID == 5 && HP >= 0 && Score - prevscore >= 10000){
            mapbutton.setX(2000);
            stagebutton.setX(2000);
            titlebutton.setY(-400);
            titlebutton.setBackgroundColor(Color.rgb(50,50,255));
        }

        //来たステージに戻る
        stagebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(MapID == 1 ) {
                    Intent intent = new Intent(getApplication(), Stage_1.class);
                    if(HP <= 0) {
                        intent.putExtra("HPgive", 100);
                    }else{
                        intent.putExtra("HPgive",HP);
                    }
                    intent.putExtra("Scoregive",Score);
                    intent.putExtra("Clearflag",Clearflag);
                    bgm.release();
                    startActivity(intent);

                }
                if(MapID == 2 ) {
                    Intent intent = new Intent(getApplication(), Stage_2.class);
                    if(HP <= 0) {
                        intent.putExtra("HPgive", 100);
                    }else{
                        intent.putExtra("HPgive",HP);
                    }
                    intent.putExtra("Scoregive",Score);
                    intent.putExtra("Clearflag",Clearflag);
                    bgm.release();
                    startActivity(intent);

                }
                if(MapID == 3 ) {
                    Intent intent = new Intent(getApplication(), Stage_3.class);
                    if(HP <= 0) {
                        intent.putExtra("HPgive", 100);
                    }else{
                        intent.putExtra("HPgive",HP);
                    }
                    intent.putExtra("Scoregive",Score);
                    intent.putExtra("Clearflag",Clearflag);
                    bgm.release();
                    startActivity(intent);

                }
                if(MapID == 4) {
                    Intent intent = new Intent(getApplication(), Stage_4.class);
                    if(HP <= 0) {
                        intent.putExtra("HPgive", 100);
                    }else{
                        intent.putExtra("HPgive",HP);
                    }
                    intent.putExtra("Scoregive",Score);
                    intent.putExtra("Clearflag",Clearflag);
                    bgm.release();
                    startActivity(intent);

                }
                if(MapID == 5 ) {
                    Intent intent = new Intent(getApplication(), Stage_5.class);
                    if(HP <= 0) {
                        intent.putExtra("HPgive", 100);
                    }else{
                        intent.putExtra("HPgive",HP);
                    }
                    intent.putExtra("Scoregive",Score);
                    intent.putExtra("Clearflag",Clearflag);
                    bgm.release();
                    startActivity(intent);

                }
            }
        });//マップに戻る
        mapbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getApplication(), Map.class);
                if(HP <= 0) {
                    intent.putExtra("HPgive", 100);
                }else{
                    intent.putExtra("HPgive",HP);
                }
                intent.putExtra("Scoregive",Score);
                intent.putExtra("MapID",MapID);
                intent.putExtra("Clearflag",Clearflag);
                bgm.release();
                startActivity(intent);


            }
        });//タイトルに戻る
        titlebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if(MapID == 5 && HP >= 0 && Score - prevscore >= 10000) {
                    Intent intent = new Intent(getApplication(), Ending.class);
                    intent.putExtra("HPgive", HP);
                    intent.putExtra("Scoregive", Score);
                    bgm.release();
                    startActivity(intent);
                }else {
                    kakunin.setX(300);
                    kakunin.setY(1572);
                    //Intent intent = new Intent(getApplication(), MainActivity.class);
                    //bgm.release();
                    //startActivity(intent);
                }
            }
        });

        kakunin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                bgm.release();
                startActivity(intent);

            }

        });
        if(MapID == 1){
            gameover.setScaleX(4);
            gameover.setScaleY(4);
            because.setScaleX(2);
            because.setScaleY(2);

            if(HP <= 0) {
                gameover.setText("Game Over!!!");
                because.setText("サメさんは強かったね\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
                stagebutton.setText("HPを回復して元のステージをやり直す");
                mapbutton.setText("HPを回復してマップに戻る");
            }else if(Score - prevscore <10000){
                gameover.setText("Game Over!!!");
                because.setText("もう少し頑張りましょう\n10000点ぐらいは\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
            }else{
                gameover.setText("Game Clear!!!");
                because.setText("よくできました\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
            }
        }
        if(MapID == 2){
            gameover.setScaleX(4);
            gameover.setScaleY(4);
            because.setScaleX(2);
            because.setScaleY(2);

            if(HP <= 0) {
                gameover.setText("Game Over!!!");
                because.setText("誰だ鉄球落としたの\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
                stagebutton.setText("HPを回復して元のステージをやり直す");
                mapbutton.setText("HPを回復してマップに戻る");
            }else if(Score - prevscore <10000){
                gameover.setText("Game Over!!!");
                because.setText("もう少し頑張りましょう\n10000点ぐらいは\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
            }else{
                gameover.setText("Game Clear!!!");
                because.setText("よくできました\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
            }
        }
        //Stage_3のゲームオーバーテキスト
        if(MapID == 3){
            gameover.setScaleX(4);
            gameover.setScaleY(4);
            because.setScaleX(2);
            because.setScaleY(2);


            if(HP <= 0) {
                gameover.setText("Game Over!!!");
                because.setText("とげとげ、いたい\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
                stagebutton.setText("HPを回復して元のステージをやり直す");
                mapbutton.setText("HPを回復してマップに戻る");
            }else if(Score - prevscore <10000){
                gameover.setText("Game Over!!!");
                because.setText("もう少し頑張りましょう\n10000点ぐらいは\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
            }else{
                gameover.setText("Game Clear!!!");
                because.setText("よくできました\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
            }
        }
        //Stage_4のゲームオーバーテキスト
        if(MapID == 4){
            gameover.setScaleX(4);
            gameover.setScaleY(4);
            because.setScaleX(2);
            because.setScaleY(2);

            if(HP <= 0) {
                gameover.setText("Game Over!!!");
                because.setText("ポイをつけすぎっぽい\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
                stagebutton.setText("HPを回復して元のステージをやり直す");
                mapbutton.setText("HPを回復してマップに戻る");
            }else if(Score - prevscore <10000){
                gameover.setText("Game Over!!!");
                because.setText("もう少し頑張りましょう\n10000点ぐらいは\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
            }else{
                gameover.setText("Game Clear!!!");
                because.setText("よくできました\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
            }
        }
        //Stage_5のゲームオーバーテキスト
        if(MapID == 5){
            gameover.setScaleX(4);
            gameover.setScaleY(4);
            because.setScaleX(2);
            because.setScaleY(2);

            if(HP <= 0) {
                gameover.setText("Game Over!!!");
                because.setText("ドラゴンに当たると大ダメージ\n離れて戦いましょう\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
                stagebutton.setText("HPを回復して元のステージをやり直す");
                mapbutton.setText("HPを回復してマップに戻る");
            }else if(Score - prevscore <10000){
                gameover.setText("Game Over!!!");
                because.setText("ドラゴンの一瞬止まるところに\n狙いを定めましょう\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
            }else{
                gameover.setText("Game Clear!!!");
                //because.setTextColor(Color.rgb(255,0,0));
                because.setText("あなたは\n世界最強の清掃員!\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
            }
        }

    }
    public void BGM(){ //仮置き
        bgm = MediaPlayer.create(this,R.raw.n74);

        bgm.start();
    }
    public void BGM2(){
        bgm = MediaPlayer.create(this,R.raw.n11);
        bgm.start();
    }

}