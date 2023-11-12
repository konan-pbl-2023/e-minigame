package com.example.prototypeapi22;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

//Map画面予定
//Map画像を読み込んでマリオみたく？　てくてく歩いてもらって
//...って感じ、うちの作った8方向キーを使えば相互移動可能かも
//それでスコア集めたらいいエンディングになるとか...
//ラスボス戦もあるし時間ないかも


public class Map extends AppCompatActivity {


    int tapx = 0;
    int tapy = 0;

    public TextView scoretext;
    public TextView hptext;
    public ImageView hidariue;
    public ImageView ue;
    public ImageView migiue;
    public ImageView migi;
    public ImageView migisita;
    public ImageView sita;
    public ImageView hidarisita;
    public ImageView hidari;
    public ImageView mannaka;
    float bscale = 2; //ボタンの大きさ
    int bx;
    int by;
    float intex;
    float intey;
    int gamestart = 0;
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    float movex = 0;
    float movey = 0;
    //ボタン処理
    int movenum = 0; // 左上→1、上→2、右上3、右4、右下5、下6、左下7、左8、入力なし0
    int tap = 0; // 0 = 押してない、1 = 押している
    int buttontoumeido = 70; //ボタンの透明度、0で完全に透明、255で完全に不透明
//intだとなぜかsetAlphaに打ち消し線入るけどちゃんと動く
//floatだと打ち消し線入らないのにちゃんと動かない、謎


//ボタン処理終わり
    ImageView hito;
    int hitox = 0; //人の初期位置、受け取ったMapID(どのプログラムからここに)
    int hitoy = 0;//飛んだかによって変更
    int hitomoves = 3; //人の移動速度
    ImageView stage1;
    ImageView stage2;
    ImageView stage3;
    ImageView stage4;
    ImageView stage5;
    
    int stagex[] = new int[5];
    int stagey[] = new int[5];
    int stagemoveflag = 0;
    TextView load;
    int MapID;
    int HP;
    int Score;
    ImageView map;
    int Clearflag;
    int ssize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //Homeボタンなど消しのおまじない
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Intent intent = getIntent();
        MapID = intent.getIntExtra("MapID",0);
        HP = intent.getIntExtra("HPgive", 0);
        Score = intent.getIntExtra("Scoregive",0);
        Clearflag = intent.getIntExtra("Clearflag",1);
        //1　→ *2 ;2 → 3;3→5;4→7
        hptext = findViewById(R.id.hp);
        scoretext = findViewById(R.id.score);
        hptext.setText("HP:"+HP);
        scoretext.setText("Score:"+Score);
        hito = findViewById(R.id.hito);


        //ボタン機能
        hidariue = findViewById(R.id.hidariue);
        ue = findViewById(R.id.ue);
        migiue = findViewById(R.id.migiue);
        migi = findViewById(R.id.migi);
        migisita = findViewById(R.id.migisita);
        sita = findViewById(R.id.sita);
        hidarisita = findViewById(R.id.hidarisita);
        hidari = findViewById(R.id.hidari);
        mannaka = findViewById(R.id.mannaka);
        //ボタンのデフォルト設定
        bx = 600; //buttonX
        by = 1500; //buttonY
        intex = 84 * bscale; //boxの大きさ
        intey = 73 * bscale;
        hidariue.setScaleX(bscale);
        hidariue.setScaleY(bscale);
        hidariue.setAlpha(buttontoumeido);
        ue.setScaleX(bscale);
        ue.setScaleY(bscale);
        ue.setAlpha(buttontoumeido);
        migiue.setScaleX(bscale);
        migiue.setScaleY(bscale);
        migiue.setAlpha(buttontoumeido);
        migi.setScaleX(bscale);
        migi.setScaleY(bscale);
        migi.setAlpha(buttontoumeido);
        migisita.setScaleX(bscale);
        migisita.setScaleY(bscale);
        migisita.setAlpha(buttontoumeido);
        sita.setScaleX(bscale);
        sita.setScaleY(bscale);
        sita.setAlpha(buttontoumeido);
        hidarisita.setScaleX(bscale);
        hidarisita.setScaleY(bscale);
        hidarisita.setAlpha(buttontoumeido);
        hidari.setScaleX(bscale);
        hidari.setScaleY(bscale);
        hidari.setAlpha(buttontoumeido);
        mannaka.setScaleX(bscale);
        mannaka.setScaleY(bscale);
        mannaka.setAlpha(150);
        //ボタン処理ここまで

        stage1 = findViewById(R.id.stage1);
        stage2 = findViewById(R.id.stage2);
        stage3 = findViewById(R.id.stage3);
        stage4 = findViewById(R.id.stage4);
        stage5 = findViewById(R.id.stage5);

        ssize = 2;

        stagex[0] = 780; //仮置きのステージの座標
        stagex[1] = 350;
        stagex[2] = 220;
        stagex[3] = 830;
        stagex[4] = 720;
        stagey[0] = 1750; //仮置きのステージの座標
        stagey[1] = 900;
        stagey[2] = 1350;
        stagey[3] = 1050;
        stagey[4] = 560;

        stage1.setScaleX(ssize);
        stage1.setScaleY(ssize);
        stage2.setScaleX(ssize);
        stage2.setScaleY(ssize);
        stage3.setScaleX(ssize);
        stage3.setScaleY(ssize);
        stage4.setScaleX(ssize);
        stage4.setScaleY(ssize);
        stage5.setScaleX(ssize);
        stage5.setScaleY(ssize);


        if(Clearflag % 2 == 0 && Clearflag != 0) {
            stage1.setColorFilter(Color.rgb(0, 50, 250));
        }
        if(Clearflag % 3 == 0 && Clearflag != 0) {
            stage2.setColorFilter(Color.rgb(0, 50, 250));
        }
        if(Clearflag % 5 == 0 && Clearflag != 0) {
            stage3.setColorFilter(Color.rgb(0, 50, 250));
        }
        if(Clearflag % 7 == 0 && Clearflag != 0) {
            stage4.setColorFilter(Color.rgb(0, 50, 250));
        }
        if(Clearflag % 11 == 0 && Clearflag != 0) {
            stage5.setColorFilter(Color.rgb(0, 50, 250));
        }

        if(MapID == 0){ //初期位置
            hitox = 200;
            hitoy = 400;
        }else{
            hitox = stagex[MapID-1];
            hitoy = stagey[MapID-1] - 50;
        }

        load = findViewById(R.id.load); //ステージ遷移時の読み込みに使用
        load.setX(2000); //最初は見えない所に飛ばしておく
        map = findViewById(R.id.map);
        map.setScaleX((float)1.4);
        map.setScaleY((float)1.4);
        map.setX(100);
        map.setY(100);

    }
    public void game(){
        //ボタン処理開始
        float gosax = intex / 4; //なぜか若干誤差が出る(マウスカーソルの当たり判定とタップの判定が違う？)
        float gosay = intey / (float)4;  //ので調整用,大きさ2倍ならそれぞれinte(x or y)/4
        //当たり判定違う理由わかった、これ座標の基準の点真ん中じゃなくて左上だわ
        if (tap == 1) {
            if (tapx > bx - intex / 2 + gosax && tapx <= bx + intex / 2 + gosax) { //左の列だった場合
                if (tapy > by - intey / 2 + gosay && tapy <= by + intey / 2 + gosay) {
                    movenum = 1;
                } else if (tapy > by + intey / 2 + gosay && tapy <= by + intey * 3 / 2 + gosay) {
                    movenum = 8;
                } else if (tapy > by + intey * 3 / 2 + gosay && tapy < by + intey * 5 / 2 + gosay) {
                    movenum = 7;
                } else {
                    movenum = 0;
                }

            } else if (tapx > bx + intex / 2 + gosax && tapx <= bx + intex * 3 / 2 + gosax) { //真ん中の列だった場合
                if (tapy > by - intey / 2 + gosay && tapy <= by + intey / 2 + gosay) {
                    movenum = 2;
                } else if (tapy > by + intey * 3 / 2 + gosay && tapy < by + intey * 5 / 2 + gosay) {
                    movenum = 6;
                }else if(tapy > by + intey / 2 + gosay && tapy <= by + intey * 3 / 2 + gosay) {
                    movenum = 9;

                } else {
                    movenum = 0;
                }
            } else if (tapx > bx + intex * 3 / 2 + gosax && tapx <= bx + intex * 5 / 2 + gosax) { //右の列だった場合
                if (tapy > by - intey / 2 + gosay && tapy <= by + intey / 2 + gosay) {
                    movenum = 3;
                } else if (tapy > by + intey / 2 + gosay && tapy <= by + intey * 3 / 2 + gosay) {
                    movenum = 4;
                } else if (tapy > by + intey * 3 / 2 + gosay && tapy < by + intey * 5 / 2 + gosay) {
                    movenum = 5;
                } else {
                    movenum = 0;
                }


            } else {
                movenum = 0;
            }
        } else {
            movenum = 0;
        }
        hidariue.setColorFilter(Color.rgb(0, 0, 0));
        ue.setColorFilter(Color.rgb(0, 0, 0));
        migiue.setColorFilter(Color.rgb(0, 0, 0));
        migi.setColorFilter(Color.rgb(0, 0, 0));
        migisita.setColorFilter(Color.rgb(0, 0, 0));
        sita.setColorFilter(Color.rgb(0, 0, 0));
        hidarisita.setColorFilter(Color.rgb(0, 0, 0));
        hidari.setColorFilter(Color.rgb(0, 0, 0));
        mannaka.setColorFilter(Color.rgb(0,0,255));

        if (movenum == 1) {
            hidariue.setColorFilter(Color.rgb(255, 255, 0));
            hitox -= hitomoves;
            hitoy -= hitomoves;
        } else if (movenum == 2) {
            ue.setColorFilter(Color.rgb(255, 255, 0));
            hitoy -= hitomoves;
        } else if (movenum == 3) {
            migiue.setColorFilter(Color.rgb(255, 255, 0));
            hitox += hitomoves;
            hitoy -= hitomoves;
        } else if (movenum == 4) {
            migi.setColorFilter(Color.rgb(255, 255, 0));
            hitox += hitomoves;
        } else if (movenum == 5) {
            migisita.setColorFilter(Color.rgb(255, 255, 0));
            hitox += hitomoves;
            hitoy += hitomoves;
        } else if (movenum == 6) {
            sita.setColorFilter(Color.rgb(255, 255, 0));
            hitoy += hitomoves;
        } else if (movenum == 7) {
            hidarisita.setColorFilter(Color.rgb(255, 255, 0));
            hitox -= hitomoves;
            hitoy += hitomoves;
        } else if (movenum == 8) {
            hidari.setColorFilter(Color.rgb(255, 255, 0));
            hitox -= hitomoves;
        }
        stagemoveflag = 0;
        for(int i = 0; i < 5; i++){
            if(Math.abs(hitox + 40 - stagex[i] - 20) * Math.abs(hitox + 40 - stagex[i] - 20) + Math.abs(hitoy + 40 - stagey[i] - 20) * Math.abs(hitoy + 40 - stagey[i] - 20)< 5000){
                stagemoveflag = i+1;
                mannaka.setColorFilter(Color.rgb(200,0,0));
            }
        }
        if(stagemoveflag == 1 && movenum == 9){

            load.setX(200);
            load.setY(1500);
            load.setScaleX(2);
            load.setScaleY(2);
            load.setText("ロード中...\nしばし待たれよ!");
            timer.cancel();
            Intent intent = new Intent(getApplication(), Stage_1.class);


            intent.putExtra("HPgive", HP);
            intent.putExtra("Scoregive",Score);
            intent.putExtra("Clearflag",Clearflag);
            startActivity(intent);
        //}else{
        //    load.setText("テストなう");
        }else if(stagemoveflag == 2 && movenum == 9){

            load.setX(200);
            load.setY(1500);
            load.setScaleX(2);
            load.setScaleY(2);
            load.setText("ロード中...\nしばし待たれよ!");
            timer.cancel();
            Intent intent = new Intent(getApplication(), Stage_2.class);


            intent.putExtra("HPgive", HP);
            intent.putExtra("Scoregive",Score);
            intent.putExtra("Clearflag",Clearflag);
            startActivity(intent);
            //}else{
            //    load.setText("テストなう");
        }else if(stagemoveflag == 3 && movenum == 9){

            load.setX(200);
            load.setY(1500);
            load.setScaleX(2);
            load.setScaleY(2);
            load.setText("ロード中...\nしばし待たれよ!");
            timer.cancel();
            Intent intent = new Intent(getApplication(), Stage_3.class);


            intent.putExtra("HPgive", HP);
            intent.putExtra("Scoregive",Score);
            intent.putExtra("Clearflag",Clearflag);
            startActivity(intent);
            //}else{
            //    load.setText("テストなう");
        }else if(stagemoveflag == 4 && movenum == 9){

            load.setX(200);
            load.setY(1500);
            load.setScaleX(2);
            load.setScaleY(2);
            load.setText("ロード中...\nしばし待たれよ!");

            timer.cancel();
            Intent intent = new Intent(getApplication(), Stage_4.class);


            intent.putExtra("HPgive", HP);
            intent.putExtra("Scoregive",Score);
            intent.putExtra("Clearflag",Clearflag);
            startActivity(intent);
            //}else{
            //    load.setText("テストなう");
        }else if(stagemoveflag == 5 && movenum == 9){

            load.setX(200);
            load.setY(1500);
            load.setScaleX(2);
            load.setScaleY(2);
            load.setText("ロード中...\nしばし待たれよ!");
            timer.cancel();
            Intent intent = new Intent(getApplication(), Stage_5.class);


            intent.putExtra("HPgive", HP);
            intent.putExtra("Scoregive",Score);
            intent.putExtra("Clearflag",Clearflag);
            startActivity(intent);
            //}else{
            //    load.setText("テストなう");
        }


        hito.setX(hitox);
        hito.setY(hitoy);

    }//ループ終わり
    @Override
    public boolean onTouchEvent (MotionEvent event) {
        //ボタン関係の処理はじめ
        tapx = (int) event.getX();
        tapy = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            tap = 1;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            tap = 0;

        }
        if(gamestart == 0){ //最初だけgameを起動
            hito.setX(200);
            hito.setY(400);
            gamestart = 1;

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

        hidariue.setX(bx);
        hidariue.setY(by);
        ue.setX(bx + intex);
        ue.setY(by);
        migiue.setX(bx + intex * 2);
        migiue.setY(by);
        migi.setX(bx + intex * 2);
        migi.setY(by + intey);
        migisita.setX(bx + intex * 2);
        migisita.setY(by + intey * 2);
        sita.setX(bx + intex);
        sita.setY(by + intey * 2);
        hidarisita.setX(bx);
        hidarisita.setY(by + intey * 2);
        hidari.setX(bx);
        hidari.setY(by + intey);
        mannaka.setX(bx + intex);
        mannaka.setY(by + intey);

        stage1.setX(stagex[0]);
        stage2.setX(stagex[1]);
        stage3.setX(stagex[2]);
        stage4.setX(stagex[3]);
        stage5.setX(stagex[4]);

        stage1.setY(stagey[0]);
        stage2.setY(stagey[1]);
        stage3.setY(stagey[2]);
        stage4.setY(stagey[3]);
        stage5.setY(stagey[4]);
        return true;
    }
}