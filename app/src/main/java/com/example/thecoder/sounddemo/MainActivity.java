package com.example.thecoder.sounddemo;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    AudioManager audioManager;

    public void playSound(View view)
    {
        //Button play = (Button) findViewById(R.id.button);
        mediaPlayer.start();
    }

    public void pauseSound(View view)
    {
        //MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.roar);
        mediaPlayer.pause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.roar);

        //To set up Audio manager here
        audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        int maxValue = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentV = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar vControl = (SeekBar) findViewById(R.id.vBar);
        vControl.setMax(maxValue);
        vControl.setProgress(currentV);

        vControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Loginfo", Integer.toString(progress));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final SeekBar vControl2 = (SeekBar) findViewById(R.id.vBar2);
        vControl2.setMax(mediaPlayer.getDuration());
        vControl2.setProgress(currentV);

        vControl2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Log.i("Loginfo", "This is for testing == progress");
                //audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,0);
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //mediaPlayer.seekTo(progress);
            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                vControl2.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 300);


    }
}
