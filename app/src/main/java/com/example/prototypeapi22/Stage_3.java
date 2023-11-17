package com.example.prototypeapi22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Stage_3 extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView box;
    private ImageView orange;
    private ImageView pink;
    private ImageView black;

    //Size
    private int frameHeight;
    private int boxSize;
    private int screenWidth;

    //Position
    private float boxY;
    private float orangeX;
    private float orangeY;
    private float pinkX;
    private float pinkY;
    private float blackX;
    private float blackY;

    //Speed
    private int boxSpeed;
    private int orangeSpeed;
    private int pinkSpeed;
    private int blackSpeed;

    //Score
    private int score = 0;
    //load
    TextView load;

    //Handler & Timer
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    //Status
    private boolean action_flg = false;
    private boolean start_flg = false;

    //Sound
    private SoundPlayer soundPlayer;
    int HP;
    int Clearflag;
    int nomiss;

    int time;
    int prevscore;
    ImageView syateki;
    TextView setumei;
    ImageView wback;
    TextView hptext;
    TextView scoretext;
    TextView timetext;
    ImageView haikei;
    MediaPlayer mainbgm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage3);

        syateki = findViewById(R.id.syateki);
        syateki.setScaleX(1.5f);
        syateki.setScaleY(1.5f);
        syateki.setY(-400);
        setumei = findViewById(R.id.setumei);
        setumei.setScaleX(1.5f);
        setumei.setScaleY(1.5f);
        setumei.setY(1600);
        setumei.setX(270);
        wback = findViewById(R.id.wback);
        wback.setScaleY(6);
        wback.setScaleX(20);
        wback.setY(1720);
        wback.setX(300);
        wback.setColorFilter(Color.rgb(255,255,255));
        haikei = findViewById(R.id.haikei);

        //setumei.setTextColor(Color.rgb(255,255,255));
        setumei.setText("射的\nりんごやもも(色の弾)に当たり\nタップしていない間高度が下がり\n" +
                "タップしてる間高度が上がります\nとげとげに注意！！");

        soundPlayer = new SoundPlayer(this);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        Intent intent = getIntent();
        HP = intent.getIntExtra("HPgive", 100);
        score = intent.getIntExtra("Scoregive",0);
        Clearflag = intent.getIntExtra("Clearflag",1);
        nomiss = intent.getIntExtra("nomissflag",0);
        prevscore = score;

        //scoreLabel = findViewById(R.id.scoreLabel);
        startLabel = findViewById(R.id.startLabel);
        box = findViewById(R.id.box);
        orange = findViewById(R.id.orange);
        pink = findViewById(R.id.pink);
        black = findViewById(R.id.black);

        //Screen Size
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenWidth = size.x;
        int screenHeight = size.y;

        boxSpeed = Math.round(screenHeight / 60f);
        orangeSpeed = Math.round(screenWidth / 60f);
        pinkSpeed = Math.round(screenWidth / 36f);
        blackSpeed = Math.round(screenWidth / 45f);

        orange.setX(-80.0f);
        orange.setY(-80.0f);
        pink.setX(-80.0f);
        pink.setY(-80.0f);
        black.setX(-80.0f);
        black.setY(-80.0f);
        hptext = findViewById(R.id.hptext);
        scoretext = findViewById(R.id.scoretext);
        timetext = findViewById(R.id.timetext);
        hptext.setScaleX(1.5f);
        hptext.setScaleY(1.5f);
        timetext.setScaleX(1.5f);
        timetext.setScaleY(1.5f);
        scoretext.setScaleX(1.5f);
        scoretext.setScaleY(1.5f);
        hptext.setX(50);
        hptext.setY(50);
        scoretext.setX(50);
        scoretext.setY(150);
        timetext.setX(500);
        timetext.setY(50);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                // USAGE_MEDIA
                // USAGE_GAME
                .setUsage(AudioAttributes.USAGE_GAME)
                // CONTENT_TYPE_MUSIC
                // CONTENT_TYPE_SPEECH, etc.
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();
        BGM();


        //scoreLabel.setText(getString(R.string.score_label, 0));

        load = findViewById(R.id.load);
        load.setX(3000);
    }

    public void BGM(){
        mainbgm = MediaPlayer.create(this,R.raw.mainbgm);

        mainbgm.start();
    }

    public void changePos() {

        hitCheck();

        //Orange
        orangeX -= orangeSpeed;
        if (orangeX < 0) {
            orangeX = screenWidth + 20;
            orangeY = (float)Math.floor(Math.random() * (frameHeight - orange.getHeight()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);

        //Black
        blackX -= blackSpeed;
        if (blackX < 0) {
            blackX = screenWidth + 10;
            blackY = (float)Math.floor(Math.random() * (frameHeight - black.getHeight()));
        }
        black.setX(blackX);
        black.setY(blackY);

        //Pink
        pinkX -= pinkSpeed;
        if (pinkX < 0) {
            pinkX = screenWidth + 5000;
            pinkY = (float)Math.floor(Math.random() * (frameHeight - pink.getHeight()));
        }
        pink.setX(pinkX);
        pink.setY(pinkY);

        //Box
        if (action_flg) {
            boxY -= boxSpeed;

        } else {
            boxY += boxSpeed;
        }

        if (boxY < 0) boxY = 0;

        //青いボックスが画面の一番下にある時、boxYはframeの高さからボックスの高さを引いた値

        if (boxY > frameHeight - boxSize) boxY = frameHeight - boxSize;

        box.setY(boxY);

        //scoreLabel.setText(getString(R.string.score_label, score));
    }

    public void hitCheck() {
        //Orange
        float orangeCenterX = orangeX + orange.getWidth() / 2.0f;
        float orangeCenterY = orangeY + orange.getWidth() / 2f;

        if (hitStatus(orangeCenterX, orangeCenterY)) {
            orangeX = -10.0f;
            score += 750;
            HP += 5;
            soundPlayer.playHitSound();
        }
        //Pink
        float pinkCenterX = pinkX + pink.getWidth() / 2;
        float pinkCenterY = pinkY + pink.getHeight() / 2;

        if (hitStatus(pinkCenterX, pinkCenterY)) {
            pinkX = -10.0f;
            score += 2250;
            HP += 5;
            soundPlayer.playHitSound();
        }
        //Black
        float blackCenterX = blackX + black.getWidth() / 2;
        float blackCenterY = blackY + black.getHeight() / 2;

        if (hitStatus(blackCenterX, blackCenterY)) {
            HP -= 30;
            score -= 30;
            blackX = -10;
        }
        time -= 4;
        hptext.setText("HP:"+HP);
        scoretext.setText("Score:"  + score);
        timetext.setText("Time:" + time);
        hptext.setTextColor(Color.rgb(255,255,255));
        scoretext.setTextColor(Color.rgb(255,255,255));
        timetext.setTextColor(Color.rgb(255,255,255));
        if(time <= 0 || HP <= 0){
            if(timer != null) {
                timer.cancel();
                timer = null;
                soundPlayer.playOverSound();
            }
            load.setX(200);
            load.setY(1500);
            load.setScaleX(2);
            load.setScaleY(2);
            load.setText("ロード中...\nしばし待たれよ!");
            //mainbgm.stop();
            Intent intent = new Intent(getApplicationContext(), GameOver.class);
            intent.putExtra("Scoregive", score);
            intent.putExtra("HPgive",HP);
            intent.putExtra("MapID",3);
            intent.putExtra("nomissflag",nomiss);
            intent.putExtra("Prevscore",prevscore);
            if(HP > 0 && score - prevscore >= 10000){

            }else{
                nomiss = 1;
            }
            intent.putExtra("nomissflag",nomiss);

            if(Clearflag % 5 != 0 && HP > 0 && score >= 10000){
                Clearflag *= 5;
            }
            intent.putExtra("Clearflag",Clearflag);
            mainbgm.release();
            startActivity(intent);
        }


    }

    public boolean hitStatus(float centerX, float centerY) {
        return (0 <= centerX && centerX <= boxSize &&
                boxY <= centerY && centerY <= boxY + boxSize);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!start_flg) {


            wback.setX(3000);
            setumei.setX(3000);
            syateki.setX(3000);
            haikei.setScaleX(1.3f);
            haikei.setScaleY(1.3f);
            haikei.setX(90);
            haikei.setY(200);
            start_flg = true;
            time = 6000;
            FrameLayout frame = findViewById(R.id.frame);
            frameHeight = frame.getHeight();

            boxY = box.getY();
            boxSize = box.getHeight();
            boxY = 1000;

            startLabel.setVisibility(View.GONE);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);
        } else {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;

            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                action_flg = false;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() { }
}