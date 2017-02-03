package com.example.jovan.soundandvideodemo;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mPlayer;
    AudioManager audioManager;
    VideoView videoView;

    public void playVideo(View view){
        String path = "http://img-9gag-fun.9cache.com/photo/aAPyvp9_460sv.mp4";
        Uri uri = Uri.parse(path);

        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        videoView.setVisibility(View.VISIBLE);

        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();


    }

    public void play(View view){

        mPlayer.start();

    }
    public void pause(View view){

        mPlayer.pause();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        videoView = (VideoView)findViewById(R.id.videoView) ;

       // MediaPlayer mPlayer =  MediaPlayer.create(this, R.raw.thunder);
       // mPlayer.start();
        mPlayer =  MediaPlayer.create(this, R.raw.thunder);




        audioManager =(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);



        SeekBar volumeControl = (SeekBar)findViewById(R.id.seekBar);

        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(curVolume);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean formUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);

            }
        });

        final SeekBar lengthControl = (SeekBar)findViewById(R.id.seekBar2);
        lengthControl.setMax(mPlayer.getDuration());
//Updatuje svake sekunde trenutnu poziciju muzike na seekbar
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                lengthControl.setProgress(mPlayer.getCurrentPosition());

            }
        },0,1000);

        lengthControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                mPlayer.seekTo(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
