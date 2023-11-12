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
//ラスボス戦の場所
public class Stage_5 extends AppCompatActivity {
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
    int jumptime = 150; //ジャンプの時間
    int jump = -jumptime;
    float jumpx = (float)0.0007; //ジャンプの高さを何倍にするか
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
    //float monox[] = new float[obj];
    //float monoy[] = new float[obj];
    float monomovex[] = new float[obj];
    float monomovey[] = new float[obj];
    Random rand = new Random();
    ImageView ball;
    int HP;
    int Score;
    int prevscore;
    int time;
    TextView timetext;
    int cnt;
    TextView load;
    int monox[] = new int[12];
    int monoy[] = new int[12];
    int bullet[] = new int[16];
    int bullety[] = new int[16];
    int bulletcount = 0;
    int bullets = 5;
    int Clearflag;
    int zimenride = 0; //ジャンプできる場面の時1
    int ridenum = 0;
    int zimens = 2; //地面の流れる速度
    int fallflag = 0; //足場の端っこから落ちた時の処理
    int cnt2 = 0;
    ImageView mannaka;
    ImageView dragon;
    float dragonx = 1000;
    float dragony = 900;
    int dragontimer = 800;
    int nowdragontimer = 0;
    float dragonmove = (float)0.0006; //ドラゴンの動きの大きさの倍率
    int tst;
    ImageView poison;
    int poisoncount = 0;
    int bulletlarge = 2;
    ImageView bullet1;
    ImageView bullet2;
    ImageView bullet3;
    ImageView bullet4;
    ImageView bullet5;
    ImageView bullet6;
    ImageView bullet7;
    ImageView bullet8;
    ImageView bullet9;
    ImageView bullet10;
    ImageView bullet15;
    ImageView bullet11;
    ImageView bullet12;
    ImageView bullet13;
    ImageView bullet16;
    ImageView bullet14;
    int tapprev = 2;
    int bulletcooltime = 0;
    int bulletnum = 0;
    TextView dragonhp;
    int dragonhpnum = 0;
    int dragonhit = 0;
    ImageView dragons;
    int tst2;
    int tst3;
    int tst4;
    int bonuscnt = 0; //低確率で撃破時ボーナス点を2重にとるバグが起きる可能性があるので
    //(200フレームルールの弊害)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage5);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Intent intent = getIntent();
        HP = intent.getIntExtra("HPgive", 100); //デバッグ用に初期値10
        Score = intent.getIntExtra("Scoregive",10000);
        Clearflag = intent.getIntExtra("Clearflag",0);
        prevscore = Score;
        timetext = findViewById(R.id.timetext);

        load = findViewById(R.id.load);

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

        hito = findViewById(R.id.hito);

        scoretext = findViewById(R.id.score);
        hptext = findViewById(R.id.hp);
        scoretext.setX(0);
        scoretext.setY(200);
        hptext.setX(0);
        hptext.setY(200);
        scoretext.setScaleX((float)1.5);
        hptext.setScaleX((float)1.5);
        scoretext.setScaleY((float)1.5);
        hptext.setScaleY((float)1.5);


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


        int mlongy = 1; //足場の長さ
        int mlongx = 4;
        mono1.setScaleX(mlongx);
        mono1.setScaleY(mlongy);
        mono2.setScaleX(mlongx);
        mono2.setScaleY(mlongy);
        mono3.setScaleX(mlongx);
        mono3.setScaleY(mlongy);
        mono4.setScaleX(mlongx);
        mono4.setScaleY(mlongy);
        mono5.setScaleX(mlongx);
        mono5.setScaleY(mlongy);
        mono6.setScaleX(mlongx);
        mono6.setScaleY(mlongy);
        mono7.setScaleX(mlongx);
        mono7.setScaleY(mlongy);
        mono8.setScaleX(mlongx);
        mono8.setScaleY(mlongy);
        mono9.setScaleX(mlongx);
        mono9.setScaleY(mlongy);
        mono10.setScaleX(mlongx);
        mono10.setScaleY(mlongy);
        mono11.setScaleX(mlongx);
        mono11.setScaleY(mlongy);
        mono12.setScaleX(mlongx);
        mono12.setScaleY(mlongy);

        //足場の配置決め
        for(int i = 0; i < 12; i++){

            while(true) {
                cnt = 0;
                monox[i] = rand.nextInt(2400) + 1200;
                for(int j = 0; j < 12;j++){
                    if(Math.abs(monox[i] - monox[j]) < 50){
                        cnt += 1;
                    }
                }
                if(cnt == 1){
                    break;
                }
            }
        }

        for(int i = 0; i < 12; i++){

            while(true) {
                cnt = 0;
                monoy[i] = rand.nextInt(1200)+ 400;
                for(int j = 0; j < 12;j++){
                    if(Math.abs(monoy[i] - monoy[j]) < 50){
                        cnt += 1;
                    }
                }
                if(cnt == 1){
                    break;
                }
            }
        }
        load = findViewById(R.id.load);
        load.setX(2000);

        dragon = findViewById(R.id.dragon);
        int dragonsize = 2;
        dragon.setScaleX(2 * dragonsize);
        dragon.setScaleY(3 * dragonsize);

        poison = findViewById(R.id.poison);

        bullet1 = findViewById(R.id.bullet1);
        bullet2 = findViewById(R.id.bullet2);
        bullet3 = findViewById(R.id.bullet3);
        bullet4 = findViewById(R.id.bullet4);
        bullet5 = findViewById(R.id.bullet5);
        bullet6 = findViewById(R.id.bullet6);
        bullet7 = findViewById(R.id.bullet7);
        bullet8 = findViewById(R.id.bullet8);
        bullet9 = findViewById(R.id.bullet9);
        bullet10 = findViewById(R.id.bullet10);
        bullet11 = findViewById(R.id.bullet11);
        bullet12 = findViewById(R.id.bullet12);
        bullet13 = findViewById(R.id.bullet13);
        bullet14 = findViewById(R.id.bullet14);
        bullet15 = findViewById(R.id.bullet15);
        bullet16 = findViewById(R.id.bullet16);

        for(int i = 0; i < 16; i++){
            bullet[i] = 2000;

        }
        hitoy = 500;
        jump = 0;

        dragonhp = findViewById(R.id.dragonhp);
        dragons = findViewById(R.id.dragons);
        load.setX(2000);
    } //onCreate終わり

    public void game(){
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
        }else if (movenum == 9) {
            mannaka.setColorFilter(Color.rgb(200, 0, 0));

        }
        //ボタン処理終わり
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

        hito.setColorFilter(Color.rgb(150, 200, 200));
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
            if (zimenride == 1) {
                zimenride = 0;
                jump = jumptime;
            }
        }
        //if (jump > -jumptime) {
        if (zimenride == 0) {
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
            if (hitoy > 1701) {
                hitoy = 1701;
                zimenride = 1;
                jump =  -jumptime;
                ridenum = 0;
            }

        }
        fallflag = 0;
        if(zimenride == 1 && hitoy != 1701){
            fallflag = 1;
            cnt2 = 0;
        }

        for(int i = 0; i < 12; i++){
            monox[i] -= zimens;
            if(monoy[i] <= hitoy +73 * 1.5 && monoy[i] + 40 >= hitoy + 73 * 1.5){
                if(hitox >= monox[i] - 2.5 * 73 && hitox <= monox[i] + 2.5 * 73 && jump <= 0){
                    ridenum = i+1; //i個目のものに乗ってる
                    zimenride = 1;
                    hitoy = monoy[i] - 73 * (float)1.5;
                    cnt2 += 1;
                }
            }
        }
        if(fallflag == 1 && cnt2 == 0 && ridenum != 0){
            jump = 0;
            zimenride = 0;
        }
        if(ridenum != 0){
            hitox -= zimens;
        }

        hito.setY(hitoy);
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



        for(int i = 0; i < 12; i++){
            if(monox[i] < -400){
            while(true) {
                cnt = 0;
                monox[i] = rand.nextInt(2400) + 1200;
                monoy[i] = rand.nextInt(1200)+ 400;
                for (int j = 0; j < 12; j++) {
                    if (Math.abs(monox[i] - monox[j]) < 50) {
                        cnt += 1;
                    }
                    if(Math.abs(monoy[i] - monoy[j]) < 50){
                        cnt += 1;
                    }
                }
                if (cnt == 2) {
                    break;
                }
            }
            }

        }

        if(nowdragontimer < dragontimer / 4){
            tst = dragontimer / 4 - nowdragontimer;
            dragony -= tst * tst * dragonmove;
        }else if(nowdragontimer < dragontimer / 2){
            tst = nowdragontimer - dragontimer / 4;
            dragony += tst * tst * dragonmove;
        }else if(nowdragontimer < dragontimer * 3 / 4){
            tst = dragontimer * 3 / 4 - nowdragontimer;
            dragony += tst * tst * dragonmove;
        }else if(nowdragontimer < dragontimer){
            tst = nowdragontimer - dragontimer * 3 / 4;
            dragony -= tst * tst * dragonmove;
        }else{
            nowdragontimer = 0;
            dragony = 900;
        }

        nowdragontimer += 2;
        dragon.setY(dragony);
        time -= 1;


        if(hitoy + 1.5 * 73 >= 1700) {
            poisoncount += 1;

            if (poisoncount % 3 == 0) {
                HP -= 1;
            }
        }else{
            poisoncount = 0;
        }

        //たま処理
        //真ん中押しててかつ前のフレームに押してなかった場合
        if(movenum == 9 && tapprev != 9){
            tst = bulletnum % 16;
            bullet[tst] = (int)hitox;
            bullety[tst] = (int)hitoy;
            if(tst == 0){
                tst2 = rand.nextInt(255);
                tst3 = rand.nextInt(255);
                tst4 = rand.nextInt(255);
                bullet1.setColorFilter(Color.rgb(tst2,tst3,tst4));
            }
            if(tst == 1){
                tst2 = rand.nextInt(255);
                tst3 = rand.nextInt(255);
                tst4 = rand.nextInt(255);
                bullet2.setColorFilter(Color.rgb(tst2,tst3,tst4));
            }
            if(tst == 2){
                tst2 = rand.nextInt(255);
                tst3 = rand.nextInt(255);
                tst4 = rand.nextInt(255);
                bullet3.setColorFilter(Color.rgb(tst2,tst3,tst4));
            }
            if(tst == 3){
                tst2 = rand.nextInt(255);
                tst3 = rand.nextInt(255);
                tst4 = rand.nextInt(255);
                bullet4.setColorFilter(Color.rgb(tst2,tst3,tst4));
            }
            if(tst == 4){
                tst2 = rand.nextInt(255);
                tst3 = rand.nextInt(255);
                tst4 = rand.nextInt(255);
                bullet5.setColorFilter(Color.rgb(tst2,tst3,tst4));
            }
            if(tst == 5){
                tst2 = rand.nextInt(255);
                tst3 = rand.nextInt(255);
                tst4 = rand.nextInt(255);
                bullet6.setColorFilter(Color.rgb(tst2,tst3,tst4));
            }
            if(tst == 6){
                tst2 = rand.nextInt(255);
                tst3 = rand.nextInt(255);
                tst4 = rand.nextInt(255);
                bullet7.setColorFilter(Color.rgb(tst2,tst3,tst4));
            }
            if(tst == 7){
                tst2 = rand.nextInt(255);
                tst3 = rand.nextInt(255);
                tst4 = rand.nextInt(255);
                bullet8.setColorFilter(Color.rgb(tst2,tst3,tst4));
            }
            if(tst == 8){
                tst2 = rand.nextInt(255);
                tst3 = rand.nextInt(255);
                tst4 = rand.nextInt(255);
                bullet9.setColorFilter(Color.rgb(tst2,tst3,tst4));
            }
            if(tst == 9){
                tst2 = rand.nextInt(255);
                tst3 = rand.nextInt(255);
                tst4 = rand.nextInt(255);
                bullet10.setColorFilter(Color.rgb(tst2,tst3,tst4));
            }
            if(tst == 10){
                tst2 = rand.nextInt(255);
                tst3 = rand.nextInt(255);
                tst4 = rand.nextInt(255);
                bullet11.setColorFilter(Color.rgb(tst2,tst3,tst4));
            }
            if(tst == 11){
                tst2 = rand.nextInt(255);
                tst3 = rand.nextInt(255);
                tst4 = rand.nextInt(255);
                bullet12.setColorFilter(Color.rgb(tst2,tst3,tst4));
            }
            if(tst == 12){
                tst2 = rand.nextInt(255);
                tst3 = rand.nextInt(255);
                tst4 = rand.nextInt(255);
                bullet13.setColorFilter(Color.rgb(tst2,tst3,tst4));
            }
            if(tst == 13){
                tst2 = rand.nextInt(255);
                tst3 = rand.nextInt(255);
                tst4 = rand.nextInt(255);
                bullet14.setColorFilter(Color.rgb(tst2,tst3,tst4));
            }
            if(tst == 14){
                tst2 = rand.nextInt(255);
                tst3 = rand.nextInt(255);
                tst4 = rand.nextInt(255);
                bullet15.setColorFilter(Color.rgb(tst2,tst3,tst4));
            }
            if(tst == 15){
                tst2 = rand.nextInt(255);
                tst3 = rand.nextInt(255);
                tst4 = rand.nextInt(255);
                bullet16.setColorFilter(Color.rgb(tst2,tst3,tst4));
            }
            bulletnum += 1;
        }
        tapprev = movenum;

        for(int i = 0; i < 16; i++){
            bullet[i] += 7;
            if(bullet[i] > dragonx  - 1.5 * 84&& bullet[i] < dragonx + 2.5 * 84 && bullety[i] > dragony - 73 * 2.5 && bullety[i] < dragony + 73 * 3.5){
                bullet[i] = 2000;
                dragonhpnum -= 500;
                dragonhit = 80;
                Score += 100;
            }
        }

        bullet1.setX(bullet[0]);
        bullet1.setY(bullety[0]);
        bullet2.setX(bullet[1]);
        bullet2.setY(bullety[1]);
        bullet3.setX(bullet[2]);
        bullet3.setY(bullety[2]);
        bullet4.setX(bullet[3]);
        bullet4.setY(bullety[3]);
        bullet5.setX(bullet[4]);
        bullet5.setY(bullety[4]);
        bullet6.setX(bullet[5]);
        bullet6.setY(bullety[5]);
        bullet7.setX(bullet[6]);
        bullet7.setY(bullety[6]);
        bullet8.setX(bullet[7]);
        bullet8.setY(bullety[7]);
        bullet9.setX(bullet[8]);
        bullet9.setY(bullety[8]);
        bullet10.setX(bullet[9]);
        bullet10.setY(bullety[9]);
        bullet11.setX(bullet[10]);
        bullet11.setY(bullety[10]);
        bullet12.setX(bullet[11]);
        bullet12.setY(bullety[11]);
        bullet13.setX(bullet[12]);
        bullet13.setY(bullety[12]);
        bullet14.setX(bullet[13]);
        bullet14.setY(bullety[13]);
        bullet15.setX(bullet[14]);
        bullet15.setY(bullety[14]);
        bullet16.setX(bullet[15]);
        bullet16.setY(bullety[15]);
        dragonhp.setText("Dragon HP" + dragonhpnum);
        scoretext.setText("Score:" + Score);
        hptext.setText("HP:" + HP);
        timetext.setX(400);
        timetext.setY(150);
        timetext.setScaleX(2);
        timetext.setScaleY(2);
        timetext.setText("Time" + time);

        if(dragonhit == 0){
            dragon.setX(dragonx);
            dragon.setY(dragony);
            dragons.setX(3000);
        }
        else if(dragonhit % 20 <  10 && dragonhit != 0){
            dragons.setColorFilter(Color.rgb(50,50,50));
            dragons.setX(dragonx);
            dragons.setY(dragony);
            dragon.setX(3000);
        }else{
            dragons.setX(dragonx);
            dragons.setY(dragony);
            dragon.setX(3000);
            dragons.setColorFilter(Color.rgb(200,200,200));
        }
        if(dragonhit != 0){
            dragonhit -= 1;
        }
        if (hitox > dragonx - 2.5 * 84 && hitox < dragonx + 2.5 * 84 && hitoy > dragony - 4 * 73 && hitoy < dragony + 4 * 73) {
            HP -= 1;

        }

        if(HP <= 0 || time <= 0 || dragonhpnum <= 0){
            Intent intent = new Intent(getApplication(), GameOver.class);
            //Random rand = new Random();
            load.setX(200);
            load.setY(1500);
            load.setScaleX(2);
            load.setScaleY(2);
            load.setText("ロード中...\nしばし待たれよ!");
            timer.cancel();
            if(dragonhpnum <= 0 && bonuscnt == 0){
                Score += 50000;
                bonuscnt = 1;
            }
            intent.putExtra("HPgive",HP);
            intent.putExtra("Scoregive",Score);
            intent.putExtra("MapID",5);
            intent.putExtra("Prevscore",prevscore);
            if(Clearflag % 11 != 0 && HP > 0 && Score >= 10000){
                Clearflag *= 11;
            }
            intent.putExtra("Clearflag",Clearflag);
            startActivity(intent);

        }
    }


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
        mannaka.setX(bx + intex);
        mannaka.setY(by + intey);

        poison.setX(540);
        poison.setY(1846);
        poison.setScaleX(20);
        poison.setScaleY(5);
        poison.setColorFilter(Color.rgb(255,0,255));



        //ボタン関係の処理終わり
        if(gamestart == 0){ //最初だけgameを起動
            dragon.setX(1000);
            dragon.setY(900);
            gamestart = 1;
            time = 12000; //デバッグ終わったら12000予定
            dragonhp.setX(500);
            dragonhp.setY(50);
            dragonhp.setScaleX(2);
            dragonhp.setScaleY(2);
            dragonhpnum = 50000; //50000
            dragonhp.setTextColor(Color.rgb(250,0,0));
            dragons.setScaleX(4);
            dragons.setScaleY(6);
            dragons.setX(3000);
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