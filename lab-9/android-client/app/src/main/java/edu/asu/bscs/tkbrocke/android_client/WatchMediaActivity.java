package edu.asu.bscs.tkbrocke.android_client;

import android.content.Context;
import android.content.res.Configuration;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONObject;

import java.net.URL;

import edu.asu.bscs.tkbrocke.android_client.Network.HttpRequest;
import edu.asu.bscs.tkbrocke.android_client.Network.ServerRequest;

public class WatchMediaActivity extends AppCompatActivity {

    VideoView player;
    private MediaController controller;
    private MediaMetadataRetriever metadataRetriever;

    String filename = "";
    Context context;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_watch_media);

        try {
            filename = getIntent().getStringExtra("filename");
        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving URL for video", Toast.LENGTH_LONG).show();
            finish();
        }

        if(savedInstanceState != null){
            position = savedInstanceState.getInt("position", 0);
        }

        String fileUrl = getString(R.string.mediastreamerserver) + "/" + filename;

        player = (VideoView)findViewById(R.id.player);
        player.setVideoPath(fileUrl);
        controller = new MediaController(this);
        controller.setAnchorView(player);
        player.setMediaController(controller);
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.seekTo(position);
                mp.start();
                // player.start();
            }
        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        outState.putInt("position", player.getCurrentPosition());
        super.onSaveInstanceState(outState);
    }

}
