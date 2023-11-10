//ゲーム画面から結果画面への遷移
package com.example.prototypeapi22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.prototypeapi22.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView scoreLabel = findViewById(R.id.scoreLabel);
        TextView highScoreLabel = findViewById(R.id.highScoreLabel);

        int score = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText(getString(R.string.score_result, score));

        //アプリにデータを保存
        SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", MODE_PRIVATE); //SharedPreferencesの準備
        int highScore = sharedPreferences.getInt("HIGH_SCORE", 0); //ハイスコアの取り出し

        if (score > highScore) {
            //ハイスコアを表示(最新スコアがハイスコアを超えた場合)
            highScoreLabel.setText(getString(R.string.high_score, score));

            //最新スコアがハイスコアを超えた場合、ハイスコアを更新
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("HIGH_SCORE", score);
            editor.apply();

        } else {
            //ハイスコアを表示(最新スコアがHigh Scoreを超えなかった場合)
            highScoreLabel.setText(getString(R.string.high_score, highScore));
        }
    }

    public void tryAgain(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onBackPressed() { }
}