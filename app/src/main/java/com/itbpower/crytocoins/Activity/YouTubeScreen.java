package com.itbpower.crytocoins.Activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.itbpower.crytocoins.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class YouTubeScreen extends AppCompatActivity implements YouTubePlayerFullScreenListener {
    String youtube;
    String TAG="YouTubeScreen";
    ImageView iv_back;
    YouTubePlayerView youtube_player_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_screen);
        youtube_player_view  = findViewById(R.id.youtube_player_view);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

            LayoutParams value= new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            youtube_player_view.setLayoutParams(value);

            //Do some stuff
        }else{
            LayoutParams lp = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
            youtube_player_view.setLayoutParams(lp);
            //Do some stuff
        }


        dancevideoGetIntent();
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.iv_back:
                        finish();
                }
            }
        });

        getLifecycle().addObserver(youtube_player_view);
        youtube_player_view.addYouTubePlayerListener(new YouTubePlayerListener() {
            @Override
            public void onReady(com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(youtube,0f);
            }

            @Override
            public void onStateChange(com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer, PlayerConstants.PlayerState playerState) {

            }

            @Override
            public void onPlaybackQualityChange(com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer, PlayerConstants.PlaybackQuality playbackQuality) {

            }

            @Override
            public void onPlaybackRateChange(com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer, PlayerConstants.PlaybackRate playbackRate) {

            }

            @Override
            public void onError(com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer, PlayerConstants.PlayerError playerError) {

            }

            @Override
            public void onCurrentSecond(com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer, float v) {

            }

            @Override
            public void onVideoDuration(com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer, float v) {

            }

            @Override
            public void onVideoLoadedFraction(com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer, float v) {

            }

            @Override
            public void onVideoId(com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer, String s) {

            }

            @Override
            public void onApiChange(com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer youTubePlayer) {

            }
        });


        youtube_player_view.addFullScreenListener(this);

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation== Configuration.ORIENTATION_LANDSCAPE){
            LayoutParams value= new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            youtube_player_view.setLayoutParams(value);
        }else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            LayoutParams lp = new LayoutParams(MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            youtube_player_view.setLayoutParams(lp);



        }


    }

    private void dancevideoGetIntent() {
        youtube = getIntent().getStringExtra("YoutubeLink");
        Log.e(TAG, "personalityGetIntent: "+youtube );
    }

    @Override
    public void onYouTubePlayerEnterFullScreen() {
    }

    @Override
    public void onYouTubePlayerExitFullScreen() {

    }


    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}