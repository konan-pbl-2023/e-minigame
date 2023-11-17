package com.example.prototypeapi22;



//魚釣り
//魚などのプログラムは縦73横84の長さのブロックをn倍して作っているので
//置き換えはそのサイズの魚などの画像を作ってください
//ブロックは赤→fish1,4 標準的な動き　大きさX4倍Y2.5倍
//青→fish2,5　比較的直線的に動く　X5倍Y2倍
//黄→fish3,6  比較的ジグザグに動く　X2倍Y1倍　
//さめ→same,same2　X6倍 Y3倍
//になっているはずなのでそれぐらいにフィットする魚の画像(透過処理済み)を作ってから
//73*84に縮小すれば(画質はかなり粗くなるかもしれませんが)動く...はずです
//釣り竿及び足場はこのままで行く予定です
//画像差し替えのやり方
//①xmlで置き換えたいImageviewのIDを適当に変更
//②置き換え後のIDを置き換え前のIDに合わせる
//例えばfish1の画像を置き換えたければ元々のfish1を適当にfish1_oldなどにして
//新しく入れた画像のIDをfish1にする



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
import android.os.Handler;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.view.MotionEvent;


public class Stage_1 extends AppCompatActivity {
    public ImageView turiito;
    public ImageView ue; //上の地面とか人がいる予定の所
    public TextView scoretext;
    public TextView hptext;
    float i = 4; //釣り糸のデフォルトの長さ
    float longbar = 0; //釣り糸長さ変更

    int tst = 0; //テスト用とか直後に使う適当置き変数
    int tst2 = 0;
    int tst3 = 0;
    int gamestart = 0; //gameが始まってるかを管理
    int updown = 0; //押してるかどうかを判別
    int score = 0;
    int hp = 0;

    int obj = 11; //現在の魚+αの数
    public ImageView fish1; //魚とかの画像たち

    public ImageView fish2;

    public ImageView fish3;
    public ImageView fish4;
    public ImageView fish5;
    public ImageView fish6;
    public ImageView bag;
    public ImageView kan;
    public ImageView same;
    public ImageView same2;
    public ImageView fish7;
    public ImageView redball; //釣り糸の先のやつ
    Random rand = new Random();
    int fishx[] = new int[obj]; //魚たちの座標を格納する配列
    int fishy[] = new int[obj];

    int kanflag = 0; //缶の動きを調整
    int bagflag = 0;
    ImageView asiba; //足場の画像
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    int hitr = 0; //テスト用
    int commenttime = 0;
    int commentnum = 0;
    int totta = 0; //釣れた魚を表示しているか
    TextView tottatext;
    TextView comment;
    TextView combotext;
    int combo = 0;
    int tottatexty = 0; //tottatextの高さ調整用
    int time = 0;
    TextView timetext;
    int xscore = 1; //scoreの倍率
    TextView load;
    int prevscore = 0;
    int v;
    int Clearflag;
    SoundPool soundPool;
    int fishse;
    MediaPlayer mainbgm;
    ImageView st1;
    int nomiss;
    ImageView back;
    TextView setumei;
    ImageView wback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage1);

        //Homeボタンなど消しのおまじない
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //https://developer.android.com/training/system-ui/navigation?hl=ja#java
        back = findViewById(R.id.back);
        //back.setColorFilter(Color.rgb(255,255,255));
        back.setScaleX(1.65f);
        back.setScaleY(1.65f);
        setumei = findViewById(R.id.setumei);
        setumei.setScaleX(1.5f);
        setumei.setScaleY(1.5f);
        setumei.setY(1150);
        wback = findViewById(R.id.wback);
        wback.setScaleY(6);
        wback.setScaleX(20);
        wback.setY(250);
        wback.setColorFilter(Color.rgb(255,255,255));
        //setumei.setTextColor(Color.rgb(255,255,255));
        setumei.setText("釣り\n画面タップでウキが下に\n離したらウキが上に\n" +
                "お魚の口元を狙いましょう\nサメには注意！");

        Intent intent = getIntent();
        hp = intent.getIntExtra("HPgive", 0);
        score = intent.getIntExtra("Scoregive",0);
        Clearflag = intent.getIntExtra("Clearflag",0);
        nomiss = intent.getIntExtra("nomissflag",0);
        prevscore = score;
        st1 = findViewById(R.id.st1);
        st1.setScaleX(1.1f);
        st1.setScaleY(1.1f);
        st1.setY(50);
        ue = findViewById(R.id.ue); //水上の画像、仮置き
        ue.setX(-100);
        ue.setY(-200);

        turiito = findViewById(R.id.turiito); //釣り糸の画像取得
        //turiito.setX(0);
        //turiito.setY(0);
        turiito.setScaleX((float)0.5); //釣り糸の太さ

        scoretext = findViewById(R.id.score); //画像取得
        hptext = findViewById(R.id.hp);
        fish1 = findViewById(R.id.fish1);
        fish2 = findViewById(R.id.fish2);
        fish3 = findViewById(R.id.fish3);
        fish4 = findViewById(R.id.fish4);
        fish5 = findViewById(R.id.fish5);
        fish6 = findViewById(R.id.fish6);
        fish7 = findViewById(R.id.fish7);
        kan = findViewById(R.id.kan);
        same = findViewById(R.id.same);
        same2 = findViewById(R.id.same2);
        bag = findViewById(R.id.bag);
        fish7.setX(-1000);

        tst = rand.nextInt(500) - 600;
        tst2 = rand.nextInt(1200) + 300;

        fishx[0] = tst;
        fishy[0] = tst2;
        fish1.setX(fishx[0]); //OnCreate内だとxmlでの配置状況が(0,0)
        fish1.setY(fishy[0]); //それ以外だと左上が(0,0)になるっぽい？
        //fish1.setColorFilter(Color.rgb(229,56,49));
        tst = rand.nextInt(500) - 600;
        tst2 = rand.nextInt(1000) + 500;
        //tst = rand.nextInt(300) - 400;
        //tst2 = rand.nextInt(900) + 300;
        fishx[1] = tst;
        fishy[1] = tst2;
        fish2.setX(tst);
        fish2.setY(tst2);
        //fish2.setColorFilter(Color.rgb(97,123,235));

        tst = rand.nextInt(500) - 600;
        tst2 = rand.nextInt(1000) + 500;
        fishx[2] = tst;
        fishy[2] = tst2;
        fish3.setX(tst);
        fish3.setY(tst2);
        //fish3.setColorFilter(Color.rgb(244,245,24));

        //tst = rand.nextInt(300) - 400;
        //tst2 = rand.nextInt(900) + 300;
        //fishx[2] = tst;
        //fishy[2] = tst2;
        //fish3.setX(tst);
        //fish3.setY(tst2);

        tst = rand.nextInt(500) + 1100;
        tst2 = rand.nextInt(1000) + 500;
        fishx[3] = tst;
        fishy[3] = tst2;
        fish4.setX(tst);
        fish4.setY(tst2);
        //fish4.setColorFilter(Color.rgb(229,56,49));

        tst = rand.nextInt(500) + 1100;
        tst2 = rand.nextInt(1000) + 500;
        fishx[4] = tst;
        fishy[4] = tst2;
        fish5.setX(tst);
        fish5.setY(tst2);
        //fish5.setColorFilter(Color.rgb(97,123,235));

        tst = rand.nextInt(300) + 1100;
        tst2 = rand.nextInt(1000) + 500;
        fishx[5] = tst;
        fishy[5] = tst2;
        fish6.setX(tst);
        fish6.setY(tst2);
        //fish6.setColorFilter(Color.rgb(244,245,24));

        if(rand.nextInt(100) < 50){
            tst = rand.nextInt(300) - 400;
        }else{
            tst = rand.nextInt(300) + 1100;
        }

        tst2 = rand.nextInt(900) + 300;
        fishx[6] = tst;
        fishy[6] = tst2;
        //fish7.setX(tst);
        //fish7.setY(tst2);

        //fish1~3 左から
        //fish4~6 右から
        //fish7　特殊挙動予定だったけどもう放置かも

        if(rand.nextInt(100) < 50){
            tst = rand.nextInt(300) - 400;
        }else{
            tst = rand.nextInt(300) + 1100;
        }

        tst2 = rand.nextInt(1300) + 500;
        fishx[7] = tst;
        fishy[7] = tst2;
        kan.setX(tst);
        kan.setY(tst2);
        kan.setColorFilter(Color.rgb(165,165,165));
        if(rand.nextInt(100) < 50){
            tst = rand.nextInt(300) - 400;
        }else{
            tst = rand.nextInt(300) + 1100;
        }

        tst2 = rand.nextInt(900) + 300;
        fishx[8] = tst;
        fishy[8] = tst2;
        bag.setX(tst);
        bag.setY(tst2);
        bag.setColorFilter(Color.rgb(207,207,207));
        tst = rand.nextInt(500) - 600;
        tst2 = rand.nextInt(1000) + 500;
        fishx[9] = tst;
        fishy[9] = tst2;
        same.setX(tst);
        same.setY(tst2);
        //same.setColorFilter((Color.rgb(0,47,117)));

        tst = rand.nextInt(500) + 1100;
        tst2 = rand.nextInt(1000) + 500;
        fishx[10] = tst;
        fishy[10] = tst2;
        same2.setX(tst);
        same2.setY(tst2);
        //same2.setColorFilter((Color.rgb(0,47,117)));

        //kan.setColorFilter(Color.rgb(100,30,240));

        redball = findViewById(R.id.redball);
        redball.setScaleX((float)1.5);
        redball.setScaleY((float)1.5);
        scoretext.setText("" + fishx[0]);
        hptext.setText("" + fishy[0]);
        //same.setColorFilter(Color.rgb(250,0,0));

        asiba = findViewById(R.id.asiba);
        asiba.setScaleX(3);
        asiba.setScaleY((float)0.5);
        asiba.setColorFilter(Color.rgb(107,49,29));

        //サイズ調整
        float kake =  (float)1;
        fish1.setScaleX(4 / kake);
        fish4.setScaleX(4/ kake);
        fish1.setScaleY((float)2.5 / kake);
        fish4.setScaleY((float)2.5/kake);
        fish2.setScaleX((float)5/kake);
        fish5.setScaleX((float)5/kake);
        fish2.setScaleY(2/kake);
        fish5.setScaleY(2/kake);
        fish3.setScaleX(2/kake);
        fish6.setScaleX(2/kake);
        fish3.setScaleY(1/kake);
        fish6.setScaleY(1/kake);
        same.setScaleX(6/kake);
        same.setScaleY(3/kake);
        same2.setScaleX(6/kake);
        same2.setScaleY(3/kake);
        comment = findViewById(R.id.comment);
        tottatext = findViewById(R.id.totta);
        tottatext.setY(-1000);
        combotext = findViewById(R.id.dragonhp);

        timetext = findViewById(R.id.time);
        load = findViewById(R.id.load);
        load.setX(2000);


        BGM();
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
        fishse = soundPool.load(this,R.raw.sakana,0);
    } //onCreate終わり
    public void BGM(){ //仮置き
        mainbgm = MediaPlayer.create(this,R.raw.mainbgm2);

        mainbgm.start();
    }
    public void moveturiito(){ //gameがはじめられた時に始動

        comment.setX(50);
        //hptext.setX(200);
        comment.setY(180);
        if(commenttime == 0 && commentnum == 0){
            tst = rand.nextInt(10);
            if(tst < 3) {
                comment.setTextColor(Color.rgb(0,0,0));
                comment.setText("いつかはヌシを\n釣り人だもの");
            }else if(tst < 6){
                comment.setTextColor(Color.rgb(0,0,0));
                comment.setText("魚は色によって\n動きが違うらしい");
            }else if(tst < 9){
                comment.setTextColor(Color.rgb(0,0,0));
                comment.setText("実はこの池の魚\n合わせて数匹しかいないらしい");
            }else{
                comment.setTextColor(Color.rgb(255,0,0));
                comment.setText("10分の1のレアテキスト!!!!\n今日はついてる...かも");
            }
            commenttime = 300;
        }
        if(commenttime == 0 && commentnum == 1){
            tst = rand.nextInt(10);
            if(tst < 3) {
                comment.setTextColor(Color.rgb(0,0,0));
                comment.setText("ここの魚ってなんで\nこんなにカラフルなの？");
            }else if(tst < 6){
                comment.setTextColor(Color.rgb(0,0,0));
                comment.setText("赤い魚「本気で泳げば\nあと3倍は速く泳げるぜ」");
            }else if(tst < 9){
                comment.setTextColor(Color.rgb(0,0,0));
                comment.setText("タイが赤いのは\nアスタキサンチンという色素のせい");
            }else{
                comment.setTextColor(Color.rgb(255,0,0));
                comment.setText("このプログラム現時点で\n619行とかなってんだけど");
            }
            commenttime = 300;
            commentnum = 0;
        }
        if(commenttime == 0 && commentnum == 2){
            tst = rand.nextInt(10);
            if(tst < 3) {
                comment.setTextColor(Color.rgb(0,0,0));
                comment.setText("青い魚はまっすぐ進んで速い\n仕様変更がなければ");
            }else if(tst < 6){
                comment.setTextColor(Color.rgb(0,0,0));
                comment.setText("ギョギョギョ！\n魚でギョざいます");
            }else if(tst < 9){
                comment.setTextColor(Color.rgb(0,0,0));
                comment.setText("今でも青い魚が住んでいる\n今でも青い魚は住んでいる");
            }else{
                comment.setTextColor(Color.rgb(255,0,0));
                comment.setText("このテキストのセンスの責任は\nこのプログラム担当の1人に全て");
            }
            commenttime = 300;
            commentnum = 0;
        }
        if(commenttime == 0 && commentnum == 3){
            tst = rand.nextInt(10);
            if(tst < 3) {
                comment.setTextColor(Color.rgb(0,0,0));
                comment.setText("黄色の魚はジグザグに進む\n仕様変更はきっとない");
            }else if(tst < 6){
                comment.setTextColor(Color.rgb(0,0,0));
                comment.setText("ビビットカラーの魚は\nちょっと怖い");
            }else if(tst < 9){
                comment.setTextColor(Color.rgb(0,0,0));
                comment.setText("黄色の魚を釣るのに必要なことは\n多分8割運です");
            }else{
                comment.setTextColor(Color.rgb(255,0,0));
                comment.setText("何か思いついたら\n後で書きます");
            }
            commenttime = 300;
            commentnum = 0;
        }
        if(commenttime == 0 && commentnum == 4){
            tst = rand.nextInt(10);
            if(tst < 3) {
                comment.setTextColor(Color.rgb(0,0,0));
                comment.setText("人は基本的に\n一騎打ちでサメには勝てない");
            }else if(tst < 6){
                comment.setTextColor(Color.rgb(0,0,0));
                comment.setText("さめだね～さめよ～\nさ～め～なのよ～");
            }else if(tst < 9){
                comment.setTextColor(Color.rgb(0,0,0));
                comment.setText("SSHHAAAAAAAAA\nAAAAAAARRK!!!");
            }else{
                comment.setTextColor(Color.rgb(255,0,0));
                comment.setText("某瑞典の家具店のサメぬい\n大きくてもふもふでかわいい");
            }
            commenttime = 300;
            commentnum = 0;
        }
        commenttime -= 1;
        asiba.setX(800);
        asiba.setY(300);
        if(updown == 1){ //押されている時iを大きく
            if(longbar < 40) {
                longbar += 0.2;
            }
        }else{ //押されていない時iを小さくしている
            if(longbar > 0) {
                longbar -= 0.2;
            }
        }
        turiito.setScaleY(i + longbar/4);


        //tst = (int)score * (int)i;
        //scoretext.setText("" + tst);
        //scoretext.setText("" + fishx[0]);
        //hptext.setText("" + fishy[0]);

        //魚さんの動き 1,2,3が左から、4,5,6が右から
        if(rand.nextInt(100) < 96){
            fishx[0] += (rand.nextInt(5) * 1.3);
            fishy[0] += (rand.nextInt(19) -9) * 2;
        }else{
            fishx[0] += 24;
            if(rand.nextInt(100) < 50) {
                fishy[0] += 50;
            }else{
                fishy[0] -= 50;
            }
        }
        if(fishx[0] > 1300){
            tst = rand.nextInt(500) - 600;
            tst2 = rand.nextInt(1000) + 500;
            fishx[0] = tst;
            fishy[0] = tst2;
        }
        if(fishy[0] < 400){
            fishy[0] = 400;
        }
        fish1.setX(fishx[0]);
        fish1.setY(fishy[0]);

        if(rand.nextInt(100) < 96){
            fishx[1] += (rand.nextInt(5) * 1.8);
            fishy[1] += (rand.nextInt(19) -9);
        }else{
            fishx[1] += 24;
            if(rand.nextInt(100) < 50) {
                fishy[1] += 5;
            }else{
                fishy[1] -= 5;
            }
        }
        if(fishx[1] > 1300){
            tst = rand.nextInt(500) - 600;
            tst2 = rand.nextInt(1000) + 500;
            fishx[1] = tst;
            fishy[1] = tst2;
        }
        if(fishy[1] < 400){
            fishy[1] = 400;
        }
        fish2.setX(fishx[1]);
        fish2.setY(fishy[1]);

        if(rand.nextInt(100) < 96){
            fishx[2] += (rand.nextInt(5) * 1);
            fishy[2] += (rand.nextInt(19) -9) * 3;
        }else{
            fishx[2] += 24;
            if(rand.nextInt(100) < 50) {
                fishy[2] += 60;
            }else{
                fishy[2] -= 60;
            }
        }
        if(fishx[2] > 1300){
            tst = rand.nextInt(500) - 600;
            tst2 = rand.nextInt(1000) + 500;
            fishx[2] = tst;
            fishy[2] = tst2;
        }
        if(fishy[2] < 400){
            fishy[2] = 400;
        }
        fish3.setX(fishx[2]);
        fish3.setY(fishy[2]);

        //左側の動きここまで

        if(rand.nextInt(100) < 96){
            fishx[3] -= (rand.nextInt(5) * 1.3);
            fishy[3] += (rand.nextInt(19) -9) * 2;
        }else{
            fishx[3] -= 24;
            if(rand.nextInt(100) < 50) {
                fishy[3] -= 50;
            }else{
                fishy[3] -= 50;
            }
        }
        if(fishx[3] < -400){
            tst = rand.nextInt(500) + 1100;
            tst2 = rand.nextInt(1000) + 500;
            fishx[3] = tst;
            fishy[3] = tst2;
        }
        if(fishy[3] < 400){
            fishy[3] = 400;
        }
        fish4.setX(fishx[3]);
        fish4.setY(fishy[3]);

        if(rand.nextInt(100) < 96){
            fishx[4] -= (rand.nextInt(5) * 1.8);
            fishy[4] += (rand.nextInt(19) -9);
        }else{
            fishx[4] -= 24;
            if(rand.nextInt(100) < 50) {
                fishy[4] += 5;
            }else{
                fishy[4] -= 5;
            }
        }
        if(fishx[4] < -400){
            tst = rand.nextInt(500) + 1100;
            tst2 = rand.nextInt(1000) + 500;
            fishx[4] = tst;
            fishy[4] = tst2;
        }
        if(fishy[4] < 400){
            fishy[4] = 400;
        }
        fish5.setX(fishx[4]);
        fish5.setY(fishy[4]);

        if(rand.nextInt(100) < 96){
            fishx[5] -= (rand.nextInt(5) * 1);
            fishy[5] += (rand.nextInt(19) -9) * 3;
        }else{
            fishx[5] -= 24;
            if(rand.nextInt(100) < 50) {
                fishy[5] += 60;
            }else{
                fishy[5] -= 60;
            }
        }
        if(fishx[5] < -400){
            tst = rand.nextInt(500) + 1100;
            tst2 = rand.nextInt(1000) + 500;
            fishx[5] = tst;
            fishy[5] = tst2;
        }
        if(fishy[5] < 400){
            fishy[5] = 400;
        }
        fish6.setX(fishx[5]);
        fish6.setY(fishy[5]);

        if(kanflag == 0){
            fishx[7] += rand.nextInt(10);
            if(rand.nextInt(100) > 96){
                kanflag = 1;
            }
        }else{
            fishx[7] -= rand.nextInt(10);
            if(rand.nextInt(100) > 96){
                kanflag = 0;
            }
        }
        if(fishx[7] < 200){
            fishx[7] += 10;
        }
        if(fishx[7] > 1000){
            fishx[7] -= 10;
        }
        fishy[7] += rand.nextInt(19) - 9;
        if(fishy[7] < 400){
            fishy[7] = 400;
        }else if(fishy[7] < 600){
            fishy[7] += 2;
        }else if(fishy[7] > 1600){
            fishy[7] -= 2;
        }


        if(rand.nextInt(100) > 95){
            fishy[7] += 10;
        }else if(rand.nextInt(100) > 95){
            fishy[7] -= 10;
        }
        kan.setX(fishx[7]);
        kan.setY(fishy[7]);

        if(bagflag == 0){
            fishx[8] += rand.nextInt(10);
            if(rand.nextInt(100) > 96){
                bagflag = 1;
            }
        }else{
            fishx[8] -= rand.nextInt(10);
            if(rand.nextInt(100) > 96){
                bagflag = 0;
            }
        }
        if(fishx[8] < 200){
            fishx[8] += 10;
        }
        if(fishx[8] > 1000){
            fishx[8] -= 10;
        }
        fishy[8] += rand.nextInt(19) - 9;
        if(fishy[8] < 400){
            fishy[8] = 400;
        }else if(fishy[8] < 600){
            fishy[8] += 2;
        }else if(fishy[8] > 1600){
            fishy[8] -= 2;
        }
        if(rand.nextInt(100) > 95){
            fishy[8] += 10;
        }else if(rand.nextInt(100) > 95){
            fishy[8] -= 10;
        }
        bag.setX(fishx[8]);
        bag.setY(fishy[8]);

        if(fishx[9] < -100){
            fishx[9] += rand.nextInt(2);
        }else{
            fishx[9] += rand.nextInt(10) + 4;
        }
        fishy[9] += rand.nextInt(3) - 1;
        if(fishx[9] > 2000){
            tst = rand.nextInt(300) - 400;
            tst2 = rand.nextInt(1000) + 500;
            fishx[9] = tst;
            fishy[9] = tst2;
        }

        same.setX(fishx[9]);
        same.setY(fishy[9]);

        if(fishx[10] > 1150){
            fishx[10] -= rand.nextInt(2);
        }else{
            fishx[10] -= rand.nextInt(10) + 4;
        }
        fishy[10] += rand.nextInt(3) - 1;
        if(fishx[10] < -500){
            tst = rand.nextInt(500) + 1100;
            tst2 = rand.nextInt(1000) + 500;
            fishx[10] = tst;
            fishy[10] = tst2;
        }

        same2.setX(fishx[10]);
        same2.setY(fishy[10]);

        //tst = fish1.getWidth();
        turiito.setX(540);
        turiito.setY(200 + (turiito.getHeight() * (1 +longbar/(float)8.5)));
        redball.setX(550);
        redball.setY(200 + turiito.getHeight()*(float)1.5 * i/2 + turiito.getHeight() * longbar / (float)4.65);
        //釣り上げ処理
        tst3 = (int)(200 + turiito.getHeight()*(float)1.5 * i/2 + turiito.getHeight() * longbar / (float)4.65);
        tst = fishx[0] + 2 * 84; //口の大体の座標
        tst2 = fishy[0];
        tottatexty = 0; //tottatextの高さ設定用
        if((550 - tst)*(550 - tst)  + (tst3 - tst2) *(tst3 - tst2) < 10000){
            if(commentnum == 0){
                commentnum = 1;
            }
            if(totta == 0){
                tottatext.setX(300);
                tottatext.setY(tottatexty);
                totta = 1;
            }
            tst = rand.nextInt(500) - 600;
            tst2 = rand.nextInt(1000) + 500;
            fishx[0] = tst;
            fishy[0] = tst2;
            tottatext.setTextColor(Color.rgb(229,56,39));
            tottatext.setText("赤い魚を\n釣り上げた!!");
            combo += 1;
            score += 100 * xscore;
            soundPool.play(fishse, 3.0f, 3.0f, 0, 0, 1.0f);
        }
        tst = fishx[3] - 2 * 84; //口の大体の座標
        tst2 = fishy[3];
        if((550 - tst)*(550 - tst)  + (tst3 - tst2) *(tst3 - tst2) < 10000){
            if(commentnum == 0){
                commentnum = 1;
            }
            if(totta == 0){
                tottatext.setX(300);
                tottatext.setY(tottatexty);
                totta = 1;
            }
            tst = rand.nextInt(500) + 1100;
            tst2 = rand.nextInt(1000) + 500;
            fishx[3] = tst;
            fishy[3] = tst2;
            tottatext.setTextColor(Color.rgb(229,56,39));
            tottatext.setText("赤い魚を\n釣り上げた!!");
            combo += 1;
            score += 100 * xscore;
            soundPool.play(fishse, 3.0f, 3.0f, 0, 0, 1.0f);
        }
        tst = fishx[1] + 5 * 42; //口の大体の座標
        tst2 = fishy[1];
        if((550 - tst)*(550 - tst)  + (tst3 - tst2) *(tst3 - tst2) < 10000){
            if(commentnum == 0){
                commentnum = 2;
            }
            if(totta == 0){
                tottatext.setX(300);
                tottatext.setY(tottatexty);
                totta = 1;
            }
            tst = rand.nextInt(500) - 600;
            tst2 = rand.nextInt(1000) + 500;
            fishx[1] = tst;
            fishy[1] = tst2;
            tottatext.setTextColor(Color.rgb(97,123,235));
            tottatext.setText("青い魚を\n釣り上げた!!");
            combo += 1;
            score += 200 * xscore;
            soundPool.play(fishse, 3.0f, 3.0f, 0, 0, 1.0f);
        }
        tst = fishx[4] - 5 * 42; //口の大体の座標
        tst2 = fishy[4];
        if((550 - tst)*(550 - tst)  + (tst3 - tst2) *(tst3 - tst2) < 10000){
            if(commentnum == 0){
                commentnum = 2;
            }
            if(totta == 0){
                tottatext.setX(300);
                tottatext.setY(50);
                totta = 1;
            }
            tst = rand.nextInt(500) + 1100;
            tst2 = rand.nextInt(1000) + 500;
            fishx[4] = tst;
            fishy[4] = tst2;
            tottatext.setTextColor(Color.rgb(97,123,235));
            tottatext.setText("青い魚を\n釣り上げた!!");
            combo += 1;
            score += 200 * xscore;
            soundPool.play(fishse, 3.0f, 3.0f, 0, 0, 1.0f);
        }
        tst = fishx[2] +84; //口の大体の座標
        tst2 = fishy[2];
        if((550 - tst)*(550 - tst)  + (tst3 - tst2) *(tst3 - tst2) < 10000){
            if(commentnum == 0){
                commentnum = 3;
            }
            if(totta == 0){
                tottatext.setX(300);
                tottatext.setY(tottatexty);
                totta = 1;
            }
            tst = rand.nextInt(500) - 600;
            tst2 = rand.nextInt(1000) + 500;
            fishx[2] = tst;
            fishy[2] = tst2;
            tottatext.setTextColor(Color.rgb(244,245,24));
            tottatext.setText("黄色い魚を\n釣り上げた!!");
            combo += 1;
            score += 500 * xscore;
            hp += 5;
            soundPool.play(fishse, 3.0f, 3.0f, 0, 0, 1.0f);
        }
        tst = fishx[5] - 84; //口の大体の座標
        tst2 = fishy[5];
        if((550 - tst)*(550 - tst)  + (tst3 - tst2) *(tst3 - tst2) < 10000){
            if(commentnum == 0){
                commentnum = 3;
            }
            if(totta == 0){
                tottatext.setX(300);
                tottatext.setY(tottatexty);
                totta = 1;
            }
            tst = rand.nextInt(500) + 1100;
            tst2 = rand.nextInt(1000) + 500;
            fishx[5] = tst;
            fishy[5] = tst2;
            tottatext.setTextColor(Color.rgb(244,245,24));
            tottatext.setText("黄色い魚を\n釣り上げた!!");
            combo += 1;
            score += 500 * xscore;
            hp += 5;
            soundPool.play(fishse, 3.0f, 3.0f, 0, 0, 1.0f);
        }
        if((550 - fishx[7])*(550 - fishx[7])  + (tst3 - fishy[7]) *(tst3 - fishy[7]) < 10000){
            fishx[7] = 600;
            fishy[7] = 4000;
            tottatext.setTextColor(Color.rgb(0,0,0));
            tottatext.setText("空き缶を\n釣り上げた!!");
            combo += 1;
            score += 600 * xscore;
            soundPool.play(fishse, 3.0f, 3.0f, 0, 0, 1.0f);
        }
        if((550 - fishx[8])*(550 - fishx[8])  + (tst3 - fishy[8]) *(tst3 - fishy[8]) < 10000){
            fishx[8] = 600;
            fishy[8] = 4000;
            tottatext.setTextColor(Color.rgb(0,0,0));
            tottatext.setText("ビニール袋を\n釣り上げた!!");
            combo += 1;
            score += 800 * xscore;
            soundPool.play(fishse, 3.0f, 3.0f, 0, 0, 1.0f);
        }
        tst = fishx[9] + 3 *84; //口の大体の座標
        tst2 = fishy[9];
        if((550 - tst)*(550 - tst)  + (tst3 - tst2) *(tst3 - tst2) < 10000){
            if(commentnum == 0){
                commentnum = 4;
            }
            if(totta == 0){
                tottatext.setX(300);
                tottatext.setY(tottatexty);
                totta = 1;
            }
            tst = rand.nextInt(500) - 600;
            tst2 = rand.nextInt(1000) + 500;
            fishx[9] = tst;
            fishy[9] = tst2;
            tottatext.setTextColor(Color.rgb(0,47,117));
            tottatext.setText("サメを釣り上げて\nサメパンチされた!!");
            combo = 0;
            score -= 1000 * xscore;
            hp -= 40;
            soundPool.play(fishse, 3.0f, 3.0f, 0, 0, 1.0f);
        }
        tst = fishx[10] - 3 * 84; //口の大体の座標
        tst2 = fishy[10];
        if((550 - tst)*(550 - tst)  + (tst3 - tst2) *(tst3 - tst2) < 10000){
            if(commentnum == 0){
                commentnum = 4;
            }
            if(totta == 0){
                tottatext.setX(300);
                tottatext.setY(tottatexty);
                totta = 1;
            }
            tst = rand.nextInt(500) + 1100;
            tst2 = rand.nextInt(1000) + 500;
            fishx[10] = tst;
            fishy[10] = tst2;
            tottatext.setTextColor(Color.rgb(244,245,24));
            tottatext.setText("サメを釣り上げて\nサメキックされた!!");
            combo = 0;
            score -= 1000 * xscore;
            hp -= 40;
            soundPool.play(fishse, 3.0f, 3.0f, 0, 0, 1.0f);
        }


        scoretext.setX(50);
        scoretext.setY(0);
        hptext.setX(50);
        hptext.setY(100);

        scoretext.setText("Score:"+ score); //box84 //turiito 66 //ball 44
        hptext.setText("HP:"+ hp); //73  //132
        combotext.setX(800);
        combotext.setY(50);
        combotext.setText("Combo:" + combo);
        time -= 1;
        timetext.setText("Time\n" + time);
        timetext.setX(600);
        timetext.setY(50);
        timetext.setScaleX((float)1.5);
        timetext.setScaleY((float)1.5);

        if(hp <= 0 || time <= 0){
            Intent intent = new Intent(getApplication(), GameOver.class);
            //Random rand = new Random();
            load.setX(200);
            load.setY(1500);
            load.setScaleX(2);
            load.setScaleY(2);
            load.setText("ロード中...\nしばし待たれよ!");
            timer.cancel();
            mainbgm.stop();
            intent.putExtra("HPgive",hp);
            intent.putExtra("Scoregive",score);
            intent.putExtra("MapID",1);
            intent.putExtra("Prevscore",prevscore);
            if(hp > 0 && score - prevscore >= 10000){

            }else{
                nomiss = 1;
            }
            intent.putExtra("nomissflag",nomiss);

            if(Clearflag % 2 != 0 && hp > 0 && score >= 10000){
                Clearflag *= 2;
            }
            intent.putExtra("Clearflag",Clearflag);
            startActivity(intent);

        }

        //v = fish1.getHeight();
        //hptext.setText("" + v);
    } //ループ終わり

    @Override
    public boolean onTouchEvent (MotionEvent event) {

        if(gamestart == 0){ //最初だけmoveturiitoを起動
            gamestart = 1;
            time = 6000;
            setumei.setX(3000);
            back.setX(3000);
            wback.setX(3000);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            moveturiito();
                        }
                    });
                }
            }, 0, 5);
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            updown = 1; //最初じゃない時押されている時updownを切り替え

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            updown = 0; //TrueFalseじゃなくて01なのは趣味

        }
        //i += 0.1;
        return true;
    }
}


