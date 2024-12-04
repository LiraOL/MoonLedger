package com.example.moonledger;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int counter = 0;
    private TextView counterTextView;
    private SoundPool soundPool;
    private int tickSound;
    private Animation scaleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counterTextView = findViewById(R.id.counterTextView);
        Button resetButton = findViewById(R.id.resetButton);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        tickSound = soundPool.load(this, R.raw.tick, 1);

        scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 0;
                updateCounter();
            }
        });

        updateCounter();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            counter++;
            playTickSound();
            updateCounter();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            counter--;
            playTickSound();
            updateCounter();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void updateCounter() {
        counterTextView.setText(String.valueOf(counter));
        counterTextView.startAnimation(scaleAnimation);
    }

    private void playTickSound() {
        soundPool.play(tickSound, 1, 1, 0, 0, 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
    }
}