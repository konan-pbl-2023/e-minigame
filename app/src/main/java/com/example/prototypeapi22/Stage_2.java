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

public class Stage_2 extends AppCompatActivity {
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
    ImageView zimen;
    ImageView hito;
    float hitox = 540;
    float hitoy = 1701;
    int jumptime = 50; //ジャンプの時間
    int jump = -jumptime;
    float jumpx = (float)0.005; //ジャンプの高さを何倍にするか
    ImageView boxn; //通常状態のごみばこ
    ImageView boxr; //右に進んでる時のごみばこ
    ImageView boxl; //左に進んでる時のごみばこ
    int boxscalex = 2; //ごみばこの大きさ倍率
    int boxscaley = 3;

    ImageView mono1;
    ImageView mono2;
    ImageView mono3;
    ImageView mono4;
    ImageView mono5;
    ImageView mono6;
    ImageView mono7;
    ImageView mono8;
    ImageView mono9;
    ImageView mono10;
    ImageView mono11;
    ImageView mono12;

    int obj = 13;
    float monox[] = new float[obj];
    float monoy[] = new float[obj];
    float monomovex[] = new float[obj];
    float monomovey[] = new float[obj];
    Random rand = new Random();
    ImageView ball;
    int HP;
    int Score;
    int prevscore;
    int time;
    TextView timetext;
    TextView load;
    int Clearflag;
    SoundPool soundPool;
    int jumpse;
    int tst;
    int tst2;
    int tst3;
    MediaPlayer mainbgm;
    int nomiss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage2);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        Intent intent = getIntent();
        HP = intent.getIntExtra("HPgive", 100); //デバッグ用に初期値10
        Score = intent.getIntExtra("Scoregive",0);
        Clearflag = intent.getIntExtra("Clearflag",0);
        nomiss = intent.getIntExtra("nomissflag",0);
        prevscore = Score;
        timetext = findViewById(R.id.timetext);

        //ボタン機能
        hidariue = findViewById(R.id.hidariue);
        ue = findViewById(R.id.ue);
        migiue = findViewById(R.id.migiue);
        migi = findViewById(R.id.migi);
        migisita = findViewById(R.id.migisita);
        sita = findViewById(R.id.sita);
        hidarisita = findViewById(R.id.hidarisita);
        hidari = findViewById(R.id.hidari);

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

        //ボタン処理ここまで

        scoretext = findViewById(R.id.score);
        hptext = findViewById(R.id.hp);
        scoretext.setX(50);
        scoretext.setY(0);
        hptext.setX(50);
        hptext.setY(100);

        zimen = findViewById(R.id.zimen);
        hito = findViewById(R.id.hito);

        boxn = findViewById(R.id.boxnormal);
        boxl = findViewById(R.id.boxhidari);
        boxr = findViewById(R.id.boxmigi);
        boxn.setScaleX(boxscalex);
        boxn.setScaleY(boxscaley);
        boxl.setScaleX(boxscalex);
        boxl.setScaleY(boxscaley);
        boxr.setScaleX(boxscalex);
        boxr.setScaleY(boxscaley);
        //boxn.setColorFilter(Color.rgb(50,50,255));
        //boxl.setColorFilter(Color.rgb(50,250,255));
        //boxr.setColorFilter(Color.rgb(250,50,255));



        mono1 = findViewById(R.id.mono1);
        mono2 = findViewById(R.id.mono2);
        mono3 = findViewById(R.id.mono3);
        mono4 = findViewById(R.id.mono4);
        mono5 = findViewById(R.id.mono5);
        mono6 = findViewById(R.id.mono6);
        mono7 = findViewById(R.id.mono7);
        mono8 = findViewById(R.id.mono8);
        mono9 = findViewById(R.id.mono9);
        mono10 = findViewById(R.id.mono10);
        mono11 = findViewById(R.id.mono11);
        mono12 = findViewById(R.id.mono12);
        ball = findViewById(R.id.ball);
        ball.setScaleX(5);
        ball.setScaleY(5);
        for(int i = 0; i < obj; i++){
            monox[i] = rand.nextInt(1000) + 800;
        }
        for(int i = 0; i < obj; i++){
            monoy[i] = rand.nextInt(700) + 200;
        }

        for(int i = 0; i < obj; i++){
            monomovex[i] = -(rand.nextInt(20) / 5 + 1);
        }
        for(int i = 0; i < obj; i++){
            monomovey[i] = -(rand.nextInt(100));

        }
        load = findViewById(R.id.load);
        load.setX(2000);
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
        jumpse = soundPool.load(this,R.raw.taa,0);
        //となりの建物から色々飛んでくるのを放物線の要領で計算して
        //傾いたバケツで受ける的な
        BGM();
        tst = rand.nextInt(255);
        tst2 = rand.nextInt(255);
        tst3 = rand.nextInt(255);
        mono1.setColorFilter(Color.rgb(tst,tst2,tst3));
        tst = rand.nextInt(255);
        tst2 = rand.nextInt(255);
        tst3 = rand.nextInt(255);
        mono2.setColorFilter(Color.rgb(tst,tst2,tst3));
        tst = rand.nextInt(255);
        tst2 = rand.nextInt(255);
        tst3 = rand.nextInt(255);
        mono3.setColorFilter(Color.rgb(tst,tst2,tst3));
        tst = rand.nextInt(255);
        tst2 = rand.nextInt(255);
        tst3 = rand.nextInt(255);
        mono4.setColorFilter(Color.rgb(tst,tst2,tst3));
        tst = rand.nextInt(255);
        tst2 = rand.nextInt(255);
        tst3 = rand.nextInt(255);
        mono5.setColorFilter(Color.rgb(tst,tst2,tst3));
        tst = rand.nextInt(255);
        tst2 = rand.nextInt(255);
        tst3 = rand.nextInt(255);
        mono6.setColorFilter(Color.rgb(tst,tst2,tst3));
        tst = rand.nextInt(255);
        tst2 = rand.nextInt(255);
        tst3 = rand.nextInt(255);
        mono7.setColorFilter(Color.rgb(tst,tst2,tst3));
        tst = rand.nextInt(255);
        tst2 = rand.nextInt(255);
        tst3 = rand.nextInt(255);
        mono8.setColorFilter(Color.rgb(tst,tst2,tst3));
        tst = rand.nextInt(255);
        tst2 = rand.nextInt(255);
        tst3 = rand.nextInt(255);
        mono9.setColorFilter(Color.rgb(tst,tst2,tst3));
        tst = rand.nextInt(255);
        tst2 = rand.nextInt(255);
        tst3 = rand.nextInt(255);
        mono10.setColorFilter(Color.rgb(tst,tst2,tst3));
        tst = rand.nextInt(255);
        tst2 = rand.nextInt(255);
        tst3 = rand.nextInt(255);
        mono11.setColorFilter(Color.rgb(tst,tst2,tst3));
        tst = rand.nextInt(255);
        tst2 = rand.nextInt(255);
        tst3 = rand.nextInt(255);
        mono12.setColorFilter(Color.rgb(tst,tst2,tst3));


    }
    public void BGM(){ //仮置き
        mainbgm = MediaPlayer.create(this,R.raw.mainbgm);

        mainbgm.start();
    }

    public void game() {


        //ボタン処理開始
        float gosax = intex / 4; //なぜか若干誤差が出る(マウスカーソルの当たり判定とタップの判定が違う？)
        float gosay = intey / 4;  //ので調整用,大きさ2倍ならそれぞれinte(x or y)/4
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
        if (movenum == 1) {
            hidariue.setColorFilter(Color.rgb(255, 255, 0));
        } else if (movenum == 2) {
            ue.setColorFilter(Color.rgb(255, 255, 0));
        } else if (movenum == 3) {
            migiue.setColorFilter(Color.rgb(255, 255, 0));
        } else if (movenum == 4) {
            migi.setColorFilter(Color.rgb(255, 255, 0));
        } else if (movenum == 5) {
            migisita.setColorFilter(Color.rgb(255, 255, 0));
        } else if (movenum == 6) {
            sita.setColorFilter(Color.rgb(255, 255, 0));
        } else if (movenum == 7) {
            hidarisita.setColorFilter(Color.rgb(255, 255, 0));
        } else if (movenum == 8) {
            hidari.setColorFilter(Color.rgb(255, 255, 0));
        }
        //ボタン処理終わり

        zimen.setScaleX(14);
        zimen.setX(540);
        zimen.setScaleY(4);
        zimen.setY(1920);
        zimen.setColorFilter(Color.rgb(74, 36, 0));
        //横移動処理
        if (movenum == 8 || movenum == 1 || movenum == 7) {
            if (movex >= -5) {
                movex -= 0.05;
            }
        } else if (movenum == 3 || movenum == 4 || movenum == 5) {
            if (movex <= 5) {
                movex += 0.05;
            }
        } else {
            if (movex < 0) {
                movex += 0.05;
            } else if (movex > 0) {
                movex -= 0.05;
            }
        }
        hitox += movex;
        hito.setScaleY(2);
        hito.setX(hitox);

        //hito.setColorFilter(Color.rgb(150, 200, 200));
        if (hitox <= 0) {
            hitox = 0;
            if (movex < 0) {
                movex = 0;
            }
        }
        if (hitox >= 927) {
            hitox = 927;
            if (movex > 0) {
                movex = 0;
            }
        }

        //縦移動処理
        if (movenum == 1 || movenum == 2 || movenum == 3) {
            if (jump == -jumptime) {
                jump = jumptime;
                soundPool.play(jumpse, 10, 10, 0, 0, 1.0f);
            }
        }
        if (jump > -jumptime) {
            if (movenum == 7 || movenum == 6 || movenum == 5) { //下を押すと早く降りれる
                if (jump > 0) {
                    hitoy -= jump * jump * jumpx;
                } else {
                    hitoy += jump * jump * jumpx;
                }
                jump -= 1;
                if (jump > 0) {
                    hitoy -= jump * jump * jumpx;
                } else {
                    hitoy += jump * jump * jumpx;
                }
                jump -= 1;

            } else {
                if (jump > 0) {
                    hitoy -= jump * jump * jumpx;
                } else {
                    hitoy += jump * jump * jumpx;
                }
                jump -= 1;

            }
            if (jump <= -jumptime) {
                hitoy = 1701;
                jump =  -jumptime;
            }

        }
        hito.setY(hitoy);

        //ごみばこ持ち替え(右に歩いてる時は左に倒れる的な)
        if(movex >= 3){
            boxr.setX(hitox - 50);
            boxr.setY((float)(hitoy - 73 * ((float)boxscaley / 2 + 1)) + 20);
            boxl.setX(-1000);
            boxn.setX(-1000);
        }else if(movex <= -3){
            boxl.setX(hitox + 50);
            boxl.setY((float)(hitoy - 73 * ((float)boxscaley / 2 + 1)) + 20);
            boxr.setX(-1000);
            boxn.setX(-1000);

        }else{
            boxn.setX(hitox);
            boxn.setY((float)(hitoy - 73 * ((float)boxscaley / 2 + 1)));
            boxr.setX(-1000);
            boxl.setX(-1000);
        }



        boxl.setRotation(30);
        boxr.setRotation(-30);

        //ごみばこ描画終わり



        for(int i = 0; i < obj; i++){
            monox[i] += monomovex[i];
            if(monox[i] < -300){
                monox[i] = rand.nextInt(1000) + 800;
                monomovex[i] = -(rand.nextInt(100) / 20);
                monoy[i] = rand.nextInt(700) + 200;
                monomovey[i] = -(rand.nextInt(100));
                if(i == 0){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono1.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 1){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono2.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 2){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono3.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 3){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono4.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 4){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono5.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 5){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono6.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 6){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono7.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 7){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono8.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 8){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono9.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 9){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono10.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 10){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono11.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 11){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono12.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
            }
        }
        for(int i = 0; i < obj; i++){
            if(monomovey[i] < 0){
                monoy[i] -= monomovey[i] * monomovey[i] * 0.001;
            }else{
                monoy[i] += monomovey[i] * monomovey[i] * 0.001;
            }
            monomovey[i] += 0.5;
            if(monoy[i] > 2200){
                monox[i] = rand.nextInt(1000) + 800;
                monomovex[i] = -(rand.nextInt(20) / 5 + 1);
                monoy[i] = rand.nextInt(700) + 200;
                monomovey[i] = -(rand.nextInt(100));
                if(i == 0){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono1.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 1){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono2.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 2){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono3.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 3){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono4.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 4){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono5.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 5){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono6.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 6){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono7.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 7){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono8.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 8){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono9.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 9){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono10.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 10){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono11.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
                if(i == 11){
                    tst = rand.nextInt(255);
                    tst2 = rand.nextInt(255);
                    tst3 = rand.nextInt(255);
                    mono12.setColorFilter(Color.rgb(tst,tst2,tst3));
                }
            }
        }

        for(int i = 0; i < 12; i++) {
            if (movex >= 3) { //3
                //ごみばこが左に倒れている時の処理
                if(monox[i] >= hitox - 20&& monox[i] <= hitox + 73 * 2  && monoy[i] >= (float) (hitoy - 73 * ((float) boxscaley / 2 + 1))  - 73*1.5 + 60){
                    if(Math.abs(monox[i]  - hitox) >= Math.abs(monox[i] - (hitox - 100))){
                        monox[i] = hitox - 73 * 2;
                    }else{
                        monox[i] = hitox + 73 * (float)1.5;
                    }
                }


                if(monox[i]  >= hitox - 200 && monox[i] <= hitox - 30 && monoy[i] >= (float) (hitoy - 73 * ((float) boxscaley / 2 + 1))  - 73*1.5 + 20) {
                    monox[i] = rand.nextInt(1000) + 800;
                    monomovex[i] = -(rand.nextInt(20) / 5 + 1);
                    monoy[i] = rand.nextInt(700) + 200;
                    monomovey[i] = -(rand.nextInt(100));
                    Score += 900;
                    HP += 2;
                    if(i == 0){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono1.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 1){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono2.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 2){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono3.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 3){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono4.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 4){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono5.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 5){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono6.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 6){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono7.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 7){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono8.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 8){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono9.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 9){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono10.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 10){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono11.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 11){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono12.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                }

            } else if (movex <= -3) { //-3
                //ごみばこが右に倒れている時の処理
                if(monox[i] >= hitox - 80&& monox[i] <= hitox + 73 * 2  && monoy[i] >= (float) (hitoy - 73 * ((float) boxscaley / 2 + 1))  - 73*1.5 + 40){
                    if(Math.abs(monox[i]  - hitox) >= Math.abs(monox[i] - (hitox - 100))){
                        monox[i] = hitox - 73 * (float)1.5;
                    }else{
                        monox[i] = hitox + 73 * (float)1.5;
                    }
                }


                if(monox[i]  >= hitox + 50 && monox[i] <= hitox + 73 * 3 && monoy[i] >= (float) (hitoy - 73 * ((float) boxscaley / 2 + 1))  - 73*1.5 + 20) {
                    monox[i] = rand.nextInt(1000) + 800;
                    monomovex[i] = -(rand.nextInt(20) / 5 + 1);
                    monoy[i] = rand.nextInt(700) + 200;
                    monomovey[i] = -(rand.nextInt(100));
                    Score += 900;
                    HP += 2;
                    if(i == 0){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono1.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 1){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono2.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 2){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono3.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 3){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono4.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 4){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono5.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 5){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono6.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 6){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono7.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 7){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono8.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 8){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono9.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 9){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono10.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 10){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono11.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 11){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono12.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                }




            } else {
                //ごみばこが直立してる時の処理
                if(monox[i]  >= hitox - 73 * 1.5 && monox[i] <= hitox + 73 * 2  && monoy[i] >= (float) (hitoy - 73 * ((float) boxscaley / 2 + 1))  - 73*1.5 + 60){
                    if(Math.abs(monox[i]  - hitox) >= Math.abs(monox[i] - (hitox - 100))){
                        monox[i] = hitox - 73 * 2;
                    }else{
                        monox[i] = hitox + 73 * (float)1.5;
                    }
                }

                if(monox[i]  >= hitox - 73 && monox[i] <= hitox + 73 && monoy[i] >= (float) (hitoy - 73 * ((float) boxscaley / 2 + 1))  - 73*1.5) {
                    monox[i] = rand.nextInt(1000) + 800;
                    monomovex[i] = -(rand.nextInt(20) / 5 + 1);
                    monoy[i] = rand.nextInt(700) + 200;
                    monomovey[i] = -(rand.nextInt(100));
                    Score += 900;
                    HP += 2;
                    if(i == 0){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono1.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 1){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono2.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 2){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono3.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 3){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono4.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 4){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono5.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 5){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono6.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 6){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono7.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 7){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono8.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 8){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono9.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 9){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono10.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 10){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono11.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                    if(i == 11){
                        tst = rand.nextInt(255);
                        tst2 = rand.nextInt(255);
                        tst3 = rand.nextInt(255);
                        mono12.setColorFilter(Color.rgb(tst,tst2,tst3));
                    }
                }
            }
        }

        //鉄球の処理

        if (movex >= 3) { //3
            //ごみばこが左に倒れている時の処理
            if(monox[12] >= hitox - 60&& monox[12] <= hitox + 73 * 2-20  && monoy[12] >= (float) (hitoy - 73 * ((float) boxscaley / 2 + 1))  - 73*1.5 ){
                monox[12] = rand.nextInt(1000) + 800;
                monomovex[12] = -(rand.nextInt(20) / 5 + 1);
                monoy[12] = rand.nextInt(700) + 200;
                monomovey[12] = -(rand.nextInt(100));
                Score -= 2000;
                HP -= 30;
            }


            if(monox[12]  >= hitox - 300 && monox[12] <= hitox + 70 && monoy[12] >= (float) (hitoy - 73 * ((float) boxscaley / 2 + 1))  - 73*1.5 -100) {
                monox[12] = rand.nextInt(1000) + 800;
                monomovex[12] = -(rand.nextInt(20) / 5 + 1);
                monoy[12] = rand.nextInt(700) + 200;
                monomovey[12] = -(rand.nextInt(100));
                Score -= 2000;
                HP -= 30;
            }

        } else if (movex <= -3) { //-3
            //ごみばこが右に倒れている時の処理
            if(monox[12] >= hitox - 180&& monox[12] <= hitox + 73 * 2+100  && monoy[12] >= (float) (hitoy - 73 * ((float) boxscaley / 2 + 1))  - 73*1.5){
                monox[12] = rand.nextInt(1000) + 800;
                monomovex[12] = -(rand.nextInt(20) / 5 + 1);
                monoy[12] = rand.nextInt(700) + 200;
                monomovey[12] = -(rand.nextInt(100));
                Score -= 2000;
                HP -= 30;
            }


            if(monox[12]  >= hitox -50 && monox[12] <= hitox + 73 * 3+100 && monoy[12] >= (float) (hitoy - 73 * ((float) boxscaley / 2 + 1))  - 73*1.5-100) {
                monox[12] = rand.nextInt(1000) + 800;
                monomovex[12] = -(rand.nextInt(20) / 5 + 1);
                monoy[12] = rand.nextInt(700) + 200;
                monomovey[12] = -(rand.nextInt(100));
                Score -= 2000;
                HP -= 30;
            }




        } else {
            //ごみばこが直立してる時の処理
            if(monox[12]  >= hitox - 73 * 1.5 -100&& monox[12] <= hitox + 73 * 2 +10 && monoy[12] >= (float) (hitoy - 73 * ((float) boxscaley / 2 + 1))  - 73*1.5 + 60){
                monox[12] = rand.nextInt(1000) + 800;
                monomovex[12] = -(rand.nextInt(20) / 5 + 1);
                monoy[12] = rand.nextInt(700) + 200;
                monomovey[12] = -(rand.nextInt(100));
                Score -= 2000;
                HP -= 30;
            }

            if(monox[12]  >= hitox - 173 && monox[12] <= hitox + 173 && monoy[12] >= (float) (hitoy - 73 * ((float) boxscaley / 2 + 1))  - 73*1.5) {
                monox[12] = rand.nextInt(1000) + 800;
                monomovex[12] = -(rand.nextInt(20) / 5 + 1);
                monoy[12] = rand.nextInt(700) + 200;
                monomovey[12] = -(rand.nextInt(100));
                Score -= 2000;
                HP -= 30;
            }
        }



        mono1.setX(monox[0]);
        mono2.setX(monox[1]);
        mono3.setX(monox[2]);
        mono4.setX(monox[3]);
        mono5.setX(monox[4]);
        mono6.setX(monox[5]);
        mono7.setX(monox[6]);
        mono8.setX(monox[7]);
        mono9.setX(monox[8]);
        mono10.setX(monox[9]);
        mono11.setX(monox[10]);
        mono12.setX(monox[11]);
        ball.setX(monox[12]);

        mono1.setY(monoy[0]);
        mono2.setY(monoy[1]);
        mono3.setY(monoy[2]);
        mono4.setY(monoy[3]);
        mono5.setY(monoy[4]);
        mono6.setY(monoy[5]);
        mono7.setY(monoy[6]);
        mono8.setY(monoy[7]);
        mono9.setY(monoy[8]);
        mono10.setY(monoy[9]);
        mono11.setY(monoy[10]);
        mono12.setY(monoy[11]);
        ball.setY(monoy[12]);


        // for(int i = 0; i < obj; i++){
        //    monox[i] = rand.nextInt(1000) + 800;
        //}
        //for(int i = 0; i < obj; i++){
        //    monoy[i] = rand.nextInt(700) + 200;
        //}

        //for(int i = 0; i < obj; i++){
        //    monomovex[i] = -(rand.nextInt(100) / 20);
        //}
        //for(int i = 0; i < obj; i++){
        //    monomovey[i] = -(rand.nextInt(100));

        //}


        hptext.setText("HP:" + HP);
        scoretext.setText("Score:" + Score);

        time -= 1;
        timetext.setText("Time:"+ time);

        timetext.setX(600);
        timetext.setY(50);
        timetext.setScaleX((float)1.5);
        timetext.setScaleY((float)1.5);

        if(HP <= 0 || time <= 0){
            Intent intent = new Intent(getApplication(), GameOver.class);
            //Random rand = new Random();
            load.setX(200);
            load.setY(1500);
            load.setScaleX(2);
            load.setScaleY(2);
            load.setText("ロード中...\nしばし待たれよ!");
            timer.cancel();
            mainbgm.stop();
            intent.putExtra("HPgive",HP);
            intent.putExtra("Scoregive",Score);
            intent.putExtra("MapID",2);
            intent.putExtra("Prevscore",prevscore);
            if(HP > 0 && Score - prevscore >= 10000){

            }else{
                nomiss = 1;
            }
            intent.putExtra("nomissflag",nomiss);

            if(Clearflag % 3 != 0 && HP > 0 && Score >= 10000){
                Clearflag *= 3;
            }
            intent.putExtra("Clearflag",Clearflag);
            startActivity(intent);

        }
        zimen.setScaleX(40);
        ball.setColorFilter(Color.rgb(230,230,230));
    }//ループ終わり


    @Override
    public boolean onTouchEvent (MotionEvent event) {
        //ボタン関係の処理はじめ
        tapx = (int)event.getX();
        tapy = (int)event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            tap = 1;
        } else if(event.getAction() == MotionEvent.ACTION_UP) {
            tap = 0;

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
        //ボタン関係の処理終わり
        if(gamestart == 0){ //最初だけgameを起動
            gamestart = 1;
            time = 6000;
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