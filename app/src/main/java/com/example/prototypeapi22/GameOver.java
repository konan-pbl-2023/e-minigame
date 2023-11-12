package com.example.prototypeapi22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
        if(MapID == 5 && HP >= 0 && Score - prevscore >= 10000){
            titlebutton.setText("エンディングに行く");
        }
        prevscore = intent.getIntExtra("Prevscore",0);
        nowscore = Score - prevscore;
        if(MapID == 5 && HP >= 0 && Score - prevscore >= 10000){
            mapbutton.setX(2000);
            stagebutton.setX(2000);
            titlebutton.setY(-400);
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

                    startActivity(intent);


            }
        });//タイトルに戻る
        titlebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if(MapID == 5 && HP >= 0 && Score - prevscore >= 10000) {
                    Intent intent = new Intent(getApplication(), Ending.class);
                    intent.putExtra("HPgive", HP);
                    intent.putExtra("Scoregive", Score);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                }
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
                because.setText("かりおき\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
                stagebutton.setText("HPを回復して元のステージをやり直す");
                mapbutton.setText("HPを回復してマップに戻る");
            }else if(Score - prevscore <10000){
                gameover.setText("Game Over!!!");
                because.setText("かりおき\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
            }else{
                gameover.setText("Game Clear!!!");
                because.setText("かりおき\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
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
                because.setText("かりおき\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
                stagebutton.setText("HPを回復して元のステージをやり直す");
                mapbutton.setText("HPを回復してマップに戻る");
            }else if(Score - prevscore <10000){
                gameover.setText("Game Over!!!");
                because.setText("かりおき\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
            }else{
                gameover.setText("Game Clear!!!");
                because.setText("かりおき\nHP:" + HP + "\n今回のScore:"+ nowscore + "\n合計Score:" + Score);
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

}