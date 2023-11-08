package com.example.prototypeapi22;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.Path;
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
    int endingspeed = 5; //文字の流れる速さ
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);
        //Homeボタンなど消しのおまじない
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ending = findViewById(R.id.ending);
        ending.setScaleX(endingScale); //文字の大きさ
        ending.setScaleY(endingScale);
        //ここにif文を挟んでエンディング分岐簡単に！
        ending.setText("テスト文\nで改行" +
                "横に長すぎるようならこうやっても" +
                "\n問題なく繋がる");

        //ifここまで
    }

    public void game(){
        endingy -= endingspeed;
        ending.setY(endingy);
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {


        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            tap = 1;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            tap = 0;

        }
        if (gamestart == 0) { //最初だけgameを起動

            gamestart = 1;
            ending.setX(endingx); //文字の初期位置
            ending.setY(endingy);
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